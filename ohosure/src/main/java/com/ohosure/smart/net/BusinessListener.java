package com.ohosure.smart.net;

/**
 * Created by daxing on 2017/3/19.
 */

public interface BusinessListener {
    void onResponseAuth(HttpMessage inMessage);

    void onResponseHeartbeat(HttpMessage inMessage);

    void onResponseCancel();

    void onConnectLost();
}
