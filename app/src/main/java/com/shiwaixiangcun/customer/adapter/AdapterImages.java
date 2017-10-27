package com.shiwaixiangcun.customer.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;

import java.util.List;


/**
 * @author XuJhin
 */
public class AdapterImages extends BaseQuickAdapter<String, BaseViewHolder> {
    public AdapterImages(@Nullable List<String> data) {
        super(R.layout.item_photo_selected, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView ivSelect = helper.getView(R.id.iv_delete_select);
        ImageView ivCheck = helper.getView(R.id.iv_check);
        ImageView ivThumb = helper.getView(R.id.iv_thumb);

        ivCheck.setVisibility(View.GONE);

        helper.addOnClickListener(R.id.iv_delete_select);
        ImageDisplayUtil.showImageView(mContext, item, ivThumb);


    }
}
