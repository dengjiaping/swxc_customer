package com.shiwaixiangcun.customer.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.WeightHistoryAdapter;
import com.shiwaixiangcun.customer.model.PageBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.model.WeightBean;
import com.shiwaixiangcun.customer.presenter.impl.WeightHistoryImpl;
import com.shiwaixiangcun.customer.ui.IWeightHistoryView;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.pullableview.MyListener;
import com.shiwaixiangcun.customer.widget.pullableview.PullToRefreshLayout;
import com.shiwaixiangcun.customer.widget.pullableview.PullableListView;

import java.util.List;

public class WeightHistoryActivity extends AppCompatActivity implements View.OnClickListener,IWeightHistoryView{

    private ChangeLightImageView back_left;
    private PullableListView lv_weight_history;
    private RelativeLayout head_view;
    private WeightHistoryImpl weightHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_history);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        layoutView();
        initData();
    }

    private void layoutView() {
        head_view = (RelativeLayout) findViewById(R.id.head_view);
        MyListener myListener = new MyListener();
        PullToRefreshLayout refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        refresh_view.setOnRefreshListener(myListener);
        myListener.setRefreshListener(new MyListener.onRefreshListener() {
            @Override
            public void refreshScence(boolean isnot) {
                weightHistory.setBgaAdpaterAndClick(WeightHistoryActivity.this);
            }
        });

        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        lv_weight_history = (PullableListView) findViewById(R.id.lv_weight_history);
    }

    private void initData() {
        head_view.setBackgroundColor(Color.parseColor("#FFFFFF"));
        weightHistory = new WeightHistoryImpl(this,"");
        weightHistory.setBgaAdpaterAndClick(this);
        back_left.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.back_left:
                finish();
                break;
        }
    }

    @Override
    public void setBgaAdpaterAndClickResult( ResponseEntity<PageBean<WeightBean>> result) {
        if (result.getData().getElements() != null && result.getData().getElements().size() != 0){
            List<WeightBean> weight_history = result.getData().getElements();

            WeightHistoryAdapter weightHistoryAdapter = new WeightHistoryAdapter(weight_history,this);
            lv_weight_history.setAdapter(weightHistoryAdapter);


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
