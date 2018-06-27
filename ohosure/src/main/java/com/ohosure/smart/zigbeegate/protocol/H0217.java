package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitReader;

public class H0217 extends HReceive{

	BitReader bitReader;
	int resultCode;
	int linkageId;

	public H0217(byte[] data) {
		bitReader = BitReader.newInstance(data);

	}

	public void parse() {
		resultCode = bitReader.getBYTE();
		linkageId =(int) bitReader.getDWORD();
	}

	public int getResultCode() {
		return resultCode;
	}

	public int getLinkageId() {
		return linkageId;
	}

	 
	

}
