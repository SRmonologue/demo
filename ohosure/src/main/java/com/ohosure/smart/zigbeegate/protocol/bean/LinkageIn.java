package com.ohosure.smart.zigbeegate.protocol.bean;

import java.util.List;

/**
 * Created by daxing on 2017/1/12.
 */

public class LinkageIn {

    /**
     * linkageId : 0
     * linkageName : s
     * actions : [{"deviceId":0,"deviceType":0,"deviceOriginal":0,"deviceName":"s","roomAreaName":"s","featureType":0,"featureValue":"s","option1":0,"option2":0,"option3":0,"option4":0}]
     */

    private int linkageId;
    private String linkageName;
    private List<ActionsBean> actions;

    public int getLinkageId() {
        return linkageId;
    }

    public void setLinkageId(int linkageId) {
        this.linkageId = linkageId;
    }

    public String getLinkageName() {
        return linkageName;
    }

    public void setLinkageName(String linkageName) {
        this.linkageName = linkageName;
    }

    public List<ActionsBean> getActions() {
        return actions;
    }

    public void setActions(List<ActionsBean> actions) {
        this.actions = actions;
    }

    public static class ActionsBean {
        /**
         * deviceId : 0
         * deviceType : 0
         * deviceOriginal : 0
         * deviceName : s
         * roomAreaName : s
         * featureType : 0
         * featureValue : s
         * option1 : 0
         * option2 : 0
         * option3 : 0
         * option4 : 0
         */

        private int deviceId;
        private int deviceType;
        private int deviceOriginal;
        private String deviceName;
        private String roomAreaName;
        private int featureType;
        private String featureValue;
        private int option1;
        private int option2;
        private int option3;
        private int option4;

        public int getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(int deviceId) {
            this.deviceId = deviceId;
        }

        public int getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(int deviceType) {
            this.deviceType = deviceType;
        }

        public int getDeviceOriginal() {
            return deviceOriginal;
        }

        public void setDeviceOriginal(int deviceOriginal) {
            this.deviceOriginal = deviceOriginal;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public String getRoomAreaName() {
            return roomAreaName;
        }

        public void setRoomAreaName(String roomAreaName) {
            this.roomAreaName = roomAreaName;
        }

        public int getFeatureType() {
            return featureType;
        }

        public void setFeatureType(int featureType) {
            this.featureType = featureType;
        }

        public String getFeatureValue() {
            return featureValue;
        }

        public void setFeatureValue(String featureValue) {
            this.featureValue = featureValue;
        }

        public int getOption1() {
            return option1;
        }

        public void setOption1(int option1) {
            this.option1 = option1;
        }

        public int getOption2() {
            return option2;
        }

        public void setOption2(int option2) {
            this.option2 = option2;
        }

        public int getOption3() {
            return option3;
        }

        public void setOption3(int option3) {
            this.option3 = option3;
        }

        public int getOption4() {
            return option4;
        }

        public void setOption4(int option4) {
            this.option4 = option4;
        }
    }
}
