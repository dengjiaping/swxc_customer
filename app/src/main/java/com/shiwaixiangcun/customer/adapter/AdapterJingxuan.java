package com.shiwaixiangcun.customer.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.MallBean;
import com.shiwaixiangcun.customer.utils.ArithmeticUtils;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/9/6.
 */

public class AdapterJingxuan extends BaseQuickAdapter<MallBean.DataBean.DailySelectionListBean, BaseViewHolder> {


    public AdapterJingxuan(@Nullable List<MallBean.DataBean.DailySelectionListBean> data) {
        super(R.layout.item_jingxuan, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MallBean.DataBean.DailySelectionListBean item) {
        helper.setText(R.id.tv_daily_price, "¥ " + ArithmeticUtils.format(item.getMinPrice()));
        helper.setText(R.id.tv_daily_title, item.getGoodsName());

        ImageDisplayUtil.showImageView(mContext, item.getImagePath(), (ImageView) helper.getView(R.id.iv_daily_icon));
    }

    public void clearData() {

    }


}
