package com.ohosure.smart.zigbeegate.protocol.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by daxing on 2016/10/26.
 */

public class DBRecord {
    @JsonProperty("id")
    int id;
    @JsonProperty("state")
    int columnState;
    @JsonProperty("time")
    String columnTime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getColumnState() {
        return columnState;
    }

    public void setColumnState(int columnState) {
        this.columnState = columnState;
    }

    public String getColumnTime() {
        return columnTime;
    }

    public void setColumnTime(String columnTime) {
        this.columnTime = columnTime;
    }
}
