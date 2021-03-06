package com.shiwaixiangcun.customer.ui;

import com.shiwaixiangcun.customer.model.HouseSelectListBean;
import com.shiwaixiangcun.customer.model.InformationBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public interface IHouseToSellerView {
    /**
     * 设置过后的回调
     * @param result
     */
    void setBgaAdpaterAndClickResult(ResponseEntity result);

    void setHouseListResult(List<HouseSelectListBean> result);

    void setInformationResult(InformationBean result);
}
