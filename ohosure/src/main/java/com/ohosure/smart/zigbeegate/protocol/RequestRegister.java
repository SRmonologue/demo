package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.communication.HttpOutMessage;

public class RequestRegister extends HttpOutMessage {

	public RequestRegister(String username, String password) {
	 
		String value2 = "{ \"account\" : \"" + username
				+ "\", \"password\" : \"" + password
				+ "\", \"system\" : {  \"type\" : \"" + hostIdentity + "\" } }";

		sb.append("POST /requestRegister HTTP/1.1\r\n")
				.append("Accept: */*\r\n") 
				.append("Connection: close\r\n")
				.append("Content-Length: " + value2.length() + "\r\n")
				.append("\r\n").append(value2);
	}
}
