package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitWriter;

public class H09b1 extends HSend {


    long channelId;
    long controlDeviceId;

    public H09b1(long channelId, long controlDeviceId) {
        messageCode = 0x09b1;
        this.channelId = channelId;
        this.controlDeviceId = controlDeviceId;

        // message flag & message header
        bufferSize += 5;
        // channelId DWORD
        bufferSize += 4;
        // controlDeviceId DWORD
        bufferSize += 4;

    }

    public byte[] getBytes() {
        byte[] data = new byte[bufferSize];

        BitWriter bitWriter = BitWriter.newInstance(data);
        bitWriter.setBYTE(messageFlag);
        bitWriter.setWORD(messageCode);
        bitWriter.setWORD(bufferSize - sizeBefore);
        bitWriter.setDWORD(this.channelId);
        bitWriter.setDWORD(this.controlDeviceId);
        return data;
    }
}
