package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.RecordBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.presenter.IRecordPresenter;
import com.shiwaixiangcun.customer.ui.IRecordView;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.LoginOutUtil;
import com.shiwaixiangcun.customer.utils.RefreshTokenUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * @author Administrator
 * @date 2017/5/25
 */

public class HouseRecordImpl implements IRecordPresenter {
    private IRecordView iRecordView;

    public HouseRecordImpl(IRecordView iRecordView) {
        this.iRecordView = iRecordView;

    }

    @Override
    public void setBgaAdpaterAndClick(Context context) {

        sendRecordHttp(context);
    }


    //报修记录
    private void sendRecordHttp(final Context context) {
        String login_detail = SharePreference.getStringSpParams(context, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN);
        int siteId;
        siteId = (int) AppSharePreferenceMgr.get(context, GlobalConfig.CURRENT_SITE_ID, 0);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());
        hashMap.put("page.pn", 1);
        hashMap.put("page.size", 1000);

        hashMap.put("siteId", siteId);


        HttpRequest.get(Common.records, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {

                RecordBean recordBean = new Gson().fromJson(responseJson, RecordBean.class);


                if (recordBean.getResponseCode() == 1001) {
                    iRecordView.setBgaAdpaterAndClickResult(recordBean);
                } else if (recordBean.getResponseCode() == 1018) {
                    RefreshTokenUtil.sendIntDataInvatation(context, refresh_token);
                } else if (recordBean.getResponseCode() == 1019) {
                    LoginOutUtil.sendLoginOutUtil(context);
                }

            }

            @Override
            public void onFailed(Exception e) {
            }
        });
    }


}
