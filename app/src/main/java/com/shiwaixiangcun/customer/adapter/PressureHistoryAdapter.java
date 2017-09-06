package com.shiwaixiangcun.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.BloodPressureDataBean;
import com.shiwaixiangcun.customer.utils.TimeToTime;

import java.util.List;


/**
 * Created by Administrator on 2016/7/13.
 */
public class PressureHistoryAdapter extends BaseAdapter {
    private  List<BloodPressureDataBean> list;
    private Context context;

    public PressureHistoryAdapter(List<BloodPressureDataBean> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_blood_pressure_data, null);
            mViewHolder = new ViewHolder();
            mViewHolder.tv_pressure_data_time = (TextView)convertView.findViewById(R.id.tv_pressure_data_time);
            mViewHolder.tv_pressure_data_shrinkBlood = (TextView)convertView.findViewById(R.id.tv_pressure_data_shrinkBlood);
            mViewHolder.tv_pressure_data_relaxationBlood = (TextView)convertView.findViewById(R.id.tv_pressure_data_relaxationBlood);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.tv_pressure_data_time.setText(TimeToTime.stampToDate(list.get(position).getCreateTime()+""));
        mViewHolder.tv_pressure_data_shrinkBlood.setText(list.get(position).getShrinkBlood()+"");
        mViewHolder.tv_pressure_data_relaxationBlood.setText(list.get(position).getRelaxationBlood()+"");



        return convertView;
    }

    static class ViewHolder {
        TextView tv_pressure_data_shrinkBlood;
        TextView tv_pressure_data_time;
        TextView tv_pressure_data_relaxationBlood;
    }

}
