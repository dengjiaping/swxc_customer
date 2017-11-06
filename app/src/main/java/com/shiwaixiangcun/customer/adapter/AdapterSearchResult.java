package com.shiwaixiangcun.customer.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.ElementBean;
import com.shiwaixiangcun.customer.utils.ArithmeticUtils;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/9/8.
 */

public class AdapterSearchResult extends BaseQuickAdapter<ElementBean.ElementsBean, BaseViewHolder> {


    public AdapterSearchResult(@Nullable List<ElementBean.ElementsBean> data) {
        super(R.layout.item_search_result, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ElementBean.ElementsBean item) {
        helper.setText(R.id.tv_title, item.getGoodsName());
        helper.setText(R.id.tv_desc, item.getFeature());
        helper.setText(R.id.tv_price, "¥ " + ArithmeticUtils.format(item.getMinPrice()));
        ImageDisplayUtil.showImageView(mContext, item.getImagePath(), (ImageView) helper.getView(R.id.iv_cover));

    }

}
