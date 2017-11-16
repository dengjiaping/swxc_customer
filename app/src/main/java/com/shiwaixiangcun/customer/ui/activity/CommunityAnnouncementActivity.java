package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.jaeger.recyclerviewdivider.RecyclerViewDivider;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterMain;
import com.shiwaixiangcun.customer.model.NoticeBean;
import com.shiwaixiangcun.customer.model.PageBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Administrator
 */
public class CommunityAnnouncementActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.lv_community)
    RecyclerView mLvCommunity;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.cl_nodata)
    ConstraintLayout mClNodata;

    private int siteId;
    private int mCurrentPage;
    private int mPageSize;

    private AdapterMain mAdapterMain;

    private List<AdapterMain.MultipleItem> mMainList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_announcement);
        ButterKnife.bind(this);

        initViewAndEvent();
        initData(mCurrentPage, mPageSize, false);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData(mCurrentPage, mPageSize, false);
    }

    private void initViewAndEvent() {
        mBackLeft.setOnClickListener(this);
        mTvPageName.setText("社区公告");
        siteId = (int) AppSharePreferenceMgr.get(mContext, GlobalConfig.CURRENT_SITE_ID, 0);
        mCurrentPage = GlobalConfig.first_page;
        mPageSize = GlobalConfig.page_size;

        mMainList = new ArrayList<>();
        RecyclerViewDivider divider = new RecyclerViewDivider.Builder(mContext)
                .setOrientation(RecyclerViewDivider.VERTICAL)
                .setStyle(RecyclerViewDivider.Style.END)
                .setMarginLeft(16)
                .setMarginRight(16)
                .setColorRes(R.color.color_divider_0_3)
                .build();
        mLvCommunity.addItemDecoration(divider);
        mAdapterMain = new AdapterMain(mMainList);
        mLvCommunity.setLayoutManager(new LinearLayoutManager(this));
        mLvCommunity.setAdapter(mAdapterMain);
        mAdapterMain.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AdapterMain.MultipleItem item = (AdapterMain.MultipleItem) adapter.getData().get(position);
                NoticeBean data = item.getData();

                switch (data.getArticleShowType()) {
                    case "ACTIVITY":

                        Bundle bundle = new Bundle();

                        bundle.putInt("activityID", data.getId());
                        readyGo(RegisterDetailActivity.class, bundle);
                        break;
                    default:
                        Intent intent = new Intent(mContext, DetailsActivity.class);
                        intent.putExtra("articleId", data.getId() + "");
                        intent.putExtra("detailTitle", data.getTitle() + "");
                        intent.putExtra("detailContent", data.getSummary());
                        startActivity(intent);
                        break;
                }


            }
        });

        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (refreshlayout.isLoading()) {
                    refreshlayout.finishLoadmore();
                }
                mCurrentPage = 1;
                initData(mCurrentPage, mPageSize, false);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (refreshlayout.isRefreshing()) {
                    refreshlayout.finishRefresh();
                }

                if (mCurrentPage == 1) {
                    mCurrentPage++;
                }
                initData(mCurrentPage, mPageSize, true);
            }
        });
    }

    private void initData(int start, int size, final boolean isLoadMore) {

        OkGo.<String>get(Common.articleListpage)
                .params("siteId", siteId)
                .params("page.pn", start)
                .params("page.size", size)
                .params("position", "COMMUNITY_ANNOUNCEMENT")
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.finishLoadmore();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {

                        Type type = new TypeToken<ResponseEntity<PageBean<NoticeBean>>>() {
                        }.getType();
                        ResponseEntity<PageBean<NoticeBean>> responseEntity = JsonUtil.fromJson(response.body(), type);

                        if (responseEntity == null) {
                            return;
                        }
                        if (isLoadMore) {
                            mRefreshLayout.finishLoadmore();
                            List<NoticeBean> list = responseEntity.getData().getElements();
                            for (NoticeBean bean : list) {
                                AdapterMain.MultipleItem multipleItem;
                                switch (bean.getArticleShowType()) {
                                    case "ARTICLE_IMAGE":
                                        multipleItem = new AdapterMain.MultipleItem(1, bean);
                                        break;
                                    case "ACTIVE":
                                        multipleItem = new AdapterMain.MultipleItem(3, bean);
                                        break;
                                    case "ACTIVITY":
                                        multipleItem = new AdapterMain.MultipleItem(4, bean);
                                        break;
                                    default:
                                        multipleItem = new AdapterMain.MultipleItem(3, bean);
                                        break;
                                }
                                mMainList.add(multipleItem);
                            }
                        } else {
                            mRefreshLayout.finishRefresh();
                            if (responseEntity.getData().getElements().size() == 0) {
                                mClNodata.setVisibility(View.VISIBLE);
                            } else {
                                mMainList.clear();
                                List<NoticeBean> list = responseEntity.getData().getElements();
                                for (NoticeBean bean : list) {
                                    AdapterMain.MultipleItem multipleItem;
                                    switch (bean.getArticleShowType()) {
                                        case "ARTICLE_IMAGE":
                                            multipleItem = new AdapterMain.MultipleItem(1, bean);
                                            break;
                                        case "ACTIVE":
                                            multipleItem = new AdapterMain.MultipleItem(3, bean);
                                            break;

                                        case "ACTIVITY":
                                            multipleItem = new AdapterMain.MultipleItem(4, bean);
                                            break;

                                        default:
                                            multipleItem = new AdapterMain.MultipleItem(3, bean);
                                            break;
                                    }

                                    mMainList.add(multipleItem);
                                }
                            }


                        }

                        mAdapterMain.notifyDataSetChanged();
                    }
                });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.back_left:
                finish();
                break;
            default:
                break;
        }
    }

}
