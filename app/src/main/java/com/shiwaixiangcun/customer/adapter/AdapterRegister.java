package com.shiwaixiangcun.customer.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.utils.DateUtil;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;

import java.util.List;

/**
 * @author Administrator
 * @date 2017/11/10
 */

public class AdapterRegister extends BaseQuickAdapter<RegisterBean.ElementsBean, BaseViewHolder> {
    public AdapterRegister(@Nullable List<RegisterBean.ElementsBean> data) {
        super(R.layout.item_register, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RegisterBean.ElementsBean item) {
        TextView tvStatue = helper.getView(R.id.tv_status);
        switch (item.getStatus()) {
            case "ActivityEnd":

                tvStatue.setTextColor(mContext.getResources().getColor(R.color.black_text_60));
                tvStatue.setText("活动已结束");
                break;
            case "Draft":
                tvStatue.setTextColor(mContext.getResources().getColor(R.color.word_green));
                tvStatue.setText("草稿");
                break;
            case "Enter":
                tvStatue.setTextColor(mContext.getResources().getColor(R.color.word_green));
                tvStatue.setText("活动报名中");
                break;
            case "EnterEnd":
                tvStatue.setTextColor(mContext.getResources().getColor(R.color.black_text_60));
                tvStatue.setText("报名已结束");
                break;
            case "NoEnter":
                tvStatue.setTextColor(mContext.getResources().getColor(R.color.word_green));
                tvStatue.setText("报名未开始");
                break;
            case "Off":
                tvStatue.setTextColor(mContext.getResources().getColor(R.color.word_green));
                tvStatue.setText("平台下架");
                break;
            case "Revoked":
                tvStatue.setTextColor(mContext.getResources().getColor(R.color.word_green));
                tvStatue.setText("已撤回");
                break;
            default:
                break;

        }

        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_location, item.getDetailAddress());
        helper.setText(R.id.tv_date, DateUtil.getCustomFormat(item.getActivityStartDate(), "MM月dd日"));
        ImageView imageView = helper.getView(R.id.iv_cover);
        ImageDisplayUtil.showImageView(mContext, item.getPoster().getThumbImageURL(), imageView);
    }
}
