package com.shiwaixiangcun.customer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.mall.HotListHAdapter;
import com.shiwaixiangcun.customer.model.MallBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/6.
 */

public class JingxuanListAdapter extends DelegateAdapter.Adapter<JingxuanListAdapter.DailyViewHolder> {
    private Context context;
    private LayoutHelper layoutHelper;
    private RecyclerView.LayoutParams layoutParams;
    private int count = 0;
    private List<MallBean.DataBean.DailySelectionListBean> mHotLists = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;
    private HotListHAdapter mHotListHAdapter;

    public JingxuanListAdapter(Context context, LayoutHelper layoutHelper, int count) {
        this(context, layoutHelper, count,
                new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public JingxuanListAdapter(Context context, LayoutHelper layoutHelper, int count,
                               @NonNull RecyclerView.LayoutParams layoutParams) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.count = count;
        this.layoutParams = layoutParams;
        mLinearLayoutManager = new LinearLayoutManager(context);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mHotListHAdapter = new HotListHAdapter(context, mHotLists);
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public DailyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DailyViewHolder(
                LayoutInflater.from(context).inflate(R.layout.layout_mall_jingxuan, parent, false));
    }

    @Override
    public void onBindViewHolder(DailyViewHolder holder, int position) {
        holder.mRecyclerView.setLayoutManager(mLinearLayoutManager);
        holder.mRecyclerView.setAdapter(mHotListHAdapter);
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public void setHotListData(List<MallBean.DataBean.DailySelectionListBean> list) {
        mHotLists.clear();
        mHotListHAdapter.addData(list);
        mHotListHAdapter.notifyDataSetChanged();
    }

    static class DailyViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView mRecyclerView;

        public DailyViewHolder(View itemView) {
            super(itemView);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.rv_mall_jingxuan);
        }
    }
}

