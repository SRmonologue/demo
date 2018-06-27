package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.communication.HttpOutMessage;

public class RequestUpdate extends HttpOutMessage {

	public RequestUpdate(String session, String lastUpdateTime) {
		String value = "{\"queryInfo\" : { \"lastUpdateTime\" :\""
				+ lastUpdateTime + "\"}, \"sessionID\" : \"" + session
				+ "\", \"system\" : {  \"type\" : \"" + hostIdentity + "\" } }";

		sb.append("POST /requestUpdate HTTP/1.1\r\n")
				.append("Accept: */*\r\n")
				.append("Connection: keep-alive\r\n")
				.append("Content-Length: " + value.length() + "\r\n")
				.append("\r\n").append(value);
	}
}
