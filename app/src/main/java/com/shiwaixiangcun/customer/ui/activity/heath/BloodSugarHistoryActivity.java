package com.shiwaixiangcun.customer.ui.activity.heath;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.jaeger.recyclerviewdivider.RecyclerViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterSugarHistory;
import com.shiwaixiangcun.customer.model.BloodSugarBean;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 血糖历史记录页面
 */
public class BloodSugarHistoryActivity extends BaseActivity implements View.OnClickListener {

    List<BloodSugarBean.ElementsBean> mHistoryList = new ArrayList<>();
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
    @BindView(R.id.lv_blood_sugar_data)
    RecyclerView mLvBloodSugarData;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.activity_sugar_history)
    LinearLayout mActivitySugarHistory;
    private AdapterSugarHistory mHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugar_history);
        ButterKnife.bind(this);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        mHistoryList = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        mHistoryList = bundle.getParcelableArrayList("blood_history");
        Log.e(BUG_TAG, mHistoryList.size() + "");
        initView();
        initData();
    }

    private void initData() {
        mHistoryAdapter = new AdapterSugarHistory(mHistoryList);
        mLvBloodSugarData.setLayoutManager(new LinearLayoutManager(this));
        mLvBloodSugarData.setAdapter(mHistoryAdapter);

        RecyclerViewDivider divider = new RecyclerViewDivider.Builder(this)
                .setStyle(RecyclerViewDivider.Style.BETWEEN)
                .setDrawableRes(R.drawable.divider)
                .build();
        mLvBloodSugarData.addItemDecoration(divider);
    }

    private void initView() {
        mTvPageName.setText("血糖数据");
        mBackLeft.setOnClickListener(this);

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_left:
                finish();
                break;


        }
    }
}
