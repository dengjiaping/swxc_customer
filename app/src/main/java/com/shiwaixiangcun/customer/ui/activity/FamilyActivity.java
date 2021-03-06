package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.jaeger.recyclerviewdivider.RecyclerViewDivider;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.ContextSession;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterMyFamily;
import com.shiwaixiangcun.customer.model.MyFamilyBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.RefreshTokenUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的家人页面
 */
public class FamilyActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.rv_family)
    RecyclerView mRvFamily;
    @BindView(R.id.btn_add_family)
    Button mBtnAddFamily;
    AdapterMyFamily mMyFamilyAdapter;
    List<MyFamilyBean.DataBean> mMyFamilyList;
    @BindView(R.id.llayout_nodata)
    LinearLayout mLlayoutNodata;
    private String token;
    private String refreshToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);
        ButterKnife.bind(this);
        initViewAndEvent();
        requestData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        requestData();
    }


    /**
     * 初始化网络数据
     */
    private void requestData() {
        OkGo.<String>get(GlobalAPI.getFamily)
                .params("access_token", token)
                .params("isTrue", false)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(BUG_TAG, "获取家人数据:" + response.getRawCall().request().toString());
                        Type listType = new TypeToken<ResponseEntity<List<MyFamilyBean.DataBean>>>() {
                        }.getType();
                        ResponseEntity<List<MyFamilyBean.DataBean>> responseEntity = JsonUtil.fromJson(response.body(), listType);
                        if (null == responseEntity) {
                            return;
                        }
                        switch (responseEntity.getResponseCode()) {
                            case 1001:

                                if (responseEntity.getData().size() == 0) {
                                    mLlayoutNodata.setVisibility(View.VISIBLE);
                                } else {
                                    mLlayoutNodata.setVisibility(View.GONE);
                                    mMyFamilyList.clear();
                                    mMyFamilyList.addAll(responseEntity.getData());
                                    mMyFamilyAdapter.notifyDataSetChanged();
                                }

                                break;

                            case 1018:
                                RefreshTokenUtil.sendIntDataInvatation(mContext, refreshToken);
                                break;

                            default:
                                showToastShort("获取数据失败");
                                break;
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        initToken();
    }

    /**
     * 初始化视图以及点击事件
     */
    private void initViewAndEvent() {
        initToken();
        mTvPageName.setText(R.string.my_family);
        mBackLeft.setOnClickListener(this);
        mBtnAddFamily.setOnClickListener(this);
        mMyFamilyList = new ArrayList<>();
        mMyFamilyAdapter = new AdapterMyFamily(mMyFamilyList);
        mRvFamily.setLayoutManager(new LinearLayoutManager(this));
        mRvFamily.setAdapter(mMyFamilyAdapter);
        RecyclerViewDivider divider = new RecyclerViewDivider.Builder(this)
                .setOrientation(RecyclerViewDivider.VERTICAL)
                .setStyle(RecyclerViewDivider.Style.BETWEEN)
                .setMarginLeft(72)
                .setMarginRight(8)
                .setDrawableRes(R.drawable.divider)
                .build();
        mRvFamily.addItemDecoration(divider);
        mMyFamilyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("relationship", (Parcelable) adapter.getData().get(position));
                readyGo(FamilyDetailActivity.class, bundle);

            }
        });

    }

    private void initToken() {
        token = ContextSession.getTokenString();
        refreshToken = ContextSession.getRefreshToken();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_left:
                finish();
                break;
            case R.id.btn_add_family:
                readyGo(AddFamilyActivity.class);
//                finish();
                break;
            default:
                break;

        }

    }
}
