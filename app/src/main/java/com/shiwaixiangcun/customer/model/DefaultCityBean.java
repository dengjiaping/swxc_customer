package com.shiwaixiangcun.customer.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/22.
 */

public class DefaultCityBean implements Serializable {


  /**
     * data : [{"cityCode":"101040100","cityName":"重庆"},{"cityCode":"101270101","cityName":"成都"},{"cityCode":"101270301","cityName":"自贡"},{"cityCode":"101271001","cityName":"泸州"},{"cityCode":"101271101","cityName":"宜宾"},{"cityCode":"101271201","cityName":"内江"}]
     * message : 操作成功
     * responseCode : 1001
     * success : true
     */

    private String message;
    private int responseCode;
    private boolean success;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * cityCode : 101040100
         * cityName : 重庆
         */

        private String cityCode;
        private String cityName;

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }
    }
}
