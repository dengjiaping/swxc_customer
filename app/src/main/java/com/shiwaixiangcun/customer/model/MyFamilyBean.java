package com.shiwaixiangcun.customer.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 */

public class MyFamilyBean implements Parcelable {


    public static final Parcelable.Creator<MyFamilyBean> CREATOR = new Parcelable.Creator<MyFamilyBean>() {
        @Override
        public MyFamilyBean createFromParcel(Parcel source) {
            return new MyFamilyBean(source);
        }

        @Override
        public MyFamilyBean[] newArray(int size) {
            return new MyFamilyBean[size];
        }
    };
    /**
     * data : [{"age":null,"avatar":"http://resource.shiwaixiangcun.cn/group1/M00/00/0C/rBKx51kZZJCAEIHpAAAcGHPVROQ635.png","customerId":263,"name":null,"phone":null,"relationship":"我","sex":null},{"age":null,"avatar":"http://resource.shiwaixiangcun.cn/group1/M00/00/5D/rBKx51nAxkCAMihmAAP95b6Zb0o73.jpeg","customerId":259,"name":"西门吹雪","phone":"15608222076","relationship":"配偶","sex":"未知"}]
     * message : 操作成功
     * responseCode : 1001
     * success : true
     */

    private String message;
    private int responseCode;
    private boolean success;
    private List<DataBean> data;

    public MyFamilyBean() {
    }

    protected MyFamilyBean(Parcel in) {
        this.message = in.readString();
        this.responseCode = in.readInt();
        this.success = in.readByte() != 0;
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
        dest.writeInt(this.responseCode);
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.data);
    }

    public static class DataBean implements Parcelable {
        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
        /**
         * age : null
         * avatar : http://resource.shiwaixiangcun.cn/group1/M00/00/0C/rBKx51kZZJCAEIHpAAAcGHPVROQ635.png
         * customerId : 263
         * name : null
         * phone : null
         * relationship : 我
         * sex : null
         */

        private String age;
        private String avatar;
        private int customerId;
        private String name;
        private String phone;
        private String relationship;
        private String sex;

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.age = in.readString();
            this.avatar = in.readString();
            this.customerId = in.readInt();
            this.name = in.readString();
            this.phone = in.readString();
            this.relationship = in.readString();
            this.sex = in.readString();
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRelationship() {
            return relationship;
        }

        public void setRelationship(String relationship) {
            this.relationship = relationship;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.age);
            dest.writeString(this.avatar);
            dest.writeInt(this.customerId);
            dest.writeString(this.name);
            dest.writeString(this.phone);
            dest.writeString(this.relationship);
            dest.writeString(this.sex);
        }
    }
}
