package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.communication.HttpOutMessage;

public class RequestBind extends HttpOutMessage {

	public RequestBind(String username, String password, String gateName,
                       String bssid, String serial) {

		String value = "{ \"account\" : \"" + username
				+ "\", \"password\" : \"" + password + "\", \"name\" : \""
				+ gateName + "\" , \"bssid\" : \"" + bssid
				+ "\" , \"system\" : {  \"type\" : \"" + hostIdentity
				+ "\" ,\"serial\" : \"" + serial + "\"} }";

		sb.append("POST /requestBind HTTP/1.1\r\n").append("Accept: */*\r\n")
				.append("Connection: close\r\n")
				.append("Content-Length: " + value.length() + "\r\n")
				.append("\r\n").append(value);
	}
}
