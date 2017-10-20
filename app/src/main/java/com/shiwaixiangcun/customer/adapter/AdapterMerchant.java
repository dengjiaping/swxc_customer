package com.shiwaixiangcun.customer.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.MerchantListBean;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/10/20.
 * 商铺Adapter
 */

public class AdapterMerchant extends BaseQuickAdapter<MerchantListBean.DataBean.ElementsBean, BaseViewHolder> {
    public AdapterMerchant(@Nullable List<MerchantListBean.DataBean.ElementsBean> data) {
        super(R.layout.item_merchant, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MerchantListBean.DataBean.ElementsBean item) {
        helper.setText(R.id.tv_merch_content, item.getFeature());
        helper.setText(R.id.tv_merch_name, item.getName());
        ImageView imageView = helper.getView(R.id.iv_merch);
        ImageDisplayUtil.showImageView(mContext, item.getCover(), imageView);

    }
}
