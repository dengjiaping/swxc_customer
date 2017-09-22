package com.shiwaixiangcun.customer.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/9/22.
 */

public class CurrentOrder {

    /**
     * data : {"auditStatus":"WaitAudit","companyId":1070,"createTime":1506045423623,"createdBy":364,"customerAddressId":61,"customerDeleted":false,"customerId":"1657","deleted":false,"deliveryWay":"由helin旗舰店发货，并提供售后服务。","discountPrice":null,"id":452,"imagePath":null,"lastModifiedBy":null,"lastModifiedDate":null,"leavingMessage":"","new":false,"number":"1000000285","orgPath":null,"payDate":null,"payWay":"None","realPay":38.88,"remark":null,"sellerId":1070,"shouldPay":38.88,"status":"WaitPay","totalPrice":38.88,"transportMoney":0,"version":0}
     * message : 操作成功
     * responseCode : 1001
     * success : true
     */

    private DataBean data;
    private String message;
    private int responseCode;
    private boolean success;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public static class DataBean {
        /**
         * auditStatus : WaitAudit
         * companyId : 1070
         * createTime : 1506045423623
         * createdBy : 364
         * customerAddressId : 61
         * customerDeleted : false
         * customerId : 1657
         * deleted : false
         * deliveryWay : 由helin旗舰店发货，并提供售后服务。
         * discountPrice : null
         * id : 452
         * imagePath : null
         * lastModifiedBy : null
         * lastModifiedDate : null
         * <p>
         * leavingMessage :
         * new : false
         * number : 1000000285
         * orgPath : null
         * payDate : null
         * payWay : None
         * realPay : 38.88
         * remark : null
         * sellerId : 1070
         * shouldPay : 38.88
         * status : WaitPay
         * totalPrice : 38.88
         * transportMoney : 0
         * version : 0
         */

        private String auditStatus;
        private int companyId;
        private long createTime;
        private int createdBy;
        private int customerAddressId;
        private boolean customerDeleted;
        private String customerId;
        private boolean deleted;
        private String deliveryWay;
        private double discountPrice;
        private int id;
        private String imagePath;
        private String lastModifiedBy;
        private String lastModifiedDate;
        private String leavingMessage;
        @SerializedName("new")
        private boolean newX;
        private String number;
        private String orgPath;
        private String payDate;
        private String payWay;
        private double realPay;
        private String remark;
        private int sellerId;
        private double shouldPay;
        private String status;
        private double totalPrice;
        private int transportMoney;
        private int version;

        public String getAuditStatus() {
            return auditStatus;
        }

        public void setAuditStatus(String auditStatus) {
            this.auditStatus = auditStatus;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
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

        public int getCustomerAddressId() {
            return customerAddressId;
        }

        public void setCustomerAddressId(int customerAddressId) {
            this.customerAddressId = customerAddressId;
        }

        public boolean isCustomerDeleted() {
            return customerDeleted;
        }

        public void setCustomerDeleted(boolean customerDeleted) {
            this.customerDeleted = customerDeleted;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public String getDeliveryWay() {
            return deliveryWay;
        }

        public void setDeliveryWay(String deliveryWay) {
            this.deliveryWay = deliveryWay;
        }

        public double getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(double discountPrice) {
            this.discountPrice = discountPrice;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
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

        public String getLeavingMessage() {
            return leavingMessage;
        }

        public void setLeavingMessage(String leavingMessage) {
            this.leavingMessage = leavingMessage;
        }

        public boolean isNewX() {
            return newX;
        }

        public void setNewX(boolean newX) {
            this.newX = newX;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getOrgPath() {
            return orgPath;
        }

        public void setOrgPath(String orgPath) {
            this.orgPath = orgPath;
        }

        public String getPayDate() {
            return payDate;
        }

        public void setPayDate(String payDate) {
            this.payDate = payDate;
        }

        public String getPayWay() {
            return payWay;
        }

        public void setPayWay(String payWay) {
            this.payWay = payWay;
        }

        public double getRealPay() {
            return realPay;
        }

        public void setRealPay(double realPay) {
            this.realPay = realPay;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getSellerId() {
            return sellerId;
        }

        public void setSellerId(int sellerId) {
            this.sellerId = sellerId;
        }

        public double getShouldPay() {
            return shouldPay;
        }

        public void setShouldPay(double shouldPay) {
            this.shouldPay = shouldPay;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public int getTransportMoney() {
            return transportMoney;
        }

        public void setTransportMoney(int transportMoney) {
            this.transportMoney = transportMoney;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }


    }
}
