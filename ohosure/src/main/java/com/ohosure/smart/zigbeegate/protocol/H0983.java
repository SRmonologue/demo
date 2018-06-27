package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.Const;
import com.ohosure.smart.database.devkit.utils.BitWriter;

import java.io.UnsupportedEncodingException;



public class H0983 extends HSend {


    private final int timeLength;
    private String mTime;

    public H0983(String time) throws UnsupportedEncodingException {
        messageCode = 0x0983;
        this.mTime = time;
        // message flag & message header
        bufferSize += 5;

        timeLength = mTime.getBytes(Const.CHARSET).length;
        bufferSize += timeLength;
    }

    public byte[] getBytes() throws UnsupportedEncodingException {
        byte[] data = new byte[bufferSize];
        BitWriter bitWriter = BitWriter.newInstance(data);
        bitWriter.setBYTE(messageFlag);
        bitWriter.setWORD(messageCode);
        bitWriter.setWORD(bufferSize - sizeBefore);
        bitWriter.setBYTEArray(this.mTime.getBytes(Const.CHARSET));
        return data;
    }
}
