package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.communication.HttpOutMessage;

public class RequestServer extends HttpOutMessage {

	public RequestServer(String username) {
		
		String value = "{ \"account\" : \""+username+"\", \"system\" : {  \"type\" : \"" + hostIdentity + "\" } }";
		
		sb.append("POST /requestServer HTTP/1.1\r\n")
				.append("Accept: */*\r\n")
				.append("Connection: close\r\n")
				.append("Content-Length: " + value.length() + "\r\n")
				.append("\r\n").append(value);
	}
}
