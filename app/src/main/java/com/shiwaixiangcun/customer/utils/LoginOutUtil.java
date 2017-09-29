package com.shiwaixiangcun.customer.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.LogoutBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.ui.activity.LoginActivity;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.HashMap;


/**
 * Created by Administrator on 2017/5/15.
 */

public class LoginOutUtil implements Serializable {

    public static void sendLoginOutUtil(final Context context) {

        Toast.makeText(context, "您的账号在其他设备登录", Toast.LENGTH_LONG).show();
        //登出
        String login_detail = SharePreference.getStringSpParams(context, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN);
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());
        Log.i("dddddd", hashMap.toString() + "-----------" + Common.logout);
        HttpRequest.get(Common.logout, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---", responseJson);
                LogoutBean user = new Gson().fromJson(responseJson, LogoutBean.class);
                if (user.getResponseCode() == 1001){
                    SharePreference.saveStringToSpParams(context, Common.ISORNOLOGIN, Common.SIORNOLOGIN, "");
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }


            }

            @Override
            public void onFailed(Exception e) {
                Log.i("oooooo---onFailed---", e.toString());
            }
        });

    }

}
