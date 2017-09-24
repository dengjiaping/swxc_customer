package com.shiwaixiangcun.customer.pay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by Administrator on 2017/9/22.
 */

public class AppRegister extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("注册微信", "注册微信支付");

        final IWXAPI api = WXAPIFactory.createWXAPI(context, null);
        api.registerApp("wx4811bcb64cb3e434");
    }
}
