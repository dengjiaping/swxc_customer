package com.shiwaixiangcun.customer.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/2.
 */

public class BloodPressureBean implements Serializable {


    /**
     * elements : [{"createTime":1505697417000,"customerId":259,"healthStatus":"NORMAL","id":2278,"relaxationBlood":90,"relaxationBloodStatus":"NORMAL","shrinkBlood":100,"shrinkBloodStatus":"NORMAL","statusEnum":"Zhengchang","suggestion":"请继续保持当前的健康生活方式，并定期测量，祝您生活愉快哦！"}]
     * page : 1
     * size : 10
     * totalAmount : 1
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
         * createTime : 1505697417000
         * customerId : 259
         * healthStatus : NORMAL
         * id : 2278
         * relaxationBlood : 90
         * relaxationBloodStatus : NORMAL
         * shrinkBlood : 100
         * shrinkBloodStatus : NORMAL
         * statusEnum : Zhengchang
         * suggestion : 请继续保持当前的健康生活方式，并定期测量，祝您生活愉快哦！
         */

        private long createTime;
        private int customerId;
        private String healthStatus;
        private int id;
        private int relaxationBlood;
        private String relaxationBloodStatus;
        private int shrinkBlood;
        private String shrinkBloodStatus;
        private String statusEnum;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRelaxationBlood() {
            return relaxationBlood;
        }

        public void setRelaxationBlood(int relaxationBlood) {
            this.relaxationBlood = relaxationBlood;
        }

        public String getRelaxationBloodStatus() {
            return relaxationBloodStatus;
        }

        public void setRelaxationBloodStatus(String relaxationBloodStatus) {
            this.relaxationBloodStatus = relaxationBloodStatus;
        }

        public int getShrinkBlood() {
            return shrinkBlood;
        }

        public void setShrinkBlood(int shrinkBlood) {
            this.shrinkBlood = shrinkBlood;
        }

        public String getShrinkBloodStatus() {
            return shrinkBloodStatus;
        }

        public void setShrinkBloodStatus(String shrinkBloodStatus) {
            this.shrinkBloodStatus = shrinkBloodStatus;
        }

        public String getStatusEnum() {
            return statusEnum;
        }

        public void setStatusEnum(String statusEnum) {
            this.statusEnum = statusEnum;
        }

        public String getSuggestion() {
            return suggestion;
        }

        public void setSuggestion(String suggestion) {
            this.suggestion = suggestion;
        }
    }
}
