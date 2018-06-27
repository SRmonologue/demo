package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.Const;
import com.ohosure.smart.database.devkit.utils.BitWriter;

import java.io.UnsupportedEncodingException;

public class H0901 extends HSend {

	int nameLength;
	String name;
	int descriptionLength;
	String description;
	long roomAreaId;

	public H0901(long roomAreaId, String name, String description)
			throws UnsupportedEncodingException {

		messageCode = 0x0901;
		this.roomAreaId = roomAreaId;
		this.name = name;
		this.description = description;
		// message flag & message header
		bufferSize += 5;
		// length id
		bufferSize += 4;
		// length WORD
		bufferSize += 2;
		// name
		nameLength = name.getBytes(Const.CHARSET).length;
		bufferSize += nameLength;
		// length WORD
		bufferSize += 2;
		// description
		descriptionLength = description.getBytes(Const.CHARSET).length;
		bufferSize += descriptionLength;

	}

	public byte[] getBytes() throws UnsupportedEncodingException {

		byte[] data = new byte[bufferSize];

		BitWriter bitWriter = BitWriter.newInstance(data);

		bitWriter.setBYTE(messageFlag);
		bitWriter.setWORD(messageCode);
		bitWriter.setWORD(bufferSize - sizeBefore);
		bitWriter.setDWORD(this.roomAreaId);
		bitWriter.setWORD(this.nameLength);
		bitWriter.setBYTEArray(this.name.getBytes(Const.CHARSET));
		bitWriter.setWORD(this.descriptionLength);
		bitWriter.setBYTEArray(this.description.getBytes(Const.CHARSET));

		return data;
	}

}
