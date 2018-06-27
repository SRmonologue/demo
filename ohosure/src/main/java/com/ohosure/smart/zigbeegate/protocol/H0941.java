package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitWriter;

import java.io.UnsupportedEncodingException;

public class H0941 extends HSend {

	long roomAreaId;

	public H0941(long roomAreaId)  {
		messageCode = 0x0941;
		this.roomAreaId = roomAreaId;
		// message flag & message header
		bufferSize += 5;
		// id DWORD
		bufferSize += 4;
	}

	public byte[] getBytes() throws UnsupportedEncodingException {
		byte[] data = new byte[bufferSize];
		BitWriter bitWriter = BitWriter.newInstance(data);
		bitWriter.setBYTE(messageFlag);
		bitWriter.setWORD(messageCode);
		bitWriter.setWORD(bufferSize - sizeBefore);
		bitWriter.setDWORD(this.roomAreaId);
		return data;
	}
}
