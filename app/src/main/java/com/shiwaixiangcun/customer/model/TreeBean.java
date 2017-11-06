package com.shiwaixiangcun.customer.model;

import java.util.List;

/**
 * Created by Administrator on 2017/10/18.
 */

public class TreeBean {

    /**
     * appCategoryStatus : H5
     * authorization : false
     * children : []
     * imageLink : null
     * link : www
     * name : 高血压食谱
     * sign : null
     */

    private String appCategoryStatus;
    private boolean authorization;
    private String imageLink;
    private String link;
    private String name;
    private String sign;
    private List<?> children;

    public String getAppCategoryStatus() {
        return appCategoryStatus;
    }

    public void setAppCategoryStatus(String appCategoryStatus) {
        this.appCategoryStatus = appCategoryStatus;
    }

    public boolean isAuthorization() {
        return authorization;
    }

    public void setAuthorization(boolean authorization) {
        this.authorization = authorization;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public List<?> getChildren() {
        return children;
    }

    public void setChildren(List<?> children) {
        this.children = children;
    }
}
