package com.shiwaixiangcun.customer.ui.activity.heath;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterHeartRate;
import com.shiwaixiangcun.customer.event.EventCenter;
import com.shiwaixiangcun.customer.event.SimpleEvent;
import com.shiwaixiangcun.customer.http.StringDialogCallBack;
import com.shiwaixiangcun.customer.model.HeartRateBean;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.DateUtil;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.LoginOutUtil;
import com.shiwaixiangcun.customer.utils.RefreshTokenUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 心率详情界面
 */
public class HeartRateActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.lv_detail_heartate)
    RecyclerView mRvHeart;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.activity_heartate)
    FrameLayout mActivityHeartate;
    private int customId = 0;
    private List<HeartRateBean.ElementsBean> mBeanList;
    private AdapterHeartRate mAdapter;

    private View headerView = null;
    private TextView mTvHeartIntroduce;
    private TextView mTvHeartNormal;
    private TextView mTvHeartCreateTime;
    private LinearLayout mLlTopHeart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heartate);
        ButterKnife.bind(this);
        EventCenter.getInstance().register(this);
        //百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        Bundle bundle = getIntent().getExtras();
        customId = bundle.getInt("customID");
        initView();
        initData();
    }

    private void initView() {
        mBackLeft.setOnClickListener(this);
        headerView = LayoutInflater.from(mContext).inflate(R.layout.layout_heart_rate_header, null);
        mTvHeartNormal = (TextView) headerView.findViewById(R.id.tv_heart_data);
        mTvHeartCreateTime = (TextView) headerView.findViewById(R.id.tv_heart_create_time);
        mTvHeartIntroduce = (TextView) headerView.findViewById(R.id.tv_heart_introduce);
        mLlTopHeart = (LinearLayout) headerView.findViewById(R.id.ll_top_heart);
        mBeanList = new ArrayList<>();
        mAdapter = new AdapterHeartRate(mBeanList);
        mAdapter.addHeaderView(headerView);
        mRvHeart.setLayoutManager(new LinearLayoutManager(this));
        mRvHeart.setAdapter(mAdapter);
    }

    private void initData() {
        String login_detail = SharePreference.getStringSpParams(mContext, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN);
        Log.i(BUG_TAG, login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();

        String tokenString = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.TOKEN, "");
        OkGo.<String>get(GlobalAPI.getHeartRate)
                .params("access_token", tokenString)
                .params("customerId", customId)
                .params("page.pn", 1)
                .params("page.size", 7)
                .execute(new StringDialogCallBack(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(BUG_TAG, "onsuccess");
                        Type type = new TypeToken<ResponseEntity<HeartRateBean>>() {
                        }.getType();
                        ResponseEntity<HeartRateBean> responseEntity = JsonUtil.fromJson(response.body(), type);
                        if (responseEntity == null) {
                            return;
                        }
                        switch (responseEntity.getResponseCode()) {
                            case 1001:

                                HeartRateBean.ElementsBean elementsBean = responseEntity.getData().getElements().get(0);
                                EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_HEART_RATE, 1, elementsBean));
                                mBeanList.addAll(responseEntity.getData().getElements());
                                mAdapter.notifyDataSetChanged();

                                break;
                            case 1018:
                                RefreshTokenUtil.sendIntDataInvatation(mContext, refresh_token);
                                break;
                            case 1019:
                                LoginOutUtil.sendLoginOutUtil(mContext);
                                break;
                        }
                    }
                });


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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(SimpleEvent simpleEvent) {
        if (simpleEvent == null || simpleEvent.mEventType != SimpleEvent.UPDATE_HEART_RATE) {
            return;
        }
        HeartRateBean.ElementsBean elementsBean = (HeartRateBean.ElementsBean) simpleEvent.getData();
        String healthStatus = elementsBean.getHealthStatus();
        setBackground(healthStatus);
        mTvHeartIntroduce.setText(elementsBean.getSuggestion());
        Log.e(BUG_TAG, elementsBean.getHeartRate() + "");
        mTvHeartNormal.setText("" + elementsBean.getHeartRate());
        mTvHeartCreateTime.setText(DateUtil.getMillon(elementsBean.getCreateTime()));
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
        EventCenter.getInstance().unregister(this);
    }


    private void setBackground(String healthStatus) {
        switch (healthStatus) {
            case "NORMAL":
                mLlTopHeart.setBackground(getResources().getDrawable(R.drawable.shape_green_gradient));
                break;
            case "WARNING":
                mLlTopHeart.setBackground(getResources().getDrawable(R.drawable.shape_yellow_gradient));
                break;
            case "DANGER":
                mLlTopHeart.setBackground(getResources().getDrawable(R.drawable.shape_red_gradient));
                break;

        }

    }
}
