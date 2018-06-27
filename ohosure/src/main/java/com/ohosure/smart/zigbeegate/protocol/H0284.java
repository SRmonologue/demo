package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.Const;
import com.ohosure.smart.database.devkit.utils.BitReader;

import java.io.UnsupportedEncodingException;

public class H0284 extends HReceive{

	BitReader bitReader;
	int resultCode;
	int timeLength;
	String time;

	public H0284(byte[] data) {
		bitReader = BitReader.newInstance(data);

	}

	public void parse() {
		resultCode = bitReader.getBYTE();
		timeLength = bitReader.getWORD();
		try {
			time = new String(bitReader.getByteArray(timeLength),
                    Const.CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public int getResultCode() {
		return resultCode;
	}

	public String getTime() {
		return time;
	}

	
	

}
