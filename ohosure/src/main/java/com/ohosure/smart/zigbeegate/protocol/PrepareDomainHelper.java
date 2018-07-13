package com.ohosure.smart.zigbeegate.protocol;

import android.content.Context;
import android.net.wifi.WifiManager;
import com.ohosure.smart.core.Const;
import com.ohosure.smart.database.devkit.log.MLog;
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

    public interface NetCallBack {
        void onDiscoverGate(int rescode, String description);
    }


    public void udpDiscoverGate() {
        udpDiscoverGate(1);
        udpDiscoverGate(true);
    }

    public void udpDiscoverGate(final boolean overWrite) {
        Runnable logic = new Runnable() {

            final int MAX_DATA_PACKET_LENGTH = 64;
            byte[] buffer = new byte[MAX_DATA_PACKET_LENGTH];

            @Override
            public void run() {
                DatagramSocket udpSocket = null;

                WifiManager manager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
                WifiManager.MulticastLock lock = manager.createMulticastLock("discover gate");

                DatagramPacket dataPacket = null, recivedata = null;
                lock.acquire();
                try {
                    udpSocket = new DatagramSocket(Const.UDP_PORT);
                    udpSocket.setSoTimeout(Const.UDP_READ_TIMEOUT);
                    dataPacket = new DatagramPacket(buffer, MAX_DATA_PACKET_LENGTH);
                    byte[] data = Const.UDB_MESSAGE.getBytes("UTF-8");
                    dataPacket.setData(data);
                    dataPacket.setLength(data.length);
                    dataPacket.setPort(Const.UDP_PORT);
                    InetAddress broadcastAddr = InetAddress.getByName("255.255.255.255");
                    dataPacket.setAddress(broadcastAddr);
                    recivedata = new DatagramPacket(buffer, MAX_DATA_PACKET_LENGTH);
                    udpSocket.send(dataPacket);
                    while (true) {
                        udpSocket.receive(recivedata);
                        if (recivedata.getLength() != 0) {
                            String gate = new String(buffer, 0, recivedata.getLength());
                            if (gate.equalsIgnoreCase(Const.UDB_MESSAGE))
                                continue;
                            if (!gate.contains(":"))
                                continue;
                            MLog.d(TAG, "收到消息：" + gate);
                            if (overWrite) {
                                Const.SERVER = gate.split(":")[0];
                                Const.PORT = Integer.valueOf(gate.split(":")[1]);
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
                    ms = new MulticastSocket();
                    ms.setTimeToLive(32);
                    ms.setLoopbackMode(true);
                    ms.setReuseAddress(true);
                    byte[] data = "AutoBee".getBytes();
                    InetAddress address = InetAddress.getByName(multicastHost);
                    dataPacket = new DatagramPacket(data, data.length, address, 8888);
                    ms.send(dataPacket);
                    ms.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    ds = new MulticastSocket(8888);
                    receiveAddress = InetAddress.getByName(multicastHost);
                    ds.joinGroup(receiveAddress);
                    new Thread(new udpRunnable(ds)).start();
                } catch (Exception e) {
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
            DatagramPacket dp = new DatagramPacket(buf, 1024);
            while (true) {
                try {
                    ds.receive(dp);
                    String gate = new String(buf, 0, dp.getLength());
                    MLog.d(TAG, "receive client message : " + new String(buf, 0, dp.getLength()));
                    Const.SERVER = gate.split(":")[0];
                    Const.PORT = Integer.valueOf(gate.split(":")[1]);
                    Const.GATE_MAC = gate.split(":")[2];
                    listener.onDiscoverGate(0, gate);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
