package com.shiwaixiangcun.customer.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2017/9/15
 * <p>
 * desc 订单Item
 */

public class OrderBean implements Parcelable {

    public static final Parcelable.Creator<OrderBean> CREATOR = new Parcelable.Creator<OrderBean>() {
        @Override
        public OrderBean createFromParcel(Parcel source) {
            return new OrderBean(source);
        }

        @Override
        public OrderBean[] newArray(int size) {
            return new OrderBean[size];
        }
    };
    /**
     * elements : [{"afterSaleId":null,"afterSaleStatus":null,"afterSaled":false,"evaluated":false,"goodsTotal":1,"orderDetailDtoList":[{"accessURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg","fileId":null,"goodsAmount":1,"goodsAttrDescription":"重量:1kg，颜色:红心","goodsId":20,"goodsName":"零食","orderDetailId":67,"price":0.01,"shopName":"世外健康旗舰店","thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg"}],"orderId":67,"orderNumber":"0000000067","orderStatus":"WaitDeliver","realyPay":0.02,"transportMoney":0.01},{"afterSaleId":65,"afterSaleStatus":"Pending","afterSaled":true,"evaluated":false,"goodsTotal":1,"orderDetailDtoList":[{"accessURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg","fileId":null,"goodsAmount":1,"goodsAttrDescription":"重量:1kg,颜色:红心","goodsId":20,"goodsName":"零食","orderDetailId":59,"price":0.01,"shopName":"世外健康旗舰店","thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg"}],"orderId":59,"orderNumber":"0000000059","orderStatus":"WaitDeliver","realyPay":0.01,"transportMoney":0},{"afterSaleId":55,"afterSaleStatus":"Pending","afterSaled":true,"evaluated":false,"goodsTotal":1,"orderDetailDtoList":[{"accessURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg","fileId":null,"goodsAmount":1,"goodsAttrDescription":"重量:1kg,颜色:红心","goodsId":20,"goodsName":"零食","orderDetailId":57,"price":0.01,"shopName":"世外健康旗舰店","thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg"}],"orderId":57,"orderNumber":"0000000057","orderStatus":"WaitDeliver","realyPay":0.01,"transportMoney":0},{"afterSaleId":54,"afterSaleStatus":"Pending","afterSaled":true,"evaluated":false,"goodsTotal":1,"orderDetailDtoList":[{"accessURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg","fileId":null,"goodsAmount":1,"goodsAttrDescription":"重量:1kg,颜色:白心","goodsId":20,"goodsName":"零食","orderDetailId":51,"price":0.01,"shopName":"世外健康旗舰店","thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg"}],"orderId":51,"orderNumber":"0000000051","orderStatus":"WaitDeliver","realyPay":0.01,"transportMoney":0},{"afterSaleId":51,"afterSaleStatus":"RefundSuccess","afterSaled":true,"evaluated":false,"goodsTotal":1,"orderDetailDtoList":[{"accessURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg","fileId":null,"goodsAmount":1,"goodsAttrDescription":"重量:1kg,颜色:白心","goodsId":20,"goodsName":"零食","orderDetailId":48,"price":0.01,"shopName":"世外健康旗舰店","thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg"}],"orderId":48,"orderNumber":"0000000048","orderStatus":"Closed","realyPay":0.01,"transportMoney":0},{"afterSaleId":null,"afterSaleStatus":null,"afterSaled":false,"evaluated":false,"goodsTotal":1,"orderDetailDtoList":[{"accessURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/42/rBKx5VnE0iqAHDSWAAMKqMVCRV4996.jpg","fileId":null,"goodsAmount":1,"goodsAttrDescription":null,"goodsId":19,"goodsName":"爱牵挂血压计","orderDetailId":43,"price":398,"shopName":"世外健康旗舰店","thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/42/rBKx5VnE0iqAHDSWAAMKqMVCRV4996.jpg"}],"orderId":43,"orderNumber":"0000000043","orderStatus":"Closed","realyPay":408,"transportMoney":10},{"afterSaleId":null,"afterSaleStatus":null,"afterSaled":false,"evaluated":false,"goodsTotal":1,"orderDetailDtoList":[{"accessURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg","fileId":null,"goodsAmount":1,"goodsAttrDescription":"重量:1kg,颜色:白心","goodsId":20,"goodsName":"零食","orderDetailId":42,"price":0.01,"shopName":"世外健康旗舰店","thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg"}],"orderId":42,"orderNumber":"0000000042","orderStatus":"WaitDeliver","realyPay":0.01,"transportMoney":0},{"afterSaleId":null,"afterSaleStatus":null,"afterSaled":false,"evaluated":false,"goodsTotal":1,"orderDetailDtoList":[{"accessURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg","fileId":null,"goodsAmount":1,"goodsAttrDescription":"重量:1kg,颜色:红心","goodsId":20,"goodsName":"零食","orderDetailId":38,"price":0.01,"shopName":"世外健康旗舰店","thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg"}],"orderId":38,"orderNumber":"0000000038","orderStatus":"Finished","realyPay":0.01,"transportMoney":0},{"afterSaleId":34,"afterSaleStatus":"Pending","afterSaled":true,"evaluated":false,"goodsTotal":1,"orderDetailDtoList":[{"accessURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg","fileId":null,"goodsAmount":1,"goodsAttrDescription":"重量:1kg,颜色:白心","goodsId":20,"goodsName":"零食","orderDetailId":28,"price":0.01,"shopName":"世外健康旗舰店","thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg"}],"orderId":28,"orderNumber":"0000000028","orderStatus":"WaitDeliver","realyPay":0.01,"transportMoney":0},{"afterSaleId":null,"afterSaleStatus":null,"afterSaled":false,"evaluated":true,"goodsTotal":1,"orderDetailDtoList":[{"accessURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg","fileId":null,"goodsAmount":1,"goodsAttrDescription":"重量:1kg，颜色:红心","goodsId":20,"goodsName":"零食","orderDetailId":26,"price":0.01,"shopName":"世外健康旗舰店","thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg"}],"orderId":26,"orderNumber":"0000000026","orderStatus":"Finished","realyPay":0.01,"transportMoney":0},{"afterSaleId":33,"afterSaleStatus":"Pending","afterSaled":true,"evaluated":false,"goodsTotal":1,"orderDetailDtoList":[{"accessURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg","fileId":null,"goodsAmount":1,"goodsAttrDescription":"重量:2kg,颜色:红心","goodsId":20,"goodsName":"零食","orderDetailId":23,"price":0.01,"shopName":"世外健康旗舰店","thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg"}],"orderId":23,"orderNumber":"0000000023","orderStatus":"WaitDeliver","realyPay":0.01,"transportMoney":0},{"afterSaleId":32,"afterSaleStatus":"CancelServer","afterSaled":true,"evaluated":false,"goodsTotal":1,"orderDetailDtoList":[{"accessURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg","fileId":null,"goodsAmount":1,"goodsAttrDescription":"重量:1kg,颜色:白心","goodsId":20,"goodsName":"零食","orderDetailId":21,"price":0.01,"shopName":"世外健康旗舰店","thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg"}],"orderId":21,"orderNumber":"0000000021","orderStatus":"WaitDeliver","realyPay":0.01,"transportMoney":0},{"afterSaleId":31,"afterSaleStatus":"Pending","afterSaled":true,"evaluated":false,"goodsTotal":1,"orderDetailDtoList":[{"accessURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg","fileId":null,"goodsAmount":1,"goodsAttrDescription":"重量:1kg,颜色:白心","goodsId":20,"goodsName":"零食","orderDetailId":22,"price":0.01,"shopName":"世外健康旗舰店","thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg"}],"orderId":22,"orderNumber":"0000000022","orderStatus":"WaitDeliver","realyPay":0.01,"transportMoney":0},{"afterSaleId":30,"afterSaleStatus":"CancelServer","afterSaled":true,"evaluated":false,"goodsTotal":1,"orderDetailDtoList":[{"accessURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg","fileId":null,"goodsAmount":1,"goodsAttrDescription":"重量:1kg,颜色:白心","goodsId":20,"goodsName":"零食","orderDetailId":20,"price":0.01,"shopName":"世外健康旗舰店","thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg"}],"orderId":20,"orderNumber":"0000000020","orderStatus":"WaitDeliver","realyPay":0.01,"transportMoney":0},{"afterSaleId":null,"afterSaleStatus":null,"afterSaled":false,"evaluated":true,"goodsTotal":1,"orderDetailDtoList":[{"accessURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg","fileId":null,"goodsAmount":1,"goodsAttrDescription":"重量:1kg,颜色:白心","goodsId":20,"goodsName":"零食","orderDetailId":14,"price":0.01,"shopName":"世外健康旗舰店","thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg"}],"orderId":14,"orderNumber":"0000000014","orderStatus":"Finished","realyPay":0.01,"transportMoney":0},{"afterSaleId":null,"afterSaleStatus":null,"afterSaled":false,"evaluated":true,"goodsTotal":1,"orderDetailDtoList":[{"accessURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg","fileId":null,"goodsAmount":1,"goodsAttrDescription":"重量:2kg","goodsId":20,"goodsName":"零食","orderDetailId":12,"price":0.01,"shopName":"世外健康旗舰店","thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg"}],"orderId":12,"orderNumber":"0000000012","orderStatus":"Finished","realyPay":0.01,"transportMoney":0},{"afterSaleId":null,"afterSaleStatus":null,"afterSaled":false,"evaluated":true,"goodsTotal":1,"orderDetailDtoList":[{"accessURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg","fileId":null,"goodsAmount":1,"goodsAttrDescription":"重量:1kg","goodsId":20,"goodsName":"零食","orderDetailId":11,"price":0.01,"shopName":"世外健康旗舰店","thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg"}],"orderId":11,"orderNumber":"0000000011","orderStatus":"Finished","realyPay":0.01,"transportMoney":0}]
     * page : 1
     * size : 20
     * totalAmount : 17
     * totalPages : 1
     */

