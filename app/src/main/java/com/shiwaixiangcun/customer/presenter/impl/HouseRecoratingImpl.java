package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.PageBean;
import com.shiwaixiangcun.customer.model.RecoratingListBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.presenter.IRecoratingPresenter;
import com.shiwaixiangcun.customer.ui.IHouseRecoratingView;
import com.shiwaixiangcun.customer.utils.JsonUtil;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/25.
 */

public class HouseRecoratingImpl implements IRecoratingPresenter {
    private IHouseRecoratingView iHouseRecoratingView;

    public HouseRecoratingImpl(IHouseRecoratingView iHouseRecoratingView) {
        this.iHouseRecoratingView = iHouseRecoratingView;

    }

    @Override
    public void setBgaAdpaterAndClick(Context context) {

        sendGetBuyHttp(context);
    }


    //装修列表
    private void sendGetBuyHttp(final Context context) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("page.pn", 1);
        hashMap.put("page.size", 1000);

        Log.i("dddddd", hashMap.toString() + "-----------" + Common.decorateList);
        HttpRequest.get(Common.decorateList, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---", responseJson);
                Type type = new TypeToken<ResponseEntity<PageBean<RecoratingListBean>>>() {
                }.getType();
                ResponseEntity<PageBean<RecoratingListBean>> responseEntity = JsonUtil.fromJson(responseJson, type);
                if (responseEntity.getResponseCode() == 1001) {
                    iHouseRecoratingView.setBgaAdpaterAndClickResult(responseEntity);
                }
            }

            @Override
            public void onFailed(Exception e) {
                Log.i("oooooo---onFailed---", e.toString());
            }
        });
    }


}
