package com.shiwaixiangcun.customer.wxapi;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.utils.LogUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryActivity";

    private IWXAPI api;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, "ol");
        api = WXAPIFactory.createWXAPI(this, "wx4811bcb64cb3e434");
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

    }


    @Override
    public void onResp(BaseResp resp) {
        int errCode = resp.errCode;

        if (errCode == 0) {

            // 0成功 展示成功页面
            Log.d("test", "支付成功的回调方法--onResp--");
            new AlertDialog.Builder(this).setMessage("支付成功").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            }).setTitle("提示").create().show();


        } else if (errCode == -1) {
            //-1 错误 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
            LogUtil.d("fail", "-1 错误 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。");
            new AlertDialog.Builder(this).setMessage("支付出错").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            }).setTitle("提示").create().show();
            finish();
        } else if (errCode == -2) {
            //-2 用户取消 无需处理。发生场景：用户不支付了，点击取消，返回APP。
            finish();
        }
    }

}