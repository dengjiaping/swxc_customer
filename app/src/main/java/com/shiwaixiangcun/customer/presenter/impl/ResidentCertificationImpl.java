package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResidentBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.presenter.IResidentCertificationPresenter;
import com.shiwaixiangcun.customer.ui.IResifdentView;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.LoginOutUtil;
import com.shiwaixiangcun.customer.utils.RefreshTokenUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/5/25.
 */

public class ResidentCertificationImpl implements IResidentCertificationPresenter {
    private String BUG_TAG = "ResidentCertificationImpl";
    private IResifdentView iResifdentView;
    private String str_content;

    public ResidentCertificationImpl(IResifdentView iResifdentView, String str_content) {
        this.iResifdentView = iResifdentView;
        this.str_content = str_content;
    }

    @Override
    public void setBgaAdpaterAndClick(Context context) {
        sendResidentHttp(context);
    }


    //房产认证
    private void sendResidentHttp(final Context context) {
        String login_detail = SharePreference.getStringSpParams(context, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN);
        Log.i(BUG_TAG, login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        final ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();

        String tokenString = (String) AppSharePreferenceMgr.get(context, GlobalConfig.TOKEN, "");
        Log.e(BUG_TAG, "sendResidentHttp: " + tokenString);


        OkGo.<String>get(Common.areaTree)
                .params("access_token", tokenString)
                .params("fields", "id,name").execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                Log.e(BUG_TAG, response.body());
                ResidentBean residentBean = new Gson().fromJson(response.body(), ResidentBean.class);
                if (residentBean == null) {
                    return;
                }
                switch (residentBean.getResponseCode()) {
                    case 1001:
                        iResifdentView.setBgaAdpaterAndClickResult(residentBean);
                        break;
                    case 1018:

                        RefreshTokenUtil.sendIntDataInvatation(context, refresh_token);
                        break;
                    case 1019:
                        LoginOutUtil.sendLoginOutUtil(context);
                        break;

                }
            }
        });

    }


}
