package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.presenter.IEditNamePresenter;
import com.shiwaixiangcun.customer.ui.IEditNameView;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.LoginOutUtil;
import com.shiwaixiangcun.customer.utils.RefreshTokenUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.utils.Utils;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/25.
 */

public class EditNameImpl implements IEditNamePresenter {
    private IEditNameView iEditNameView;
    private String str_content;

    public EditNameImpl(IEditNameView iEditNameView, String str_content) {
        this.iEditNameView = iEditNameView;
        this.str_content = str_content;
    }

    @Override
    public void setBgaAdpaterAndClick(Context context) {
        if (!Utils.isNotEmpty(str_content)){
            Toast.makeText(context,"姓名不能为空",Toast.LENGTH_LONG).show();
            return;
        }
        sendGetRentHttp(context);
    }




    //编辑姓名
    private void sendGetRentHttp(final Context context) {
        String login_detail = SharePreference.getStringSpParams(context, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN);
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());
        hashMap.put("name", str_content);

        Log.i("dddddd", hashMap.toString() + "-----------" + Common.modify);
        HttpRequest.put(Common.modify, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---", responseJson);
                Type type = new TypeToken<ResponseEntity>() {
                }.getType();
                ResponseEntity responseEntity = JsonUtil.fromJson(responseJson, ResponseEntity.class);

                if (responseEntity.getResponseCode() == 1001) {
                    iEditNameView.setBgaAdpaterAndClickResult(responseEntity);
                } else if (responseEntity.getResponseCode() == 1018) {
                    RefreshTokenUtil.sendIntDataInvatation(context, refresh_token);
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
