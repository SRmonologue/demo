package com.ohosure.smart.zigbeegate.protocol;


public class HSmartDevice {

    int deviceId;
    int deviceType;
    int deviceOriginal;
    int featureType;
	int featureValue;
    public HSmartDevice(int deviceId, int deviceType,int deviceOriginal, int featureType,
                        int featureValue) {
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.deviceOriginal = deviceOriginal;
        this.featureType = featureType;
        this.featureValue = featureValue;

    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public int getDeviceOriginal() {
        return deviceOriginal;
    }

    public void setDeviceOriginal(int deviceOriginal) {
        this.deviceOriginal = deviceOriginal;
    }

    public int getFeatureType() {
        return featureType;
    }

    public void setFeatureType(int featureType) {
        this.featureType = featureType;
    }

    public int getFeatureValue() {
        return featureValue;
    }

    public void setFeatureValue(int featureValue) {
        this.featureValue = featureValue;
    }
}
