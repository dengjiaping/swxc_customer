package com.shiwaixiangcun.customer.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.presenter.impl.FeedBackImpl;
import com.shiwaixiangcun.customer.ui.IFeedBackView;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

public class FeedBackActivity extends AppCompatActivity implements View.OnClickListener ,IFeedBackView {

    private ChangeLightImageView back_left;
    private TextView tv_page_name;
    private TextView tv_top_right;
    private EditText post_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        layoutView();
        initData();
    }

    private void layoutView() {
        back_left = (ChangeLightImageView)findViewById(R.id.back_left);
        tv_page_name = (TextView) findViewById(R.id.tv_page_name);
        tv_top_right = (TextView) findViewById(R.id.tv_top_right);
        post_content = (EditText) findViewById(R.id.post_content);
    }

    private void initData() {

        tv_page_name.setText("意见反馈");
        tv_top_right.setVisibility(View.VISIBLE);
        tv_top_right.setText("提交");
        tv_top_right.setTextColor(Color.parseColor("#1CCC8C"));
        back_left.setOnClickListener(this);
        tv_top_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.back_left:
                finish();
                break;
            case R.id.tv_top_right:
                FeedBackImpl feedBack = new FeedBackImpl(this,post_content.getText().toString().trim());
                feedBack.setBgaAdpaterAndClick(this);
                break;
        }
    }

    @Override
    public void setBgaAdpaterAndClickResult(ResponseEntity result) {
        Toast.makeText(this,"反馈成功",Toast.LENGTH_LONG).show();
        finish();
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
