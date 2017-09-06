package com.shiwaixiangcun.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.MerchDetailBean;
import com.shiwaixiangcun.customer.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Administrator on 2016/7/13.
 */
public class SurroundDetailAdapter extends BaseAdapter {
    private List<MerchDetailBean.DataBean.AtlasBean> list;
    private Context context;

    public SurroundDetailAdapter(List<MerchDetailBean.DataBean.AtlasBean> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_surround_detail, null);
            mViewHolder = new ViewHolder();
            mViewHolder.iv_merch_atlas = (ImageView) convertView.findViewById(R.id.iv_merch_atlas);
            mViewHolder.tv_merch_atlas_name =  (TextView) convertView.findViewById(R.id.tv_merch_atlas_name);
            mViewHolder.tv_sun_icon = (TextView) convertView.findViewById(R.id.tv_sun_icon);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.iv_merch_atlas.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (Utils.isNotEmpty(list.get(position).getAtlasList().get(0).getAccessUrl())){

            Picasso.with(context).load(list.get(position).getAtlasList().get(0).getAccessUrl()).into(mViewHolder.iv_merch_atlas);
        }
        mViewHolder.tv_merch_atlas_name.setText(list.get(position).getTitle());
        if (null != list.get(position).getAtlasList()){
            mViewHolder.tv_sun_icon.setText(list.get(position).getAtlasList().size()+"");
        }else {
            mViewHolder.tv_sun_icon.setText("0");
        }


        return convertView;
    }

    static class ViewHolder {
        ImageView iv_merch_atlas;
        TextView tv_merch_atlas_name;
        TextView tv_sun_icon;
    }

}
