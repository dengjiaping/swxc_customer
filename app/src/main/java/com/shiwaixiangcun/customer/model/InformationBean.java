package com.shiwaixiangcun.customer.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/6.
 */

public class InformationBean implements Serializable {

    /**
     * data : {"birthday":631123200000,"companyPhone":"0851-22851555","healthAuth":false,"name":"ffgggg","phone":"18381049695","propertyAuth":true,"sex":"WOMAN","thumbImage":{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/19/rBKx51k2b4CAXxpEAAAIY2E2F2Q610.jpg","fileId":1492,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/19/rBKx51k2b4CAXxpEAAAIY2E2F2Q610_150x150.jpg"}}
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
         * birthday : 631123200000
         * companyPhone : 0851-22851555
         * healthAuth : false
         * name : ffgggg
         * phone : 18381049695
         * propertyAuth : true
         * sex : WOMAN
         * thumbImage : {"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/19/rBKx51k2b4CAXxpEAAAIY2E2F2Q610.jpg","fileId":1492,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/19/rBKx51k2b4CAXxpEAAAIY2E2F2Q610_150x150.jpg"}
         */

        private long birthday;
        private String companyPhone;
        private boolean healthAuth;
        private String name;
        private String phone;
        private boolean propertyAuth;
        private String sex;
        private ThumbImageBean thumbImage;

        public long getBirthday() {
            return birthday;
        }

        public void setBirthday(long birthday) {
            this.birthday = birthday;
        }

        public String getCompanyPhone() {
            return companyPhone;
        }

        public void setCompanyPhone(String companyPhone) {
            this.companyPhone = companyPhone;
        }

        public boolean isHealthAuth() {
            return healthAuth;
        }

        public void setHealthAuth(boolean healthAuth) {
            this.healthAuth = healthAuth;
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

        public boolean isPropertyAuth() {
            return propertyAuth;
        }

        public void setPropertyAuth(boolean propertyAuth) {
            this.propertyAuth = propertyAuth;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public ThumbImageBean getThumbImage() {
            return thumbImage;
        }

        public void setThumbImage(ThumbImageBean thumbImage) {
            this.thumbImage = thumbImage;
        }

        public static class ThumbImageBean {
            /**
             * accessUrl : http://resource.shiwaixiangcun.cn/group1/M00/00/19/rBKx51k2b4CAXxpEAAAIY2E2F2Q610.jpg
             * fileId : 1492
             * thumbImageURL : http://resource.shiwaixiangcun.cn/group1/M00/00/19/rBKx51k2b4CAXxpEAAAIY2E2F2Q610_150x150.jpg
             */

            private String accessUrl;
            private int fileId;
            private String thumbImageURL;

            public String getAccessUrl() {
                return accessUrl;
            }

            public void setAccessUrl(String accessUrl) {
                this.accessUrl = accessUrl;
            }

            public int getFileId() {
                return fileId;
            }

            public void setFileId(int fileId) {
                this.fileId = fileId;
            }

            public String getThumbImageURL() {
                return thumbImageURL;
            }

            public void setThumbImageURL(String thumbImageURL) {
                this.thumbImageURL = thumbImageURL;
            }
        }
    }
}
