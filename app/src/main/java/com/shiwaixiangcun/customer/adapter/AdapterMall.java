package com.shiwaixiangcun.customer.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.ElementBean;
import com.shiwaixiangcun.customer.utils.ArithmeticUtils;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2017/9/12
 */

public class AdapterMall extends BaseQuickAdapter<ElementBean.ElementsBean, BaseViewHolder> {
    public AdapterMall(@Nullable List<ElementBean.ElementsBean> data) {
        super(R.layout.suggest_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ElementBean.ElementsBean item) {
        helper.setText(R.id.tv_mall_des, item.getFeature());
        helper.setText(R.id.tv_mall_title, item.getGoodsName());
        helper.setText(R.id.tv_mall_price, "¥" + ArithmeticUtils.format(item.getMinPrice()));
        Glide.with(mContext).load(item.getImagePath()).crossFade().into((ImageView) helper.getView(R.id.iv_cover));
    }
}
