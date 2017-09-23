package com.shiwaixiangcun.customer.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.interfaces.RvListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/8.
 */

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.ViewHolderCate> {

    private List<String> mCateList = new ArrayList<>();
    private Context mContext;
    private RvListener mListener;
    private int checkedPosition;

    public AdapterCategory(Context context, List<String> mCategoryNameList, RvListener rvListener) {
        mCateList.addAll(mCategoryNameList);
        this.mListener = rvListener;
        mContext = context;
    }

    public void addData(List<String> list) {
        mCateList.clear();
        mCateList.addAll(list);
        notifyDataSetChanged();

    }

    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolderCate onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_category, parent, false);
        return new ViewHolderCate(view, mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolderCate holder, int position) {
        holder.bindHolder(mCateList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mCateList.size();
    }

    static class ViewHolder {
        @BindView(R.id.flag)
        View mFlag;
        @BindView(R.id.tv_sort)
        TextView mTvSort;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    final class ViewHolderCate extends RecyclerView.ViewHolder {
        private final View mView;
        @BindView(R.id.flag)
        View mFlag;
        @BindView(R.id.tv_sort)
        TextView mTvSort;
        private RvListener mListener;

        public ViewHolderCate(View itemView, RvListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mView = itemView;
            this.mListener = listener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mListener.onItemClick(view.getId(), getAdapterPosition());

                }
            });
        }

        public void bindHolder(String data, int position) {
            mTvSort.setText(data);
            if (position == checkedPosition) {
                mView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mFlag.setVisibility(View.VISIBLE);
            } else {
                mView.setBackgroundColor(Color.parseColor("#F8F8F8"));
                mFlag.setVisibility(View.INVISIBLE);
            }

        }
    }
}
