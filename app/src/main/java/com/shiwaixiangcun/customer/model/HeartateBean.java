package com.shiwaixiangcun.customer.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/2.
 */

public class HeartateBean implements Serializable {


    /**
     * createTime : 1496626057000
     * healthStatus : NORMAL
     * heartRate : 90
     * id : 415
     * relaxationBlood : 90
     * shrinkBlood : 100
     * statusEnum : Zhengchang
     * suggestion : 心率是指正常人安静状态下每分钟心跳的次数，也叫安静心率，一般为60～100次/分，可因年龄、性别或其他生理因素产生个体差异。运动时，心率会加快
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
