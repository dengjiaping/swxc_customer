package com.shiwaixiangcun.customer.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.RecipeBean;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.utils.StringUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */

public class AdapterRecipe extends BaseQuickAdapter<RecipeBean.ElementsBean, BaseViewHolder> {
    public AdapterRecipe(@Nullable List<RecipeBean.ElementsBean> data) {
        super(R.layout.item_recipe, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecipeBean.ElementsBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_content, item.getSummary());
        ImageView ivCover = helper.getView(R.id.iv_cover);
        if (StringUtil.isEmpty(item.getCover())) {

            Glide.with(mContext).load(R.drawable.image_error).into(ivCover);
        } else {
            ImageDisplayUtil.showImageView(mContext, R.drawable.image_error, item.getCover(), ivCover);
        }

    }
}
