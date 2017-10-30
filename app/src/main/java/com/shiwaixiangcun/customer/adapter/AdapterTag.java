package com.shiwaixiangcun.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.widget.flowtag.OnInitSelectedPosition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/12.
 */

public class AdapterTag extends BaseAdapter implements OnInitSelectedPosition {

    private final Context mContext;
    private final List<String> mDataList;

    public AdapterTag(Context context) {
        this.mContext = context;
        this.mDataList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.search_tag, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.tv_tag);

        String t = mDataList.get(position);
        textView.setText(t);
        return view;
    }

    public void onlyAddAll(List<String> datas) {
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearAndAddAll(List<String> datas) {
        mDataList.clear();
        onlyAddAll(datas);
    }

    @Override
    public boolean isSelectedPosition(int position) {

        return false;
    }
}