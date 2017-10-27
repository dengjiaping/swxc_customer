package com.shiwaixiangcun.customer.adapter;


import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.AddressBean;

import java.util.List;

/**
 * Date:  2017/9/13
 * Desc： eg
 * @author Administrator
 */

public class AdapterAddress extends BaseQuickAdapter<AddressBean, BaseViewHolder> {

    public AdapterAddress(@Nullable List<AddressBean> data) {
        super(R.layout.address, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressBean item) {
        helper.setText(R.id.tv_user_name, item.getDeliveryName());
        helper.setText(R.id.tv_user_phone, item.getDeliveryPhone());
        helper.setText(R.id.tv_address, item.getDeliveryAddress());
        if (item.isDefaulted())
            helper.getView(R.id.tv_flag).setVisibility(View.VISIBLE);

    }
}
