package com.shiwaixiangcun.customer.model;

/**
 * Created by Administrator on 2017/11/7.
 */

public class WatchInfoBean {


    /**
     * bluetoothEnable : false
     * deviceStatus : ONLINE
     * frequencyHeartRate : 30
     * frequencyLocation : 30
     * hardwareId : 8
     * heartRateEnable : true
     * intelligenceWatchId : 8
     * modelType : 智能手表爱牵挂S2 pro
     * pedometerEnable : true
     * remainingPower : 76
     * trackEnable : true
     */

    private boolean bluetoothEnable;
    private String deviceStatus;
    private int frequencyHeartRate;
    private int frequencyLocation;
    private int hardwareId;
    private boolean heartRateEnable;
    private int intelligenceWatchId;
    private String modelType;
    private boolean pedometerEnable;
    private int remainingPower;
    private boolean trackEnable;

    public boolean isBluetoothEnable() {
        return bluetoothEnable;
    }

    public void setBluetoothEnable(boolean bluetoothEnable) {
        this.bluetoothEnable = bluetoothEnable;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public int getFrequencyHeartRate() {
        return frequencyHeartRate;
    }

    public void setFrequencyHeartRate(int frequencyHeartRate) {
        this.frequencyHeartRate = frequencyHeartRate;
    }

    public int getFrequencyLocation() {
        return frequencyLocation;
    }

    public void setFrequencyLocation(int frequencyLocation) {
        this.frequencyLocation = frequencyLocation;
    }

    public int getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(int hardwareId) {
        this.hardwareId = hardwareId;
    }

    public boolean isHeartRateEnable() {
        return heartRateEnable;
    }

    public void setHeartRateEnable(boolean heartRateEnable) {
        this.heartRateEnable = heartRateEnable;
    }

    public int getIntelligenceWatchId() {
        return intelligenceWatchId;
    }

    public void setIntelligenceWatchId(int intelligenceWatchId) {
        this.intelligenceWatchId = intelligenceWatchId;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public boolean isPedometerEnable() {
        return pedometerEnable;
    }

    public void setPedometerEnable(boolean pedometerEnable) {
        this.pedometerEnable = pedometerEnable;
    }

    public int getRemainingPower() {
        return remainingPower;
    }

    public void setRemainingPower(int remainingPower) {
        this.remainingPower = remainingPower;
    }

    public boolean isTrackEnable() {
        return trackEnable;
    }

    public void setTrackEnable(boolean trackEnable) {
        this.trackEnable = trackEnable;
    }
}
