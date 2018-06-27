package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitReader;

public class H0261 extends HReceive {

	BitReader bitReader;
	int resultCode;
	 

	public H0261(byte[] data) {
		bitReader = BitReader.newInstance(data);

	}

	protected void parse() {

		resultCode = bitReader.getBYTE();
		 
	}

	public int getResultCode() {
		return resultCode;
	}

	 

}
