package com.shiwaixiangcun.customer.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/30.
 */
public class BloodPressureBean implements Parcelable {

    public static final Parcelable.Creator<BloodPressureBean> CREATOR = new Parcelable.Creator<BloodPressureBean>() {
        @Override
        public BloodPressureBean createFromParcel(Parcel source) {
            return new BloodPressureBean(source);
        }

        @Override
        public BloodPressureBean[] newArray(int size) {
            return new BloodPressureBean[size];
        }
    };
    /**
     * elements : [{"createTime":1505697417000,"customerId":259,"healthStatus":"NORMAL","id":2278,"relaxationBlood":90,"relaxationBloodStatus":"NORMAL","shrinkBlood":100,"shrinkBloodStatus":"NORMAL","statusEnum":"Zhengchang","suggestion":"请继续保持当前的健康生活方式，并定期测量，祝您生活愉快哦！"}]
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

    public BloodPressureBean() {
    }

    protected BloodPressureBean(Parcel in) {
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
         * createTime : 1505697417000
         * customerId : 259
         * healthStatus : NORMAL
         * id : 2278
         * relaxationBlood : 90
         * relaxationBloodStatus : NORMAL
         * shrinkBlood : 100
         * shrinkBloodStatus : NORMAL
         * statusEnum : Zhengchang
         * suggestion : 请继续保持当前的健康生活方式，并定期测量，祝您生活愉快哦！
         */

        private long createTime;
        private int customerId;
        private String healthStatus;
        private int id;
        private int relaxationBlood;
        private String relaxationBloodStatus;
        private int shrinkBlood;
        private String shrinkBloodStatus;
        private String statusEnum;
        private String suggestion;

        public ElementsBean() {
        }

        protected ElementsBean(Parcel in) {
            this.createTime = in.readLong();
            this.customerId = in.readInt();
            this.healthStatus = in.readString();
            this.id = in.readInt();
            this.relaxationBlood = in.readInt();
            this.relaxationBloodStatus = in.readString();
            this.shrinkBlood = in.readInt();
            this.shrinkBloodStatus = in.readString();
            this.statusEnum = in.readString();
            this.suggestion = in.readString();
        }

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

        public String getRelaxationBloodStatus() {
            return relaxationBloodStatus;
        }

        public void setRelaxationBloodStatus(String relaxationBloodStatus) {
            this.relaxationBloodStatus = relaxationBloodStatus;
        }

        public int getShrinkBlood() {
            return shrinkBlood;
        }

        public void setShrinkBlood(int shrinkBlood) {
            this.shrinkBlood = shrinkBlood;
        }

        public String getShrinkBloodStatus() {
            return shrinkBloodStatus;
        }

        public void setShrinkBloodStatus(String shrinkBloodStatus) {
            this.shrinkBloodStatus = shrinkBloodStatus;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.createTime);
            dest.writeInt(this.customerId);
            dest.writeString(this.healthStatus);
            dest.writeInt(this.id);
            dest.writeInt(this.relaxationBlood);
            dest.writeString(this.relaxationBloodStatus);
            dest.writeInt(this.shrinkBlood);
            dest.writeString(this.shrinkBloodStatus);
            dest.writeString(this.statusEnum);
            dest.writeString(this.suggestion);
        }
    }
}
