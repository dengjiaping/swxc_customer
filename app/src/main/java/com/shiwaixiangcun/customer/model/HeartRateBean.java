package com.shiwaixiangcun.customer.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/2.
 */

public class HeartRateBean implements Serializable {


    /**
     * elements : [{"createTime":1506243359000,"customerId":259,"healthStatus":"DANGER","heartRate":50,"id":208,"suggestion":"心率是指正常人安静状态下每分钟心跳的次数，也叫安静心率，一般为60～100次/分，可因年龄、性别或其他生理因素产生个体差异。运动时，心率会加快"},{"createTime":1506240596000,"customerId":259,"healthStatus":"DANGER","heartRate":50,"id":207,"suggestion":"心率是指正常人安静状态下每分钟心跳的次数，也叫安静心率，一般为60～100次/分，可因年龄、性别或其他生理因素产生个体差异。运动时，心率会加快"},{"createTime":1506240593000,"customerId":259,"healthStatus":"DANGER","heartRate":50,"id":206,"suggestion":"心率是指正常人安静状态下每分钟心跳的次数，也叫安静心率，一般为60～100次/分，可因年龄、性别或其他生理因素产生个体差异。运动时，心率会加快"},{"createTime":1506240049000,"customerId":259,"healthStatus":"DANGER","heartRate":50,"id":205,"suggestion":"心率是指正常人安静状态下每分钟心跳的次数，也叫安静心率，一般为60～100次/分，可因年龄、性别或其他生理因素产生个体差异。运动时，心率会加快"},{"createTime":1505898183000,"customerId":259,"healthStatus":"NORMAL","heartRate":89,"id":178,"suggestion":"心率是指正常人安静状态下每分钟心跳的次数，也叫安静心率，一般为60～100次/分，可因年龄、性别或其他生理因素产生个体差异。运动时，心率会加快"},{"createTime":1505697421000,"customerId":259,"healthStatus":"NORMAL","heartRate":80,"id":156,"suggestion":"心率是指正常人安静状态下每分钟心跳的次数，也叫安静心率，一般为60～100次/分，可因年龄、性别或其他生理因素产生个体差异。运动时，心率会加快"}]
     * page : 1
     * size : 10
     * totalAmount : 6
     * totalPages : 1
     */

    private int page;
    private int size;
    private int totalAmount;
    private int totalPages;
    private List<ElementsBean> elements;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<ElementsBean> getElements() {
        return elements;
    }

    public void setElements(List<ElementsBean> elements) {
        this.elements = elements;
    }

    public static class ElementsBean {
        /**
         * createTime : 1506243359000
         * customerId : 259
         * healthStatus : DANGER
         * heartRate : 50
         * id : 208
         * suggestion : 心率是指正常人安静状态下每分钟心跳的次数，也叫安静心率，一般为60～100次/分，可因年龄、性别或其他生理因素产生个体差异。运动时，心率会加快
         */

        private long createTime;
        private int customerId;
        private String healthStatus;
        private int heartRate;
        private int id;
        private String suggestion;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public String getHealthStatus() {
            return healthStatus;
        }

        public void setHealthStatus(String healthStatus) {
            this.healthStatus = healthStatus;
        }

        public int getHeartRate() {
            return heartRate;
        }

        public void setHeartRate(int heartRate) {
            this.heartRate = heartRate;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSuggestion() {
            return suggestion;
        }

        public void setSuggestion(String suggestion) {
            this.suggestion = suggestion;
        }
    }
}
