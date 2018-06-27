package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitWriter;

public class H09a2 extends HSend {

	public H09a2() {
		messageCode = 0x09a2;
		// message flag & message header
		bufferSize += 5;
	}

	public byte[] getBytes() {
		byte[] data = new byte[bufferSize];
		BitWriter bitWriter = BitWriter.newInstance(data);
		bitWriter.setBYTE(messageFlag);
		bitWriter.setWORD(messageCode);
		bitWriter.setWORD(bufferSize - sizeBefore);
		return data;
	}
}
