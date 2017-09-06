package com.shiwaixiangcun.customer.model;

/**
 * Created by Administrator on 2017/5/24.
 */
public class User {


    /**
     * data : {"birthday":null,"companyPhone":null,"hasBindHouse":true,"name":null,"phone":"18381049695","sex":"NONE","thumbImage":{"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/0F/rBKx51keZgSACAm7AAAnY-8FwQY008.jpg","fileId":931,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/0F/rBKx51keZgSACAm7AAAnY-8FwQY008_150x150.jpg"}}
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
         * birthday : null
         * companyPhone : null
         * hasBindHouse : true
         * name : null
         * phone : 18381049695
         * sex : NONE
         * thumbImage : {"accessUrl":"http://resource.shiwaixiangcun.cn/group1/M00/00/0F/rBKx51keZgSACAm7AAAnY-8FwQY008.jpg","fileId":931,"thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/0F/rBKx51keZgSACAm7AAAnY-8FwQY008_150x150.jpg"}
         */

        private String birthday;
        private String companyPhone;
        private boolean hasBindHouse;
        private String name;
        private String phone;
        private String sex;
        private ThumbImageBean thumbImage;

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getCompanyPhone() {
            return companyPhone;
        }

        public void setCompanyPhone(String companyPhone) {
            this.companyPhone = companyPhone;
        }

        public boolean isHasBindHouse() {
            return hasBindHouse;
        }

        public void setHasBindHouse(boolean hasBindHouse) {
            this.hasBindHouse = hasBindHouse;
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
             * accessUrl : http://resource.shiwaixiangcun.cn/group1/M00/00/0F/rBKx51keZgSACAm7AAAnY-8FwQY008.jpg
             * fileId : 931
             * thumbImageURL : http://resource.shiwaixiangcun.cn/group1/M00/00/0F/rBKx51keZgSACAm7AAAnY-8FwQY008_150x150.jpg
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
