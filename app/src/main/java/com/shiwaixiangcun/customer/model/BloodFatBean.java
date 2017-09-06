package com.shiwaixiangcun.customer.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/2.
 */

public class BloodFatBean implements Serializable {


    /**
     * createTime : 1496366856000
     * healthStatus : NORMAL
     * id : 133
     * lowLipo : 2
     * source : null
     * statusEnum : Zhengchang
     * suggestion : 您的血脂正常，请保持当前的健康生活方式，适当做一些有氧运动，如慢跑、骑自行车、跳绳、健身操等
     * topLipo : 9
     * totalCholesterol : 5
     * triglyceride : 1.5
     */

    private long createTime;
    private String healthStatus;
    private int id;
    private int lowLipo;
    private Object source;
    private String statusEnum;
    private String suggestion;
    private int topLipo;
    private int totalCholesterol;
    private double triglyceride;

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

    public int getLowLipo() {
        return lowLipo;
    }

    public void setLowLipo(int lowLipo) {
        this.lowLipo = lowLipo;
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

    public int getTopLipo() {
        return topLipo;
    }

    public void setTopLipo(int topLipo) {
        this.topLipo = topLipo;
    }

    public int getTotalCholesterol() {
        return totalCholesterol;
    }

    public void setTotalCholesterol(int totalCholesterol) {
        this.totalCholesterol = totalCholesterol;
    }

    public double getTriglyceride() {
        return triglyceride;
    }

    public void setTriglyceride(double triglyceride) {
        this.triglyceride = triglyceride;
    }
}
