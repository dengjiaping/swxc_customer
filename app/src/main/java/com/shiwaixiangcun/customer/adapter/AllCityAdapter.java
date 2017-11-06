package com.shiwaixiangcun.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.DefaultCityBean;

import java.util.List;


/**
 * Created by Administrator on 2016/7/13.
 */
public class AllCityAdapter extends BaseAdapter {
    private List<DefaultCityBean.DataBean> list;
    private Context context;

    public AllCityAdapter(List<DefaultCityBean.DataBean> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_all_city, null);
            mViewHolder = new ViewHolder();

            mViewHolder.tv_city_name = (TextView) convertView.findViewById(R.id.tv_city_name);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.tv_city_name.setText(list.get(position).getCityName());
        return convertView;
    }

    static class ViewHolder {
        TextView tv_city_name;
    }

}
