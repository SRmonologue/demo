package com.ohosure.smart.zigbeegate.protocol.model;

import java.io.Serializable;

/**
 * Created by daxing on 2016/11/14.
 */

public class HAction implements Serializable {
    private int id;
    private int type;

    public HAction(){};
    public HAction(int id, int type ){
        this();
        this.id=id;
        this.type=type;

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

}
