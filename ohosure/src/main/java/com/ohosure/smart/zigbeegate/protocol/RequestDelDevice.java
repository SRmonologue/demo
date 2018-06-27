package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.communication.HttpOutMessage;

/**
 * Created by lee on 2017/5/16.
 */

public class RequestDelDevice extends HttpOutMessage {
    public RequestDelDevice(String session, String gwId, String deviceID) {

        String value = "{  \"parameter\" : { \"deviceId\" : \"" + deviceID
                + "\",\"gwId\": \"" + gwId
                + "\"}, \"sessionID\" : \"" + session
                + "\", \"system\" : {  \"type\" : \"" + hostIdentity + "\" } }";

        sb.append("POST /requestDelDevice HTTP/1.1\r\n")
                .append("Accept: */*\r\n")
                .append("Connection: keep-alive\r\n")
                .append("Content-Length: " + value.length() + "\r\n")
                .append("\r\n").append(value);

    }
}
