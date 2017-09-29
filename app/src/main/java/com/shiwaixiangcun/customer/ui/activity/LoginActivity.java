package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.InformationaBean;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.ui.dialog.DialogLoading;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.LoginOutUtil;
import com.shiwaixiangcun.customer.utils.RefreshTockenUtil;
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
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);


        initView();
        initData();

    }

    private void initView() {
        tv_get_verification = (TextView) findViewById(R.id.tv_get_verification);
        btn_login = (Button) findViewById(R.id.btn_login);
        et_get_psw = (EditText) findViewById(R.id.et_get_psw);
        et_username = (EditText) findViewById(R.id.et_username);
        back_left = (ChangeLightImageView) findViewById(R.id.back_left);


    }

    private void initData() {
        time = new TimeCount(60000, 1000, tv_get_verification);
        tv_get_verification.setOnClickListener(this);

        et_username.setText((CharSequence) AppSharePreferenceMgr.get(this, Common.IS_SAVE_ACCOUNT, ""));
//        et_username.setText(SharePreference.getStringSpParams(this, Common.IS_SAVE_ACCOUNT, Common.SI_SAVE_ACCOUNT));
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
                    return;
                } else if (!Utils.isNotEmpty(et_get_psw.getText().toString().trim())) {
                    return;
                }
                sendLoginHttp();
                break;
            case R.id.back_left:
                finish();
                break;
        }
    }

    //获取验证码
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

    //登录
    private void sendLoginHttp() {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("client_id", "97d7c8d7418b49de8da45cac01615f3e");
        hashMap.put("client_secret", "fd225cc01f034b2d8c76f97190750664");
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

                        //保存用户的Token 值
                        AppSharePreferenceMgr.put(mContext, Common.TOKEN, access_token);
                        AppSharePreferenceMgr.put(mContext, Common.REFRESH_TOKEN, refresh_token);
                        //保存用户登录的账号
                        AppSharePreferenceMgr.put(mContext, Common.IS_SAVE_ACCOUNT, et_username.getText().toString().trim());
                        //保存用户的登录信息
                        AppSharePreferenceMgr.put(LoginActivity.this, Common.IS_SAVE_LOGIN, responseJson);
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

        HttpRequest.get(Common.information, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                InformationaBean user = JsonUtil.fromJson(responseJson, InformationaBean.class);
                if (user == null) {
                    return;
                }
                switch (user.getResponseCode()) {
                    case 1001:
                        AppSharePreferenceMgr.put(mContext, Common.USER_IS_LOGIN, "islogin");
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
                            }
                        }
                        finish();
                        break;
                    case 1018:
                        RefreshTockenUtil.sendIntDataInvatation(mContext, refresh_token);
                        break;
                    case 1019:
                        LoginOutUtil.sendLoginOutUtil(mContext);
                        break;
                }
            }

            @Override
            public void onFailed(Exception e) {
                Toast.makeText(mContext, "获取信息失败", Toast.LENGTH_LONG).show();
                mDialogLoading.close();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
        finish();
    }

}
