package com.shiwaixiangcun.customer.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.BannerAdapter;
import com.shiwaixiangcun.customer.adapter.HotAndNewAdapter;
import com.shiwaixiangcun.customer.adapter.JingxuanListAdapter;
import com.shiwaixiangcun.customer.adapter.PinzhiAdapter;
import com.shiwaixiangcun.customer.adapter.SearchAdapter;
import com.shiwaixiangcun.customer.adapter.SuggestAdapter;
import com.shiwaixiangcun.customer.adapter.SuggestListAdapter;
import com.shiwaixiangcun.customer.adapter.TestAdapter;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.interfaces.ItemClick;
import com.shiwaixiangcun.customer.model.MallBean;
import com.shiwaixiangcun.customer.model.MallGoods;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 商城Activity
 */

public class MallActivity extends BaseActivity implements View.OnClickListener {

    List<MallBean.DataBean.DailySelectionListBean> mDailyList = new ArrayList<>();
    List<MallGoods.DataBean.ElementsBean> mList = new ArrayList<>();
    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    private Context mContext = null;
    private RecyclerView mRvMall = null;
    private VirtualLayoutManager mLayoutManager = null;
    private DelegateAdapter delegateAdapter = null;
    private LinkedList<DelegateAdapter.Adapter> mAdapters = null;
    private JingxuanListAdapter mDailySelection = null;
    private BannerAdapter mBannerAdapter = null;
    private PinzhiAdapter mPinzhiAdapter = null;
    private SuggestListAdapter suggestListAdapter = null;
    private HotAndNewAdapter hotAndNewAdapter;
    private View mBannerView = null;
    private View mJingxuanView = null;
    private View mSearchView = null;
    private View mSuggersView = null;
    private View mTitleView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall);
        ButterKnife.bind(this);

        mContext = this;
        EventBus.getDefault().register(this);
        initView();
        requestBanner();
        requestData();
        requestList();
    }

    //?goodsSubjectValue=GuessLike&page.pn=1&page.size=6&siteId=1
    private void requestList() {
        HashMap<String, Object> params = new HashMap();
        params.put("goodsSubjectValue", "GuessLike");
        params.put("page.pn", "1");
        params.put("page.size", "6");
        params.put("siteId", "1");
        HttpRequest.get("http://mk.shiwaixiangcun.cn/mi/goods/subject/listpage.json", params, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                MallGoods mallGoods = JsonUtil.fromJson(responseJson, MallGoods.class);
                if (mallGoods != null) {
                    Log.e(BUG_TAG, "请求成功");
                    suggestListAdapter.addData(mallGoods.getData().getElements());
                    suggestListAdapter.notifyDataSetChanged();
                }
                super.onSuccess(responseJson);
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }

    private void requestBanner() {
    }

    private void initHeaders() {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        mBannerView = layoutInflater.inflate(R.layout.layout_mall_banner, null);
        mJingxuanView = layoutInflater.inflate(R.layout.layout_mall_jingxuan, null);
        mSearchView = layoutInflater.inflate(R.layout.layout_mall_search, null);
        mSuggersView = layoutInflater.inflate(R.layout.layout_mall_suggest, null);
        mTitleView = layoutInflater.inflate(R.layout.layout_mall_title, null);
    }

    private void requestData() {
        OkGo.get("http://mk.shiwaixiangcun.cn/mi/goods/subject/home.json")
                .tag(BUG_TAG)
                .cacheKey("mall")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        MallBean mallBean = JsonUtil.fromJson(s, MallBean.class);
                        if (mallBean != null) {
                            mDailyList = mallBean.getData().getDailySelectionList();
                            mDailySelection.setHotListData(mDailyList);
                            mDailySelection.notifyDataSetChanged();
                            EventBus.getDefault().post(mallBean);
                        }
                    }
                });


    }


    /**
     * 初始化视图
     */
    private void initView() {
        mRvMall = (RecyclerView) findViewById(R.id.rv_mall);
        mLayoutManager = new VirtualLayoutManager(this);
        mRvMall.setLayoutManager(mLayoutManager);


//        mRvMall.addItemDecoration(new MyDecoration(this, LinearLayoutManager.VERTICAL));
//        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
//        mRvMall.setRecycledViewPool(viewPool);
//        viewPool.setMaxRecycledViews(0, 5);
//        viewPool.setMaxRecycledViews(1, 7);


        mTvPageName.setText("商城");
        mBackLeft.setOnClickListener(this);
        mAdapters = new LinkedList<>();
        setSearch(mAdapters);
        setBanner(mAdapters);

        setJingxuanList(mAdapters);
        setSingleAdapter(mAdapters);
//        setPinzhiAdapter(mAdapters);
//        setHotAndNew(mAdapters);
        setSuggest(mAdapters);
        setList(mAdapters);
        bind(mAdapters);
    }

    private void setSingleAdapter(LinkedList<DelegateAdapter.Adapter> mAdapters) {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        TestAdapter testAdapter = new TestAdapter(this, singleLayoutHelper, 1);
        mAdapters.add(testAdapter);
    }

    private void setSearch(LinkedList<DelegateAdapter.Adapter> mAdapters) {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        SearchAdapter searchAdapter = new SearchAdapter(this, linearLayoutHelper, 1);
        searchAdapter.setItemClick(new ItemClick() {
            @Override
            public void onItemClick(View view, int position) {


                // TODO: 2017/9/11 点击跳转进行修改
//                readyGo(SearchResultActivity.class);
                readyGo(GoodDetailActivity.class);

            }
        });
        mAdapters.add(searchAdapter);
    }

    /**
     * 品质好货 模块
     *
     * @param mAdapters
     */
    private void setPinzhiAdapter(LinkedList<DelegateAdapter.Adapter> mAdapters) {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        mPinzhiAdapter = new PinzhiAdapter(this, singleLayoutHelper, 1);
        mAdapters.add(mPinzhiAdapter);
    }

    private void bind(LinkedList<DelegateAdapter.Adapter> mAdapters) {


        DelegateAdapter delegateAdapter = new DelegateAdapter(mLayoutManager);
        delegateAdapter.setAdapters(mAdapters);
        mRvMall.setAdapter(delegateAdapter);
    }

    /**
     * Banner模块
     *
     * @param adapters
     */
    public void setBanner(LinkedList<DelegateAdapter.Adapter> adapters) {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        mBannerAdapter = new BannerAdapter(this, linearLayoutHelper, 1);
        adapters.add(mBannerAdapter);
    }

    /**
     * 设置精选List
     *
     * @param mAdapters
     */
    private void setJingxuanList(LinkedList<DelegateAdapter.Adapter> mAdapters) {
        //设置通栏布局
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        singleLayoutHelper.setMarginBottom(30);
        mDailySelection = new JingxuanListAdapter(this, singleLayoutHelper, 1);
        mAdapters.add(mDailySelection);
    }

    private void setHotAndNew(LinkedList<DelegateAdapter.Adapter> mAdapters) {
        OnePlusNLayoutHelper onePlusNLayoutHelper = new OnePlusNLayoutHelper();
        onePlusNLayoutHelper.setItemCount(2);
//        onePlusNLayoutHelper.setBgColor(getResources().getColor());
        onePlusNLayoutHelper.setMarginTop(10);
        hotAndNewAdapter = new HotAndNewAdapter(this, onePlusNLayoutHelper, 2);
        mAdapters.add(hotAndNewAdapter);
    }

    private void setSuggest(LinkedList<DelegateAdapter.Adapter> mAdapters) {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        singleLayoutHelper.setMarginTop(30);
        SuggestAdapter suggestAdapter = new SuggestAdapter(this, singleLayoutHelper, 1);
        mAdapters.add(suggestAdapter);
    }

    private void setList(LinkedList<DelegateAdapter.Adapter> mAdapters) {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setDividerHeight(3);
        suggestListAdapter = new SuggestListAdapter(this, linearLayoutHelper, mList);
        mAdapters.add(suggestListAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateGoods(MallBean mallBean) {
        MallBean.DataBean data = mallBean.getData();
        mPinzhiAdapter.addData(data.getQualityGoods());
//        List<GoodBean> list=new ArrayList<>();
//        list.add(data.getQualityGoods())
//        hotAndNewAdapter.addData(data.get)
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_left:


                //// TODO: 2017/9/11  点击跳转修改
                readyGo(ConfirmOrderActivity.class);
//                readyGo(MallCategoryActivity.class);

                break;
        }
    }
}
