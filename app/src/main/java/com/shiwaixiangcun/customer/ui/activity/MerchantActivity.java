package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jaeger.recyclerviewdivider.RecyclerViewDivider;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterMerchant;
import com.shiwaixiangcun.customer.model.MerchantListBean;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MerchantActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.rv_merchant)
    RecyclerView mRvMerchant;

    private List<MerchantListBean.DataBean.ElementsBean> mDataList;
    private AdapterMerchant mAdapterMerchant;
    private String sign;
    private String strName;
    private int currentPage = GlobalConfig.first_page;
    private int pageSize = GlobalConfig.page_size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);
        ButterKnife.bind(this);
        initViewAndEvent();
        initData(currentPage, pageSize, false);
    }

    /**
     * 获取数据
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @param loadMore 是否加载更多
     */
    private void initData(int page, int pageSize, final boolean loadMore) {
        OkGo.<String>get(GlobalAPI.getMerchant)
                .params("sign", sign)
                .params("page.pn", page)
                .params("page.size", pageSize)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        MerchantListBean merchantListBean = JsonUtil.fromJson(response.body(), MerchantListBean.class);
                        if (merchantListBean == null) {
                            return;
                        }
                        switch (merchantListBean.getResponseCode()) {
                            case 1001:


                                if (loadMore) {
                                    currentPage++;
                                    mDataList.addAll(merchantListBean.getData().getElements());
                                    mRefreshLayout.finishLoadmore();
                                } else {
                                    currentPage = 1;
                                    mDataList.clear();
                                    mDataList.addAll(merchantListBean.getData().getElements());
                                    mRefreshLayout.finishRefresh();
                                }


                                mAdapterMerchant.notifyDataSetChanged();
                                break;
                            default:
                                showToastShort("获取失败");
                                mRefreshLayout.finishLoadmore();
                                mRefreshLayout.finishRefresh();
                                break;
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        showToastShort("网络连接错误...");
                    }
                });
    }
    private void initViewAndEvent() {
        sign = getIntent().getExtras().getString("sign");
//        FOOD_AND_BEVERAGE("餐饮美食"), HOTELS_AND_LODGING("酒店住宿"), SUPERMARKET("超市"), HOSPITAL("医院"), FIND_DECORATION("找装修"),
        switch (sign) {
            case "SUPERMARKET":
                strName = "超市";
                break;
            case "FOOD_AND_BEVERAGE":
                strName = "餐饮美食";
                break;
            case "HOTELS_AND_LODGING":
                strName = "酒店住宿";
                break;
            case "FIND_DECORATION":
                strName = "找装修";
                break;
            case "HOSPITAL":
                strName = "医院";
                break;
        }
        mDataList = new ArrayList<>();
        mTvPageName.setText(strName);
        mBackLeft.setOnClickListener(this);
        mAdapterMerchant = new AdapterMerchant(mDataList);
        mRvMerchant.setLayoutManager(new LinearLayoutManager(this));
        mRvMerchant.setAdapter(mAdapterMerchant);
        RecyclerViewDivider divider = new RecyclerViewDivider.Builder(mContext)
                .setOrientation(RecyclerViewDivider.VERTICAL)
                .setStyle(RecyclerViewDivider.Style.END)
                .setMarginLeft(16)
                .setMarginRight(16)
                .setDrawableRes(R.drawable.divider)
                .build();
        mRvMerchant.addItemDecoration(divider);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore();
                currentPage = 1;
                initData(currentPage, pageSize, false);

            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (currentPage == 1) {
                    currentPage++;
                }
                refreshlayout.finishRefresh();
                initData(currentPage, pageSize, true);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_left:
                finish();
                break;
        }
    }
}
