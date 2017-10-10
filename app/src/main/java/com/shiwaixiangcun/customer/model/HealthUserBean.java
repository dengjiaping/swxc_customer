package com.shiwaixiangcun.customer.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/9/25.
 */

public class HealthUserBean implements Parcelable {

    public static final Parcelable.Creator<HealthUserBean> CREATOR = new Parcelable.Creator<HealthUserBean>() {
        @Override
        public HealthUserBean createFromParcel(Parcel source) {
            return new HealthUserBean(source);
        }

        @Override
        public HealthUserBean[] newArray(int size) {
            return new HealthUserBean[size];
        }
    };
    /**
     * avatar : http://resource.shiwaixiangcun.cn/group1/M00/00/0C/rBKx51kZZJCAEIHpAAAcGHPVROQ635.png
     * bloodCreateTime : 1506667893000
     * bloodStatus : WARNING
     * bloodSugar : 10.2
     * bmi : 23.9
     * bmiCreateTime : 1506667767000
     * bmiStatus : NORMAL
     * customerId : 263
     * heartRate : 200
     * heartRateStatus : DANGER
     * heartRateTime : 1506667806000
     * height : 170
     * lowLipo : 7
     * name :
     * pressureCreateTime : 1506651795000
     * pressureStatus : NORMAL
     * relationship : 我
     * relaxationBlood : 90
     * shrinkBlood : 120
     * sugarCreateTime : 1506667851000
     * sugarStatus : DANGER
     * suggestion : 健康数据严重异常，请咨询医生
     * testStatus : KF
     * topLipo : 7
     * totalCholesterol : 7
     * totalStatus : DANGER
     * triglyceride : 7
     * weight : 69
     */

    private String avatar;
    private long bloodCreateTime;
    private String bloodStatus;
    private double bloodSugar;
    private double bmi;
    private long bmiCreateTime;
    private String bmiStatus;
    private int customerId;
    private int heartRate;
    private String heartRateStatus;
    private long heartRateTime;
    private int height;
    private double lowLipo;
    private String name;
    private long pressureCreateTime;
    private String pressureStatus;
    private String relationship;
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
    private double weight;

    public HealthUserBean() {
    }

    protected HealthUserBean(Parcel in) {
        this.avatar = in.readString();
        this.bloodCreateTime = in.readLong();
        this.bloodStatus = in.readString();
        this.bloodSugar = in.readDouble();
        this.bmi = in.readDouble();
        this.bmiCreateTime = in.readLong();
        this.bmiStatus = in.readString();
        this.customerId = in.readInt();
        this.heartRate = in.readInt();
        this.heartRateStatus = in.readString();
        this.heartRateTime = in.readLong();
        this.height = in.readInt();
        this.lowLipo = in.readInt();
        this.name = in.readString();
        this.pressureCreateTime = in.readLong();
        this.pressureStatus = in.readString();
        this.relationship = in.readString();
        this.relaxationBlood = in.readInt();
        this.shrinkBlood = in.readInt();
        this.sugarCreateTime = in.readLong();
        this.sugarStatus = in.readString();
        this.suggestion = in.readString();
        this.testStatus = in.readString();
        this.topLipo = in.readInt();
        this.totalCholesterol = in.readInt();
        this.totalStatus = in.readString();
        this.triglyceride = in.readInt();
        this.weight = in.readInt();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public String getHeartRateStatus() {
        return heartRateStatus;
    }

    public void setHeartRateStatus(String heartRateStatus) {
        this.heartRateStatus = heartRateStatus;
    }

    public long getHeartRateTime() {
        return heartRateTime;
    }

    public void setHeartRateTime(long heartRateTime) {
        this.heartRateTime = heartRateTime;
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

    public void setLowLipo(int lowLipo) {
        this.lowLipo = lowLipo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
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

    public void setTopLipo(int topLipo) {
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

    public void setTriglyceride(int triglyceride) {
        this.triglyceride = triglyceride;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.avatar);
        dest.writeLong(this.bloodCreateTime);
        dest.writeString(this.bloodStatus);
        dest.writeDouble(this.bloodSugar);
        dest.writeDouble(this.bmi);
        dest.writeLong(this.bmiCreateTime);
        dest.writeString(this.bmiStatus);
        dest.writeInt(this.customerId);
        dest.writeInt(this.heartRate);
        dest.writeString(this.heartRateStatus);
        dest.writeLong(this.heartRateTime);
        dest.writeInt(this.height);
        dest.writeDouble(this.lowLipo);
        dest.writeString(this.name);
        dest.writeLong(this.pressureCreateTime);
        dest.writeString(this.pressureStatus);
        dest.writeString(this.relationship);
        dest.writeInt(this.relaxationBlood);
        dest.writeInt(this.shrinkBlood);
        dest.writeLong(this.sugarCreateTime);
        dest.writeString(this.sugarStatus);
        dest.writeString(this.suggestion);
        dest.writeString(this.testStatus);
        dest.writeDouble(this.topLipo);
        dest.writeDouble(this.totalCholesterol);
        dest.writeString(this.totalStatus);
        dest.writeDouble(this.triglyceride);
        dest.writeDouble(this.weight);
    }
}