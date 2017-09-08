package com.shiwaixiangcun.customer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.interfaces.RvListener;
import com.shiwaixiangcun.customer.model.RightBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/8.
 */

public class AdapterDetail extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RightBean> mList;
    private Context mContext;
    private RvListener mListener;

    public AdapterDetail(List<RightBean> list, Context context, RvListener listener) {
        this.mList = list;
        this.mContext = context;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_title, parent, false);
            return new TitleHolder(view);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_detail, parent, false);
            return new DetailHolder(view, viewType, mListener);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        RightBean rightBean = mList.get(position);
        if (holder instanceof TitleHolder) {

            ((TitleHolder) holder).mTvTitle.setText(rightBean.getTitleName());
            if (position == 0) {
                ((TitleHolder) holder).mViewDivider.setVisibility(View.INVISIBLE);
            } else {
                ((TitleHolder) holder).mViewDivider.setVisibility(View.VISIBLE);
            }

        }
        if (holder instanceof DetailHolder) {
            ((DetailHolder) holder).mTvName.setText(rightBean.getName());

            Glide.with(mContext).load(rightBean.getCategoryImg())
                    .error(R.mipmap.m)
                    .into(((DetailHolder) holder).mIvCover);
        }

    }


    @Override
    public int getItemViewType(int position) {
        return mList.get(position).isTitle() ? 0 : 1;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    static class DetailHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_cover)
        ImageView mIvCover;
        @BindView(R.id.tv_name)
        TextView mTvName;

        DetailHolder(View view, final int viewType, final RvListener listener) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(view.getId(), getAdapterPosition());
                }
            });
        }
    }

    static class TitleHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view_divider)
        View mViewDivider;
        @BindView(R.id.tv_title)
        TextView mTvTitle;

        TitleHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
