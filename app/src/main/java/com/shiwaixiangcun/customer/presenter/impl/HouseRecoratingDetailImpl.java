package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;

import com.google.gson.Gson;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.RecoratingDetailBean;
import com.shiwaixiangcun.customer.presenter.IRecoratingDetailPresenter;
import com.shiwaixiangcun.customer.ui.IHouseRecoratingDetailView;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/25.
 */

public class HouseRecoratingDetailImpl implements IRecoratingDetailPresenter {
    private IHouseRecoratingDetailView iHouseRecoratingDetailView;
    private String str_id;

    public HouseRecoratingDetailImpl(IHouseRecoratingDetailView iHouseRecoratingDetailView, String str_id) {
        this.iHouseRecoratingDetailView = iHouseRecoratingDetailView;
        this.str_id = str_id;
    }

    @Override
    public void RequestData(Context context) {

        decorateDetailInfo(context);
    }


    //装修详情
    private void decorateDetailInfo(final Context context) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", str_id);

        HttpRequest.get(Common.companyDetail, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                RecoratingDetailBean recoratingDetailBean = new Gson().fromJson(responseJson, RecoratingDetailBean.class);
                if (recoratingDetailBean.getResponseCode() == 1001) {
                    iHouseRecoratingDetailView.setInfo(recoratingDetailBean);
                }
            }

            @Override
            public void onFailed(Exception e) {
            }
        });
    }


}
