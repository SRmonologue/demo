package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitReader;

public class H0202 extends HReceive {

	BitReader bitReader;
	int resultCode;
	long senceId;

	public H0202(byte[] data) {
		bitReader = BitReader.newInstance(data);

	}

	protected void parse() {
		 
			resultCode = bitReader.getBYTE();
			senceId = bitReader.getDWORD(); 
	}

	public int getResultCode() {
		return resultCode;
	}

	public long getSenceId() {
		return senceId;
	}

}
