package com.ohosure.smart.zigbeegate.protocol;



public class HBehavior {

	int deviceId;
	int deviceType;
	int deviceOriginal;
	int featureType;
	byte[] featureValue;
	int delayTime;

	public HBehavior(int deviceId, int deviceType, int deviceOriginal, int featureType, byte[] featureValue, int delayTime) {
		this.deviceId = deviceId;
		this.deviceType = deviceType;
		this.deviceOriginal = deviceOriginal;
		this.featureType = featureType;
		this.featureValue = featureValue;
		this.delayTime = delayTime;
	}

	public int getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(int delayTime) {
		this.delayTime = delayTime;
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

	public byte[] getFeatureValue() {
		return featureValue;
	}

	public void setFeatureValue(byte[] featureValue) {
		this.featureValue = featureValue;
	}
}
