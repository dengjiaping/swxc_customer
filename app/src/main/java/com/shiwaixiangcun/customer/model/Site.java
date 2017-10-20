package com.shiwaixiangcun.customer.model;

/**
 * Created by Administrator on 2017/10/11.
 */

public class Site {

    /**
     * defaultShow : true
     * name : 天鹅堡森林公园
     */

    private boolean defaultShow;
    private String name;

    public Site(boolean defaultShow, String name) {
        this.defaultShow = defaultShow;
        this.name = name;
    }

    public boolean isDefaultShow() {
        return defaultShow;
    }

    public void setDefaultShow(boolean defaultShow) {
        this.defaultShow = defaultShow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
