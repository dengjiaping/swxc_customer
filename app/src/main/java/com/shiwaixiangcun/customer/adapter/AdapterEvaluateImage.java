package com.shiwaixiangcun.customer.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.GoodDetail;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;

import java.util.List;

/**
 * @author Administrator
 * @date 2017/10/31
 * 评论中的图片
 */

public class AdapterEvaluateImage extends BaseQuickAdapter<GoodDetail.DataBean.EvaluatesBean.ImagesBean, BaseViewHolder> {
    public AdapterEvaluateImage(@Nullable List<GoodDetail.DataBean.EvaluatesBean.ImagesBean> data) {
        super(R.layout.item_photo_selected, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodDetail.DataBean.EvaluatesBean.ImagesBean item) {
        ImageView ivSelect = helper.getView(R.id.iv_delete_select);
        ImageView ivCheck = helper.getView(R.id.iv_check);
        ImageView ivThumb = helper.getView(R.id.iv_thumb);

        ivCheck.setVisibility(View.GONE);

        helper.addOnClickListener(R.id.iv_delete_select);
        ImageDisplayUtil.showImageView(mContext, item.getThumbImageURL(), ivThumb);
        ivSelect.setVisibility(View.GONE);


    }


}
