package com.shiwaixiangcun.customer.ui;

import com.shiwaixiangcun.customer.model.DefaultCityBean;

/**
 * Created by Administrator on 2017/5/25.
 */

public interface ICityDefaultView {
    /**
     * 设置过后的回调
     * @param result
     */
    void setBgaAdpaterAndClickResult(DefaultCityBean result);

    void setSearchCityResult(DefaultCityBean result);
}
