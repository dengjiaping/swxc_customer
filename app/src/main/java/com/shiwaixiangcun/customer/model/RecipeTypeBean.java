package com.shiwaixiangcun.customer.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/10/12.
 */

public class RecipeTypeBean {


    /**
     * companyId : null
     * createTime : 1505960797000
     * createdBy : 1
     * deleted : false
     * id : 258
     * lastModifiedBy : null
     * lastModifiedDate : null
     * name : 高血压食谱
     * new : false
     * orgPath : null
     * remark : null
     * type : 100020
     * value : pressure_diet
     * version : 0
     */

    private String companyId;
    private long createTime;
    private int createdBy;
    private boolean deleted;
    private int id;
    private String lastModifiedBy;
    private String lastModifiedDate;
    private String name;
    @SerializedName("new")
    private boolean newX;
    private String orgPath;
    private String remark;
    private String type;
    private String value;
    private int version;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNewX() {
        return newX;
    }

    public void setNewX(boolean newX) {
        this.newX = newX;
    }

    public String getOrgPath() {
        return orgPath;
    }

    public void setOrgPath(String orgPath) {
        this.orgPath = orgPath;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
