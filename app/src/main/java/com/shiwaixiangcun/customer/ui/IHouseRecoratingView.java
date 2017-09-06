package com.shiwaixiangcun.customer.ui;

import com.shiwaixiangcun.customer.model.RecoratingListBean;
import com.shiwaixiangcun.customer.response.PageBean;
import com.shiwaixiangcun.customer.response.ResponseEntity;

/**
 * Created by Administrator on 2017/5/25.
 */

public interface IHouseRecoratingView {
    /**
     * 设置过后的回调
     * @param result
     */
    void setBgaAdpaterAndClickResult(ResponseEntity<PageBean<RecoratingListBean>> result);


}
