package com.shiwaixiangcun.customer.model;

import java.util.List;

/**
 * Created by Administrator on 2017/9/18.
 */

public class OrderDetailBean {

    /**
     * buyersInfo : {"customerName":"15520447006","customerPhone":"15520447006","deliveryAddress":"日本秋名山","deliveryName":"藤原拓海","deliveryPhone":"13896204750","deliveryWay":"由helin旗舰店发货，并提供售后服务。"}
     * goodsDetail : [{"amount":1,"goodName":"哈密瓜-aaa","imgPath":"","price":0.01,"shopName":"helin旗舰店","subtotal":0.01}]
     * orderInfo : {"orderNumber":"1000000067","orderTime":1505467193000,"payWay":"ZhiFuBao","realPay":0.01,"shouldPay":0.01,"transportMoney":0}
     * orderStatus : {"status":"Finished"}
     */

    private BuyersInfoBean buyersInfo;
    private OrderInfoBean orderInfo;
    private OrderStatusBean orderStatus;
    private List<GoodsDetailBean> goodsDetail;

    public BuyersInfoBean getBuyersInfo() {
        return buyersInfo;
    }

    public void setBuyersInfo(BuyersInfoBean buyersInfo) {
        this.buyersInfo = buyersInfo;
    }

    public OrderInfoBean getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfoBean orderInfo) {
        this.orderInfo = orderInfo;
    }

    public OrderStatusBean getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatusBean orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<GoodsDetailBean> getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(List<GoodsDetailBean> goodsDetail) {
        this.goodsDetail = goodsDetail;
    }


    public static class ExpressWay {
    }
    public static class BuyersInfoBean {
        /**
         * customerName : 15520447006
         * customerPhone : 15520447006
         * deliveryAddress : 日本秋名山
         * deliveryName : 藤原拓海
         * deliveryPhone : 13896204750
         * deliveryWay : 由helin旗舰店发货，并提供售后服务。
         */

        private String leavingMessage;
        private String customerName;
        private String customerPhone;
        private String deliveryAddress;
        private String deliveryName;
        private String deliveryPhone;
        private String deliveryWay;
        private String expressWay;

        public String getExpressWay() {
            return expressWay;
        }

        public void setExpressWay(String expressWay) {
            this.expressWay = expressWay;
        }

        public String getLeavingMessage() {
            return leavingMessage;
        }

        public void setLeavingMessage(String leavingMessage) {
            this.leavingMessage = leavingMessage;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerPhone() {
            return customerPhone;
        }

        public void setCustomerPhone(String customerPhone) {
            this.customerPhone = customerPhone;
        }

        public String getDeliveryAddress() {
            return deliveryAddress;
        }

        public void setDeliveryAddress(String deliveryAddress) {
            this.deliveryAddress = deliveryAddress;
        }

        public String getDeliveryName() {
            return deliveryName;
        }

        public void setDeliveryName(String deliveryName) {
            this.deliveryName = deliveryName;
        }

        public String getDeliveryPhone() {
            return deliveryPhone;
        }

        public void setDeliveryPhone(String deliveryPhone) {
            this.deliveryPhone = deliveryPhone;
        }

        public String getDeliveryWay() {
            return deliveryWay;
        }

        public void setDeliveryWay(String deliveryWay) {
            this.deliveryWay = deliveryWay;
        }
    }

    public static class OrderInfoBean {
        /**
         * orderNumber : 1000000067
         * orderTime : 1505467193000
         * payWay : ZhiFuBao
         * realPay : 0.01
         * shouldPay : 0.01
         * transportMoney : 0
         */

        private String orderNumber;
        private long orderTime;
        private String payWay;
        private double realPay;
        private double shouldPay;
        private double transportMoney;

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public long getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(long orderTime) {
            this.orderTime = orderTime;
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

        public double getShouldPay() {
            return shouldPay;
        }

        public void setShouldPay(double shouldPay) {
            this.shouldPay = shouldPay;
        }

        public double getTransportMoney() {
            return transportMoney;
        }

        public void setTransportMoney(int transportMoney) {
            this.transportMoney = transportMoney;
        }
    }

    public static class OrderStatusBean {
        /**
         * status : Finished
         */

        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class GoodsDetailBean {
        /**
         * amount : 1
         * goodName : 哈密瓜-aaa
         * imgPath :
         * price : 0.01
         * shopName : helin旗舰店
         * subtotal : 0.01
         */

        private String attrDescription;
        private int amount;
        private String goodName;
        private String imgPath;
        private double price;
        private String shopName;
        private double subtotal;

        public String getAttrDescription() {
            return attrDescription;
        }

        public void setAttrDescription(String attrDescription) {
            this.attrDescription = attrDescription;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getGoodName() {
            return goodName;
        }

        public void setGoodName(String goodName) {
            this.goodName = goodName;
        }

        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public double getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(double subtotal) {
            this.subtotal = subtotal;
        }
    }
}
