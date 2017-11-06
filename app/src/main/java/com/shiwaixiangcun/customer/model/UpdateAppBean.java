package com.shiwaixiangcun.customer.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/24.
 */

public class UpdateAppBean  implements Serializable{


    /**
     * message : 操作成功
     * data : {"latestVersion":"2.0","downloadUrl":"http://xxx.xxx","needUpdate":true}
     * success : true
     * responseCode : 1001
     */

    private String message;
    private DataBean data;
    private boolean success;
    private int responseCode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public static class DataBean {
        /**
         * latestVersion : 2.0
         * downloadUrl : http://xxx.xxx
         * needUpdate : true
         */

        private String latestVersion;
        private String downloadUrl;
        private boolean needUpdate;

        public String getLatestVersion() {
            return latestVersion;
        }

        public void setLatestVersion(String latestVersion) {
            this.latestVersion = latestVersion;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public boolean isNeedUpdate() {
            return needUpdate;
        }

        public void setNeedUpdate(boolean needUpdate) {
            this.needUpdate = needUpdate;
        }
    }
}
