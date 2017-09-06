package com.shiwaixiangcun.customer.ui;

import com.shiwaixiangcun.customer.model.WeightBean;
import com.shiwaixiangcun.customer.response.PageBean;
import com.shiwaixiangcun.customer.response.ResponseEntity;

/**
 * Created by Administrator on 2017/5/25.
 */

public interface IWeightHistoryView {
    /**
     * 设置过后的回调
     * @param result
     */
    void setBgaAdpaterAndClickResult( ResponseEntity<PageBean<WeightBean>> result);


}
