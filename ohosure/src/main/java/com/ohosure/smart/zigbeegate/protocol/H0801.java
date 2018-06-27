package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitWriter;
import com.ohosure.smart.zigbeegate.protocol.model.HDevice;

import java.io.UnsupportedEncodingException;

public class H0801 extends HSend {

    long deviceId;
    int deviceType;
    int originalType;
    int featureType;
    int lengthOfValue;
    byte[] value;

    public H0801(HDevice mHDevice,
                 int featureType,
                 byte[] value) {

        messageCode = 0x0801;
        this.deviceId = mHDevice.getId();
        this.deviceType = mHDevice.getType();
        this.originalType = mHDevice.getOriginal();
        this.featureType = featureType;
        this.lengthOfValue = value.length;
        this.value=value;

        // message flag & message header
        bufferSize += 5;
        // length id
        bufferSize += 4;
        bufferSize += 7;
        bufferSize += lengthOfValue;

    }

    public H0801(long deviceId,
                 int deviceType,
                 int originalType,
                 int featureType,
                 int lengthOfValue,
                 byte[] value) {

        messageCode = 0x0801;
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.originalType = originalType;
        this.featureType = featureType;
        this.lengthOfValue = lengthOfValue;
        this.value=value;

        // message flag & message header
        bufferSize += 5;
        // length id
        bufferSize += 4;
        bufferSize += 7;
        bufferSize += lengthOfValue;

    }

    public H0801(long deviceId,
                 int deviceType,
                 int originalType,
                 int featureType,
                 byte[] value) {

        messageCode = 0x0801;
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.originalType = originalType;
        this.featureType = featureType;
        this.lengthOfValue = value.length;
        this.value=value;

        // message flag & message header
        bufferSize += 5;
        // length id
        bufferSize += 4;
        bufferSize += 7;
        bufferSize += lengthOfValue;

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
        bitWriter.setWORD(this.featureType);
        bitWriter.setWORD(this.lengthOfValue);
        bitWriter.setBYTEArray(this.value);

        return data;
    }

}
