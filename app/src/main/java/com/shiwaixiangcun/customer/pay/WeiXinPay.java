package com.shiwaixiangcun.customer.pay;

import android.content.Context;
import android.util.Log;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by Administrator on 2017/9/22.
 */

public class WeiXinPay {

    private String Bug = "WeiXinPay";

    private WeiXinInfo.WeiXinResponseBean mWeiXinResponseBean;
    private IWXAPI api;

    public WeiXinPay(WeiXinInfo.WeiXinResponseBean weiXinResponseBean) {
        this.mWeiXinResponseBean = weiXinResponseBean;
    }

    /**
     * 注册APPID
     *
     * @param mContext
     * @return
     */
    public void createWXAPI(Context mContext) {
        Log.e("微信支付", mWeiXinResponseBean.getAppid());
        api = WXAPIFactory.createWXAPI(mContext, mWeiXinResponseBean.getAppid(), true);
        api.registerApp(mWeiXinResponseBean.getAppid());
    }

    public boolean isPaySupported() {
        return api != null && api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
    }

    /**
     * 调用支付
     *
     * @param
     */
    public void sendPayReq() {
        PayReq request = new PayReq();
        request.appId = mWeiXinResponseBean.getAppid();
        request.partnerId = mWeiXinResponseBean.getPartnerid();
        request.prepayId = mWeiXinResponseBean.getPrepayid();
        request.nonceStr = mWeiXinResponseBean.getNoncestr();
        request.timeStamp = mWeiXinResponseBean.getTimestamp();
        request.packageValue = mWeiXinResponseBean.getPackageX();
        request.sign = mWeiXinResponseBean.getSign();
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        api.sendReq(request);
    }

}

