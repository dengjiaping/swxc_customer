package com.shiwaixiangcun.customer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.NoticeBean;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.utils.TimerToTimerUtil;

import java.util.List;


public class ComListAdapter extends BaseAdapter {
    public static final int TYPE_TITLE = 0;
    public static final int TYPE_COMPANY = 1;
    public static final int TYPE_DOWN = 2;
    private static final int TYPE_ACTIVITY = 3;
    private Context context;
    private List<NoticeBean> list;
    private int index = -1;

    public ComListAdapter(Context context, List<NoticeBean> list_type_adapter) {
        super();
        this.context = context;
        this.list = list_type_adapter;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {

        if (list.get(position).getShowType().equals("ARTICLE_IMAGE")) {
            return TYPE_TITLE;
        } else if (list.get(position).getShowType().equals("ACTIVE")) {
            return TYPE_COMPANY;
        } else if (list.get(position).getShowType().equals("ARTICLE_NO_IMAGE")) {
            return TYPE_DOWN;
        } else if (list.get(position).getShowType().equals("ACTIVITY")) {
            return TYPE_ACTIVITY;
        }
        return 8;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TitleViewHolder titleHolder;
        ComViewHolder comHolder;
        DownHolder downHolder;

        switch (getItemViewType(position)) {
            case TYPE_TITLE:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_right_iv, null);
                    titleHolder = new TitleViewHolder();
                    titleHolder.tv_title = convertView.findViewById(R.id.tv_title);
                    titleHolder.tv_summary = convertView.findViewById(R.id.tv_summary);
                    titleHolder.tv_publishTime = convertView.findViewById(R.id.tv_publishTime);
                    titleHolder.tv_source = convertView.findViewById(R.id.tv_source);
                    titleHolder.iv_coverPath = convertView.findViewById(R.id.iv_coverPath);
                    convertView.setTag(titleHolder);
                } else {
                    titleHolder = (TitleViewHolder) convertView.getTag();
                }
                titleHolder.tv_title.setText(list.get(position).getTitle());
                titleHolder.tv_summary.setText(list.get(position).getSummary());
                titleHolder.tv_source.setText(list.get(position).getSource());
                titleHolder.iv_coverPath.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ImageDisplayUtil.showImageView(context, list.get(position).getCoverPath(), titleHolder.iv_coverPath);
                String s = TimerToTimerUtil.stampToDate(list.get(position).getPublishTime() + "");
                titleHolder.tv_publishTime.setText(s);

                boolean judgetoDay = TimerToTimerUtil.getJudgetoDay(s);
                boolean judgeYesterday = TimerToTimerUtil.getJudgeYesterday(s);
                if (judgetoDay) {
                    titleHolder.tv_publishTime.setText(TimerToTimerUtil.stampToNewDate(list.get(position).getPublishTime() + ""));
                } else if (judgeYesterday) {
                    titleHolder.tv_publishTime.setText(TimerToTimerUtil.stamYesterDate(list.get(position).getPublishTime() + ""));
                }

                break;
            case TYPE_COMPANY:

                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_bottom_iv, null);
                    comHolder = new ComViewHolder();
                    comHolder.tv_title = convertView.findViewById(R.id.tv_title);
                    comHolder.tv_publishTime = convertView.findViewById(R.id.tv_publishTime);
                    comHolder.tv_source = convertView.findViewById(R.id.tv_source);
                    comHolder.iv_coverPath = convertView.findViewById(R.id.iv_coverPath);
                    convertView.setTag(comHolder);
                } else {
                    comHolder = (ComViewHolder) convertView.getTag();
                }
                comHolder.tv_title.setText(list.get(position).getTitle());
                comHolder.tv_source.setText(list.get(position).getSource());
                comHolder.iv_coverPath.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ImageDisplayUtil.showImageView(context, list.get(position).getCoverPath(), comHolder.iv_coverPath);
                String s_a = TimerToTimerUtil.stampToDate(list.get(position).getPublishTime() + "");
                comHolder.tv_publishTime.setText(s_a);


                boolean judgetoDay_a = TimerToTimerUtil.getJudgetoDay(s_a);
                boolean judgeYesterday_a = TimerToTimerUtil.getJudgeYesterday(s_a);
                if (judgetoDay_a) {
                    comHolder.tv_publishTime.setText(TimerToTimerUtil.stampToNewDate(list.get(position).getPublishTime() + ""));
                } else if (judgeYesterday_a) {
                    comHolder.tv_publishTime.setText(TimerToTimerUtil.stamYesterDate(list.get(position).getPublishTime() + ""));
                }

                break;
            case TYPE_DOWN:

                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_no_iv, null);
                    downHolder = new DownHolder();
                    downHolder.tv_title = convertView.findViewById(R.id.tv_title);
                    downHolder.tv_summary = convertView.findViewById(R.id.tv_summary);
                    downHolder.tv_publishTime = convertView.findViewById(R.id.tv_publishTime);
                    downHolder.tv_come_from = convertView.findViewById(R.id.tv_source);
                    convertView.setTag(downHolder);
                } else {
                    downHolder = (DownHolder) convertView.getTag();
                }
                downHolder.tv_title.setText(list.get(position).getTitle());
                downHolder.tv_summary.setText(list.get(position).getSummary());
                downHolder.tv_come_from.setText(list.get(position).getSource());


                String s_b = TimerToTimerUtil.stampToDate(list.get(position).getPublishTime() + "");
                downHolder.tv_publishTime.setText(s_b);


                boolean judgetoDay_b = TimerToTimerUtil.getJudgetoDay(s_b);
                boolean judgeYesterday_b = TimerToTimerUtil.getJudgeYesterday(s_b);
                if (judgetoDay_b) {
                    downHolder.tv_publishTime.setText(TimerToTimerUtil.stampToNewDate(list.get(position).getPublishTime() + ""));
                } else if (judgeYesterday_b) {
                    downHolder.tv_publishTime.setText(TimerToTimerUtil.stamYesterDate(list.get(position).getPublishTime() + ""));
                }

                break;


        }


        return convertView;
    }

    static class TitleViewHolder {
        TextView tv_title;
        TextView tv_summary;
        TextView tv_publishTime;
        TextView tv_source;
        ImageView iv_coverPath;
    }

    static class ComViewHolder {
        TextView tv_title;
        TextView tv_publishTime;
        TextView tv_source;
        ImageView iv_coverPath;
    }

    static class DownHolder {
        TextView tv_title;
        TextView tv_summary;
        TextView tv_publishTime;
        TextView tv_come_from;
    }


}