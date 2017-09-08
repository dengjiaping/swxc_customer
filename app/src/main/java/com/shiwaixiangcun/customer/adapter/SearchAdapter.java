package com.shiwaixiangcun.customer.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.interfaces.ItemClick;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/7.
 * searchView
 */

public class SearchAdapter extends DelegateAdapter.Adapter<SearchAdapter.SearchViewHolder> {

    private Context context;
    private LayoutHelper layoutHelper;
    private int count = 0;
    private ItemClick mItemClick;

    public SearchAdapter(Context context, LayoutHelper layoutHelper, int count) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.count = count;
    }

    public void setItemClick(ItemClick itemClick) {
        mItemClick = itemClick;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_mall_search, parent, false));
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, final int position) {
        if (mItemClick != null) {
            holder.mEdtSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClick.onItemClick(view, position);
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return count;
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.edt_search)
        TextView mEdtSearch;

        SearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
