package com.ohosure.smart.zigbeegate.protocol;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import com.ohosure.smart.core.Const;
import com.ohosure.smart.core.communication.HttpInMessage;
import com.ohosure.smart.core.communication.HttpSockect;
import com.ohosure.smart.database.devkit.log.MLog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;


public class PrepareDomainHelper {

    private final static String TAG = PrepareDomainHelper.class.getSimpleName();

    private Context mContext;
    private NetCallBack listener;

    public PrepareDomainHelper(Context context) {
        mContext = context;

    }

    public void setCallBackListener(NetCallBack listener) {
        this.listener = listener;
    }

    public static interface NetCallBack {
        void onSerchServer(int rescode, String description);

        void onBindUser(int rescode, String description);

        void onSubmiteRegister(int rescode, String description);

        void onDiscoverGate(int rescode, String description);

        void onChangePassword(int rescode, String description);

        void onSearchGates(int rescode, String description, JSONArray jArray);

        void onSearchAccount(int rescode, String description, JSONObject jObj);
    }

    public void tcpSearchGates(String username, String password) {
        final HttpSockect communicate = new HttpSockect();
        String sockectRes = communicate.openSocket(Const.SERVER,
                Const.PORT);
        if (sockectRes != null) {
            listener.onSerchServer(1, "" + sockectRes);
            return;
        }
        communicate.setMessage(new RequestGateway(username, password));
        try {
            while (true) {
                HttpInMessage inMessage = communicate.getMessage();
                if (TextUtils.isEmpty(inMessage.getJsonContent()))
                    break;

				/*
                 * 执行业务处理逻辑
				 */
                if (inMessage.getHeaders().get("match-request")
                        .indexOf("requestGateway") > 0) {

                    JSONObject jsonObject;

                    jsonObject = new JSONObject(inMessage.getJsonContent());

                    int s = jsonObject.getInt("result");
                    JSONArray jArray = null;
                    if (s == 0) {

                        jArray = jsonObject.optJSONArray("gatewayInfo");

                        listener.onSearchGates(0, "", jArray);
                    } else {
                        MLog.w(TAG, "查询反馈错误码：" + s);
                        if (s == 101)
                            listener.onSearchGates(s, "用户名不存在", null);
                        else if (s == 102)
                            listener.onSearchGates(s, "密码错误", null);
                        else
                            listener.onSearchGates(s, "查询网关失败，错误类型" + s, null);
                    }
                } else {

                    listener.onSearchGates(1, "报文不匹配", null);
                }

            }
        } catch (Exception e) {

            listener.onSearchGates(1, "解析异常", null);
            e.printStackTrace();
        }

        Log.v(TAG, "业务线程结束");
    }

    public void tcpSearchAccount(String unionid, String type) {
        final HttpSockect communicate = new HttpSockect();
        String sockectRes = communicate.openSocket(Const.SERVER,
                Const.PORT);
        if (sockectRes != null) {
            listener.onSerchServer(1, "" + sockectRes);
            return;
        }
        communicate.setMessage(new RequestThirdAccount(unionid, type));
        try {
            while (true) {
                HttpInMessage inMessage = communicate.getMessage();
                if (TextUtils.isEmpty(inMessage.getJsonContent()))
                    break;

				/*
                 * 执行业务处理逻辑
				 */
                if (inMessage.getHeaders().get("match-request")
                        .indexOf("requestThirdAccount") > 0) {

                    JSONObject jsonObject;

                    jsonObject = new JSONObject(inMessage.getJsonContent());

                    int s = jsonObject.getInt("result");
                    if (s == 0) {

                        listener.onSearchAccount(0, "", jsonObject);
                    } else {
                        MLog.w(TAG, "查询反馈错误码：" + s);

                        listener.onSearchAccount(s, "账号对接失败，错误码：" + s, null);

                    }
                } else {

                    listener.onSearchAccount(1, "报文不匹配", null);
                }

            }
        } catch (Exception e) {

            listener.onSearchAccount(1, "解析异常", null);
            e.printStackTrace();
        }

        Log.v(TAG, "业务线程结束");
    }

