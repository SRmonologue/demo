package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.Const;
import com.ohosure.smart.database.devkit.utils.BitWriter;

import java.io.UnsupportedEncodingException;

public class H0911 extends HSend {

	int nameLength;
	String name;
	long productId;
	long roomAreaId;

	public H0911(long productId, long roomAreaId, String name)
			throws UnsupportedEncodingException {
		messageCode = 0x0911;
		this.name = name;
		this.roomAreaId = roomAreaId;
		this.productId = productId;
		// message flag & message header
		bufferSize += 5;
		// productId DWORD
		bufferSize += 4;
		// roomAreaId DWORD
		bufferSize += 4;
		// nameLength WORD
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
		bitWriter.setDWORD(this.productId);
		bitWriter.setDWORD(this.roomAreaId);
		bitWriter.setWORD(this.nameLength);
		bitWriter.setBYTEArray(this.name.getBytes(Const.CHARSET));
		return data;
	}
}
