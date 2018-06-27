package com.ohosure.smart.zigbeegate.protocol.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by daxing on 2016/10/26.
 */

public class DBProduct {
    @JsonProperty("id")
    int columnProductId;
    @JsonProperty("type")
    int columnProductType;
    @JsonProperty("name")
    String columnProductName;
    @JsonProperty("room_id")
    int columnRoomAreaId;
    @JsonProperty("room_name")
    String columnRoomAreaName;
    @JsonProperty("state")
    int columnProductState;

    public int getColumnProductId() {
        return columnProductId;
    }

    public void setColumnProductId(int columnProductId) {
        this.columnProductId = columnProductId;
    }

    public int getColumnProductType() {
        return columnProductType;
    }

    public void setColumnProductType(int columnProductType) {
        this.columnProductType = columnProductType;
    }

    public String getColumnProductName() {
        return columnProductName;
    }

    public void setColumnProductName(String columnProductName) {
        this.columnProductName = columnProductName;
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

    public int getColumnProductState() {
        return columnProductState;
    }

    public void setColumnProductState(int columnProductState) {
        this.columnProductState = columnProductState;
    }
}
