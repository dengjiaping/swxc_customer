package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterAddress;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.AddressBean;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.utils.ItemDecoration;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.RecyclerViewDivider;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 选择地址Activity
 */
public class ChooseAddressActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.tv_top_right)
    TextView mTvTopRight;
    @BindView(R.id.top_bar_write)
    RelativeLayout mTopBarWrite;
    @BindView(R.id.rv_address)
    RecyclerView mRvAddress;

    List<AddressBean> mAddressBeanList = new ArrayList<>();

    AdapterAddress mAdapterAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_address);
        ButterKnife.bind(this);
        initView();
        initEvent();
        iniData();
    }


    private void iniData() {

        final String login_detail = SharePreference.getStringSpParams(this, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        HashMap<String, Object> params = new HashMap<>();
        Log.e(BUG_TAG,responseEntity.getData().getAccess_token());
        params.put("access_token", responseEntity.getData().getAccess_token());
        params.put("fields", "");
        HttpRequest.get("http://mk.shiwaixiangcun.cn/mc/delivery/address/listdata.json", params, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                super.onSuccess(responseJson);
                if (responseJson == null) {
                    return;
                }
                Type type = new TypeToken<ResponseEntity<List<AddressBean>>>() {
                }.getType();
                ResponseEntity<List<AddressBean>> response = JsonUtil.fromJson(responseJson, type);
                if (response == null) {
                    return;
                }
//                mAddressBeanList.addAll(response.getData());
//                mAddressBeanList.addAll(response.getData());
                mAdapterAddress.notifyDataSetChanged();
            }


            @Override
            public void onFailed(Exception e) {

            }
        });


    }

    private void initEvent() {
        mBackLeft.setOnClickListener(this);
        mTvTopRight.setOnClickListener(this);
    }

    private void initView() {
        mTvPageName.setText("收货地址");
        mTvTopRight.setText("管理");
        mTvTopRight.setVisibility(View.VISIBLE);
        mAdapterAddress = new AdapterAddress(mAddressBeanList);
        mRvAddress.setLayoutManager(new LinearLayoutManager(this));

        mRvAddress.setAdapter(mAdapterAddress);
        RecyclerViewDivider recyclerViewDivider = new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL, 10, R.color.black);
//        recyclerViewDivider.paddingLeft(16);
        mRvAddress.addItemDecoration(new ItemDecoration(this, LinearLayoutManager.VERTICAL));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_left:
                finish();
                break;
            case R.id.tv_top_right:
                Bundle bundle=new Bundle();
                bundle.putBoolean("clickable",false);
                readyGo(ManageAddressActivity.class,bundle);
                break;
        }

    }
}
