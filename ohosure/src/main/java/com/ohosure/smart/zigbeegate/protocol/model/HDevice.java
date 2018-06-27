package com.ohosure.smart.zigbeegate.protocol.model;

import java.io.Serializable;

/**
 * Created by daxing on 2016/11/14.
 */

public class HDevice implements Serializable {
    private int id;
    private int type;
    private int original;

    public HDevice(){};
    public HDevice(int id,int type,int original){
        this();
        this.id=id;
        this.type=type;
        this.original=original;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOriginal() {
        return original;
    }

    public void setOriginal(int original) {
        this.original = original;
    }

    @Override
    public int hashCode() {

        return id+type+original;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HDevice other = (HDevice) obj;
        if (id != other.getId() || type != other.getType() || original != other.getOriginal())
            return false;
        return true;
    }

}
