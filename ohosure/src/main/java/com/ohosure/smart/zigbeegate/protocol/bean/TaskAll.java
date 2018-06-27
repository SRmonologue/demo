package com.ohosure.smart.zigbeegate.protocol.bean;

import java.util.List;

/**
 * Created by daxing on 2017/1/3.
 */

public class TaskAll {

    private List<TasksBean> tasks;

    public List<TasksBean> getTasks() {
        return tasks;
    }

    public void setTasks(List<TasksBean> tasks) {
        this.tasks = tasks;
    }

    public static class TasksBean  {
        /**
         * taskId : 0
         * taskName : s
         * repeatTime : 0
         * executeTime : 0
         * enableStatus : 0
         */

        private int taskId;
        private String taskName;
        private int repeatTime;
        private int executeTime;
        private int enableStatus;

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

        public int getRepeatTime() {
            return repeatTime;
        }

        public void setRepeatTime(int repeatTime) {
            this.repeatTime = repeatTime;
        }

        public int getExecuteTime() {
            return executeTime;
        }

        public void setExecuteTime(int executeTime) {
            this.executeTime = executeTime;
        }

        public int getEnableStatus() {
            return enableStatus;
        }

        public void setEnableStatus(int enableStatus) {
            this.enableStatus = enableStatus;
        }
    }
}
