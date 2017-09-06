package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
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
import com.shiwaixiangcun.customer.model.WeightBean;
import com.shiwaixiangcun.customer.presenter.impl.WeightImpl;
import com.shiwaixiangcun.customer.pullableview.MyListener;
import com.shiwaixiangcun.customer.pullableview.PullToRefreshLayout;
import com.shiwaixiangcun.customer.response.PageBean;
import com.shiwaixiangcun.customer.response.ResponseEntity;
import com.shiwaixiangcun.customer.utils.DisplayUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.utils.TimeToTime;
import com.shiwaixiangcun.customer.ui.IWeightView;

import java.util.ArrayList;
import java.util.List;

public class WeightActivity extends BaseActivity implements View.OnClickListener,IWeightView{

    private ChangeLightImageView back_left;

    private ArrayList<ICurveData> mDataList = new ArrayList<>();
    private CurveData mCurveData = new CurveData();
    private ArrayList<PointF> mPointArrayList = new ArrayList<>();
    private LinearLayout ll_weight_back;
    private TextView tv_weight_name;
    private TextView tv_weight_data;
    private TextView tv_weight_bmi;
    private TextView tv_weight_time;
    private TextView tv_weight_dream;
    private RelativeLayout rl_history_blood;
    private TextView tv_weight_introduce;
    private RelativeLayout head_view;
    private WeightImpl weight;
    private CurveChart curveChart;
    private List<WeightBean> mylist;
    private TextView tv_weight_zt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        Intent intent = getIntent();
        mylist = (List<WeightBean>)intent.getSerializableExtra("list_weight");
        Log.i("ggggggaaaa",mylist.get(0).getWeight()+"");

        layoutView();
        initData();

//        curveChart.setData(mCurveData);

    }

    private void layoutView() {
        MyListener myListener = new MyListener();
        PullToRefreshLayout refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        refresh_view.setOnRefreshListener(myListener);
        myListener.setRefreshListener(new MyListener.onRefreshListener() {
            @Override
            public void refreshScence(boolean isnot) {
                weight.setBgaAdpaterAndClick(WeightActivity.this);
            }
        });
        head_view = (RelativeLayout) findViewById(R.id.head_view);

        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        ll_weight_back = (LinearLayout) findViewById(R.id.ll_weight_back);
        tv_weight_name = (TextView) findViewById(R.id.tv_weight_name);
        tv_weight_data = (TextView) findViewById(R.id.tv_weight_data);
        tv_weight_bmi = (TextView) findViewById(R.id.tv_weight_bmi);
        tv_weight_time = (TextView) findViewById(R.id.tv_weight_time);
        tv_weight_dream = (TextView) findViewById(R.id.tv_weight_dream);
        rl_history_blood = (RelativeLayout) findViewById(R.id.rl_history_blood);
        tv_weight_introduce = (TextView) findViewById(R.id.tv_weight_introduce);
        curveChart = (CurveChart)findViewById(R.id.curveChart);
        tv_weight_zt = (TextView) findViewById(R.id.tv_weight_zt);
    }

//    private void initData() {
//

//    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.back_left:
                finish();
                break;
            case R.id.rl_history_blood:
                Intent intent = new Intent(this,WeightHistoryActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initData() {
        weight = new WeightImpl(this,"");
        weight.setBgaAdpaterAndClick(this);

        back_left.setOnClickListener(this);
        rl_history_blood.setOnClickListener(this);
        if (null != mylist){
            for (int i = 0; i < mylist.size(); i++) {
                mPointArrayList.add(new PointF(i, mylist.get(i).getWeight()));
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
    public void setBgaAdpaterAndClickResult(ResponseEntity<PageBean<WeightBean>> result) {
        if (result.getData().getElements() != null && result.getData().getElements().size() != 0){

            String healthStatus = result.getData().getElements().get(0).getHealthStatus();
            if (healthStatus.equals("NORMAL")){
                tv_weight_name.setText("体重正常");
                tv_weight_zt.setText("您的体重正常");
                tv_weight_zt.setTextColor(Color.parseColor("#15B77C"));
                Resources resources = this.getResources();
                Drawable drawable = resources.getDrawable(R.drawable.back_start_end);
                Drawable drawable_a = resources.getDrawable(R.drawable.back_start_end);
                ll_weight_back.setBackgroundDrawable(drawable);
                head_view.setBackgroundDrawable(drawable_a);
            }else if (healthStatus.equals("WARNING")){
                tv_weight_name.setText("偏胖");
                tv_weight_zt.setText("您的身体偏胖");
                tv_weight_zt.setTextColor(Color.parseColor("#F96B21"));
                Resources resources = this.getResources();
                Drawable drawable = resources.getDrawable(R.drawable.back_start_end_yj);
                Drawable drawable_a = resources.getDrawable(R.drawable.back_start_end_yj);
                ll_weight_back.setBackgroundDrawable(drawable);
                head_view.setBackgroundDrawable(drawable_a);
            }else if (healthStatus.equals("DANGER")){
                tv_weight_name.setText("肥胖");
                tv_weight_zt.setText("您的身体偏胖");
                tv_weight_zt.setTextColor(Color.parseColor("#D53635"));
                Resources resources = this.getResources();
                Drawable drawable = resources.getDrawable(R.drawable.back_start_end_wx);
                Drawable drawable_a = resources.getDrawable(R.drawable.back_start_end_wx);
                ll_weight_back.setBackgroundDrawable(drawable);
                head_view.setBackgroundDrawable(drawable_a);
            }
            String statusEnum = result.getData().getElements().get(0).getStatusEnum();
            if (statusEnum.equals("Zhengchang")){
                tv_weight_name.setText("体重正常");
            }else if (statusEnum.equals("Piangao")){
                tv_weight_name.setText("偏胖");
            }else if (statusEnum.equals("Paindi")){
                tv_weight_name.setText("偏瘦");
            }else if (statusEnum.equals("Yzpiangao")){
                tv_weight_name.setText("肥胖");
            }

            tv_weight_data.setText(result.getData().getElements().get(0).getWeight()+"");
            tv_weight_bmi.setText(result.getData().getElements().get(0).getBmi()+"");
            tv_weight_time.setText(TimeToTime.stampToDate(result.getData().getElements().get(0).getCreateTime()+""));
            tv_weight_dream.setText(result.getData().getElements().get(0).getWeightDream()+"");
            tv_weight_introduce.setText(result.getData().getElements().get(0).getSuggestion());



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