package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.jaeger.recyclerviewdivider.RecyclerViewDivider;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterSite;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.model.Site;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.lang.reflect.Type;
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

        OkGo.<String>get(GlobalAPI.getSite)
                .params("fields", "id,name,defaultShow")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<ResponseEntity<List<Site>>>() {
                        }.getType();
                        ResponseEntity<List<Site>> responseEntity = JsonUtil.fromJson(response.body(), type);
                        if (responseEntity == null) {
                            return;
                        }
                        switch (responseEntity.getResponseCode()) {
                            case 1001:
                                mSiteList.addAll(responseEntity.getData());
        mAdapterSite.notifyDataSetChanged();
                                break;
                            default:
                                showToastShort("获取数据失败");
                                break;
    }
                    }
                });
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
                String name = site.getName();
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
