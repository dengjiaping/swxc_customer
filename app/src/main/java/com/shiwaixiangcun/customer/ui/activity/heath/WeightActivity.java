package com.shiwaixiangcun.customer.ui.activity.heath;

import android.graphics.Color;
import android.graphics.PointF;
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
import com.idtk.smallchart.data.CurveData;
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
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.model.WeightBean;
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
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * 体重详情页面
 */
public class WeightActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_weight_name)
    TextView mTvWeightName;
    @BindView(R.id.tv_weight_data)
    TextView mTvWeightData;
    @BindView(R.id.tv_weight_bmi)
    TextView mTvWeightBmi;
    @BindView(R.id.tv_weight_time)
    TextView mTvWeightTime;
    @BindView(R.id.ll_weight_back)
    LinearLayout mLlWeightBack;
    @BindView(R.id.tv_weight_zt)
    TextView mTvWeightZt;
    @BindView(R.id.tv_weight_introduce)
    TextView mTvWeightIntroduce;
    @BindView(R.id.tv_weight_dream)
    TextView mTvWeightDream;
    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.textView2)
    TextView mTextView2;
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
    private ChangeLightImageView back_left;

    private ArrayList<ICurveData> mDataList = new ArrayList<>();
    private CurveData mCurveData = new CurveData();
    private ArrayList<PointF> mPointArrayList = new ArrayList<>();

    private List<WeightBean.ElementsBean> mWeightList;


    private int customId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        ButterKnife.bind(this);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        Bundle bundle = getIntent().getExtras();
        customId = bundle.getInt("customID");
        EventCenter.getInstance().register(this);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);
        layoutView();
        requestData();
        mWeightList = new ArrayList<>();
//        initData();

    }

    private void requestData() {

        String login_detail = SharePreference.getStringSpParams(mContext, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Log.i(BUG_TAG, login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        OkGo.<String>get(GlobalConfig.getWeight)
                .params("access_token", responseEntity.getData().getAccess_token())
                .params("customerId", customId)
                .params("page.pn", 1)
                .params("page.size", 7)
                .execute(new StringDialogCallBack(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(BUG_TAG, "onsuccess");
                        Type type = new TypeToken<ResponseEntity<WeightBean>>() {
                        }.getType();
                        ResponseEntity<WeightBean> responseEntity = JsonUtil.fromJson(response.body(), type);
                        if (responseEntity == null) {
                            return;
                        }
                        Log.e(BUG_TAG, responseEntity.getResponseCode() + "");
                        switch (responseEntity.getResponseCode()) {
                            case 1001:

                                WeightBean.ElementsBean elementsBean = responseEntity.getData().getElements().get(0);
                                EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_WIGHT, 1, elementsBean));
                                mWeightList.addAll(responseEntity.getData().getElements());
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
        //坐标点
        List<PointValue> mPointValues = new ArrayList<PointValue>();

        List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
        for (int i = 0; i < 7; i++) {
            mPointValues.add(new PointValue(i, i * i));
            mAxisXValues.add(new AxisValue(i).setLabel(i * 2 + ""));
        }
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));  //折线的颜色（橙色）
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(true);//曲线是否平滑，即是曲线还是折线
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(false);//曲线的数据坐标是否加上备注
//      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);
        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.BLACK);  //设置字体颜色
        axisX.setName("time");  //表格名称
        axisX.setTextSize(6);//设置字体大小
        axisX.setMaxLabelChars(8); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        axisX.setHasLines(true); //x 轴分割线
        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        axisY.setName("Kg");//y轴标注
        axisY.setTextSize(6);//设置字体大小
        data.setAxisXBottom(axisX); //x 轴在底部
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        mCurveChart.setInteractive(true);
        mCurveChart.setZoomType(ZoomType.HORIZONTAL);
        mCurveChart.setMaxZoom((float) 2);//最大方法比例
//        mCurveChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        mCurveChart.setLineChartData(data);
        mCurveChart.setVisibility(View.VISIBLE);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(SimpleEvent simpleEvent) {
        if (simpleEvent == null || simpleEvent.mEventType != SimpleEvent.UPDATE_WIGHT) {
            return;
        }
        WeightBean.ElementsBean elementsBean = (WeightBean.ElementsBean) simpleEvent.getData();
        String healthStatus = elementsBean.getHealthStatus();
        String statusEnum = elementsBean.getStatusEnum();
        setBackground(healthStatus, statusEnum);

        mTvWeightData.setText(elementsBean.getWeight() + "");
        mTvWeightBmi.setText(elementsBean.getBmi() + "");
        mTvWeightTime.setText(DateUtil.getMillon(elementsBean.getCreateTime()));
        mTvWeightDream.setText(elementsBean.getWeightDream() + "");
        mTvWeightIntroduce.setText(elementsBean.getSuggestion());

    }

    /**
     * 设置头部颜色
     *
     * @param healthStatus 状态
     */
    private void setBackground(String healthStatus, String statusEnum) {
        switch (healthStatus) {
            case "NORMAL":
                mLlWeightBack.setBackground(getResources().getDrawable(R.drawable.shape_green_gradient));
                break;
            case "WARNING":
                mLlWeightBack.setBackground(getResources().getDrawable(R.drawable.shape_yellow_gradient));

                break;
            case "DANGER":
                mLlWeightBack.setBackground(getResources().getDrawable(R.drawable.shape_red_gradient));
                break;

        }
        switch (statusEnum) {
            case "Zhengchang":
                mTvWeightName.setText("体重正常");
                mTvWeightZt.setText("您的体重正常");
                mTvWeightZt.setTextColor(Color.parseColor("#15B77C"));
                break;
            case "Piangao":
                mTvWeightName.setText("偏胖");
                mTvWeightZt.setText("您的身体偏胖");
                mTvWeightZt.setTextColor(Color.parseColor("#F96B21"));
                break;
            case "Paindi":
                mTvWeightName.setText("偏瘦");
                mTvWeightZt.setText("您的身体偏瘦");
                mTvWeightZt.setTextColor(Color.parseColor("#F96B21"));
                break;
            case "Yzpiangao":
                mTvWeightName.setText("肥胖");
                mTvWeightZt.setText("你的身体肥胖");
                mTvWeightZt.setTextColor(Color.parseColor("#D53635"));
                break;
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
                bundle.putParcelableArrayList("blood_history", (ArrayList<? extends Parcelable>) mWeightList);
                readyGo(WeightHistoryActivity.class, bundle);
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