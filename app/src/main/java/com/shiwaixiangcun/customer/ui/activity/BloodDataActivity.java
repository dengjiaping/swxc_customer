package com.shiwaixiangcun.customer.ui.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.PressureHistoryAdapter;
import com.shiwaixiangcun.customer.model.BloodPressureDataBean;
import com.shiwaixiangcun.customer.presenter.impl.BloodDataImpl;
import com.shiwaixiangcun.customer.pullableview.MyListener;
import com.shiwaixiangcun.customer.pullableview.PullToRefreshLayout;
import com.shiwaixiangcun.customer.pullableview.PullableListView;
import com.shiwaixiangcun.customer.response.PageBean;
import com.shiwaixiangcun.customer.response.ResponseEntity;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.ui.IBloodDataView;

import java.util.List;

public class BloodDataActivity extends AppCompatActivity implements View.OnClickListener,IBloodDataView{

    private ChangeLightImageView back_left;
    private TextView tv_page_name;
    private PullableListView lv_blood_pressure_data;
    private RelativeLayout head_view;
    private BloodDataImpl bloodData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_data);
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
                bloodData.setBgaAdpaterAndClick(BloodDataActivity.this);
            }
        });


        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        tv_page_name = (TextView) findViewById(R.id.tv_page_name);
        lv_blood_pressure_data = (PullableListView) findViewById(R.id.lv_blood_pressure_data);
    }

    private void initData() {
        head_view.setBackgroundColor(Color.parseColor("#FFFFFF"));
        bloodData = new BloodDataImpl(this,"");
        bloodData.setBgaAdpaterAndClick(this);
        tv_page_name.setText("血压数据");
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
    public void setBgaAdpaterAndClickResult( ResponseEntity<PageBean<BloodPressureDataBean>> result) {
        if (result.getData().getElements() != null && result.getData().getElements().size() != 0){
            List<BloodPressureDataBean> blood_pressure_data = result.getData().getElements();
            PressureHistoryAdapter pressureHistoryAdapter = new PressureHistoryAdapter(blood_pressure_data,this);
            lv_blood_pressure_data.setAdapter(pressureHistoryAdapter);

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
