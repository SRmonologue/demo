package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.Const;
import com.ohosure.smart.database.devkit.utils.BitWriter;

import java.io.UnsupportedEncodingException;



public class H0908 extends HSend {

    int productId;
    int controlDeviceId;
    int roomAreaId;
    int controlDeviceType;
    int nameLength;
    String controlDeviceName;
    int port;
    int model;
    int unitType;
    int paramLength;
    String param;

    public H0908(int productId,
                 int controlDeviceId,
                 int roomAreaId,
                 int controlDeviceType,
                 String controlDeviceName,
                 int port,
                 int unitType,
                 String param)
            throws UnsupportedEncodingException {
        messageCode = 0x0908;
        this.productId = productId;
        this.controlDeviceId = controlDeviceId;
        this.roomAreaId = roomAreaId;
        this.controlDeviceType = controlDeviceType;
        this.nameLength = controlDeviceName.getBytes(Const.CHARSET).length;
        this.controlDeviceName = controlDeviceName;
        this.port = port;
        this.unitType = unitType;
        this.paramLength = param.getBytes(Const.CHARSET).length;
        this.param = param;

        // message flag & message header
        bufferSize += 5;
        bufferSize += 4;
        bufferSize += 4;
        bufferSize += 4;
        bufferSize += 2;
        bufferSize += 2;
        bufferSize += nameLength;
        bufferSize += 2;
        bufferSize += 2;
        bufferSize += 2;
        bufferSize += paramLength;


    }

    public byte[] getBytes() throws UnsupportedEncodingException {
        byte[] data = new byte[bufferSize];
        BitWriter bitWriter = BitWriter.newInstance(data);
        bitWriter.setBYTE(messageFlag);
        bitWriter.setWORD(messageCode);
        bitWriter.setWORD(bufferSize - sizeBefore);
        bitWriter.setDWORD(this.productId);
        bitWriter.setDWORD(this.controlDeviceId);
        bitWriter.setDWORD(this.roomAreaId);
        bitWriter.setWORD(this.controlDeviceType);
        bitWriter.setWORD(this.nameLength);
        bitWriter.setBYTEArray(this.controlDeviceName.getBytes(Const.CHARSET));
        bitWriter.setWORD(this.port);
        bitWriter.setWORD(this.unitType);
        bitWriter.setWORD(this.paramLength);
        bitWriter.setBYTEArray(this.param.getBytes(Const.CHARSET));
        return data;
    }
}
