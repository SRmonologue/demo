package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.Const;
import com.ohosure.smart.database.devkit.utils.BitWriter;
import com.ohosure.smart.zigbeegate.protocol.model.HDevice;

import java.io.UnsupportedEncodingException;

public class H090b extends HSend {

    long deviceId;
    int deviceType;
    int originalType;
    int userId;
    int Type;
    int lengthOfName;
    String name;

    public H090b(HDevice mHDevice,
                 int userId,
                 int Type,
                 String name) throws UnsupportedEncodingException {

        messageCode = 0x090b;
        this.deviceId = mHDevice.getId();
        this.deviceType = mHDevice.getType();
        this.originalType = mHDevice.getOriginal();
        this.userId = userId;
        this.Type = Type;
        lengthOfName = name.getBytes(Const.CHARSET).length;
        this.name = name;

        // message flag & message header
        bufferSize += 5;
        // length id
        bufferSize += 7;

        // length userId
        bufferSize += 2;

        // length Type
        bufferSize += 1;

        // length lengthOfName
        bufferSize += 2;

        // length name
        bufferSize += name.getBytes(Const.CHARSET).length;
    }




    public byte[] getBytes() throws UnsupportedEncodingException {

        byte[] data = new byte[bufferSize];

        BitWriter bitWriter = BitWriter.newInstance(data);

        bitWriter.setBYTE(messageFlag);
        bitWriter.setWORD(messageCode);
        bitWriter.setWORD(bufferSize - sizeBefore);
        bitWriter.setDWORD(this.deviceId);
        bitWriter.setWORD(this.deviceType);
        bitWriter.setBYTE(this.originalType);
        bitWriter.setWORD(this.userId);
        bitWriter.setBYTE(this.Type);
        bitWriter.setWORD(this.lengthOfName);
        bitWriter.setBYTEArray(this.name.getBytes(Const.CHARSET));

        return data;
    }

}
