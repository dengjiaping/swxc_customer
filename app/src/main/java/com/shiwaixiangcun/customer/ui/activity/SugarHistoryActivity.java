package com.shiwaixiangcun.customer.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.SugarHistoryAdapter;
import com.shiwaixiangcun.customer.model.BloodSugarBean;
import com.shiwaixiangcun.customer.presenter.impl.SugarHistoryImpl;
import com.shiwaixiangcun.customer.ui.ISugarHistoryView;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.pullableview.MyListener;
import com.shiwaixiangcun.customer.widget.pullableview.PullToRefreshLayout;
import com.shiwaixiangcun.customer.widget.pullableview.PullableListView;

import java.util.List;

public class SugarHistoryActivity extends AppCompatActivity implements View.OnClickListener,ISugarHistoryView{

    private ChangeLightImageView back_left;
    private PullableListView lv_blood_sugar_data;
    private RelativeLayout head_view;
    private SugarHistoryImpl sugarHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugar_history);
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
                sugarHistory.setBgaAdpaterAndClick(SugarHistoryActivity.this);
            }
        });

        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        lv_blood_sugar_data = (PullableListView) findViewById(R.id.lv_blood_sugar_data);
    }

    private void initData() {
        head_view.setBackgroundColor(Color.parseColor("#FFFFFF"));
        sugarHistory = new SugarHistoryImpl(this,"");
        sugarHistory.setBgaAdpaterAndClick(this);
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
    public void setBgaAdpaterAndClickResult(BloodSugarBean result) {
        if (result.getData().getElements() != null && result.getData().getElements().size() != 0){
            List<BloodSugarBean.DataBean.ElementsBean> elements_sugar = result.getData().getElements();
            SugarHistoryAdapter sugarHistoryAdapter = new SugarHistoryAdapter(elements_sugar,this);
            lv_blood_sugar_data.setAdapter(sugarHistoryAdapter);

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
