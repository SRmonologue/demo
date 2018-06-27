package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitWriter;

public class H0916 extends HSend {


	int keyId;

	public H0916(int keyId)
			{
		messageCode = 0x0916;
		this.keyId = keyId;
		// message flag & message header
		bufferSize += 5;

		bufferSize += 4;

	}

	public byte[] getBytes() {
		byte[] data = new byte[bufferSize];

		BitWriter bitWriter = BitWriter.newInstance(data);
		bitWriter.setBYTE(messageFlag);
		bitWriter.setWORD(messageCode);
		bitWriter.setWORD(bufferSize - sizeBefore);
		bitWriter.setDWORD(this.keyId);
		return data;
	}
}
