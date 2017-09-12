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
import com.shiwaixiangcun.customer.model.MallBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/6.
 */

public class PinzhiAdapter extends DelegateAdapter.Adapter<PinzhiAdapter.MainViewHolder> {

    MallBean.DataBean.QualityGoodsBean mQualityGoods;
    private Context context;
    private LayoutHelper layoutHelper;
    private int count = 0;

    public PinzhiAdapter(Context context, LayoutHelper layoutHelper, int count) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.count = count;
        mQualityGoods = new MallBean.DataBean.QualityGoodsBean();
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_mall_pinzhi, parent, false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        holder.tvPinzhiName.setText(mQualityGoods.getGoodsName());
        holder.tvPinzhiPrice.setText("¥" + mQualityGoods.getMinPrice());
        Glide.with(context).load(mQualityGoods.getImagePath()).into(holder.ivPinzhiIcon);

    }

    @Override
    public int getItemCount() {
        return count;
    }

    public void addData(MallBean.DataBean.QualityGoodsBean qualityGoods) {
        mQualityGoods = qualityGoods;
        notifyDataSetChanged();
    }


    static class MainViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_pinzhi_more)
        TextView tvPinzhiMore;
        @BindView(R.id.tv_pinzhi_name)
        TextView tvPinzhiName;
        @BindView(R.id.tv_pinzhi_price)
        TextView tvPinzhiPrice;
        @BindView(R.id.iv_pinzhi_icon)
        ImageView ivPinzhiIcon;

        MainViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}