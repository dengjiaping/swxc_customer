package com.shiwaixiangcun.customer.adapter;

import android.util.Log;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.HealthUserBean;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.widget.CircleImageView;

/**
 * Created by Administrator on 2017/9/25.
 */

public class AdapterFamily extends BaseQuickAdapter<HealthUserBean, BaseViewHolder> {
    public AdapterFamily() {
        super(R.layout.layout_physical);
    }

    @Override
    protected void convert(BaseViewHolder helper, HealthUserBean item) {
        helper.setText(R.id.tv_user_name, item.getRelationship());
        CircleImageView circleImageView = helper.getView(R.id.civ_user_avatar);
        ImageView ivFlag = helper.getView(R.id.iv_flag);
        ImageDisplayUtil.showImageView(mContext, item.getAvatar(), circleImageView);
        if (item.getTotalStatus() == null) {
            return;
        }
        String status = item.getTotalStatus();
        Log.e("adapter", status);
//        switch (status) {
//            case "DANGER":
//                circleImageView.setBorderColor(Color.parseColor("#E94F4F"));
//                ivFlag.setVisibility(View.VISIBLE);
//                Glide.with(mContext).load(R.drawable.urgency).into(ivFlag);
//                break;
//            case "NORMAL":
//                circleImageView.setBorderColor(Color.parseColor("#1CCC8C"));
//                ivFlag.setVisibility(View.GONE);
//                break;
//
//            case "WARNING":
//                circleImageView.setBorderColor(Color.parseColor("#1CCC8C"));
//                ivFlag.setVisibility(View.VISIBLE);
//                Glide.with(mContext).load(R.drawable.waring).into(ivFlag);
//                break;


//        }

    }
}
