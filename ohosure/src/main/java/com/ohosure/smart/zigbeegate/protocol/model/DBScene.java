package com.ohosure.smart.zigbeegate.protocol.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by daxing on 2016/10/26.
 */

public class DBScene implements Serializable {
    @JsonProperty("id")
    int columnSceneId;
    @JsonProperty("name")
    String columnSceneName;
    @JsonProperty("desc")
    String columnSceneDescription;
    @JsonProperty("room_id")
    int columnRoomAreaId;
    @JsonProperty("room_name")
    String columnRoomAreaName;
    @JsonProperty("overview_id")
    int columnOverviewId;
    @JsonProperty("scene_type")
    int columnSceneType;

    public int getColumnSceneType() {
        return columnSceneType;
    }

    public void setColumnSceneType(int columnSceneType) {
        this.columnSceneType = columnSceneType;
    }

    public int getColumnSceneId() {
        return columnSceneId;
    }

    public void setColumnSceneId(int columnSceneId) {
        this.columnSceneId = columnSceneId;
    }

    public String getColumnSceneName() {
        return columnSceneName;
    }

    public void setColumnSceneName(String columnSceneName) {
        this.columnSceneName = columnSceneName;
    }

    public String getColumnSceneDescription() {
        return columnSceneDescription;
    }

    public void setColumnSceneDescription(String columnSceneDescription) {
        this.columnSceneDescription = columnSceneDescription;
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

    public int getColumnOverviewId() {
        return columnOverviewId;
    }

    public void setColumnOverviewId(int columnOverviewId) {
        this.columnOverviewId = columnOverviewId;
    }
}
