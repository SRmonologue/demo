package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.communication.HttpOutMessage;

public class RequestGateway extends HttpOutMessage {

	public RequestGateway(String username, String password) {
		
		String value = "{ \"account\" : \""+username+"\",\"password\":\""+password+"\", \"system\" : {  \"type\" : \"" + hostIdentity + "\" } }";
		
		sb.append("POST /requestGateway HTTP/1.1\r\n")
				.append("Accept: */*\r\n")
				.append("Connection: close\r\n")
				.append("Content-Length: " + value.length() + "\r\n")
				.append("\r\n").append(value);
	}
}
