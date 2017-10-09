package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.AwardBean;
import com.shiwaixiangcun.customer.presenter.IAwardPresenter;
import com.shiwaixiangcun.customer.ui.IAwardView;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/25.
 */

public class AwardImpl implements IAwardPresenter {
    private IAwardView iAwardView;


    public AwardImpl(IAwardView iAwardView) {
        this.iAwardView = iAwardView;

    }

    @Override
    public void setBgaAdpaterAndClick(Context context,int pagepn) {
        sendAwardHttp(context,pagepn);
    }




    //活动
    private void sendAwardHttp(final Context context,int pagepn) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("fields", "id,coverPath");
        hashMap.put("page.pn", pagepn);
        hashMap.put("page.size", 5);

        HttpRequest.get(Common.article, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                AwardBean awardBean = new Gson().fromJson(responseJson, AwardBean.class);
                if (awardBean.getResponseCode() == 1001){
                    iAwardView.setBgaAdpaterAndClickResult(awardBean);
                }else {
                    Toast.makeText(context,awardBean.getMessage(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailed(Exception e) {
                Toast.makeText(context,"网络异常，请稍后再试...",Toast.LENGTH_LONG).show();

            }
        });
    }


}
