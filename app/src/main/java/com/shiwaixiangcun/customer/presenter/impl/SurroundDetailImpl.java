package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.MerchDetailBean;
import com.shiwaixiangcun.customer.presenter.ISurroundDetailPresenter;
import com.shiwaixiangcun.customer.ui.ISurroundDetailView;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/25.
 */

public class SurroundDetailImpl implements ISurroundDetailPresenter {
    private ISurroundDetailView iSurroundDetailView;


    public SurroundDetailImpl(ISurroundDetailView iSurroundDetailView) {
        this.iSurroundDetailView = iSurroundDetailView;

    }

    @Override
    public void setBgaAdpaterAndClick(Context context,String merchId) {
        sendGetRentHttp(context,merchId);
    }




    //商户详情
    private void sendGetRentHttp(final Context context,String merchId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", merchId);
        HttpRequest.get(Common.merchantDetail, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {

                MerchDetailBean merchDetailBean = new Gson().fromJson(responseJson, MerchDetailBean.class);
                if (merchDetailBean.getResponseCode() == 1001){
                    iSurroundDetailView.setBgaAdpaterAndClickResult(merchDetailBean);
                }else {
                    Toast.makeText(context,merchDetailBean.getMessage(),Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailed(Exception e) {
                Log.e("oooooo---onFailed---", e.toString());
                Toast.makeText(context,"网络异常，请稍后再试...",Toast.LENGTH_LONG).show();

            }
        });
    }


}
