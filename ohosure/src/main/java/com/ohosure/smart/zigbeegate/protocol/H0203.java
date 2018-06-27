package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitReader;

public class H0203 extends HReceive{

	BitReader bitReader;
	int resultCode;
	long controlDeviceId;

	public H0203(byte[] data) {
		bitReader = BitReader.newInstance(data);

	}

	public void parse() {
		resultCode = bitReader.getBYTE();
		controlDeviceId = bitReader.getDWORD();
	}

	public int getResultCode() {
		return resultCode;
	}

	public long getControlDeviceIdId() {
		return controlDeviceId;
	}
	
	

}
