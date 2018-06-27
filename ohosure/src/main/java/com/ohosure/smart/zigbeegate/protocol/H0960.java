package com.ohosure.smart.zigbeegate.protocol;

import com.ohosure.smart.database.devkit.utils.BitWriter;

import java.util.ArrayList;



public class H0960 extends HSend {


	ArrayList<Long> listProductId;

	public H0960(ArrayList<Long> listProductId) {
		messageCode = 0x0960;
		this.listProductId = listProductId;
		// message flag & message header
		bufferSize += 5;
		bufferSize += 2;
		// productId DWORD
		bufferSize += 4*listProductId.size();

	}

	public byte[] getBytes()  {
		byte[] data = new byte[bufferSize];

		BitWriter bitWriter = BitWriter.newInstance(data);
		bitWriter.setBYTE(messageFlag);
		bitWriter.setWORD(messageCode);
		bitWriter.setWORD(bufferSize - sizeBefore);
		bitWriter.setWORD(listProductId.size());
		for(Long l:listProductId)
		{
			bitWriter.setDWORD(l);
		}
		
		return data;
	}
}
