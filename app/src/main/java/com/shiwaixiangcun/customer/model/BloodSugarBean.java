package com.shiwaixiangcun.customer.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2017/6/2.
 */

public class BloodSugarBean implements Parcelable {

    public static final Parcelable.Creator<BloodSugarBean> CREATOR = new Parcelable.Creator<BloodSugarBean>() {
        @Override
        public BloodSugarBean createFromParcel(Parcel source) {
            return new BloodSugarBean(source);
        }

        @Override
        public BloodSugarBean[] newArray(int size) {
            return new BloodSugarBean[size];
        }
    };
    /**
     * elements : [{"bloodSugar":5.6,"createTime":1505697429000,"healthStatus":"NORMAL","id":332,"statusEnum":"Zhengchang","sugarStatus":"KF","suggestion":"请继续保持当前的健康生活方式，可适度运动，如散步、慢跑等"}]
     * page : 1
     * size : 10
     * totalAmount : 1
     * totalPages : 1
     */

    private int page;
    private int size;
    private int totalAmount;
    private int totalPages;
    private List<ElementsBean> elements;

    public BloodSugarBean() {
    }

    protected BloodSugarBean(Parcel in) {
        this.page = in.readInt();
        this.size = in.readInt();
        this.totalAmount = in.readInt();
        this.totalPages = in.readInt();
        this.elements = in.createTypedArrayList(ElementsBean.CREATOR);
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
        dest.writeInt(this.size);
        dest.writeInt(this.totalAmount);
        dest.writeInt(this.totalPages);
        dest.writeTypedList(this.elements);
    }

    public static class ElementsBean implements Parcelable {

        public static final Parcelable.Creator<ElementsBean> CREATOR = new Parcelable.Creator<ElementsBean>() {
            @Override
            public ElementsBean createFromParcel(Parcel source) {
                return new ElementsBean(source);
            }

            @Override
            public ElementsBean[] newArray(int size) {
                return new ElementsBean[size];
            }
        };
        /**
         * bloodSugar : 5.6
         * createTime : 1505697429000
         * healthStatus : NORMAL
         * id : 332
         * statusEnum : Zhengchang
         * sugarStatus : KF
         * suggestion : 请继续保持当前的健康生活方式，可适度运动，如散步、慢跑等
         */

        private double bloodSugar;
        private long createTime;
        private String healthStatus;
        private int id;
        private String statusEnum;
        private String sugarStatus;
        private String suggestion;

        public ElementsBean() {
        }

        protected ElementsBean(Parcel in) {
            this.bloodSugar = in.readDouble();
            this.createTime = in.readLong();
            this.healthStatus = in.readString();
            this.id = in.readInt();
            this.statusEnum = in.readString();
            this.sugarStatus = in.readString();
            this.suggestion = in.readString();
        }

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(this.bloodSugar);
            dest.writeLong(this.createTime);
            dest.writeString(this.healthStatus);
            dest.writeInt(this.id);
            dest.writeString(this.statusEnum);
            dest.writeString(this.sugarStatus);
            dest.writeString(this.suggestion);
        }
    }
}