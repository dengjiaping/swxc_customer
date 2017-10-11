package com.shiwaixiangcun.customer.ui.activity;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.Site;

import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */

public class AdapterSite extends BaseQuickAdapter<Site, BaseViewHolder> {
    public AdapterSite(@Nullable List<Site> data) {
        super(R.layout.item_site, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Site item) {
        helper.setText(R.id.tv_site, item.site);

    }
}
