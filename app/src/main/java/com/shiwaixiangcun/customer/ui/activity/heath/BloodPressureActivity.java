package com.shiwaixiangcun.customer.ui.activity.heath;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.google.gson.reflect.TypeToken;
import com.idtk.smallchart.chart.CurveChart;
import com.idtk.smallchart.data.CurveData;
import com.idtk.smallchart.interfaces.iData.ICurveData;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.event.EventCenter;
import com.shiwaixiangcun.customer.event.SimpleEvent;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.BloodPressureBean;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.utils.DateUtil;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.LoginOutUtil;
import com.shiwaixiangcun.customer.utils.RefreshTockenUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 血压页面
 */
public class BloodPressureActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_blood_name)
    TextView mTvBloodName;
    @BindView(R.id.tv_shrink_blood)
    TextView mTvShrinkBlood;
    @BindView(R.id.tv_relax_blood)
    TextView mTvRelaxBlood;
    @BindView(R.id.tv_blood_pressure_time)
    TextView mTvBloodPressureTime;
    @BindView(R.id.ll_blood_pressure)
    LinearLayout mLlBloodPressure;
    @BindView(R.id.tv_pressure_qs)
    TextView mTvPressureQs;
    @BindView(R.id.tv_pressure_introduce)
    TextView mTvPressureIntroduce;
    @BindView(R.id.tv_day)
    TextView mTvDay;
    @BindView(R.id.tv_week)
    TextView mTvWeek;
    @BindView(R.id.tv_month)
    TextView mTvMonth;
    @BindView(R.id.tv_year)
    TextView mTvYear;
    @BindView(R.id.curveChart)
    CurveChart mCurveChart;
    @BindView(R.id.curveChart_relax)
    CurveChart mCurveChartRelax;
    @BindView(R.id.rl_history_blood)
    RelativeLayout mRlHistoryBlood;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
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
    @BindView(R.id.top_bar_transparent)
    RelativeLayout mTopBarTransparent;
    @BindView(R.id.activity_blood_pressure)
    RelativeLayout mActivityBloodPressure;
    private ChangeLightImageView back_left;
    private RelativeLayout rl_history_blood;
    private LinearLayout ll_blood_pressure;
    private TextView tv_shrink_blood;
    private TextView tv_relax_blood;
    private TextView tv_blood_name;
    private TextView tv_blood_pressure_time;
    private TextView tv_pressure_introduce;
    private RelativeLayout head_view;
    private CurveChart curveChart;
    private ArrayList<PointF> mPointArrayList = new ArrayList<>();
    private ArrayList<PointF> mPointArrayList_relax = new ArrayList<>();
    private CurveData mCurveData = new CurveData();
    private CurveData mCurveData_relax = new CurveData();
    private ArrayList<ICurveData> mDataList = new ArrayList<>();
    private ArrayList<ICurveData> mDataList_relax = new ArrayList<>();
    private CurveChart curveChart_relax;
    private TextView tv_day;
    private TextView tv_week;
    private TextView tv_month;
    private TextView tv_year;
    private TextView tv_pressure_qs;

    private int customId;

    private List<BloodPressureBean.ElementsBean> mList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);
        ButterKnife.bind(this);
        EventCenter.getInstance().register(this);
        //        百度统计
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
        mRlHistoryBlood.setOnClickListener(this);


    }

    private void initData() {
        String login_detail = SharePreference.getStringSpParams(mContext, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Log.i(BUG_TAG, login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());
        hashMap.put("customerId", customId);
        HttpRequest.get(Common.pressureBlood, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.e(BUG_TAG, "onSuccess");
                Type type = new TypeToken<ResponseEntity<BloodPressureBean>>() {
                }.getType();
                ResponseEntity<BloodPressureBean> responseEntity = JsonUtil.fromJson(responseJson, type);
                if (responseEntity == null) {
                    return;
                }
                switch (responseEntity.getResponseCode()) {
                    case 1001:
//                        configuration(responseEntity.getData().getElements());
                        BloodPressureBean.ElementsBean elementsBean = responseEntity.getData().getElements().get(0);
                        EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_BLOOD_PRESSURE, 1, elementsBean));
                        mList.addAll(responseEntity.getData().getElements());
                        break;
                    case 1018:
                        RefreshTockenUtil.sendIntDataInvatation(mContext, refresh_token);
                        break;
                    case 1019:
                        LoginOutUtil.sendLoginOutUtil(mContext);
                        break;
                }
            }

            @Override
            public void onFailed(Exception e) {
                Log.e(BUG_TAG, e.toString());
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(SimpleEvent simpleEvent) {
        if (simpleEvent == null || simpleEvent.mEventType != SimpleEvent.UPDATE_BLOOD_PRESSURE) {
            return;
        }
        BloodPressureBean.ElementsBean elementsBean = (BloodPressureBean.ElementsBean) simpleEvent.getData();
        String healthStatus = elementsBean.getHealthStatus();
        String statusEnum = elementsBean.getStatusEnum();
        setBackground(healthStatus, statusEnum);
        mTvShrinkBlood.setText(elementsBean.getShrinkBlood() + "");
        mTvRelaxBlood.setText(elementsBean.getRelaxationBlood() + "");
        mTvPressureIntroduce.setText(elementsBean.getSuggestion());
        mTvBloodPressureTime.setText(DateUtil.getMillon(elementsBean.getCreateTime()));


    }

    /**
     * 设置头部颜色
     *
     * @param healthStatus 状态
     */
    private void setBackground(String healthStatus, String statusEnum) {
        switch (healthStatus) {
            case "NORMAL":
                mLlBloodPressure.setBackground(getResources().getDrawable(R.drawable.shape_green_gradient));
                break;
            case "WARNING":
                mLlBloodPressure.setBackground(getResources().getDrawable(R.drawable.shape_yellow_gradient));
                break;
            case "DANGER":
                mLlBloodPressure.setBackground(getResources().getDrawable(R.drawable.shape_red_gradient));

                break;

        }
        switch (statusEnum) {
            case "Zhengchang":
                mTvBloodName.setText("血压正常");
                mTvPressureQs.setText("您的血压正常");
                mTvPressureQs.setTextColor(Color.parseColor("#15B77C"));
                break;
            case "Piangao":
                mTvBloodName.setText("血压偏高");
                mTvPressureQs.setText("您的血压偏高");
                mTvPressureQs.setTextColor(Color.parseColor("#F96B21"));
                break;
            case "Paindi":
                mTvBloodName.setText("血压偏低");
                mTvPressureQs.setText("您的血压偏低");
                mTvPressureQs.setTextColor(Color.parseColor("#F96B21"));
                break;
            case "Yzpiangao":
                mTvBloodName.setText("血压严重偏高");
                mTvPressureQs.setText("您的血压严重偏高");
                mTvPressureQs.setTextColor(Color.parseColor("#D53635"));
                break;
        }
    }


//    private void configuration(List<BloodPressureBean.ElementsBean> elements) {
//        mPointArrayList.clear();
//        mPointArrayList_relax.clear();
//        if (null != elements) {
//            for (int i = 0; i < elements.size(); i++) {
//                mPointArrayList.add(new PointF(i, elements.get(i).getShrinkBlood()));
//                mPointArrayList_relax.add(new PointF(i, elements.get(i).getRelaxationBlood()));
//            }
//
//            Log.e(BUG_TAG, mPointArrayList.toString());
//            mCurveData.setValue(mPointArrayList);
//            mCurveData.setColor(Color.parseColor("#1CCC8C"));
//            Drawable drawable_b = ContextCompat.getDrawable(this, R.drawable.fade_red);
//            mCurveData.setDrawable(drawable_b);
//            mCurveData.setPointShape(PointShape.CIRCLE);
//            mCurveData.setPaintWidth(DisplayUtil.px2dip(this, 3));
//            mCurveData.setTextSize(DisplayUtil.px2dip(this, 10));
//            mDataList.add(mCurveData);
//            //relax
//            mCurveData_relax.setValue(mPointArrayList_relax);
//            mCurveData_relax.setColor(Color.parseColor("#1CCC8C"));
//            Drawable drawable_c = ContextCompat.getDrawable(this, R.drawable.fade_red);
//            mCurveData_relax.setDrawable(drawable_c);
//            mCurveData_relax.setPointShape(PointShape.CIRCLE);
//            mCurveData_relax.setPaintWidth(DisplayUtil.px2dip(this, 3));
//            mDataList_relax.add(mCurveData_relax);
//            Log.e(BUG_TAG, mDataList.size() + "---------" + mDataList_relax.size());
//            if (mPointArrayList_relax.size() > 1) {
//                curveChart_relax.setDataList(mDataList_relax);
//            }
//
//            if (mPointArrayList.size() > 1) {
//                curveChart.setDataList(mDataList);
//            }
//
//        }
//    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.rl_blood_pressure:

                break;
            case R.id.back_left:
                finish();
                break;
            case R.id.rl_history_blood:
                Bundle bundle = new Bundle();
//                bundle.putParcelableArray("blood_pressure");
                Intent intent = new Intent(this, BloodHistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_day:

                tv_day.setTextColor(Color.parseColor("#FFFFFF"));
                tv_week.setTextColor(Color.parseColor("#1CCC8C"));
                tv_month.setTextColor(Color.parseColor("#1CCC8C"));
                tv_year.setTextColor(Color.parseColor("#1CCC8C"));

                tv_day.setBackgroundColor(Color.parseColor("#1CCC8C"));
                tv_week.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_month.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_year.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case R.id.tv_week:

                tv_day.setTextColor(Color.parseColor("#1CCC8C"));
                tv_week.setTextColor(Color.parseColor("#FFFFFF"));
                tv_month.setTextColor(Color.parseColor("#1CCC8C"));
                tv_year.setTextColor(Color.parseColor("#1CCC8C"));

                tv_day.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_week.setBackgroundColor(Color.parseColor("#1CCC8C"));
                tv_month.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_year.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case R.id.tv_month:

                tv_day.setTextColor(Color.parseColor("#1CCC8C"));
                tv_week.setTextColor(Color.parseColor("#1CCC8C"));
                tv_month.setTextColor(Color.parseColor("#FFFFFF"));
                tv_year.setTextColor(Color.parseColor("#1CCC8C"));

                tv_day.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_week.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_month.setBackgroundColor(Color.parseColor("#1CCC8C"));
                tv_year.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case R.id.tv_year:

                tv_day.setTextColor(Color.parseColor("#1CCC8C"));
                tv_week.setTextColor(Color.parseColor("#1CCC8C"));
                tv_month.setTextColor(Color.parseColor("#1CCC8C"));
                tv_year.setTextColor(Color.parseColor("#FFFFFF"));

                tv_day.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_week.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_month.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_year.setBackgroundColor(Color.parseColor("#1CCC8C"));
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
        EventCenter.getInstance().unregister(this);
    }

}