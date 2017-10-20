package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.utils.VersionUpdateUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

public class AboutActivity extends BaseActivity implements View.OnClickListener {

    private ChangeLightImageView back_left;
    private TextView tv_page_name;
    private TextView tv_version;
    private TextView mTvPrivacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_life);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        layoutView();
        initData();
    }

    private void layoutView() {
        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        tv_page_name = (TextView) findViewById(R.id.tv_page_name);
        tv_version = (TextView) findViewById(R.id.tv_version);
        mTvPrivacy = (TextView) findViewById(R.id.tv_privacy);
    }

    private void initData() {
        String verName = VersionUpdateUtil.getVerName(getApplicationContext());
        tv_version.setText("V " + verName);
        tv_page_name.setText("关于世外生活");
        back_left.setOnClickListener(this);
        mTvPrivacy.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.back_left:
                finish();
                break;
            case R.id.tv_privacy:
                readyGo(PrivacyActivity.class);
                break;
        }
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
