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
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.event.EventCenter;
import com.shiwaixiangcun.customer.event.SimpleEvent;
import com.shiwaixiangcun.customer.model.BloodFatBean;
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
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * 血脂详情页面
 */
public class BloodFatActivity extends BaseActivity implements View.OnClickListener {


    List<BloodFatBean.ElementsBean> mBloodFatList = new ArrayList<>();
    @BindView(R.id.tv_blood_fat_name)
    TextView mTvBloodFatName;
    @BindView(R.id.tv_total_cholesterol)
    TextView mTvTotalCholesterol;
    @BindView(R.id.tv_total_value)
    TextView mTvTotalValue;
    @BindView(R.id.tv_hdl_name)
    TextView mTvHdlName;
    @BindView(R.id.tv_topLipo)
    TextView mTvTopLipo;
    @BindView(R.id.tv_triglyceride)
    TextView mTvTriglyceride;
    @BindView(R.id.tv_lowLipo)
    TextView mTvLowLipo;
    @BindView(R.id.tv_fat_time)
    TextView mTvFatTime;
    @BindView(R.id.ll_blood_fat)
    LinearLayout mLlBloodFat;
    @BindView(R.id.tv_fat_tx)
    TextView mTvFatTx;
    @BindView(R.id.tv_blood_fat_introduce)
    TextView mTvBloodFatIntroduce;

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
    @BindView(R.id.curveChart_t)
    LineChartView mCurveChartT;
    @BindView(R.id.curveChart_a)
    LineChartView mCurveChartA;
    @BindView(R.id.curveChart_b)
    LineChartView mCurveChartB;
    @BindView(R.id.curveChart_c)
    LineChartView mCurveChartC;
    @BindView(R.id.llayout_chart)
    LinearLayout mLlayoutChart;


