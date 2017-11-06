package com.shiwaixiangcun.customer.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.AfterServiceBean;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2017/10/13
 * 售后服务
 */

public class AdapterAfterService extends BaseQuickAdapter<AfterServiceBean.ElementsBean, BaseViewHolder> {
    public AdapterAfterService(@Nullable List<AfterServiceBean.ElementsBean> data) {
        super(R.layout.item_after_service, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AfterServiceBean.ElementsBean item) {
        ImageView mIvCover = helper.getView(R.id.iv_cover);
        TextView textView = helper.getView(R.id.tv_order_stature);
        helper.setText(R.id.tv_order_number, "服务单号  " + item.getNumber());
        helper.setText(R.id.tv_title, item.getGoodsName());
        helper.setText(R.id.tv_desc, item.getAttrDes());
        helper.setText(R.id.tv_order_amount, "x " + item.getAmount());
        ImageDisplayUtil.showImageView(mContext, item.getPath(), mIvCover);

        String stature = item.getStatus();
        if (stature == null) {
            return;
        }

        switch (stature) {
            case "Pending":
                helper.setText(R.id.tv_order_stature, "退款中");
                textView.setTextColor(mContext.getResources().getColor(R.color.word_green));
                break;
            case "RefundSuccess":
                helper.setText(R.id.tv_order_stature, "退款成功");
                textView.setTextColor(mContext.getResources().getColor(R.color.black_text_60));
                break;
            case "RefundFailed":
                helper.setText(R.id.tv_order_stature, "退款失败");
                textView.setTextColor(mContext.getResources().getColor(R.color.black_text_60));

                break;
            case "CancelServer":
                helper.setText(R.id.tv_order_stature, "服务取消");
                textView.setTextColor(mContext.getResources().getColor(R.color.black_text_60));
                break;

        }

    }
}
