package com.shiwaixiangcun.customer.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.GoodDetail;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.widget.CircleImageView;
import com.shiwaixiangcun.customer.widget.ratingBar.BaseRatingBar;

import java.util.List;

/**
 * @author Administrator
 * @date 2017/10/30
 */

public class AdapterEvaluate extends BaseQuickAdapter<GoodDetail.DataBean.EvaluatesBean, BaseViewHolder> {
    public AdapterEvaluate(@Nullable List<GoodDetail.DataBean.EvaluatesBean> data) {
        super(R.layout.item_evaluate, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodDetail.DataBean.EvaluatesBean item) {
        helper.setText(R.id.tv_content, item.getContent());
        helper.setText(R.id.tv_attr, item.getAttrDescription());
        helper.setText(R.id.tv_nickname, item.getNick());
        helper.setText(R.id.tv_date, item.getEvaluateTime());
        BaseRatingBar ratingBar = helper.getView(R.id.ratingbar);
        CircleImageView civAvatar = helper.getView(R.id.iv_head_my_image);
        ImageDisplayUtil.showImageView(mContext, item.getAvatar(), civAvatar);

        ratingBar.setRating(item.getScore());
        ratingBar.setTouchable(false);

    }
}
