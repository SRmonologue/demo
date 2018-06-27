package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitWriter;

public class H0917 extends HSend {

	int linkageId;

	int enableStatus;

	public H0917(int linkageId, int enableStatus) {
		messageCode = 0x0917;
		this.linkageId = linkageId;
		 
		this.enableStatus = enableStatus;
		// message flag & message header
		bufferSize += 5;

		// taskId
		bufferSize += 4;
	 
		// enableStatus
		bufferSize += 1;
	}

	public byte[] getBytes()  {
		byte[] data = new byte[bufferSize];
		BitWriter bitWriter = BitWriter.newInstance(data);
		bitWriter.setBYTE(messageFlag);
		bitWriter.setWORD(messageCode);
		bitWriter.setWORD(bufferSize - sizeBefore);
		bitWriter.setDWORD(this.linkageId);
		bitWriter.setBYTE(this.enableStatus);
		return data;
	}
}
