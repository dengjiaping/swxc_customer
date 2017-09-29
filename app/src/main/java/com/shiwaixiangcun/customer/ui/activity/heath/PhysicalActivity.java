package com.shiwaixiangcun.customer.ui.activity.heath;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterFamily;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.model.HealthUserBean;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Desc：    体征数据
 */
public class PhysicalActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.fLayout_content)
    FrameLayout mFLayoutContent;
    AdapterFamily mAdapterFamily;
    @BindView(R.id.rv_family)
    RecyclerView mRvFamily;
    FragmentManager fragmentManager;
    FragmentTransaction ft;
    private List<HealthUserBean> mUserBeanList;
    private FragmentHealth mFragmentHealth;
    private String tokenString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical);
        ButterKnife.bind(this);
        iniView();
        initData();
    }

    /**
     * 请求数据
     */
    private void initData() {
        final String loginInfo = SharePreference.getStringSpParams(mContext, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(loginInfo, type);
        tokenString = responseEntity.getData().getAccess_token();
        Log.e(BUG_TAG, tokenString);
        OkGo.<String>get(GlobalConfig.getPhysical)
                .params("access_token", tokenString)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(BUG_TAG, response.getRawCall().request().toString());
                        Log.e(BUG_TAG, response.body());
                        Type type = new TypeToken<ResponseEntity<List<HealthUserBean>>>() {
                        }.getType();
                        ResponseEntity<List<HealthUserBean>> responseEntity = JsonUtil.fromJson(response.body(), type);
                        if (responseEntity == null) {
                            return;
                        }
                        switch (responseEntity.getResponseCode()) {
                            case 1001:
                                boolean visible = false;
                                if (responseEntity.getData().size() == 1) {
                                    mRvFamily.setVisibility(View.GONE);
                                    visible = true;
                                }
                                mUserBeanList.addAll(responseEntity.getData());
                                mAdapterFamily.addData(mUserBeanList);
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("health", mUserBeanList.get(0));
                                bundle.putBoolean("visible", visible);
                                mFragmentHealth.setArguments(bundle);
                                ft = fragmentManager.beginTransaction();
                                ft.add(R.id.fLayout_content, mFragmentHealth).commit();
                                break;
                            default:
                                Toast.makeText(mContext, "获取数据出错", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        Log.e(BUG_TAG, responseEntity.getMessage());
                    }
                });
    }

    private void iniView() {
        mUserBeanList = new ArrayList<>();
        mBackLeft.setOnClickListener(this);
        mTvPageName.setText("体征数据");
        mAdapterFamily = new AdapterFamily();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvFamily.setLayoutManager(layoutManager);
        mRvFamily.setAdapter(mAdapterFamily);
        mFragmentHealth = FragmentHealth.getInstance();
        fragmentManager = getSupportFragmentManager();

        mAdapterFamily.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FragmentHealth fragmentHealth = FragmentHealth.getInstance();
                mAdapterFamily.setSelected(position);
                Bundle bundle = new Bundle();
                bundle.putBoolean("visible", false);
                bundle.putParcelable("health", mUserBeanList.get(position));
                fragmentHealth.setArguments(bundle);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fLayout_content, fragmentHealth);
                fragmentTransaction.commit();
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
