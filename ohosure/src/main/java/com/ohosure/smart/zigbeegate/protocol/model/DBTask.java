package com.ohosure.smart.zigbeegate.protocol.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by daxing on 2016/10/26.
 */

public class DBTask {
    @JsonProperty("id")
    int columnTaskId;
    @JsonProperty("name")
    String columnTaskName;
    @JsonProperty("repeat_time")
    int columnRepeatTime;
    @JsonProperty("execute_time")
    int columnExecuteTime;
    @JsonProperty("enable_status")
    int columnEnableStatus;

    public int getColumnTaskId() {
        return columnTaskId;
    }

    public void setColumnTaskId(int columnTaskId) {
        this.columnTaskId = columnTaskId;
    }

    public String getColumnTaskName() {
        return columnTaskName;
    }

    public void setColumnTaskName(String columnTaskName) {
        this.columnTaskName = columnTaskName;
    }

    public int getColumnRepeatTime() {
        return columnRepeatTime;
    }

    public void setColumnRepeatTime(int columnRepeatTime) {
        this.columnRepeatTime = columnRepeatTime;
    }

    public int getColumnExecuteTime() {
        return columnExecuteTime;
    }

    public void setColumnExecuteTime(int columnExecuteTime) {
        this.columnExecuteTime = columnExecuteTime;
    }

    public int getColumnEnableStatus() {
        return columnEnableStatus;
    }

    public void setColumnEnableStatus(int columnEnableStatus) {
        this.columnEnableStatus = columnEnableStatus;
    }
}
