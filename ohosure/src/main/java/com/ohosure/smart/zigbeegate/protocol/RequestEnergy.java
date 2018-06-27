package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.communication.HttpOutMessage;

import org.json.JSONObject;

public class RequestEnergy extends HttpOutMessage {

	public RequestEnergy(JSONObject jobject) {
		String value =  jobject.toString();
		System.out.println(value);
		
		sb.append("POST /requestEnergy HTTP/1.1\r\n").append("Accept: */*\r\n")
				.append("Connection: keep-alive\r\n")
				.append("Content-Length: " + value.length() + "\r\n")
				.append("\r\n").append(value.toString());
	}
}
