package com.shiwaixiangcun.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.BloodSugarBean;
import com.shiwaixiangcun.customer.utils.TimeToTime;

import java.util.List;


/**
 * Created by Administrator on 2016/7/13.
 */
public class SugarHistoryAdapter extends BaseAdapter {
    private  List<BloodSugarBean.DataBean.ElementsBean> list;
    private Context context;

    public SugarHistoryAdapter( List<BloodSugarBean.DataBean.ElementsBean> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_sugar_history, null);
            mViewHolder = new ViewHolder();
            mViewHolder.tv_sugar_data_time = (TextView)convertView.findViewById(R.id.tv_sugar_data_time);
            mViewHolder.tv_isnot_kf = (TextView)convertView.findViewById(R.id.tv_isnot_kf);
            mViewHolder.tv_sugar_data_shrinkBlood = (TextView)convertView.findViewById(R.id.tv_sugar_data_shrinkBlood);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.tv_sugar_data_time.setText(TimeToTime.stampToDate(list.get(position).getCreateTime()+""));
        if (list.get(position).getSugarStatus().equals("KF")){
            mViewHolder.tv_isnot_kf.setText("空腹");
        }else if (list.get(position).getSugarStatus().equals("FH")){
            mViewHolder.tv_isnot_kf.setText("饭后2小时");
        }
        mViewHolder.tv_sugar_data_shrinkBlood.setText(list.get(position).getBloodSugar()+"");

        return convertView;
    }

    static class ViewHolder {
        TextView tv_sugar_data_time;
        TextView tv_isnot_kf;
        TextView tv_sugar_data_shrinkBlood;
    }

}
