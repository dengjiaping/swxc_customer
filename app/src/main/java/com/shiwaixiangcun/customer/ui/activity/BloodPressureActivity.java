package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.idtk.smallchart.chart.CurveChart;
import com.idtk.smallchart.data.CurveData;
import com.idtk.smallchart.data.PointShape;
import com.idtk.smallchart.interfaces.iData.ICurveData;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.BloodPressurebean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.presenter.impl.BloodPressureImpl;
import com.shiwaixiangcun.customer.ui.IBloodPressureView;
import com.shiwaixiangcun.customer.utils.DisplayUtil;
import com.shiwaixiangcun.customer.utils.TimerToTimerUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.pullableview.MyListener;
import com.shiwaixiangcun.customer.widget.pullableview.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class BloodPressureActivity extends BaseActivity implements View.OnClickListener,IBloodPressureView{

    private ChangeLightImageView back_left;
    private RelativeLayout rl_history_blood;
    private LinearLayout ll_blood_pressure;
    private TextView tv_shrink_blood;
    private TextView tv_relax_blood;
    private TextView tv_blood_name;
    private TextView tv_blood_pressure_time;
    private TextView tv_pressure_introduce;
    private RelativeLayout head_view;
    private BloodPressureImpl bloodPressureImpl;
    private CurveChart curveChart;
    private ArrayList<PointF> mPointArrayList = new ArrayList<>();
    private ArrayList<PointF> mPointArrayList_relax = new ArrayList<>();
    private CurveData mCurveData = new CurveData();
    private CurveData mCurveData_relax = new CurveData();
    private ArrayList<ICurveData> mDataList = new ArrayList<>();
    private ArrayList<ICurveData> mDataList_relax = new ArrayList<>();
    private List<BloodPressurebean> mylist_pressure;
    private CurveChart curveChart_relax;
    private TextView tv_day;
    private TextView tv_week;
    private TextView tv_month;
    private TextView tv_year;
    private TextView tv_pressure_qs;
    //    private List<BloodPressurebean> list_pressure_week;
//    private List<BloodPressurebean> list_pressure_month;
//    private List<BloodPressurebean> list_pressure_year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        Intent intent = getIntent();
        mylist_pressure = (List<BloodPressurebean>)intent.getSerializableExtra("list_pressure");

//        list_pressure_week = (List<BloodPressurebean>) intent.getSerializableExtra("list_pressure_week");
//        list_pressure_month = (List<BloodPressurebean>) intent.getSerializableExtra("list_pressure_month");
//        list_pressure_year = (List<BloodPressurebean>) intent.getSerializableExtra("list_pressure_year");
        layoutView();
        initData();
    }



    private void layoutView() {
//        top_bar_transparent = (RelativeLayout) findViewById(R.id.include);
        head_view = (RelativeLayout) findViewById(R.id.head_view);
        MyListener myListener = new MyListener();
        PullToRefreshLayout refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        refresh_view.setOnRefreshListener(myListener);
        myListener.setRefreshListener(new MyListener.onRefreshListener() {
            @Override
            public void refreshScence(boolean isnot) {
                bloodPressureImpl.setBgaAdpaterAndClick(BloodPressureActivity.this);
            }
        });


        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        rl_history_blood = (RelativeLayout) findViewById(R.id.rl_history_blood);
        ll_blood_pressure = (LinearLayout) findViewById(R.id.ll_blood_pressure);
        tv_shrink_blood = (TextView) findViewById(R.id.tv_shrink_blood);
        tv_relax_blood = (TextView) findViewById(R.id.tv_relax_blood);
        tv_blood_name = (TextView) findViewById(R.id.tv_blood_name);
        tv_blood_pressure_time = (TextView) findViewById(R.id.tv_blood_pressure_time);
        tv_pressure_introduce = (TextView) findViewById(R.id.tv_pressure_introduce);
        curveChart = (CurveChart)findViewById(R.id.curveChart);
        curveChart_relax = (CurveChart) findViewById(R.id.curveChart_relax);
        tv_day = (TextView) findViewById(R.id.tv_day);
        tv_week = (TextView) findViewById(R.id.tv_week);
        tv_month = (TextView) findViewById(R.id.tv_month);
        tv_year = (TextView) findViewById(R.id.tv_year);
        tv_pressure_qs = (TextView) findViewById(R.id.tv_pressure_qs);


    }

    private void initData() {
        bloodPressureImpl = new BloodPressureImpl(this,"");
        bloodPressureImpl.setBgaAdpaterAndClick(this);
        configuration(mylist_pressure);


        back_left.setOnClickListener(this);
        rl_history_blood.setOnClickListener(this);

        tv_day.setOnClickListener(this);
        tv_week.setOnClickListener(this);
        tv_month.setOnClickListener(this);
        tv_year.setOnClickListener(this);
    }

    private void configuration(List<BloodPressurebean> list_pressure) {
        mPointArrayList.clear();
        mPointArrayList_relax.clear();
        if (null != list_pressure){
            for (int i = 0; i < list_pressure.size(); i++) {
//            mPointArrayList.add(new PointF(points[i][0], points[i][1]));
                mPointArrayList.add(new PointF(i,list_pressure.get(i).getShrinkBlood()));
                mPointArrayList_relax.add(new PointF(i,list_pressure.get(i).getRelaxationBlood()));
            }

            Log.i("aaaaaaaaaaa",mPointArrayList.toString());
            mCurveData.setValue(mPointArrayList);
            mCurveData.setColor(Color.parseColor("#1CCC8C"));
            Drawable drawable_b = ContextCompat.getDrawable(this, R.drawable.fade_red);
            mCurveData.setDrawable(drawable_b);
            mCurveData.setPointShape(PointShape.CIRCLE);
            mCurveData.setPaintWidth(DisplayUtil.px2dip(this,3));
            mCurveData.setTextSize(DisplayUtil.px2dip(this,10));
            mDataList.add(mCurveData);
            //relax
            mCurveData_relax.setValue(mPointArrayList_relax);
            mCurveData_relax.setColor(Color.parseColor("#1CCC8C"));
            Drawable drawable_c = ContextCompat.getDrawable(this, R.drawable.fade_red);
            mCurveData_relax.setDrawable(drawable_c);
            mCurveData_relax.setPointShape(PointShape.CIRCLE);
            mCurveData_relax.setPaintWidth(DisplayUtil.px2dip(this,3));
            mDataList_relax.add(mCurveData_relax);
            Log.i("gggggggggiiiooo",mDataList.size()+"---------"+mDataList_relax.size());
            if (mPointArrayList_relax.size() > 1){
                curveChart_relax.setDataList(mDataList_relax);
            }

          if (mPointArrayList.size() >1){
              curveChart.setDataList(mDataList);
          }

        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.rl_blood_pressure:

                break;
            case R.id.back_left:
                finish();
                break;
            case R.id.rl_history_blood:
                Intent intent = new Intent(this,BloodDataActivity.class);
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
    public void setBgaAdpaterAndClickResult(ResponseEntity<List<BloodPressurebean>> result) {
        Log.i("oooooookkkk",result.getData().get(0).getHealthStatus());
        if (result.getData() != null && result.getData().size() != 0){
            String healthStatus = result.getData().get(0).getHealthStatus();
            Log.i("ffffffffaaaaa",healthStatus);
            if (healthStatus.equals("NORMAL")){

                Resources resources = this.getResources();
                Drawable drawable = resources.getDrawable(R.drawable.back_start_end);
                Drawable drawable_a = resources.getDrawable(R.drawable.back_start_end);
                ll_blood_pressure.setBackgroundDrawable(drawable_a);
                head_view.setBackgroundDrawable(drawable);
//                top_bar_transparent.setBackgroundDrawable(drawable);
//                ll_blood_pressure.setBackgroundColor(Color.parseColor("#5EDCAE"));
            }else if (healthStatus.equals("WARNING")){
                Log.i("aaaaaaaaaaa","aaaaaaaaa");
                Resources resources = this.getResources();
                Drawable drawable = resources.getDrawable(R.drawable.back_start_end_yj);
                Drawable drawable_a = resources.getDrawable(R.drawable.back_start_end_yj);
                head_view.setBackgroundDrawable(drawable);
                ll_blood_pressure.setBackgroundDrawable(drawable_a);

//                top_bar_transparent.setBackgroundDrawable(drawable);
//                head_view.setBackgroundColor(Color.parseColor("#F96B21"));
//                ll_blood_pressure.setBackgroundColor(Color.parseColor("#FA9D52"));
            }else if (healthStatus.equals("DANGER")){
                Resources resources = this.getResources();
                Drawable drawable = resources.getDrawable(R.drawable.back_start_end_wx);
                Drawable drawable_a = resources.getDrawable(R.drawable.back_start_end_wx);
                ll_blood_pressure.setBackgroundDrawable(drawable_a);
                head_view.setBackgroundDrawable(drawable);
//                top_bar_transparent.setBackgroundDrawable(drawable);
//                ll_blood_pressure.setBackgroundColor(Color.parseColor("#F36766"));
            }
            String statusEnum = result.getData().get(0).getStatusEnum();
            if (statusEnum.equals("Zhengchang")){
                tv_blood_name.setText("血压正常");
                tv_pressure_qs.setText("您的血压正常");
                tv_pressure_qs.setTextColor(Color.parseColor("#15B77C"));
            }else if (statusEnum.equals("Piangao")){
                tv_blood_name.setText("血压偏高");
                tv_pressure_qs.setText("您的血压偏高");
                tv_pressure_qs.setTextColor(Color.parseColor("#F96B21"));
            }else if (statusEnum.equals("Paindi")){
                tv_blood_name.setText("血压偏低");
                tv_pressure_qs.setText("您的血压偏低");
                tv_pressure_qs.setTextColor(Color.parseColor("#F96B21"));
            }else if (statusEnum.equals("Yzpiangao")){
                tv_blood_name.setText("血压严重偏高");
                tv_pressure_qs.setText("您的血压严重偏高");
                tv_pressure_qs.setTextColor(Color.parseColor("#D53635"));
            }

            tv_shrink_blood.setText(result.getData().get(0).getShrinkBlood()+"");
            tv_relax_blood.setText(result.getData().get(0).getRelaxationBlood()+"");
            tv_pressure_introduce.setText(result.getData().get(0).getSuggestion());

            String s = TimerToTimerUtil.stampToDate(result.getData().get(0).getCreateTime()+"");
            tv_blood_pressure_time.setText(s);

            boolean judgetoDay = TimerToTimerUtil.getJudgetoDay(s);
            boolean judgeYesterday = TimerToTimerUtil.getJudgeYesterday(s);
            if (judgetoDay) {
                tv_blood_pressure_time.setText(TimerToTimerUtil.stampToNewDate(result.getData().get(0).getCreateTime() + ""));
            }else if (judgeYesterday){
                tv_blood_pressure_time.setText(TimerToTimerUtil.stamYesterDate(result.getData().get(0).getCreateTime() + ""));
            }


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
