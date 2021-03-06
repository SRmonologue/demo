package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.Const;
import com.ohosure.smart.database.devkit.utils.BitWriter;

import java.io.UnsupportedEncodingException;

public class H0903 extends HSend {

	int nameLength;
	String name;
	long roomAreaId;
	int type;
	long controlDeviceId;

	public H0903(long controlDeviceId, String name, long roomAreaId, int type)
			throws UnsupportedEncodingException {
		messageCode = 0x0903;
		this.controlDeviceId=controlDeviceId;
		this.name = name;
		this.roomAreaId = roomAreaId;
		this.type = type; 
		// message flag & message header
		bufferSize += 5;

		// controlDeviceId
		bufferSize += 4;
		// roomAreaId
		bufferSize += 4;
		// type
		bufferSize += 2;
		// length WORD
		bufferSize += 2;
		// name
		nameLength = name.getBytes(Const.CHARSET).length;
		bufferSize += nameLength;
	

	}

	public byte[] getBytes() throws UnsupportedEncodingException {
		byte[] data = new byte[bufferSize];
		BitWriter bitWriter = BitWriter.newInstance(data);
		bitWriter.setBYTE(messageFlag);
		bitWriter.setWORD(messageCode);
		bitWriter.setWORD(bufferSize - sizeBefore);
		bitWriter.setDWORD(this.controlDeviceId);
		bitWriter.setDWORD(this.roomAreaId);
		bitWriter.setWORD(this.type);
		bitWriter.setWORD(this.nameLength);
		bitWriter.setBYTEArray(this.name.getBytes(Const.CHARSET));
	
		return data;
	}
}
