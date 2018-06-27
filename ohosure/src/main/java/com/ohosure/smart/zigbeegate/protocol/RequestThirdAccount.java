package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.communication.HttpOutMessage;

public class RequestThirdAccount extends HttpOutMessage {

	public RequestThirdAccount(String unionid, String type) {
		
		String value = "{ \"unionid\" : \""+unionid+"\",\"type\":\""+type+"\", \"system\" : {  \"type\" : \"" + hostIdentity + "\" } }";
		
		sb.append("POST /requestThirdAccount HTTP/1.1\r\n")
				.append("Accept: */*\r\n")
				.append("Connection: close\r\n")
				.append("Content-Length: " + value.length() + "\r\n")
				.append("\r\n").append(value);
	}
}
