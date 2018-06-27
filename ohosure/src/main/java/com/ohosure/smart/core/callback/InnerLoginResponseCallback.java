package com.ohosure.smart.core.callback;

/**
 * 描述：
 * Created by 9527 on 2018/6/26.
 */
public interface InnerLoginResponseCallback {
    void onSuccess();
    void onError(String res);
}
