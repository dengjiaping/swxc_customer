package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.FeedbackBean;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.presenter.IFeedBackPresenter;
import com.shiwaixiangcun.customer.ui.IFeedBackView;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.LoginOutUtil;
import com.shiwaixiangcun.customer.utils.RefreshTockenUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.utils.Utils;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/25.
 */

public class FeedBackImpl implements IFeedBackPresenter {
    private IFeedBackView iFeedBackView;
    private String str_content;

    public FeedBackImpl(IFeedBackView iFeedBackView, String str_content) {
        this.iFeedBackView = iFeedBackView;
        this.str_content = str_content;
    }

    @Override
    public void setBgaAdpaterAndClick(Context context) {
        if (!Utils.isNotEmpty(str_content)) {
            Toast.makeText(context, "反馈内容不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        sendGetRentHttp(context);
    }


    //意见反馈
    private void sendGetRentHttp(final Context context) {
        String login_detail = SharePreference.getStringSpParams(context, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN);
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();

        String tokenString = (String) AppSharePreferenceMgr.get(context, GlobalConfig.TOKEN, "");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", tokenString);
        hashMap.put("content", str_content);
        hashMap.put("dataSource", "PROPERTY_CUSTOMER_APP");

        Log.i("dddddd", hashMap.toString() + "-----------" + Common.feedBack);
        HttpRequest.post(Common.feedBack, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---", responseJson);
                Type type = new TypeToken<ResponseEntity<FeedbackBean>>() {
                }.getType();
                ResponseEntity<FeedbackBean> responseEntity = JsonUtil.fromJson(responseJson, ResponseEntity.class);

                if (responseEntity.getResponseCode() == 1001) {
                    iFeedBackView.setBgaAdpaterAndClickResult(responseEntity);
                } else if (responseEntity.getResponseCode() == 1018) {
                    RefreshTockenUtil.sendIntDataInvatation(context, refresh_token);
                } else if (responseEntity.getResponseCode() == 1019) {
                    LoginOutUtil.sendLoginOutUtil(context);
                }else {
                    Toast.makeText(context,"反馈失败",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailed(Exception e) {
                Toast.makeText(context,"网络异常，请稍后再试",Toast.LENGTH_LONG).show();
                Log.i("oooooo---onFailed---", e.toString());
            }
        });
    }


}
