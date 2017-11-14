package com.shiwaixiangcun.customer.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.jaeger.recyclerviewdivider.RecyclerViewDivider;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterRecipe;
import com.shiwaixiangcun.customer.model.RecipeBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.ui.activity.RecipeArticleActivity;
import com.shiwaixiangcun.customer.utils.JsonUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/10/11.
 */

public class RecipeFragment extends LazyFragment {


    private static String BUG_TAG = "RecipeFragment";
    @BindView(R.id.rv_recipe)
    RecyclerView mRvRecipe;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.cl_nodata)
    ConstraintLayout mClNodata;
    Unbinder unbinder;


    private String mTitle;
    private int mId;
    private List<RecipeBean.ElementsBean> mRecipeList;
    private AdapterRecipe mAdapterRecipe;
    private int currentPage = GlobalConfig.first_page;
    private int pageSize = GlobalConfig.page_size;

    public static Fragment getInstance(String title, Integer id) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", title);
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
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
        mAdapterRecipe.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                RecipeBean.ElementsBean bean = (RecipeBean.ElementsBean) adapter.getData().get(position);
                Bundle bundle = new Bundle();
                bundle.putString("articleTitle", bean.getTitle());
                bundle.putInt("articleId", bean.getArticleId());
                readyGo(RecipeArticleActivity.class, bundle);
            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore();
                currentPage = 1;
                requestData(mId, currentPage, pageSize, false);

            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (currentPage == 1) {
                    currentPage++;
                }
                refreshlayout.finishRefresh();
                requestData(mId, currentPage, pageSize, true);

            }
        });
    }


    /**
     * 获取列表数据
     *
     * @param id       当前列表id
     * @param page     页码
     * @param pageSize 每页显示数目
     * @param loadMore 是否上拉加载
     */
    public void requestData(int id, int page, int pageSize, final boolean loadMore) {
        OkGo.<String>get(GlobalAPI.getRecipeList)
                .params("dietTypeId", id)
                .params("page.pn", page)
                .params("page.size", pageSize)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        Log.e(BUG_TAG, "success");
                        Type type = new TypeToken<ResponseEntity<RecipeBean>>() {
                        }.getType();

                        ResponseEntity<RecipeBean> responseEntity = JsonUtil.fromJson(response.body(), type);
                        if (responseEntity == null) {
                            return;

                        }
                        switch (responseEntity.getResponseCode()) {
                            case 1001:

                                if (loadMore) {

                                    if (responseEntity.getData().getElements().size() == 0) {
                                        mRefreshLayout.finishLoadmore(true);
                                    } else {
                                        currentPage++;
                                        mRecipeList.addAll(responseEntity.getData().getElements());
                                    }

                                } else {
                                    if (responseEntity.getData().getElements().size() == 0) {
                                        mClNodata.setVisibility(View.VISIBLE);
                                        mRefreshLayout.finishRefresh();
                                    } else {
                                        currentPage = 1;
                                        mRecipeList.clear();
                                        mRecipeList.addAll(responseEntity.getData().getElements());
                                        mRefreshLayout.finishRefresh(true);
                                    }
                                }
                                mAdapterRecipe.notifyDataSetChanged();
                                break;
                            default:
                                Log.e(BUG_TAG, "加载失败");
                                break;
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e(BUG_TAG, "error");
                    }
                });


    }

    @Override
    protected void onFirstUserVisible() {
        requestData(mId, currentPage, pageSize, false);


    }

    @Override
    protected void onUserVisible() {
        requestData(mId, currentPage, pageSize, false);

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void destroyViewAndThing() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();
        mTitle = (String) getArguments().get("name");
        mId = (int) getArguments().get("id");
        Log.e(BUG_TAG, "名字:" + mTitle + "id：" + mId);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