    private int page;
    private int size;
    private int totalAmount;
    private int totalPages;
    private List<ElementsBean> elements;

    public OrderBean() {
    }

    protected OrderBean(Parcel in) {
        this.page = in.readInt();
        this.size = in.readInt();
        this.totalAmount = in.readInt();
        this.totalPages = in.readInt();
        this.elements = in.createTypedArrayList(ElementsBean.CREATOR);
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
        dest.writeInt(this.size);
        dest.writeInt(this.totalAmount);
        dest.writeInt(this.totalPages);
        dest.writeTypedList(this.elements);
    }

    public static class ElementsBean implements Parcelable {
        public static final Parcelable.Creator<ElementsBean> CREATOR = new Parcelable.Creator<ElementsBean>() {
            @Override
            public ElementsBean createFromParcel(Parcel source) {
                return new ElementsBean(source);
            }

            @Override
            public ElementsBean[] newArray(int size) {
                return new ElementsBean[size];
            }
        };
        /**
         * afterSaleId : null
         * afterSaleStatus : null
         * afterSaled : false
         * evaluated : false
         * goodsTotal : 1
         * orderDetailDtoList : [{"accessURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg","fileId":null,"goodsAmount":1,"goodsAttrDescription":"重量:1kg，颜色:红心","goodsId":20,"goodsName":"零食","orderDetailId":67,"price":0.01,"shopName":"世外健康旗舰店","thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg"}]
         * orderId : 67
         * orderNumber : 0000000067
         * orderStatus : WaitDeliver
         * realyPay : 0.02
         * transportMoney : 0.01
         */

