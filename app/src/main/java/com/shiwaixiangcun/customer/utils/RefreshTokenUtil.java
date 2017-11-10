package com.shiwaixiangcun.customer.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.ui.activity.LoginActivity;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 *
 * @author Administrator
 * @date 2017/5/9
 */

public class RefreshTokenUtil implements Serializable {

    public static String BUG_TAG = "RefreshTokenUtil";

    public static void sendIntDataInvatation(final Context context, String refresh_token) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("client_id", GlobalConfig.clientId);
        hashMap.put("client_secret", GlobalConfig.clientSecret);
        hashMap.put("grant_type", "refresh_token");
        hashMap.put("refresh_token", refresh_token);

        Log.e(BUG_TAG, hashMap.toString() + "---" + Common.login);
        HttpRequest.post(Common.login, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.e(BUG_TAG, responseJson);
                Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
                }.getType();
                ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(responseJson, type);

                if (responseEntity.getResponseCode() == 1003) {

                    String access_token = responseEntity.getData().getAccess_token();
                    Log.e(BUG_TAG, access_token);
                    SharePreference.saveStringToSpParams(context, Common.ISORNOLOGIN, Common.SIORNOLOGIN, "yesLogin");
                    SharePreference.saveStringToSpParams(context, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN, responseJson);


//                        sendInformationHttp(context);
                } else {
                    Toast.makeText(context, "token失效,重新登录", Toast.LENGTH_SHORT).show();
                    SharePreference.saveStringToSpParams(context, Common.ISORNOLOGIN, Common.SIORNOLOGIN, "");
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);


                }

            }

            @Override
            public void onFailed(Exception e) {
                Log.e(BUG_TAG, e.toString());
            }
        });


    }
}
