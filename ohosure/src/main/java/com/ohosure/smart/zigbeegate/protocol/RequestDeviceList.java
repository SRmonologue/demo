package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.communication.HttpOutMessage;

/**
 * Created by lee on 2017/6/27.
 */

public class RequestDeviceList extends HttpOutMessage {
    public RequestDeviceList(String session, String gwId) {

        String value = "{  \"parameter\" : { \"gwId\" : \"" + gwId
                + "\"}, \"sessionID\" : \"" + session
                + "\", \"system\" : {  \"type\" : \"" + hostIdentity + "\" } }";

        sb.append("POST /requestDeviceList HTTP/1.1\r\n")
                .append("Accept: */*\r\n")
                .append("Connection: keep-alive\r\n")
                .append("Content-Length: " + value.length() + "\r\n")
                .append("\r\n").append(value);

    }
}
