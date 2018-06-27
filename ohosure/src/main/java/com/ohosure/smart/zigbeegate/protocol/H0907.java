package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.core.Const;
import com.ohosure.smart.database.devkit.utils.BitWriter;

import java.io.UnsupportedEncodingException;

public class H0907 extends HSend {

    int deviceId;
    int roomAreaId;
    int deviceType;
    int nameLength;
    String deviceName;
    int ipLength;
    String ip;

    public H0907(int deviceId,
                 int roomAreaId,
                 int deviceType,
                 String deviceName,
                 String ip)
            throws UnsupportedEncodingException {
        messageCode = 0x0907;
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.roomAreaId = roomAreaId;
        this.nameLength=deviceName.getBytes(Const.CHARSET).length;
        this.deviceName = deviceName;
        this.ipLength=ip.getBytes(Const.CHARSET).length;
        this.ip = ip;
        // message flag & message header
        bufferSize += 5;
        bufferSize += 4;
        bufferSize += 4;
        bufferSize += 2;
        bufferSize += 2;
        bufferSize +=nameLength;
        bufferSize += 2;
        bufferSize +=ipLength;


    }

    public byte[] getBytes() throws UnsupportedEncodingException {
        byte[] data = new byte[bufferSize];
        BitWriter bitWriter = BitWriter.newInstance(data);
        bitWriter.setBYTE(messageFlag);
        bitWriter.setWORD(messageCode);
        bitWriter.setWORD(bufferSize - sizeBefore);
        bitWriter.setDWORD(this.deviceId);
        bitWriter.setDWORD(this.roomAreaId);
        bitWriter.setWORD(this.deviceType);
        bitWriter.setWORD(this.nameLength);
        bitWriter.setBYTEArray(this.deviceName.getBytes(Const.CHARSET));
        bitWriter.setWORD(this.ipLength);
        bitWriter.setBYTEArray(this.ip.getBytes(Const.CHARSET));
        return data;
    }
}
