package com.ohosure.smart.zigbeegate.protocol.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by daxing on 2016/11/14.
 */

public class HLockRecord implements Serializable {
    @JsonProperty("time")
    private String time;
    @JsonProperty("group")
    private int group;
    @JsonProperty("name")
    private String name;

    public HLockRecord(){};
    public HLockRecord(String time, int group, String name ){
        this();
        this.time=time;
        this.group=group;
        this.name=name;


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }
}
