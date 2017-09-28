package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;

public class StartPageActivity extends BaseActivity {

    CountDownTimer cdt;
    private TextView tv_center_word;
    private String token;
    private String refreshToken;

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
        cdt = new CountDownTimer(1500, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
//                tv_hello.setText(millisUntilFinished + "");
            }

            @Override
            public void onFinish() {
                readyGoThenKill(MainActivity.class);

            }
        };
        cdt.start();
//        token = (String) AppSharePreferenceMgr.get(mContext, Common.TOKEN, "token");
//        refreshToken = (String) AppSharePreferenceMgr.get(mContext, Common.REFRESH_TOKEN, "refresh_token");
//
//        TokenUtils.checkToken(token);


    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void updateUI(SimpleEvent simpleEvent) {
//        if (simpleEvent == null) {
//            return;
//        }
//        if (simpleEvent.mEventType == SimpleEvent.CHECK_TOKEN) {
//            switch (simpleEvent.mEventValue) {
//                case 1:
//                    Log.e(BUG_TAG, "检查有效");
//                    cdt.start();
//                    break;
//                case 2:
//                    Log.e(BUG_TAG, "检查无效");
//                    TokenUtils.refreshToken(mContext,refreshToken);
//                    break;
//            }
//        }
//        if (simpleEvent.mEventType == SimpleEvent.REFRESH_TOKEN) {
//            switch (simpleEvent.mEventValue) {
//                case 1:
//                    Log.e(BUG_TAG, "刷新成功");
//                    cdt.start();
//                    break;
//                case 2:
//                    Log.e(BUG_TAG, "刷新失败");
//                    readyGoThenKill(LoginActivity.class);
//                    break;
//            }
//        }


//    }

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
