package com.ohosure.smart.zigbeegate.protocol.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by daxing on 2016/10/25.
 */

public class DBRoomArea implements Serializable {
    @JsonProperty("id")
    int columnRoomAreaId;
    @JsonProperty("name")
    String columnRoomAreaName;
    @JsonProperty("desc")
    String columnRoomAreaDescription;
    @JsonProperty("floor")
    int columnFloor;
    @JsonProperty("type")
    int columnType;

    public DBRoomArea(){}

    public DBRoomArea(int id, String name, String desc, int floor, int type){
        this.columnRoomAreaId=id;
        this.columnRoomAreaName=name;
        this.columnRoomAreaDescription=desc;
        this.columnFloor=floor;
        this.columnType=type;
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

    public String getColumnRoomAreaDescription() {
        return columnRoomAreaDescription;
    }

    public void setColumnRoomAreaDescription(String columnRoomAreaDescription) {
        this.columnRoomAreaDescription = columnRoomAreaDescription;
    }

    public int getColumnFloor() {
        return columnFloor;
    }

    public void setColumnFloor(int columnFloor) {
        this.columnFloor = columnFloor;
    }

    public int getColumnType() {
        return columnType;
    }

    public void setColumnType(int columnType) {
        this.columnType = columnType;
    }

    @Override
    public String toString() {
        return "DBRoomArea{" +
                "columnRoomAreaId=" + columnRoomAreaId +
                ", columnRoomAreaName='" + columnRoomAreaName + '\'' +
                ", columnRoomAreaDescription='" + columnRoomAreaDescription + '\'' +
                '}';
    }
}
