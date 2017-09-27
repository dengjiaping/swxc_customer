package com.shiwaixiangcun.customer.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/2.
 */

public class WeightBean implements Parcelable {

    public static final Parcelable.Creator<WeightBean> CREATOR = new Parcelable.Creator<WeightBean>() {
        @Override
        public WeightBean createFromParcel(Parcel source) {
            return new WeightBean(source);
        }

        @Override
        public WeightBean[] newArray(int size) {
            return new WeightBean[size];
        }
    };
    /**
     * elements : [{"bmi":20.8,"createTime":1505697412000,"healthStatus":"NORMAL","height":170,"id":351,"statusEnum":"Zhengchang","suggestion":"请继续保持当前健康生活习惯","weight":60,"weightDream":"53.5-69.1"}]
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

    public WeightBean() {
    }

    protected WeightBean(Parcel in) {
        this.page = in.readInt();
        this.size = in.readInt();
        this.totalAmount = in.readInt();
        this.totalPages = in.readInt();
        this.elements = new ArrayList<ElementsBean>();
        in.readList(this.elements, ElementsBean.class.getClassLoader());
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
        dest.writeList(this.elements);
    }

    public static class ElementsBean implements Parcelable {
        public static final Creator<ElementsBean> CREATOR = new Creator<ElementsBean>() {
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
         * bmi : 20.8
         * createTime : 1505697412000
         * healthStatus : NORMAL
         * height : 170
         * id : 351
         * statusEnum : Zhengchang
         * suggestion : 请继续保持当前健康生活习惯
         * weight : 60
         * weightDream : 53.5-69.1
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

        public ElementsBean() {
        }

        protected ElementsBean(Parcel in) {
            this.bmi = in.readDouble();
            this.createTime = in.readLong();
            this.healthStatus = in.readString();
            this.height = in.readInt();
            this.id = in.readInt();
            this.statusEnum = in.readString();
            this.suggestion = in.readString();
            this.weight = in.readInt();
            this.weightDream = in.readString();
        }

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(this.bmi);
            dest.writeLong(this.createTime);
            dest.writeString(this.healthStatus);
            dest.writeInt(this.height);
            dest.writeInt(this.id);
            dest.writeString(this.statusEnum);
            dest.writeString(this.suggestion);
            dest.writeInt(this.weight);
            dest.writeString(this.weightDream);
        }
    }
}