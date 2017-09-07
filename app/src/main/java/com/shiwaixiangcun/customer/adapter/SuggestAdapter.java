package com.shiwaixiangcun.customer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.shiwaixiangcun.customer.R;

/**
 * Created by Administrator on 2017/9/7.
 */

public class SuggestAdapter extends DelegateAdapter.Adapter<SuggestAdapter.SuggestViewHolder> {
    private Context context;
    private LayoutHelper layoutHelper;
    private int count = 0;

    public SuggestAdapter(Context context, LayoutHelper layoutHelper, int count) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.count = count;

    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public SuggestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SuggestViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_mall_title, parent, false));
    }

    @Override
    public void onBindViewHolder(SuggestViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return count;
    }

    static class SuggestViewHolder extends RecyclerView.ViewHolder {
        public SuggestViewHolder(View itemView) {
            super(itemView);
        }
    }
}
