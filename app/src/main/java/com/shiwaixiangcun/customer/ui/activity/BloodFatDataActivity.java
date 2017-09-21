package com.shiwaixiangcun.customer.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.BloodFatHistoryAdapter;
import com.shiwaixiangcun.customer.model.PressureFatBean;
import com.shiwaixiangcun.customer.presenter.impl.BloodFatDataImpl;
import com.shiwaixiangcun.customer.ui.IBloodFatDataView;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.pullableview.MyListener;
import com.shiwaixiangcun.customer.widget.pullableview.PullToRefreshLayout;
import com.shiwaixiangcun.customer.widget.pullableview.PullableListView;

import java.util.List;

public class BloodFatDataActivity extends AppCompatActivity implements View.OnClickListener,IBloodFatDataView{

    private ChangeLightImageView back_left;
    private PullableListView lv_blood_fat_data;
    private RelativeLayout head_view;
    private BloodFatDataImpl bloodFatData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_fat_data);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        layoutView();
        initData();
    }

    private void layoutView() {
        MyListener myListener = new MyListener();
        PullToRefreshLayout refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        refresh_view.setOnRefreshListener(myListener);
        myListener.setRefreshListener(new MyListener.onRefreshListener() {
            @Override
            public void refreshScence(boolean isnot) {
                bloodFatData.setBgaAdpaterAndClick(BloodFatDataActivity.this);
            }
        });
        head_view = (RelativeLayout) findViewById(R.id.head_view);

        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        lv_blood_fat_data = (PullableListView) findViewById(R.id.lv_blood_fat_data);
    }

    private void initData() {
        head_view.setBackgroundColor(Color.parseColor("#FFFFFF"));
        bloodFatData = new BloodFatDataImpl(this,"");
        bloodFatData.setBgaAdpaterAndClick(this);
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
    public void setBgaAdpaterAndClickResult(PressureFatBean result) {
        if (result.getData().getElements() != null && result.getData().getElements().size() != 0){
            List<PressureFatBean.DataBean.ElementsBean> blood_fat_list = result.getData().getElements();
            BloodFatHistoryAdapter bloodFatHistoryAdapter = new BloodFatHistoryAdapter(blood_fat_list,this);
            lv_blood_fat_data.setAdapter(bloodFatHistoryAdapter);
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
