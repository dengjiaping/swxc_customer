package com.shiwaixiangcun.customer.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.SearchResult;

import java.util.List;

/**
 * Created by Administrator on 2017/9/8.
 */

public class AdapterSearchResult extends BaseQuickAdapter<SearchResult.DataBean.ElementsBean, BaseViewHolder> {


    public AdapterSearchResult(@Nullable List<SearchResult.DataBean.ElementsBean> data) {
        super(R.layout.item_search_result, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchResult.DataBean.ElementsBean item) {
        helper.setText(R.id.tv_title, item.getGoodsName());
        helper.setText(R.id.tv_desc, item.getFeature());
        helper.setText(R.id.tv_price, item.getMinPrice() + "");
        Glide.with(mContext).load(item.getImagePath()).crossFade().into((ImageView) helper.getView(R.id.iv_cover));

    }

}
