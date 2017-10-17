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
 * Created by Administrator on 2017/10/17.
 */

public class AdapterMain extends BaseMultiItemQuickAdapter<AdapterMain.MultipleItem, BaseViewHolder> {


    public AdapterMain(@Nullable List<MultipleItem> data) {
        super(data);
        addItemType(MultipleItem.IMAGE_RIGHT, R.layout.item_right_iv);
        addItemType(MultipleItem.IMAGE_BOTTOM, R.layout.item_bottom_iv);
        addItemType(MultipleItem.IMAGE_NULL, R.layout.item_no_iv);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {

        NoticeBean data = item.getData();


        switch (item.getItemType()) {
            case MultipleItem.IMAGE_RIGHT:
                helper.setText(R.id.tv_title, data.getTitle());
                helper.setText(R.id.tv_summary, data.getSummary());
                helper.setText(R.id.tv_source, data.getSource());
                helper.setText(R.id.tv_publishTime, DateUtil.getMillon(data.getPublishTime()));
                ImageView imageView = helper.getView(R.id.iv_coverPath);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ImageDisplayUtil.showImageView(mContext, data.getCoverPath(), imageView);
                break;
            case MultipleItem.IMAGE_BOTTOM:
                helper.setText(R.id.tv_title, data.getTitle());
                helper.setText(R.id.tv_source, data.getSource());
                helper.setText(R.id.tv_publishTime, DateUtil.getMillon(data.getPublishTime()));
                ImageView image = helper.getView(R.id.iv_coverPath);
                image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ImageDisplayUtil.showImageView(mContext, data.getCoverPath(), image);
                break;
            case MultipleItem.IMAGE_NULL:
                helper.setText(R.id.tv_title, data.getTitle());
                helper.setText(R.id.tv_summary, data.getSummary());
                helper.setText(R.id.tv_source, data.getSource());
                helper.setText(R.id.tv_publishTime, DateUtil.getMillon(data.getPublishTime()));
                break;

        }


    }

    public static class MultipleItem implements MultiItemEntity {

        private static final int IMAGE_RIGHT = 1;
        private static final int IMAGE_BOTTOM = 2;
        private static final int IMAGE_NULL = 3;
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
