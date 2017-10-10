package com.shiwaixiangcun.customer.pay;

import android.app.Activity;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.utils.JsonUtil;

import java.lang.reflect.Type;

import okhttp3.Call;

/**
 * 支付工具类
 */

public class PayUtil {
    private static String BUG_TAG = "PayUtil";

    /**
     * 调用支付宝进行支付
     *
     * @param orderNumber 订单号
     * @param tokenString token值
     * @param mContext    调用Activity
     */
    public static void payAli(String orderNumber, String tokenString, final Activity mContext) {
        Log.e(BUG_TAG, "支付宝支付");
        Log.e(BUG_TAG, "支付宝支付    " + tokenString);
        Log.e(BUG_TAG, "支付宝支付    " + orderNumber);
        OkGo.<String>post(GlobalAPI.payZhiFuBao)
                .params("access_token", tokenString)
                .params("orderNumber", orderNumber)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(BUG_TAG, "支付宝支付   " + "获取数据成功");
                        Type type = new TypeToken<ResponseEntity<AliInfo>>() {
                        }.getType();
                        ResponseEntity<AliInfo> entity = JsonUtil.fromJson(response.body(), type);
                        Log.e(BUG_TAG, entity.getMessage());
                        if (entity == null) {
                            return;
                        }
                        String zhiFuBaoResponse = entity.getData().getZhiFuBaoResponse();
                        AliPay.getInstance().pay(zhiFuBaoResponse, mContext);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                        Call rawCall = response.getRawCall();
                        Log.e(BUG_TAG, rawCall.request().url().toString());
                        Log.e(BUG_TAG, "支付宝支付   获取数据失败");
                    }
                });
    }

    /**
     * 调用微信支付
     *
     * @param orderNumber
     * @param tokenString
     * @param context
     */
    public static void payWeixin(String orderNumber, String tokenString, final Activity context) {

        OkGo.<String>post(GlobalAPI.payWeiXin)
                .params("access_token", tokenString)
                .params("orderNumber", orderNumber)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(BUG_TAG, "微信支付    " + response.body());
                        Type type = new TypeToken<ResponseEntity<WeiXinInfo>>() {
                        }.getType();
                        ResponseEntity<WeiXinInfo> entity = JsonUtil.fromJson(response.body(), type);
                        if (entity == null) {
                            return;
                        }
                        WeiXinInfo.WeiXinResponseBean weiXinResponse = entity.getData().getWeiXinResponse();
                        WeiXinPay wXpay = new WeiXinPay(weiXinResponse);
                        wXpay.createWXAPI(context);
                        wXpay.sendPayReq();

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                        Call rawCall = response.getRawCall();
                        Log.e(BUG_TAG, rawCall.request().url().toString());
                        Log.e(BUG_TAG, "微信支付    " + response.body());
                    }
                });


    }
}
