package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitWriter;

public class H0946 extends HSend {

	int linkageId;

	public H0946(int linkageId) {
		messageCode = 0x0946;
		this.linkageId = linkageId;
		// message flag & message header
		bufferSize += 5;
		// id DWORD
		bufferSize += 4;
	}

	public byte[] getBytes() {
		byte[] data = new byte[bufferSize];
		BitWriter bitWriter = BitWriter.newInstance(data);
		bitWriter.setBYTE(messageFlag);
		bitWriter.setWORD(messageCode);
		bitWriter.setWORD(bufferSize - sizeBefore);
		bitWriter.setDWORD(this.linkageId);
		return data;
	}
}
