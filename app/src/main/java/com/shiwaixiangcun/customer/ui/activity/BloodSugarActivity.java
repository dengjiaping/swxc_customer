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
import com.shiwaixiangcun.customer.model.BloodSugarBean;
import com.shiwaixiangcun.customer.presenter.impl.BloodSugarImpl;
import com.shiwaixiangcun.customer.ui.IBloodSugarView;
import com.shiwaixiangcun.customer.utils.DisplayUtil;
import com.shiwaixiangcun.customer.utils.TimeToTime;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.pullableview.MyListener;
import com.shiwaixiangcun.customer.widget.pullableview.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class BloodSugarActivity extends BaseActivity implements View.OnClickListener,IBloodSugarView{

    private ChangeLightImageView back_left;
    private TextView tv_sugar_name;
    private LinearLayout ll_blood_sugar;
    private TextView tv_blood_sugar_data;
    private TextView tv_blood_sugar_time;
    private TextView tv_isnot_kf;
    private RelativeLayout rl_history_blood;
    private TextView tv_health_introduce;
    private RelativeLayout head_view;
    private BloodSugarImpl bloodSugar;

    private ArrayList<ICurveData> mDataList = new ArrayList<>();
    private CurveData mCurveData = new CurveData();
    private ArrayList<PointF> mPointArrayList = new ArrayList<>();
    private CurveChart curveChart;
    private List<BloodSugarBean.DataBean.ElementsBean> mylist;
    private TextView tv_sugar_qs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_sugar);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        Intent intent = getIntent();
        mylist = (List<BloodSugarBean.DataBean.ElementsBean>)intent.getSerializableExtra("list_sugar");
        layoutView();
        initData();
    }

    private void layoutView() {
        MyListener myListener = new MyListener();
        PullToRefreshLayout refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        refresh_view.setOnRefreshListener(myListener);
        myListener.setRefreshListener(new MyListener.onRefreshListener() {
            @Override
            public void refreshScence(boolean isnot) {
                bloodSugar.setBgaAdpaterAndClick(BloodSugarActivity.this);
            }
        });
        head_view = (RelativeLayout) findViewById(R.id.head_view);

        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        tv_sugar_name = (TextView) findViewById(R.id.tv_sugar_name);
        ll_blood_sugar = (LinearLayout) findViewById(R.id.ll_blood_sugar);
        tv_blood_sugar_data = (TextView) findViewById(R.id.tv_blood_sugar_data);
        tv_blood_sugar_time = (TextView) findViewById(R.id.tv_blood_sugar_time);
        tv_isnot_kf = (TextView) findViewById(R.id.tv_isnot_kf);
        rl_history_blood = (RelativeLayout) findViewById(R.id.rl_history_blood);
        tv_health_introduce = (TextView) findViewById(R.id.tv_health_introduce);
        curveChart = (CurveChart)findViewById(R.id.curveChart);
        tv_sugar_qs = (TextView) findViewById(R.id.tv_sugar_qs);
    }

    private void initData() {
        bloodSugar = new BloodSugarImpl(this,"");
        bloodSugar.setBgaAdpaterAndClick(this);
        back_left.setOnClickListener(this);
        rl_history_blood.setOnClickListener(this);

        if (null != mylist){
            for (int i = 0; i < mylist.size(); i++) {
                mPointArrayList.add(new PointF(i,Float.parseFloat(mylist.get(i).getBloodSugar()+"")));
            }
            mCurveData.setValue(mPointArrayList);
            mCurveData.setColor(Color.parseColor("#1CCC8C"));

            Drawable drawable_b = ContextCompat.getDrawable(this, R.drawable.fade_red);
            mCurveData.setDrawable(drawable_b);

            mCurveData.setPointShape(PointShape.CIRCLE);



            mCurveData.setPaintWidth(DisplayUtil.px2dip(this,3));
            mCurveData.setTextSize(DisplayUtil.px2dip(this,10));
            mDataList.add(mCurveData);
            Log.i("wwwwwwwwww",mDataList.size()+"");
            if (mPointArrayList.size() > 1){
                curveChart.setDataList(mDataList);
            }

        }

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.back_left:
                finish();
                break;
            case R.id.rl_history_blood:
                Intent intent = new Intent(this,SugarHistoryActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void setBgaAdpaterAndClickResult(BloodSugarBean  result) {
        if (result.getData().getElements() != null && result.getData().getElements().size() != 0){
            String healthStatus = result.getData().getElements().get(0).getHealthStatus();
            if (healthStatus.equals("NORMAL")){
                Resources resources = this.getResources();
                Drawable drawable = resources.getDrawable(R.drawable.back_start_end);
                Drawable drawable_a = resources.getDrawable(R.drawable.back_start_end);
                ll_blood_sugar.setBackgroundDrawable(drawable);
                head_view.setBackgroundDrawable(drawable_a);
            }else if (healthStatus.equals("WARNING")){
                Resources resources = this.getResources();
                Drawable drawable = resources.getDrawable(R.drawable.back_start_end_yj);
                Drawable drawable_a = resources.getDrawable(R.drawable.back_start_end_yj);
                ll_blood_sugar.setBackgroundDrawable(drawable);
                head_view.setBackgroundDrawable(drawable_a);
            }else if (healthStatus.equals("DANGER")){
                Resources resources = this.getResources();
                Drawable drawable = resources.getDrawable(R.drawable.back_start_end_wx);
                Drawable drawable_a = resources.getDrawable(R.drawable.back_start_end_wx);
                ll_blood_sugar.setBackgroundDrawable(drawable);
                head_view.setBackgroundDrawable(drawable_a);
            }


            String statusEnum = result.getData().getElements().get(0).getStatusEnum();
            if (statusEnum.equals("Zhengchang")){
                tv_sugar_name.setText("血糖正常");
                tv_sugar_qs.setText("您的血糖正常");
                tv_sugar_qs.setTextColor(Color.parseColor("#15B77C"));
            }else if (statusEnum.equals("Piangao")){
                tv_sugar_name.setText("血糖偏高");
                tv_sugar_qs.setText("您的血糖偏高");
                tv_sugar_qs.setTextColor(Color.parseColor("#F96B21"));
            }else if (statusEnum.equals("Paindi")){
                tv_sugar_name.setText("血糖偏低");
                tv_sugar_qs.setText("您的血糖偏低");
                tv_sugar_qs.setTextColor(Color.parseColor("#F96B21"));
            }else if (statusEnum.equals("Yzpiangao")){
                tv_sugar_name.setText("血糖严重偏高");
                tv_sugar_qs.setText("您的血糖严重偏高");
                tv_sugar_qs.setTextColor(Color.parseColor("#D53635"));
            }

            String sugarStatus = result.getData().getElements().get(0).getSugarStatus();
            if (sugarStatus.equals("KF")){
                tv_isnot_kf.setText("空腹");
            }else if (sugarStatus.equals("FH")){
                tv_isnot_kf.setText("饭后两小时");
            }

            tv_blood_sugar_data.setText(result.getData().getElements().get(0).getBloodSugar()+"");
            tv_blood_sugar_time.setText(TimeToTime.stampToDate(result.getData().getElements().get(0).getCreateTime()+""));
            tv_health_introduce.setText(result.getData().getElements().get(0).getSuggestion());
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
