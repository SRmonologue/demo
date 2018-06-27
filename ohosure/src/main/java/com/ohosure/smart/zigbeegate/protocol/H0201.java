package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitReader;

public class H0201 extends HReceive {

	BitReader bitReader;
	int resultCode;
	long roomAreaId;

	public H0201(byte[] data) {
		bitReader = BitReader.newInstance(data);

	}

	protected void parse() {

		resultCode = bitReader.getBYTE();
		roomAreaId = bitReader.getDWORD();

	}

	public int getResultCode() {
		return resultCode;
	}

	public long getRoomAreaId() {
		return roomAreaId;
	}

}
