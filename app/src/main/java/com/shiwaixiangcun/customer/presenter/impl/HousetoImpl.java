package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.InformationBean;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.presenter.IOnhousetoPresenter;
import com.shiwaixiangcun.customer.ui.IHouseToView;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.LoginOutUtil;
import com.shiwaixiangcun.customer.utils.RefreshTockenUtil;
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
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        final ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());

        Log.i("dddddd", hashMap.toString() + "-----------" + Common.information);
        HttpRequest.get(Common.information, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---", responseJson);
                InformationBean user = new Gson().fromJson(responseJson, InformationBean.class);

                if (user.getResponseCode() == 1001) {
                    iHouseToView.setBgaAdpaterAndClickResult(user);
                } else if (user.getResponseCode() == 1018) {
                    RefreshTockenUtil.sendIntDataInvatation(context, refresh_token);
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
