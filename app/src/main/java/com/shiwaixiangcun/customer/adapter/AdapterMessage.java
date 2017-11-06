package com.shiwaixiangcun.customer.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.MessageBean;
import com.shiwaixiangcun.customer.utils.DateUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/10/12.
 */

public class AdapterMessage extends BaseQuickAdapter<MessageBean.ElementsBean, BaseViewHolder> {
    public AdapterMessage(@Nullable List<MessageBean.ElementsBean> data) {
        super(R.layout.item_message, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean.ElementsBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_content, item.getContent());
        String day = DateUtil.getDay(item.getSendDate());
        helper.setText(R.id.tv_date, day);
        ImageView imageView = helper.getView(R.id.iv_flag);
        switch (item.getMessageCoreType()) {
            case "INTERROGATION":
                Glide.with(mContext).load(R.drawable.message_doctor).into(imageView);
                break;
            case "HEALTHMESSAGE":
                Glide.with(mContext).load(R.drawable.message_health).into(imageView);
                break;
        }


    }
}