        private int afterSaleId;
        private String afterSaleStatus;
        private boolean afterSaled;
        private boolean evaluated;
        private int goodsTotal;
        private int orderId;
        private String orderNumber;
        private String orderStatus;
        private double realyPay;
        private double transportMoney;
        private List<OrderDetailDtoListBean> orderDetailDtoList;

        public ElementsBean() {
        }

        protected ElementsBean(Parcel in) {
            this.afterSaleId = in.readInt();
            this.afterSaleStatus = in.readString();
            this.afterSaled = in.readByte() != 0;
            this.evaluated = in.readByte() != 0;
            this.goodsTotal = in.readInt();
            this.orderId = in.readInt();
            this.orderNumber = in.readString();
            this.orderStatus = in.readString();
            this.realyPay = in.readDouble();
            this.transportMoney = in.readDouble();
            this.orderDetailDtoList = new ArrayList<OrderDetailDtoListBean>();
            in.readList(this.orderDetailDtoList, OrderDetailDtoListBean.class.getClassLoader());
        }

        public int getAfterSaleId() {
            return afterSaleId;
        }

        public void setAfterSaleId(int afterSaleId) {
            this.afterSaleId = afterSaleId;
        }

        public String getAfterSaleStatus() {
            return afterSaleStatus;
        }

        public void setAfterSaleStatus(String afterSaleStatus) {
            this.afterSaleStatus = afterSaleStatus;
        }

        public boolean isAfterSaled() {
            return afterSaled;
        }

        public void setAfterSaled(boolean afterSaled) {
            this.afterSaled = afterSaled;
        }

        public boolean isEvaluated() {
            return evaluated;
        }

        public void setEvaluated(boolean evaluated) {
            this.evaluated = evaluated;
        }

