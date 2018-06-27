package com.ohosure.smart.zigbeegate.protocol;

import java.io.UnsupportedEncodingException;

public abstract class HSend {

	protected  int bufferSize;
	protected final int messageFlag=0x7f;
	protected int messageCode;
	protected final int sizeBefore=5;
	public  int getBufferSize()
	{
		return bufferSize;
	}
	public abstract byte[] getBytes() throws UnsupportedEncodingException;
}
