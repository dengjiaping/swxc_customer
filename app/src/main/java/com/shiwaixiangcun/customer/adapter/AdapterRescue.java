package com.shiwaixiangcun.customer.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.RescueWayBean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/23.
 */

public class AdapterRescue extends BaseQuickAdapter<RescueWayBean, BaseViewHolder> {
    public AdapterRescue(@Nullable List<RescueWayBean> data) {
        super(R.layout.item_rescue, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RescueWayBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_content, item.getContent());
        ImageView imageView = helper.getView(R.id.iv_logo);
        Glide.with(mContext).load(item.getImage()).into(imageView);


    }
}
