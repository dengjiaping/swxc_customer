package com.shiwaixiangcun.customer.adapter;/**
 * Author:Administrator
 * Date:  2017/9/13
 * Desc： eg
 */

import android.support.annotation.LayoutRes;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.AddressBean;

/**
 * Created by Administrator on 2017/9/13.
 */

public class AdapterAddress extends BaseQuickAdapter<AddressBean, BaseViewHolder> {
    public AdapterAddress(@LayoutRes int layoutResId) {
        //// TODO: 2017/9/13 地址 
        super(R.layout.address);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressBean item) {

    }
}
