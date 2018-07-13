package com.ohosure.smart.net;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Keep;
import android.text.TextUtils;

import com.ohosure.smart.core.Const;
import com.ohosure.smart.database.devkit.log.MLog;
import com.ohosure.smart.net.hx.smarthome.protocols.Auth;
import com.ohosure.smart.net.hx.smarthome.protocols.Cancel;
import com.ohosure.smart.net.hx.smarthome.protocols.Heartbeat;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.ohosure.smart.core.Const.DEFAULT_BIND_USERNAME;


/**
 * Created by daxing on 2017/3/17.
 */

@Keep
public class CoreService extends Service {

    private static final String TAG = CoreService.class.getSimpleName();
    //身份认证丢失
    private static final int AUTHENTICATION_LOST = 999;
    //终端辨识类型
    private static final String CLIENT_TYPE = "and_ab";
    //固定线程池，开启通信、业务逻辑处理线程
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
    //登录session
    private String session;
    //已登录标识
    private volatile boolean isLogin;
    //长连接通信实例
    private HttpSocketClient mClient;
    //主要用户失联重连
    private UIHandler mUIHandler;
    //业务服务器连接必须信息
    private ConnectInfo mConnectInfo;
    //实际业务逻辑处理回调
    private AbstractBusinessCallback businessCallback;
    //派发处理服务器收到的消息队列中的任务
    private MessageDispatchRunnable messageDispatchRunnable = new MessageDispatchRunnable();
    //心跳监听
    private HeartBeatWatcher heartBeatWatcher = new HeartBeatWatcher();
    //用户主动退出标识
    private boolean isCancel;

    private MqttAndroidClient client;
    private MqttConnectOptions conOpt;
    private boolean mDisconnect = false;

    private static int[] Qos = {2, 2};

    public class BusinessManager extends Binder {
        public ConnectInfo getConnectInfo() {
            return mConnectInfo;
        }

        public String getSession() {
            return session;
        }

        public boolean isLogin() {
            return isLogin;
        }

        public void sendControl(byte[] message) {
            mClient.setMessage(message);
        }

        public void publishMsg(byte[] message) {
            publish(message);
        }

        public void publishHttpMsg(byte[] message) {
            publishHttp(message);
        }


        public void cancelLogin() {

            if (mConnectInfo.name.equalsIgnoreCase(DEFAULT_BIND_USERNAME)) {

                mClient.setMessage(new Cancel(session, CLIENT_TYPE).getByte());

                heartBeatWatcher.onResponseCancel();
            } else {

                mqttDisconnect();
            }
        }

