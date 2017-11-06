package com.shiwaixiangcun.customer.widget.flowtag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.GoodDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/13.
 * 具体属性的Adapter  如 尺码中的 s m l
 */

public class TagAdapter extends BaseAdapter implements OnInitSelectedPosition {

    private final Context mContext;
    private final List<GoodDetail.DataBean.SpecificationsBean.AttributesBean> mDataList;

    public TagAdapter(Context context) {
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

        View view = LayoutInflater.from(mContext).inflate(R.layout.tag_attrs, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.tv_tag);

        GoodDetail.DataBean.SpecificationsBean.AttributesBean t = mDataList.get(position);
        textView.setText(t.getValue());
        return view;
    }

    public void onlyAddAll(List<GoodDetail.DataBean.SpecificationsBean.AttributesBean> datas) {
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearAndAddAll(List<GoodDetail.DataBean.SpecificationsBean.AttributesBean> datas) {
        mDataList.clear();
        onlyAddAll(datas);
    }

    @Override
    public boolean isSelectedPosition(int position) {

        return false;
    }
}