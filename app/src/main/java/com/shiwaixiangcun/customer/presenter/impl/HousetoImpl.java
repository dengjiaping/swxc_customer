package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.InformationBean;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.presenter.IOnhousetoPresenter;
import com.shiwaixiangcun.customer.ui.IHouseToView;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.LoginOutUtil;
import com.shiwaixiangcun.customer.utils.RefreshTokenUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/25.
 */

public class HousetoImpl implements IOnhousetoPresenter {
    private IHouseToView iHouseToView;


    public HousetoImpl(IHouseToView iHouseToView) {
        this.iHouseToView = iHouseToView;

    }

    @Override
    public void setBgaAdpaterAndClick(Context context) {
        sendInformationHttp(context);
    }


    //个人信息
    private void sendInformationHttp(final Context context) {
        String login_detail = SharePreference.getStringSpParams(context, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();

        String strToken = (String) AppSharePreferenceMgr.get(context, GlobalConfig.TOKEN, "");
        final String refreshToken = (String) AppSharePreferenceMgr.get(context, GlobalConfig.Refresh_token, "");
        int siteId = (int) AppSharePreferenceMgr.get(context, GlobalConfig.CURRENT_SITE_ID, 0);
        final ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);

        HashMap<String, Object> hashMap = new HashMap<>(2);
        hashMap.put("access_token", strToken);
        hashMap.put("siteId", siteId);
        HttpRequest.get(Common.information, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                InformationBean user = new Gson().fromJson(responseJson, InformationBean.class);

                if (user.getResponseCode() == 1001) {
                    iHouseToView.setBgaAdpaterAndClickResult(user);
                } else if (user.getResponseCode() == 1018) {
                    RefreshTokenUtil.sendIntDataInvatation(context, refreshToken);
                } else if (user.getResponseCode() == 1019) {
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
