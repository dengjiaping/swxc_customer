package com.shiwaixiangcun.customer.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.BloodSugarBean;
import com.shiwaixiangcun.customer.utils.DateUtil;

import java.util.List;


/**
 * Created by Administrator on 2016/7/13.
 * <p>
 * 血糖历史Adapter
 */
public class AdapterSugarHistory extends BaseQuickAdapter<BloodSugarBean.ElementsBean, BaseViewHolder> {

    public AdapterSugarHistory(@Nullable List<BloodSugarBean.ElementsBean> data) {
        super(R.layout.item_sugar_history, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BloodSugarBean.ElementsBean item) {
        helper.setText(R.id.tv_sugar_data_time, DateUtil.getMillon(item.getCreateTime()));
        if (item.getSugarStatus().equals("KF")) {
            helper.setText(R.id.tv_kf, "空腹");
        } else if (item.getSugarStatus().equals("FH")) {
            helper.setText(R.id.tv_kf, "饭后2小时");
        }
        helper.setText(R.id.tv_sugar_data_shrinkBlood, item.getBloodSugar() + "");

    }
}
