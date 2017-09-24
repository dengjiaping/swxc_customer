package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.R;

public class StartPageActivity extends AppCompatActivity {

    private TextView tv_center_word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        layoutView();
        initData();
    }

    private void layoutView() {
        tv_center_word = (TextView) findViewById(R.id.tv_center_word);
        AlphaAnimation alpha = new AlphaAnimation(0.0f, 1.0f);
        alpha.setDuration(500);
        alpha.setFillAfter(true);
        tv_center_word.setAnimation(alpha);

    }

    private void initData() {
        CountDownTimer cdt = new CountDownTimer(1500, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
//                tv_hello.setText(millisUntilFinished + "");
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(StartPageActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        cdt.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }
}
