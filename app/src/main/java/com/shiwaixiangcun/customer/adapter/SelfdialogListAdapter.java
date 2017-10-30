package com.shiwaixiangcun.customer.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.HouseSelectListBean;
import com.shiwaixiangcun.customer.utils.SharePreference;

import java.util.List;


/**
 * Created by Administrator on 2016/7/13.
 */
public class SelfdialogListAdapter extends BaseAdapter {
    private List<HouseSelectListBean> list;


    public SelfdialogListAdapter(List<HouseSelectListBean> list) {
        this.list = list;

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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_self_dialog, null);
            mViewHolder = new ViewHolder();
            mViewHolder.rl_select_house_a = (RelativeLayout) convertView.findViewById(R.id.rl_select_house_a);
            mViewHolder.iv_select_y = (ImageView) convertView.findViewById(R.id.iv_select_y);
            mViewHolder.iv_select_n = (ImageView) convertView.findViewById(R.id.iv_select_n);
            mViewHolder.tv_house_title = (TextView)convertView.findViewById(R.id.tv_house_title);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final ViewHolder finalMViewHolder = mViewHolder;
        Log.i("ttttttttt", "eeeeeeeeee"+list.get(position).getId()+"------"+position);
        String stringSpParams = SharePreference.getStringSpParams(parent.getContext(), Common.ISSELECTHOSE, Common.SISELECTHOSE);

//        mViewHolder.rl_select_house_a.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finalMViewHolder.iv_select_y.setVisibility(View.VISIBLE);
//                finalMViewHolder.iv_select_n.setVisibility(View.GONE);
//            }
//        });
        mViewHolder.tv_house_title.setText(list.get(position).getNumberDesc());
        if (stringSpParams.equals(position+"")) {
            finalMViewHolder.iv_select_y.setVisibility(View.VISIBLE);
            finalMViewHolder.iv_select_n.setVisibility(View.GONE);
        }else {
            finalMViewHolder.iv_select_y.setVisibility(View.GONE);
            finalMViewHolder.iv_select_n.setVisibility(View.VISIBLE);
        }


        return convertView;
    }

    static class ViewHolder {
        RelativeLayout rl_select_house_a;
        ImageView iv_select_y;
        ImageView iv_select_n;
        TextView tv_house_title;
    }

}
package com.shiwaixiangcun.customer.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.model.HouseSelectListBean;
import com.shiwaixiangcun.customer.utils.ShareUtil;

import java.util.List;


/**
 * Created by Administrator on 2016/7/13.
 */
public class SelfdialogListAdapter extends BaseAdapter {
    private List<HouseSelectListBean> list;


    public SelfdialogListAdapter(List<HouseSelectListBean> list) {
        this.list = list;

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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_self_dialog, null);
            mViewHolder = new ViewHolder();
            mViewHolder.rl_select_house_a = (RelativeLayout) convertView.findViewById(R.id.rl_select_house_a);
            mViewHolder.iv_select_y = (ImageView) convertView.findViewById(R.id.iv_select_y);
            mViewHolder.iv_select_n = (ImageView) convertView.findViewById(R.id.iv_select_n);
            mViewHolder.tv_house_title = (TextView)convertView.findViewById(R.id.tv_house_title);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final ViewHolder finalMViewHolder = mViewHolder;
        Log.i("ttttttttt", "eeeeeeeeee"+list.get(position).getId()+"------"+position);
        String stringSpParams = ShareUtil.getStringSpParams(parent.getContext(), Common.ISSELECTHOSE, Common.SISELECTHOSE);

//        mViewHolder.rl_select_house_a.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finalMViewHolder.iv_select_y.setVisibility(View.VISIBLE);
//                finalMViewHolder.iv_select_n.setVisibility(View.GONE);
//            }
//        });
        mViewHolder.tv_house_title.setText(list.get(position).getNumberDesc());
        if (stringSpParams.equals(position+"")) {
            finalMViewHolder.iv_select_y.setVisibility(View.VISIBLE);
            finalMViewHolder.iv_select_n.setVisibility(View.GONE);
        }else {
            finalMViewHolder.iv_select_y.setVisibility(View.GONE);
            finalMViewHolder.iv_select_n.setVisibility(View.VISIBLE);
        }


        return convertView;
    }

    static class ViewHolder {
        RelativeLayout rl_select_house_a;
        ImageView iv_select_y;
        ImageView iv_select_n;
        TextView tv_house_title;
    }

}
