package com.shiwaixiangcun.customer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.GoodDetail;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/12.
 */

public class AdapterAttr extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mAttrNames;
    private Context mContext;

    private List<GoodDetail.DataBean.SpecificationsBean> mDatas;

    public AdapterAttr(Context context) {
        this.mContext = context;
        mDatas = new ArrayList<>();
    }

    public void addData(List<GoodDetail.DataBean.SpecificationsBean> data) {
        mDatas.addAll(data);
        mAttrNames = new ArrayList<>();
        for (int i = 0, size = data.size(); i < size; i++) {
            GoodDetail.DataBean.SpecificationsBean specificationsBean = data.get(i);
            for (int j = 0, count = specificationsBean.getAttributes().size(); j < count; j++) {
                GoodDetail.DataBean.SpecificationsBean.AttributesBean attributesBean = specificationsBean.getAttributes().get(j);
                mAttrNames.add(attributesBean.getValue());
            }
        }
        for (int t = 0; t < 4; t++) {
            mAttrNames.add(t + "kg");
        }
        Log.e("你妈的", mAttrNames.size() + "");
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ui_spec_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).mSpecName.setText(mDatas.get(position).getName());
            ((ViewHolder) holder).mSpecList.setAdapter(new TagAdapter<String>(mAttrNames) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    View inflate = LayoutInflater.from(mContext).inflate(R.layout.tag_attrs, parent, false);
                    TextView tvTag = (TextView) inflate.findViewById(R.id.tv_tag);
                    tvTag.setText(s);
                    return inflate;
                }


            });
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.spec_name)
        TextView mSpecName;
        @BindView(R.id.spec_list)
        TagFlowLayout mSpecList;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
