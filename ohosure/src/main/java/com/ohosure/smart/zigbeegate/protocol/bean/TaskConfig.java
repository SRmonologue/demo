package com.ohosure.smart.zigbeegate.protocol.bean;

import java.util.List;

/**
 * Created by daxing on 2017/1/3.
 */

public class TaskConfig {

    /**
     * taskId : 0
     * taskName : s
     * sceneId : 0
     * sceneName : s
     * actions : [{"deviceId":0,"deviceType":0,"deviceOriginal":0,"deviceName":"s","roomAreaName":"s","featureType":0,"featureValue":"s","delayTime":0}]
     */

    private int taskId;
    private String taskName;
    private int sceneId;
    private String sceneName;
    private List<ActionsBean> actions;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getSceneId() {
        return sceneId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
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
         * delayTime : 0
         */

        private int deviceId;
        private int deviceType;
        private int deviceOriginal;
        private String deviceName;
        private String roomAreaName;
        private int featureType;
        private int featureValue;
        private int delayTime;

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

        public int getFeatureValue() {
            return featureValue;
        }

        public void setFeatureValue(int featureValue) {
            this.featureValue = featureValue;
        }

        public int getDelayTime() {
            return delayTime;
        }

        public void setDelayTime(int delayTime) {
            this.delayTime = delayTime;
        }
    }
}
