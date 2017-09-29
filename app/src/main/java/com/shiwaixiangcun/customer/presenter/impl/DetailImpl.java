package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.presenter.IDetailPresenter;
import com.shiwaixiangcun.customer.ui.IDetailView;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/25.
 */

public class DetailImpl implements IDetailPresenter {
    private IDetailView iDetailView;
    private String str_content;

    public DetailImpl(IDetailView iDetailView, String str_content) {
        this.iDetailView = iDetailView;
        this.str_content = str_content;
    }

    @Override
    public void setBgaAdpaterAndClick(Context context) {
        sendDetailHttp(context);
    }




    //首页点击详情加载web
    private void sendDetailHttp(Context context) {
        String login_detail = SharePreference.getStringSpParams(context, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN);
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());
        hashMap.put("articleId", str_content);

        Log.i("dddddd", hashMap.toString() + "-----------" + Common.articleDetailView);
        HttpRequest.get(Common.articleDetailView, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---", responseJson);
//                Type type = new TypeToken<ResponseEntity>() {
//                }.getType();
//                ResponseEntity responseEntity = JsonUtil.fromJson(responseJson, ResponseEntity.class);
//                iDetailView.setBgaAdapterAndClickResult(responseEntity);


            }

            @Override
            public void onFailed(Exception e) {
                Log.i("oooooo---onFailed---", e.toString());
            }
        });
    }


}
