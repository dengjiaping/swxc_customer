package com.shiwaixiangcun.customer.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.HeartRateBean;
import com.shiwaixiangcun.customer.utils.DateUtil;

import java.util.List;


/**
 *
 * @author Administrator
 * @date 2016/7/13
 * 心率数据的列表Adapter
 */
public class AdapterHeartRate extends BaseQuickAdapter<HeartRateBean.ElementsBean, BaseViewHolder> {


    public AdapterHeartRate(@Nullable List<HeartRateBean.ElementsBean> data) {
        super(R.layout.item_heart_rate, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, HeartRateBean.ElementsBean item) {
        helper.setText(R.id.tv_heartate_list_time, DateUtil.getMillon(item.getCreateTime()));

        helper.setText(R.id.tv_heatate_list_data, "" + item.getHeartRate());


    }


}
