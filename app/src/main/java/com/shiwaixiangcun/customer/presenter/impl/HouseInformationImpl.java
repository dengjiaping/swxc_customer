package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.InformationaBean;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.LogoutBean;
import com.shiwaixiangcun.customer.model.User;
import com.shiwaixiangcun.customer.presenter.IInformationPresenter;
import com.shiwaixiangcun.customer.response.ResponseEntity;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.LoginOutUtil;
import com.shiwaixiangcun.customer.utils.RefreshTockenUtil;
import com.shiwaixiangcun.customer.utils.ShareUtil;
import com.shiwaixiangcun.customer.ui.IHouseInformationView;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/25.
 */

public class HouseInformationImpl implements IInformationPresenter {
    private IHouseInformationView iHouseInformationView;
    private String str_content;

    public HouseInformationImpl(IHouseInformationView iHouseInformationView, String str_content) {
        this.iHouseInformationView = iHouseInformationView;
        this.str_content = str_content;
    }

    @Override
    public void setBgaAdpaterAndClick(Context context) {

        sendInformationHttp(context);
    }

    @Override
    public void setHeadImageXg(Context context) {
        sendHeadImageHttp(context);
    }

    @Override
    public void setSex(Context context) {
        sendSexHttp(context);
    }

    @Override
    public void setData(Context context) {
        sendDataHttp(context);
    }

    @Override
    public void setLogout(Context context) {
        sendLogoutHttp(context);
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
                InformationaBean informationaBean = new Gson().fromJson(responseJson, InformationaBean.class);
                if (informationaBean.getResponseCode() == 1001) {
                    iHouseInformationView.setBgaAdpaterAndClickResult(informationaBean);
                } else if (informationaBean.getResponseCode() == 1018) {
                    RefreshTockenUtil.sendIntDataInvatation(context, refresh_token);
                } else if (informationaBean.getResponseCode() == 1019) {
                    LoginOutUtil.sendLoginOutUtil(context);
                }

            }

            @Override
            public void onFailed(Exception e) {
                Log.i("oooooo---onFailed---", e.toString());
            }
        });
    }





    //头像修改
    private void sendHeadImageHttp(final Context context) {
        String login_detail = ShareUtil.getStringSpParams(context, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());
        hashMap.put("imageId", str_content);
        Log.i("ddddddaaass", hashMap.toString() + "-----------" + Common.modify);
        HttpRequest.put(Common.modify, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---", responseJson);
                User user = new Gson().fromJson(responseJson, User.class);
                if (user.getResponseCode() == 1001) {
                    iHouseInformationView.setHeadImage(user);
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

    //设置性别
    private void sendSexHttp(final Context context) {
        String login_detail = ShareUtil.getStringSpParams(context, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());
        hashMap.put("sex", str_content);
        Log.i("dddddd", hashMap.toString() + "-----------" + Common.modify);
        HttpRequest.put(Common.modify, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---", responseJson);
                User user = new Gson().fromJson(responseJson, User.class);
                if (user.getResponseCode() == 1001) {
                    iHouseInformationView.setSex(user);
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

    //设置生日
    private void sendDataHttp(final Context context) {
        String login_detail = ShareUtil.getStringSpParams(context, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());
        hashMap.put("birthday", str_content);
        Log.i("dddddd", hashMap.toString() + "-----------" + Common.modify);
        HttpRequest.put(Common.modify, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---", responseJson);
                User user = new Gson().fromJson(responseJson, User.class);
                if (user.getResponseCode() == 1001) {
                    iHouseInformationView.setData(user);
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

    //登出
    private void sendLogoutHttp(final Context context) {
        String login_detail = ShareUtil.getStringSpParams(context, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());
        Log.i("dddddd", hashMap.toString() + "-----------" + Common.logout);
        HttpRequest.get(Common.logout, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---", responseJson);
                LogoutBean user = new Gson().fromJson(responseJson, LogoutBean.class);
                iHouseInformationView.setLogout(user);


            }

            @Override
            public void onFailed(Exception e) {
                Log.i("oooooo---onFailed---", e.toString());
            }
        });
    }


}
