package com.ohosure.smart.zigbeegate.protocol.bean;

import java.util.List;

/**
 * Created by daxing on 2017/1/11.
 */

public class LinkageAll {


    private List<LinkagesBean> linkages;

    public List<LinkagesBean> getLinkages() {
        return linkages;
    }

    public void setLinkages(List<LinkagesBean> linkages) {
        this.linkages = linkages;
    }

    public static class LinkagesBean {
        /**
         * linkageId : 0
         * linkName : s
         * enableStatus : 0
         */

        private int linkageId;
        private String linkageName;
        private int enableStatus;

        public int getLinkageId() {
            return linkageId;
        }

        public void setLinkageId(int linkageId) {
            this.linkageId = linkageId;
        }

        public String getLinkageName() {
            return linkageName;
        }

        public void setLinkName(String linkName) {
            this.linkageName = linkName;
        }

        public int getEnableStatus() {
            return enableStatus;
        }

        public void setEnableStatus(int enableStatus) {
            this.enableStatus = enableStatus;
        }
    }
}
