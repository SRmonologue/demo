package com.ohosure.smart.zigbeegate.protocol;

import com.ohosure.smart.database.devkit.utils.BitWriter;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;



public class H0921 extends HSend {

	long sceneId;
	int channelNumber;

	ArrayList<HChannel> mChannelList;

	public H0921(long sceneId, ArrayList<HChannel> list)
			{
		messageCode = 0x0921;
		this.sceneId = sceneId;
		this.channelNumber = list.size();
		this.mChannelList = list;
		// message flag & message header
		bufferSize += 5;
		// sceneId DWORD
		bufferSize += 4;
		// channelNumber WORD
		bufferSize += 2;
		// Channel[N]
		bufferSize += 9 * channelNumber;

	}

	public byte[] getBytes() throws UnsupportedEncodingException {
		byte[] data = new byte[bufferSize];

		BitWriter bitWriter = BitWriter.newInstance(data);

		bitWriter.setBYTE(messageFlag);
		bitWriter.setWORD(messageCode);
		bitWriter.setWORD(bufferSize - sizeBefore);
		bitWriter.setDWORD(this.sceneId);
		bitWriter.setWORD(this.channelNumber);
		for (HChannel hChannel : this.mChannelList) {
			bitWriter.setDWORD(hChannel.getChannelId());
			bitWriter.setBYTE(hChannel.getChannelFeatureType());
			bitWriter.setDWORD(hChannel.getChannelFeatureValue());
		}

		return data;
	}
}
