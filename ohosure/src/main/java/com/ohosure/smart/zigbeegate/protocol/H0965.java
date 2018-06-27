package com.ohosure.smart.zigbeegate.protocol;

import com.ohosure.smart.database.devkit.utils.BitWriter;

import java.util.ArrayList;



public class H0965 extends HSend {


	ArrayList<HSmartDevice> listSmartDevice;

	public H0965(ArrayList<HSmartDevice> list) {
		messageCode = 0x0965;
		this.listSmartDevice = list;
		// message flag & message header
		bufferSize += 5;
		//list size
		bufferSize += 2;

		bufferSize += 9*listSmartDevice.size();

	}

	public byte[] getBytes()  {
		byte[] data = new byte[bufferSize];

		BitWriter bitWriter = BitWriter.newInstance(data);
		bitWriter.setBYTE(messageFlag);
		bitWriter.setWORD(messageCode);
		bitWriter.setWORD(bufferSize - sizeBefore);
		bitWriter.setWORD(listSmartDevice.size());
		for(HSmartDevice device:listSmartDevice)
		{
			bitWriter.setDWORD(device.getDeviceId());
			bitWriter.setWORD(device.getDeviceType());
			bitWriter.setBYTE(device.getDeviceOriginal());
			bitWriter.setWORD(device.getFeatureType());
		}
		
		return data;
	}
}
