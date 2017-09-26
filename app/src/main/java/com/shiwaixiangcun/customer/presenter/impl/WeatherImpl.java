package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.WeatherDataBean;
import com.shiwaixiangcun.customer.presenter.IWeatherPresenter;
import com.shiwaixiangcun.customer.ui.IWeatherView;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/25.
 */

public class WeatherImpl implements IWeatherPresenter {
    private IWeatherView iWeatherView;


    public WeatherImpl(IWeatherView iWeatherView) {
        this.iWeatherView = iWeatherView;

    }

    @Override
    public void setBgaAdpaterAndClick(Context context,String cityCode) {
        sendWeatherHttp(context,cityCode);
    }




    //天气数据
    private void sendWeatherHttp(final Context context,String cityCode) {
        HashMap<String, Object> hashMap = new HashMap<>();

        Log.e("dddddd", hashMap.toString() + "-----------" + "http://tqapi.mobile.360.cn/v4/"+cityCode+".json");
        HttpRequest.get("http://tqapi.mobile.360.cn/v4/"+cityCode+".json", hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.e("oooooo---onSuccess---", responseJson);
                WeatherDataBean weatherDataBean = new Gson().fromJson(responseJson, WeatherDataBean.class);
                iWeatherView.setBgaAdpaterAndClickResult(weatherDataBean);



//                JSONObject result = JSON.parseObject(responseJson);
//                result
//
//
//                WeatherDataBean weatherDataBean = new WeatherDataBean(result);
//
//                iWeatherView.setBgaAdapterAndClickResult(weatherDataBean);

            }

            @Override
            public void onFailed(Exception e) {
                Log.e("oooooo---onFailed---", e.toString());
                Toast.makeText(context,"网络异常，请稍后再试...",Toast.LENGTH_LONG).show();

            }
        });
    }


}
