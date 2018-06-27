package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitReader;

public class H0207 extends HReceive{

	BitReader bitReader;
	int resultCode;
	int productId;

	public H0207(byte[] data) {
		bitReader = BitReader.newInstance(data);

	}

	public void parse() {
		resultCode = bitReader.getBYTE();
		productId = (int)bitReader.getDWORD();
	}

	public int getResultCode() {
		return resultCode;
	}

	public int getProductId() {
		return productId;
	}
}
