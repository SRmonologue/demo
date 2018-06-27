package com.ohosure.smart.zigbeegate.protocol;

import com.ohosure.smart.database.devkit.utils.BitWriter;

import java.io.UnsupportedEncodingException;



public class H0981 extends HSend {
    private int id;
    private int number;
    private int sceneID;
    private int address;
    private int type;
    private int controyDeviceId;

    public H0981(int id,int type, int sceneId,int number, int address,int deviceId) throws UnsupportedEncodingException {
        messageCode = 0x0981;
        this.id = id;
        this.number = number;
        this.sceneID = sceneId;
        this.address = address;
        this.type = type;
        this.controyDeviceId = deviceId;
        // message flag & message header
        bufferSize += 5;

        // length of id
        bufferSize += 4;

        // length of number
        bufferSize += 1;

        //length of address
        bufferSize += 2;

        // length of sceneId
        bufferSize += 4;

        //length of type
        bufferSize += 2;

        //length of controyDeviceId
        bufferSize += 4;

    }

    public byte[] getBytes() throws UnsupportedEncodingException {
        byte[] data = new byte[bufferSize];
        BitWriter bitWriter = BitWriter.newInstance(data);
        bitWriter.setBYTE(messageFlag);
        bitWriter.setWORD(messageCode);
        bitWriter.setWORD(bufferSize - sizeBefore);
        bitWriter.setDWORD(this.id);
        bitWriter.setBYTE(this.number);
        bitWriter.setWORD(this.address);
        bitWriter.setDWORD(this.sceneID);
        bitWriter.setWORD(this.type);
        bitWriter.setDWORD(this.controyDeviceId);

        return data;
    }
}
