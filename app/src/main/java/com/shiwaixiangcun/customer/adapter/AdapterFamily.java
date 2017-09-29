package com.shiwaixiangcun.customer.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.HealthUserBean;
import com.shiwaixiangcun.customer.utils.DisplayUtil;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.utils.StringUtil;
import com.shiwaixiangcun.customer.widget.CircleImageView;

/**
 * Created by Administrator on 2017/9/25.
 */

public class AdapterFamily extends BaseQuickAdapter<HealthUserBean, BaseViewHolder> {

    private int checkedPosition = 0;

    public AdapterFamily() {
        super(R.layout.layout_physical);
    }

    @Override
    protected void convert(BaseViewHolder helper, HealthUserBean item) {
        CircleImageView circleImageView = helper.getView(R.id.civ_user_avatar);
        ImageView ivFlag = helper.getView(R.id.iv_flag);
        if (checkedPosition == helper.getAdapterPosition()) {
            circleImageView.setBorderWidth(DisplayUtil.dip2px(mContext, 3));
        } else {
            circleImageView.setBorderWidth(0);
        }
        Log.e("adapter", item.getName().trim());
        if (StringUtil.isEmpty(item.getName().trim())) {
            helper.setText(R.id.tv_family_name, "");
        } else {
            helper.setText(R.id.tv_family_name, "(" + item.getName() + ")");
        }
        helper.setText(R.id.tv_relationship, item.getRelationship() + "");
        ImageDisplayUtil.showImageView(mContext, item.getAvatar(), circleImageView);
        if (item.getTotalStatus() == null) {
            return;
        }
        String status = item.getTotalStatus();
        switch (status) {
            case "DANGER":
                circleImageView.setBorderColor(Color.parseColor("#E94F4F"));
                ivFlag.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(R.drawable.urgency).into(ivFlag);
                break;
            case "NORMAL":
                circleImageView.setBorderColor(Color.parseColor("#1CCC8C"));
                ivFlag.setVisibility(View.GONE);
                break;
            case "WARNING":
                circleImageView.setBorderColor(Color.parseColor("#1CCC8C"));
                ivFlag.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(R.drawable.waring).into(ivFlag);
                break;
        }

    }

    /**
     * 设置item为选中状态
     *
     * @param position
     */
    public void setSelected(int position) {
        this.checkedPosition = position;
        notifyDataSetChanged();
    }
}
