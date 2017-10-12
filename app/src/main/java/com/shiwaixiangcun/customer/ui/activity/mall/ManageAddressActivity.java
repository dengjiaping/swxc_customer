package com.shiwaixiangcun.customer.ui.activity.mall;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterManageAddress;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.http.StringDialogCallBack;
import com.shiwaixiangcun.customer.interfaces.onCheckboxClickListener;
import com.shiwaixiangcun.customer.model.AddressBean;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.ui.dialog.DialogInfo;
import com.shiwaixiangcun.customer.ui.dialog.DialogLoading;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.DisplayUtil;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.RefreshTokenUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;
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
    @BindView(R.id.btn_add)
    Button mBtnAdd;
    @BindView(R.id.root_view)
    LinearLayout mRootView;
    private List<AddressBean> mAddressBeanList = new ArrayList<>();
    private AdapterManageAddress mManageAdapter;
    private String token = "";
    private DialogLoading mDialogLoading;

    private boolean clickable = false;
    private String refresh_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_address);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        clickable = bundle.getBoolean("clickable");
        Log.e(BUG_TAG, "是否可以点击" + clickable);
        initToken();
        initView();
        initEvent();
    }

    /**
     * 获取Token
     */
    private void initToken() {
        String login_info = SharePreference.getStringSpParams(this, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_info, type);

        token = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.TOKEN, "");
        refresh_token = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.Refresh_token, "");
        Log.e(BUG_TAG, responseEntity.getData().getAccess_token());
    }


    /**
     * 初始化Data数据
     */
    private void initData() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("access_token", token);
        params.put("fields", "");
        HttpRequest.get(GlobalAPI.getAddress, params, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                super.onSuccess(responseJson);
                Log.e(BUG_TAG, "onSuccess");
                if (responseJson == null) {
                    return;
                }
                Log.e(BUG_TAG, responseJson);
                Type listType = new TypeToken<ResponseEntity<List<AddressBean>>>() {
                }.getType();
                Gson gson = new Gson();
                ResponseEntity<List<AddressBean>> response = gson.fromJson(responseJson, listType);
                if (response == null) {
                    return;
                }
                switch (response.getResponseCode()) {
                    case 1001:
                        mAddressBeanList.clear();
                        mAddressBeanList.addAll(response.getData());
                        mManageAdapter.notifyDataSetChanged();
                        break;

                    case 1018:
                        RefreshTokenUtil.sendIntDataInvatation(mContext, refresh_token);
                        break;
                    default:
                        Toast.makeText(mContext, "验证错误", Toast.LENGTH_SHORT).show();
                        break;

                }

            }

            @Override
            public void onFailed(Exception e) {

            }
        });

    }

    private void initEvent() {
        mBackLeft.setOnClickListener(this);
        mBtnAdd.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }


    private void initView() {
        mTvPageName.setText("我的地址");
        mManageAdapter = new AdapterManageAddress(mAddressBeanList);
        mRvAddress.setLayoutManager(new LinearLayoutManager(this));
        mRvAddress.setAdapter(mManageAdapter);
        mRvAddress.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 0, DisplayUtil.dip2px(mContext, 10));
            }
        });
        if (clickable) {
            mManageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    AddressBean addressInfo = mAddressBeanList.get(position);
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putInt("addressID", addressInfo.getId());
                    bundle.putString("userName", addressInfo.getDeliveryName());
                    bundle.putString("userPhone", addressInfo.getDeliveryPhone());
                    bundle.putString("userAddress", addressInfo.getDeliveryAddress());
                    intent.putExtras(bundle);
                    setResult(0x13, intent);
                    finish();
                }
            });
        }
        mManageAdapter.setCheckboxClickListener(new onCheckboxClickListener() {
            @Override
            public void checkboxClick(int position, View view, boolean modify) {
                if (modify) {
                    modifyDefaultAddress(mAddressBeanList.get(position));
                }
            }


        });
        mManageAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                switch (view.getId()) {
                    case R.id.tv_delete:
                        DialogInfo dialogInfo = new DialogInfo(mContext);
                        dialogInfo.setDialogTitle("删除地址");
                        dialogInfo.setDialogInfo("你的地址信息很重要！确定要删除么？");
                        dialogInfo.setListener(new DialogInfo.onCallBackListener() {
                            @Override
                            public void closeBtn(DialogInfo dialog) {
                                dialog.dismiss();
                            }

                            @Override
                            public void confirmBtn(DialogInfo dialog) {
                                dialog.dismiss();
                                deleteAddress(mAddressBeanList.get(position));

                            }
                        });
                        dialogInfo.show();

                        break;
                    case R.id.tv_edit:

                        editAddress(mAddressBeanList.get(position));

                        break;

                }
            }
        });
    }

    /**
     * 编辑地址
     *
     * @param addressBean
     */
    private void editAddress(AddressBean addressBean) {

        Bundle bundle = new Bundle();
        bundle.putParcelable("address", addressBean);
        readyGo(EditAddressActivity.class, bundle);

    }

    /**
     * 修改默认地址
     *
     * @param addressBean
     */
    private void modifyDefaultAddress(AddressBean addressBean) {
        Log.e(BUG_TAG, "修改地址");
        HashMap<String, Object> params = new HashMap<>();
        params.put("access_token", token);
        params.put("address", addressBean.getDeliveryAddress());
        params.put("name", addressBean.getDeliveryName());
        params.put("phone", addressBean.getDeliveryPhone());
        params.put("id", addressBean.getId());
        params.put("default", true);
        HttpRequest.put(GlobalAPI.modifyDefaultAddress, params, new HttpCallBack() {
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                if (s == null) {
                    return;
                }
                ResponseEntity result = JsonUtil.fromJson(s, ResponseEntity.class);
                if (result == null) {
                    return;
                }
                mDialogLoading = new DialogLoading(mContext, "正在修改...");
                mDialogLoading.show();
                switch (result.getResponseCode()) {
                    case 1001:
                        mDialogLoading.close();
                        initData();
                        break;
                    default:

                        mDialogLoading.close();
                        break;
                }
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }

    /**
     * 删除地址
     *
     * @param addressBean
     */
    private void deleteAddress(AddressBean addressBean) {
        HashMap<String, String> stringHashMap = new HashMap<>();
        stringHashMap.put("access_token", token);
        stringHashMap.put("id", addressBean.getId() + "");
        Log.e(BUG_TAG, "delete  " + token);
        Log.e(BUG_TAG, "delete  " + addressBean.getId());
        OkGo.<String>delete(GlobalAPI.deleteAddress)
                .params(stringHashMap, false)
                .isSpliceUrl(true)
                .execute(new StringDialogCallBack(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (response == null) {
                            return;
                        }
                        String s = response.body();
                        Log.e(BUG_TAG, s);
                        ResponseEntity result = JsonUtil.fromJson(s, ResponseEntity.class);
                        if (result == null) {
                            return;
                        }
                        Log.e(BUG_TAG, result.getResponseCode() + "");
                        switch (result.getResponseCode()) {
                            case 1001:
                                Snackbar.make(mRootView, "删除成功", Snackbar.LENGTH_SHORT).show();
                                initData();
                                break;
                            default:
                                Snackbar.make(mRootView, "删除失败", Snackbar.LENGTH_SHORT).show();
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
            case R.id.btn_add:
                readyGo(AddAddressActivity.class);
                break;
        }
    }
}
