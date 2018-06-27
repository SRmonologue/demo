package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.communication.HttpOutMessage;

/**
 * Created by lee on 2017/6/27.
 */

public class RequestUpdateDescription extends HttpOutMessage {
    public RequestUpdateDescription(String session, String version) {

        String value = "{  \"parameter\" : { \"version\" : \"" + version
                + "\"}, \"sessionID\" : \"" + session
                + "\", \"system\" : {  \"type\" : \"" + hostIdentity + "\" } }";

        sb.append("POST /requestUpdateDescription HTTP/1.1\r\n")
                .append("Accept: */*\r\n")
                .append("Connection: keep-alive\r\n")
                .append("Content-Length: " + value.length() + "\r\n")
                .append("\r\n").append(value);

    }
}
