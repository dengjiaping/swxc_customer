package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.HealthAllActivity;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.presenter.IHealthOkPresenter;
import com.shiwaixiangcun.customer.response.ResponseEntity;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.LoginOutUtil;
import com.shiwaixiangcun.customer.utils.RefreshTockenUtil;
import com.shiwaixiangcun.customer.utils.ShareUtil;
import com.shiwaixiangcun.customer.ui.IHealthOkView;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/25.
 */

public class HealthOkImpl implements IHealthOkPresenter {
    private IHealthOkView iHealthOkView;
    private String str_content;

    public HealthOkImpl(IHealthOkView iHealthOkView, String str_content) {
        this.iHealthOkView = iHealthOkView;
        this.str_content = str_content;
    }

    @Override
    public void setBgaAdpaterAndClick(Context context) {
        sendhealthOkHttp(context);
    }


    //健康状况
    private void sendhealthOkHttp(final Context context) {
        String login_detail = ShareUtil.getStringSpParams(context, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        final ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());
//        hashMap.put("fields", "bloodCreateTime,bloodStatus,bmi,bmiCreateTime,bmiStatus,heartRate,height,lowLipo, pressureCreateTime,pressureStatus,relaxationBlood,shrinkBlood, sugarCreateTime,sugarStatus,testStatus,topLipo,totalCholesterol,triglyceride,weight");

        Log.i("dddddd", hashMap.toString() + "-----------" + Common.customerDetail);
        HttpRequest.get(Common.customerDetail, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---healthok", responseJson);
                HealthAllActivity healthAllActivity = new Gson().fromJson(responseJson, HealthAllActivity.class);

                if (healthAllActivity.getResponseCode() == 1001) {
                    iHealthOkView.setBgaAdpaterAndClickResult(healthAllActivity);
                } else if (healthAllActivity.getResponseCode() == 1018) {
                    RefreshTockenUtil.sendIntDataInvatation(context, refresh_token);
                } else if (healthAllActivity.getResponseCode() == 1019) {
                    LoginOutUtil.sendLoginOutUtil(context);
                }
            }

            @Override
            public void onFailed(Exception e) {
                Log.i("oooooo---onFailed---", e.toString());
            }
        });
    }


}
