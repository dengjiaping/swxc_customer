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
import com.shiwaixiangcun.customer.interfaces.ItemClick;
import com.shiwaixiangcun.customer.model.MallGoods;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/7.
 */

public class SuggestListAdapter extends DelegateAdapter.Adapter<SuggestListAdapter.SuggestViewHolder> {

    List<MallGoods.DataBean.ElementsBean> mList;

    private Context context;
    private LayoutHelper layoutHelper;
    private ItemClick mItemClick;

    public SuggestListAdapter(Context context, LayoutHelper layoutHelper) {
        this.context = context;
        this.layoutHelper = layoutHelper;

        mList = new ArrayList<>();

    }

    public void setItemClick(ItemClick itemClick) {
        mItemClick = itemClick;
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
        View view = LayoutInflater.from(context).inflate(R.layout.suggest_list, parent, false);
        return new SuggestViewHolder(view, mItemClick);
    }

    @Override
    public void onBindViewHolder(final SuggestViewHolder holder, final int position) {
        final MallGoods.DataBean.ElementsBean goodBean = mList.get(position);
        holder.mTvMallDes.setText(goodBean.getFeature());
        holder.mTvMallTitle.setText(goodBean.getGoodsName());
        holder.mTvMallPrice.setText("¥" + goodBean.getMinPrice());
        Glide.with(context).load(goodBean.getImagePath()).into(holder.mIvCover);


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class SuggestViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cover)
        ImageView mIvCover;
        @BindView(R.id.tv_mall_title)
        TextView mTvMallTitle;
        @BindView(R.id.tv_mall_des)
        TextView mTvMallDes;
        @BindView(R.id.tv_mall_price)
        TextView mTvMallPrice;

        public SuggestViewHolder(View itemView, final ItemClick itemClick) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClick.onItemClick(view, getAdapterPosition());

                }
            });
        }
    }

}
