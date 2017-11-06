package com.shiwaixiangcun.customer.ui;

import com.shiwaixiangcun.customer.model.AllMerchBean;
import com.shiwaixiangcun.customer.model.SurroundMerchantTypeBean;

/**
 * Created by Administrator on 2017/5/25.
 */

public interface IHomeSurroundView {
    /**
     * 设置过后的回调
     * @param result
     */
    void setBgaAdpaterAndClickResult(SurroundMerchantTypeBean result);

    void setAllMerchResult(AllMerchBean result);


}
