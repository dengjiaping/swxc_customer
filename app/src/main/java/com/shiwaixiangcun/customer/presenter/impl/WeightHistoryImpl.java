package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.WeightBean;
import com.shiwaixiangcun.customer.presenter.IWeightHistoryPresenter;
import com.shiwaixiangcun.customer.response.PageBean;
import com.shiwaixiangcun.customer.response.ResponseEntity;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.LoginOutUtil;
import com.shiwaixiangcun.customer.utils.RefreshTockenUtil;
import com.shiwaixiangcun.customer.utils.ShareUtil;
import com.shiwaixiangcun.customer.ui.IWeightHistoryView;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/25.
 */

public class WeightHistoryImpl implements IWeightHistoryPresenter {
    private IWeightHistoryView iWeightHistoryView;
    private String str_content;

    public WeightHistoryImpl(IWeightHistoryView iWeightHistoryView, String str_content) {
        this.iWeightHistoryView = iWeightHistoryView;
        this.str_content = str_content;
    }

    @Override
    public void setBgaAdpaterAndClick(Context context) {

        sendWeightHistiryHttp(context);
    }




    //体重列表
    private void sendWeightHistiryHttp(final Context context) {
        String login_detail = ShareUtil.getStringSpParams(context, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());
        hashMap.put("page.pn", 1);
        hashMap.put("page.size", 1000);

        Log.i("dddddd", hashMap.toString() + "-----------" + Common.bmiList);
        HttpRequest.get(Common.bmiList, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---", responseJson);
                Type type = new TypeToken<ResponseEntity<PageBean<WeightBean>>>() {
                }.getType();
                ResponseEntity<PageBean<WeightBean>> responseEntity = JsonUtil.fromJson(responseJson, type);
                if (responseEntity.getResponseCode() == 1001) {
                    iWeightHistoryView.setBgaAdpaterAndClickResult(responseEntity);
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
