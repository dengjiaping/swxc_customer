package com.shiwaixiangcun.customer.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/2.
 */

public class BloodPressureDataBean implements Serializable {


    /**
     * createTime : 1496366809000
     * customerId : 146
     * healthStatus : WARNING
     * heartRate : 102
     * id : 406
     * relaxationBlood : 83
     * shrinkBlood : 122
     * source : null
     * statusEnum : Zhengchang
     */

    private long createTime;
    private int customerId;
    private String healthStatus;
    private int heartRate;
    private int id;
    private int relaxationBlood;
    private int shrinkBlood;
    private Object source;
    private String statusEnum;

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
}
