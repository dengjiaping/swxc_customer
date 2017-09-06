package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.AnnouncementBean;
import com.shiwaixiangcun.customer.presenter.ICommunityPresenter;
import com.shiwaixiangcun.customer.response.PageBean;
import com.shiwaixiangcun.customer.response.ResponseEntity;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.ui.CommunityView;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/5/25.
 */

public class CommunityPresenterImpl implements ICommunityPresenter {
    private CommunityView communityView;

    public CommunityPresenterImpl(CommunityView communityView) {
        this.communityView = communityView;
    }

    @Override
    public void setBgaAdpaterAndClick(Context context) {
        sendAnnouncementListpageHttp();
    }

    // 公告 public
    private void sendAnnouncementListpageHttp() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("page.pn", 1);
        hashMap.put("page.size", 1000);
        hashMap.put("position", "COMMUNITY_ANNOUNCEMENT");

        HttpRequest.get(Common.articleListpage, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---COMMUNITY_ANNOUNCEMENT", responseJson);
                // 分页列表
                Type type = new TypeToken<ResponseEntity<PageBean<AnnouncementBean>>>() {
                }.getType();
                ResponseEntity<PageBean<AnnouncementBean>> responseEntity_ann = JsonUtil.fromJson(responseJson, type);
                communityView.setBgaAdpaterAndClickResult(responseEntity_ann);

            }

            @Override
            public void onFailed(Exception e) {
                Log.i("oooooo---onFailed---", e.toString());
            }
        });
    }
}
