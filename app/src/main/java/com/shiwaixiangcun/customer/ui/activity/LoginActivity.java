package com.shiwaixiangcun.customer.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.loadingDialog.LoadingDialog;
import com.shiwaixiangcun.customer.model.InformationaBean;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.response.ResponseEntity;
import com.shiwaixiangcun.customer.utils.ShareUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.LoginOutUtil;
import com.shiwaixiangcun.customer.utils.RefreshTockenUtil;
import com.shiwaixiangcun.customer.utils.TimeCount;
import com.shiwaixiangcun.customer.utils.TimerToTimerUtil;
import com.shiwaixiangcun.customer.utils.Utils;

import java.lang.reflect.Type;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String BUG_TAG = "LogActivity";
    private TimeCount time;
    private TextView tv_get_verification;
    private Button btn_login;
    private EditText et_get_psw;
    private EditText et_username;
    private ChangeLightImageView back_left;
    private LoadingDialog loadingDialog;
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


        layoutView();
        initData();

    }

    private void layoutView() {
        tv_get_verification = (TextView) findViewById(R.id.tv_get_verification);
        btn_login = (Button) findViewById(R.id.btn_login);
        et_get_psw = (EditText) findViewById(R.id.et_get_psw);
        et_username = (EditText) findViewById(R.id.et_username);
        back_left = (ChangeLightImageView) findViewById(R.id.back_left);


    }

    private void initData() {
        time = new TimeCount(60000, 1000, tv_get_verification);
        tv_get_verification.setOnClickListener(this);
        et_username.setText(ShareUtil.getStringSpParams(this, Common.ISSAVEACCOUNT, Common.SISAVEACCOUNT));
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


//        HttpRequest.get("http://www.baidu.com").executeJson(new HttpCallBack() {
//
//            @Override
//            public void onSuccess(String responseJson) {
//            }
//
//            @Override
//            public void onFailed(Exception e) {
//            }
//        });
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

                if (responseEntity.getResponseCode() == 1003) {
                    loadingDialog = new LoadingDialog(LoginActivity.this, "正在登录...");
                    loadingDialog.show();
                    String access_token = responseEntity.getData().getAccess_token();

                    ShareUtil.saveStringToSpParams(LoginActivity.this, Common.ISSAVEACCOUNT, Common.SISAVEACCOUNT, et_username.getText().toString().trim());
                    ShareUtil.saveStringToSpParams(LoginActivity.this, Common.ISSAVELOGIN, Common.SISAVELOGIN, responseJson);

                    sendInformationHttp(LoginActivity.this);

                } else if (responseEntity.getResponseCode() == 1016) {
                    Toast.makeText(LoginActivity.this, "验证码错误", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailed(Exception e) {
            }
        });
    }


    //个人信息
    private void sendInformationHttp(final Context context) {
        String login_detail = ShareUtil.getStringSpParams(context, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());

        HttpRequest.get(Common.information, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                InformationaBean user = new Gson().fromJson(responseJson, InformationaBean.class);
                if (user.getResponseCode() == 1001) {
//                    List<Cookie> allCookie = OkGo.getInstance().getCookieJar().getCookieStore().getAllCookie();
//                    for (Cookie cookie : allCookie) {
//
//                        String name = cookie.name();
//                        String value = cookie.value();
//                        Log.e("bbbbbbbbbbbm", name + "------" + value);
//                        if (name.equals("uid")) {
//                            Log.e("bbbbbbbbbbbm---------------", value);
//                        ShareUtil.saveStringToSpParams(LoginActivity.this,Common.ISCOOKIE,Common.SICOOKIE,value);
//                        }
//
//                    }
                    ShareUtil.saveStringToSpParams(LoginActivity.this, Common.ISORNOLOGIN, Common.SIORNOLOGIN, "yesLogin");
                    if (Utils.isNotEmpty(user.getData().getAvatar())) {
                        if (Utils.isNotEmpty(user.getData().getAvatar().getAccessUrl())) {
                            ShareUtil.saveStringToSpParams(LoginActivity.this, Common.ISIMAGEHEAD, Common.SIIMAGEHEAD, user.getData().getAvatar().getAccessUrl());
                        } else {
                            ShareUtil.saveStringToSpParams(LoginActivity.this, Common.ISIMAGEHEAD, Common.SIIMAGEHEAD, "");
                        }
                    } else {
                        ShareUtil.saveStringToSpParams(LoginActivity.this, Common.ISIMAGEHEAD, Common.SIIMAGEHEAD, "");
                    }


                    ShareUtil.saveStringToSpParams(LoginActivity.this, Common.ISUSERNAME, Common.SIUSERNAME, user.getData().getName());
                    ShareUtil.saveStringToSpParams(LoginActivity.this, Common.ISUSERSEX, Common.SIUSERSEX, user.getData().getSex());
                    ShareUtil.saveStringToSpParams(LoginActivity.this, Common.ISUSEROLD, Common.SIUSEROLD, TimerToTimerUtil.stampToInspectionDate(user.getData().getBirthday() + ""));
                    ShareUtil.saveStringToSpParams(LoginActivity.this, Common.ISUSERPHONE, Common.SIUSERPHONE, user.getData().getPhone());
                    loadingDialog.close();
                    if (Utils.isNotEmpty(mineLogin)) {
                        if (mineLogin.equals("mineLogin")) {
                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                        }
                    }
                    finish();
                } else if (user.getResponseCode() == 1018) {
                    RefreshTockenUtil.sendIntDataInvatation(context, refresh_token);
                } else if (user.getResponseCode() == 1019) {
                    LoginOutUtil.sendLoginOutUtil(context);
                }
            }

            @Override
            public void onFailed(Exception e) {
                Toast.makeText(context, "登录失败", Toast.LENGTH_LONG).show();
                loadingDialog.close();
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
