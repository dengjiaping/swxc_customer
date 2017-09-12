package com.shiwaixiangcun.customer.model;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7.
 */

public class MallGoods {

    /**
     * data : {"elements":[{"feature":"得分王","goodsId":106,"goodsName":"服饰01","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/56/rBKx51m2P4mAaOdBADe7UTq8v-g118.jpg","minPrice":0.01,"subjectId":248},{"feature":"打发我个","goodsId":109,"goodsName":"芒果01（有运费）","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/56/rBKx51m2Q0WATSGoAAAyxNR9tng611.jpg","minPrice":0.01,"subjectId":248},{"feature":"dfhrgjs","goodsId":107,"goodsName":"零食01（限购3）","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/56/rBKx51m2QL6AP1HmAADN4JNFDVo636.jpg","minPrice":0.01,"subjectId":248}],"page":1,"size":6,"totalAmount":3,"totalPages":1}
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
         * elements : [{"feature":"得分王","goodsId":106,"goodsName":"服饰01","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/56/rBKx51m2P4mAaOdBADe7UTq8v-g118.jpg","minPrice":0.01,"subjectId":248},{"feature":"打发我个","goodsId":109,"goodsName":"芒果01（有运费）","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/56/rBKx51m2Q0WATSGoAAAyxNR9tng611.jpg","minPrice":0.01,"subjectId":248},{"feature":"dfhrgjs","goodsId":107,"goodsName":"零食01（限购3）","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/56/rBKx51m2QL6AP1HmAADN4JNFDVo636.jpg","minPrice":0.01,"subjectId":248}]
         * page : 1
         * size : 6
         * totalAmount : 3
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
             * feature : 得分王
             * goodsId : 106
             * goodsName : 服饰01
             * imagePath : http://resource.shiwaixiangcun.cn/group1/M00/00/56/rBKx51m2P4mAaOdBADe7UTq8v-g118.jpg
             * minPrice : 0.01
             * subjectId : 248
             */

            private String feature;
            private int goodsId;
            private String goodsName;
            private String imagePath;
            private double minPrice;
            private int subjectId;

            public String getFeature() {
                return feature;
            }

            public void setFeature(String feature) {
                this.feature = feature;
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

            public String getImagePath() {
                return imagePath;
            }

            public void setImagePath(String imagePath) {
                this.imagePath = imagePath;
            }

            public double getMinPrice() {
                return minPrice;
            }

            public void setMinPrice(double minPrice) {
                this.minPrice = minPrice;
            }

            public int getSubjectId() {
                return subjectId;
            }

            public void setSubjectId(int subjectId) {
                this.subjectId = subjectId;
            }
        }
    }
}
