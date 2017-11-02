package com.shiwaixiangcun.customer.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.GoodDetail;
import com.shiwaixiangcun.customer.ui.activity.ImageGalleryActivity;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.utils.StringUtil;
import com.shiwaixiangcun.customer.widget.CircleImageView;
import com.shiwaixiangcun.customer.widget.ratingBar.BaseRatingBar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2017/10/30
 * 评论列表
 */

public class AdapterEvaluate extends BaseQuickAdapter<GoodDetail.DataBean.EvaluatesBean, BaseViewHolder> {


    public AdapterEvaluate(@Nullable List<GoodDetail.DataBean.EvaluatesBean> data) {
        super(R.layout.item_evaluate, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, final GoodDetail.DataBean.EvaluatesBean item) {
        if (StringUtil.isEmpty(item.getContent()) && item.getImages().size() == 0) {
            helper.setText(R.id.tv_content, "买家只打了分,没有图文评价");
        } else {
            helper.setText(R.id.tv_content, item.getContent());
        }
        helper.setText(R.id.tv_attr, item.getAttrDescription());
        helper.setText(R.id.tv_nickname, item.getNick());
        helper.setText(R.id.tv_date, item.getEvaluateTime());
        BaseRatingBar ratingBar = helper.getView(R.id.ratingbar);
        CircleImageView civAvatar = helper.getView(R.id.iv_head_my_image);
        RecyclerView recyclerView = helper.getView(R.id.rv_image);
        ImageDisplayUtil.showImageView(mContext, R.drawable.defalt_image, item.getAvatar(), civAvatar);
        ratingBar.setRating(item.getScore());
        ratingBar.setTouchable(false);
        //设置用户评价上传的图片
        if (item.getImages().size() > 0) {

            final ArrayList<String> imageList = new ArrayList<>();
            for (GoodDetail.DataBean.EvaluatesBean.ImagesBean imageData : item.getImages()) {
                imageList.add(imageData.getAccessUrl());
            }
            recyclerView.setVisibility(View.VISIBLE);
            AdapterEvaluateImage mAdapterImages = new AdapterEvaluateImage(item.getImages());
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(mAdapterImages);
            mAdapterImages.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, ImageGalleryActivity.class);
                    intent.putExtra("imageList", imageList);
                    intent.putExtra("imageTitle", "");
                    mContext.startActivity(intent);
                }
            });
        } else {
            recyclerView.setVisibility(View.GONE);
        }
    }
}
