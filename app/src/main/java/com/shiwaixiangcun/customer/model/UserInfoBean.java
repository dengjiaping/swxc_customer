package com.shiwaixiangcun.customer.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/15.
 */

public class UserInfoBean implements Serializable {

    /**
     * data : {"avatar":{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/1B/rBKx51k-Y9CAK_9dAAANxftsOvE290.jpg","fileId":1659,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/1B/rBKx51k-Y9CAK_9dAAANxftsOvE290_150x150.jpg"},"birthday":631987200000,"companyPhone":"0851-22851555","healthAuth":false,"name":"ghhhhgh","phone":"18381049695","propertyAuth":true,"sex":"WOMAN"}
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
         * avatar : {"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/1B/rBKx51k-Y9CAK_9dAAANxftsOvE290.jpg","fileId":1659,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/1B/rBKx51k-Y9CAK_9dAAANxftsOvE290_150x150.jpg"}
         * birthday : 631987200000
         * companyPhone : 0851-22851555
         * healthAuth : false
         * name : ghhhhgh
         * phone : 18381049695
         * propertyAuth : true
         * sex : WOMAN
         */

        private AvatarBean avatar;
        private long birthday;
        private String companyPhone;
        private boolean healthAuth;
        private String name;
        private String phone;
        private boolean propertyAuth;
        private String sex;

        public AvatarBean getAvatar() {
            return avatar;
        }

        public void setAvatar(AvatarBean avatar) {
            this.avatar = avatar;
        }

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

        public static class AvatarBean {
            /**
             * accessUrl : http://resource.shiwaixiangcun.cn/group1/M00/00/1B/rBKx51k-Y9CAK_9dAAANxftsOvE290.jpg
             * fileId : 1659
             * thumbImageURL : http://resource.shiwaixiangcun.cn/group1/M00/00/1B/rBKx51k-Y9CAK_9dAAANxftsOvE290_150x150.jpg
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
