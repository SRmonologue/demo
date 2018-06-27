package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitReader;

public class H0204 extends HReceive{

	BitReader bitReader;
	int resultCode;
	int taskId;

	public H0204(byte[] data) {
		bitReader = BitReader.newInstance(data);

	}

	public void parse() {
		resultCode = bitReader.getBYTE();
		taskId = (int)bitReader.getDWORD();
	}

	public int getResultCode() {
		return resultCode;
	}

	public int getTaskId() {
		return taskId;
	}

	 
	

}
