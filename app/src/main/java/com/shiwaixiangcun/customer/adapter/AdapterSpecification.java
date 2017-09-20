package com.shiwaixiangcun.customer.adapter;

import android.content.Context;
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
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/12.
 * <p>
 * 规格的Adapter  (如重量 颜色 尺码)
 */

public class AdapterSpecification extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //用于存放属性Value的HashMap
    final HashMap<String, String> valueMap = new HashMap<String, String>();
    //用于存放属性ID 的HashMap
    final HashMap<String, Integer> idMap = new HashMap<String, Integer>();
    private final String BUG_TAG = this.getClass().getSimpleName();
    private Context mContext;
    private List<GoodDetail.DataBean.SpecificationsBean> mDatas;
    private List<FlowTagLayout> mTagFlowLayouts = new ArrayList<>();
    private TagAdapter mTagAdapter;
    private StringBuilder nameBuilder = new StringBuilder();
    private StringBuilder idBuilder = new StringBuilder();
    private int mGoodID = 0;
    private TagClick mTagClick;

    public AdapterSpecification(Context context, int goodId) {
        this.mContext = context;
        mDatas = new ArrayList<>();
        this.mGoodID = goodId;
    }

    public HashMap<String, String> getValueMap() {
        return valueMap;
    }

    public HashMap<String, Integer> getIdMap() {
        return idMap;
    }

    public void setTagClick(TagClick tagClick) {
        mTagClick = tagClick;
    }

    public StringBuilder getNameBuilder() {
        return nameBuilder;
    }

    public StringBuilder getIdBuilder() {
        return idBuilder;
    }

    public void addData(List<GoodDetail.DataBean.SpecificationsBean> data) {
        mDatas.addAll(data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ui_spec_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            final GoodDetail.DataBean.SpecificationsBean specificationsBean = mDatas.get(position);
            if (specificationsBean.getName() == null) {
                return;
            }
            ((ViewHolder) holder).mSpecName.setText(specificationsBean.getName());
            //初始化属性数据
            if (specificationsBean.getAttributes() == null) {
                return;
            }
            final List<GoodDetail.DataBean.SpecificationsBean.AttributesBean> mAttributesList = new ArrayList<>();
            mAttributesList.clear();
            mAttributesList.addAll(specificationsBean.getAttributes());
            //初始化Adapter
            mTagAdapter = new TagAdapter(mContext);
            mTagAdapter.clearAndAddAll(mAttributesList);
            ((ViewHolder) holder).mSpecList.setAdapter(mTagAdapter);
            mTagAdapter.notifyDataSetChanged();
            //设置Adapter点击事件
            ((ViewHolder) holder).mSpecList.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
            ((ViewHolder) holder).mSpecList.setOnTagClickListener(new OnTagClickListener() {
                @Override
                public void onItemClick(FlowTagLayout parent, View view, int position) {
                    mTagAdapter.isSelectedPosition(position);
                    mTagAdapter.notifyDataSetChanged();
                }
            });
            mTagFlowLayouts.add(((ViewHolder) holder).mSpecList);
            ((ViewHolder) holder).mSpecList.setOnTagSelectListener(new OnTagSelectListener() {
                @Override
                public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                    if (selectedList != null && selectedList.size() > 0) {
                        for (int i : selectedList) {
                            idMap.put(specificationsBean.getName(), mAttributesList.get(i).getId());
                        }
                    }
                    updateUI();
                }
            });
        }

    }

    private void updateUI() {
        nameBuilder.setLength(0);
//        idBuilder.setLength(0);
        Set setValue = valueMap.keySet();
        for (Object aSetValue : setValue) {
            String key = (String) aSetValue;
            nameBuilder.append("/" + valueMap.get(key));
        }
        Set setId = idMap.keySet();

        for (Object aSetId : setId) {
            String key = (String) aSetId;
            idBuilder.insert(0, idMap.get(key)).append(",");
        }
        if (mTagClick != null) {
            mTagClick.onTagClick();
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
        FlowTagLayout mSpecList;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
