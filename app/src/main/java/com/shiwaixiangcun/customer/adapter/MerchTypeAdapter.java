package com.shiwaixiangcun.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.SurroundmerchantTypeBean;
import com.shiwaixiangcun.customer.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Administrator on 2016/7/13.
 */
public class MerchTypeAdapter extends BaseAdapter {
    private List<SurroundmerchantTypeBean.DataBean>  list;
    private Context context;

    public MerchTypeAdapter(List<SurroundmerchantTypeBean.DataBean> list, Context context) {
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
            mViewHolder.tv_name_merch_type = (TextView)convertView.findViewById(R.id.tv_name_merch_type);
            mViewHolder.iv_certificates = (ImageView)convertView.findViewById(R.id.iv_certificates);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        if (Utils.isNotEmpty(list.get(position).getImage().getAccessUrl())){
            Picasso.with(context).load(list.get(position).getImage().getAccessUrl()).into(mViewHolder.iv_certificates);
        }

        mViewHolder.tv_name_merch_type.setText(list.get(position).getName());
        return convertView;
    }

    static class ViewHolder {
        TextView tv_name_merch_type;
        ImageView iv_certificates;
    }

}
