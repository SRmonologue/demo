package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitWriter;

import java.io.UnsupportedEncodingException;

public class H0943 extends HSend {

	long controlDeviceId;
	int controlDeviceEnable;//2为删除

	public H0943(long controlDeviceId,int enable) {
		messageCode = 0x0943;
		this.controlDeviceId = controlDeviceId;
		this.controlDeviceEnable=enable;
		// message flag & message header
		bufferSize += 5;
		// id DWORD
		bufferSize += 4;
		// enable
		bufferSize += 1;
	}

	public byte[] getBytes() throws UnsupportedEncodingException {
		byte[] data = new byte[bufferSize];
		BitWriter bitWriter = BitWriter.newInstance(data);
		bitWriter.setBYTE(messageFlag);
		bitWriter.setWORD(messageCode);
		bitWriter.setWORD(bufferSize - sizeBefore);
		bitWriter.setDWORD(this.controlDeviceId);
		bitWriter.setBYTE(this.controlDeviceEnable);
		return data;
	}
}
