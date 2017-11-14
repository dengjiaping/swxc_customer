package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
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
import com.shiwaixiangcun.customer.ContextSession;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterRight;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.model.RightsRecordBean;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.RefreshTokenUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Administrator
 *         维权记录
 */
public class RightsRecordActivity extends BaseActivity {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.rv_right_record)
    RecyclerView mRvRightRecord;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rlayout_no_data)
    RelativeLayout mRlayoutNoData;

    private List<RightsRecordBean.ElementsBean> mList;
    private AdapterRight mAdapterRight;
    private String refreshToken;
    private String tokenString;
    private int mCurrentPage = GlobalConfig.first_page;
    private int mPageSize = GlobalConfig.page_size;
    private int siteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rights_record);
        ButterKnife.bind(this);
        initViewAndEvent();
        initToken();
        requestData(mCurrentPage, mPageSize, false);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        initToken();
    }

    private void initToken() {
        refreshToken = ContextSession.getTokenString();
        tokenString = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.TOKEN, "");
    }

    private void requestData(int page, int pageSize, final boolean isLoadMore) {

        OkGo.<String>get(GlobalAPI.rightRecord)
                .params("access_token", tokenString)
                .params("page.pn", page)
                .params("page.size", pageSize)
                .params("siteId", siteID)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.finishLoadmore();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(BUG_TAG, "success");
                        Type type = new TypeToken<ResponseEntity<RightsRecordBean>>() {
                        }.getType();

                        ResponseEntity<RightsRecordBean> responseEntity = JsonUtil.fromJson(response.body(), type);
                        if (responseEntity == null) {
                            return;

                        }
                        switch (responseEntity.getResponseCode()) {
                            case 1001:
                                if (!isLoadMore && responseEntity.getData().getElements().size() == 0) {
                                    mRlayoutNoData.setVisibility(View.VISIBLE);

                                } else {
                                    if (isLoadMore) {
                                        mCurrentPage++;
                                        mRefreshLayout.finishLoadmore();
                                    } else {
                                        mCurrentPage = 1;
                                        mList.clear();
                                        mRefreshLayout.finishRefresh();
                                    }
                                    mList.addAll(responseEntity.getData().getElements());
                                    mAdapterRight.notifyDataSetChanged();
                                }
                                break;
                            case 1018:
                                mRefreshLayout.finishRefresh();
                                mRefreshLayout.finishLoadmore();
                                RefreshTokenUtil.sendIntDataInvatation(mContext, refreshToken);
                                break;
                            default:
                                Log.e(BUG_TAG, "加载失败");
                                mRefreshLayout.finishRefresh();
                                mRefreshLayout.finishLoadmore();
                                break;
                        }


                    }
                });

    }

    private void initViewAndEvent() {
        siteID = (int) AppSharePreferenceMgr.get(mContext, GlobalConfig.CURRENT_SITE_ID, 0);
        mTvPageName.setText("维权记录");
        mBackLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mList = new ArrayList<>();
        mAdapterRight = new AdapterRight(mList);
        mRvRightRecord.setLayoutManager(new LinearLayoutManager(mContext));
        mRvRightRecord.setAdapter(mAdapterRight);
        RecyclerViewDivider divider = new RecyclerViewDivider.Builder(mContext)
                .setOrientation(RecyclerViewDivider.VERTICAL)
                .setStyle(RecyclerViewDivider.Style.END)
                .setMarginLeft(16)
                .setMarginRight(0)
                .setDrawableRes(R.drawable.divider)
                .build();
        mRvRightRecord.addItemDecoration(divider);
        mAdapterRight.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RightsRecordBean.ElementsBean bean = (RightsRecordBean.ElementsBean) adapter.getData().get(position);

                Bundle bundle = new Bundle();
                bundle.putParcelable("detail", bean);
                readyGo(RightDetailActivity.class, bundle);
            }
        });

        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                refreshlayout.finishLoadmore();

                mCurrentPage = 1;
                requestData(mCurrentPage, mPageSize, false);
            }

            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh();
                if (mCurrentPage == 1) {
                    mCurrentPage++;
                }
                requestData(mCurrentPage, mPageSize, true);

            }
        });

    }
}
