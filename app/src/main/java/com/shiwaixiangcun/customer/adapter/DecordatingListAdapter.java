package com.shiwaixiangcun.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.RecoratingListBean;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Administrator on 2016/7/13.
 */
public class DecordatingListAdapter extends BaseAdapter {
    private List<RecoratingListBean> list;
    private Context context;

    public DecordatingListAdapter(List<RecoratingListBean> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_look_decorating, null);
            mViewHolder = new ViewHolder();
            mViewHolder.iv_recorating = (ImageView) convertView.findViewById(R.id.iv_recorating);
            mViewHolder.tv_decorating_title = (TextView)convertView.findViewById(R.id.tv_decorating_title);
            mViewHolder.tv_recorating_content = (TextView)convertView.findViewById(R.id.tv_recorating_content);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(context).load(list.get(position).getLogo()).into(mViewHolder.iv_recorating);
        mViewHolder.tv_decorating_title.setText(list.get(position).getName());
        mViewHolder.tv_recorating_content.setText(list.get(position).getSummary());
        return convertView;
    }

    static class ViewHolder {
        ImageView iv_recorating;
        TextView tv_decorating_title;
        TextView tv_recorating_content;
    }

}
