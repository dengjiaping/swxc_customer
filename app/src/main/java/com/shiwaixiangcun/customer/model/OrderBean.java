package com.shiwaixiangcun.customer.model;

import java.util.List;

/**
 * Created by Administrator on 2017/9/15.
 */

public class OrderBean {

    /**
     * elements : [{"goodsTotal":1,"orderDetailDtoList":[{"accessURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/59/rBKx51m7Si6ANfF7AACzkL_KqTk408.jpg","fileId":4553,"goodsAmount":1,"goodsAttrDescription":"10斤装","goodsId":129,"goodsName":"越南火龙果","orderDetailId":160,"price":0.02,"shopName":"世外生活旗舰店","thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/59/rBKx51m7Si6ANfF7AACzkL_KqTk408.jpg"}],"orderId":224,"orderNumber":"1000000057","orderStatus":"Closed","realyPay":10.02,"transportMoney":10},{"goodsTotal":1,"orderDetailDtoList":[{"accessURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/59/rBKx51m7SjCAWeQsAAC79wqpqtk314.jpg","fileId":4554,"goodsAmount":1,"goodsAttrDescription":"1斤装","goodsId":129,"goodsName":"越南火龙果","orderDetailId":146,"price":20,"shopName":"世外生活旗舰店","thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/59/rBKx51m7SjCAWeQsAAC79wqpqtk314.jpg"}],"orderId":210,"orderNumber":"1000000043","orderStatus":"Closed","realyPay":30,"transportMoney":10}]
     * page : 1
     * size : 10
     * totalAmount : 2
     * totalPages : 1
     */

    private int page;
    private int size;
    private int totalAmount;
    private int totalPages;
    private List<ElementsBean> elements;

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

    public static class ElementsBean {
        /**
         * goodsTotal : 1
         * orderDetailDtoList : [{"accessURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/59/rBKx51m7Si6ANfF7AACzkL_KqTk408.jpg","fileId":4553,"goodsAmount":1,"goodsAttrDescription":"10斤装","goodsId":129,"goodsName":"越南火龙果","orderDetailId":160,"price":0.02,"shopName":"世外生活旗舰店","thumbImageURL":"http://resource.shiwaixiangcun.cn/group1/M00/00/59/rBKx51m7Si6ANfF7AACzkL_KqTk408.jpg"}]
         * orderId : 224
         * orderNumber : 1000000057
         * orderStatus : Closed
         * realyPay : 10.02
         * transportMoney : 10
         */

        private int goodsTotal;
        private int orderId;
        private String orderNumber;
        private String orderStatus;
        private double realyPay;
        private int transportMoney;
        private List<OrderDetailDtoListBean> orderDetailDtoList;

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

        public int getTransportMoney() {
            return transportMoney;
        }

        public void setTransportMoney(int transportMoney) {
            this.transportMoney = transportMoney;
        }

        public List<OrderDetailDtoListBean> getOrderDetailDtoList() {
            return orderDetailDtoList;
        }

        public void setOrderDetailDtoList(List<OrderDetailDtoListBean> orderDetailDtoList) {
            this.orderDetailDtoList = orderDetailDtoList;
        }

        public static class OrderDetailDtoListBean {
            /**
             * accessURL : http://resource.shiwaixiangcun.cn/group1/M00/00/59/rBKx51m7Si6ANfF7AACzkL_KqTk408.jpg
             * fileId : 4553
             * goodsAmount : 1
             * goodsAttrDescription : 10斤装
             * goodsId : 129
             * goodsName : 越南火龙果
             * orderDetailId : 160
             * price : 0.02
             * shopName : 世外生活旗舰店
             * thumbImageURL : http://resource.shiwaixiangcun.cn/group1/M00/00/59/rBKx51m7Si6ANfF7AACzkL_KqTk408.jpg
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
        }
    }
}
