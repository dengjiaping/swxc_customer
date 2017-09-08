package com.shiwaixiangcun.customer.model;

import java.util.List;

/**
 * Created by Administrator on 2017/9/8.
 */

public class RightBean {

    private String titleName;
    private String categoryImg;
    private int id;
    private String name;
    private int parentId;
    private Object parentIds;
    private Object weight;
    private List<?> children;
    private String tag;
    private boolean isTitle;

    public RightBean(String name) {
        this.name = name;
    }

    public RightBean() {

    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(String categoryImg) {
        this.categoryImg = categoryImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public Object getParentIds() {
        return parentIds;
    }

    public void setParentIds(Object parentIds) {
        this.parentIds = parentIds;
    }

    public Object getWeight() {
        return weight;
    }

    public void setWeight(Object weight) {
        this.weight = weight;
    }

    public List<?> getChildren() {
        return children;
    }

    public void setChildren(List<?> children) {
        this.children = children;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }
}
