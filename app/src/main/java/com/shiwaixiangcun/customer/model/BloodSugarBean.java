package com.shiwaixiangcun.customer.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/2.
 */

public class BloodSugarBean implements Serializable {


    /**
     * data : {"elements":[{"bloodSugar":9.8,"createTime":1496382476000,"healthStatus":"WARNING","id":163,"statusEnum":"Piangao","sugarStatus":"FH","suggestion":"您的血糖偏高，注意控制饮食，减少含糖食物的食用，饭后1小时后适当做一些运动，如大步走、慢跑等，如有身体不适，请及时就诊"},{"bloodSugar":55,"createTime":1496367967000,"healthStatus":"DANGER","id":162,"statusEnum":"Yzpiangao","sugarStatus":"FH","suggestion":"您的血糖严重偏高，请及时就诊"},{"bloodSugar":5.2,"createTime":1496366761000,"healthStatus":"NORMAL","id":161,"statusEnum":"Zhengchang","sugarStatus":"FH","suggestion":"您的血糖正常，请继续保持当前的健康生活方式，可适度运动，如散步、慢跑等"},{"bloodSugar":5.2,"createTime":1496366758000,"healthStatus":"NORMAL","id":160,"statusEnum":"Zhengchang","sugarStatus":"KF","suggestion":"您的血糖正常，请继续保持当前的健康生活方式，可适度运动，如散步、慢跑等"},{"bloodSugar":5,"createTime":1496366701000,"healthStatus":"NORMAL","id":159,"statusEnum":"Zhengchang","sugarStatus":"KF","suggestion":"您的血糖正常，请继续保持当前的健康生活方式，可适度运动，如散步、慢跑等"},{"bloodSugar":7,"createTime":1496366697000,"healthStatus":"WARNING","id":158,"statusEnum":"Piangao","sugarStatus":"KF","suggestion":"您的血糖偏高，注意控制饮食，减少含糖食物的食用，饭后1小时后适当做一些运动，如大步走、慢跑等，如有身体不适，请及时就诊"},{"bloodSugar":6,"createTime":1496366693000,"healthStatus":"NORMAL","id":157,"statusEnum":"Zhengchang","sugarStatus":"KF","suggestion":"您的血糖正常，请继续保持当前的健康生活方式，可适度运动，如散步、慢跑等"}],"page":1,"size":7,"totalAmount":12,"totalPages":2}
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

    public static class DataBean implements Serializable{
        /**
         * elements : [{"bloodSugar":9.8,"createTime":1496382476000,"healthStatus":"WARNING","id":163,"statusEnum":"Piangao","sugarStatus":"FH","suggestion":"您的血糖偏高，注意控制饮食，减少含糖食物的食用，饭后1小时后适当做一些运动，如大步走、慢跑等，如有身体不适，请及时就诊"},{"bloodSugar":55,"createTime":1496367967000,"healthStatus":"DANGER","id":162,"statusEnum":"Yzpiangao","sugarStatus":"FH","suggestion":"您的血糖严重偏高，请及时就诊"},{"bloodSugar":5.2,"createTime":1496366761000,"healthStatus":"NORMAL","id":161,"statusEnum":"Zhengchang","sugarStatus":"FH","suggestion":"您的血糖正常，请继续保持当前的健康生活方式，可适度运动，如散步、慢跑等"},{"bloodSugar":5.2,"createTime":1496366758000,"healthStatus":"NORMAL","id":160,"statusEnum":"Zhengchang","sugarStatus":"KF","suggestion":"您的血糖正常，请继续保持当前的健康生活方式，可适度运动，如散步、慢跑等"},{"bloodSugar":5,"createTime":1496366701000,"healthStatus":"NORMAL","id":159,"statusEnum":"Zhengchang","sugarStatus":"KF","suggestion":"您的血糖正常，请继续保持当前的健康生活方式，可适度运动，如散步、慢跑等"},{"bloodSugar":7,"createTime":1496366697000,"healthStatus":"WARNING","id":158,"statusEnum":"Piangao","sugarStatus":"KF","suggestion":"您的血糖偏高，注意控制饮食，减少含糖食物的食用，饭后1小时后适当做一些运动，如大步走、慢跑等，如有身体不适，请及时就诊"},{"bloodSugar":6,"createTime":1496366693000,"healthStatus":"NORMAL","id":157,"statusEnum":"Zhengchang","sugarStatus":"KF","suggestion":"您的血糖正常，请继续保持当前的健康生活方式，可适度运动，如散步、慢跑等"}]
         * page : 1
         * size : 7
         * totalAmount : 12
         * totalPages : 2
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
             * bloodSugar : 9.8
             * createTime : 1496382476000
             * healthStatus : WARNING
             * id : 163
             * statusEnum : Piangao
             * sugarStatus : FH
             * suggestion : 您的血糖偏高，注意控制饮食，减少含糖食物的食用，饭后1小时后适当做一些运动，如大步走、慢跑等，如有身体不适，请及时就诊
             */

            private double bloodSugar;
            private long createTime;
            private String healthStatus;
            private int id;
            private String statusEnum;
            private String sugarStatus;
            private String suggestion;

            public double getBloodSugar() {
                return bloodSugar;
            }

            public void setBloodSugar(double bloodSugar) {
                this.bloodSugar = bloodSugar;
            }

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

            public String getStatusEnum() {
                return statusEnum;
            }

            public void setStatusEnum(String statusEnum) {
                this.statusEnum = statusEnum;
            }

            public String getSugarStatus() {
                return sugarStatus;
            }

            public void setSugarStatus(String sugarStatus) {
                this.sugarStatus = sugarStatus;
            }

            public String getSuggestion() {
                return suggestion;
            }

            public void setSuggestion(String suggestion) {
                this.suggestion = suggestion;
            }
        }
    }
}
