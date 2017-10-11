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
import com.shiwaixiangcun.customer.presenter.IGetBuyHousePresenter;
import com.shiwaixiangcun.customer.ui.IHouseGetBuyView;
import com.shiwaixiangcun.customer.ui.dialog.DialogLoading;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.LoginOutUtil;
import com.shiwaixiangcun.customer.utils.RefreshTokenUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/25.
 */

public class HouseGetBuyImpl implements IGetBuyHousePresenter {
    private IHouseGetBuyView iHouseGetBuyView;
    private String str_content;
    private DialogLoading mDialogLoading;

    public HouseGetBuyImpl(IHouseGetBuyView iHouseGetBuyView, String str_content) {
        this.iHouseGetBuyView = iHouseGetBuyView;
        this.str_content = str_content;
    }

    @Override
    public void setBgaAdpaterAndClick(Context context) {
        mDialogLoading = new DialogLoading(context, "正在提交");
        mDialogLoading.show();
        sendGetBuyHttp(context);
    }




    //买房
    private void sendGetBuyHttp(final Context context) {
        String login_detail = SharePreference.getStringSpParams(context, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN);
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());
        hashMap.put("content", str_content);

        Log.i("dddddd", hashMap.toString() + "-----------" + Common.getBuy);
        HttpRequest.post(Common.getBuy, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---", responseJson);
                Type type = new TypeToken<ResponseEntity>() {
                }.getType();
                ResponseEntity responseEntity = JsonUtil.fromJson(responseJson, ResponseEntity.class);
                if (responseEntity.getResponseCode() == 1001) {
                    iHouseGetBuyView.setBgaAdpaterAndClickResult(responseEntity);
                    mDialogLoading.close();
                } else if (responseEntity.getResponseCode() == 1018) {
                    RefreshTokenUtil.sendIntDataInvatation(context, refresh_token);
                } else if (responseEntity.getResponseCode() == 1019) {
                    LoginOutUtil.sendLoginOutUtil(context);
                }else if (responseEntity.getResponseCode() == 1002){
                    Toast.makeText(context,responseEntity.getMessage(),Toast.LENGTH_LONG).show();
                    mDialogLoading.close();
                }
            }

            @Override
            public void onFailed(Exception e) {
                Log.i("oooooo---onFailed---", e.toString());

                Toast.makeText(context,"网络异常，请稍后再试...",Toast.LENGTH_LONG).show();
                mDialogLoading.close();
            }
        });
    }


}
