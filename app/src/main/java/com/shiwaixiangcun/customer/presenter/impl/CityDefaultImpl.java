package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.DefaultCityBean;
import com.shiwaixiangcun.customer.presenter.ICityDefaultPresenter;
import com.shiwaixiangcun.customer.ui.ICityDefaultView;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/25.
 */

public class CityDefaultImpl implements ICityDefaultPresenter {
    private ICityDefaultView iCityDefaultView;


    public CityDefaultImpl(ICityDefaultView iCityDefaultView) {
        this.iCityDefaultView = iCityDefaultView;

    }

    @Override
    public void setBgaAdpaterAndClick(Context context) {
        sendCityDefaultHttp(context);
    }

    @Override
    public void setSearchCityClic(Context context,String cityName) {
        Log.e("hhhhhhkka",cityName);
        sendSearchCityHttp(context,cityName);
    }


    //默认城市列表
    private void sendCityDefaultHttp(final Context context) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name","");
        Log.e("dddddd", hashMap.toString() + "-----------" + Common.findCityList);
        HttpRequest.get(Common.findCityList, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.e("oooooo---onSuccess---findCityListss", responseJson);
                DefaultCityBean defaultCityBean = new Gson().fromJson(responseJson, DefaultCityBean.class);
                if (defaultCityBean.getResponseCode() == 1001){
                    iCityDefaultView.setBgaAdpaterAndClickResult(defaultCityBean);
                }

            }

            @Override
            public void onFailed(Exception e) {
                Log.e("oooooo---onFailed---", e.toString());
                Toast.makeText(context,"网络异常，请稍后再试...",Toast.LENGTH_LONG).show();

            }
        });
    }


    //搜索城市
    private void sendSearchCityHttp(final Context context,String cityName) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name",cityName);
        Log.e("dddddd", hashMap.toString() + "-----------" + Common.findCityList);
        HttpRequest.get(Common.findCityList, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.e("oooooo---onSuccess---ssdda", responseJson);
                DefaultCityBean defaultCityBean = new Gson().fromJson(responseJson, DefaultCityBean.class);
                if (defaultCityBean.getResponseCode() == 1001){
                    iCityDefaultView.setSearchCityResult(defaultCityBean);
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
