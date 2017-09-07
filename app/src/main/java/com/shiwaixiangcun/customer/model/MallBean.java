package com.shiwaixiangcun.customer.model;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7.
 */

public class MallBean {

    /**
     * data : {"dailySelectionList":[{"feature":"无卖点","goodsId":67,"goodsName":"水果蔬菜-水果-芒果04","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mncBqAMAGOAAAyxNR9tng809.jpg","minPrice":12.23,"subjectId":244},{"feature":"无","goodsId":66,"goodsName":"水果蔬菜-水果-苹果03","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mnb6-AFQGXAAAMimw3x3I514.jpg","minPrice":50.02,"subjectId":244},{"feature":"卖点","goodsId":61,"goodsName":"水果蔬菜-水果-芒果01","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mnYqeAYS_tAAAMimw3x3I455.jpg","minPrice":23,"subjectId":244},{"feature":"卖点卖点","goodsId":62,"goodsName":"水果蔬菜-水果-芒果02","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mnbW6Ae0zcAAA76G3dmhA407.jpg","minPrice":3.55,"subjectId":244},{"feature":"2","goodsId":68,"goodsName":"2","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/52/rBKx51mnuciAMi4iAAGxlqf_jyw501.jpg","minPrice":2,"subjectId":244},{"feature":"hot","goodsId":87,"goodsName":"coco","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/54/rBKx51mucP-AEdUrAABpjNMDuPU879.jpg","minPrice":1,"subjectId":244},{"feature":"卖点","goodsId":65,"goodsName":"水果蔬菜-水果-苹果02","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mnb2SADRhjAAAUnqGei5c185.jpg","minPrice":2,"subjectId":244},{"feature":"卖点卖点","goodsId":63,"goodsName":"水果蔬菜-水果-芒果03","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mnbkOAXETDAAAZvkrF0nQ537.jpg","minPrice":15.55,"subjectId":244}],"hotSell":{"feature":"而我更威风","goodsId":73,"goodsName":"5645","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/53/rBKx51muOJGAHFMAAALq3M6GGss130.png","minPrice":42532,"subjectId":246},"newGoodsSale":{"feature":"卖点","goodsId":61,"goodsName":"水果蔬菜-水果-芒果01","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mnYqeAYS_tAAAMimw3x3I455.jpg","minPrice":23,"subjectId":247},"qualityGoods":{"feature":"卖点卖点","goodsId":62,"goodsName":"水果蔬菜-水果-芒果02","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mnbW6Ae0zcAAA76G3dmhA407.jpg","minPrice":3.55,"subjectId":245}}
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
         * dailySelectionList : [{"feature":"无卖点","goodsId":67,"goodsName":"水果蔬菜-水果-芒果04","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mncBqAMAGOAAAyxNR9tng809.jpg","minPrice":12.23,"subjectId":244},{"feature":"无","goodsId":66,"goodsName":"水果蔬菜-水果-苹果03","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mnb6-AFQGXAAAMimw3x3I514.jpg","minPrice":50.02,"subjectId":244},{"feature":"卖点","goodsId":61,"goodsName":"水果蔬菜-水果-芒果01","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mnYqeAYS_tAAAMimw3x3I455.jpg","minPrice":23,"subjectId":244},{"feature":"卖点卖点","goodsId":62,"goodsName":"水果蔬菜-水果-芒果02","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mnbW6Ae0zcAAA76G3dmhA407.jpg","minPrice":3.55,"subjectId":244},{"feature":"2","goodsId":68,"goodsName":"2","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/52/rBKx51mnuciAMi4iAAGxlqf_jyw501.jpg","minPrice":2,"subjectId":244},{"feature":"hot","goodsId":87,"goodsName":"coco","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/54/rBKx51mucP-AEdUrAABpjNMDuPU879.jpg","minPrice":1,"subjectId":244},{"feature":"卖点","goodsId":65,"goodsName":"水果蔬菜-水果-苹果02","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mnb2SADRhjAAAUnqGei5c185.jpg","minPrice":2,"subjectId":244},{"feature":"卖点卖点","goodsId":63,"goodsName":"水果蔬菜-水果-芒果03","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mnbkOAXETDAAAZvkrF0nQ537.jpg","minPrice":15.55,"subjectId":244}]
         * hotSell : {"feature":"而我更威风","goodsId":73,"goodsName":"5645","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/53/rBKx51muOJGAHFMAAALq3M6GGss130.png","minPrice":42532,"subjectId":246}
         * newGoodsSale : {"feature":"卖点","goodsId":61,"goodsName":"水果蔬菜-水果-芒果01","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mnYqeAYS_tAAAMimw3x3I455.jpg","minPrice":23,"subjectId":247}
         * qualityGoods : {"feature":"卖点卖点","goodsId":62,"goodsName":"水果蔬菜-水果-芒果02","imagePath":"http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mnbW6Ae0zcAAA76G3dmhA407.jpg","minPrice":3.55,"subjectId":245}
         */

