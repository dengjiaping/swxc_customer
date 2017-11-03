package com.shiwaixiangcun.customer.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.RightsRecordBean;
import com.shiwaixiangcun.customer.utils.DateUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/10/16.
 */

public class AdapterRight extends BaseQuickAdapter<RightsRecordBean.ElementsBean, BaseViewHolder> {
    public AdapterRight(@Nullable List<RightsRecordBean.ElementsBean> data) {
        super(R.layout.item_right_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RightsRecordBean.ElementsBean item) {

        helper.setText(R.id.tv_number, item.getNumber());
        helper.setText(R.id.tv_content, item.getContent());
        switch (item.getStatus()) {
            case "ACCEPTED":
                helper.setBackgroundRes(R.id.tv_stature, R.drawable.shape_stature_green);
                helper.setText(R.id.tv_stature, "受理中");
                break;
            case "FINISHED":
                helper.setBackgroundRes(R.id.tv_stature, R.drawable.shape_stature_gray);
                helper.setText(R.id.tv_stature, "已完成");
            default:
                break;
        }
        helper.setText(R.id.tv_date, DateUtil.getSecond(item.getTime()));

    }
}
