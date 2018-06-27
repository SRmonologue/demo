package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitReader;

public class H02b1 extends HReceive{

	BitReader bitReader;
	int resultCode;
	long remoteId;

	public H02b1(byte[] data) {
		bitReader = BitReader.newInstance(data);

	}

	public void parse() {
		resultCode = bitReader.getBYTE();
		remoteId=bitReader.getDWORD();
	}

	public int getResultCode() {
		return resultCode;
	}

	public long getRemoteId() {
		return remoteId;
	}
}
