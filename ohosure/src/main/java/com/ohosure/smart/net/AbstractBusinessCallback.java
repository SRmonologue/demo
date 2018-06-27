package com.ohosure.smart.net;

/**
 * Created by daxing on 2017/3/19.
 */

public abstract class AbstractBusinessCallback {
    public abstract void onMqttFeedback(String mqtt);
    public abstract void onServerFeedback(HttpMessage inMessage);
    public abstract void onServerIn(HttpMessage inMessage);
    public abstract void onLoginResult(int result);
    public abstract void onLoginOut();
    public abstract void onConnectLost();
}
