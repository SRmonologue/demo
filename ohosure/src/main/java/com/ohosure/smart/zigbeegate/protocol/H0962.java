package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitWriter;

public class H0962 extends HSend {


	long sceneId;

	public H0962(long sceneId)
			 {
		messageCode = 0x0962;
		this.sceneId = sceneId;
		// message flag & message header
		bufferSize += 5;
		// sceneId DWORD
		bufferSize += 4;

	}

	public byte[] getBytes(){
		byte[] data = new byte[bufferSize];

		BitWriter bitWriter = BitWriter.newInstance(data);
		bitWriter.setBYTE(messageFlag);
		bitWriter.setWORD(messageCode);
		bitWriter.setWORD(bufferSize - sizeBefore);
		bitWriter.setDWORD(this.sceneId);
		return data;
	}
}
