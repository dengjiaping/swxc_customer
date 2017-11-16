package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.MerchantListBean;
import com.shiwaixiangcun.customer.presenter.ISurroundTopPresenter;
import com.shiwaixiangcun.customer.ui.ISurroundTopView;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/25.
 */

public class SurroundTopImpl implements ISurroundTopPresenter {
    private ISurroundTopView iSurroundTopView;


    public SurroundTopImpl(ISurroundTopView iSurroundTopView) {
        this.iSurroundTopView = iSurroundTopView;

    }

    @Override
    public void setBgaAdpaterAndClick(Context context, String merchantId) {
        sendGetRentHttp(context, merchantId);
    }


    /**
     * @param context
     * @param merchantId
     */
    private void sendGetRentHttp(final Context context, String merchantId) {
        int siteId = (int) AppSharePreferenceMgr.get(context, GlobalConfig.CURRENT_SITE_ID, 0);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("fields", "cover,id,name,feature");
        hashMap.put("typeId", merchantId);

        hashMap.put("siteId", siteId);
        HttpRequest.get(Common.merchant, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                MerchantListBean merchantListBean = new Gson().fromJson(responseJson, MerchantListBean.class);
                if (merchantListBean.getResponseCode() == 1001) {
                    iSurroundTopView.setBgaAdpaterAndClickResult(merchantListBean);
                } else {
                    Toast.makeText(context, merchantListBean.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailed(Exception e) {
                Toast.makeText(context, "网络异常，请稍后再试...", Toast.LENGTH_LONG).show();

            }
        });
    }


}
