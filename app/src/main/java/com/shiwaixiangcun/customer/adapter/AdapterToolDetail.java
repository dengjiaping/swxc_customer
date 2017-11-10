package com.shiwaixiangcun.customer.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.ToolCategoryBean;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;

/**
 *
 * @author Administrator
 * @date 2017/10/19
 */

public class AdapterToolDetail extends BaseQuickAdapter<ToolCategoryBean.ChildrenBeanX.ChildrenBean, BaseViewHolder> {
    public AdapterToolDetail() {
        super(R.layout.item_tool_detail);
    }

    @Override
    protected void convert(BaseViewHolder helper, ToolCategoryBean.ChildrenBeanX.ChildrenBean item) {
        helper.setText(R.id.tv_detail_title, item.getName());
        helper.setText(R.id.tv_detail_content, item.getRemark());
        ImageView ivCover = helper.getView(R.id.iv_detail_cover);
        ImageView ivRight = helper.getView(R.id.iv_right);
        ImageDisplayUtil.showImageView(mContext, item.getImageLink(), ivCover);
        Glide.with(mContext).load(R.drawable.to_right).into(ivRight);


    }
}