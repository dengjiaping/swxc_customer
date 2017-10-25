package com.shiwaixiangcun.customer.model;

/**
 * Created by Administrator on 2017/10/23.
 */

public class PhoneBean {
    private String title;
    private String number;

    public PhoneBean(String title, String number) {
        this.title = title;
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
