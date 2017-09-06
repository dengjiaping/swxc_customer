package com.shiwaixiangcun.customer.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/20.
 */

public class PressureFatBean implements Serializable {

    /**
     * data : {"elements":[{"createTime":1497592618000,"healthStatus":"NORMAL","id":147,"lowLipo":2.5,"lowLipoStatus":"NORMAL","source":null,"statusEnum":"Zhengchang","suggestion":"请保持当前的健康生活方式，适当做一些有氧运动，如慢跑、骑自行车、跳绳、健身操等","topLipo":3.1,"topLipo1Status":"NORMAL","totalCholesterol":5,"totalCholesterolStatus":"NORMAL","triglyceride":1.5,"triglycerideStatus":"WARNING"}],"page":1,"size":7,"totalAmount":1,"totalPages":1}
     * message : 操作成功
     * responseCode : 1001
     * success : true
     */

    private DataBean data;
    private String message;
    private int responseCode;
    private boolean success;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean implements Serializable {
        /**
         * elements : [{"createTime":1497592618000,"healthStatus":"NORMAL","id":147,"lowLipo":2.5,"lowLipoStatus":"NORMAL","source":null,"statusEnum":"Zhengchang","suggestion":"请保持当前的健康生活方式，适当做一些有氧运动，如慢跑、骑自行车、跳绳、健身操等","topLipo":3.1,"topLipo1Status":"NORMAL","totalCholesterol":5,"totalCholesterolStatus":"NORMAL","triglyceride":1.5,"triglycerideStatus":"WARNING"}]
         * page : 1
         * size : 7
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

        public static class ElementsBean implements Serializable{
            /**
             * createTime : 1497592618000
             * healthStatus : NORMAL
             * id : 147
             * lowLipo : 2.5
             * lowLipoStatus : NORMAL
             * source : null
             * statusEnum : Zhengchang
             * suggestion : 请保持当前的健康生活方式，适当做一些有氧运动，如慢跑、骑自行车、跳绳、健身操等
             * topLipo : 3.1
             * topLipo1Status : NORMAL
             * totalCholesterol : 5
             * totalCholesterolStatus : NORMAL
             * triglyceride : 1.5
             * triglycerideStatus : WARNING
             */

            private long createTime;
            private String healthStatus;
            private int id;
            private double lowLipo;
            private String lowLipoStatus;
            private Object source;
            private String statusEnum;
            private String suggestion;
            private double topLipo;
            private String topLipo1Status;
            private double totalCholesterol;
            private String totalCholesterolStatus;
            private double triglyceride;
            private String triglycerideStatus;

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
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

            public String getLowLipoStatus() {
                return lowLipoStatus;
            }

            public void setLowLipoStatus(String lowLipoStatus) {
                this.lowLipoStatus = lowLipoStatus;
            }

            public Object getSource() {
                return source;
            }

            public void setSource(Object source) {
                this.source = source;
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

            public double getTopLipo() {
                return topLipo;
            }

            public void setTopLipo(double topLipo) {
                this.topLipo = topLipo;
            }

            public String getTopLipo1Status() {
                return topLipo1Status;
            }

            public void setTopLipo1Status(String topLipo1Status) {
                this.topLipo1Status = topLipo1Status;
            }

            public double getTotalCholesterol() {
                return totalCholesterol;
            }

            public void setTotalCholesterol(double totalCholesterol) {
                this.totalCholesterol = totalCholesterol;
            }

            public String getTotalCholesterolStatus() {
                return totalCholesterolStatus;
            }

            public void setTotalCholesterolStatus(String totalCholesterolStatus) {
                this.totalCholesterolStatus = totalCholesterolStatus;
            }

            public double getTriglyceride() {
                return triglyceride;
            }

            public void setTriglyceride(double triglyceride) {
                this.triglyceride = triglyceride;
            }

            public String getTriglycerideStatus() {
                return triglycerideStatus;
            }

            public void setTriglycerideStatus(String triglycerideStatus) {
                this.triglycerideStatus = triglycerideStatus;
            }
        }
    }
}
