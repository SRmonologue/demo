package com.ohosure.smart.zigbeegate.protocol;

import com.ohosure.smart.database.devkit.utils.BitReader;

import java.util.ArrayList;



public class H0264 extends HReceive {

	BitReader bitReader;
	int resultCode;
	int channelListSize;
	ArrayList<HChannel> list = new ArrayList<HChannel>();

	public H0264(byte[] data) {
		bitReader = BitReader.newInstance(data); 
	}

	protected void parse() {

		resultCode = bitReader.getBYTE();
		channelListSize = bitReader.getWORD();
		for (int i = 0; i < channelListSize; i++) {
			HChannel hChannel = new HChannel(bitReader.getDWORD(),
					bitReader.getBYTE(), bitReader.getDWORD());
			list.add(hChannel);
		}

	}

	public int getResultCode() {
		return resultCode;
	}

	public int getChannelListSize() {
		return channelListSize;
	}

	public ArrayList<HChannel> getList() {
		return list;
	}

}
