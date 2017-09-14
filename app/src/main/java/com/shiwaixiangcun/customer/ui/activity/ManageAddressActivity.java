package com.shiwaixiangcun.customer.ui.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterManageAddress;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.interfaces.onCheckboxClickListener;
import com.shiwaixiangcun.customer.model.AddressBean;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.response.ResponseEntity;
import com.shiwaixiangcun.customer.utils.DisplayUtil;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.ShareUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManageAddressActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.tv_top_right)
    TextView mTvTopRight;
    @BindView(R.id.ll_image_right)
    LinearLayout mLlImageRight;
    @BindView(R.id.top_bar_write)
    RelativeLayout mTopBarWrite;
    @BindView(R.id.rv_address)
    RecyclerView mRvAddress;
    List<AddressBean> mAddressBeanList = new ArrayList<>();
    AdapterManageAddress mManageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_address);
        ButterKnife.bind(this);
        initView();
        initEvent();
        initData();
    }


    private void initData() {

        Log.e(BUG_TAG, "iniData");
        final String login_detail = ShareUtil.getStringSpParams(this, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        HashMap<String, Object> params = new HashMap<>();
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
                mAddressBeanList.addAll(response.getData());
                mAddressBeanList.addAll(response.getData());
                Log.e(BUG_TAG, mAddressBeanList.size() + "");
                mManageAdapter.notifyDataSetChanged();
            }


            @Override
            public void onFailed(Exception e) {

            }
        });


    }

    private void initEvent() {
        mBackLeft.setOnClickListener(this);

    }

    private void initView() {
        mTvPageName.setText("管理地址");
        mManageAdapter = new AdapterManageAddress(mAddressBeanList);
        mRvAddress.setLayoutManager(new LinearLayoutManager(this));
        mRvAddress.setAdapter(mManageAdapter);
        mRvAddress.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 0, DisplayUtil.dip2px(mContext, 10));
            }
        });
        mManageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(mContext, "item点击", Toast.LENGTH_SHORT).show();
            }
        });
        mManageAdapter.setCheckboxClickListener(new onCheckboxClickListener() {
            @Override
            public void checkboxClick(int position, View view, boolean b) {
                Toast.makeText(mContext, "checkBox点击///////////" + position, Toast.LENGTH_SHORT).show();
                Log.e("adapter", "点击");
                // TODO: 2017/9/14
            }


        });
        mManageAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_delete:
                        Toast.makeText(mContext, "点击删除", Toast.LENGTH_SHORT).show();
                        // TODO: 2017/9/14
                        break;
                    case R.id.tv_edit:
                        readyGo(EditAddressActivity.class);
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
}
