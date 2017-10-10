package com.shiwaixiangcun.customer.ui.activity.heath;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.event.EventCenter;
import com.shiwaixiangcun.customer.event.SimpleEvent;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.BloodPressureBean;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
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
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;

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
    @BindView(R.id.curveChart)
    LineChartView mCurveChart;
    @BindView(R.id.curveChart_relax)
    LineChartView mCurveChartRelax;
    @BindView(R.id.llayout_chart)
    LinearLayout mLlayoutChart;


    private int customId;

    private List<BloodPressureBean.ElementsBean> mList = new ArrayList<>();

    private List<PointValue> mRelaxationList = new ArrayList<>();
    private List<PointValue> mShrinkList = new ArrayList<>();
    private List<AxisValue> mAxisValueList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);
        ButterKnife.bind(this);
        EventCenter.getInstance().register(this);
        // 百度统计
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
        String login_detail = SharePreference.getStringSpParams(mContext, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN);
        Log.i(BUG_TAG, login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();

        String tokenString = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.TOKEN, "");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", tokenString);
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
                        BloodPressureBean.ElementsBean elementsBean = responseEntity.getData().getElements().get(0);
                        EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_BLOOD_PRESSURE, 1, elementsBean));
                        mList.addAll(responseEntity.getData().getElements());
                        configuration(responseEntity.getData().getElements());
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

    /**
     * 绘制图表
     *
     * @param elements
     */
    private void configuration(List<BloodPressureBean.ElementsBean> elements) {
        if (elements == null) {
            return;
        }
        if (elements.size() < 2) {
            mLlayoutChart.setVisibility(View.GONE);
        }
        //初始化表格的数据
        for (int i = 0; i < elements.size(); i++) {
            BloodPressureBean.ElementsBean elementsBean = elements.get(i);
            mAxisValueList.add(new AxisValue(i).setLabel(i + ""));
            mShrinkList.add(new PointValue(i, elementsBean.getShrinkBlood()));
            mRelaxationList.add(new PointValue(i, elementsBean.getRelaxationBlood()));
        }
        //绘制收缩压曲线
        Line shrinkLine = new Line(mShrinkList).setColor(Color.parseColor("#FE9020"));  //折线的颜色（橙色）
        List<Line> lines = new ArrayList<Line>();
        shrinkLine.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        shrinkLine.setCubic(true);//曲线是否平滑，即是曲线还是折线
        shrinkLine.setFilled(false);//是否填充曲线的面积
        shrinkLine.setHasLabels(false);//曲线的数据坐标是否加上备注
        shrinkLine.setStrokeWidth(1);
        shrinkLine.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        shrinkLine.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(shrinkLine);

        //绘制舒张压线
        Line relaxLine = new Line(mRelaxationList).setColor(Color.parseColor("#1CCC8C"));
        List<Line> relaxLines = new ArrayList<Line>();
        relaxLine.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        relaxLine.setCubic(true);//曲线是否平滑，即是曲线还是折线
        relaxLine.setFilled(false);//是否填充曲线的面积
        relaxLine.setStrokeWidth(1);
        relaxLine.setHasLabels(false);//曲线的数据坐标是否加上备注
//      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        relaxLine.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        relaxLine.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        relaxLines.add(relaxLine);


        //舒张压数据
        LineChartData relaxData = new LineChartData();
        //收缩压数据
        LineChartData shrinkData = new LineChartData();
        shrinkData.setLines(lines);
        relaxData.setLines(relaxLines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(false);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(getResources().getColor(R.color.black_text_80));  //设置字体颜色
        //axisX.setName("date");  //表格名称
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(7); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisValueList);  //填充X轴的坐标名称
        axisX.isAutoGenerated();

        relaxData.setAxisXBottom(axisX);
        shrinkData.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线

        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        axisY.setName("mmHg");//y轴标注
        axisY.setTextColor(getResources().getColor(R.color.black_text_80));
        axisY.setTextSize(10);//设置字体大小
        axisY.setHasLines(true);
        relaxData.setAxisYLeft(axisY);
        shrinkData.setAxisYLeft(axisY);  //Y轴设置在左边

        //data.setAxisYRight(axisY);  //y轴设置在右边


        //设置行为属性，支持缩放、滑动以及平移

        mCurveChart.setLineChartData(shrinkData);

        mCurveChartRelax.setLineChartData(relaxData);
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
        Log.e(BUG_TAG, elementsBean.getSuggestion());

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
                bundle.putParcelableArrayList("blood_pressure", (ArrayList<? extends Parcelable>) mList);
                readyGo(BloodHistoryActivity.class, bundle);
                break;
            case R.id.tv_day:

                mTvDay.setTextColor(Color.parseColor("#FFFFFF"));
                mTvWeek.setTextColor(Color.parseColor("#1CCC8C"));
                mTvMonth.setTextColor(Color.parseColor("#1CCC8C"));
                mTvYear.setTextColor(Color.parseColor("#1CCC8C"));
                mTvDay.setBackgroundColor(Color.parseColor("#1CCC8C"));
                mTvWeek.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mTvMonth.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mTvYear.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case R.id.tv_week:

                mTvDay.setTextColor(Color.parseColor("#1CCC8C"));
                mTvWeek.setTextColor(Color.parseColor("#FFFFFF"));
                mTvMonth.setTextColor(Color.parseColor("#1CCC8C"));
                mTvYear.setTextColor(Color.parseColor("#1CCC8C"));

                mTvDay.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mTvWeek.setBackgroundColor(Color.parseColor("#1CCC8C"));
                mTvMonth.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mTvYear.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case R.id.tv_month:

                mTvDay.setTextColor(Color.parseColor("#1CCC8C"));
                mTvWeek.setTextColor(Color.parseColor("#1CCC8C"));
                mTvMonth.setTextColor(Color.parseColor("#FFFFFF"));
                mTvYear.setTextColor(Color.parseColor("#1CCC8C"));

                mTvDay.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mTvWeek.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mTvMonth.setBackgroundColor(Color.parseColor("#1CCC8C"));
                mTvYear.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case R.id.tv_year:

                mTvDay.setTextColor(Color.parseColor("#1CCC8C"));
                mTvWeek.setTextColor(Color.parseColor("#1CCC8C"));
                mTvMonth.setTextColor(Color.parseColor("#1CCC8C"));
                mTvYear.setTextColor(Color.parseColor("#FFFFFF"));

                mTvDay.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mTvWeek.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mTvMonth.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mTvYear.setBackgroundColor(Color.parseColor("#1CCC8C"));
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