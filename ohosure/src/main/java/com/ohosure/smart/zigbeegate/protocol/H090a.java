package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.Const;
import com.ohosure.smart.database.devkit.utils.BitWriter;

import java.io.UnsupportedEncodingException;

public class H090a extends HSend {

	int nameLength;
	String name;
	int linkageId;
int enable;
	public H090a(int linkageId,int enable, String name)
			throws UnsupportedEncodingException {
		messageCode = 0x090a;
		this.name = name;
		this.linkageId = linkageId;
		this.enable=enable;
		// message flag & message header
		bufferSize += 5;
		bufferSize += 4;
		bufferSize+=1;
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
		bitWriter.setDWORD(this.linkageId);
		bitWriter.setBYTE(this.enable);
		bitWriter.setWORD(this.nameLength);
		bitWriter.setBYTEArray(this.name.getBytes(Const.CHARSET));
		return data;
	}
}
