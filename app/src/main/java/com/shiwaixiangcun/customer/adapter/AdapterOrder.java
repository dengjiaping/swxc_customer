package com.shiwaixiangcun.customer.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.OrderBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/15.
 */

public class AdapterOrder  extends BaseQuickAdapter<OrderBean.ElementsBean,BaseViewHolder> {

    public AdapterOrder(@LayoutRes int layoutResId, @Nullable List<OrderBean.ElementsBean> data) {
        super(R.layout.item_order, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderBean.ElementsBean item) {

    }
}
