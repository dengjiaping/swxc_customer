package com.shiwaixiangcun.customer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.shiwaixiangcun.customer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/6.
 */

public class PinzhiAdapter extends DelegateAdapter.Adapter<PinzhiAdapter.MainViewHolder> {
    private Context context;
    private LayoutHelper layoutHelper;
    private int count = 0;

    public PinzhiAdapter(Context context, LayoutHelper layoutHelper, int count) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.count = count;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_pinzhi, parent, false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return count;
    }


    static class MainViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_more)
        TextView tvMore;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.iv_icon)
        ImageView ivIcon;

        MainViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
