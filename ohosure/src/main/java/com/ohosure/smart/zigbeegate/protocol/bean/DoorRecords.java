package com.ohosure.smart.zigbeegate.protocol.bean;

import java.util.List;

/**
 * Created by daxing on 2016/12/30.
 */

public class DoorRecords {


    private List<DoorRecordsBean> doorRecords;

    public List<DoorRecordsBean> getDoorRecords() {
        return doorRecords;
    }

    public void setDoorRecords(List<DoorRecordsBean> doorRecords) {
        this.doorRecords = doorRecords;
    }

    public static class DoorRecordsBean {
        /**
         * time : yyyy-mm-dd HH:mm:ss
         * state : 0
         * userId : 0
         * userName : s
         * type : 0
         */

        private String time;
        private int state;
        private int userId;
        private String userName;
        private int type;

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

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
