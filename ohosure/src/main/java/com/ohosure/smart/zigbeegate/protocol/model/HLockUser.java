package com.ohosure.smart.zigbeegate.protocol.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by daxing on 2016/11/14.
 */

public class HLockUser implements Serializable {
    @JsonProperty("id")
    private int id;
    @JsonProperty("group")
    private int group;
    @JsonProperty("name")
    private String name;

    public HLockUser(){};
    public HLockUser(int id, int group,String name ){
        this();
        this.id=id;
        this.group=group;
        this.name=name;


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }
}
