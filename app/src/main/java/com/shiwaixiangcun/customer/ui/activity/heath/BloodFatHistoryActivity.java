package com.shiwaixiangcun.customer.ui.activity.heath;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.jaeger.recyclerviewdivider.RecyclerViewDivider;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterBloodFatHistory;
import com.shiwaixiangcun.customer.model.BloodFatBean;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 血脂历史记录页面
 */

public class BloodFatHistoryActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.lv_blood_fat_data)
    RecyclerView mLvBloodFatData;

    private List<BloodFatBean.ElementsBean> mList = new ArrayList<>();

    private AdapterBloodFatHistory mHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_fat_data);
        ButterKnife.bind(this);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);


        initData();
        initView();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        mList = bundle.getParcelableArrayList("blood_fat_history");


    }

    private void initView() {

        mTvPageName.setText("血脂历史数据");
        mBackLeft.setOnClickListener(this);
        mHistoryAdapter = new AdapterBloodFatHistory(mList);
        mLvBloodFatData.setLayoutManager(new LinearLayoutManager(this));
        mLvBloodFatData.setAdapter(mHistoryAdapter);
        RecyclerViewDivider divider = new RecyclerViewDivider.Builder(this)
                .setStyle(RecyclerViewDivider.Style.BETWEEN)
                .setDrawableRes(R.drawable.divider)
                .build();
        mLvBloodFatData.addItemDecoration(divider);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.back_left:
                finish();
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
