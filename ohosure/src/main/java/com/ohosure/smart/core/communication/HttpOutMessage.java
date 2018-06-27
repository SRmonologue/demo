package com.ohosure.smart.core.communication;


import com.ohosure.smart.core.Const;

public class HttpOutMessage {

	protected StringBuffer sb=new StringBuffer();
	protected final String hostIdentity= Const.HOST_IDENTITY;
	public String getString()
	{
		return sb.toString();
	}
}
