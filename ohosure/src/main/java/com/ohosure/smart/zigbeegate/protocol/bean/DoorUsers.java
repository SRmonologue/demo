package com.ohosure.smart.zigbeegate.protocol.bean;

import java.util.List;

/**
 * Created by daxing on 2016/12/30.
 */

public class DoorUsers {

    private List<DoorUsersBean> doorUsers;

    public List<DoorUsersBean> getDoorUsers() {
        return doorUsers;
    }

    public void setDoorUsers(List<DoorUsersBean> doorUsers) {
        this.doorUsers = doorUsers;
    }

    public static class DoorUsersBean {
        /**
         * userId : 0
         * type : 0
         * userName : s
         */

        private int userId;
        private int type;
        private String userName;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
