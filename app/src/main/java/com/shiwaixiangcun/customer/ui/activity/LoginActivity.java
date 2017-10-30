package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.model.UserInfoBean;
import com.shiwaixiangcun.customer.ui.dialog.DialogLoading;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.LoginOutUtil;
import com.shiwaixiangcun.customer.utils.RefreshTokenUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.utils.TimeCount;
import com.shiwaixiangcun.customer.utils.TimerToTimerUtil;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.lang.reflect.Type;
import java.util.HashMap;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private TimeCount time;
    private TextView tv_get_verification;
    private Button btn_login;
    private EditText et_get_psw;
    private EditText et_username;
    private ChangeLightImageView back_left;
    private DialogLoading mDialogLoading;
    private String mineLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        mineLogin = intent.getStringExtra("mineLogin");

        initView(savedInstanceState);
        initData();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("account", et_username.getText().toString());
    }

    private void initView(Bundle savedInstanceState) {
        tv_get_verification = (TextView) findViewById(R.id.tv_get_verification);
        btn_login = (Button) findViewById(R.id.btn_login);
        et_get_psw = (EditText) findViewById(R.id.et_get_psw);
        et_username = (EditText) findViewById(R.id.et_username);
        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        if (savedInstanceState != null) {
            et_username.setText(savedInstanceState.getString("account"));
        }


    }

    private void initData() {
        time = new TimeCount(60000, 1000, tv_get_verification);
        tv_get_verification.setOnClickListener(this);
        et_username.setText((CharSequence) AppSharePreferenceMgr.get(this, Common.IS_SAVE_ACCOUNT, ""));
        btn_login.setOnClickListener(this);
        back_left.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_get_verification:
                time.start();
                sendGetVerificationHttp();
                break;
            case R.id.btn_login:
                if (!Utils.isNotEmpty(et_username.getText().toString().trim())) {
                    showToastShort("请输入用户账号");
                    return;
                } else if (!Utils.isNotEmpty(et_get_psw.getText().toString().trim())) {
                    showToastShort("请输入验证码");
                    return;
                }
                sendLoginHttp();
                break;
            case R.id.back_left:
                finish();
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void sendGetVerificationHttp() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("mobile", et_username.getText().toString().trim());
        HttpRequest.get(Common.getVerification, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                ResponseEntity responseEntity = JSON.parseObject(responseJson, ResponseEntity.class);
            }

            @Override
            public void onFailed(Exception e) {
            }
        });
    }

    /**
     * 登录
     */
    private void sendLoginHttp() {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("client_id", GlobalConfig.clientId);
        hashMap.put("client_secret", GlobalConfig.clientSecret);
        hashMap.put("grant_type", "dynamic_password");
        hashMap.put("scope", "property_customer_app");
        hashMap.put("username", et_username.getText().toString().trim());
        hashMap.put("password", et_get_psw.getText().toString().trim());
        HttpRequest.post(Common.login, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
                }.getType();
                ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(responseJson, type);
                if (responseEntity == null) {
                    Toast.makeText(mContext, "服务器出错", Toast.LENGTH_SHORT).show();
                    return;
                }
                switch (responseEntity.getResponseCode()) {
                    case 1003:
                        mDialogLoading = new DialogLoading(LoginActivity.this, "正在登录...");
                        mDialogLoading.show();
                        String access_token = responseEntity.getData().getAccess_token();
                        String refresh_token = responseEntity.getData().getRefresh_token();
                        AppSharePreferenceMgr.put(mContext, GlobalConfig.FIRST_USE, false);
                        //保存用户的Token值
                        AppSharePreferenceMgr.put(mContext, GlobalConfig.TOKEN, access_token);
                        Log.e(BUG_TAG, "登录的Token：" + access_token);
                        AppSharePreferenceMgr.put(mContext, GlobalConfig.Refresh_token, refresh_token);
                        //保存用户登录的账号
                        AppSharePreferenceMgr.put(mContext, Common.IS_SAVE_ACCOUNT, et_username.getText().toString().trim());
                        //保存用户的登录信息
                        SharePreference.saveStringToSpParams(mContext, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN, responseJson);
                        requestInformation(access_token, refresh_token);
                        break;
                    case 1016:
                        Toast.makeText(LoginActivity.this, "验证码错误", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(LoginActivity.this, "登录出错", Toast.LENGTH_LONG).show();
                        break;
                }

            }

            @Override
            public void onFailed(Exception e) {
            }
        });
    }


    /**
     * 获取当前登录用户的用户信息
     *
     * @param token         登录时获取到的Token
     * @param refresh_token 登录时获取的Refresh_token
     */
    private void requestInformation(final String token, final String refresh_token) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", token);
        OkGo.<String>get(Common.information)
                .params("access_token", token)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(BUG_TAG, String.valueOf(response.getRawCall().request().url()));
                        UserInfoBean user = JsonUtil.fromJson(response.body(), UserInfoBean.class);
                        if (user == null) {
                            return;
                        }
                        switch (user.getResponseCode()) {
                            case 1001:
                                //将用户信息写入SharePreference
                                UserInfoBean.DataBean userInfo = user.getData();
                                String info = JsonUtil.toJson(userInfo);
                                AppSharePreferenceMgr.put(mContext, GlobalConfig.userInfo, info);
                                AppSharePreferenceMgr.put(mContext, GlobalConfig.isLogin, "islogin");
                                AppSharePreferenceMgr.put(mContext, GlobalConfig.propertyAuth, userInfo.isPropertyAuth());
                                SharePreference.saveStringToSpParams(LoginActivity.this, Common.ISORNOLOGIN, Common.SIORNOLOGIN, "isLogin");
                                if (Utils.isNotEmpty(user.getData().getAvatar())) {
                                    if (Utils.isNotEmpty(user.getData().getAvatar().getAccessUrl())) {
                                        SharePreference.saveStringToSpParams(mContext, Common.ISIMAGEHEAD, Common.SIIMAGEHEAD, user.getData().getAvatar().getAccessUrl());
                                    } else {
                                        SharePreference.saveStringToSpParams(mContext, Common.ISIMAGEHEAD, Common.SIIMAGEHEAD, "");
                                    }
                                } else {
                                    SharePreference.saveStringToSpParams(mContext, Common.ISIMAGEHEAD, Common.SIIMAGEHEAD, "");
                                }
                                SharePreference.saveStringToSpParams(mContext, Common.ISUSERNAME, Common.SIUSERNAME, user.getData().getName());
                                SharePreference.saveStringToSpParams(mContext, Common.ISUSERSEX, Common.SIUSERSEX, user.getData().getSex());
                                SharePreference.saveStringToSpParams(mContext, Common.ISUSEROLD, Common.SIUSEROLD, TimerToTimerUtil.stampToInspectionDate(user.getData().getBirthday() + ""));
                                SharePreference.saveStringToSpParams(mContext, Common.ISUSERPHONE, Common.SIUSERPHONE, user.getData().getPhone());
                                mDialogLoading.close();
                                if (Utils.isNotEmpty(mineLogin)) {
                                    if (mineLogin.equals("mineLogin")) {
                                        Intent intent = new Intent();
                                        setResult(RESULT_OK, intent);
                                    } else {
                                        readyGoThenKill(MainActivity.class);
                                    }
                                }
                                finish();
                                break;
                            case 1018:
                                RefreshTokenUtil.sendIntDataInvatation(mContext, refresh_token);
                                break;
                            case 1019:
                                LoginOutUtil.sendLoginOutUtil(mContext);
                                break;
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Toast.makeText(mContext, "获取信息失败", Toast.LENGTH_LONG).show();
                        mDialogLoading.close();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
