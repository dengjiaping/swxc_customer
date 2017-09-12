package com.shiwaixiangcun.customer.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.MallGoods;

import java.util.List;

/**
 * Created by Administrator on 2017/9/12.
 */

public class AdapterMall extends BaseQuickAdapter<MallGoods.DataBean.ElementsBean, BaseViewHolder> {
    public AdapterMall(@Nullable List<MallGoods.DataBean.ElementsBean> data) {
        super(R.layout.suggest_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MallGoods.DataBean.ElementsBean item) {
        helper.setText(R.id.tv_mall_des, item.getFeature());
        helper.setText(R.id.tv_mall_title, item.getGoodsName());
        helper.setText(R.id.tv_mall_price, "¥" + item.getMinPrice());
        Glide.with(mContext).load(item.getImagePath()).crossFade().into((ImageView) helper.getView(R.id.iv_cover));
    }
}
