package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.loadingDialog.LoadingDialog;
import com.shiwaixiangcun.customer.model.HouseSelectListBean;
import com.shiwaixiangcun.customer.model.InformationBean;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.presenter.IOnhousetoRentPresenter;
import com.shiwaixiangcun.customer.response.ResponseEntity;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.LoginOutUtil;
import com.shiwaixiangcun.customer.utils.RefreshTockenUtil;
import com.shiwaixiangcun.customer.utils.ShareUtil;
import com.shiwaixiangcun.customer.ui.IHouseToRentView;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public class HousetoRentImpl implements IOnhousetoRentPresenter {
    private IHouseToRentView iHouseToRentView;
    private String str_houseId;
    private String str_content;
    private LoadingDialog loadingDialog;

    public HousetoRentImpl(IHouseToRentView iHouseToRentView,String str_houseId,String str_content) {
        this.iHouseToRentView = iHouseToRentView;
        this.str_houseId = str_houseId;
        this.str_content = str_content;
    }

    @Override
    public void setBgaAdpaterAndClick(Context context) {
        loadingDialog = new LoadingDialog(context, "正在提交");
        loadingDialog.show();
        sendToRentHttp(context);
    }

    @Override
    public void setHouseList(Context context) {
        sendHouseListHttp(context);
    }

    @Override
    public void setInformation(Context context) {
        sendInformationHttp(context);
    }


    //出租房
    private void sendToRentHttp(final Context context) {
        String login_detail = ShareUtil.getStringSpParams(context, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token =  responseEntity.getData().getRefresh_token();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());
        hashMap.put("content", str_content);
        hashMap.put("houseId", str_houseId);

        Log.i("dddddd", hashMap.toString() + "-----------" + Common.toRent);
        HttpRequest.post(Common.toRent, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---", responseJson);
                Type type = new TypeToken<ResponseEntity>() {
                }.getType();
                ResponseEntity responseEntity = JsonUtil.fromJson(responseJson, ResponseEntity.class);
                if (responseEntity.getResponseCode() == 1001){
                    iHouseToRentView.setBgaAdpaterAndClickResult(responseEntity);
                    loadingDialog.close();
                }else if (responseEntity.getResponseCode() == 1018){
                    RefreshTockenUtil.sendIntDataInvatation(context,refresh_token);
                }else if (responseEntity.getResponseCode() == 1019){
                    LoginOutUtil.sendLoginOutUtil(context);
                }else if (responseEntity.getResponseCode() == 1002){
                    Toast.makeText(context,responseEntity.getMessage(),Toast.LENGTH_LONG).show();
                    loadingDialog.close();
                }



            }

            @Override
            public void onFailed(Exception e) {
                Log.i("oooooo---onFailed---", e.toString());
                Toast.makeText(context,"网络异常，请稍后再试...",Toast.LENGTH_LONG).show();
                loadingDialog.close();
            }
        });
    }


    //房列表
    private void sendHouseListHttp(final Context context) {
        String login_detail = ShareUtil.getStringSpParams(context, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());
        hashMap.put("fields", "id,numberDesc");


        Log.i("dddddd", hashMap.toString() + "-----------" + Common.associatedHouses);
        HttpRequest.get(Common.associatedHouses, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---aaaa", responseJson);
                Type type = new TypeToken<ResponseEntity<List<HouseSelectListBean>>>() {
                }.getType();
                ResponseEntity<List<HouseSelectListBean>> responseEntity = JsonUtil.fromJson(responseJson, type);
                if (null != responseEntity.getData()){
                    if (responseEntity.getResponseCode() == 1001){
                        List<HouseSelectListBean> data = responseEntity.getData();
                        iHouseToRentView.setHouseListResult(data);
                    }else if (responseEntity.getResponseCode() == 1018){
                        RefreshTockenUtil.sendIntDataInvatation(context,refresh_token);
                    }else if (responseEntity.getResponseCode() == 1019){
                        LoginOutUtil.sendLoginOutUtil(context);
                    }


                }


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
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        final ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
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
                    iHouseToRentView.setInformationResult(user);
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


}
