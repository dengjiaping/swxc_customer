package com.shiwaixiangcun.customer.http;

/**
 * Created by fyj on 2017/5/24.
 */
public interface CallBack {

    /**
     *  网络请求, 失败回调
     * @param e
     */
    void onFailed(Exception e);
}
