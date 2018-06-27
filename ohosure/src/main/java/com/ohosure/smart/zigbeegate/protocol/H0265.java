package com.ohosure.smart.zigbeegate.protocol;

import com.ohosure.smart.database.devkit.utils.BitReader;

import java.util.ArrayList;



public class H0265 extends HReceive {

    BitReader bitReader;
    int resultCode;
    int deviceListSize;
    ArrayList<HSmartDevice> list = new ArrayList<HSmartDevice>();

    public H0265(byte[] data) {
        bitReader = BitReader.newInstance(data);
    }

    protected void parse() {

        resultCode = bitReader.getBYTE();
        deviceListSize = bitReader.getWORD();
        for (int i = 0; i < deviceListSize; i++) {
            HSmartDevice device = new HSmartDevice((int) bitReader.getDWORD(),
                    bitReader.getWORD(), bitReader.getBYTE(), bitReader.getWORD(),
                    (int) bitReader.getDWORD());
            list.add(device);
        }

    }

    public int getResultCode() {
        return resultCode;
    }

    public int getDeviceListSize() {
        return deviceListSize;
    }

    public ArrayList<HSmartDevice> getList() {
        return list;
    }
}
