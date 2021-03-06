package com.shiwaixiangcun.customer.ui.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterAfterService;
import com.shiwaixiangcun.customer.http.StringDialogCallBack;
import com.shiwaixiangcun.customer.model.AfterServiceBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.DisplayUtil;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.RefreshTokenUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 售后服务页面
 *
 * @author Administrator
 */

public class AfterServiceActivity extends BaseActivity {


    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.rv_after_service)
    RecyclerView mRvAfterService;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.cl_nodata)
    ConstraintLayout mClNodata;
    private AdapterAfterService mAdapterAfterService;
    private List<AfterServiceBean.ElementsBean> mAfterServiceList;
    private String tokenString;
    private String refreshToken;

    private int mCurrentPage = GlobalConfig.first_page;
    private int mPageSize = GlobalConfig.page_size;

    public AfterServiceActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_service);
        ButterKnife.bind(this);
        initToken();
        initViewAndEvent();
        requestData(false);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initToken();
        mCurrentPage = 1;
        requestData(false);
    }

    private void initToken() {
        refreshToken = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.Refresh_token, "");
        tokenString = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.TOKEN, "");
    }

    private void requestData(final boolean isLoadMore) {


        OkGo.<String>get(GlobalAPI.afterService)
                .params("access_token", tokenString)
                .params("page.pn", mCurrentPage)
                .params("page.size", mPageSize)
                .execute(new StringDialogCallBack(this) {

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.finishLoadmore();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<ResponseEntity<AfterServiceBean>>() {
                        }.getType();

                        ResponseEntity<AfterServiceBean> responseEntity = JsonUtil.fromJson(response.body(), type);
                        if (responseEntity == null) {
                            return;

                        }
                        switch (responseEntity.getResponseCode()) {
                            case 1001:
                                if (isLoadMore) {
                                    mCurrentPage++;
                                    mRefreshLayout.finishLoadmore();
                                } else {

                                    if (responseEntity.getData().getElements().size() == 0) {
                                        mClNodata.setVisibility(View.VISIBLE);
                                    } else {
                                        mClNodata.setVisibility(View.GONE);
                                    }
                                    mRefreshLayout.finishRefresh();
                                    mAfterServiceList.clear();
                                }
                                mAfterServiceList.addAll(responseEntity.getData().getElements());
                                mAdapterAfterService.notifyDataSetChanged();
                                break;
                            case 1018:
                                RefreshTokenUtil.sendIntDataInvatation(mContext, refreshToken);
                                break;
                            default:
                                Log.e(BUG_TAG, "加载失败");
                                break;
                        }

                    }
                });


    }

    private void initViewAndEvent() {
        mTvPageName.setText(R.string.after_service);
        mBackLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mAfterServiceList = new ArrayList<>();
        mAdapterAfterService = new AdapterAfterService(mAfterServiceList);
        mRvAfterService.setLayoutManager(new LinearLayoutManager(mContext));
        mRvAfterService.setAdapter(mAdapterAfterService);
        mRvAfterService.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 0, DisplayUtil.dip2px(mContext, 16));
            }
        });
        mAdapterAfterService.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AfterServiceBean.ElementsBean bean = (AfterServiceBean.ElementsBean) adapter.getData().get(position);
                Bundle bundle = new Bundle();
                bundle.putInt("id", bean.getId());
                readyGo(AfterDetailActivity.class, bundle);
            }
        });

        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (mRefreshLayout.isRefreshing()) {
                    mRefreshLayout.finishRefresh();
                }

                if (mCurrentPage == 1) {
                    mCurrentPage++;
                }
                requestData(true);

            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (mRefreshLayout.isLoading()) {
                    mRefreshLayout.finishLoadmore();
                }

                mCurrentPage = 1;
                requestData(false);

            }
        });


    }
}
