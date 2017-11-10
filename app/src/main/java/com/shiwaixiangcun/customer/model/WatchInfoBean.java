package com.shiwaixiangcun.customer.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Administrator
 * @date 2017/11/7
 */

public class WatchInfoBean {


    /**
     * watchData : {"bluetoothEnable":true,"deviceStatus":"OFFLINE","frequencyHeartRate":30,"frequencyLocation":30,"hardwareId":21,"heartRateEnable":true,"intelligenceWatchId":21,"modelType":"智能手表爱牵挂S2 pro","pedometerEnable":true,"remainingPower":71,"trackEnable":false}
     */

    private WatchDataBean watchData;

    public WatchDataBean getWatchData() {
        return watchData;
    }

    public void setWatchData(WatchDataBean watchData) {
        this.watchData = watchData;
    }

    public static class WatchDataBean implements Parcelable {
        public static final Parcelable.Creator<WatchDataBean> CREATOR = new Parcelable.Creator<WatchDataBean>() {
            @Override
            public WatchDataBean createFromParcel(Parcel source) {
                return new WatchDataBean(source);
            }

            @Override
            public WatchDataBean[] newArray(int size) {
                return new WatchDataBean[size];
            }
        };
        /**
         * bluetoothEnable : true
         * deviceStatus : OFFLINE
         * frequencyHeartRate : 30
         * frequencyLocation : 30
         * hardwareId : 21
         * heartRateEnable : true
         * intelligenceWatchId : 21
         * modelType : 智能手表爱牵挂S2 pro
         * pedometerEnable : true
         * remainingPower : 71
         * trackEnable : false
         */

        private boolean familyLocation;
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

        public WatchDataBean() {
        }

        protected WatchDataBean(Parcel in) {
            this.bluetoothEnable = in.readByte() != 0;
            this.deviceStatus = in.readString();
            this.frequencyHeartRate = in.readInt();
            this.frequencyLocation = in.readInt();
            this.hardwareId = in.readInt();
            this.heartRateEnable = in.readByte() != 0;
            this.intelligenceWatchId = in.readInt();
            this.modelType = in.readString();
            this.pedometerEnable = in.readByte() != 0;
            this.remainingPower = in.readInt();
            this.trackEnable = in.readByte() != 0;
        }

        public static Creator<WatchDataBean> getCREATOR() {
            return CREATOR;
        }

        public boolean isFamilyLocation() {
            return familyLocation;
        }

        public void setFamilyLocation(boolean familyLocation) {
            this.familyLocation = familyLocation;
        }

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeByte(this.bluetoothEnable ? (byte) 1 : (byte) 0);
            dest.writeString(this.deviceStatus);
            dest.writeInt(this.frequencyHeartRate);
            dest.writeInt(this.frequencyLocation);
            dest.writeInt(this.hardwareId);
            dest.writeByte(this.heartRateEnable ? (byte) 1 : (byte) 0);
            dest.writeInt(this.intelligenceWatchId);
            dest.writeString(this.modelType);
            dest.writeByte(this.pedometerEnable ? (byte) 1 : (byte) 0);
            dest.writeInt(this.remainingPower);
            dest.writeByte(this.trackEnable ? (byte) 1 : (byte) 0);
        }
    }
}
