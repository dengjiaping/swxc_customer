package com.shiwaixiangcun.customer.ui.activity;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
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
import com.idtk.smallchart.data.PointShape;
import com.idtk.smallchart.interfaces.iData.ICurveData;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.event.EventCenter;
import com.shiwaixiangcun.customer.event.SimpleEvent;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.StringDialogCallBack;
import com.shiwaixiangcun.customer.model.BloodSugarBean;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.utils.DateUtil;
import com.shiwaixiangcun.customer.utils.DisplayUtil;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.LoginOutUtil;
import com.shiwaixiangcun.customer.utils.RefreshTockenUtil;
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
 * 血糖详情Activity
 */
public class BloodSugarActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_sugar_name)
    TextView mTvSugarName;
    @BindView(R.id.tv_kf)
    TextView mTvKf;
    @BindView(R.id.tv_blood_sugar_data)
    TextView mTvBloodSugarData;
    @BindView(R.id.tv_blood_sugar_time)
    TextView mTvBloodSugarTime;
    @BindView(R.id.ll_blood_sugar)
    LinearLayout mLlBloodSugar;
    @BindView(R.id.tv_sugar_qs)
    TextView mTvSugarQs;
    @BindView(R.id.tv_health_introduce)
    TextView mTvHealthIntroduce;
    @BindView(R.id.curveChart)
    CurveChart mCurveChart;
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


    private ArrayList<ICurveData> mDataList = new ArrayList<>();
    private CurveData mCurveData = new CurveData();
    private ArrayList<PointF> mPointArrayList = new ArrayList<>();
    private CurveChart curveChart;
    private List<BloodSugarBean.ElementsBean> mBloodSugarList;

    private int customId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_sugar);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        customId = bundle.getInt("customID");
        EventCenter.getInstance().register(this);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);
        layoutView();
        requestData();
        mBloodSugarList = new ArrayList<>();
        initData();
    }

    /**
     * 请求数据
     */
    private void requestData() {
        String login_detail = SharePreference.getStringSpParams(mContext, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Log.i(BUG_TAG, login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        OkGo.<String>get(GlobalConfig.getBloodSugar)
                .params("access_token", responseEntity.getData().getAccess_token())
                .params("customerId", customId)
                .params("page.pn", 1)
                .params("page.size", 7)
                .execute(new StringDialogCallBack(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(BUG_TAG, "onsuccess");
                        Type type = new TypeToken<ResponseEntity<BloodSugarBean>>() {
                        }.getType();
                        ResponseEntity<BloodSugarBean> responseEntity = JsonUtil.fromJson(response.body(), type);
                        if (responseEntity == null) {
                            return;
                        }
                        Log.e(BUG_TAG, responseEntity.getResponseCode() + "");
                        switch (responseEntity.getResponseCode()) {
                            case 1001:

                                BloodSugarBean.ElementsBean elementsBean = responseEntity.getData().getElements().get(0);
                                EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_BLOOD_SUGAR, 1, elementsBean));
                                mBloodSugarList.addAll(responseEntity.getData().getElements());
//                                mAdapter.notifyDataSetChanged();

                                break;
                            case 1018:
                                RefreshTockenUtil.sendIntDataInvatation(mContext, refresh_token);
                                break;
                            case 1019:
                                LoginOutUtil.sendLoginOutUtil(mContext);
                                break;
                        }
                    }

                });
    }

    private void layoutView() {

        mBackLeft.setOnClickListener(this);
        mRlHistoryBlood.setOnClickListener(this);
        curveChart = (CurveChart) findViewById(R.id.curveChart);
    }

    private void initData() {

        if (null != mBloodSugarList) {
            for (int i = 0; i < mBloodSugarList.size(); i++) {
                mPointArrayList.add(new PointF(i, Float.parseFloat(mBloodSugarList.get(i).getBloodSugar() + "")));
            }
            mCurveData.setValue(mPointArrayList);
            mCurveData.setColor(Color.parseColor("#1CCC8C"));
            Drawable drawable_b = ContextCompat.getDrawable(this, R.drawable.fade_red);
            mCurveData.setDrawable(drawable_b);

            mCurveData.setPointShape(PointShape.CIRCLE);


            mCurveData.setPaintWidth(DisplayUtil.px2dip(this, 3));
            mCurveData.setTextSize(DisplayUtil.px2dip(this, 10));
            mDataList.add(mCurveData);
            Log.i("wwwwwwwwww", mDataList.size() + "");
            if (mPointArrayList.size() > 1) {
                curveChart.setDataList(mDataList);
            }

        }

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.back_left:
                finish();
                break;
            case R.id.rl_history_blood:
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("blood_history", (ArrayList<? extends Parcelable>) mBloodSugarList);
                readyGo(SugarHistoryActivity.class, bundle);
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(SimpleEvent simpleEvent) {
        if (simpleEvent == null || simpleEvent.mEventType != SimpleEvent.UPDATE_BLOOD_SUGAR) {
            return;
        }
        BloodSugarBean.ElementsBean elementsBean = (BloodSugarBean.ElementsBean) simpleEvent.getData();
        String healthStatus = elementsBean.getHealthStatus();
        String statusEnum = elementsBean.getStatusEnum();
        setBackground(healthStatus, statusEnum);
        String sugarStatus = elementsBean.getSugarStatus();
        if (sugarStatus.equals("KF")) {
            mTvKf.setText("空腹");
        } else if (sugarStatus.equals("FH")) {
            mTvKf.setText("饭后两小时");
        }
        mTvBloodSugarData.setText(elementsBean.getBloodSugar() + "");
        mTvBloodSugarTime.setText("" + DateUtil.getMillon(elementsBean.getCreateTime()));
        mTvHealthIntroduce.setText(elementsBean.getSuggestion());
    }

    /**
     * 设置头部颜色
     *
     * @param healthStatus 状态
     */
    private void setBackground(String healthStatus, String statusEnum) {
        switch (healthStatus) {
            case "NORMAL":
                mLlBloodSugar.setBackground(getResources().getDrawable(R.drawable.shape_green_gradient));
                break;
            case "WARNING":
                mLlBloodSugar.setBackground(getResources().getDrawable(R.drawable.shape_yellow_gradient));
                break;
            case "DANGER":
                mLlBloodSugar.setBackground(getResources().getDrawable(R.drawable.shape_red_gradient));
                break;

        }
        switch (statusEnum) {
            case "Zhengchang":
                mTvSugarName.setText("血糖正常");
                mTvSugarQs.setText("您的血糖正常");
                mTvSugarQs.setTextColor(Color.parseColor("#15B77C"));
                break;
            case "Piangao":
                mTvSugarName.setText("血糖偏高");
                mTvSugarQs.setText("您的血糖偏高");
                mTvSugarQs.setTextColor(Color.parseColor("#F96B21"));
                break;
            case "Paindi":
                mTvSugarName.setText("血糖偏低");
                mTvSugarQs.setText("您的血糖偏低");
                mTvSugarQs.setTextColor(Color.parseColor("#F96B21"));
                break;
            case "Yzpiangao":
                mTvSugarName.setText("血糖严重偏高");
                mTvSugarQs.setText("您的血糖严重偏高");
                mTvSugarQs.setTextColor(Color.parseColor("#D53635"));
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
