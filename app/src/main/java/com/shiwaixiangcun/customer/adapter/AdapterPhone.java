package com.shiwaixiangcun.customer.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.PhoneBean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/23.
 */

public class AdapterPhone extends BaseQuickAdapter<PhoneBean, BaseViewHolder> {
    public AdapterPhone(@Nullable List<PhoneBean> data) {
        super(R.layout.item_phone, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PhoneBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_content, item.getNumber());

    }
}
