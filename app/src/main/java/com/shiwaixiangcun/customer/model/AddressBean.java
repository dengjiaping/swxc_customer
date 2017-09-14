package com.shiwaixiangcun.customer.model;/**
 * Author:Administrator
 * Date:  2017/9/13
 * Desc： eg
 */

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/9/13.
 */

public class AddressBean {

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
    private Object lastModifiedBy;
    private Object lastModifiedDate;
    @SerializedName("new")
    private boolean newX;
    private Object orgPath;
    private int version;

    public AddressBean() {
    }

    public AddressBean(Integer companyId, String deliveryAddress, String deliveryName, String deliveryPhone) {
        this.companyId = companyId;
        this.deliveryAddress = deliveryAddress;
        this.deliveryName = deliveryName;
        this.deliveryPhone = deliveryPhone;
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

    public Object getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Object lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Object getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Object lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public boolean isNewX() {
        return newX;
    }

    public void setNewX(boolean newX) {
        this.newX = newX;
    }

    public Object getOrgPath() {
        return orgPath;
    }

    public void setOrgPath(Object orgPath) {
        this.orgPath = orgPath;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
