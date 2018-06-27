package com.ohosure.smart.zigbeegate.protocol;

import com.ohosure.smart.database.devkit.utils.BitWriter;

import java.io.UnsupportedEncodingException;



public class H0982 extends HSend {
    private int id;

    public H0982(int id) throws UnsupportedEncodingException {
        messageCode = 0x0982;
        this.id = id;
        // message flag & message header
        bufferSize += 5;

        // length of id
        bufferSize += 4;


    }

    public byte[] getBytes() throws UnsupportedEncodingException {
        byte[] data = new byte[bufferSize];
        BitWriter bitWriter = BitWriter.newInstance(data);
        bitWriter.setBYTE(messageFlag);
        bitWriter.setWORD(messageCode);
        bitWriter.setWORD(bufferSize - sizeBefore);
        bitWriter.setDWORD(this.id);
        return data;
    }
}
