package com.shiwaixiangcun.customer.ui;

import com.shiwaixiangcun.customer.model.BloodPressureDataBean;
import com.shiwaixiangcun.customer.model.PageBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;

/**
 * Created by Administrator on 2017/5/25.
 */

public interface IBloodDataView {
    /**
     * 设置过后的回调
     * @param result
     */
    void setBgaAdpaterAndClickResult( ResponseEntity<PageBean<BloodPressureDataBean>> result);


}
