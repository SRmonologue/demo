package com.ohosure.smart.zigbeegate.protocol;


import com.ohosure.smart.database.devkit.utils.BitReader;
import com.ohosure.smart.zigbeegate.protocol.model.HDevice;

/**
 * 设备定位上报
 *
 * @author daxing
 */
public class H0231 extends HReceive {

    BitReader bitReader;
    int deviceId;
    int deviceType;
    int deviceOriginal;
    HDevice device;

    public H0231(byte[] data) {
        bitReader = BitReader.newInstance(data);

    }

    public void parse() {
        deviceId = (int) bitReader.getDWORD();
        deviceType = bitReader.getWORD();
        deviceOriginal = bitReader.getBYTE();
        device=new HDevice(deviceId,deviceType,deviceOriginal);
    }


    public int getDeviceId() {
        return deviceId;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public int getDeviceOriginal() {
        return deviceOriginal;
    }

    public HDevice getDevice() {
        return device;
    }
}
