package com.ohosure.smart.zigbeegate.protocol;



public class HLinkageIn {

	int deviceId;
	int deviceType;
	int deviceOriginal;
	int featureType;
	byte[] featureValue;
	int condition1;
	int condition2;
	int condition3;
	int condition4;

	public HLinkageIn(int deviceId, int deviceType, int deviceOriginal, int featureType, byte[] featureValue, int condition1
	, int condition2, int condition3, int condition4) {
		this.deviceId = deviceId;
		this.deviceType = deviceType;
		this.deviceOriginal = deviceOriginal;
		this.featureType = featureType;
		this.featureValue = featureValue;
		this.condition1 = condition1;
		this.condition2 = condition2;
		this.condition3 = condition3;
		this.condition4 = condition4;

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

	public int getCondition1() {
		return condition1;
	}

	public void setCondition1(int condition1) {
		this.condition1 = condition1;
	}

	public int getCondition2() {
		return condition2;
	}

	public void setCondition2(int condition2) {
		this.condition2 = condition2;
	}

	public int getCondition3() {
		return condition3;
	}

	public void setCondition3(int condition3) {
		this.condition3 = condition3;
	}

	public int getCondition4() {
		return condition4;
	}

	public void setCondition4(int condition4) {
		this.condition4 = condition4;
	}
}
