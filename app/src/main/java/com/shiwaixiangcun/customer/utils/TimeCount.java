package com.shiwaixiangcun.customer.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 发送验证码 倒计时
 */
public class TimeCount extends CountDownTimer {
    private TextView textView;

    public TimeCount(long millisInFuture, long countDownInterval, TextView textView) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;

    }

    @Override
    public void onTick(long millisUntilFinished) {
        textView.setClickable(false);
        textView.setText("(" + millisUntilFinished / 1000 + ") 秒后重试");
    }

    @Override
    public void onFinish() {
        textView.setText("重新获取验证码");
        textView.setClickable(true);

    }
}