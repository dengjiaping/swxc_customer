package com.shiwaixiangcun.customer.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/15.
 */


public class LzyResponse<T> implements Serializable {

    private static final long serialVersionUID = 5213230387175987834L;

    public int code;
    public String msg;
    public T data;

    @Override
    public String toString() {
        return "LzyResponse{\n" +//
                "\tcode=" + code + "\n" +//
                "\tmsg='" + msg + "\'\n" +//
                "\tdata=" + data + "\n" +//
                '}';

    }
}