        private HotSellBean hotSell;
        private NewGoodsSaleBean newGoodsSale;
        private QualityGoodsBean qualityGoods;
        private List<DailySelectionListBean> dailySelectionList;

        public HotSellBean getHotSell() {
            return hotSell;
        }

        public void setHotSell(HotSellBean hotSell) {
            this.hotSell = hotSell;
        }

        public NewGoodsSaleBean getNewGoodsSale() {
            return newGoodsSale;
        }

        public void setNewGoodsSale(NewGoodsSaleBean newGoodsSale) {
            this.newGoodsSale = newGoodsSale;
        }

        public QualityGoodsBean getQualityGoods() {
            return qualityGoods;
        }

        public void setQualityGoods(QualityGoodsBean qualityGoods) {
            this.qualityGoods = qualityGoods;
        }

        public List<DailySelectionListBean> getDailySelectionList() {
            return dailySelectionList;
        }

        public void setDailySelectionList(List<DailySelectionListBean> dailySelectionList) {
            this.dailySelectionList = dailySelectionList;
        }

        public static class HotSellBean {
            /**
             * feature : 而我更威风
             * goodsId : 73
             * goodsName : 5645
             * imagePath : http://resource.shiwaixiangcun.cn/group1/M00/00/53/rBKx51muOJGAHFMAAALq3M6GGss130.png
             * minPrice : 42532
             * subjectId : 246
             */

            private String feature;
            private int goodsId;
            private String goodsName;
            private String imagePath;
            private int minPrice;
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

            public int getMinPrice() {
                return minPrice;
            }

            public void setMinPrice(int minPrice) {
                this.minPrice = minPrice;
            }

            public int getSubjectId() {
                return subjectId;
            }

            public void setSubjectId(int subjectId) {
                this.subjectId = subjectId;
            }
        }

        public static class NewGoodsSaleBean {
            /**
             * feature : 卖点
             * goodsId : 61
             * goodsName : 水果蔬菜-水果-芒果01
             * imagePath : http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mnYqeAYS_tAAAMimw3x3I455.jpg
             * minPrice : 23
             * subjectId : 247
             */

            private String feature;
            private int goodsId;
            private String goodsName;
            private String imagePath;
            private int minPrice;
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

            public int getMinPrice() {
                return minPrice;
            }

            public void setMinPrice(int minPrice) {
                this.minPrice = minPrice;
            }

            public int getSubjectId() {
                return subjectId;
            }

            public void setSubjectId(int subjectId) {
                this.subjectId = subjectId;
            }
        }

        public static class QualityGoodsBean {
            /**
             * feature : 卖点卖点
             * goodsId : 62
             * goodsName : 水果蔬菜-水果-芒果02
             * imagePath : http://resource.shiwaixiangcun.cn/group1/M00/00/51/rBKx51mnbW6Ae0zcAAA76G3dmhA407.jpg
             * minPrice : 3.55
             * subjectId : 245
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

        public static class DailySelectionListBean {
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
