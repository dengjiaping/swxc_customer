package com.shiwaixiangcun.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.PressureFatBean;
import com.shiwaixiangcun.customer.utils.TimeToTime;

import java.util.List;


/**
 * Created by Administrator on 2016/7/13.
 */
public class BloodFatHistoryAdapter extends BaseAdapter {
    private  List<PressureFatBean.DataBean.ElementsBean>  list;
    private Context context;

    public BloodFatHistoryAdapter(List<PressureFatBean.DataBean.ElementsBean>  list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_fat_history, null);
            mViewHolder = new ViewHolder();
            mViewHolder.tv_fat_data_time = (TextView)convertView.findViewById(R.id.tv_fat_data_time);
            mViewHolder.tv_item_totalCholesterol = (TextView)convertView.findViewById(R.id.tv_item_totalCholesterol);
            mViewHolder.tv_item_triglyceride = (TextView)convertView.findViewById(R.id.tv_item_triglyceride);
            mViewHolder.tv_item_topLipo = (TextView)convertView.findViewById(R.id.tv_item_topLipo);
            mViewHolder.tv_item_lowLipo = (TextView)convertView.findViewById(R.id.tv_item_lowLipo);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.tv_fat_data_time.setText(TimeToTime.stampToDate(list.get(position).getCreateTime()+""));
        mViewHolder.tv_item_totalCholesterol.setText(list.get(position).getTotalCholesterol()+"");
        mViewHolder.tv_item_triglyceride.setText(list.get(position).getTriglyceride()+"");
        mViewHolder.tv_item_topLipo.setText(list.get(position).getTopLipo()+"");
        mViewHolder.tv_item_lowLipo.setText(list.get(position).getLowLipo()+"");
        return convertView;
    }

    static class ViewHolder {
        TextView tv_fat_data_time;
        TextView tv_item_totalCholesterol;
        TextView tv_item_triglyceride;
        TextView tv_item_topLipo;
        TextView tv_item_lowLipo;
    }

}
