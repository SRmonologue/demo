package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitWriter;

public class H0905 extends HSend {

	long taskId;
	 
	int enableStatus;

	public H0905(long taskId,  int enableStatus) {
		messageCode = 0x0905;
		this.taskId = taskId;
		 
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
		bitWriter.setDWORD(this.taskId); 
		bitWriter.setBYTE(this.enableStatus);
		return data;
	}
}
