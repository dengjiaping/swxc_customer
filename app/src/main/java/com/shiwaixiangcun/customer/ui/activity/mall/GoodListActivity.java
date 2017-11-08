package com.shiwaixiangcun.customer.ui.activity.mall;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
import com.shiwaixiangcun.customer.adapter.AdapterGoodList;
import com.shiwaixiangcun.customer.event.EventCenter;
import com.shiwaixiangcun.customer.event.SimpleEvent;
import com.shiwaixiangcun.customer.model.ElementBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 商品列表
 *
 * @author Administrator
 */
public class GoodListActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.rv_good_list)
    RecyclerView mRvGoodList;
    AdapterGoodList mAdapterGoodList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    //用于标志当前页面类型
    private int flag = 0;
    private String type;
    private List<ElementBean.ElementsBean> mGoodList = new ArrayList<>();

    //当前页码
    private int mCurrentPage = GlobalConfig.first_page;

    //每页显示数目
    private int mPageSize = GlobalConfig.page_size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_list);
        EventCenter.getInstance().register(this);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventCenter.getInstance().unregister(this);
    }

    private void initView() {
        switch (flag) {
            case 4:
                mTvPageName.setText("每日精选");
                break;
            case 1:
                mTvPageName.setText("品质好货");
                break;
            case 2:
                mTvPageName.setText("热卖商品");
                break;
            case 3:
                mTvPageName.setText("新品特卖");
                break;
            default:
                break;
        }

        mAdapterGoodList = new AdapterGoodList(mGoodList);
        mBackLeft.setOnClickListener(this);
        mRvGoodList.setAdapter(mAdapterGoodList);
        mRvGoodList.setLayoutManager(new LinearLayoutManager(this));
        mAdapterGoodList.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("goodId", mGoodList.get(position).getGoodsId());
                readyGo(GoodDetailActivity.class, bundle);
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {

                refreshlayout.finishLoadmore();
                mCurrentPage = 1;
                requestData(type, flag, mCurrentPage, mPageSize, false);

            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

                if (mCurrentPage == 1) {
                    mCurrentPage++;
                }
                refreshlayout.finishRefresh();
                requestData(type, flag, mCurrentPage, mPageSize, true);
            }
        });
        RecyclerViewDivider divider = new RecyclerViewDivider.Builder(this)
                .setOrientation(RecyclerViewDivider.VERTICAL)
                .setStyle(RecyclerViewDivider.Style.END)
                .setSize(10f)
                .setMarginLeft(8)
                .setMarginRight(8)
                .setDrawableRes(R.drawable.divider)
                .build();
        mRvGoodList.addItemDecoration(divider);


    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("type");
        flag = bundle.getInt("flag");
        requestData(type, flag, mCurrentPage, mPageSize, false);

    }

    /**
     * 请求数据
     *
     * @param type 请求的类型
     * @param flag 更新界面的标志
     */
    public void requestData(String type, final int flag, int start, int size, final boolean isLoadMore) {

        HttpParams params = new HttpParams();
        params.put("goodsSubjectValue", type);
        params.put("page.pn", start);
        params.put("page.size", size);
        params.put("siteId", GlobalConfig.siteID);
        OkGo.<String>get(GlobalAPI.getGuessLike)
                .params(params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String jsonString = response.body();
                        Log.e(BUG_TAG, response.getRawCall().request().toString());
                        Type type = new TypeToken<ResponseEntity<ElementBean>>() {
                        }.getType();
                        Gson gson = new Gson();
                        ResponseEntity<ElementBean> data = gson.fromJson(jsonString, type);
                        if (data == null) {
                            return;
                        }

                        switch (data.getResponseCode()) {
                            case 1001:

                                if (data.getData().getElements().size() == 0) {
                                    mRefreshLayout.finishLoadmore(true);
                                    mRefreshLayout.finishRefresh(true);
                                    return;

                                }

                                if (isLoadMore) {
                                    mCurrentPage++;
                                    EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_GOOD_LIST, flag, data.getData()));
                                    mRefreshLayout.finishLoadmore(true);
                                } else {
                                    mCurrentPage = 1;
                                    mGoodList.clear();
                                    EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_GOOD_LIST, flag, data.getData()));
                                    mRefreshLayout.finishRefresh(true);
                                }
                                break;
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_left:
                finish();
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(SimpleEvent simpleEvent) {
        if (simpleEvent.mEventType != SimpleEvent.UPDATE_GOOD_LIST) {
            return;
        }
        switch (simpleEvent.mEventValue) {
            //更新品质好货
            case 1:
                ElementBean data = (ElementBean) simpleEvent.getData();
                mGoodList.addAll(data.getElements());
                mAdapterGoodList.notifyDataSetChanged();
                break;

            //更新热卖商品
            case 2:
                ElementBean hotData = (ElementBean) simpleEvent.getData();
                mGoodList.addAll(hotData.getElements());
                mAdapterGoodList.notifyDataSetChanged();
                break;
            //更新 新品热卖
            case 3:
                ElementBean newData = (ElementBean) simpleEvent.getData();
                mGoodList.addAll(newData.getElements());
                mAdapterGoodList.notifyDataSetChanged();
                break;
            //更新每日精选
            case 4:
                ElementBean jingxuanData = (ElementBean) simpleEvent.getData();
                mGoodList.addAll(jingxuanData.getElements());
                mAdapterGoodList.notifyDataSetChanged();
                break;

        }
    }

}
