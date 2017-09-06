package com.shiwaixiangcun.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.WeightBean;
import com.shiwaixiangcun.customer.utils.TimeToTime;

import java.util.List;


/**
 * Created by Administrator on 2016/7/13.
 */
public class WeightHistoryAdapter extends BaseAdapter {
    private List<WeightBean> list;
    private Context context;

    public WeightHistoryAdapter(List<WeightBean> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_weight_history, null);
            mViewHolder = new ViewHolder();
            mViewHolder.tv_weight_data_time = (TextView)convertView.findViewById(R.id.tv_weight_data_time);
            mViewHolder.tv_weight_data = (TextView)convertView.findViewById(R.id.tv_weight_data);
            mViewHolder.tv_weight_data_bmi = (TextView)convertView.findViewById(R.id.tv_weight_data_bmi);


            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.tv_weight_data_time.setText(TimeToTime.stampToDate(list.get(position).getCreateTime()+""));
        mViewHolder.tv_weight_data.setText(list.get(position).getWeight()+" kg");
        mViewHolder.tv_weight_data_bmi.setText(list.get(position).getBmi()+"");

        return convertView;
    }

    static class ViewHolder {
        TextView tv_weight_data_time;
        TextView tv_weight_data;
        TextView tv_weight_data_bmi;
    }

}
