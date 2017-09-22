package com.shiwaixiangcun.customer.pay;

/**
 * Created by Administrator on 2017/9/22.
 */

public class AliInfo {


    /**
     * payWay : ZhiFuBao
     * weiXinResponse : null
     * zhiFuBaoResponse : alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2017091008651455&biz_content=%7B%22out_trade_no%22%3A%221000000290%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E7%88%B1%E7%89%B5%E6%8C%82K1%E5%A4%9A%E7%94%A8%E9%80%94%E5%BE%AE%E5%9E%8B%E5%AE%9A%E4%BD%8D%E5%99%A8%22%2C%22timeout_express%22%3A%221h%22%2C%22total_amount%22%3A%22228.0%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.payZhiFuBao&notify_url=http%3A%2F%2Fmk.shiwaixiangcun.cn%2Fmi%2Fpay%2Fresult%2Fnotify%2FZhiFuBao.htm&sign=cyejSqCiMuAUCo6W4T%2BZdocMN8ScV7%2FdhajY%2BWoX2xnAri8dEkXUrM7qVd1H0BRuqC2naU02liN5sP2Oou55a26o0%2BBn%2Ffi5uSw8loKyGfLKsssF68Xr33eVvozzU1vly4yE%2B8aD3JmxPMj6LhtauN296QL9z4l1INUsj6VnyMKfziAnJMQPWH0fks8hOpzTbTGFELuuzZiRJLpH2D4dLFktQMzbKPMSldTcKKUBsssQaAasnxydCSj3ylFsqRRH953UZ0LhiOQaDn09D78kDuxt1l3VUjJPgdlA8wVWj3YyFw%2FoQVYS%2Fl6WJJ9zc1TXm2z3ZoXdjVPU%2FLCCyw%2BYUA%3D%3D&sign_type=RSA2&timestamp=2017-09-22+10%3A22%3A45&version=1.0
     */

    private String payWay;
    private Object weiXinResponse;
    private String zhiFuBaoResponse;

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public Object getWeiXinResponse() {
        return weiXinResponse;
    }

    public void setWeiXinResponse(Object weiXinResponse) {
        this.weiXinResponse = weiXinResponse;
    }

    public String getZhiFuBaoResponse() {
        return zhiFuBaoResponse;
    }

    public void setZhiFuBaoResponse(String zhiFuBaoResponse) {
        this.zhiFuBaoResponse = zhiFuBaoResponse;
    }
}
