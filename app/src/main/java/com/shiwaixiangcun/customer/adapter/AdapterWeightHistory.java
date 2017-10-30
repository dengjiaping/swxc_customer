package com.shiwaixiangcun.customer.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.WeightBean;
import com.shiwaixiangcun.customer.utils.ArithmeticUtils;
import com.shiwaixiangcun.customer.utils.DateUtil;

import java.util.List;


/**
 * Created by Administrator on 2016/7/13.
 */
public class AdapterWeightHistory extends BaseQuickAdapter<WeightBean.ElementsBean, BaseViewHolder> {


    public AdapterWeightHistory(@Nullable List<WeightBean.ElementsBean> data) {
        super(R.layout.item_weight_history, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WeightBean.ElementsBean item) {
        helper.setText(R.id.tv_weight_data_time, DateUtil.getMillon(item.getCreateTime()));
        helper.setText(R.id.tv_weight_data, ArithmeticUtils.format(item.getWeight()) + " kg");
        helper.setText(R.id.tv_weight_data_bmi, ArithmeticUtils.format(item.getBmi()));

    }

}
