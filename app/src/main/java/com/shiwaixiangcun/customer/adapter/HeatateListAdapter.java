package com.shiwaixiangcun.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.HeartateBean;
import com.shiwaixiangcun.customer.utils.TimeToTime;

import java.util.List;


/**
 * Created by Administrator on 2016/7/13.
 */
public class HeatateListAdapter extends BaseAdapter {
    private List<HeartateBean> list;
    private Context context;

    public HeatateListAdapter(List<HeartateBean> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_heartate_list, null);
            mViewHolder = new ViewHolder();
            mViewHolder.tv_heartate_list_time = (TextView)convertView.findViewById(R.id.tv_heartate_list_time);
            mViewHolder.tv_heatate_list_data = (TextView)convertView.findViewById(R.id.tv_heatate_list_data);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.tv_heartate_list_time.setText(TimeToTime.stampToDate(list.get(position).getCreateTime()+""));
        mViewHolder.tv_heatate_list_data.setText(list.get(position).getHeartRate()+"");


        return convertView;
    }

    static class ViewHolder {
        TextView tv_heartate_list_time;
        TextView tv_heatate_list_data;
    }

}
