package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.ImageReturnbean;
import com.shiwaixiangcun.customer.model.InformationBean;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.presenter.IOnlineServicePresenter;
import com.shiwaixiangcun.customer.ui.IOnlineServiceView;
import com.shiwaixiangcun.customer.ui.dialog.DialogLoading;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.LoginOutUtil;
import com.shiwaixiangcun.customer.utils.RefreshTokenUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public class OnlineServiceImpl implements IOnlineServicePresenter {
    String refresh_token;
    String strToken;
    private IOnlineServiceView iOnlineServiceView;
    private String content;
    private HashMap<String, File> hash_image;
    private String str_imaId = "";
    private String s_imageId = "";
    private DialogLoading mDialogLoading;
    private List<File> list_file = new ArrayList<>();

    public OnlineServiceImpl(IOnlineServiceView iOnlineServiceView, String content, HashMap<String, File> hash_image) {
        this.iOnlineServiceView = iOnlineServiceView;
        this.content = content;
        this.hash_image = hash_image;
    }

    @Override
    public void setBgaAdpaterAndClick(Context context) {
        sendOnlineServiceHttp(context);
    }

    @Override
    public void setHaveImageClick(Context context) {
        mDialogLoading = new DialogLoading(context, "正在提交");
        mDialogLoading.show();

        sendImageHttp(context);
    }

    @Override
    public void setInformation(Context context) {
//        sendInformationHttp(context);
    }

    //在线报修
    private void sendOnlineServiceHttp(final Context context) {

        String login_detail = SharePreference.getStringSpParams(context, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN);

        int siteID = (int) AppSharePreferenceMgr.get(context, GlobalConfig.CURRENT_SITE_ID, 0);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        refresh_token = (String) AppSharePreferenceMgr.get(context, GlobalConfig.Refresh_token, "");
        strToken = (String) AppSharePreferenceMgr.get(context, GlobalConfig.TOKEN, "");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", strToken);
        hashMap.put("content", content);
        hashMap.put("siteId", siteID);
        HttpRequest.post(Common.OnlineRepair, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Type type = new TypeToken<ResponseEntity>() {
                }.getType();
                ResponseEntity responseEntity = JsonUtil.fromJson(responseJson, ResponseEntity.class);
                if (responseEntity == null) {
                    return;
                }
                switch (responseEntity.getResponseCode()) {
                    case 1001:
                        iOnlineServiceView.setBgaAdpaterAndClickResult(responseEntity);
                        break;
                    case 1002:
                        Toast.makeText(context, responseEntity.getMessage(), Toast.LENGTH_SHORT).show();
                        break;
                    case 1018:
                        RefreshTokenUtil.sendIntDataInvatation(context, refresh_token);
                        break;
                    case 1019:
                        LoginOutUtil.sendLoginOutUtil(context);
                        break;
                    default:
                        break;


                }


            }

            @Override
            public void onFailed(Exception e) {
                Log.i("oooooo---onFailed---", e.toString());
            }
        });
    }

    //图片提交
    private void sendImageHttp(final Context context) {
        for (int i = 0; i < list_file.size(); i++) {
            list_file.remove(i);
        }

        Iterator iter = hash_image.keySet().iterator();
        while (iter.hasNext()) {
            Object key = iter.next();
            Object val = hash_image.get(key);
            list_file.add((File) val);
        }


        String login_detail = SharePreference.getStringSpParams(context, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        refresh_token = (String) AppSharePreferenceMgr.get(context, GlobalConfig.Refresh_token, "");
        strToken = (String) AppSharePreferenceMgr.get(context, GlobalConfig.TOKEN, "");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", strToken);
        hashMap.put("images", list_file);


        HttpRequest.post(Common.fileSend, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Type type = new TypeToken<ResponseEntity<List<ImageReturnbean>>>() {
                }.getType();
                ResponseEntity<List<ImageReturnbean>> responseEntity = JsonUtil.fromJson(responseJson, type);
                iOnlineServiceView.setHaveImageResult(responseEntity);

                if (responseEntity.getResponseCode() == 1001) {
                    List<ImageReturnbean> data_image = responseEntity.getData();
                    for (int i = 0; i < data_image.size(); i++) {
                        str_imaId += data_image.get(i).getFileId() + ",";
                    }
                    s_imageId = str_imaId.substring(0, str_imaId.length() - 1);

                    sendOnlineServiceImageHttp(context, s_imageId);
                }
            }

            @Override
            public void onFailed(Exception e) {
                Log.i("oooooo---onFailed---", e.toString());
                mDialogLoading.close();
            }
        });
    }

    //在线报修
    private void sendOnlineServiceImageHttp(Context context, String s_imageId) {
        String login_detail = SharePreference.getStringSpParams(context, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        refresh_token = (String) AppSharePreferenceMgr.get(context, GlobalConfig.Refresh_token, "");
        strToken = (String) AppSharePreferenceMgr.get(context, GlobalConfig.TOKEN, "");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", strToken);
        hashMap.put("content", content);
        hashMap.put("imageIds", s_imageId);
        Log.i("ddddddaaaa", hashMap.toString() + "-----------" + Common.OnlineRepair);
        HttpRequest.post(Common.OnlineRepair, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                mDialogLoading.close();
                Type type = new TypeToken<ResponseEntity>() {
                }.getType();
                ResponseEntity responseEntity = JsonUtil.fromJson(responseJson, ResponseEntity.class);
                iOnlineServiceView.setHaveImageResult(responseEntity);


            }

            @Override
            public void onFailed(Exception e) {
                Log.i("oooooo---onFailed---", e.toString());
                mDialogLoading.close();
            }
        });
    }

    //个人信息
    private void sendInformationHttp(final Context context) {
        String login_detail = SharePreference.getStringSpParams(context, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN);
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
                    iOnlineServiceView.setInformation(user);
                } else if (user.getResponseCode() == 1018) {
                    RefreshTokenUtil.sendIntDataInvatation(context, refresh_token);
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
