package com.ohosure.smart.zigbeegate.protocol.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by daxing on 2016/10/26.
 */

public class DBSafeRecord {
    @JsonProperty("time")
    String time;
    @JsonProperty("state")
    int state;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
