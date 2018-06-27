package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitWriter;

public class H0945 extends HSend {

	long productId;

	public H0945(long productId) {
		messageCode = 0x0945;
		this.productId = productId;
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
		bitWriter.setDWORD(this.productId);
		return data;
	}
}
