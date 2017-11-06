package com.shiwaixiangcun.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.MerchDetailBean;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.utils.Utils;

import java.util.List;


/**
 * Created by Administrator on 2016/7/13.
 */
public class ImageMerchantDetailAdapter extends BaseAdapter {
    private List<MerchDetailBean.DataBean.AtlasBean.AtlasListBean> list;
    private Context context;

    public ImageMerchantDetailAdapter(List<MerchDetailBean.DataBean.AtlasBean.AtlasListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_image_detail, null);
            mViewHolder = new ViewHolder();
            mViewHolder.iv_detail = (ImageView)convertView.findViewById(R.id.iv_detail);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        if (Utils.isNotEmpty(list.get(position).getAccessUrl())){
            ImageDisplayUtil.showImageView(context, list.get(position).getAccessUrl(), mViewHolder.iv_detail);
        }


        return convertView;
    }

    static class ViewHolder {
        ImageView iv_detail;
    }

}
