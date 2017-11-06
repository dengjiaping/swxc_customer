package com.shiwaixiangcun.customer.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/5.
 */

public class HealthAllActivity implements Serializable {


    /**
     * data : {"bloodCreateTime":1496366856000,"bloodStatus":"NORMAL","bloodSugar":9.8,"bmi":19.6,"bmiCreateTime":1496732942000,"bmiStatus":"NORMAL","customerId":146,"dataComplete":"","heartRate":128,"height":175,"lowLipo":2,"pressureCreateTime":1496896200000,"pressureStatus":"WARNING","relaxationBlood":80,"shrinkBlood":100,"sugarCreateTime":1496382476000,"sugarStatus":"WARNING","suggestion":"非常好，您的健康数据均正常，请继续保持","testStatus":"FH","topLipo":9,"totalCholesterol":5,"totalStatus":"WARNING","triglyceride":1.5,"weight":60,"xinlvStatus":"WARNING"}
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

    public static class DataBean {
        /**
         * bloodCreateTime : 1496366856000
         * bloodStatus : NORMAL
         * bloodSugar : 9.8
         * bmi : 19.6
         * bmiCreateTime : 1496732942000
         * bmiStatus : NORMAL
         * customerId : 146
         * dataComplete :
         * heartRate : 128
         * height : 175
         * lowLipo : 2
         * pressureCreateTime : 1496896200000
         * pressureStatus : WARNING
         * relaxationBlood : 80
         * shrinkBlood : 100
         * sugarCreateTime : 1496382476000
         * sugarStatus : WARNING
         * suggestion : 非常好，您的健康数据均正常，请继续保持
         * testStatus : FH
         * topLipo : 9
         * totalCholesterol : 5
         * totalStatus : WARNING
         * triglyceride : 1.5
         * weight : 60
         * xinlvStatus : WARNING
         */

        private long bloodCreateTime;
        private String bloodStatus;
        private double bloodSugar;
        private double bmi;
        private long bmiCreateTime;
        private String bmiStatus;
        private Integer customerId;
        private String dataComplete;
        private int heartRate;
        private int height;
        private double lowLipo;
        private long pressureCreateTime;
        private String pressureStatus;
        private int relaxationBlood;
        private int shrinkBlood;
        private long sugarCreateTime;
        private String sugarStatus;
        private String suggestion;
        private String testStatus;
        private double topLipo;
        private double totalCholesterol;
        private String totalStatus;
        private double triglyceride;
        private int weight;
        private String xinlvStatus;

        public long getBloodCreateTime() {
            return bloodCreateTime;
        }

        public void setBloodCreateTime(long bloodCreateTime) {
            this.bloodCreateTime = bloodCreateTime;
        }

        public String getBloodStatus() {
            return bloodStatus;
        }

        public void setBloodStatus(String bloodStatus) {
            this.bloodStatus = bloodStatus;
        }

        public double getBloodSugar() {
            return bloodSugar;
        }

        public void setBloodSugar(double bloodSugar) {
            this.bloodSugar = bloodSugar;
        }

        public double getBmi() {
            return bmi;
        }

        public void setBmi(double bmi) {
            this.bmi = bmi;
        }

        public long getBmiCreateTime() {
            return bmiCreateTime;
        }

        public void setBmiCreateTime(long bmiCreateTime) {
            this.bmiCreateTime = bmiCreateTime;
        }

        public String getBmiStatus() {
            return bmiStatus;
        }

        public void setBmiStatus(String bmiStatus) {
            this.bmiStatus = bmiStatus;
        }

        public Integer getCustomerId() {
            return customerId;
        }

        public void setCustomerId(Integer customerId) {
            this.customerId = customerId;
        }

        public String getDataComplete() {
            return dataComplete;
        }

        public void setDataComplete(String dataComplete) {
            this.dataComplete = dataComplete;
        }

        public int getHeartRate() {
            return heartRate;
        }

        public void setHeartRate(int heartRate) {
            this.heartRate = heartRate;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public double getLowLipo() {
            return lowLipo;
        }

        public void setLowLipo(double lowLipo) {
            this.lowLipo = lowLipo;
        }

        public long getPressureCreateTime() {
            return pressureCreateTime;
        }

        public void setPressureCreateTime(long pressureCreateTime) {
            this.pressureCreateTime = pressureCreateTime;
        }

        public String getPressureStatus() {
            return pressureStatus;
        }

        public void setPressureStatus(String pressureStatus) {
            this.pressureStatus = pressureStatus;
        }

        public int getRelaxationBlood() {
            return relaxationBlood;
        }

        public void setRelaxationBlood(int relaxationBlood) {
            this.relaxationBlood = relaxationBlood;
        }

        public int getShrinkBlood() {
            return shrinkBlood;
        }

        public void setShrinkBlood(int shrinkBlood) {
            this.shrinkBlood = shrinkBlood;
        }

        public long getSugarCreateTime() {
            return sugarCreateTime;
        }

        public void setSugarCreateTime(long sugarCreateTime) {
            this.sugarCreateTime = sugarCreateTime;
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

        public String getTestStatus() {
            return testStatus;
        }

        public void setTestStatus(String testStatus) {
            this.testStatus = testStatus;
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

        public String getTotalStatus() {
            return totalStatus;
        }

        public void setTotalStatus(String totalStatus) {
            this.totalStatus = totalStatus;
        }

        public double getTriglyceride() {
            return triglyceride;
        }

        public void setTriglyceride(double triglyceride) {
            this.triglyceride = triglyceride;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public String getXinlvStatus() {
            return xinlvStatus;
        }

        public void setXinlvStatus(String xinlvStatus) {
            this.xinlvStatus = xinlvStatus;
        }
    }
}
