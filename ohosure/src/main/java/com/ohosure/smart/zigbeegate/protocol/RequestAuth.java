package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.communication.HttpOutMessage;

public class RequestAuth extends HttpOutMessage {

	public RequestAuth(String username, String password, String gatemac) {
	
		String value = "{ \"account\" : \"" + username
				+ "\", \"password\" : \"" + password
				+ "\", \"system\" : {  \"type\" : \"" + hostIdentity + "\" ,\"serial\" : \""+gatemac+"\"} }";

		sb.append("POST /requestAuth HTTP/1.1\r\n")
				.append("Accept: */*\r\n")
				.append("Connection: keep-alive\r\n")
				.append("Content-Length: " + value.length() + "\r\n")
				.append("\r\n").append(value);
	}
}
