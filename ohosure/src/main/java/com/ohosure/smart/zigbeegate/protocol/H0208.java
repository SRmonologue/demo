package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitReader;

public class H0208 extends HReceive{

	BitReader bitReader;
	int resultCode;
	int controlDeviceId;

	public H0208(byte[] data) {
		bitReader = BitReader.newInstance(data);

	}

	public void parse() {
		resultCode = bitReader.getBYTE();
		controlDeviceId = (int)bitReader.getDWORD();
	}

	public int getResultCode() {
		return resultCode;
	}

	public int getControlDeviceId() {
		return controlDeviceId;
	}
}
