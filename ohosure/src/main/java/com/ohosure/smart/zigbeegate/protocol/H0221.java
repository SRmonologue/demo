package com.ohosure.smart.zigbeegate.protocol;

import com.ohosure.smart.database.devkit.utils.BitReader;

import java.util.ArrayList;



public class H0221  extends HReceive{

	BitReader bitReader;
	int resultCode;
	long sceneId;
	int channelListSize;
	ArrayList<HChannel> list = new ArrayList<HChannel>();

	public H0221(byte[] data) {
		bitReader = BitReader.newInstance(data); 
	}

	protected void parse() {

		resultCode = bitReader.getBYTE();
		sceneId=bitReader.getDWORD();
		channelListSize = bitReader.getWORD();
		for (int i = 0; i < channelListSize; i++) {
			HChannel channel = new HChannel(bitReader.getDWORD(),
					bitReader.getBYTE(), bitReader.getDWORD());
			list.add(channel);
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

	public long getSceneId() {
		return sceneId;
	}

	 
	

}
