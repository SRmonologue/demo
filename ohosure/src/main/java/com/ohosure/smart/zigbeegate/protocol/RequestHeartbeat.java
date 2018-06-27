package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.communication.HttpOutMessage;

public class RequestHeartbeat extends HttpOutMessage {

	public RequestHeartbeat(String session, String host) {
		String value = "{\"sessionID\":\"" + session
				+ "\",\"system\" :{\"type\":\"" + hostIdentity + "\"}}";

		sb.append("POST /requestHeartbeat HTTP/1.1\r\n")
				.append("Connection: keep-alive\r\n")
				.append("Content-Length: " + value.length() + "\r\n")
				.append("\r\n").append(value);
	}
}
