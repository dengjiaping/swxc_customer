package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.AnnouncementBean;
import com.shiwaixiangcun.customer.model.InformationBean;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.WeatherDataBean;
import com.shiwaixiangcun.customer.presenter.IHomePresenter;
import com.shiwaixiangcun.customer.response.PageBean;
import com.shiwaixiangcun.customer.response.ResponseEntity;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.LoginOutUtil;
import com.shiwaixiangcun.customer.utils.RefreshTockenUtil;
import com.shiwaixiangcun.customer.utils.ShareUtil;
import com.shiwaixiangcun.customer.ui.IHomeView;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by fyj on 2017/05/22.
 */
public class HomePresenterImpl implements IHomePresenter {

    private IHomeView iHomeView;


    public HomePresenterImpl(IHomeView iHomeView) {
        this.iHomeView = iHomeView;

    }


    @Override
    public void setBgaAdpaterAndClick(Context context) {
        sendListpageHttp();


    }

    @Override
    public void setAnnouncement(Context context) {
        sendAnnouncementListpageHttp();

    }

    @Override
    public void setHeadline(Context context) {
        sendHeadlinesListpageHttp();
    }

    @Override
    public void setInformation(Context context) {
        sendInformationHttp(context);
    }

    @Override
    public void setWeatherHomeClick(Context context,String cityCode) {
        sendWeatherHttp(context,cityCode);
    }


    //banner列表 vewpager public
    private void sendListpageHttp() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("position", "OWNER_APP");
        hashMap.put("fields", "imagePath,link");

        HttpRequest.get(Common.listpage, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---banner", responseJson);
                iHomeView.setBgaAdpaterAndClickResult(responseJson);
//                Type type = new TypeToken<ResponseEntity<List<Banner>>>() {
//                }.getType();
//                responseEntity = JsonUtil.fromJson(responseJson, type);
//                String imagePath = responseEntity.getData().get(0).getImagePath();
//                Log.i("oooooo---onSuccess---ssss----", responseJson);


            }

            @Override
            public void onFailed(Exception e) {
                Log.i("oooooo---onFailed---", e.toString());
            }
        });
    }



    // 公告 public
    private void sendAnnouncementListpageHttp() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("page.pn", "1");
        hashMap.put("page.size", "3");
        hashMap.put("position", "COMMUNITY_ANNOUNCEMENT");

        HttpRequest.get(Common.articleListpage, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---111", responseJson);
                // 分页列表
                Type type = new TypeToken<ResponseEntity<PageBean<AnnouncementBean>>>() {
                }.getType();
                ResponseEntity<PageBean<AnnouncementBean>> responseEntity_ann = JsonUtil.fromJson(responseJson, type);
                iHomeView.setAnnouncementResult(responseEntity_ann);

            }

            @Override
            public void onFailed(Exception e) {
                Log.i("oooooo---onFailed---", e.toString());
            }
        });
    }

    // 头条 public
    private void sendHeadlinesListpageHttp() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("page.pn", 1);
        hashMap.put("page.size", 1000);
        hashMap.put("position", "COMMUNITY_HEADLINES");

        HttpRequest.get(Common.articleListpage, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess--123321", responseJson);
                // 分页列表
                Type type = new TypeToken<ResponseEntity<PageBean<AnnouncementBean>>>() {
                }.getType();
                ResponseEntity<PageBean<AnnouncementBean>> responseEntity_ann = JsonUtil.fromJson(responseJson, type);
                iHomeView.setHeadlineResult(responseEntity_ann);

            }

            @Override
            public void onFailed(Exception e) {
                Log.i("oooooo---onFailed---", e.toString());
            }
        });
    }


    //个人信息
    private void sendInformationHttp(final Context context) {
        String login_detail = ShareUtil.getStringSpParams(context, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Log.i("eeeeeettt", "0-------------0");
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());

        Log.i("dddddd", hashMap.toString() + "-----------" + Common.information);
        HttpRequest.get(Common.information, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---", responseJson);
                InformationBean user = new Gson().fromJson(responseJson, InformationBean.class);
                if (user.getResponseCode() == 1001) {
                    iHomeView.setInformationResult(user);
                } else if (user.getResponseCode() == 1018) {
                    RefreshTockenUtil.sendIntDataInvatation(context, refresh_token);
                } else if (user.getResponseCode() == 1019) {
                    LoginOutUtil.sendLoginOutUtil(context);
                }
            }

            @Override
            public void onFailed(Exception e) {
                Log.i("oooooo---onFailed---", e.toString());
            }
        });
    }


    //天气数据
    private void sendWeatherHttp(final Context context,String cityCode) {
        HashMap<String, Object> hashMap = new HashMap<>();

        Log.e("dddddd", hashMap.toString() + "-----------" + "http://tqapi.mobile.360.cn/v4/"+cityCode+".json");
        HttpRequest.get("http://tqapi.mobile.360.cn/v4/"+cityCode+".json", hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.e("oooooo---onSuccess---yyyy", responseJson);
                 JSON.parse(responseJson);
                WeatherDataBean weatherDataBean = new Gson().fromJson(responseJson, WeatherDataBean.class);
                iHomeView.setHomeWeatherClick(weatherDataBean);


            }

            @Override
            public void onFailed(Exception e) {
                Log.e("oooooo---onFailed---", e.toString());
                Toast.makeText(context,"网络异常，请稍后再试...",Toast.LENGTH_LONG).show();

            }
        });
    }
}
