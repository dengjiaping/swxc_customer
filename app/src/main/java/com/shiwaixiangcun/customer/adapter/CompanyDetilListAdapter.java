package com.shiwaixiangcun.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.RecoratingDetailBean;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.utils.Utils;

import java.util.List;


/**
 * Created by Administrator on 2016/7/13.
 */
public class CompanyDetilListAdapter extends BaseAdapter {
    private List<RecoratingDetailBean.DataBean.DecoratePlanDtosBean> list;
    private Context context;

    public CompanyDetilListAdapter(List<RecoratingDetailBean.DataBean.DecoratePlanDtosBean> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_company_detail, null);
            mViewHolder = new ViewHolder();
            mViewHolder.iv_thumbImageURL = (ImageView) convertView.findViewById(R.id.iv_thumbImageURL);
            mViewHolder.tv_name_left = (TextView)convertView.findViewById(R.id.tv_name_left);
            mViewHolder.tv_size_image = (TextView)convertView.findViewById(R.id.tv_size_image);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        List<RecoratingDetailBean.DataBean.DecoratePlanDtosBean.DesignChartsBean> designCharts = list.get(position).getDesignCharts();
        if (null != designCharts && designCharts.size() != 0){
            if (Utils.isNotEmpty(list.get(position).getDesignCharts().get(0).getAccessUrl())) {
                mViewHolder.iv_thumbImageURL.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ImageDisplayUtil.showImageView(context, list.get(position).getDesignCharts().get(0).getAccessUrl(), mViewHolder.iv_thumbImageURL);

            }
        }

        mViewHolder.tv_name_left.setText(list.get(position).getPlanName());
        mViewHolder.tv_size_image.setText(list.get(position).getDesignCharts().size()+"张效果图");

        return convertView;
    }

    static class ViewHolder {
        ImageView iv_thumbImageURL;
        TextView tv_name_left;
        TextView tv_size_image;
    }

}
package com.shiwaixiangcun.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.RecoratingDetailBean;
import com.shiwaixiangcun.customer.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Administrator on 2016/7/13.
 */
public class CompanyDetilListAdapter extends BaseAdapter {
    private List<RecoratingDetailBean.DataBean.DecoratePlanDtosBean> list;
    private Context context;

    public CompanyDetilListAdapter(List<RecoratingDetailBean.DataBean.DecoratePlanDtosBean> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_company_detail, null);
            mViewHolder = new ViewHolder();
            mViewHolder.iv_thumbImageURL = (ImageView) convertView.findViewById(R.id.iv_thumbImageURL);
            mViewHolder.tv_name_left = (TextView)convertView.findViewById(R.id.tv_name_left);
            mViewHolder.tv_size_image = (TextView)convertView.findViewById(R.id.tv_size_image);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        List<RecoratingDetailBean.DataBean.DecoratePlanDtosBean.DesignChartsBean> designCharts = list.get(position).getDesignCharts();
        if (null != designCharts && designCharts.size() != 0){
            if (Utils.isNotEmpty(list.get(position).getDesignCharts().get(0).getAccessUrl())) {
                mViewHolder.iv_thumbImageURL.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Picasso.with(context).load(list.get(position).getDesignCharts().get(0).getAccessUrl()).into(mViewHolder.iv_thumbImageURL);
            }
        }

        mViewHolder.tv_name_left.setText(list.get(position).getPlanName());
        mViewHolder.tv_size_image.setText(list.get(position).getDesignCharts().size()+"张效果图");

        return convertView;
    }

    static class ViewHolder {
        ImageView iv_thumbImageURL;
        TextView tv_name_left;
        TextView tv_size_image;
    }

}
