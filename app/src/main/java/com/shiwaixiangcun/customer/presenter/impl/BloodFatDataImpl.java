package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.PressureFatBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.presenter.IBloodFatDataPresenter;
import com.shiwaixiangcun.customer.ui.IBloodFatDataView;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.LoginOutUtil;
import com.shiwaixiangcun.customer.utils.RefreshTockenUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/25.
 */

public class BloodFatDataImpl implements IBloodFatDataPresenter {
    private IBloodFatDataView iBloodFatDataView;
    private String str_content;

    public BloodFatDataImpl(IBloodFatDataView iBloodFatDataView, String str_content) {
        this.iBloodFatDataView = iBloodFatDataView;
        this.str_content = str_content;
    }

    @Override
    public void setBgaAdpaterAndClick(Context context) {
        sendBloodFatHttp(context);
    }




    //血脂
    private void sendBloodFatHttp(final Context context) {
        String login_detail = SharePreference.getStringSpParams(context, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());
        hashMap.put("page.pn", 1);
        hashMap.put("page.size", 7);

        Log.i("dddddd", hashMap.toString() + "-----------" + Common.bloodFat);
        HttpRequest.get(Common.bloodFat, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---", responseJson);
//                Type type = new TypeToken<ResponseEntity<PageBean<BloodFatBean>>>() {
//                }.getType();
//                ResponseEntity<PageBean<BloodFatBean>> responseEntity = JsonUtil.fromJson(responseJson, type);
                PressureFatBean pressureFatBean = new Gson().fromJson(responseJson, PressureFatBean.class);
                if (pressureFatBean.getResponseCode() == 1001) {
                    iBloodFatDataView.setBgaAdpaterAndClickResult(pressureFatBean);
                } else if (pressureFatBean.getResponseCode() == 1018) {
                    RefreshTockenUtil.sendIntDataInvatation(context, refresh_token);
                } else if (pressureFatBean.getResponseCode() == 1019) {
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
