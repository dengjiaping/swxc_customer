package com.shiwaixiangcun.customer.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.MyFamilyBean;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.widget.CircleImageView;

import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 */

public class AdapterMyFamily extends BaseQuickAdapter<MyFamilyBean.DataBean, BaseViewHolder> {
    public AdapterMyFamily(@Nullable List<MyFamilyBean.DataBean> data) {
        super(R.layout.item_family, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyFamilyBean.DataBean item) {
        helper.setText(R.id.tv_relationship, item.getRelationship());
        CircleImageView ivAvatar = helper.getView(R.id.iv_avatar);
        ImageDisplayUtil.showImageView(mContext, item.getAvatar(), ivAvatar);

    }
}
