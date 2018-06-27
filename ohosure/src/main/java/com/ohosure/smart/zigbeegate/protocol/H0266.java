package com.ohosure.smart.zigbeegate.protocol;

import com.ohosure.smart.database.devkit.utils.BitReader;

import java.util.ArrayList;



public class H0266 extends HReceive {

	BitReader bitReader;
	int resultCode;
	int productListSize;
	ArrayList<HProduct> list = new ArrayList<HProduct>();

	public H0266(byte[] data) {
		bitReader = BitReader.newInstance(data); 
	}

	protected void parse() {

		resultCode = bitReader.getBYTE();
		productListSize = bitReader.getWORD();
		for (int i = 0; i < productListSize; i++) {
			HProduct hProduct = new HProduct(bitReader.getDWORD(),
					bitReader.getBYTE(), bitReader.getBYTE());
			list.add(hProduct);
		}

	}

	public int getResultCode() {
		return resultCode;
	}

	public int getProductListSize() {
		return productListSize;
	}

	public ArrayList<HProduct> getList() {
		return list;
	}

	 
}
