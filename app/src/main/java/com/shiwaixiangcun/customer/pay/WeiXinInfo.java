package com.shiwaixiangcun.customer.pay;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/9/22.
 */

public class WeiXinInfo {

    /**
     * payWay : WeiXin
     * weiXinResponse : {"appid":"wx4811bcb64cb3e434","noncestr":"1506065713948","package":"Sign=WXPay","partnerid":"1488983952","prepayid":"wx20170922153513bdf9b72ac80158202515","sign":"b490eee7d50bc8ecec495584ff2819aa","timestamp":"1506065713"}
     * zhiFuBaoResponse : null
     */

    private String payWay;
    private WeiXinResponseBean weiXinResponse;
    private Object zhiFuBaoResponse;

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public WeiXinResponseBean getWeiXinResponse() {
        return weiXinResponse;
    }

    public void setWeiXinResponse(WeiXinResponseBean weiXinResponse) {
        this.weiXinResponse = weiXinResponse;
    }

    public Object getZhiFuBaoResponse() {
        return zhiFuBaoResponse;
    }

    public void setZhiFuBaoResponse(Object zhiFuBaoResponse) {
        this.zhiFuBaoResponse = zhiFuBaoResponse;
    }

    public static class WeiXinResponseBean {
        /**
         * appid : wx4811bcb64cb3e434
         * noncestr : 1506065713948
         * package : Sign=WXPay
         * partnerid : 1488983952
         * prepayid : wx20170922153513bdf9b72ac80158202515
         * sign : b490eee7d50bc8ecec495584ff2819aa
         * timestamp : 1506065713
         */

        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private String sign;
        private String timestamp;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
