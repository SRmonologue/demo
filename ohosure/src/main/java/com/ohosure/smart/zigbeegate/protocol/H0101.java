package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitReader;
import com.ohosure.smart.zigbeegate.protocol.model.HDevice;

public class H0101 extends HReceive {

    BitReader bitReader;

    HDevice mHDevice;
    long deviceId;
    int deviceType;
    int originalType;
    int featureType;
    int lengthOfValue;
    byte[] value;

    public H0101(byte[] data) {
        bitReader = BitReader.newInstance(data);

    }

    public H0101(long deviceId, int deviceType, int originalType, int featureType, int lengthOfValue, byte[] value) {
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.originalType = originalType;
        this.featureType = featureType;
        this.lengthOfValue = lengthOfValue;
        this.value = value;
    }

    public void parse() {
        deviceId = bitReader.getDWORD();
        deviceType = bitReader.getWORD();
        originalType = bitReader.getBYTE();
        featureType = bitReader.getWORD();
        lengthOfValue = bitReader.getWORD();
        value = bitReader.getByteArray(lengthOfValue);
        mHDevice = new HDevice((int) deviceId, deviceType, originalType);
    }


    public BitReader getBitReader() {
        return bitReader;
    }

    public long getDeviceId() {
        return deviceId;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public int getOriginalType() {
        return originalType;
    }

    public int getFeatureType() {
        return featureType;
    }

    public int getLengthOfValue() {
        return lengthOfValue;
    }

    public byte[] getValue() {
        return value;
    }

    public HDevice getmHDevice() {
        return mHDevice;
    }
}
