package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitWriter;
import com.ohosure.smart.zigbeegate.protocol.model.HDevice;

import java.io.UnsupportedEncodingException;

//修改设备类型
public class H0909 extends HSend {

	int deviceId;
	int deviceType;
	int deviceOriginal;

	public H0909(HDevice device){
		messageCode = 0x0909;
		this.deviceId=device.getId();
		this.deviceType=device.getType();
		this.deviceOriginal = device.getOriginal();
		// message flag & message header
		bufferSize += 5;

		bufferSize += 4;
		bufferSize += 2;
		bufferSize += 1;
	

	}


	public H0909(int deviceId,int deviceType,int deviceOriginal){
		messageCode = 0x0909;
		this.deviceId=deviceId;
		this.deviceType=deviceType;
		this.deviceOriginal = deviceOriginal;
		// message flag & message header
		bufferSize += 5;

		bufferSize += 4;
		bufferSize += 2;
		bufferSize += 1;


	}

	public byte[] getBytes() throws UnsupportedEncodingException {
		byte[] data = new byte[bufferSize];
		BitWriter bitWriter = BitWriter.newInstance(data);
		bitWriter.setBYTE(messageFlag);
		bitWriter.setWORD(messageCode);
		bitWriter.setWORD(bufferSize - sizeBefore);
		bitWriter.setDWORD(this.deviceId);
		bitWriter.setWORD(this.deviceType);
		bitWriter.setBYTE(this.deviceOriginal);
		return data;
	}
}
