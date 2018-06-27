package com.ohosure.smart.zigbeegate.protocol.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by daxing on 2016/10/26.
 */

public class DBConfig {
    @JsonProperty("id")
    int columnDeviceId;
    @JsonProperty("device_type")
    int columnDeviceType;
    @JsonProperty("original_type")
    int columnOriginalType;
    @JsonProperty("feature_type")
    int columnFeatureType;
    //值以base64字符串返回里面为字节数组
    @JsonProperty("value")
    String columnActionValue;

    public int getColumnDeviceId() {
        return columnDeviceId;
    }

    public void setColumnDeviceId(int columnDeviceId) {
        this.columnDeviceId = columnDeviceId;
    }

    public int getColumnDeviceType() {
        return columnDeviceType;
    }

    public void setColumnDeviceType(int columnDeviceType) {
        this.columnDeviceType = columnDeviceType;
    }

    public int getColumnOriginalType() {
        return columnOriginalType;
    }

    public void setColumnOriginalType(int columnOriginalType) {
        this.columnOriginalType = columnOriginalType;
    }

    public int getColumnFeatureType() {
        return columnFeatureType;
    }

    public void setColumnFeatureType(int columnFeatureType) {
        this.columnFeatureType = columnFeatureType;
    }

    public String getColumnActionValue() {
        return columnActionValue;
    }

    public void setColumnActionValue(String columnActionValue) {
        this.columnActionValue = columnActionValue;
    }
}
