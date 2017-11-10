package com.shiwaixiangcun.customer.ui.activity.mall;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.recyclerviewdivider.RecyclerViewDivider;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterSearchResult;
import com.shiwaixiangcun.customer.event.EventCenter;
import com.shiwaixiangcun.customer.event.SimpleEvent;
import com.shiwaixiangcun.customer.model.ElementBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品分类的Activity
 *
 * @author Administrator
 */
public class CategoryActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.tv_top_right)
    TextView mTvTopRight;
    @BindView(R.id.iv_share_right)
    ImageView mIvShareRight;
    @BindView(R.id.iv_sao_right)
    ImageView mIvSaoRight;
    @BindView(R.id.ll_image_right)
    LinearLayout mLlImageRight;
    @BindView(R.id.top_bar_write)
    RelativeLayout mTopBarWrite;
    @BindView(R.id.rv_category)
    RecyclerView mRvCategory;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.iv_no)
    ImageView mIvNo;
    @BindView(R.id.tv_prompt)
    TextView mTvPrompt;
    @BindView(R.id.rlayout_no_data)
    RelativeLayout mRlayoutNoData;

    private String mCatrgotyName;
    private int mCategoryId;
    private AdapterSearchResult mAdapter;
    private List<ElementBean.ElementsBean> mList = new ArrayList<>();

    private int pgCount = 1;

    private int siteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        EventCenter.getInstance().register(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        siteId = (int) AppSharePreferenceMgr.get(mContext, GlobalConfig.CURRENT_SITE_ID, GlobalConfig.DEFAULT_SITE_ID);
        mCategoryId = bundle.getInt("categoryId");
        mCatrgotyName = bundle.getString("categoryName");
        ButterKnife.bind(this);
        initView();
        initData();

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    private void initData() {
        requestData(mCategoryId, pgCount, 15, 1);
    }

    /**
     * 请求数据
     *
     * @param categoryId 商品名称
     * @param pg         页数
     * @param size       每页数量
     * @param isLoadMore 标志是否是上拉加载  1代表下拉刷新和普通请求   2代表上拉加载更多
     */
    private void requestData(int categoryId, int pg, int size, final int isLoadMore) {
        Log.e(BUG_TAG, "请求数据");
        HttpParams httpParams = new HttpParams();
        httpParams.put("categoryId", categoryId);
        httpParams.put("page.pn", pg);
        httpParams.put("page.size", size);
        httpParams.put("siteId", siteId);
        OkGo.<String>get(GlobalAPI.searchCategory)
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        mRefreshLayout.finishRefresh();
                        Log.e(BUG_TAG, response.getRawCall().request().toString());
                        Log.e(BUG_TAG, "success");
                        String jsonString = response.body();
                        Type type = new TypeToken<ResponseEntity<ElementBean>>() {
                        }.getType();
                        Gson gson = new Gson();
                        ResponseEntity<ElementBean> data = gson.fromJson(jsonString, type);
                        if (data == null) {
                            return;
                        }

                        switch (data.getResponseCode()) {
                            case 1001:
                                EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_CATEGORY, isLoadMore, data.getData()));
                                break;
                            default:
                                mRlayoutNoData.setVisibility(View.VISIBLE);
                                break;
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e(BUG_TAG, "onError");
                    }
                });


    }

    private void initView() {
        mTvPageName.setText(mCatrgotyName);
        mBackLeft.setOnClickListener(this);
        mRvCategory.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AdapterSearchResult(mList);
        mRvCategory.setAdapter(mAdapter);
        RecyclerViewDivider divider = new RecyclerViewDivider.Builder(this)
                .setOrientation(RecyclerViewDivider.VERTICAL)
                .setStyle(RecyclerViewDivider.Style.END)
                .setMarginLeft(16)
                .setMarginRight(16)
                .setDrawableRes(R.drawable.divider)
                .build();
        mRvCategory.addItemDecoration(divider);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("goodId", mList.get(position).getGoodsId());
                readyGo(GoodDetailActivity.class, bundle);
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                requestData(mCategoryId, 1, 15, 1);
                refreshlayout.finishRefresh(2000);
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                requestData(mCategoryId, pgCount, 15, 2);
                refreshlayout.finishLoadmore(2000);
            }
        });
    }

    /**
     * 从服务器获取数据以后更新
     *
     * @param simpleEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(SimpleEvent simpleEvent) {
        if (simpleEvent.mEventType != SimpleEvent.UPDATE_CATEGORY) {
            return;
        }
        switch (simpleEvent.mEventValue) {
            case 1:
                mRefreshLayout.finishRefresh();
                ElementBean guessData = (ElementBean) simpleEvent.getData();
                if (guessData.getElements().size() == 0) {
                    mRlayoutNoData.setVisibility(View.VISIBLE);
                }
                mList.clear();
                mList.addAll(guessData.getElements());
                mAdapter.notifyDataSetChanged();
                break;
            case 2:
                pgCount += 1;
                mRefreshLayout.finishLoadmore();

                ElementBean loadMoreData = (ElementBean) simpleEvent.getData();
                mList.addAll(loadMoreData.getElements());
                mAdapter.notifyDataSetChanged();
                break;

            default:
                break;


        }
    }

    @Override
    protected void onDestroy() {
        EventCenter.getInstance().unregister(this);
        super.onDestroy();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_left:
                finish();
                break;
            default:
                break;
        }
    }
}
