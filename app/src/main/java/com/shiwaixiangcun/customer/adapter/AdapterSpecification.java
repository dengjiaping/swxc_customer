package com.shiwaixiangcun.customer.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.interfaces.TagClick;
import com.shiwaixiangcun.customer.model.GoodDetail;
import com.shiwaixiangcun.customer.widget.flowtag.FlowTagLayout;
import com.shiwaixiangcun.customer.widget.flowtag.OnTagClickListener;
import com.shiwaixiangcun.customer.widget.flowtag.OnTagSelectListener;
import com.shiwaixiangcun.customer.widget.flowtag.TagAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/12.
 * <p>
 * 规格的Adapter  (如重量 颜色 尺码)
 */

public class AdapterSpecification extends RecyclerView.Adapter<AdapterSpecification.ViewHolder> {


    private static String BUG_TAG = "AdapterSpecification";
    private Activity mContext;
    private List<GoodDetail.DataBean.SpecificationsBean> mDatas;
    private TagClick mTagClick;
    private HashMap<String, GoodDetail.DataBean.SpecificationsBean.AttributesBean> mSelectedAttr = new HashMap<>();

    public AdapterSpecification(Activity context, List<GoodDetail.DataBean.SpecificationsBean> specificationList) {
        this.mContext = context;
        this.mDatas = specificationList;
    }

    /**
     * 获取选中的属性 信息
     *
     * @return
     */
    public HashMap<String, GoodDetail.DataBean.SpecificationsBean.AttributesBean> getSelectedAttr() {
        return mSelectedAttr;
    }

    public void setTagClick(TagClick tagClick) {
        mTagClick = tagClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ui_spec_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder == null) {
            return;
        }
        final GoodDetail.DataBean.SpecificationsBean specificationsBean = mDatas.get(position);
        if (specificationsBean.getName() == null) {
            return;
        }
        holder.mSpecName.setText(specificationsBean.getName());
        //初始化属性数据
        if (specificationsBean.getAttributes() == null) {
            return;
        }
        final List<GoodDetail.DataBean.SpecificationsBean.AttributesBean> mAttributesList = new ArrayList<>();
        mAttributesList.clear();
        mAttributesList.addAll(specificationsBean.getAttributes());
        //初始化Adapter
        TagAdapter tagAdapter = new TagAdapter(mContext);
        tagAdapter.clearAndAddAll(mAttributesList);
        holder.mSpecList.setAdapter(tagAdapter);
        tagAdapter.notifyDataSetChanged();
        //设置Adapter点击事件
        holder.mSpecList.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        holder.mSpecList.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
                GoodDetail.DataBean.SpecificationsBean.AttributesBean attributesBean = mAttributesList.get(position);
            }
        });
        holder.mSpecList.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                if (selectedList != null && selectedList.size() > 0) {
                    for (int i : selectedList) {
                        GoodDetail.DataBean.SpecificationsBean.AttributesBean attributesBean = mAttributesList.get(i);
                        mSelectedAttr.put(specificationsBean.getName(), attributesBean);
                    }
                }
                if (mTagClick != null) {
                    mTagClick.onTagClick();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.spec_name)
        TextView mSpecName;
        @BindView(R.id.spec_list)
        FlowTagLayout mSpecList;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
