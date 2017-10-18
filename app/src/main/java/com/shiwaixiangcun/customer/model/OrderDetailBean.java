package com.shiwaixiangcun.customer.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/18.
 */

public class OrderDetailBean implements Parcelable {

    public static final Parcelable.Creator<OrderDetailBean> CREATOR = new Parcelable.Creator<OrderDetailBean>() {
        @Override
        public OrderDetailBean createFromParcel(Parcel source) {
            return new OrderDetailBean(source);
        }

        @Override
        public OrderDetailBean[] newArray(int size) {
            return new OrderDetailBean[size];
        }
    };
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

    public OrderDetailBean() {
    }

    protected OrderDetailBean(Parcel in) {
        this.buyersInfo = in.readParcelable(BuyersInfoBean.class.getClassLoader());
        this.orderInfo = in.readParcelable(OrderInfoBean.class.getClassLoader());
        this.orderStatus = in.readParcelable(OrderStatusBean.class.getClassLoader());
        this.goodsDetail = new ArrayList<GoodsDetailBean>();
        in.readList(this.goodsDetail, GoodsDetailBean.class.getClassLoader());
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.buyersInfo, flags);
        dest.writeParcelable(this.orderInfo, flags);
        dest.writeParcelable(this.orderStatus, flags);
        dest.writeList(this.goodsDetail);
    }

    public static class ExpressWay {
    }

    public static class BuyersInfoBean implements Parcelable {
        public static final Creator<BuyersInfoBean> CREATOR = new Creator<BuyersInfoBean>() {
            @Override
            public BuyersInfoBean createFromParcel(Parcel source) {
                return new BuyersInfoBean(source);
            }

            @Override
            public BuyersInfoBean[] newArray(int size) {
                return new BuyersInfoBean[size];
            }
        };
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

        public BuyersInfoBean() {
        }

        protected BuyersInfoBean(Parcel in) {
            this.leavingMessage = in.readString();
            this.customerName = in.readString();
            this.customerPhone = in.readString();
            this.deliveryAddress = in.readString();
            this.deliveryName = in.readString();
            this.deliveryPhone = in.readString();
            this.deliveryWay = in.readString();
            this.expressWay = in.readString();
        }

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.leavingMessage);
            dest.writeString(this.customerName);
            dest.writeString(this.customerPhone);
            dest.writeString(this.deliveryAddress);
            dest.writeString(this.deliveryName);
            dest.writeString(this.deliveryPhone);
            dest.writeString(this.deliveryWay);
            dest.writeString(this.expressWay);
        }
    }

    public static class OrderInfoBean implements Parcelable {
        public static final Creator<OrderInfoBean> CREATOR = new Creator<OrderInfoBean>() {
            @Override
            public OrderInfoBean createFromParcel(Parcel source) {
                return new OrderInfoBean(source);
            }

            @Override
            public OrderInfoBean[] newArray(int size) {
                return new OrderInfoBean[size];
            }
        };
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

        public OrderInfoBean() {
        }

        protected OrderInfoBean(Parcel in) {
            this.orderNumber = in.readString();
            this.orderTime = in.readLong();
            this.payWay = in.readString();
            this.realPay = in.readDouble();
            this.shouldPay = in.readDouble();
            this.transportMoney = in.readDouble();
        }

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.orderNumber);
            dest.writeLong(this.orderTime);
            dest.writeString(this.payWay);
            dest.writeDouble(this.realPay);
            dest.writeDouble(this.shouldPay);
            dest.writeDouble(this.transportMoney);
        }
    }

    public static class OrderStatusBean implements Parcelable {
        public static final Creator<OrderStatusBean> CREATOR = new Creator<OrderStatusBean>() {
            @Override
            public OrderStatusBean createFromParcel(Parcel source) {
                return new OrderStatusBean(source);
            }

            @Override
            public OrderStatusBean[] newArray(int size) {
                return new OrderStatusBean[size];
            }
        };
        /**
         * status : Finished
         */

        private String status;

        public OrderStatusBean() {
        }

        protected OrderStatusBean(Parcel in) {
            this.status = in.readString();
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.status);
        }
    }

    public static class GoodsDetailBean implements Parcelable {
        public static final Creator<GoodsDetailBean> CREATOR = new Creator<GoodsDetailBean>() {
            @Override
            public GoodsDetailBean createFromParcel(Parcel source) {
                return new GoodsDetailBean(source);
            }

            @Override
            public GoodsDetailBean[] newArray(int size) {
                return new GoodsDetailBean[size];
            }
        };
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
        private int goodsId;

        public GoodsDetailBean() {
        }

        protected GoodsDetailBean(Parcel in) {
            this.attrDescription = in.readString();
            this.amount = in.readInt();
            this.goodName = in.readString();
            this.imgPath = in.readString();
            this.price = in.readDouble();
            this.shopName = in.readString();
            this.subtotal = in.readDouble();
            this.goodsId = in.readInt();
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.attrDescription);
            dest.writeInt(this.amount);
            dest.writeString(this.goodName);
            dest.writeString(this.imgPath);
            dest.writeDouble(this.price);
            dest.writeString(this.shopName);
            dest.writeDouble(this.subtotal);
            dest.writeInt(this.goodsId);
        }
    }
}
