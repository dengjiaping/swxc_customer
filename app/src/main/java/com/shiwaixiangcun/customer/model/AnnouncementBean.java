package com.shiwaixiangcun.customer.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/25.
 */

public class AnnouncementBean implements Serializable {


    /**
     * articleShowType : ACTIVE
     * categoryId : 104
     * categoryName : 活动
     * coverPath : http://resource.shiwaixiangcun.cn/group1/M00/00/13/rBKx51kmQRWAOhxhABk27CHyoFU947.png
     * id : 13
     * publishTime : 1495679255000
     * source : 来源123
     * summary : huodong2

     * title : 测试保存2
     * top : false
     */

    private String articleShowType;
    private int categoryId;
    private String categoryName;
    private String coverPath;
    private int id;
    private long publishTime;
    private String source;
    private String summary;
    private String title;
    private boolean top;


    public String getArticleShowType() {
        return articleShowType;
    }

    public void setArticleShowType(String articleShowType) {
        this.articleShowType = articleShowType;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }
}
