package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.BloodSugarBean;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.presenter.IBloodSugarPresenter;
import com.shiwaixiangcun.customer.ui.IBloodSugarView;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/25.
 */

public class BloodSugarImpl implements IBloodSugarPresenter {
    private IBloodSugarView iBloodSugarView;
    private String str_content;

    public BloodSugarImpl(IBloodSugarView iBloodSugarView, String str_content) {
        this.iBloodSugarView = iBloodSugarView;
        this.str_content = str_content;
    }

    @Override
    public void setBgaAdpaterAndClick(Context context) {

        sendBloodSugarHttp(context);
    }


    //血糖页面和列表
    private void sendBloodSugarHttp(final Context context) {
        String login_detail = SharePreference.getStringSpParams(context, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        final ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());
        hashMap.put("page.pn", 1);
        hashMap.put("page.size", 7);

        Log.i("dddddd", hashMap.toString() + "-----------" + Common.sugarList);
        HttpRequest.get(Common.sugarList, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---", responseJson);
                BloodSugarBean bloodSugarBean = new Gson().fromJson(responseJson, BloodSugarBean.class);


//                if (bloodSugarBean.getResponseCode() == 1001) {
//                    iBloodSugarView.setBgaAdpaterAndClickResult(bloodSugarBean);
//                } else if (bloodSugarBean.getResponseCode() == 1018) {
//                    RefreshTockenUtil.sendIntDataInvatation(context, refresh_token);
//                } else if (bloodSugarBean.getResponseCode() == 1019) {
//                    LoginOutUtil.sendLoginOutUtil(context);
//                }
            }

            @Override
            public void onFailed(Exception e) {
                Log.i("oooooo---onFailed---", e.toString());
            }
        });
    }


}
