package com.ohosure.smart.zigbeegate.protocol.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by daxing on 2016/10/26.
 */

public class DBControlDevice implements Serializable {
    @JsonProperty("id")
    int columnControlDeviceId;
    @JsonProperty("type")
    int columnControlDeviceType;
    @JsonProperty("name")
    String columnControlDeviceName;
    @JsonProperty("room_id")
    int columnRoomAreaId;
    @JsonProperty("room_name")
    String columnRoomAreaName;
    @JsonProperty("product_id")
    int columnProductId;

    public int getColumnControlDeviceId() {
        return columnControlDeviceId;
    }

    public void setColumnControlDeviceId(int columnControlDeviceId) {
        this.columnControlDeviceId = columnControlDeviceId;
    }

    public int getColumnControlDeviceType() {
        return columnControlDeviceType;
    }

    public void setColumnControlDeviceType(int columnControlDeviceType) {
        this.columnControlDeviceType = columnControlDeviceType;
    }

    public String getColumnControlDeviceName() {
        return columnControlDeviceName;
    }

    public void setColumnControlDeviceName(String columnControlDeviceName) {
        this.columnControlDeviceName = columnControlDeviceName;
    }

    public int getColumnRoomAreaId() {
        return columnRoomAreaId;
    }

    public void setColumnRoomAreaId(int columnRoomAreaId) {
        this.columnRoomAreaId = columnRoomAreaId;
    }

    public String getColumnRoomAreaName() {
        return columnRoomAreaName;
    }

    public void setColumnRoomAreaName(String columnRoomAreaName) {
        this.columnRoomAreaName = columnRoomAreaName;
    }

    public int getColumnProductId() {
        return columnProductId;
    }

    public void setColumnProductId(int columnProductId) {
        this.columnProductId = columnProductId;
    }
}
