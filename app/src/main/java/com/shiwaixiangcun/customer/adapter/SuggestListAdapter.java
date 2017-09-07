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
import com.bumptech.glide.Glide;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.GoodBean;
import com.shiwaixiangcun.customer.model.MallGoods;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/7.
 */

public class SuggestListAdapter extends DelegateAdapter.Adapter<SuggestListAdapter.SuggestViewHolder> {

    List<GoodBean> mList;
    private Context context;
    private LayoutHelper layoutHelper;

    public SuggestListAdapter(Context context, LayoutHelper layoutHelper, List<MallGoods.DataBean.ElementsBean> list) {
        this.context = context;
        this.layoutHelper = layoutHelper;

        mList = new ArrayList<>();
        mList.addAll(list);

    }

    public void addData(List<MallGoods.DataBean.ElementsBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public SuggestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SuggestViewHolder(LayoutInflater.from(context).inflate(R.layout.suggest_list, parent, false));
    }

    @Override
    public void onBindViewHolder(SuggestViewHolder holder, int position) {
        GoodBean goodBean = mList.get(position);
        holder.tvMallDes.setText(goodBean.getFeature());
        holder.tvMallTitle.setText(goodBean.getGoodsName());
        Glide.with(context).load(goodBean.getImagePath()).into(holder.ivCover);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class SuggestViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.tv_mall_title)
        TextView tvMallTitle;
        @BindView(R.id.tv_mall_des)
        TextView tvMallDes;

        public SuggestViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}
