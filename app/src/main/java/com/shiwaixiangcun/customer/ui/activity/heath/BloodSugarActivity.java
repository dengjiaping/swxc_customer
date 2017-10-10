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
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.event.EventCenter;
import com.shiwaixiangcun.customer.event.SimpleEvent;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.StringDialogCallBack;
import com.shiwaixiangcun.customer.model.BloodSugarBean;
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
    @BindView(R.id.llayout_chart)
    LinearLayout mLlayoutChart;
    private List<BloodSugarBean.ElementsBean> mBloodSugarList;

    private int customId = 0;
    //血糖数据点
    private List<PointValue> mSugarList = new ArrayList<>();
    //X坐标
    private List<AxisValue> mAxisXList = new ArrayList<>();

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
        initView();
        requestData();
        mBloodSugarList = new ArrayList<>();

    }

    /**
     * 请求数据
     */
    private void requestData() {
        String login_detail = SharePreference.getStringSpParams(mContext, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN);
        Log.i(BUG_TAG, login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        OkGo.<String>get(GlobalAPI.getBloodSugar)
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
                                initChart(mBloodSugarList);

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

    /**
     * 设置图表数据
     *
     * @param bloodSugarList
     */
    private void initChart(List<BloodSugarBean.ElementsBean> bloodSugarList) {
        if (bloodSugarList == null) {
            return;
        }

        if (bloodSugarList.size() < 2) {
            // TODO: 2017/9/29 绘制一个点
            mLlayoutChart.setVisibility(View.GONE);
        }
        Log.e(BUG_TAG, "" + bloodSugarList.size());
        for (int i = 0; i < bloodSugarList.size(); i++) {
            BloodSugarBean.ElementsBean elementsBean = bloodSugarList.get(i);
            mSugarList.add(new PointValue(i, (float) elementsBean.getBloodSugar()));
            mAxisXList.add(new AxisValue(i).setLabel(i + ""));
        }

        LineChartData lineChartData = new LineChartData();
        //设置线的属性
        Line line = new Line(mSugarList);
        line.setColor(getResources().getColor(R.color.ui_green));
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(true);//曲线是否平滑，即是曲线还是折线
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(false);//曲线的数据坐标是否加上备注
        line.setStrokeWidth(1);
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        //将所有线条添加至数组中
        List<Line> lines = new ArrayList<>();
        lines.add(line);

        lineChartData.setLines(lines);
        //设置坐标轴

        //X轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(false);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(getResources().getColor(R.color.black_text_80));  //设置字体颜色
        //axisX.setName("date");  //表格名称
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(7); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXList);  //填充X轴的坐标名称
        axisX.isAutoGenerated();
        axisX.setHasLines(true); //x 轴分割线
        lineChartData.setAxisXBottom(axisX);


        // Y轴
        Axis axisY = new Axis();  //Y轴
        axisY.setName("mmol/L");//y轴标注
        axisY.setTextColor(getResources().getColor(R.color.black_text_80));
        axisY.setTextSize(10);//设置字体大小
        axisY.setHasLines(true);
        lineChartData.setAxisYLeft(axisY);

        mCurveChart.setLineChartData(lineChartData);
    }

    private void initView() {
        mBackLeft.setOnClickListener(this);
        mRlHistoryBlood.setOnClickListener(this);
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
                readyGo(BloodSugarHistoryActivity.class, bundle);
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
