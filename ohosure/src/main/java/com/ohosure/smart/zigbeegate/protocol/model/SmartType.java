package com.ohosure.smart.zigbeegate.protocol.model;

/**
 * Created by daxing on 2016/11/14.
 */

public class SmartType {
    private int type;
    private String typeName;
    public SmartType() {
    }
    public SmartType(int type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
