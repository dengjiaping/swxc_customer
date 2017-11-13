package com.shiwaixiangcun.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.SurroundMerchantTypeBean;

import java.util.List;


/**
 *
 * @author Administrator
 * @date 2016/7/13
 */
public class MerchTypeAdapter extends BaseAdapter {
    private List<SurroundMerchantTypeBean.DataBean> list;
    private Context context;

    public MerchTypeAdapter(List<SurroundMerchantTypeBean.DataBean> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home_surround_image, null);
            mViewHolder = new ViewHolder();
            mViewHolder.tv_name_merch_type = convertView.findViewById(R.id.tv_name_merch_type);
            mViewHolder.iv_certificates = convertView.findViewById(R.id.iv_certificates);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        SurroundMerchantTypeBean.DataBean dataBean = list.get(position);
        mViewHolder.tv_name_merch_type.setText(dataBean.getName());
        if (dataBean.getName().equals("找装修")) {
            Glide.with(context).load(R.drawable.decorate).into(mViewHolder.iv_certificates);
        } else {
            Glide.with(context).load(dataBean.getImage().getAccessUrl()).into(mViewHolder.iv_certificates);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView tv_name_merch_type;
        ImageView iv_certificates;
    }

}
