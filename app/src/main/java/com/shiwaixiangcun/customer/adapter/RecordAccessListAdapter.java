package com.shiwaixiangcun.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.RecordBean;
import com.shiwaixiangcun.customer.utils.TimerToTimerUtil;

import java.util.List;


/**
 * Created by Administrator on 2016/7/13.
 */
public class RecordAccessListAdapter extends BaseAdapter {
    private List<RecordBean.DataBean.ElementsBean> list;
    private Context context;

    public RecordAccessListAdapter(List<RecordBean.DataBean.ElementsBean> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_record_access, null);
            mViewHolder = new ViewHolder();
            mViewHolder.tv_accept_record = (TextView)convertView.findViewById(R.id.tv_accept_record);
            mViewHolder.tv_accept_time = (TextView)convertView.findViewById(R.id.tv_accept_time);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.tv_accept_record.setText(list.get(position).getContent());

        String s = TimerToTimerUtil.stampToDate(list.get(position).getCreateTime()+"");
        mViewHolder.tv_accept_time.setText(s);
        boolean judgetoDay = TimerToTimerUtil.getJudgetoDay(s);
        boolean judgeYesterday = TimerToTimerUtil.getJudgeYesterday(s);
        if (judgetoDay) {
            mViewHolder.tv_accept_time.setText(TimerToTimerUtil.stampToNewDate(list.get(position).getCreateTime() + ""));
        }else if (judgeYesterday){
            mViewHolder.tv_accept_time.setText(TimerToTimerUtil.stamYesterDate(list.get(position).getCreateTime() + ""));
        }



        return convertView;
    }

    static class ViewHolder {
        TextView tv_accept_record;
        TextView tv_accept_time;
    }

}
