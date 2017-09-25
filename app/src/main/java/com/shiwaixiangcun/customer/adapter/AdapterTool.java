package com.shiwaixiangcun.customer.adapter;

import android.support.annotation.LayoutRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.ToolBean;

/**
 * Created by Administrator on 2017/9/25.
 */

public class AdapterTool extends BaseQuickAdapter<ToolBean, BaseViewHolder> {
    public AdapterTool(@LayoutRes int layoutResId) {
        super(R.layout.layout_tool);
    }

    @Override
    protected void convert(BaseViewHolder helper, ToolBean item) {
        helper.setText(R.id.tv_tool_name, item.name);
        Glide.with(mContext).load(item.pic).into((ImageView) helper.getView(R.id.iv_tool));

    }


}
