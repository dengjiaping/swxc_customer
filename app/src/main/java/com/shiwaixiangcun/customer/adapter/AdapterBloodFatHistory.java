package com.shiwaixiangcun.customer.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.BloodFatBean;
import com.shiwaixiangcun.customer.utils.DateUtil;

import java.util.List;


/**
 * Created by Administrator on 2016/7/13.
 */
public class AdapterBloodFatHistory extends BaseQuickAdapter<BloodFatBean.ElementsBean, BaseViewHolder> {

    public AdapterBloodFatHistory(@Nullable List<BloodFatBean.ElementsBean> data) {
        super(R.layout.item_fat_history, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BloodFatBean.ElementsBean item) {
        helper.setText(R.id.tv_fat_data_time, DateUtil.getMillon(item.getCreateTime()));
        helper.setText(R.id.tv_item_totalCholesterol, item.getTotalCholesterol() + "");
        helper.setText(R.id.tv_item_triglyceride, item.getTriglyceride() + "");
        helper.setText(R.id.tv_item_topLipo, item.getTopLipo() + "");
        helper.setText(R.id.tv_item_lowLipo, item.getLowLipo() + "");
    }


}
