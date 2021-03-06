package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.AllMerchBean;
import com.shiwaixiangcun.customer.model.SurroundMerchantTypeBean;
import com.shiwaixiangcun.customer.presenter.IHomeSurroundPresenter;
import com.shiwaixiangcun.customer.ui.IHomeSurroundView;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;

import java.util.HashMap;

/**
 * @author Administrator
 * @date 2017/5/25
 */

public class HomeSurroundImpl implements IHomeSurroundPresenter {
    private IHomeSurroundView iHomeSurroundView;


    public HomeSurroundImpl(IHomeSurroundView iHomeSurroundView) {
        this.iHomeSurroundView = iHomeSurroundView;

    }

    @Override
    public void setBgaAdpaterAndClick(Context context) {
        sendMerchantTypeHttp(context);
    }

    @Override
    public void setAllMerchClick(Context context, String all_id) {
        sendAllMerchHttp(context, all_id);
    }


    /**
     * @param context
     */
    private void sendMerchantTypeHttp(final Context context) {
        int siteID = (int) AppSharePreferenceMgr.get(context, GlobalConfig.CURRENT_SITE_ID, 0);
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("siteId", siteID);
        HttpRequest.get(Common.merchantType, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                SurroundMerchantTypeBean surroundmerchantTypeBean = new Gson().fromJson(responseJson, SurroundMerchantTypeBean.class);
                JSONObject jsonObject = JSON.parseObject(responseJson);
                JSONArray data = jsonObject.getJSONArray("data");
                Boolean all = data.getJSONObject(1).getBoolean("all");
                if (surroundmerchantTypeBean.getResponseCode() == 1001) {
                    iHomeSurroundView.setBgaAdpaterAndClickResult(surroundmerchantTypeBean);
                } else {
                    Toast.makeText(context, surroundmerchantTypeBean.getMessage(), Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailed(Exception e) {
                Toast.makeText(context, "网络异常，请稍后再试...", Toast.LENGTH_LONG).show();

            }
        });
    }

    /**
     * @param context
     * @param all_id
     */
    private void sendAllMerchHttp(final Context context, String all_id) {
        int siteId = (int) AppSharePreferenceMgr.get(context, GlobalConfig.CURRENT_SITE_ID, 0);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("fields", "cover,id,name,feature");
        hashMap.put("typeId", all_id);


        hashMap.put("siteId", siteId);

        HttpRequest.get(Common.merchant, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                AllMerchBean allMerchBean = new Gson().fromJson(responseJson, AllMerchBean.class);
                if (allMerchBean.getResponseCode() == 1001) {
                    iHomeSurroundView.setAllMerchResult(allMerchBean);
                } else {
                    Toast.makeText(context, allMerchBean.getMessage(), Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailed(Exception e) {
                Toast.makeText(context, "网络异常，请稍后再试...", Toast.LENGTH_LONG).show();

            }
        });
    }


}
