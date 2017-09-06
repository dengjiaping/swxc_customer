package com.shiwaixiangcun.customer.ui;

import com.shiwaixiangcun.customer.model.HousePhoneBean;
import com.shiwaixiangcun.customer.response.ResponseEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public interface IHousePhoneView {
    /**
     * 设置过后的回调
     * @param result
     */
    void setBgaAdpaterAndClickResult(ResponseEntity<List<HousePhoneBean>>  result);

    void setPhoneResult(ResponseEntity  result);


}
