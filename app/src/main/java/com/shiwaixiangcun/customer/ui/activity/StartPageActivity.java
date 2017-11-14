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
import com.shiwaixiangcun.customer.model.Site;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.JsonUtil;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Administrator
 */
public class StartPageActivity extends BaseActivity {

    CountDownTimer cdt;
    private TextView tvCenterWord;
    private String token;
    private String refreshToken;

    private int siteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);
        layoutView();

        requestData();
//        initData();
    }

    /**
     * 请求站点数据
     */
    private void requestData() {
        siteId = (int) AppSharePreferenceMgr.get(mContext, GlobalConfig.CURRENT_SITE_ID, -1);
        if (siteId == -1) {
            OkGo.<String>get(GlobalAPI.getSite)
                    .params("fields", "id,name,defaultShow")
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            initData();
                        }

                        @Override
                        public void onSuccess(Response<String> response) {
                            Type type = new TypeToken<ResponseEntity<List<Site>>>() {
                            }.getType();
                            ResponseEntity<List<Site>> responseEntity = JsonUtil.fromJson(response.body(), type);
                            if (responseEntity == null) {
                                return;
                            }
                            switch (responseEntity.getResponseCode()) {

                                case 1001:
                                    if (responseEntity.getData().size() > 0) {
                                        for (Site site : responseEntity.getData()) {
                                            if (site.isDefaultShow()) {
                                                AppSharePreferenceMgr.put(mContext, GlobalConfig.DEFAULT_SITE_ID, site.getId());
                                                AppSharePreferenceMgr.put(mContext, GlobalConfig.CURRENT_SITE_ID, site.getId());
                                                AppSharePreferenceMgr.put(mContext, GlobalConfig.DEFAULT_SITE_NAME, site.getName());
                                                AppSharePreferenceMgr.put(mContext, GlobalConfig.CURRENT_SITE_NAME, site.getName());


                                                break;
                                            }
                                        }
                                    }
                                    initData();
                                    break;
                                default:
                                    initData();
                                    break;
                            }
                        }
                    });
        } else {
            initData();
        }

    }

    private void layoutView() {
        tvCenterWord = findViewById(R.id.tv_center_word);
        AlphaAnimation alpha = new AlphaAnimation(0.0f, 1.0f);
        alpha.setDuration(500);
        alpha.setFillAfter(true);
        tvCenterWord.setAnimation(alpha);
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
                .params("grant_type", "refreshToken")
                .params("refreshToken", refresh_token)
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

        refreshToken(mContext, refreshToken);


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
