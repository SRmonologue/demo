package com.ohosure.smart.net.hx.smarthome.protocols;

/**
 * Created by daxing on 2017/3/31.
 */

public class Heartbeat {

    StringBuilder httpMessage = new StringBuilder();

    public Heartbeat(String session, String type) {
        String body = "{\"sessionID\":\"" + session + "\",\"system\":{\"type\":\"" + type + "\"}}";
        httpMessage.append("POST /requestHeartbeat HTTP/1.1\r\n")
                .append("Connection: keep-alive\r\n")
                .append("Content-Length: " + body.length() + "\r\n")
                .append("\r\n").append(body);
    }

    public byte[] getByte() {
        return httpMessage.toString().getBytes();
    }

}
