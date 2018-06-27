package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.communication.HttpOutMessage;

public class RequestCancel extends HttpOutMessage {

	public RequestCancel(String session) {
	
		String value2 = "{ \"sessionID\" : \"" + session
				+ "\", \"system\" : { \"type\" : \"" + hostIdentity + "\" } }";
		sb.append("POST /requestCancel HTTP/1.1\r\n")
				.append("Accept: */*\r\n")
				.append("Connection: close\r\n")
				.append("Content-Length: " + value2.length() + "\r\n")
				.append("\r\n").append(value2);
	}
}
