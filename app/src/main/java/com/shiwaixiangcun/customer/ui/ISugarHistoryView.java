package com.shiwaixiangcun.customer.ui;

import com.shiwaixiangcun.customer.model.BloodSugarBean;

/**
 * Created by Administrator on 2017/5/25.
 */

public interface ISugarHistoryView {
    /**
     * 设置过后的回调
     * @param result
     */
    void setBgaAdpaterAndClickResult(BloodSugarBean result);


}
