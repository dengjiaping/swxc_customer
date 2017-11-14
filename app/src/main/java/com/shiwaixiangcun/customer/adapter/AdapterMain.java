package com.shiwaixiangcun.customer.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.NoticeBean;
import com.shiwaixiangcun.customer.utils.DateUtil;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;

import java.util.List;

/**
 * @author Administrator
 * @date 2017/10/17
 * 首页
 */

public class AdapterMain extends BaseMultiItemQuickAdapter<AdapterMain.MultipleItem, BaseViewHolder> {


    public AdapterMain(@Nullable List<MultipleItem> data) {
        super(data);
        addItemType(MultipleItem.IMAGE_RIGHT, R.layout.item_right_iv);
        addItemType(MultipleItem.IMAGE_BOTTOM, R.layout.item_bottom_iv);
        addItemType(MultipleItem.IMAGE_NULL, R.layout.item_no_iv);
        addItemType(MultipleItem.ACTIVITY, R.layout.item_activity);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {

        NoticeBean data = item.getData();


        switch (item.getItemType()) {
            case MultipleItem.IMAGE_RIGHT:
                helper.setText(R.id.tv_title, data.getTitle());
                helper.setText(R.id.tv_summary, data.getSummary());
                helper.setText(R.id.tv_source, data.getSource());
                helper.setText(R.id.tv_publishTime, DateUtil.getSecond(data.getPublishTime()));
                ImageView imageView = helper.getView(R.id.iv_coverPath);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ImageDisplayUtil.showImageView(mContext, data.getCoverPath(), imageView);
                break;
            case MultipleItem.IMAGE_BOTTOM:
                helper.setText(R.id.tv_title, data.getTitle());
                helper.setText(R.id.tv_source, data.getSource());
                helper.setText(R.id.tv_publishTime, DateUtil.getSecond(data.getPublishTime()));
                ImageView image = helper.getView(R.id.iv_coverPath);
                image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ImageDisplayUtil.showImageView(mContext, data.getCoverPath(), image);
                break;
            case MultipleItem.IMAGE_NULL:
                helper.setText(R.id.tv_title, data.getTitle());
                helper.setText(R.id.tv_summary, data.getSummary());
                helper.setText(R.id.tv_source, data.getSource());
                helper.setText(R.id.tv_publishTime, DateUtil.getSecond(data.getPublishTime()));
                break;

            case MultipleItem.ACTIVITY:
                helper.setText(R.id.tv_title, data.getTitle());
                helper.setText(R.id.tv_location, data.getSummary());
                helper.setText(R.id.tv_date, DateUtil.getCustomFormat(data.getPublishTime(), "MM月dd日"));
                ImageView imageView1 = helper.getView(R.id.iv_cover);
                ImageDisplayUtil.showImageView(mContext, data.getCoverPath(), imageView1);

            default:
                break;

        }


    }

    public static class MultipleItem implements MultiItemEntity {


        /**
         * item是文章带图片
         */
        private static final int IMAGE_RIGHT = 1;
        private static final int IMAGE_BOTTOM = 2;
        /**
         * item是文章不带图片
         */
        private static final int IMAGE_NULL = 3;
        /**
         * item是活动
         */
        private static final int ACTIVITY = 4;
        private int itemType;
        private NoticeBean data;

        public MultipleItem(int itemType, NoticeBean data) {
            this.itemType = itemType;
            this.data = data;
        }

        public NoticeBean getData() {
            return data;
        }

        public void setData(NoticeBean data) {
            this.data = data;
        }

        @Override
        public int getItemType() {
            return itemType;
        }
    }
}
