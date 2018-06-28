package com.ohosure.smart.core.callback;

/**
 * 描述：
 * Created by 9527 on 2018/6/28.
 */
public interface RoomResponseCallback {
    void onSuccess(String res);

    void onError(String res);
}