        public void setBusinessCallback(AbstractBusinessCallback callback) {
            businessCallback = callback;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mConnectInfo = new ConnectInfo();
        SharedPreferences preferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        mConnectInfo.name = preferences.getString("name", "");
        mConnectInfo.password = preferences.getString("password", "");
        mConnectInfo.mac = preferences.getString("mac", "");
        mConnectInfo.host = preferences.getString("host", "");
        mConnectInfo.port = preferences.getInt("port", 0);

        mUIHandler = new UIHandler(this);
        //后台通信单例
        mClient = HttpSocketClient.getInstance(mLinstener);
        if (mConnectInfo.name.equalsIgnoreCase(DEFAULT_BIND_USERNAME)) {

        } else {

            if (client != null) {
                if (!TextUtils.equals(client.getServerURI(), mConnectInfo.getHost()) || !TextUtils.equals(client.getClientId(), mConnectInfo.getClientId())) {
                    initMqtt();
                }
            } else {
                if (!mConnectInfo.getName().isEmpty()) {
                    initMqtt();
                }
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new BusinessManager();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            ConnectInfo connectInfo = (ConnectInfo) intent.getSerializableExtra("ConnectInfo");
            if (connectInfo != null) {
                mConnectInfo.port = connectInfo.port;
                mConnectInfo.host = connectInfo.host;
                mConnectInfo.name = connectInfo.name;
                mConnectInfo.password = connectInfo.password;
                mConnectInfo.mac = connectInfo.mac;
                mConnectInfo.clientId = connectInfo.clientId;

                if (mConnectInfo.name.equalsIgnoreCase(DEFAULT_BIND_USERNAME)) {

                } else {

                    if (client != null) {
                        if (!TextUtils.equals(client.getServerURI(), mConnectInfo.getHost()) || !TextUtils.equals(client.getClientId(), mConnectInfo.getClientId())) {
                            initMqtt();
                        }
                    } else {
                        if (!mConnectInfo.getName().isEmpty()) {
                            initMqtt();
                        }
                    }

                    //修改密码后
                    SharedPreferences preferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                    if (preferences.getBoolean("modify", false)) {
                        initMqtt();
                    }
                }
            }
        }
        if (mConnectInfo.name.equalsIgnoreCase(DEFAULT_BIND_USERNAME)) {

            conncet();
        } else {

            doClientConnection();
        }

        return START_STICKY;
    }

    /**
     * 重新连接服务器
     *
     * @param delayTime 秒
     */
    private synchronized void reLogin(int delayTime) {

        //存在重连任务则抛弃其他重连请求
        if (mUIHandler.hasMessages(AUTHENTICATION_LOST))
            return;
        //用户主动退出后不进行任何重连操作
        MLog.d(TAG, "立即通知用户掉线，在" + delayTime + "秒后重连");
        if (isCancel) {
            MLog.d(TAG, "用户注销或登录信息已清除，放弃自动重连");
            return;
        }
        Message msg = mUIHandler.obtainMessage();
        msg.arg1 = delayTime;
        msg.what = AUTHENTICATION_LOST;
        mUIHandler.sendMessageDelayed(msg, delayTime * 1000);
    }

    //长连接监听
    HttpSocketClient.Callback mLinstener = new HttpSocketClient.Callback() {
        @Override
        public void onConnectError(String message) {

            //通知掉线
            if (heartBeatWatcher != null)
                heartBeatWatcher.onConnectLost();
            //网络或服务器状况不好延迟较长时间后进行尝试登录

            if (TextUtils.isEmpty(mClient.getServerHost())) {
                MLog.d(TAG, "没有存储的服务器地址，放弃建立连接");
                return;
            }
            reLogin(20);
            MLog.d(TAG, message);
        }

        @Override
        public void onConnectSuccess() {
            //账号信息不完整断开长连接且无需再自动发起连接
            System.out.println("onConnectSuccess");
            if (TextUtils.isEmpty(mConnectInfo.password)) {
                isCancel = true;
                MLog.d(TAG, "用户已注销，断开后台长连接");
                mClient.closeSocket();
                return;
            }

            //启动派发线程
            if (!messageDispatchRunnable.isRunning)
                fixedThreadPool.execute(messageDispatchRunnable);

            //连接建立成功后直接自动登录
            mClient.setMessage(new Auth(mConnectInfo.name, mConnectInfo.password, mConnectInfo.mac, CLIENT_TYPE).getByte());

        }

        @Override
        public void onReadError() {
            //通知掉线
            if (heartBeatWatcher != null)
                heartBeatWatcher.onConnectLost();
            reLogin(5);

        }

        @Override
        public void onReadEnd() {
            //通知掉线
            if (heartBeatWatcher != null)
                heartBeatWatcher.onConnectLost();
            reLogin(5);
        }

        @Override
        public void onWriteError() {
            //通知掉线
            if (heartBeatWatcher != null)
                heartBeatWatcher.onConnectLost();
            reLogin(0);

        }

        @Override
        public void onWriteEnd() {
            //通知掉线
            if (heartBeatWatcher != null)
                heartBeatWatcher.onConnectLost();
            reLogin(0);
        }
    };


    static class UIHandler extends Handler {
        WeakReference<CoreService> serviceWeakReference;

        public UIHandler(CoreService service) {
            serviceWeakReference = new WeakReference<CoreService>(service);
        }

        @Override
        public void handleMessage(Message msg) {
            CoreService service;
            if ((service = serviceWeakReference.get()) != null) {
                switch (msg.what) {
                    case AUTHENTICATION_LOST:
                        service.conncet();
                        break;
                }
            }
        }
    }


    class HeartBeatWatcher implements BusinessListener {
        //最近一次收到心跳回复时间戳
        long lastTimeStamp;
        //心跳时间间隔
        int repeatTime = 20000;
        //心跳定时器
        Timer timer;

        private void sendHeartBeat() {
            //三次未收到心跳包，服务器无响应
            if (lastTimeStamp != 0 && (System.currentTimeMillis() - lastTimeStamp) > 3 * repeatTime) {
                MLog.d(TAG, "三次未收到心跳包服务端无响应，关闭连接");
                //关闭socket连接
                mClient.closeSocket();
            } else {
                //正常发心跳
                mClient.setMessage(new Heartbeat(session, CLIENT_TYPE).getByte());
            }
        }

        @Override
        public synchronized void onResponseAuth(HttpMessage inMessage) {
            int result = -1;
            MLog.i(TAG, "-------->>result:" + result + "<<--------");
            try {
                JSONObject jsonObject = new JSONObject(
                        inMessage.getResponse());
                result = jsonObject.getInt("result");
                if (result == 0) {

                    isCancel = false;
                    session = jsonObject.optString("sessionID");
                    //保证仅一个心跳线程监听
                    if (isLogin)
                        return;

                    isLogin = true;
                    SharedPreferences sharedPreferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("host", mConnectInfo.host);
                    editor.putInt("port", mConnectInfo.port);
                    editor.putString("name", mConnectInfo.name);
                    editor.putString("password", mConnectInfo.password);
                    editor.putString("mac", mConnectInfo.mac);
                    editor.commit();
                    //每次心跳线程开启重置时间戳
                    lastTimeStamp = 0;
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            sendHeartBeat();
                        }
                    }, 0, repeatTime);
                } else {
                    isLogin = false;
                    isCancel = true;
                    mClient.closeSocket();
                    MLog.d(TAG, "关闭连接，登录失败Code=" + result);
                }
            } catch (Exception e) {
                isLogin = false;
                isCancel = true;
                mClient.closeSocket();
                MLog.d(TAG, "关闭连接，登录异常");
                MLog.e(TAG, "！！！不可忽视的异常！！！");
                e.printStackTrace();
            } finally {
                if (businessCallback != null)
                    businessCallback.onLoginResult(result);
            }
        }

