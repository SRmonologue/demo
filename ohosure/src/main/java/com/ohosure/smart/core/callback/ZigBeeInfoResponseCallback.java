package com.ohosure.smart.core.callback;

/**
 * 描述：
 * Created by 9527 on 2018/7/10.
 */
public interface ZigBeeInfoResponseCallback {
    void onSuccess(String host, int port, String mac);

    void onError(String msg);
}
