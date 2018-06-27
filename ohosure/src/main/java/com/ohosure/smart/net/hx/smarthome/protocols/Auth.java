package com.ohosure.smart.net.hx.smarthome.protocols;

/**
 * Created by daxing on 2017/3/31.
 */

public class Auth {
    StringBuilder httpMessage = new StringBuilder();

    public Auth(String name, String password, String mac, String type) {
        String body = "{ \"account\" : \"" + name + "\", \"password\" : \"" +
                password + "\", \"system\" : {  \"type\" : \"and_ab\" ,\"serial\" : \"" +
                mac + "\"} }";
        httpMessage.append("POST /requestAuth HTTP/1.1\r\n")
                .append("Connection: keep-alive\r\n")
                .append("Content-Length: " + body.length() + "\r\n")
                .append("\r\n").append(body);
    }

    public byte[] getByte() {
        return httpMessage.toString().getBytes();
    }
}
