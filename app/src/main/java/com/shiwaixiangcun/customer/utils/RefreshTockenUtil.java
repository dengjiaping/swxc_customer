package com.shiwaixiangcun.customer.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.ui.activity.LoginActivity;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/9.
 */

public class RefreshTockenUtil implements Serializable {
    //shuanxin
    public static void sendIntDataInvatation(final Context context,String refresh_token) {


//        if (("one").equals(SharePreference.getStringSpParams(context,Common.ISFRESHTOKEN,Common.SIFRESHTOKEN))){
//            return;
//        }
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("client_id", "650c9dda9b1d4fa89a035a382a4f1c04");
//        hashMap.put("client_secret", "b605f96870a24149a14149391deae9d9");
//        hashMap.put("grant_type", "refresh_token");
//        hashMap.put("refresh_token", refresh_token);
//
//        AppLogger.e("mmmmmmmm", "-------3------" + hashMap.toString() + "--->>>>>" + Common.login);
//        api.downLoadDataFromNetworkAir(Common.login, hashMap, new MoneyApi.DownLoadSuccessAirListener() {
//            @Override
//            public void downLoadSuccessAir(JSONObject reslut) {
//                AppLogger.e("vvvvvvvvvvv",reslut.toString());
//
//                LoginBean loginBean = new Gson().fromJson(reslut.toString(), LoginBean.class);
//                if (loginBean.getResponseCode() == 1003){
//                    SharePreference.saveStringToSpParams(context,Common.ISFRESHTOKEN,Common.SIFRESHTOKEN,"one");
//                    SharePreference.saveStringToSpParams(context, Common.LOGINSHARE, Common.PSLOGINSHARE, reslut.toString());
//                }else if (loginBean.getResponseCode() == 1017){
//                    //退到登录界
//                    SharePreference.saveStringToSpParams(context, Common.ISLOGIN, Common.SILOGIN, "noLogin");
//                    Intent intent = new Intent(context, LoginActivity.class);
//                    intent.putExtra("loginoutNew","loginoutNew");
//                    intent.putExtra("tockenguoqi","offtocken");
//                    context.startActivity(intent);
//                }
//
//
//            }
//        });
//刷新
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("client_id", "97d7c8d7418b49de8da45cac01615f3e");
            hashMap.put("client_secret", "fd225cc01f034b2d8c76f97190750664");
            hashMap.put("grant_type", "refresh_token");
            hashMap.put("refresh_token", refresh_token);

            Log.i("getVerification=========", hashMap.toString()+"---"+ Common.login);
            HttpRequest.post(Common.login, hashMap, new HttpCallBack() {
                @Override
                public void onSuccess(String responseJson) {
                    Log.i("oooooo---onSuccess---", responseJson);
                    Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
                    }.getType();
                    ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(responseJson, type);

                    if (responseEntity.getResponseCode() == 1003) {

                        String access_token = responseEntity.getData().getAccess_token();
                        Log.i("ooooooooooo", access_token);
                        SharePreference.saveStringToSpParams(context, Common.ISORNOLOGIN, Common.SIORNOLOGIN, "yesLogin");
                        SharePreference.saveStringToSpParams(context, Common.ISSAVELOGIN, Common.SISAVELOGIN, responseJson);


//                        sendInformationHttp(context);
                    }else {
                        SharePreference.saveStringToSpParams(context, Common.ISORNOLOGIN, Common.SIORNOLOGIN, "");
//                        Toast.makeText(context,responseEntity.getMessage(),Toast.LENGTH_LONG).show();
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
