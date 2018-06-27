package com.ohosure.smart.core.demo;

/**
 * 描述：
 * Created by 9527 on 2018/6/27.
 */
public interface Client {
    void onInit();
    void sendMessage();
    void receiveMessage();
    void onLoginOut();
}
