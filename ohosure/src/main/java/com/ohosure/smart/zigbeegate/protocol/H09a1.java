package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.Const;
import com.ohosure.smart.database.devkit.utils.BitWriter;

import java.io.UnsupportedEncodingException;



public class H09a1 extends HSend {

	private String account;
	private int accountLength;

	public H09a1(String account) {
		messageCode = 0x09a1;
		this.account = account;
		// message flag & message header
		bufferSize += 5;
		bufferSize += 2;
		try {
			accountLength = account.getBytes(Const.CHARSET).length;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bufferSize += accountLength;
	}

	public byte[] getBytes() throws UnsupportedEncodingException {
		byte[] data = new byte[bufferSize];
		BitWriter bitWriter = BitWriter.newInstance(data);
		bitWriter.setBYTE(messageFlag);
		bitWriter.setWORD(messageCode);
		bitWriter.setWORD(bufferSize - sizeBefore);
		bitWriter.setWORD(accountLength);
		bitWriter.setBYTEArray(this.account.getBytes(Const.CHARSET));
		return data;
	}
}
