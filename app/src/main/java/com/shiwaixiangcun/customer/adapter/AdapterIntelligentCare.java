package com.shiwaixiangcun.customer.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.ToolCategoryBean;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;

/**
 * 智能关爱Adapter
 *
 * @author Administrator
 * @date 2017/11/9
 */

public class AdapterIntelligentCare extends BaseQuickAdapter<ToolCategoryBean.ChildrenBeanX.ChildrenBean, BaseViewHolder> {
    public AdapterIntelligentCare() {
        super(R.layout.item_intelligent_care);
    }

    @Override
    protected void convert(BaseViewHolder helper, ToolCategoryBean.ChildrenBeanX.ChildrenBean item) {
        helper.setText(R.id.tv_detail_title, item.getName());
        helper.setText(R.id.tv_detail_content, item.getRemark());
        helper.setText(R.id.tv_status, "未绑定");
        ImageView ivCover = helper.getView(R.id.iv_detail_cover);
        ImageDisplayUtil.showImageView(mContext, item.getImageLink(), ivCover);
        helper.addOnClickListener(R.id.llayout_location);


    }

}