        public int getGoodsTotal() {
            return goodsTotal;
        }

        public void setGoodsTotal(int goodsTotal) {
            this.goodsTotal = goodsTotal;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public double getRealyPay() {
            return realyPay;
        }

        public void setRealyPay(double realyPay) {
            this.realyPay = realyPay;
        }

        public double getTransportMoney() {
            return transportMoney;
        }

        public void setTransportMoney(double transportMoney) {
            this.transportMoney = transportMoney;
        }

        public List<OrderDetailDtoListBean> getOrderDetailDtoList() {
            return orderDetailDtoList;
        }

        public void setOrderDetailDtoList(List<OrderDetailDtoListBean> orderDetailDtoList) {
            this.orderDetailDtoList = orderDetailDtoList;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.afterSaleId);
            dest.writeString(this.afterSaleStatus);
            dest.writeByte(this.afterSaled ? (byte) 1 : (byte) 0);
            dest.writeByte(this.evaluated ? (byte) 1 : (byte) 0);
            dest.writeInt(this.goodsTotal);
            dest.writeInt(this.orderId);
            dest.writeString(this.orderNumber);
            dest.writeString(this.orderStatus);
            dest.writeDouble(this.realyPay);
            dest.writeDouble(this.transportMoney);
            dest.writeList(this.orderDetailDtoList);
        }

        public static class OrderDetailDtoListBean implements Parcelable {
            public static final Creator<OrderDetailDtoListBean> CREATOR = new Creator<OrderDetailDtoListBean>() {
                @Override
                public OrderDetailDtoListBean createFromParcel(Parcel source) {
                    return new OrderDetailDtoListBean(source);
                }

                @Override
                public OrderDetailDtoListBean[] newArray(int size) {
                    return new OrderDetailDtoListBean[size];
                }
            };
            /**
             * accessURL : http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg
             * fileId : null
             * goodsAmount : 1
             * goodsAttrDescription : 重量:1kg，颜色:红心
             * goodsId : 20
             * goodsName : 零食
             * orderDetailId : 67
             * price : 0.01
             * shopName : 世外健康旗舰店
             * thumbImageURL : http://resource.shiwaixiangcun.cn/group1/M00/00/69/rBKx51n2ivWAdyZJAABnL3EQYZs639.jpg
             */

            private String accessURL;
            private int fileId;
            private int goodsAmount;
            private String goodsAttrDescription;
            private int goodsId;
            private String goodsName;
            private int orderDetailId;
            private double price;
            private String shopName;
            private String thumbImageURL;

            public OrderDetailDtoListBean() {
            }

            protected OrderDetailDtoListBean(Parcel in) {
                this.accessURL = in.readString();
                this.fileId = in.readInt();
                this.goodsAmount = in.readInt();
                this.goodsAttrDescription = in.readString();
                this.goodsId = in.readInt();
                this.goodsName = in.readString();
                this.orderDetailId = in.readInt();
                this.price = in.readDouble();
                this.shopName = in.readString();
                this.thumbImageURL = in.readString();
            }

            public String getAccessURL() {
                return accessURL;
            }

            public void setAccessURL(String accessURL) {
                this.accessURL = accessURL;
            }

            public int getFileId() {
                return fileId;
            }

            public void setFileId(int fileId) {
                this.fileId = fileId;
            }

            public int getGoodsAmount() {
                return goodsAmount;
            }

            public void setGoodsAmount(int goodsAmount) {
                this.goodsAmount = goodsAmount;
            }

            public String getGoodsAttrDescription() {
                return goodsAttrDescription;
            }

            public void setGoodsAttrDescription(String goodsAttrDescription) {
                this.goodsAttrDescription = goodsAttrDescription;
            }

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public int getOrderDetailId() {
                return orderDetailId;
            }

            public void setOrderDetailId(int orderDetailId) {
                this.orderDetailId = orderDetailId;
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

            public String getThumbImageURL() {
                return thumbImageURL;
            }

            public void setThumbImageURL(String thumbImageURL) {
                this.thumbImageURL = thumbImageURL;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.accessURL);
                dest.writeInt(this.fileId);
                dest.writeInt(this.goodsAmount);
                dest.writeString(this.goodsAttrDescription);
                dest.writeInt(this.goodsId);
                dest.writeString(this.goodsName);
                dest.writeInt(this.orderDetailId);
                dest.writeDouble(this.price);
                dest.writeString(this.shopName);
                dest.writeString(this.thumbImageURL);
            }
        }
    }
}
