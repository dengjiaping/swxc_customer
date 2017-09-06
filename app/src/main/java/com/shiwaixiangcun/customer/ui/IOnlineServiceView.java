package com.shiwaixiangcun.customer.ui;

import com.shiwaixiangcun.customer.model.InformationBean;
import com.shiwaixiangcun.customer.response.ResponseEntity;

/**
 * Created by Administrator on 2017/5/25.
 */

public interface IOnlineServiceView  {
    /**
     * 设置过后的回调
     * @param result
     */
    void setBgaAdpaterAndClickResult(ResponseEntity result);

    void setHaveImageResult(ResponseEntity result);

    void setInformation(InformationBean result);
}
