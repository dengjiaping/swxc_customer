package com.shiwaixiangcun.customer.ui;

import com.shiwaixiangcun.customer.model.BloodPressurebean;
import com.shiwaixiangcun.customer.model.ResponseEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public interface IBloodPressureView {
    /**
     * 设置过后的回调
     * @param result
     */
    void setBgaAdpaterAndClickResult(ResponseEntity<List<BloodPressurebean>> result);


}
