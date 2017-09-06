package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.BannerAdapter;
import com.shiwaixiangcun.customer.adapter.JingxuanListAdapter;
import com.shiwaixiangcun.customer.adapter.PinzhiAdapter;
import com.shiwaixiangcun.customer.mall.HotThing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 商城Activity
 */

public class MallActivity extends AppCompatActivity {

    List<HotThing> mList = new ArrayList<>();
    private RecyclerView mRvMall = null;
    private VirtualLayoutManager mLayoutManager = null;
    private DelegateAdapter delegateAdapter = null;
    private boolean isBanner = true;
    private LinkedList<DelegateAdapter.Adapter> mAdapters = null;
    private JingxuanListAdapter mHotListAdapter = null;
    private BannerAdapter mBannerAdapter = null;
    private PinzhiAdapter mPinzhiAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall);

        initView();
        initData();
    }

    private void initData() {

        for (int i = 0; i < 6; i++) {
            HotThing hotThing = new HotThing("产品 " + i, "2000", "http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg");
            mList.add(hotThing);

        }
        mHotListAdapter.setHotListData(mList);
        mHotListAdapter.notifyDataSetChanged();
    }


    /**
     * 初始化视图
     */
    private void initView() {
        mRvMall = (RecyclerView) findViewById(R.id.rv_mall);
        mLayoutManager = new VirtualLayoutManager(this);
        mRvMall.setLayoutManager(mLayoutManager);
        delegateAdapter = new DelegateAdapter(mLayoutManager, true);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        mRvMall.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 5);
        viewPool.setMaxRecycledViews(1, 7);
        mAdapters = new LinkedList<>();
        setBanner(mAdapters);
        setJingxuanList(mAdapters);
        setPinzhiAdapter(mAdapters);
        bind(mLayoutManager, mAdapters);
    }

    private void setPinzhiAdapter(LinkedList<DelegateAdapter.Adapter> mAdapters) {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        mPinzhiAdapter = new PinzhiAdapter(this, singleLayoutHelper, 1);
        mAdapters.add(mPinzhiAdapter);
    }

    private void bind(VirtualLayoutManager mLayoutManager, LinkedList<DelegateAdapter.Adapter> mAdapters) {

        delegateAdapter.setAdapters(mAdapters);
        mRvMall.setAdapter(delegateAdapter);
    }

    /**
     * 设置Banner布局
     *
     * @param header
     */
    public void setBanner(LinkedList<DelegateAdapter.Adapter> header) {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        mBannerAdapter = new BannerAdapter(this, linearLayoutHelper, 1);
        header.add(mBannerAdapter);
    }

    /**
     * 设置精选List
     *
     * @param mAdapters
     */
    private void setJingxuanList(List<DelegateAdapter.Adapter> mAdapters) {
        //设置通栏布局
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        singleLayoutHelper.setMarginBottom(30);
        mHotListAdapter = new JingxuanListAdapter(this, singleLayoutHelper, 1);
        mAdapters.add(mHotListAdapter);
    }
}
