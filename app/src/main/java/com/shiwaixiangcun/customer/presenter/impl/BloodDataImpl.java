package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.BloodPressureDataBean;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.presenter.IBloodDataPresenter;
import com.shiwaixiangcun.customer.response.PageBean;
import com.shiwaixiangcun.customer.response.ResponseEntity;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.LoginOutUtil;
import com.shiwaixiangcun.customer.utils.RefreshTockenUtil;
import com.shiwaixiangcun.customer.utils.ShareUtil;
import com.shiwaixiangcun.customer.ui.IBloodDataView;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/25.
 */

public class BloodDataImpl implements IBloodDataPresenter {
    private IBloodDataView iBloodDataView;
    private String str_content;

    public BloodDataImpl(IBloodDataView iBloodDataView, String str_content) {
        this.iBloodDataView = iBloodDataView;
        this.str_content = str_content;
    }

    @Override
    public void setBgaAdpaterAndClick(Context context) {

        sendBloodDataHttp(context);
    }


    //血压历史数据
    private void sendBloodDataHttp(final Context context) {
        String login_detail = ShareUtil.getStringSpParams(context, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());
//        hashMap.put("fields", "createTime,healthStatus,id,heartRate,relaxationBlood,shrinkBlood");

        Log.i("dddddd", hashMap.toString() + "-----------" + Common.pressureRecord);
        HttpRequest.get(Common.pressureRecord, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---", responseJson);
                Type type = new TypeToken<ResponseEntity<PageBean<BloodPressureDataBean>>>() {
                }.getType();
                ResponseEntity<PageBean<BloodPressureDataBean>> responseEntity = JsonUtil.fromJson(responseJson, type);
                if (responseEntity.getResponseCode() == 1001) {
                    iBloodDataView.setBgaAdpaterAndClickResult(responseEntity);
                } else if (responseEntity.getResponseCode() == 1018) {
                    RefreshTockenUtil.sendIntDataInvatation(context, refresh_token);
                } else if (responseEntity.getResponseCode() == 1019) {
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
