package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.communication.HttpOutMessage;

/**
 * Created by lee on 2017/5/16.
 */

public class RequestAddUser extends HttpOutMessage {
    public RequestAddUser(String session, String gwId, String userName, String passWord) {

        String value = "{  \"parameter\" : { \"gwId\" : \"" + gwId
                + "\",\"passWord\": \"" + passWord
                + "\",\"userName\": \"" + userName
                + "\"}, \"sessionID\" : \"" + session
                + "\", \"system\" : {  \"type\" : \"" + hostIdentity + "\" } }";

        sb.append("POST /requestAddUser HTTP/1.1\r\n")
                .append("Accept: */*\r\n")
                .append("Connection: keep-alive\r\n")
                .append("Content-Length: " + value.length() + "\r\n")
                .append("\r\n").append(value);

    }
}
