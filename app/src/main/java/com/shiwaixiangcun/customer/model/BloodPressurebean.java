package com.shiwaixiangcun.customer.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/2.
 */

public class BloodPressurebean implements Serializable {


    /**
     * createTime : 1496626120000
     * healthStatus : NORMAL
     * heartRate : 90
     * id : 416
     * relaxationBlood : 90
     * shrinkBlood : 100
     * statusEnum : Zhengchang
     * suggestion : 您的血压正常，请继续保持当前的健康生活方式，并定期测量，祝您生活愉快哦！
     */

    private long createTime;
    private String healthStatus;
    private int heartRate;
    private int id;
    private int relaxationBlood;
    private int shrinkBlood;
    private String statusEnum;
    private String suggestion;

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