        @Override
        public void onResponseHeartbeat(HttpMessage inMessage) {
            lastTimeStamp = System.currentTimeMillis();
        }

        @Override
        public void onResponseCancel() {
            isLogin = false;
            isCancel = true;
            //防止服务器无响应的时候，确保用户的主动退出能关闭原先持有的长连接
            mClient.closeSocket();
            if (timer != null) {
                timer.cancel();
            }
            if (businessCallback != null)
                businessCallback.onLoginOut();
        }

        @Override
        public void onConnectLost() {
            isLogin = false;
            if (timer != null) {
                timer.cancel();
            }
            if (businessCallback != null)
                businessCallback.onConnectLost();
        }
    }


    /**
     * 尝试连接智能主机（云端或本地）
     */
    private void conncet() {
        if (mClient.isRunning()) {
            if (!TextUtils.equals(mClient.getServerHost(), mConnectInfo.host) || mClient.getServerPort() != mConnectInfo.port) {
                fixedThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        //切换服务器强制断开上次连接
                        mClient.closeSocket();
                        //重新连接服务器
                        mClient.openSocket(mConnectInfo.host, mConnectInfo.port);
                    }
                });
            }
        } else {
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    mClient.openSocket(mConnectInfo.host, mConnectInfo.port);
                }
            });
        }

    }


    /**
     * 派发消息
     */
    class MessageDispatchRunnable implements Runnable {

        public volatile boolean isRunning = false;

        @Override
        public void run() {
            isRunning = true;
            try {
                while (true) {
                    HttpMessage inMessage = mClient.getMessage();
                    if (inMessage.getStatus().indexOf("POST") >= 0) {
                        if (businessCallback != null) {
                            businessCallback.onServerIn(inMessage);
                        }
                    } else {
                        String matchURI = inMessage.getHeaders().get("match-request");
                        if (matchURI.indexOf("requestAuth") > 0)
                            heartBeatWatcher.onResponseAuth(inMessage);
                        else if (matchURI.indexOf("requestHeartbeat") > 0)
                            heartBeatWatcher.onResponseHeartbeat(inMessage);
                        else if (matchURI.indexOf("requestCancel") > 0) {
                            heartBeatWatcher.onResponseCancel();
                        } else {
                            if (businessCallback != null) {
                                businessCallback.onServerFeedback(inMessage);
                            }
                        }

                    }

                }
            } catch (Exception e) {
                MLog.w(TAG, "派发处理线程异常终止，重新启动");
                fixedThreadPool.execute(messageDispatchRunnable);
                e.printStackTrace();
            } finally {
                MLog.d(TAG, "！！！派发处理线程结束！！！");
                isRunning = false;


            }
        }
    }


    /**
     * 初始化MQTT参数
     */
    public void initMqtt() {
        client = new MqttAndroidClient(this, mConnectInfo.getHost(), mConnectInfo.getClientId());
        // 设置MQTT监听并且接受消息
        client.setCallback(mqttCallback);

        conOpt = new MqttConnectOptions();

        // 清除缓存
        conOpt.setCleanSession(true);
        // 设置超时时间，单位：秒
        conOpt.setConnectionTimeout(10);
        // 心跳包发送间隔，单位：秒
        conOpt.setKeepAliveInterval(20);
        // 用户名
        //        conOpt.setUserName(mConnectInfo.getName());
        conOpt.setUserName(mConnectInfo.name);
        // 密码
        //        conOpt.setPassword(mConnectInfo.getPassword().toCharArray());
        conOpt.setPassword(mConnectInfo.password.toCharArray());

        //        conOpt.setAutomaticReconnect(true);


    }

    /**
     * 连接MQTT服务器
     */
    private void doClientConnection() {
        if (client == null) {
            return;
        }
        if (!isConnectIsNormal()) {
            MLog.d(TAG, "没有可用网络");
            return;
        }
        if (!client.isConnected() && isConnectIsNormal()) {
            try {

                client.connect(conOpt, null, iMqttActionListener);
                mDisconnect = false;
            } catch (MqttException e) {

            }
        } else {
            try {
                client.disconnect();
                client.connect(conOpt, null, iMqttActionListener);
                mDisconnect = false;
            } catch (MqttException e) {
            }

        }

    }

    private void mqttDisconnect() {
        try {
            if (client != null && client.isConnected()) {
                mDisconnect = true;
                client.disconnect();
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * MQTT是否连接成功
     */

    private IMqttActionListener iMqttActionListener = new IMqttActionListener() {

        @Override
        public void onSuccess(IMqttToken arg0) {
            MLog.i(TAG, "---------------->>MQTT连接成功<<----------------");
            isLogin = true;
            SharedPreferences sharedPreferences = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("host", mConnectInfo.getHost());
            editor.putString("name", mConnectInfo.getName());
            editor.putString("password", mConnectInfo.getPassword());
            editor.putString("clientid", mConnectInfo.getClientId());
            editor.commit();

            try {
                // 订阅myTopic话题    Qos = 0至多一次;Qos = 1至少一次;Qos = 2刚好一次;
                String[] myTopic2 = {Const.GATE_MAC + "/topicReply", Const.GATE_MAC + "/topicResponse"};
                client.subscribe(myTopic2, Qos);

                businessCallback.onLoginResult(0);
            } catch (MqttException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(IMqttToken arg0, Throwable arg1) {
            arg1.printStackTrace();
            // 连接失败，重连
            if (!isLogin) {
                MLog.i(TAG, "---------------->>MQTT链接失败，未登陆<<----------------");
                businessCallback.onLoginResult(105);
            } else {
                MLog.i(TAG, "---------------->>MQTT链接失败，已登陆<<----------------");
                isLogin = false;
                businessCallback.onLoginOut();
            }


        }
    };

    /**
     * MQTT监听并且接受消息
     */
    private MqttCallback mqttCallback = new MqttCallback() {

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            MLog.i(TAG, "---------messageArrived--------->>:" + new String(message.getPayload()));
            MLog.i(TAG, "---------topic--------->>:" + topic);
            if (topic.equalsIgnoreCase(Const.GATE_MAC + "/topicReply")) {
                businessCallback.onMqttFeedback(new String(message.getPayload()));
            } else if (topic.equalsIgnoreCase(Const.GATE_MAC + "/topicResponse")) {
                try {
                    String msg = new String(message.getPayload());
                    JSONObject jsonObject = new JSONObject(msg);
                    String key = jsonObject.optString("key");
                    String data = jsonObject.optString("data");
                    MLog.i(TAG, "---------key--------->>:" + key);
                    MLog.i(TAG, "---------data--------->>:" + data);
                    HashMap<String, String> map = new HashMap<>();
                    map.put("match-request", "http/" + key);

                    HttpMessage httpMessage = new HttpMessage();
                    httpMessage.setHeaders(map);
                    httpMessage.setResponse(data);
                    businessCallback.onServerFeedback(httpMessage);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken arg0) {
            try {
                MLog.i(TAG, "---------deliveryComplete--------->>:" + new String(arg0.getMessage().getPayload()).toString());
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void connectionLost(Throwable arg0) {
            // 失去连接，重连
            if (mDisconnect) {
                isLogin = false;
                businessCallback.onLoginOut();
                MLog.i(TAG, "---------主动退出，MQTT断开---------");
                return;
            }
            MLog.i(TAG, "---------断开链接，connectionLost---------" + arg0);

            doClientConnection();
        }
    };

    /**
     * MQTT发布消息
     */
    public void publish(byte[] msg) {
        String topic = Const.GATE_MAC + "/topicSend";
        Integer qos = 2;
        Boolean retained = false;
        try {
            if (client != null) {
                MLog.i(TAG, "------SendTopic------->>" + topic);
                client.publish(topic, msg, qos.intValue(), retained.booleanValue());
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * MQTT发布消息
     */
    public void publishHttp(byte[] msg) {
        String topic = Const.GATE_MAC + "/topicQuery";

        Integer qos = 2;
        Boolean retained = false;
        try {
            if (client != null) {
                MLog.i(TAG, "------SendTopic------->>" + topic);
                client.publish(topic, msg, qos.intValue(), retained.booleanValue());
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * MQTT订阅消息
     */
    public void subscribe(String topic) {
        Integer qos = 2;
        try {
            if (client != null) {
                client.subscribe(topic, qos.intValue());
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    /**
     * 判断网络是否连接
     */
    private boolean isConnectIsNormal() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            String name = info.getTypeName();
            MLog.i(TAG, "MQTT当前网络名称：" + name);
            return true;
        } else {
            MLog.i(TAG, "MQTT 没有可用网络");
            isLogin = false;
            businessCallback.onLoginOut();
            return false;
        }
    }

}
