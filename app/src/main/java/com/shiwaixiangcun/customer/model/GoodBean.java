package com.shiwaixiangcun.customer.model;

/**
 * Created by Administrator on 2017/9/7.
 */

public class GoodBean {
    private String feature;
    private int goodsId;
    private String goodsName;
    private String imagePath;
    private int minPrice;
    private int subjectId;

    public GoodBean(String feature, int goodsId, String goodsName, String imagePath, int minPrice, int subjectId) {
        this.feature = feature;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.imagePath = imagePath;
        this.minPrice = minPrice;
        this.subjectId = subjectId;
    }

    public GoodBean() {
    }

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
