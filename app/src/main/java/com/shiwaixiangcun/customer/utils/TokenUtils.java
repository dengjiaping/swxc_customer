package com.shiwaixiangcun.customer.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.event.EventCenter;
import com.shiwaixiangcun.customer.event.SimpleEvent;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;

import java.lang.reflect.Type;

/**
 * Author   xujing
 * Desc:    用于获取Token 、更新Token、检查Token是否有效
 */

public class TokenUtils {
    private static final String BUG_TAG = "TokenUtils";
    private static final String client_id = "97d7c8d7418b49de8da45cac01615f3e";
    private static final String client_secret = "fd225cc01f034b2d8c76f97190750664";
    private Context mContext;


    public TokenUtils(Activity activity) {
        this.mContext = activity;

    }


    /**
     * 检查Token是否有效
     *
     * @param string
     */
    public static void checkToken(String string) {
        Log.e(BUG_TAG, string);
        OkGo.<String>get(GlobalConfig.checkToken)
                .params("access_token", string)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(BUG_TAG, "Success");
                        ResponseEntity responseEntity = JsonUtil.fromJson(response.body(), ResponseEntity.class);
                        if (responseEntity == null) {
                            return;
                        }
                        if (responseEntity.getResponseCode() == 1001) {
                        }
                        switch (responseEntity.getResponseCode()) {
                            case 1001:
                                Log.e(BUG_TAG, "Token有效");
                                EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.CHECK_TOKEN, 1));
                                break;
                            case 1018:
                                Log.e(BUG_TAG, "Token失效");
                                EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.CHECK_TOKEN, 2));
                                break;
                        }
                        Log.e(BUG_TAG, response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e(BUG_TAG, "onError");
                    }
                });


    }


    /**
     * 刷新Token
     *
     * @param refresh_token 需要刷新的值
     */
    public static void refreshToken(final Context context, String refresh_token) {
        OkGo.<String>post(GlobalConfig.refreshToken)
                .params("client_id", client_id)
                .params("client_secret", client_secret)
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
                                EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.REFRESH_TOKEN, 1));
                                LoginResultBean data = responseEntity.getData();
                                AppSharePreferenceMgr.put(context, Common.TOKEN, data.getAccess_token());
                                AppSharePreferenceMgr.put(context, Common.REFRESH_TOKEN, data.getRefresh_token());
                                break;
                            default:
                                Log.e(BUG_TAG, "刷新失败");
                                EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.REFRESH_TOKEN, 2));
                                break;
                        }
                    }
                });
    }
}
