package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.model.UpdateAppBean;
import com.shiwaixiangcun.customer.presenter.IMyMinePresenter;
import com.shiwaixiangcun.customer.ui.IMyMineView;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.LoginOutUtil;
import com.shiwaixiangcun.customer.utils.RefreshTockenUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.utils.VersionUpdateUtil;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/25.
 */

public class MyMineImpl implements IMyMinePresenter {
    private IMyMineView iMyMineView;
    private String str_content;

    public MyMineImpl(IMyMineView iMyMineView, String str_content) {
        this.iMyMineView = iMyMineView;
        this.str_content = str_content;
    }

    @Override
    public void setBgaAdpaterAndClick(Context context) {

        sendSdkUpdateHttp(context);
    }




    //app版本更新
    private void sendSdkUpdateHttp(final Context context) {
        String login_detail = SharePreference.getStringSpParams(context, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());
        hashMap.put("appType", "OWNER_APP");
        hashMap.put("version", VersionUpdateUtil.getVerName(context.getApplicationContext()));

        Log.i("dddddd", hashMap.toString() + "-----------" + Common.appUpdate);
        HttpRequest.get(Common.appUpdate, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---", responseJson);
                UpdateAppBean updateAppBean = new Gson().fromJson(responseJson, UpdateAppBean.class);
                if (updateAppBean.getResponseCode() == 1001) {
                    iMyMineView.setBgaAdpaterAndClickResult(updateAppBean);

                } else if (updateAppBean.getResponseCode() == 1018) {
                    RefreshTockenUtil.sendIntDataInvatation(context, refresh_token);
                } else if (updateAppBean.getResponseCode() == 1019) {
                    LoginOutUtil.sendLoginOutUtil(context);
                }
            }

            @Override
            public void onFailed(Exception e) {
                Log.i("oooooo---onFailed---", e.toString());
                Toast.makeText(context,"网络异常，请稍后再试...",Toast.LENGTH_LONG).show();
            }
        });
    }


}
