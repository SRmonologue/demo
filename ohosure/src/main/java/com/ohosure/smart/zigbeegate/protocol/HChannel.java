package com.ohosure.smart.zigbeegate.protocol;



public class HChannel {

	long channelId;
	int channelFeatureType;
	long channelFeatureValue;

	public HChannel(long channelId, int channelFeatureType,
			long channelFeatureValue) {
		this.channelId = channelId;
		this.channelFeatureType = channelFeatureType;
		this.channelFeatureValue = channelFeatureValue;

	}

	public long getChannelId() {
		return channelId;
	}

	public void setChannelId(long channelId) {
		this.channelId = channelId;
	}

	public int getChannelFeatureType() {
		return channelFeatureType;
	}

	public void setChannelFeatureType(int channelFeatureType) {
		this.channelFeatureType = channelFeatureType;
	}

	public long getChannelFeatureValue() {
		return channelFeatureValue;
	}

	public void setChannelFeatureValue(long channelFeatureValue) {
		this.channelFeatureValue = channelFeatureValue;
	}

	 

}
