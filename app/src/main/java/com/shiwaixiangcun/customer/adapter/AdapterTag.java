package com.shiwaixiangcun.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.GoodDetail;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/12.
 */

public class AdapterTag extends TagAdapter<GoodDetail.DataBean.SpecificationsBean.AttributesBean> {

    private List<GoodDetail.DataBean.SpecificationsBean.AttributesBean> mDatas = new ArrayList<>();

    private Context mContext;

    public AdapterTag(Context context, List<GoodDetail.DataBean.SpecificationsBean.AttributesBean> datas) {
        super(datas);
        this.mContext = context;
        mDatas.addAll(datas);
    }


    @Override
    public boolean setSelected(int position, GoodDetail.DataBean.SpecificationsBean.AttributesBean attributesBean) {


        return true;
    }


    @Override
    public View getView(FlowLayout parent, int position, GoodDetail.DataBean.SpecificationsBean.AttributesBean attributesBean) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.tag_attrs, parent, false);
        TextView textView = (TextView) convertView.findViewById(R.id.tv_tag);
        GoodDetail.DataBean.SpecificationsBean.AttributesBean bean = mDatas.get(position);
        textView.setText(bean.getValue());
        textView.setSelected(true);

        return convertView;
    }
}
