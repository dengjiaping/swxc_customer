package com.shiwaixiangcun.customer.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/26.
 */

public class SubmitRecordsBean implements Serializable {


    /**
     * accept : DEFAULT
     * categoryId : 128
     * categoryName : 在线报修
     * content : yhhgggg
     * createTime : 1497515260000
     * createdBy : 107
     * dataSource : PROPERTY_CUSTOMER_APP
     * id : 29
     * images : null
     * launcherId : 1587
     * launcherMobile : 18381049695
     * launcherName : 钟英杰
     * number : 00014
     * operatorType : CUSTOMER
     * orgId : 2
     * orgName : 天鹅堡物业中心
     * priorityLevel : NORMAL
     * staffId : null
     * staffName : null
     * status : UNTREATED
     * timeout : false
     * title : 钟英杰 18381049695 在线报修
     * updateTime : 1497515260000
     */

    private String accept;
    private int categoryId;
    private String categoryName;
    private String content;
    private long createTime;
    private int createdBy;
    private String dataSource;
    private int id;
    private Object images;
    private int launcherId;
    private String launcherMobile;
    private String launcherName;
    private String number;
    private String operatorType;
    private int orgId;
    private String orgName;
    private String priorityLevel;
    private Object staffId;
    private Object staffName;
    private String status;
    private boolean timeout;
    private String title;
    private long updateTime;

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getImages() {
        return images;
    }

    public void setImages(Object images) {
        this.images = images;
    }

    public int getLauncherId() {
        return launcherId;
    }

    public void setLauncherId(int launcherId) {
        this.launcherId = launcherId;
    }

    public String getLauncherMobile() {
        return launcherMobile;
    }

    public void setLauncherMobile(String launcherMobile) {
        this.launcherMobile = launcherMobile;
    }

    public String getLauncherName() {
        return launcherName;
    }

    public void setLauncherName(String launcherName) {
        this.launcherName = launcherName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public Object getStaffId() {
        return staffId;
    }

    public void setStaffId(Object staffId) {
        this.staffId = staffId;
    }

    public Object getStaffName() {
        return staffName;
    }

    public void setStaffName(Object staffName) {
        this.staffName = staffName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isTimeout() {
        return timeout;
    }

    public void setTimeout(boolean timeout) {
        this.timeout = timeout;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
