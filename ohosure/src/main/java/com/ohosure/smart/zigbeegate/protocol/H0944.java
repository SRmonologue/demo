package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitWriter;

public class H0944 extends HSend {

	int taskId;

	public H0944(int taskId) {
		messageCode = 0x0944;
		this.taskId = taskId;
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
		bitWriter.setDWORD(this.taskId);
		return data;
	}
}
