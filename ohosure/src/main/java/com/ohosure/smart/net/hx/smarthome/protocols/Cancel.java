package com.ohosure.smart.net.hx.smarthome.protocols;

/**
 * Created by daxing on 2017/3/31.
 */

public class Cancel {

    StringBuilder httpMessage = new StringBuilder();

    public Cancel(String session, String type) {
        String body = "{\"sessionID\":\"" + session + "\", \"system\":{\"type\":\""+type+"\" } }";
        httpMessage.append("POST /requestCancel HTTP/1.1\r\n")
                .append("Connection: close\r\n")
                .append("Content-Length: " + body.length() + "\r\n")
                .append("\r\n").append(body);
    }

    public byte[] getByte() {
        return httpMessage.toString().getBytes();
    }

}
