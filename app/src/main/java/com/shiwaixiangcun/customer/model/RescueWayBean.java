package com.shiwaixiangcun.customer.model;

/**
 * Created by Administrator on 2017/10/23.
 */

public class RescueWayBean {
    private String title;
    private String content;
    private int image;

    public RescueWayBean(String title, String content, int image) {
        this.title = title;
        this.content = content;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
