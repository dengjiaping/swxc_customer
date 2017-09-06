package com.shiwaixiangcun.customer.ui;

import com.shiwaixiangcun.customer.model.WeatherDataBean;

/**
 * Created by Administrator on 2017/5/25.
 */

public interface IWeatherView {
    /**
     * 设置过后的回调
     * @param result
     */
    void setBgaAdpaterAndClickResult(WeatherDataBean result);


}
