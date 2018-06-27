package com.ohosure.smart.zigbeegate.protocol.bean;

import java.util.List;

/**
 * Created by daxing on 2017/1/10.
 */

public class Configs {

    private List<ConfigsBean> configs;

    public List<ConfigsBean> getConfigs() {
        return configs;
    }

    public void setConfigs(List<ConfigsBean> configs) {
        this.configs = configs;
    }

    public static class ConfigsBean {
        /**
         * id : 435
         * device_type : 6
         * original_type : 1
         * feature_type : 1
         * value : 0
         */

        private int id;
        private int device_type;
        private int original_type;
        private int feature_type;
        private String value;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getDevice_type() {
            return device_type;
        }

        public void setDevice_type(int device_type) {
            this.device_type = device_type;
        }

        public int getOriginal_type() {
            return original_type;
        }

        public void setOriginal_type(int original_type) {
            this.original_type = original_type;
        }

        public int getFeature_type() {
            return feature_type;
        }

        public void setFeature_type(int feature_type) {
            this.feature_type = feature_type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
