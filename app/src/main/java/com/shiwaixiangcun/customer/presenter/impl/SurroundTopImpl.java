package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.MerchantListBean;
import com.shiwaixiangcun.customer.presenter.ISurroundTopPresenter;
import com.shiwaixiangcun.customer.ui.ISurroundTopView;

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
    public void setBgaAdpaterAndClick(Context context,String merchantId) {
        sendGetRentHttp(context,merchantId);
    }




    //商户列表
    private void sendGetRentHttp(final Context context,String merchantId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("fields", "cover,id,name,feature");
        hashMap.put("typeId", merchantId);
        HttpRequest.get(Common.merchant, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                MerchantListBean merchantListBean = new Gson().fromJson(responseJson, MerchantListBean.class);
                if (merchantListBean.getResponseCode() == 1001){
                    iSurroundTopView.setBgaAdpaterAndClickResult(merchantListBean);
                }else {
                    Toast.makeText(context,merchantListBean.getMessage(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailed(Exception e) {
                Toast.makeText(context,"网络异常，请稍后再试...",Toast.LENGTH_LONG).show();

            }
        });
    }


}
package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.MerchantListBean;
import com.shiwaixiangcun.customer.presenter.ISurroundTopPresenter;
import com.shiwaixiangcun.customer.ui.ISurroundTopView;

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
    public void setBgaAdpaterAndClick(Context context,String merchantId) {
        sendGetRentHttp(context,merchantId);
    }




    //商户列表
    private void sendGetRentHttp(final Context context,String merchantId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("fields", "cover,id,name,feature");
        hashMap.put("typeId", merchantId);

        Log.e("dddddd", hashMap.toString() + "-----------" + Common.getRent);
        HttpRequest.get(Common.merchant, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.e("oooooo---onSuccess---merchant", responseJson);
                MerchantListBean merchantListBean = new Gson().fromJson(responseJson, MerchantListBean.class);
                if (merchantListBean.getResponseCode() == 1001){
                    iSurroundTopView.setBgaAdpaterAndClickResult(merchantListBean);
                }else {
                    Toast.makeText(context,merchantListBean.getMessage(),Toast.LENGTH_LONG).show();
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
