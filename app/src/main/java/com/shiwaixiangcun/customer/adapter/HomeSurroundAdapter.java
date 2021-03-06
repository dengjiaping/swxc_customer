package com.shiwaixiangcun.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.AllMerchBean;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.utils.Utils;

import java.util.List;


/**
 * Created by Administrator on 2016/7/13.
 */
public class HomeSurroundAdapter extends BaseAdapter {
    private List<AllMerchBean.DataBean.ElementsBean> list;
    private Context context;

    public HomeSurroundAdapter(List<AllMerchBean.DataBean.ElementsBean>  list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_home_surround, null);
            mViewHolder = new ViewHolder();
            mViewHolder.iv_merch = (ImageView)convertView.findViewById(R.id.iv_merch);
            mViewHolder.tv_merch_name = (TextView)convertView.findViewById(R.id.tv_merch_name);
            mViewHolder.tv_merch_content = (TextView)convertView.findViewById(R.id.tv_merch_content);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.tv_merch_name.setText(list.get(position).getName());
        mViewHolder.tv_merch_content.setText(list.get(position).getFeature());
        if (Utils.isNotEmpty(list.get(position).getCover())){
            ImageDisplayUtil.showImageView(context, list.get(position).getCover(), mViewHolder.iv_merch);

        }

        return convertView;
    }

    static class ViewHolder {
        ImageView iv_merch;
        TextView tv_merch_name;
        TextView tv_merch_content;

    }

}
