package com.ohosure.smart.zigbeegate.protocol.bean;

import java.util.List;

/**
 * Created by daxing on 2017/1/5.
 */

public class SceneAll {

    private List<ScenesBean> scenes;

    public List<ScenesBean> getScenes() {
        return scenes;
    }

    public void setScenes(List<ScenesBean> scenes) {
        this.scenes = scenes;
    }

    public static class ScenesBean {
        /**
         * sceneId : 0
         * sceneName : s
         * sceneDescription : s
         * sceneType : 0
         * roomAreaId : 0
         * roomAreaName : s
         * overviewId : 0
         */

        private int sceneId;
        private String sceneName;
        private String sceneDescription;
        private int sceneType;
        private int roomAreaId;
        private String roomAreaName;
        private int overviewId;

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

        public String getSceneDescription() {
            return sceneDescription;
        }

        public void setSceneDescription(String sceneDescription) {
            this.sceneDescription = sceneDescription;
        }

        public int getSceneType() {
            return sceneType;
        }

        public void setSceneType(int sceneType) {
            this.sceneType = sceneType;
        }

        public int getRoomAreaId() {
            return roomAreaId;
        }

        public void setRoomAreaId(int roomAreaId) {
            this.roomAreaId = roomAreaId;
        }

        public String getRoomAreaName() {
            return roomAreaName;
        }

        public void setRoomAreaName(String roomAreaName) {
            this.roomAreaName = roomAreaName;
        }

        public int getOverviewId() {
            return overviewId;
        }

        public void setOverviewId(int overviewId) {
            this.overviewId = overviewId;
        }
    }
}
