package com.shiwaixiangcun.customer.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.response.ResponseEntity;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.ShareUtil;
import com.shiwaixiangcun.customer.utils.StringUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.lang.reflect.Type;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 添加地址Activity
 */
public class AddAddressActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.edt_name)
    EditText mEdtName;
    @BindView(R.id.edt_phone)
    EditText mEdtPhone;
    @BindView(R.id.edt_address)
    EditText mEdtAddress;
    @BindView(R.id.lLayout_body)
    LinearLayout mLLayoutBody;
    @BindView(R.id.tv_top_right)
    TextView mTvTopRight;
    @BindView(R.id.ll_image_right)
    LinearLayout mLlImageRight;
    @BindView(R.id.cb_default)
    CheckBox mCheckbox;
    String userName;
    String userPhone;
    String userAddress;


    private boolean isDefault = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initEvent() {
        mTvTopRight.setOnClickListener(this);
        mBackLeft.setOnClickListener(this);
    }

    private void initView() {
        mTvPageName.setText("添加收获地址");
        mTvTopRight.setText("保存");
        mTvTopRight.setVisibility(View.VISIBLE);
        mLlImageRight.setVisibility(View.GONE);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_top_right:
                sendDataToServer();
                break;
            case R.id.back_left:
                finish();
                break;
        }
    }

    private void sendDataToServer() {
        userName = mEdtName.getText().toString().trim();
        userPhone = mEdtPhone.getText().toString().trim();
        userAddress = mEdtAddress.getText().toString().trim();
        isDefault = mCheckbox.isChecked();
        if (checkValidity()) {

            if (isDefault) {
//                AddressBean addressBean = new AddressBean(userAddress, userName, userPhone);
//                String addressJson = JsonUtil.toJson(addressBean);
//                ShareUtil.saveStringToSpParams(this, Common.ADDRESS, Common.DEFAULT_ADDRESS, addressJson);

            }
            //关闭软键盘
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
            }

            //获取token值
            final String login_detail = ShareUtil.getStringSpParams(this, Common.ISSAVELOGIN, Common.SISAVELOGIN);
            Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
            }.getType();
            final ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
            final String refresh_token = responseEntity.getData().getRefresh_token();
            HashMap<String, Object> params = new HashMap<>();
            params.put("access_token", responseEntity.getData().getAccess_token());
            params.put("name", userName);
            params.put("phone", userPhone);
            params.put("address", userAddress);
            params.put("default", isDefault);
            Log.e(BUG_TAG, responseEntity.getData().getAccess_token());
            HttpRequest.post(GlobalConfig.addAddress, params, new HttpCallBack() {
                @Override
                public void onSuccess(String responseJson) {
                    super.onSuccess(responseJson);
                    ResponseEntity response = JsonUtil.fromJson(responseJson, ResponseEntity.class);
                    if (response == null) {
                        return;
                    }
                    switch (response.getResponseCode()) {
                        case 1001:
                            Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putString("userName", userName);
                            bundle.putString("userPhone", userPhone);
                            bundle.putString("userAddress", userAddress);

                            intent.putExtras(bundle);
                            setResult(0X12, intent);
                            finish();
                            break;
                        case 1002:
                            Toast.makeText(mContext, "地址已经存在", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(mContext, "保存失败", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

                @Override
                public void onFailed(Exception e) {


                }
            });

        }

    }

    /**
     * 检查用户姓名，电话号码是否为空
     *
     * @return
     */
    private boolean checkValidity() {
        if (StringUtil.isEmpty(userName)) {
            Toast.makeText(this, "请输入收货人姓名", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!checkNumber(userPhone)) {
            return false;
        }
        if (StringUtil.isEmpty(userAddress)) {
            Toast.makeText(this, "请输入收货人地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 检查号码是否合法
     *
     * @param number
     * @return
     */

    private boolean checkNumber(String number) {
        String telRegex = "[1][34578]\\d{9}";
        if (StringUtil.isEmpty(number)) {
            Toast.makeText(this, "请输入收货人电话", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!number.matches(telRegex)) {
            Toast.makeText(this, "请确认手机号码是否正确", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
