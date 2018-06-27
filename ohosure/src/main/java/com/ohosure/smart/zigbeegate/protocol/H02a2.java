package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.Const;
import com.ohosure.smart.database.devkit.utils.BitReader;

import java.io.UnsupportedEncodingException;



public class H02a2 extends HReceive {

	BitReader bitReader;
	int resultCode;
	int lengthAccount;
	String account;

	public H02a2(byte[] data) {
		bitReader = BitReader.newInstance(data);

	}

	public void parse() {
		resultCode = bitReader.getBYTE();
		lengthAccount = bitReader.getWORD();
		try {
			account = new String(bitReader.getByteArray(lengthAccount),
					Const.CHARSET);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getResultCode() {
		return resultCode;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

}
