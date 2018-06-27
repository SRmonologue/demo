package com.ohosure.smart.net;


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
 * App登录后保持后服务器之间的唯一长连接，以http协议发送数据
 * Created by daxing on 2017/3/17.
 */

public class HttpSocketClient {
    private final static String TAG = HttpSocketClient.class.getSimpleName();
    //socket建立连接超时时间
    private final static int CONNECT_TIMEOUT = 5000;
    //socket读超时
    private final static int READ_TIMEOUT = 120000;

    /**
     * 客户端通信情况回调
     */
    interface Callback {
        void onConnectError(String message);

        void onConnectSuccess();

        void onReadError();

        void onReadEnd();

        void onWriteError();

        void onWriteEnd();
    }

    private static HttpSocketClient instance;
    //socket通信
    private Socket mSocket;
    //连接服务器地址
    private String serverHost;
    //连接服务器端口
    private int serverPort;
    //通信是否真正运行标识
    private boolean isRunning;
    // 固定线程分配（读、写）线程池
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
    //待发送给主机的数据队列
    private LinkedBlockingQueue<byte[]> outMessageQueue = new LinkedBlockingQueue<>();
    //接受到主机发来的数据队列
    private LinkedBlockingQueue<HttpMessage> inMessageQueue = new LinkedBlockingQueue<>();
    //通信状态回调监听
    private Callback callback;

    //防止客户端多个长连接实例产生
    private HttpSocketClient(Callback linstener) {
        this.callback = linstener;
    }

    public static HttpSocketClient getInstance(Callback linstener) {
        synchronized (HttpSocketClient.class) {
            if (instance == null)
                instance = new HttpSocketClient(linstener);
        }
        return instance;
    }

    /**
     * 开启Socket连接，并启动读写处理线程
     *
     * @param host
     * @param port
     * @return
     */
    public synchronized void openSocket(String host, int port) {
        if (isRunning) {
            MLog.d(TAG, "通信连接建立中，无需重复建立");
            return;
        }
        boolean socketSuccess = false;
        String res = null;
        try {
            mSocket = new Socket();
            //清空待发送和已接收到未处理完的数据
            inMessageQueue.clear();
            outMessageQueue.clear();
            SocketAddress address = new InetSocketAddress(host, port);
            mSocket.connect(address, CONNECT_TIMEOUT);
            mSocket.setSoTimeout(READ_TIMEOUT);
            serverHost = host;
            serverPort = port;
            System.out.println("serverHost:"+serverHost+",serverPort:"+serverPort);
            socketSuccess = true;
        } catch (UnknownHostException e) {
            res = "域名无法解析，请检查网络";
        } catch (ConnectException e) {
            e.printStackTrace();
            res = "端口失效";
        } catch (SocketTimeoutException e) {
            res = "无响应";
        } catch (SocketException e) {
            e.printStackTrace();
            res = "通信异常";
        } catch (IOException e) {
            res = "通信失败，请检查网络";
        } finally {
            isRunning = socketSuccess;
            System.out.println("isRunning:"+isRunning);
            if (socketSuccess) {
                MLog.d(TAG, "开启链路成功，启动收发线程");
                fixedThreadPool.execute(new ReadRunable());
                fixedThreadPool.execute(new WriteRunable());
                callback.onConnectSuccess();
            } else {
                closeSocket();
                MLog.d(TAG, "开启链路失败");
                callback.onConnectError(res);
            }
        }
    }

    /**
     * 关闭socket
     */
    public synchronized void closeSocket() {
        if (!isRunning) {
            MLog.d(TAG, "通信连接未建立，无需关闭连接");
            return;
        }
        try {
            if (mSocket != null)
                mSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
            MLog.w(TAG, "尝试通信关闭失败：isRunning" + isRunning);
        }
        isRunning = false;
    }


    /**
     * 取出一条服务端发来的消息
     *
     * @return
     */
    public HttpMessage getMessage() {
        try {
            return inMessageQueue.take();
        } catch (InterruptedException e) {
            return new HttpMessage();
        }
    }

    /**
     * 向待发送服务器消息中新增一条消息
     *
     * @param message
     */
    public void setMessage(byte[] message) {
        try {
            outMessageQueue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //--------------- Runnable block-----------

    /**
     * HTTP消息读取预处理
     *
     * @author daxing
     */
    private class ReadRunable implements Runnable {

        @Override
        public void run() {

            boolean readSuccess = true;
            Stream2HttpMessage httpParse = new Stream2HttpMessage();
            InputStream in;
            try {
                in = mSocket.getInputStream();
                while (true) {
                    HttpMessage inMessage = new HttpMessage();
                    if (-1 == httpParse.readResponse(in, inMessage))
                        break;
                    else
                        inMessageQueue.put(inMessage);
                }
            } catch (Exception e) {
                readSuccess = false;
                MLog.w(TAG, "接收消息处理线程异常");
                e.printStackTrace();
            } finally {

                //让发送线程同时结束
                setMessage(new byte[0]);
                if (!readSuccess)
                    callback.onReadError();
                else
                    callback.onReadEnd();
                MLog.d(TAG, "接收线程结束");
                closeSocket();
            }
        }
    }

    /**
     * HTTP消息发送预处理
     *
     * @author daxing
     */
    private class WriteRunable implements Runnable {

        @Override
        public void run() {
            boolean writeSuccess = true;
            OutputStream out;
            try {
                out = mSocket.getOutputStream();


                while (true) {
                    byte[] outMessage = outMessageQueue.take();
                    MLog.i(TAG, new String(outMessage));
                    if (outMessage.length == 0)
                        break;

                    if(outMessage.length>1000)
                    {
                        int i=0;
                        for(;i<outMessage.length/1000;i++)
                        {
                            out.write(outMessage,i*1000,1000);
                            out.flush();
                            Thread.sleep(50);
                        }
                        out.write(outMessage,i*1000,outMessage.length%1000);
                        out.flush();
                    }else {

                        Thread.sleep(50);
                        out.write(outMessage);
                        out.flush();
                    }


//                    out.write(outMessage);
//                    out.flush();
                }
            } catch (Exception e) {
                writeSuccess = false;
                MLog.w(TAG, "发送消息处理线程异常");
                e.printStackTrace();
            } finally {
                if (!writeSuccess)
                    callback.onWriteError();
                else
                    callback.onWriteEnd();
                MLog.d(TAG, "发送线程结束");
                closeSocket();
            }
        }
    }

    //-----------Generate methods part-------------
    public String getServerHost() {
        return serverHost;
    }

    public int getServerPort() {
        return serverPort;
    }

    public boolean isRunning() {
        return isRunning;
    }
}

