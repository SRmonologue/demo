package com.ohosure.smart.zigbeegate.protocol.bean;

import java.util.List;

/**
 * Created by daxing on 2017/1/3.
 */

public class TaskToday {


    /**
     * sessionID : session_autobee-107
     * result : 0
     * queryInfo : taskToday
     * config : {"tasks":[{"taskId":"16","taskName":"1","repeatTime":"13","executeTime":"2238","enableStatus":"1","Status":"1"},{"taskId":"47","taskName":"1","repeatTime":"15","executeTime":"1138","enableStatus":"1","Status":"0"},{"taskId":"62","taskName":"15","repeatTime":"8","executeTime":"1119","enableStatus":"1","Status":"0"}]}
     * system : {"type":"sg_ab"}
     */

    private String sessionID;
    private String result;
    private String queryInfo;
    private ConfigBean config;
    private SystemBean system;

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getQueryInfo() {
        return queryInfo;
    }

    public void setQueryInfo(String queryInfo) {
        this.queryInfo = queryInfo;
    }

    public ConfigBean getConfig() {
        return config;
    }

    public void setConfig(ConfigBean config) {
        this.config = config;
    }

    public SystemBean getSystem() {
        return system;
    }

    public void setSystem(SystemBean system) {
        this.system = system;
    }

    public static class ConfigBean {
        private List<TasksBean> tasks;

        public List<TasksBean> getTasks() {
            return tasks;
        }

        public void setTasks(List<TasksBean> tasks) {
            this.tasks = tasks;
        }

        public static class TasksBean {
            /**
             * taskId : 16
             * taskName : 1
             * repeatTime : 13
             * executeTime : 2238
             * enableStatus : 1
             * Status : 1
             */

            private String taskId;
            private String taskName;
            private String repeatTime;
            private String executeTime;
            private String enableStatus;
            private String Status;

            public String getTaskId() {
                return taskId;
            }

            public void setTaskId(String taskId) {
                this.taskId = taskId;
            }

            public String getTaskName() {
                return taskName;
            }

            public void setTaskName(String taskName) {
                this.taskName = taskName;
            }

            public String getRepeatTime() {
                return repeatTime;
            }

            public void setRepeatTime(String repeatTime) {
                this.repeatTime = repeatTime;
            }

            public String getExecuteTime() {
                return executeTime;
            }

            public void setExecuteTime(String executeTime) {
                this.executeTime = executeTime;
            }

            public String getEnableStatus() {
                return enableStatus;
            }

            public void setEnableStatus(String enableStatus) {
                this.enableStatus = enableStatus;
            }

            public String getStatus() {
                return Status;
            }

            public void setStatus(String Status) {
                this.Status = Status;
            }
        }
    }

    public static class SystemBean {
        /**
         * type : sg_ab
         */

        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
