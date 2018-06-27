package com.ohosure.smart.zigbeegate.protocol;

import com.ohosure.smart.database.devkit.utils.BitReader;

import java.util.ArrayList;
import java.util.List;



public class H0102 extends HReceive {

    BitReader bitReader;

    H0101 mHDevice;
    long deviceId;
    int deviceType;
    int originalType;
    int featureType;
    int lengthOfValue;
    byte[] value;
    int count;

    private List<H0101> mHDeviceList = new ArrayList<>();


    public H0102(byte[] data) {
        bitReader = BitReader.newInstance(data);
        parse();
    }

    public void parse() {
        count = bitReader.getWORD();
        for (int i = 0; i < count; i++) {
            int currentIndex = bitReader.getCurrentIndex();
            bitReader.setCurrentIndex(currentIndex + 5);
            deviceId = bitReader.getDWORD();
            deviceType = bitReader.getWORD();
            originalType = bitReader.getBYTE();
            featureType = bitReader.getWORD();
            lengthOfValue = bitReader.getWORD();
            value = bitReader.getByteArray(lengthOfValue);
            bitReader.setCurrentIndex(bitReader.getCurrentIndex() + lengthOfValue);
            mHDevice = new H0101(deviceId, deviceType, originalType, featureType, lengthOfValue, value);
            mHDeviceList.add(mHDevice);
        }
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

    public H0101 getmHDevice() {
        return mHDevice;
    }

    public List<H0101> getHDeviceList() {
        return mHDeviceList;
    }
}
