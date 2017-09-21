package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.HousePhoneBean;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.presenter.IHousePhonePresenter;
import com.shiwaixiangcun.customer.ui.IHousePhoneView;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.utils.Utils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public class HousePhoneImpl implements IHousePhonePresenter {
    private IHousePhoneView iHousePhoneView;
    private String str_content;
    private String phone;

    public HousePhoneImpl(IHousePhoneView iHousePhoneView, String str_content, String phone) {
        this.iHousePhoneView = iHousePhoneView;
        this.str_content = str_content;
        this.phone = phone;
    }

    @Override
    public void setBgaAdpaterAndClick(Context context) {
        if (!Utils.isNotEmpty(str_content)) {
            Toast.makeText(context, "租房信息不全", Toast.LENGTH_LONG).show();
            return;
        }
        sendHousePhoneHttp(context);
    }

    @Override
    public void setBindPhoneClick(Context context) {
        sendBindPhoneHttp(context);
    }


    //房屋号码
    private void sendHousePhoneHttp(Context context) {
        String login_detail = SharePreference.getStringSpParams(context, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());
        hashMap.put("houseId", str_content);
        hashMap.put("fields", "phone");

        Log.i("dddddd", hashMap.toString() + "-----------" + Common.housePhone);
        HttpRequest.get(Common.housePhone, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---", responseJson);
                Type type = new TypeToken<ResponseEntity<List<HousePhoneBean>>>() {
                }.getType();
                ResponseEntity<List<HousePhoneBean>> responseEntity = JsonUtil.fromJson(responseJson, type);
                iHousePhoneView.setBgaAdpaterAndClickResult(responseEntity);


            }

            @Override
            public void onFailed(Exception e) {
                Log.i("oooooo---onFailed---", e.toString());
            }
        });
    }


    //绑定房屋
    private void sendBindPhoneHttp(Context context) {
        String login_detail = SharePreference.getStringSpParams(context, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());
        hashMap.put("houseId", str_content);
        hashMap.put("phone", phone);

        Log.i("dddddd", hashMap.toString() + "-----------" + Common.bindPhone);
        HttpRequest.post(Common.bindPhone, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---", responseJson);
                Type type = new TypeToken<ResponseEntity<ResponseEntity>>() {
                }.getType();
                ResponseEntity<ResponseEntity> responseEntity = JsonUtil.fromJson(responseJson, ResponseEntity.class);
                iHousePhoneView.setPhoneResult(responseEntity);


            }

            @Override
            public void onFailed(Exception e) {
                Log.i("oooooo---onFailed---", e.toString());
            }
        });
    }


}
