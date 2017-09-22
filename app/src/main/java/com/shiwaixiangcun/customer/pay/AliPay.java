package com.shiwaixiangcun.customer.pay;

import android.app.Activity;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.shiwaixiangcun.customer.event.EventCenter;
import com.shiwaixiangcun.customer.event.SimpleEvent;

/**
 * 支付宝支付
 */

public class AliPay {

    // 静态实例变量
    private static AliPay instance;
    private String BUG_TAG = this.getClass().getSimpleName();
    private boolean isPay = false;

    // 私有化构造函数
    private AliPay() {

    }

    // 静态public方法，向整个应用提供单例获取方式
    public static AliPay getInstance() {
        if (instance == null) {
            instance = new AliPay();
        }
        return instance;
    }

    public boolean isPay() {
        return isPay;
    }

    public void pay(final String zhiFuBaoResponse, final Activity mContext) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask aliPay = new PayTask(mContext);
                // 调用支付接口，获取支付结果
                String result = aliPay.pay(zhiFuBaoResponse, true);
                AliResult aliResult = new AliResult(result);
                String resultStatus = aliResult.resultStatus;
                // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                if (TextUtils.equals(resultStatus, "9000")) {
                    EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.PAY_SUCCESS, 0));
                } else {
                    EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.PAY_DEFAULT, 0));
                    // 判断resultStatus 为非“9000”则代表可能支付失败
                    // “8000” 代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                }

            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();


    }

}
