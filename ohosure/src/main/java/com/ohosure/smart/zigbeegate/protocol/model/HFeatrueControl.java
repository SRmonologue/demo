package com.ohosure.smart.zigbeegate.protocol.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by daxing on 2016/12/21.
 */

public class HFeatrueControl implements Serializable {
    @JsonProperty("fc")
    private int fc;
    @JsonProperty("fv")
    private String fv;


    public HFeatrueControl(){};
    public HFeatrueControl(int fc, String fv ){
        this();
        this.fc=fc;
        this.fv=fv;


    }

    public String getFv() {
        return fv;
    }

    public void setFv(String fv) {
        this.fv = fv;
    }

    public int getFc() {
        return fc;
    }

    public void setFc(int fc) {
        this.fc = fc;
    }
}