    public void tcpSearchServer(String username) {
        final HttpSockect communicate = new HttpSockect();
        String sockectRes = communicate.openSocket(Const.ROOT_SERVER,
                Const.ROOT_PORT);
        if (sockectRes != null) {
            listener.onSerchServer(1, "" + sockectRes);
            return;
        }
        communicate.setMessage(new RequestServer(""));

        try {
            while (true) {
                HttpInMessage inMessage = communicate.getMessage();
                if (TextUtils.isEmpty(inMessage.getJsonContent()))
                    break;
                /*
                 * 执行业务处理逻辑
				 */
                if (inMessage.getHeaders().get("match-request")
                        .indexOf("requestServer") > 0) {

                    JSONObject jsonObject = new JSONObject(
                            inMessage.getJsonContent());
                    int result = jsonObject.getInt("result");
                    if (result == 0) {
                        JSONArray jArray = new JSONArray(
                                jsonObject.getString("service"));
                        Const.SERVER = jArray.getJSONObject(0)
                                .getString("uri");
                        Const.PORT = Integer.valueOf(jArray
                                .getJSONObject(0).getString("port"));
                        listener.onSerchServer(0, "");
                    } else {
                        listener.onSerchServer(1, "查询失败");
                    }
                } else {
                    listener.onSerchServer(1, "报文不匹配");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            listener.onSerchServer(1, "解析异常");
        }

        Log.v(TAG, "业务线程结束");
    }

    public void tcpBindUser(String username, String password, String gateName,
                            String bssid, String localGate) {
        final HttpSockect communicate = new HttpSockect();
        String sockectRes = communicate.openSocket(localGate.split(":")[0],
                Integer.valueOf(localGate.split(":")[1]));
        if (sockectRes != null) {
            listener.onBindUser(1, "网关" + sockectRes);
            return;
        }
        communicate.setMessage(new RequestBind(username, password, gateName,
                bssid, localGate.split(":")[2]));

        try {
            while (true) {
                HttpInMessage inMessage = communicate.getMessage();
                if (TextUtils.isEmpty(inMessage.getJsonContent()))
                    break;
                /*
                 * 执行业务处理逻辑
				 */
                if (inMessage.getHeaders().get("match-request")
                        .indexOf("requestBind") > 0) {

                    JSONObject jsonObject = new JSONObject(
                            inMessage.getJsonContent());
                    String s = jsonObject.getString("result");
                    if (s.equalsIgnoreCase("0")) {
                        listener.onBindUser(Integer.valueOf(s), null);
                        MLog.d(TAG, "绑定成功");
                    } else {
                        listener.onBindUser(Integer.valueOf(s), "绑定错误码：" + s);
                        MLog.w(TAG, "绑定错误码：" + s);
                    }

                } else {

                    listener.onBindUser(1, "报文不匹配");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            listener.onBindUser(1, "解析异常");
        }

        Log.v(TAG, "业务线程结束");
    }

    public void tcpBindUser(String username, String password, String gateName,
                            String bssid) {
        tcpBindUser(username, password, gateName, bssid, Const.SERVER + ":" + Const.PORT + ":" + Const.GATE_MAC);
    }

    public void tcpChangePassword(String username, String password) {
        final HttpSockect communicate = new HttpSockect();
        String sockectRes = communicate.openSocket(Const.SERVER,
                Const.PORT);
        if (sockectRes != null) {
            listener.onChangePassword(1, sockectRes);
            return;
        }
        communicate.setMessage(new RequestPassword(username, password));

        try {
            while (true) {
                HttpInMessage inMessage = communicate.getMessage();
                if (TextUtils.isEmpty(inMessage.getJsonContent()))
                    break;
                /*
				 * 执行业务处理逻辑
				 */
                if (inMessage.getHeaders().get("match-request")
                        .indexOf("requestPassword") > 0) {

                    JSONObject jsonObject = new JSONObject(
                            inMessage.getJsonContent());

                    int s = jsonObject.getInt("result");
                    if (s == 0) {

                        MLog.d(TAG, "密码修改成功");
                        listener.onChangePassword(0, "");
                    } else {
                        MLog.w(TAG, "密码修改错误码：" + s);
                        listener.onChangePassword(s, "密码修改错误码：" + s);
                    }
                } else {
                    listener.onChangePassword(1, "报文不匹配");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            listener.onChangePassword(1, "解析异常");
        }

        Log.v(TAG, "业务线程结束");
    }

    public void udpDiscoverGate(final boolean overWrite) {
        Runnable logic = new Runnable() {

            final int MAX_DATA_PACKET_LENGTH = 64;
            byte[] buffer = new byte[MAX_DATA_PACKET_LENGTH];

            @Override
            public void run() {
                DatagramSocket udpSocket = null;

                WifiManager manager = (WifiManager) mContext
                        .getSystemService(Context.WIFI_SERVICE);
                WifiManager.MulticastLock lock = manager
                        .createMulticastLock("discover gate");


                DatagramPacket dataPacket = null, recivedata = null;
                lock.acquire();
                try {

                    udpSocket = new DatagramSocket(Const.UDP_PORT);//只允许数据报发送给指定的目标地址
                    udpSocket.setSoTimeout(Const.UDP_READ_TIMEOUT);//3秒超时
                    dataPacket = new DatagramPacket(buffer,
                            MAX_DATA_PACKET_LENGTH);
                    byte[] data = Const.UDB_MESSAGE.getBytes("UTF-8");
                    dataPacket.setData(data);
                    dataPacket.setLength(data.length);
                    dataPacket.setPort(Const.UDP_PORT);
                    InetAddress broadcastAddr = InetAddress
                            .getByName("255.255.255.255");
                    dataPacket.setAddress(broadcastAddr);
                    recivedata = new DatagramPacket(buffer,
                            MAX_DATA_PACKET_LENGTH);
                    MLog.d("MJ", "收到消息：1");
                    udpSocket.send(dataPacket);
                    while (true) {
                        MLog.d("MJ", "收到消息：2");
                        udpSocket.receive(recivedata);
                        MLog.d("MJ", "收到消息：3");
                        if (recivedata.getLength() != 0) {
                            String gate = new String(buffer, 0,
                                    recivedata.getLength());
                            if (gate.equalsIgnoreCase(Const.UDB_MESSAGE))
                                continue;
                            if (gate.indexOf(":") < 0)
                                continue;
                            MLog.d("MJ", "收到消息：" + gate);
                            if (overWrite) {
                                Const.SERVER = gate.split(":")[0];
                                Const.PORT = Integer.valueOf(gate
                                        .split(":")[1]);
                                Const.GATE_MAC = gate.split(":")[2];
                                listener.onDiscoverGate(0, gate);
                            } else {
                                listener.onDiscoverGate(0, gate);
                            }
                        }

                        break;
                    }


                    MLog.v(TAG, "udp 线程结束");
                    udpSocket.close();
                    lock.release();
                } catch (SocketTimeoutException e) {
                    udpSocket.close();
                    lock.release();
                    listener.onDiscoverGate(1, "本地网关无响应");
                    MLog.w(TAG, "家庭网关无响应");
                } catch (BindException e) {
                    MLog.d(TAG, "网关自发现中...不要频繁尝试");
                    lock.release();
                } catch (SocketException e) {
                    udpSocket.close();
                    lock.release();
                    listener.onDiscoverGate(1, "网络不可用，请检查网络");
                    MLog.w(TAG, "网络创建失败");
                } catch (Exception e) {
                    udpSocket.close();
                    lock.release();
                    listener.onDiscoverGate(1, "自发现网关异常");
                    MLog.e(TAG, e.toString());
                }

            }
        };

        new Thread(logic).start();
    }

    void udpDiscoverGate(int i) {
        Runnable logic = new Runnable() {
            @Override
            public void run() {
                MulticastSocket ds;
                String multicastHost = "224.0.0.8";
                InetAddress receiveAddress;
                MulticastSocket ms = null;
                DatagramPacket dataPacket = null;
                try {
                    ms = new MulticastSocket();//可以将数据包以广播的形式发送给多个客户端，也可以接收，空的构造方法是使用本机地址和随机端口
                    ms.setTimeToLive(32);//只能发送到本站点的网络中
                    ms.setLoopbackMode(true);
                    ms.setReuseAddress(true);
                    // 将本机的IP（这里可以写动态获取的IP）地址放到数据包里，其实server端接收到数据包后也能获取到发包方的IP的
                    byte[] data = "AutoBee".getBytes();
                    InetAddress address = InetAddress.getByName(multicastHost);
                    dataPacket = new DatagramPacket(data, data.length, address, 8888);
                    ms.send(dataPacket);
                    ms.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                try {
                    ds = new MulticastSocket(8888);//使用本机地址，指定端口
                    receiveAddress = InetAddress.getByName(multicastHost);//在给定主机名的情况下确定主机的 IP 地址
                    ds.joinGroup(receiveAddress);//加入指定组
                    new Thread(new udpRunnable(ds)).start();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
        new Thread(logic).start();
    }

    class udpRunnable implements Runnable {
        MulticastSocket ds;

        public udpRunnable(MulticastSocket ds) {
            this.ds = ds;
        }

        public void run() {
            byte buf[] = new byte[1024];
            DatagramPacket dp = new DatagramPacket(buf, 1024);//发送和接收数据报包的套接字
            while (true) {
                try {
                    ds.receive(dp);//接收数据报包
                    String gate=new String(buf, 0, dp.getLength());
                    MLog.d("MJ","receive client message : "
                            + new String(buf, 0, dp.getLength()));

                    Const.SERVER = gate.split(":")[0];
                    Const.PORT = Integer.valueOf(gate
                            .split(":")[1]);
                    Const.GATE_MAC = gate.split(":")[2];
                    listener.onDiscoverGate(0, gate);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }
    /**
     * 自发现本地网关
     */
    public void udpDiscoverGate() {

        udpDiscoverGate(1);
        udpDiscoverGate(true);
    }

    public void tcpSubmitRegister(String username, String password) {
        final HttpSockect communicate = new HttpSockect();
        String sockectRes = communicate.openSocket(Const.SERVER,
                Const.PORT);
        if (sockectRes != null) {
            listener.onSubmiteRegister(1, sockectRes);
            return;
        }
        communicate.setMessage(new RequestRegister(username, password));

        try {
            while (true) {
                HttpInMessage inMessage = communicate.getMessage();
                if (TextUtils.isEmpty(inMessage.getJsonContent()))
                    break;
				/*
				 * 执行业务处理逻辑
				 */
                if (inMessage.getHeaders().get("match-request")
                        .indexOf("requestRegister") > 0) {

                    JSONObject jsonObject = new JSONObject(
                            inMessage.getJsonContent());

                    int s = jsonObject.getInt("result");

                    if (s == 0) {

                        MLog.d(TAG, "注册成功");
                        listener.onSubmiteRegister(0, "");
                    } else {
                        MLog.w(TAG, "注册错误码：" + s);
                        listener.onSubmiteRegister(s, "注册错误码：" + s);
                    }

                } else {

                    listener.onSubmiteRegister(1, "报文不匹配");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            listener.onSubmiteRegister(1, "解析异常");
        }

        Log.v(TAG, "业务线程结束");
    }
}
