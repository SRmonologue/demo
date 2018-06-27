package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitReader;

public class H0280  extends HReceive{

	BitReader bitReader;
	int resultCode;
	int functionCode;

	public H0280(byte[] data) {
		bitReader = BitReader.newInstance(data);

	}

	public void parse() {
		resultCode = bitReader.getBYTE();
		functionCode=bitReader.getBYTE();
	}

	public int getResultCode() {
		return resultCode;
	}

	public int getFunctionCode() {
		return functionCode;
	}

	
	

}