    private int customId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_fat);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        customId = bundle.getInt("customID");
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);
        EventCenter.getInstance().register(this);
        initView();
        initData();

    }

    private void initView() {
        mBackLeft.setOnClickListener(this);
        mRlHistoryBlood.setOnClickListener(this);

    }


    /**
     * 初始化胆固醇表格
     *
     * @param bloodFatList
     */
    private void initChart(List<BloodFatBean.ElementsBean> bloodFatList) {
        if (bloodFatList.size() < 2) {
            mLlayoutChart.setVisibility(View.GONE);
        }

        Log.e(BUG_TAG, bloodFatList.size() + "");
        /***
         * 统一定义
         * 甘油三酯     A
         * 高密度脂蛋白 B
         * 低密度脂蛋白 C
         * 总胆固醇     T
         *
         */
        List<PointValue> mPointT = new ArrayList<>();
        //总胆固醇 数据点
        List<PointValue> mPointA = new ArrayList<>();
        //高密度脂蛋白 数据点
        List<PointValue> mPointB = new ArrayList<>();
        //低密度脂蛋白 数据点
        List<PointValue> mPointC = new ArrayList<>();


        List<Line> linesT = new ArrayList<Line>();
        List<Line> linesC = new ArrayList<>();
        List<Line> linesA = new ArrayList<>();
        List<Line> linesB = new ArrayList<>();


        List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();

        int size = mBloodFatList.size();
        for (int i = 0; i < size; i++) {
            BloodFatBean.ElementsBean elementsBean = bloodFatList.get(i);
            mPointT.add(new PointValue(size - i - 1, (float) elementsBean.getTotalCholesterol()));
            mPointA.add(new PointValue(size - i - 1, (float) elementsBean.getTriglyceride()));
            mPointB.add(new PointValue(size - i - 1, (float) elementsBean.getTopLipo()));
            mPointC.add(new PointValue(size - i - 1, (float) elementsBean.getLowLipo()));
            mAxisXValues.add(new AxisValue(i).setLabel(i + ""));
        }
        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(false);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(getResources().getColor(R.color.black_text_80));  //设置字体颜色
        axisX.setTextSize(6);//设置字体大小
        axisX.setMaxLabelChars(7); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        axisX.setHasLines(false); //x 轴分割线
        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        axisY.setName("mmol/L");//y轴标注
        axisY.setHasLines(true);
        axisY.setHasSeparationLine(true);
        axisY.setTextSize(6);//设置字体大小


        LineChartData dataT = new LineChartData();
        LineChartData dataA = new LineChartData();
        LineChartData dataB = new LineChartData();
        LineChartData dataC = new LineChartData();

        Line lineT = new Line(mPointT).setColor(getResources().getColor(R.color.ui_green));
        Line lineA = new Line(mPointA).setColor(getResources().getColor(R.color.ui_green));
        Line lineB = new Line(mPointB).setColor(getResources().getColor(R.color.ui_green));
        Line lineC = new Line(mPointC).setColor(getResources().getColor(R.color.ui_green));

        initLine(lineT);
        initLine(lineA);
        initLine(lineB);
        initLine(lineC);

        linesT.add(lineT);
        linesA.add(lineA);
        linesB.add(lineB);
        linesC.add(lineC);


        dataT.setLines(linesT);
        dataA.setLines(linesA);
        dataB.setLines(linesB);
        dataC.setLines(linesC);


        dataT.setAxisXBottom(axisX); //x 轴在底部
        dataT.setAxisYLeft(axisY);  //Y轴设置在左边
        dataA.setAxisXBottom(axisX); //x 轴在底部
        dataA.setAxisYLeft(axisY);  //Y轴设置在左边
        dataB.setAxisXBottom(axisX); //x 轴在底部
        dataB.setAxisYLeft(axisY);  //Y轴设置在左边
        dataC.setAxisXBottom(axisX); //x 轴在底部
        dataC.setAxisYLeft(axisY);  //Y轴设置在左边


        mCurveChartT.setInteractive(true);
        mCurveChartT.setLineChartData(dataT);
        mCurveChartA.setLineChartData(dataA);
        mCurveChartB.setLineChartData(dataB);
        mCurveChartC.setLineChartData(dataC);

    }

    private void initLine(Line line) {
        line.setStrokeWidth(1);
        line.setShape(ValueShape.CIRCLE);
        line.setFilled(false);
        line.setCubic(true);//曲线是否平滑，即是曲线还是折线
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(SimpleEvent simpleEvent) {
        if (simpleEvent == null || simpleEvent.mEventType != SimpleEvent.UPDATE_BLOOD_FAT) {
            return;
        }
        BloodFatBean.ElementsBean elementsBean = (BloodFatBean.ElementsBean) simpleEvent.getData();
        String healthStatus = elementsBean.getHealthStatus();
        String statusEnum = elementsBean.getStatusEnum();
        setBackground(healthStatus, statusEnum);


        mTvTotalValue.setText(elementsBean.getTotalCholesterol() + "");
        mTvTriglyceride.setText(elementsBean.getTriglyceride() + "");
        mTvTopLipo.setText(elementsBean.getTopLipo() + "");
        mTvLowLipo.setText(elementsBean.getLowLipo() + "");
        mTvFatTime.setText(DateUtil.getSecond(elementsBean.getCreateTime()));
        mTvBloodFatIntroduce.setText(elementsBean.getSuggestion());

    }

    /**
     * 设置头部颜色
     *
     * @param healthStatus 状态
     */
    private void setBackground(String healthStatus, String statusEnum) {
        switch (healthStatus) {
            case "NORMAL":

                mTvBloodFatName.setText("血脂正常");
                mTvFatTx.setText("您的血脂正常");
                mTvFatTx.setTextColor(Color.parseColor("#15B77C"));
                mLlBloodFat.setBackground(getResources().getDrawable(R.drawable.shape_green_gradient));
                break;
            case "WARNING":

                mTvBloodFatName.setText("血脂异常");
                mTvFatTx.setText("您的血脂异常");
                mTvFatTx.setTextColor(Color.parseColor("#F96B21"));
                mLlBloodFat.setBackground(getResources().getDrawable(R.drawable.shape_yellow_gradient));

                break;
            case "DANGER":

                mTvBloodFatName.setText("血脂危险");
                mTvFatTx.setText("您的血脂处于危险状态");
                mTvFatTx.setTextColor(Color.parseColor("#F96B21"));
                mLlBloodFat.setBackground(getResources().getDrawable(R.drawable.shape_red_gradient));
                break;

        }
        switch (statusEnum) {
            case "Zhengchang":
//                mTvSugarName.setText("血糖正常");
//                mTvSugarQs.setText("您的血糖正常");
//                mTvSugarQs.setTextColor(Color.parseColor("#15B77C"));
                break;
            case "Piangao":
//                mTvSugarName.setText("血糖偏高");
//                mTvSugarQs.setText("您的血糖偏高");
//                mTvSugarQs.setTextColor(Color.parseColor("#F96B21"));
                break;
            case "Paindi":
//                mTvSugarName.setText("血糖偏低");
//                mTvSugarQs.setText("您的血糖偏低");
//                mTvSugarQs.setTextColor(Color.parseColor("#F96B21"));
                break;
            case "Yzpiangao":
//                mTvSugarName.setText("血糖严重偏高");
//                mTvSugarQs.setText("您的血糖严重偏高");
//                mTvSugarQs.setTextColor(Color.parseColor("#D53635"));
                break;
        }
    }


    /**
     * 获取数据
     */
    private void initData() {

        String login_detail = SharePreference.getStringSpParams(mContext, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN);
        Log.i(BUG_TAG, login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        String tokenString = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.TOKEN, "");
        OkGo.<String>get(GlobalAPI.getBloodFat)
                .params("access_token", tokenString)
                .params("customerId", customId)
                .params("page.pn", 1)
                .params("page.size", 7)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<ResponseEntity<BloodFatBean>>() {
                        }.getType();
                        ResponseEntity<BloodFatBean> responseEntity = JsonUtil.fromJson(response.body(), type);
                        if (responseEntity == null) {
                            return;
                        }
                        Log.e(BUG_TAG, responseEntity.getResponseCode() + "");
                        switch (responseEntity.getResponseCode()) {
                            case 1001:

                                BloodFatBean.ElementsBean elementsBean = responseEntity.getData().getElements().get(0);
                                mBloodFatList.addAll(responseEntity.getData().getElements());
                                EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_BLOOD_FAT, 1, elementsBean));
                                initChart(mBloodFatList);

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
            case R.id.rl_history_blood:

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("blood_fat_history", (ArrayList<? extends Parcelable>) mBloodFatList);
                readyGo(BloodFatHistoryActivity.class, bundle);

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
        EventCenter.getInstance().unregister(this);
        StatService.onPause(this);
    }


}
