package com.shiwaixiangcun.customer.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/2.
 */

public class BloodFatBean implements Parcelable {


    public static final Parcelable.Creator<BloodFatBean> CREATOR = new Parcelable.Creator<BloodFatBean>() {
        @Override
        public BloodFatBean createFromParcel(Parcel source) {
            return new BloodFatBean(source);
        }

        @Override
        public BloodFatBean[] newArray(int size) {
            return new BloodFatBean[size];
        }
    };
    /**
     * elements : [{"createTime":1505697513000,"healthStatus":"NORMAL","id":255,"lowLipo":3,"lowLipoStatus":"NORMAL","source":"BackgroundSystem","statusEnum":"Zhengchang","suggestion":"请保持当前的健康生活方式,适当做一些有氧运动，如慢跑、骑自行车、跳绳、健身操等","topLipo":3,"topLipo1Status":"NORMAL","totalCholesterol":4,"totalCholesterolStatus":"NORMAL","triglyceride":1,"triglycerideStatus":"NORMAL"},{"createTime":1505697448000,"healthStatus":"WARNING","id":254,"lowLipo":3,"lowLipoStatus":"NORMAL","source":"BackgroundSystem","statusEnum":"Yichang","suggestion":"请调整饮食结构，减少肉食、蛋、乳制品等脂肪酸、胆固醇含量多的食物，戒烟戒酒，同时多参加锻炼，慢跑、骑自行车、健身操等有氧运动有助于您的血脂改善，如有身体不适，请您及时就诊","topLipo":3,"topLipo1Status":"NORMAL","totalCholesterol":4,"totalCholesterolStatus":"NORMAL","triglyceride":2,"triglycerideStatus":"NORMAL"}]
     * page : 1
     * size : 10
     * totalAmount : 2
     * totalPages : 1
     */

    private int page;
    private int size;
    private int totalAmount;
    private int totalPages;
    private List<ElementsBean> elements;

    public BloodFatBean() {
    }

    protected BloodFatBean(Parcel in) {
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
         * createTime : 1505697513000
         * healthStatus : NORMAL
         * id : 255
         * lowLipo : 3
         * lowLipoStatus : NORMAL
         * source : BackgroundSystem
         * statusEnum : Zhengchang
         * suggestion : 请保持当前的健康生活方式,适当做一些有氧运动，如慢跑、骑自行车、跳绳、健身操等
         * topLipo : 3
         * topLipo1Status : NORMAL
         * totalCholesterol : 4
         * totalCholesterolStatus : NORMAL
         * triglyceride : 1
         * triglycerideStatus : NORMAL
         */

        private long createTime;
        private String healthStatus;
        private int id;
        private int lowLipo;
        private String lowLipoStatus;
        private String source;
        private String statusEnum;
        private String suggestion;
        private int topLipo;
        private String topLipo1Status;
        private int totalCholesterol;
        private String totalCholesterolStatus;
        private int triglyceride;
        private String triglycerideStatus;

        public ElementsBean() {
        }

        protected ElementsBean(Parcel in) {
            this.createTime = in.readLong();
            this.healthStatus = in.readString();
            this.id = in.readInt();
            this.lowLipo = in.readInt();
            this.lowLipoStatus = in.readString();
            this.source = in.readString();
            this.statusEnum = in.readString();
            this.suggestion = in.readString();
            this.topLipo = in.readInt();
            this.topLipo1Status = in.readString();
            this.totalCholesterol = in.readInt();
            this.totalCholesterolStatus = in.readString();
            this.triglyceride = in.readInt();
            this.triglycerideStatus = in.readString();
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

        public int getLowLipo() {
            return lowLipo;
        }

        public void setLowLipo(int lowLipo) {
            this.lowLipo = lowLipo;
        }

        public String getLowLipoStatus() {
            return lowLipoStatus;
        }

        public void setLowLipoStatus(String lowLipoStatus) {
            this.lowLipoStatus = lowLipoStatus;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
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

        public String getTopLipo1Status() {
            return topLipo1Status;
        }

        public void setTopLipo1Status(String topLipo1Status) {
            this.topLipo1Status = topLipo1Status;
        }

        public int getTotalCholesterol() {
            return totalCholesterol;
        }

        public void setTotalCholesterol(int totalCholesterol) {
            this.totalCholesterol = totalCholesterol;
        }

        public String getTotalCholesterolStatus() {
            return totalCholesterolStatus;
        }

        public void setTotalCholesterolStatus(String totalCholesterolStatus) {
            this.totalCholesterolStatus = totalCholesterolStatus;
        }

        public int getTriglyceride() {
            return triglyceride;
        }

        public void setTriglyceride(int triglyceride) {
            this.triglyceride = triglyceride;
        }

        public String getTriglycerideStatus() {
            return triglycerideStatus;
        }

        public void setTriglycerideStatus(String triglycerideStatus) {
            this.triglycerideStatus = triglycerideStatus;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.createTime);
            dest.writeString(this.healthStatus);
            dest.writeInt(this.id);
            dest.writeInt(this.lowLipo);
            dest.writeString(this.lowLipoStatus);
            dest.writeString(this.source);
            dest.writeString(this.statusEnum);
            dest.writeString(this.suggestion);
            dest.writeInt(this.topLipo);
            dest.writeString(this.topLipo1Status);
            dest.writeInt(this.totalCholesterol);
            dest.writeString(this.totalCholesterolStatus);
            dest.writeInt(this.triglyceride);
            dest.writeString(this.triglycerideStatus);
        }
    }
}
