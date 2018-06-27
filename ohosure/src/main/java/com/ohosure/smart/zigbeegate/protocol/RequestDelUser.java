package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.communication.HttpOutMessage;

/**
 * Created by lee on 2017/5/16.
 */

public class RequestDelUser extends HttpOutMessage {
    public RequestDelUser(String session, String gwId, String userName) {
        String value = "{  \"parameter\" : { \"gwId\" : \"" + gwId
                + "\",\"userName\": \"" + userName
                + "\"}, \"sessionID\" : \"" + session
                + "\", \"system\" : {  \"type\" : \"" + hostIdentity + "\" } }";

        sb.append("POST /requestDelUser HTTP/1.1\r\n")
                .append("Accept: */*\r\n")
                .append("Connection: keep-alive\r\n")
                .append("Content-Length: " + value.length() + "\r\n")
                .append("\r\n").append(value);

    }
}
