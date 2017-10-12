package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaeger.recyclerviewdivider.RecyclerViewDivider;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.Site;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 切换站点Activity
 */
public class SiteActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.rv_site)
    RecyclerView mRvSite;
    View headerView;
    private AdapterSite mAdapterSite;
    private List<Site> mSiteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site);
        ButterKnife.bind(this);
        initViewAndEvent();
        initData();
    }

    private void initData() {
        Site site1 = new Site(1, "恒信天鹅堡森林公园");
        Site site2 = new Site(2, "恒信天然森林");
        Site site3 = new Site(3, "恒信三岔湖森林公园");

        mSiteList.add(site1);
        mSiteList.add(site2);
        mSiteList.add(site3);
        mAdapterSite.notifyDataSetChanged();
    }

    private void initViewAndEvent() {

        headerView = LayoutInflater.from(this).inflate(R.layout.header_site, null);
        mBackLeft.setOnClickListener(this);
        mSiteList = new ArrayList<>();
        mAdapterSite = new AdapterSite(mSiteList);
        mAdapterSite.addHeaderView(headerView);
        mRvSite.setLayoutManager(new LinearLayoutManager(this));
        mRvSite.setAdapter(mAdapterSite);
        RecyclerViewDivider divider = new RecyclerViewDivider.Builder(this)
                .setOrientation(RecyclerViewDivider.VERTICAL)
                .setStyle(RecyclerViewDivider.Style.END)
                .setMarginLeft(16)
                .setMarginRight(16)
                .setDrawableRes(R.drawable.divider)
                .build();
        mRvSite.addItemDecoration(divider);

        mAdapterSite.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Site site = (Site) adapter.getData().get(position);
                String name = site.site;
                AppSharePreferenceMgr.put(mContext, GlobalConfig.SITE_NAME, name);
                Intent intent = new Intent();
                intent.putExtra("site", name);
                setResult(RESULT_OK, intent);
                finish();

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