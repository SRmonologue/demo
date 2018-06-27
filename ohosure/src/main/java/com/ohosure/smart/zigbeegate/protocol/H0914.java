package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitWriter;

public class H0914 extends HSend {

	
	long keyId;
	long senceId;

	public H0914(long keyId, long senceId) {
		messageCode=0x0914;
	
		this.keyId = keyId;
		this.senceId = senceId;
		// message flag & message header
		bufferSize += 5;
	
		// keyId DWORD
		bufferSize += 4;
		// senceId DWORD
		bufferSize += 4;

	}

	public byte[] getBytes(){
		byte[] data = new byte[bufferSize];

		BitWriter bitWriter = BitWriter.newInstance(data);

		bitWriter.setBYTE(messageFlag);
		bitWriter.setWORD(messageCode);
		bitWriter.setWORD(bufferSize - sizeBefore);
		
		bitWriter.setDWORD(this.keyId);
		bitWriter.setDWORD(this.senceId);
		return data;
	}
}
