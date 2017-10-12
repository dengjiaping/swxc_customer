package com.shiwaixiangcun.customer.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.RecipeBean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */

public class AdapterRecipe extends BaseQuickAdapter<RecipeBean, BaseViewHolder> {
    public AdapterRecipe(@Nullable List<RecipeBean> data) {
        super(R.layout.item_recipe, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecipeBean item) {

    }
}
