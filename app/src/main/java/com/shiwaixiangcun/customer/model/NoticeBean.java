package com.shiwaixiangcun.customer.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Administrator
 * @date 2017/5/25
 */

public class NoticeBean implements Parcelable {
    public static final Parcelable.Creator<NoticeBean> CREATOR = new Parcelable.Creator<NoticeBean>() {
        @Override
        public NoticeBean createFromParcel(Parcel source) {
            return new NoticeBean(source);
        }

        @Override
        public NoticeBean[] newArray(int size) {
            return new NoticeBean[size];
        }
    };
    /**
     * categoryId : null
     * categoryName : null
     * coverId : 8946
     * coverPath : http://resource.shiwaixiangcun.cn/group1/M00/00/9F/rBKx51oJM8WAODodAAA9WpZ8tQw888.png
     * id : 25
     * publishTime : 1510638840000
     * articleShowType : ACTIVITY
     * source : PROPERTY
     * summary : 辽宁省鞍山市傻大个睡觉啊过的痕迹撒
     * title : 物业活动-限额3人
     * top : false
     */

    private int categoryId;
    private String categoryName;
    private int coverId;
    private String coverPath;
    private int id;
    private long publishTime;
    private String articleShowType;
    private String source;
    private String summary;
    private String title;
    private boolean top;

    public NoticeBean() {
    }

    protected NoticeBean(Parcel in) {
        this.categoryId = in.readInt();
        this.categoryName = in.readString();
        this.coverId = in.readInt();
        this.coverPath = in.readString();
        this.id = in.readInt();
        this.publishTime = in.readLong();
        this.articleShowType = in.readString();
        this.source = in.readString();
        this.summary = in.readString();
        this.title = in.readString();
        this.top = in.readByte() != 0;
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

    public int getCoverId() {
        return coverId;
    }

    public void setCoverId(int coverId) {
        this.coverId = coverId;
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

    public String getArticleShowType() {
        return articleShowType;
    }

    public void setArticleShowType(String articleShowType) {
        this.articleShowType = articleShowType;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.categoryId);
        dest.writeString(this.categoryName);
        dest.writeInt(this.coverId);
        dest.writeString(this.coverPath);
        dest.writeInt(this.id);
        dest.writeLong(this.publishTime);
        dest.writeString(this.articleShowType);
        dest.writeString(this.source);
        dest.writeString(this.summary);
        dest.writeString(this.title);
        dest.writeByte(this.top ? (byte) 1 : (byte) 0);
    }
}

