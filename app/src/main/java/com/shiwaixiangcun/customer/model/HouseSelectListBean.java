package com.shiwaixiangcun.customer.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/25.
 */

public class HouseSelectListBean implements Serializable {
    private int id;
    private String numberDesc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumberDesc() {
        return numberDesc;
    }

    public void setNumberDesc(String numberDesc) {
        this.numberDesc = numberDesc;
    }
}
