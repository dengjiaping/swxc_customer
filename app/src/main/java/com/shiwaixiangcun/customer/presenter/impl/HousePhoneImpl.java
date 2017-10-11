package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.model.HousePhoneBean;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.presenter.IHousePhonePresenter;
import com.shiwaixiangcun.customer.ui.IHousePhoneView;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.utils.Utils;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public class HousePhoneImpl implements IHousePhonePresenter {
    private IHousePhoneView iHousePhoneView;
    private String houseId;
    private String phone;

    public HousePhoneImpl(IHousePhoneView iHousePhoneView, String houseID, String phone) {
        this.iHousePhoneView = iHousePhoneView;
        this.houseId = houseID;
        this.phone = phone;
    }

    @Override
    public void getHouseNumber(Context context) {
        if (!Utils.isNotEmpty(houseId)) {
            Toast.makeText(context, "租房信息不全", Toast.LENGTH_LONG).show();
            return;
        }
        getNumber(context);
    }

    @Override
    public void validateNumber(Context context) {
        sendBindPhoneHttp(context);
    }


    /**
     * 获取房主号码
     *
     * @param context
     */
    private void getNumber(Context context) {
        String login_detail = SharePreference.getStringSpParams(context, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN);
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);

        String tokenString = (String) AppSharePreferenceMgr.get(context, GlobalConfig.TOKEN, "");
        OkGo.<String>get(Common.housePhone)
                .params("access_token", tokenString)
                .params("houseId", houseId)
                .params("fields", "phone")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<ResponseEntity<List<HousePhoneBean>>>() {
                        }.getType();
                        ResponseEntity<List<HousePhoneBean>> responseEntity = JsonUtil.fromJson(response.body(), type);
                        if (responseEntity == null) {
                            return;
                        }
                        iHousePhoneView.setPhoneInfo(responseEntity);
                    }
                });
    }

    /**
     * 验证用户号码
     *
     * @param context
     */
    private void sendBindPhoneHttp(Context context) {
        String login_detail = SharePreference.getStringSpParams(context, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN);
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();


        String tokenString = (String) AppSharePreferenceMgr.get(context, GlobalConfig.TOKEN, "");
        OkGo.<String>post(Common.bindPhone)
                .params("access_token", tokenString)
                .params("houseId", houseId)
                .params("phone", phone)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        ResponseEntity<ResponseEntity> responseEntity = JsonUtil.fromJson(response.body(), ResponseEntity.class);
                        iHousePhoneView.setPhoneResult(responseEntity);
                    }
                });
    }


}
