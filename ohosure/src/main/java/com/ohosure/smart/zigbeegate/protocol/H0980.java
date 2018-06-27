package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitWriter;

public class H0980 extends HSend {

 
	private int code;

	public H0980(int functionCode)  {
		messageCode = 0x0980;
		this.code=functionCode;
		// message flag & message header
		bufferSize += 5;
		// function code
		bufferSize += 1;
	}

	public byte[] getBytes() {
		byte[] data = new byte[bufferSize];
		BitWriter bitWriter = BitWriter.newInstance(data);
		bitWriter.setBYTE(messageFlag);
		bitWriter.setWORD(messageCode);
		bitWriter.setWORD(bufferSize - sizeBefore);
		bitWriter.setBYTE(this.code);
		return data;
	}
}
