package com.shiwaixiangcun.customer.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.TokenUtils;

public class TeatActivity extends BaseActivity {

    private String token;
    private String refresh_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teat);
        token = (String) AppSharePreferenceMgr.get(mContext, Common.TOKEN, "token");
        refresh_token = (String) AppSharePreferenceMgr.get(mContext, Common.REFRESH_TOKEN, "refresh_token");

        Log.e(BUG_TAG, "初始Token" + token);
        Log.e(BUG_TAG, "初始reFreshToken" + refresh_token);
        TokenUtils.checkToken(token);
    }

    public void refresh(View view) {
        TokenUtils.refreshToken(mContext, refresh_token);


    }
}
