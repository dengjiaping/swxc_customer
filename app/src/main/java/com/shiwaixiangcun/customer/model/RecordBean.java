package com.shiwaixiangcun.customer.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/15.
 */

public class RecordBean implements Serializable {

    /**
     * data : {"elements":[{"accept":"DEFAULT","categoryId":128,"categoryName":"在线报修","content":"gggg","createTime":1497260017000,"createdBy":119,"dataSource":"PROPERTY_CUSTOMER_APP","id":536,"images":null,"launcherId":1587,"launcherMobile":"18381049695","launcherName":"钟英杰","number":"00216","operatorType":"CUSTOMER","orgId":8,"orgName":"管家部","priorityLevel":"NORMAL","staffId":140,"staffName":"老司机","status":"ACCEPTED","timeout":false,"title":"钟英杰 18381049695 在线报修","updateTime":1497260017000}],"page":1,"size":10,"totalAmount":1,"totalPages":1}
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
         * elements : [{"accept":"DEFAULT","categoryId":128,"categoryName":"在线报修","content":"gggg","createTime":1497260017000,"createdBy":119,"dataSource":"PROPERTY_CUSTOMER_APP","id":536,"images":null,"launcherId":1587,"launcherMobile":"18381049695","launcherName":"钟英杰","number":"00216","operatorType":"CUSTOMER","orgId":8,"orgName":"管家部","priorityLevel":"NORMAL","staffId":140,"staffName":"老司机","status":"ACCEPTED","timeout":false,"title":"钟英杰 18381049695 在线报修","updateTime":1497260017000}]
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

        public static class ElementsBean {
            /**
             * accept : DEFAULT
             * categoryId : 128
             * categoryName : 在线报修
             * content : gggg
             * createTime : 1497260017000
             * createdBy : 119
             * dataSource : PROPERTY_CUSTOMER_APP
             * id : 536
             * images : null
             * launcherId : 1587
             * launcherMobile : 18381049695
             * launcherName : 钟英杰
             * number : 00216
             * operatorType : CUSTOMER
             * orgId : 8
             * orgName : 管家部
             * priorityLevel : NORMAL
             * staffId : 140
             * staffName : 老司机
             * status : ACCEPTED
             * timeout : false
             * title : 钟英杰 18381049695 在线报修
             * updateTime : 1497260017000
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
            private int staffId;
            private String staffName;
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

            public int getStaffId() {
                return staffId;
            }

            public void setStaffId(int staffId) {
                this.staffId = staffId;
            }

            public String getStaffName() {
                return staffName;
            }

            public void setStaffName(String staffName) {
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
    }
}
