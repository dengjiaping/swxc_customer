package com.shiwaixiangcun.customer.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jaeger.recyclerviewdivider.RecyclerViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterRecipe;
import com.shiwaixiangcun.customer.model.RecipeBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/10/11.
 */

public class RecipeFragment extends LazyFragment {

    @BindView(R.id.rv_recipe)
    RecyclerView mRvRecipe;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;


    private String mTitle;
    private List<RecipeBean> mRecipeList;
    private AdapterRecipe mAdapterRecipe;

    public static Fragment getInstance(String title) {

        RecipeFragment fragment = new RecipeFragment();
        fragment.mTitle = title;
        return fragment;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_recipe;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        mRecipeList = new ArrayList<>();
        mAdapterRecipe = new AdapterRecipe(mRecipeList);
        mRvRecipe.setLayoutManager(new LinearLayoutManager(mContext));
        mRvRecipe.setAdapter(mAdapterRecipe);
        RecyclerViewDivider divider = new RecyclerViewDivider.Builder(mContext)
                .setOrientation(RecyclerViewDivider.VERTICAL)
                .setStyle(RecyclerViewDivider.Style.END)
                .setMarginLeft(16)
                .setMarginRight(0)
                .setDrawableRes(R.drawable.divider)
                .build();
        mRvRecipe.addItemDecoration(divider);


    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void DestroyViewAndThing() {
//        EventBus.getDefault().unregister(this);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();
//        EventBus.getDefault().register(this);
    }

}
