package com.shiwaixiangcun.customer.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/26.
 */

public class RecoratingListBean implements Serializable {

    /**
     * authentication : false
     * certificates : null
     * decoratePlanDtos : null
     * id : 1
     * introduce : null
     * logo : null
     * name : test
     * phone : null
     * summary : test
     */

    private boolean authentication;
    private Object certificates;
    private Object decoratePlanDtos;
    private int id;
    private Object introduce;
    private String logo;
    private String name;
    private Object phone;
    private String summary;

    public boolean isAuthentication() {
        return authentication;
    }

    public void setAuthentication(boolean authentication) {
        this.authentication = authentication;
    }

    public Object getCertificates() {
        return certificates;
    }

    public void setCertificates(Object certificates) {
        this.certificates = certificates;
    }

    public Object getDecoratePlanDtos() {
        return decoratePlanDtos;
    }

    public void setDecoratePlanDtos(Object decoratePlanDtos) {
        this.decoratePlanDtos = decoratePlanDtos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getIntroduce() {
        return introduce;
    }

    public void setIntroduce(Object introduce) {
        this.introduce = introduce;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
