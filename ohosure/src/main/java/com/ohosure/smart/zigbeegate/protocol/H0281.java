package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitReader;

public class H0281 extends HReceive{

	BitReader bitReader;
	int resultCode; 

	public H0281(byte[] data) {
		bitReader = BitReader.newInstance(data);

	}

	public void parse() {
		resultCode = bitReader.getBYTE(); 
	}

	public int getResultCode() {
		return resultCode;
	} 

	
	

}
