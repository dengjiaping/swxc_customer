package com.shiwaixiangcun.customer.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.JsonUtil;

import java.lang.reflect.Type;

public class StartPageActivity extends BaseActivity {

    CountDownTimer cdt;
    private TextView tv_center_word;
    private String token;
    private String refreshToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);
        layoutView();
        initData();
    }

    private void layoutView() {
        tv_center_word = (TextView) findViewById(R.id.tv_center_word);
        AlphaAnimation alpha = new AlphaAnimation(0.0f, 1.0f);
        alpha.setDuration(500);
        alpha.setFillAfter(true);
        tv_center_word.setAnimation(alpha);
        refreshToken = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.Refresh_token, "");
//        isFirstUse = (boolean) AppSharePreferenceMgr.get(mContext, GlobalConfig.FIRST_USE, true);

    }

    /**
     * 刷新Token
     *
     * @param refresh_token 需要刷新的值
     */
    public void refreshToken(final Context context, String refresh_token) {
        OkGo.<String>post(GlobalAPI.refreshToken)
                .params("client_id", GlobalConfig.clientId)
                .params("client_secret", GlobalConfig.clientSecret)
                .params("grant_type", "refresh_token")
                .params("refresh_token", refresh_token)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(BUG_TAG, "onSuccess");
                        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
                        }.getType();
                        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(response.body(), type);
                        switch (responseEntity.getResponseCode()) {
                            case 1003:
                                Log.e(BUG_TAG, "刷新成功");
                                LoginResultBean data = responseEntity.getData();
                                AppSharePreferenceMgr.put(context, GlobalConfig.TOKEN, data.getAccess_token());
                                AppSharePreferenceMgr.put(context, GlobalConfig.Refresh_token, data.getRefresh_token());
                                Log.e(BUG_TAG, "刷新以后的token:" + data.getAccess_token());
                                Log.e(BUG_TAG, "刷新以后的Refresh_token:" + data.getRefresh_token());
                                cdt.start();
                                break;
                            default:
                                Log.e(BUG_TAG, "刷新失败");
                                Intent intent = new Intent();
//                                intent.putExtra("mineLogin", "Login");
//                                intent.setClass(mContext, LoginActivity.class);
//                                startActivity(intent);
                                readyGoThenKill(MainActivity.class);
                                finish();
                                break;
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        readyGoThenKill(MainActivity.class);
                        finish();
                    }
                });
    }

    private void initData() {
        cdt = new CountDownTimer(1500, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                readyGoThenKill(MainActivity.class);
                finish();

            }
        };
//
//        if (StringUtil.isEmpty(refreshToken)) {
//            readyGoThenKill(MainActivity.class);
//            finish();
//        }
        refreshToken(mContext, refreshToken);


    }

    //
    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        StatService.onPause(this);
    }
}
package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.os.CountDownTimer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.R;

public class StartPageActivity extends AppCompatActivity {

    private TextView tv_center_word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        layoutView();
        initData();
    }

    private void layoutView() {
        tv_center_word = (TextView) findViewById(R.id.tv_center_word);

        AlphaAnimation alpha = new AlphaAnimation(0.0f, 1.0f);
        alpha.setDuration(500);
        alpha.setFillAfter(true);
        tv_center_word.setAnimation(alpha);

    }

    private void initData() {
        CountDownTimer cdt = new CountDownTimer(1500, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
//                tv_hello.setText(millisUntilFinished + "");
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(StartPageActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        };
        cdt.start();
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
    }
}
