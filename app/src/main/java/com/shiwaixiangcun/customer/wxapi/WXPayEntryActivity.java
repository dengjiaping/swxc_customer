package com.shiwaixiangcun.customer.wxapi;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private static final String APP_ID = "wx4811bcb64cb3e434";
    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        api = WXAPIFactory.createWXAPI(this, APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        Log.d(BUG_TAG, "请求");
        Log.d(BUG_TAG, req.openId);
    }

    /**
     * 得到支付结果回调
     */
    @Override
    public void onResp(BaseResp resp) {


    }

}