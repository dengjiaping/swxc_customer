package com.shiwaixiangcun.customer.model;/**
 * Author:Administrator
 * Date:  2017/9/13
 * Desc： eg
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/9/13.
 */

public class AddressBean implements Parcelable {

    public static final Parcelable.Creator<AddressBean> CREATOR = new Parcelable.Creator<AddressBean>() {
        @Override
        public AddressBean createFromParcel(Parcel source) {
            return new AddressBean(source);
        }

        @Override
        public AddressBean[] newArray(int size) {
            return new AddressBean[size];
        }
    };
    /**
     * companyId : null
     * createTime : 1505356635000
     * createdBy : 364
     * customerId : 1657
     * defaulted : false
     * deleted : false
     * deliveryAddress : 滴滴滴
     * deliveryName : 滴滴滴
     * deliveryPhone : 滴滴滴
     * id : 19
     * lastModifiedBy : null
     * lastModifiedDate : null
     * new : false
     * orgPath : null
     * version : 0
     */

    private Integer companyId;
    private long createTime;
    private Integer createdBy;
    private Integer customerId;
    private boolean defaulted;
    private boolean deleted;
    private String deliveryAddress;
    private String deliveryName;
    private String deliveryPhone;
    private Integer id;
    private String lastModifiedBy;
    private String lastModifiedDate;
    @SerializedName("new")
    private String newX;
    private String orgPath;
    private int version;

    public AddressBean() {
    }

    public AddressBean(Integer companyId, String deliveryAddress, String deliveryName, String deliveryPhone) {
        this.companyId = companyId;
        this.deliveryAddress = deliveryAddress;
        this.deliveryName = deliveryName;
        this.deliveryPhone = deliveryPhone;
    }

    protected AddressBean(Parcel in) {
        this.companyId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.createTime = in.readLong();
        this.createdBy = (Integer) in.readValue(Integer.class.getClassLoader());
        this.customerId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.defaulted = in.readByte() != 0;
        this.deleted = in.readByte() != 0;
        this.deliveryAddress = in.readString();
        this.deliveryName = in.readString();
        this.deliveryPhone = in.readString();
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.lastModifiedBy = in.readString();
        this.lastModifiedDate = in.readString();
        this.newX = in.readString();
        this.orgPath = in.readString();
        this.version = in.readInt();
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public boolean isDefaulted() {
        return defaulted;
    }

    public void setDefaulted(boolean defaulted) {
        this.defaulted = defaulted;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getDeliveryPhone() {
        return deliveryPhone;
    }

    public void setDeliveryPhone(String deliveryPhone) {
        this.deliveryPhone = deliveryPhone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getNewX() {
        return newX;
    }

    public void setNewX(String newX) {
        this.newX = newX;
    }

    public String getOrgPath() {
        return orgPath;
    }

    public void setOrgPath(String orgPath) {
        this.orgPath = orgPath;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.companyId);
        dest.writeLong(this.createTime);
        dest.writeValue(this.createdBy);
        dest.writeValue(this.customerId);
        dest.writeByte(this.defaulted ? (byte) 1 : (byte) 0);
        dest.writeByte(this.deleted ? (byte) 1 : (byte) 0);
        dest.writeString(this.deliveryAddress);
        dest.writeString(this.deliveryName);
        dest.writeString(this.deliveryPhone);
        dest.writeValue(this.id);
        dest.writeString(this.lastModifiedBy);
        dest.writeString(this.lastModifiedDate);
        dest.writeString(this.newX);
        dest.writeString(this.orgPath);
        dest.writeInt(this.version);
    }
}
