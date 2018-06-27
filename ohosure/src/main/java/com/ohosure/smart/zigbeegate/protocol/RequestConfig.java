package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.communication.HttpOutMessage;

public class RequestConfig extends HttpOutMessage {

	public RequestConfig(String session) {
		String value = "{ \"sessionID\" : \"" + session + "\", \"system\" : {  \"type\" : \"" + hostIdentity + "\" } }";

		sb.append("POST /requestConfig HTTP/1.1\r\n")
				.append("Accept: */*\r\n")
				.append("Connection: keep-alive\r\n")
				.append("Content-Length: " + value.length() + "\r\n")
				.append("\r\n").append(value);
	}
}
