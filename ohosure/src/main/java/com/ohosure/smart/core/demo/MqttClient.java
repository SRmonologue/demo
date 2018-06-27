package com.ohosure.smart.core.demo;

import android.content.Context;

import org.eclipse.paho.android.service.MqttAndroidClient;

/**
 * 描述：
 * Created by 9527 on 2018/6/27.
 */
public class MqttClient extends MqttAndroidClient implements Client{

    public MqttClient(Context context, String serverURI, String clientId) {
        super(context, serverURI, clientId);
    }

    @Override
    public void onInit() {

    }

    @Override
    public void sendMessage() {

    }

    @Override
    public void receiveMessage() {

    }

    @Override
    public void onLoginOut() {

    }
}
