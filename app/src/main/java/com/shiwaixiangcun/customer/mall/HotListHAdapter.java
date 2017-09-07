package com.shiwaixiangcun.customer.mall;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.MallBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/6.
 */

public class HotListHAdapter extends RecyclerView.Adapter<HotListHAdapter.ViewHolder> {

    private Context mContext;
    private List<MallBean.DataBean.DailySelectionListBean> mList;

    public HotListHAdapter(Context context, List<MallBean.DataBean.DailySelectionListBean> data) {
        this.mContext = context;
        mList = new ArrayList<>();
        mList.addAll(data);

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_hotlists_h_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MallBean.DataBean.DailySelectionListBean bean = mList.get(position);
        holder.tvDailyPrice.setText("¥" + (int) bean.getMinPrice());
        holder.tvDailyTitle.setText(bean.getGoodsName());
        Glide.with(mContext).load(bean.getImagePath()).into(holder.ivDailyIcon);


    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    public void addData(List<MallBean.DataBean.DailySelectionListBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_daily_title)
        TextView tvDailyTitle;
        @BindView(R.id.iv_daily_icon)
        ImageView ivDailyIcon;
        @BindView(R.id.tv_daily_price)
        TextView tvDailyPrice;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
