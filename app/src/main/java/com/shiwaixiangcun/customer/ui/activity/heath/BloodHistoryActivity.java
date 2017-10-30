package com.shiwaixiangcun.customer.ui.activity.heath;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.jaeger.recyclerviewdivider.RecyclerViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterBloodPressure;
import com.shiwaixiangcun.customer.model.BloodPressureBean;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 血压历史记录
 */

public class BloodHistoryActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.tv_top_right)
    TextView mTvTopRight;
    @BindView(R.id.iv_share_right)
    ImageView mIvShareRight;
    @BindView(R.id.iv_sao_right)
    ImageView mIvSaoRight;
    @BindView(R.id.ll_image_right)
    LinearLayout mLlImageRight;
    @BindView(R.id.top_bar_write)
    RelativeLayout mTopBarWrite;
    @BindView(R.id.rv_blood_pressure_history)
    RecyclerView mRvBloodPressureHistory;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.activity_sugar_history)
    LinearLayout mActivitySugarHistory;

    private List<BloodPressureBean.ElementsBean> mHistoryList = new ArrayList<>();
    private AdapterBloodPressure mHistoryAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_data);
        ButterKnife.bind(this);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);
        Bundle bundle = getIntent().getExtras();
        mHistoryList = bundle.getParcelableArrayList("blood_pressure");
        initView();
        initData();
    }

    private void initView() {
        mTvPageName.setText("血压数据");
        mBackLeft.setOnClickListener(this);
        mActivitySugarHistory.setOnClickListener(this);
    }


    private void initData() {
        mHistoryAdapter = new AdapterBloodPressure(mHistoryList);
        mRvBloodPressureHistory.setLayoutManager(new LinearLayoutManager(this));
        mRvBloodPressureHistory.setAdapter(mHistoryAdapter);

        RecyclerViewDivider divider = new RecyclerViewDivider.Builder(this)
                .setStyle(RecyclerViewDivider.Style.BETWEEN)
                .setDrawableRes(R.drawable.divider)
                .build();
        mRvBloodPressureHistory.addItemDecoration(divider);

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
