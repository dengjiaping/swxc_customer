package com.shiwaixiangcun.customer.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/2.
 */

public class WeightBean implements Serializable {


    /**
     * bmi : 22.5
     * createTime : 1496626101000
     * healthStatus : NORMAL
     * height : 175
     * id : 174
     * statusEnum : Zhengchang
     * suggestion : 您的体重身高指数正常，请继续保持当前健康生活习惯
     * weight : 69
     * weightDream : 56.7-73.2
     */

    private double bmi;
    private long createTime;
    private String healthStatus;
    private int height;
    private int id;
    private String statusEnum;
    private String suggestion;
    private int weight;
    private String weightDream;

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getWeightDream() {
        return weightDream;
    }

    public void setWeightDream(String weightDream) {
        this.weightDream = weightDream;
    }
}
