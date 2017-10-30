package com.shiwaixiangcun.customer.model;

import java.util.List;

/**
 * Created by Administrator on 2017/9/8.
 */

public class SearchResult {

    /**
     * data : {"elements":[{"feature":"无卖点","goodsId":67,"goodsName":"水果蔬菜-水果-芒果04","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mncBqAMAGOAAAyxNR9tng809.jpg","minPrice":12.23,"subjectId":244},{"feature":"无","goodsId":66,"goodsName":"水果蔬菜-水果-苹果03","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mnb6-AFQGXAAAMimw3x3I514.jpg","minPrice":50.02,"subjectId":244},{"feature":"卖点","goodsId":61,"goodsName":"水果蔬菜-水果-芒果01","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mnYqeAYS_tAAAMimw3x3I455.jpg","minPrice":23,"subjectId":244},{"feature":"卖点卖点","goodsId":62,"goodsName":"水果蔬菜-水果-芒果02","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mnbW6Ae0zcAAA76G3dmhA407.jpg","minPrice":3.55,"subjectId":244},{"feature":"2","goodsId":68,"goodsName":"2","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/52/rBKx51mnuciAMi4iAAGxlqf_jyw501.jpg","minPrice":2,"subjectId":244},{"feature":"卖点卖点","goodsId":63,"goodsName":"水果蔬菜-水果-芒果03","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mnbkOAXETDAAAZvkrF0nQ537.jpg","minPrice":15.55,"subjectId":244}],"page":1,"size":6,"totalAmount":6,"totalPages":1}
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
         * elements : [{"feature":"无卖点","goodsId":67,"goodsName":"水果蔬菜-水果-芒果04","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mncBqAMAGOAAAyxNR9tng809.jpg","minPrice":12.23,"subjectId":244},{"feature":"无","goodsId":66,"goodsName":"水果蔬菜-水果-苹果03","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mnb6-AFQGXAAAMimw3x3I514.jpg","minPrice":50.02,"subjectId":244},{"feature":"卖点","goodsId":61,"goodsName":"水果蔬菜-水果-芒果01","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mnYqeAYS_tAAAMimw3x3I455.jpg","minPrice":23,"subjectId":244},{"feature":"卖点卖点","goodsId":62,"goodsName":"水果蔬菜-水果-芒果02","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mnbW6Ae0zcAAA76G3dmhA407.jpg","minPrice":3.55,"subjectId":244},{"feature":"2","goodsId":68,"goodsName":"2","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/52/rBKx51mnuciAMi4iAAGxlqf_jyw501.jpg","minPrice":2,"subjectId":244},{"feature":"卖点卖点","goodsId":63,"goodsName":"水果蔬菜-水果-芒果03","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mnbkOAXETDAAAZvkrF0nQ537.jpg","minPrice":15.55,"subjectId":244}]
         * page : 1
         * size : 6
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
             * feature : 无卖点
             * goodsId : 67
             * goodsName : 水果蔬菜-水果-芒果04
             * imagePath : http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mncBqAMAGOAAAyxNR9tng809.jpg
             * minPrice : 12.23
             * subjectId : 244
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
