package com.shiwaixiangcun.customer.adapter;

import android.support.annotation.LayoutRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.ToolCategoryBean;

/**
 * Created by Administrator on 2017/9/25.
 */

public class AdapterTool extends BaseQuickAdapter<ToolCategoryBean.ChildrenBeanX, BaseViewHolder> {
    public AdapterTool(@LayoutRes int layoutResId) {
        super(R.layout.layout_tool);
    }

    @Override
    protected void convert(BaseViewHolder helper, ToolCategoryBean.ChildrenBeanX item) {
        helper.setText(R.id.tv_tool_name, item.getName());
        Glide.with(mContext).load(item.getImageLink()).into((ImageView) helper.getView(R.id.iv_tool));

    }


}
