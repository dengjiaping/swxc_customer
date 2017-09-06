package com.shiwaixiangcun.customer.mall;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;

import java.util.List;

/**
 * Created by Administrator on 2017/9/6.
 */

public class HotListHAdapter extends BaseQuickAdapter<HotThing, BaseViewHolder> {
    public HotListHAdapter(List<HotThing> data) {
        super(R.layout.item_hotlists_h_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotThing item) {
        ImageView mImageView = helper.getView(R.id.item_img);
        Glide.with(mContext).load(item.Picture).into(mImageView);
        helper.setText(R.id.item_title, item.ProductName);
        helper.setText(R.id.item_type, item.PromotionInfoPrice);
    }
}
