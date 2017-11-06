package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterRescue;
import com.shiwaixiangcun.customer.model.RescueWayBean;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 救助方式
 */

public class RescueWayActivity extends BaseActivity {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.rv_rescue_way)
    RecyclerView mRvRescueWay;

    AdapterRescue mAdapterRescue;
    List<RescueWayBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescue_way);
        ButterKnife.bind(this);
        initData();
        initViewAndEvent();
    }

    private void initViewAndEvent() {
        mTvPageName.setText("救助方式");
        mAdapterRescue = new AdapterRescue(mList);
        mRvRescueWay.setLayoutManager(new LinearLayoutManager(this));
        mRvRescueWay.setAdapter(mAdapterRescue);
    }

    private void initData() {
        mList = new ArrayList<>();
        RescueWayBean jiuhuche = new RescueWayBean("救护车救助", "本地应急救助服务请求，采用就近救助原则，调动最近的救助车辆、人员等救助力量，快速到达救助现场， 实施救助。",
                R.drawable.ambulance);

        RescueWayBean zhishengji = new RescueWayBean("直升机救助", "正在建设中...", R.drawable.helicopter);


        mList.add(jiuhuche);
        mList.add(zhishengji);


    }

    @OnClick(R.id.back_left)
    public void onViewClicked() {
        finish();
    }

}
