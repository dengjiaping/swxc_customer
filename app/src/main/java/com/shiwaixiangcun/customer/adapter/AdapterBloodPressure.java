package com.shiwaixiangcun.customer.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.BloodPressureBean;
import com.shiwaixiangcun.customer.utils.DateUtil;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2017/9/29
 * 血压历史Adapter
 */

public class AdapterBloodPressure extends BaseQuickAdapter<BloodPressureBean.ElementsBean, BaseViewHolder> {
    public AdapterBloodPressure(@Nullable List<BloodPressureBean.ElementsBean> data) {
        super(R.layout.item_blood_pressure_data, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BloodPressureBean.ElementsBean item) {

        helper.setText(R.id.tv_pressure_data_time, DateUtil.getSecond(item.getCreateTime()));
        helper.setText(R.id.tv_pressure_data_shrinkBlood, item.getShrinkBlood() + "");
        helper.setText(R.id.tv_pressure_data_relaxationBlood, item.getRelaxationBlood() + "");

    }
}
