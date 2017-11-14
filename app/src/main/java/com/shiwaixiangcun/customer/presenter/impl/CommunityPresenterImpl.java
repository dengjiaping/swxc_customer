package com.shiwaixiangcun.customer.presenter.impl;

import android.content.Context;

import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.presenter.ICommunityPresenter;
import com.shiwaixiangcun.customer.ui.CommunityView;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;

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
        int siteId = (int) AppSharePreferenceMgr.get(context, GlobalConfig.CURRENT_SITE_ID, 0);
        sendAnnouncementListpageHttp(siteId);
    }

    // 公告 public
    private void sendAnnouncementListpageHttp(int siteId) {


    }
}
