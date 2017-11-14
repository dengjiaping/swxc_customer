package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterRegister;
import com.shiwaixiangcun.customer.adapter.RegisterBean;
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
 * 活动报名Activity
 *
 * @author Administrator
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.rv_register)
    RecyclerView mRvRegister;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private List<RegisterBean.ElementsBean> mList;

    private AdapterRegister mAdapterRegister;
    private int currentPage = GlobalConfig.first_page;
    private int pageSize = GlobalConfig.page_size;
    private int siteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initViewAndEvent();

        requestData(currentPage, pageSize, false);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        requestData(currentPage, pageSize, false);
    }

    /**
     * @param start
     * @param size
     * @param isLoadmore
     */
    private void requestData(int start, int size, final boolean isLoadmore) {

        OkGo.<String>get(GlobalAPI.getRegister)
                .params("page.pn", start)
                .params("page.size", size)
                .params("siteId", siteID)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String jsonString = response.body();
                        Type type = new TypeToken<ResponseEntity<RegisterBean>>() {
                        }.getType();
                        ResponseEntity<RegisterBean> data = JsonUtil.fromJson(jsonString, type);
                        if (data == null) {
                            return;
                        }
                        switch (data.getResponseCode()) {

                            case 1001:
                                if (data.getData().getElements().size() == 0) {
                                    mRefreshLayout.finishLoadmore(true);
                                    return;
                                }

                                if (isLoadmore) {

                                    currentPage++;
                                    mRefreshLayout.finishLoadmore(true);
                                } else {
                                    mList.clear();
                                    mRefreshLayout.finishRefresh(true);
                                }

                                mList.addAll(data.getData().getElements());
                                mAdapterRegister.notifyDataSetChanged();
                                break;
                            default:
                                Toast.makeText(mContext, "获取数据失败", Toast.LENGTH_SHORT).show();
                                mRefreshLayout.finishLoadmore(false);
                                break;
                        }
                    }
                });
    }


    private void initViewAndEvent() {
        mBackLeft.setOnClickListener(this);
        mTvPageName.setText(R.string.register);
        siteID = (int) AppSharePreferenceMgr.get(mContext, GlobalConfig.CURRENT_SITE_ID, 0);
        mList = new ArrayList<>();

        mAdapterRegister = new AdapterRegister(mList);
        mRvRegister.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewDivider divider = new RecyclerViewDivider.Builder(this)
                .setOrientation(RecyclerViewDivider.VERTICAL)
                .setStyle(RecyclerViewDivider.Style.END)
                .setMarginLeft(16)
                .setMarginRight(16)
                .setDrawableRes(R.drawable.divider)
                .build();
        mRvRegister.setAdapter(mAdapterRegister);
        mRvRegister.addItemDecoration(divider);

        mAdapterRegister.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {


                Bundle bundle = new Bundle();
                RegisterBean.ElementsBean item = (RegisterBean.ElementsBean) adapter.getData().get(position);
                bundle.putInt("activityID", item.getId());
                readyGo(RegisterDetailActivity.class, bundle);
            }
        });

        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (refreshlayout.isRefreshing()) {
                    refreshlayout.finishRefresh();


                }
                if (currentPage == 1) {
                    currentPage++;
                }
                requestData(currentPage, pageSize, true);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                if (refreshlayout.isLoading()) {
                    refreshlayout.finishLoadmore();

                }
                currentPage = 1;
                requestData(currentPage, pageSize, false);


            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_left:
                finish();
                break;
            default:
                break;

        }

    }
}
