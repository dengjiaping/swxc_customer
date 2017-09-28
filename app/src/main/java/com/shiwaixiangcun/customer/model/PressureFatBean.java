package com.shiwaixiangcun.customer.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/20.
 */

public class PressureFatBean implements Serializable {


    /**
     * elements : [{"createTime":1494237529000,"customerId":126,"healthStatus":"WARNING","id":28,"lowLipo":0.1,"topLipo":0.8,"totalCholesterol":2.8,"triglyceride":10}]
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
         * createTime : 1494237529000
         * customerId : 126
         * healthStatus : WARNING
         * id : 28
         * lowLipo : 0.1
         * topLipo : 0.8
         * totalCholesterol : 2.8
         * triglyceride : 10
         */

        private long createTime;
        private int customerId;
        private String healthStatus;
        private int id;
        private double lowLipo;
        private double topLipo;
        private double totalCholesterol;
        private int triglyceride;

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

        public double getLowLipo() {
            return lowLipo;
        }

        public void setLowLipo(double lowLipo) {
            this.lowLipo = lowLipo;
        }

        public double getTopLipo() {
            return topLipo;
        }

        public void setTopLipo(double topLipo) {
            this.topLipo = topLipo;
        }

        public double getTotalCholesterol() {
            return totalCholesterol;
        }

        public void setTotalCholesterol(double totalCholesterol) {
            this.totalCholesterol = totalCholesterol;
        }

        public int getTriglyceride() {
            return triglyceride;
        }

        public void setTriglyceride(int triglyceride) {
            this.triglyceride = triglyceride;
        }
    }
}
