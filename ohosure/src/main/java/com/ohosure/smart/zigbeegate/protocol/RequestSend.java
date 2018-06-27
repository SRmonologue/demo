package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.communication.HttpOutMessage;

public class RequestSend extends HttpOutMessage {

	public RequestSend(String session, String message, int messageLength,
                       boolean encode, String host) {
	
		String value = "{  \"msg\" : { \"encode\" : \"" + (encode ? "1" : "0")
				+ "\", \"len\" : \"" + messageLength
				+ "\", \"type\" : \"10\", \"value\" : \"" + message+ "\" }, \"sessionID\" : \"" + session
				+ "\", \"system\" : {  \"type\" : \"" + hostIdentity + "\" } }";

		sb.append("POST /requestSend HTTP/1.1\r\n")
				.append("Accept: */*\r\n")
				.append("Connection: keep-alive\r\n")
				.append("Content-Length: " + value.length() + "\r\n")
				.append("\r\n").append(value);

	}
}
