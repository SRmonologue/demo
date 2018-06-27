package com.ohosure.smart.core.communication;

import android.text.TextUtils;

import com.ohosure.smart.core.Const;
import com.ohosure.smart.core.tools.HttpMessageParse;
import com.ohosure.smart.database.devkit.log.MLog;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;



/**
 * HTTP通信服务类 特点：收、发独立线程、阻塞消息队列、固定线程池
 * 
 * @author daxing
 *
 */
public class HttpSockect {

	private final static String TAG = HttpSockect.class.getSimpleName();
	private Socket mSocket;
	/**
	 * HTTP 报文解析器
	 */
	private HttpMessageParse httpParse;
	/**
	 * HTTP 已接受报文阻塞队列
	 */
	private LinkedBlockingQueue<HttpInMessage> inMessageQueue;
	/**
	 * HTTP 待发送报文阻塞队列
	 */
	private LinkedBlockingQueue<HttpOutMessage> outMessageQueue;
	/**
	 * HTTP 长连接标志位，作用于read timeout
	 */
	private boolean keepAlive = false;
	/**
	 * 固定线程分配（读、写）线程池
	 */
	private ExecutorService fixedThreadPool ;

	/**
	 * Constructor()
	 */
	public HttpSockect() {
		this(false);
	}

	/**
	 * Constructor(boolean)
	 * 
	 * @param keepAlive
	 *            是否为长连接
	 */
	public HttpSockect(boolean keepAlive) {
		this.keepAlive = keepAlive;
		fixedThreadPool  = Executors.newFixedThreadPool(2);
		httpParse = new HttpMessageParse();
		inMessageQueue = new LinkedBlockingQueue<HttpInMessage>();
		outMessageQueue = new LinkedBlockingQueue<HttpOutMessage>();
	}

	/**
	 * 开启Socket连接，并启动读写处理线程
	 * @param host
	 * @param port
	 * @return
	 */
	public String openSocket(String host, int port) {
		boolean socketSuccess = false;
		String res = null;
		try {
			mSocket = new Socket();
			inMessageQueue.clear();
			outMessageQueue.clear();
			SocketAddress address = new InetSocketAddress(host, port);
			// 如果是短链接，通信超时后自动断开
			if (!keepAlive)
				mSocket.setSoTimeout(Const.HTTP_READ_TIMEOUT);
			mSocket.connect(address, Const.HTTP_CONNECT_TIMEOUT);
			MLog.v(TAG, "Socket connected success in Thread "
					+ Thread.currentThread().getId());
			socketSuccess = true;
		}

		catch (UnknownHostException e) {
			res = "域名无法解析，请检查网络";
		} catch (ConnectException e) {
			res = "端口失效";
		} catch (SocketTimeoutException e) {
			res = "无响应";
		} catch (SocketException e) {
			res = "通信异常";
		} catch (IOException e) {
			res = "通信失败，请检查网络";
		} finally {
			if (socketSuccess) {
				fixedThreadPool.execute(new ReadRunable());
				fixedThreadPool.execute(new WriteRunable());
			}
		}
		return res;
	}

	/**
	 * 关闭socket
	 */
	public void closeSocket() {
		try {
			mSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * HTTP 消息读取
	 * @author daxing
	 *
	 */
	private class ReadRunable implements Runnable {

		@Override
		public void run() {
			InputStream in;
			MLog.v(TAG, "Thread http read "+ Thread.currentThread().getId());
			try {
				in = mSocket.getInputStream();
				while (true) {
					HttpInMessage inMessage = new HttpInMessage();
					if (-1 == httpParse.readResponse(in, inMessage))
						break;
					else
						inMessageQueue.put(inMessage);
				}
			} catch (Exception e) {

			} finally {
				MLog.v(TAG, "thread read over "+ Thread.currentThread().getId());
				try {
					// 伪装一条操作报文，通知写线程结束
					outMessageQueue.put(new HttpOutMessage());
					// 伪装一条反馈报文，通知业务线程结束
					inMessageQueue.put(new HttpInMessage());

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * HTTP 消息发送
	 * @author daxing
	 *
	 */
	private class WriteRunable implements Runnable {

		@Override
		public void run() {
			OutputStream out;
			MLog.v(TAG, "Thread http write "+ Thread.currentThread().getId());
			try {
				out = mSocket.getOutputStream();
				while (true) {
					HttpOutMessage outMessage = outMessageQueue.take();
					if (TextUtils.isEmpty(outMessage.getString()))
						break;
					MLog.i(TAG, outMessage.getString());
					out.write(outMessage.getString().getBytes());
					out.flush();
				}
			} catch (Exception e) {
			} finally {
				MLog.v(TAG, "thread write over "+ Thread.currentThread().getId());
			}
		}

	}

	/**
	 * 获取一条http报文
	 * @return
	 */
	public HttpInMessage getMessage() {
		try {
			return inMessageQueue.take();
		} catch (InterruptedException e) {
			return new HttpInMessage();
		}
	}

	/**
	 * 发送一条http报文
	 * @param message
	 */
	public void setMessage(HttpOutMessage message) {
		try {
			outMessageQueue.put(message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
