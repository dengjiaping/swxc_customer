package com.shiwaixiangcun.customer.model;

import java.util.List;

/**
 * Created by Administrator on 2017/10/13.
 */

public class AfterServiceBean {

    /**
     * elements : [{"amount":1,"attrDes":"重量:2kg，尺码:大大大大，颜色:青青青色","goodsId":148,"goodsName":"冠生园 蜂蜜900g/瓶 蜂蜜 蜂制品 冲饮 蜜蜂制品","id":22,"number":"0000000014","path":"http://resource.shiwaixiangcun.cn/group1/M00/00/5E/rBKx51nDMoCAFEtFAAaxQNn9vDQ942_150x150.jpg","realPay":0.02,"status":"Pending","transportMoney":0.01},{"amount":1,"attrDes":"重量:3kg，尺码:大大大大，颜色:青青青色","goodsId":148,"goodsName":"冠生园 蜂蜜900g/瓶 蜂蜜 蜂制品 冲饮 蜜蜂制品","id":21,"number":"0000000013","path":"http://resource.shiwaixiangcun.cn/group1/M00/00/5E/rBKx51nDMoCAFEtFAAaxQNn9vDQ942_150x150.jpg","realPay":0.02,"status":"Pending","transportMoney":0.01},{"amount":1,"attrDes":"重量:2kg，尺码:大大大大，颜色:青青青色","goodsId":148,"goodsName":"冠生园 蜂蜜900g/瓶 蜂蜜 蜂制品 冲饮 蜜蜂制品","id":20,"number":"0000000012","path":"http://resource.shiwaixiangcun.cn/group1/M00/00/5E/rBKx51nDMoCAFEtFAAaxQNn9vDQ942_150x150.jpg","realPay":0.02,"status":"RefundSuccess","transportMoney":0.01},{"amount":1,"attrDes":"重量:2kg，尺码:大大大大，颜色:青青青色","goodsId":148,"goodsName":"冠生园 蜂蜜900g/瓶 蜂蜜 蜂制品 冲饮 蜜蜂制品","id":19,"number":"0000000011","path":"http://resource.shiwaixiangcun.cn/group1/M00/00/5E/rBKx51nDMoCAFEtFAAaxQNn9vDQ942_150x150.jpg","realPay":0.02,"status":"CancelServer","transportMoney":0.01},{"amount":1,"attrDes":"重量:2kg，尺码:大，颜色:青","goodsId":148,"goodsName":"冠生园 蜂蜜900g/瓶 蜂蜜 蜂制品 冲饮 蜜蜂制品","id":18,"number":"0000000010","path":"http://resource.shiwaixiangcun.cn/group1/M00/00/5E/rBKx51nDMoCAFEtFAAaxQNn9vDQ942_150x150.jpg","realPay":null,"status":"Pending","transportMoney":null},{"amount":1,"attrDes":"重量:2kg，尺码:大，颜色:青","goodsId":148,"goodsName":"冠生园 蜂蜜900g/瓶 蜂蜜 蜂制品 冲饮 蜜蜂制品","id":17,"number":"0000000009","path":"http://resource.shiwaixiangcun.cn/group1/M00/00/5E/rBKx51nDMoCAFEtFAAaxQNn9vDQ942_150x150.jpg","realPay":null,"status":"CancelServer","transportMoney":null}]
     * page : 1
     * size : 10
     * totalAmount : 6
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
         * amount : 1
         * attrDes : 重量:2kg，尺码:大大大大，颜色:青青青色
         * goodsId : 148
         * goodsName : 冠生园 蜂蜜900g/瓶 蜂蜜 蜂制品 冲饮 蜜蜂制品
         * id : 22
         * number : 0000000014
         * path : http://resource.shiwaixiangcun.cn/group1/M00/00/5E/rBKx51nDMoCAFEtFAAaxQNn9vDQ942_150x150.jpg
         * realPay : 0.02
         * status : Pending
         * transportMoney : 0.01
         */

        private int amount;
        private String attrDes;
        private int goodsId;
        private String goodsName;
        private int id;
        private String number;
        private String path;
        private double realPay;
        private String status;
        private double transportMoney;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getAttrDes() {
            return attrDes;
        }

        public void setAttrDes(String attrDes) {
            this.attrDes = attrDes;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public double getRealPay() {
            return realPay;
        }

        public void setRealPay(double realPay) {
            this.realPay = realPay;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public double getTransportMoney() {
            return transportMoney;
        }

        public void setTransportMoney(double transportMoney) {
            this.transportMoney = transportMoney;
        }
    }
}
