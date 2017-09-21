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
import com.shiwaixiangcun.customer.model.PressureFatBean;
import com.shiwaixiangcun.customer.presenter.impl.BloodFatImpl;
import com.shiwaixiangcun.customer.ui.IBloodFatView;
import com.shiwaixiangcun.customer.utils.DisplayUtil;
import com.shiwaixiangcun.customer.utils.TimeToTime;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.pullableview.MyListener;
import com.shiwaixiangcun.customer.widget.pullableview.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class BloodFatActivity extends BaseActivity implements View.OnClickListener, IBloodFatView {

    private ChangeLightImageView back_left;
    private TextView tv_blood_fat_name;
    private LinearLayout ll_blood_fat;
    private TextView tv_total_value;
    private TextView tv_triglyceride;
    private TextView tv_topLipo;
    private TextView tv_lowLipo;
    private RelativeLayout rl_history_blood;
    private TextView tv_fat_time;
    private TextView tv_blood_fat_introduce;
    private RelativeLayout head_view;
    private BloodFatImpl bloodFat;

    private ArrayList<ICurveData> mDataList = new ArrayList<>();
    private CurveData mCurveData = new CurveData();
    private ArrayList<PointF> mPointArrayList_a = new ArrayList<>();
    private CurveChart curveChart_a;
    private ArrayList<PointF> mPointArrayList_h = new ArrayList<>();
    private List<PressureFatBean.DataBean.ElementsBean> mylist;

    private ArrayList<ICurveData> mDataList_b = new ArrayList<>();
    private CurveData mCurveData_b = new CurveData();
    private ArrayList<PointF> mPointArrayList_b = new ArrayList<>();
    private CurveChart curveChart_b;

    private ArrayList<ICurveData> mDataList_c = new ArrayList<>();
    private CurveData mCurveData_c = new CurveData();
    private ArrayList<PointF> mPointArrayList_c = new ArrayList<>();
    private CurveChart curveChart_c;

    private ArrayList<ICurveData> mDataList_d = new ArrayList<>();
    private CurveData mCurveData_d = new CurveData();
    private ArrayList<PointF> mPointArrayList_d = new ArrayList<>();
    private CurveChart curveChart_d;
    private TextView tv_fat_tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_fat);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        Intent intent = getIntent();
        mylist = (List<PressureFatBean.DataBean.ElementsBean>) intent.getSerializableExtra("list_fat");
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
                bloodFat.setBgaAdpaterAndClick(BloodFatActivity.this);
                back_left.setOnClickListener(BloodFatActivity.this);
            }
        });
        head_view = (RelativeLayout) findViewById(R.id.head_view);

        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        tv_blood_fat_name = (TextView) findViewById(R.id.tv_blood_fat_name);
        ll_blood_fat = (LinearLayout) findViewById(R.id.ll_blood_fat);
        tv_total_value = (TextView) findViewById(R.id.tv_total_value);
        tv_triglyceride = (TextView) findViewById(R.id.tv_triglyceride);
        tv_topLipo = (TextView) findViewById(R.id.tv_topLipo);
        tv_lowLipo = (TextView) findViewById(R.id.tv_lowLipo);
        rl_history_blood = (RelativeLayout) findViewById(R.id.rl_history_blood);
        tv_fat_time = (TextView) findViewById(R.id.tv_fat_time);
        tv_blood_fat_introduce = (TextView) findViewById(R.id.tv_blood_fat_introduce);
        curveChart_a = (CurveChart) findViewById(R.id.curveChart_a);
        curveChart_b = (CurveChart) findViewById(R.id.curveChart_b);
        curveChart_c = (CurveChart) findViewById(R.id.curveChart_c);
        curveChart_d = (CurveChart) findViewById(R.id.curveChart_d);
        tv_fat_tx = (TextView) findViewById(R.id.tv_fat_tx);
    }

    private void initData() {
        bloodFat = new BloodFatImpl(this, "");
        bloodFat.setBgaAdpaterAndClick(this);
        back_left.setOnClickListener(this);
        rl_history_blood.setOnClickListener(this);
        if (null != mylist) {
            for (int i = 0; i < mylist.size(); i++) {
                Log.i("ssssssssssssspppoo",mylist.get(i).getTotalCholesterol()+"");
                mPointArrayList_a.add(new PointF(i, Float.parseFloat(mylist.get(i).getTotalCholesterol() + "")));
                mPointArrayList_h.add(new PointF(i, Float.parseFloat(mylist.get(i).getTotalCholesterol() + "")));
                mPointArrayList_b.add(new PointF(i, Float.parseFloat(mylist.get(i).getTriglyceride() + "")));
                mPointArrayList_c.add(new PointF(i, Float.parseFloat(mylist.get(i).getTopLipo() + "")));
                mPointArrayList_d.add(new PointF(i, Float.parseFloat(mylist.get(i).getLowLipo() + "")));
            }
//            Log.i("dddddddddd",mPo)

            mCurveData.setValue(mPointArrayList_h);
            mCurveData_b.setValue(mPointArrayList_b);
            mCurveData_c.setValue(mPointArrayList_c);
            mCurveData_d.setValue(mPointArrayList_d);

            mCurveData.setColor(Color.parseColor("#1CCC8C"));
            mCurveData_b.setColor(Color.parseColor("#1CCC8C"));
            mCurveData_c.setColor(Color.parseColor("#1CCC8C"));
            mCurveData_d.setColor(Color.parseColor("#1CCC8C"));

            Drawable drawable_a = ContextCompat.getDrawable(this, R.drawable.fade_red);
            Drawable drawable_b = ContextCompat.getDrawable(this, R.drawable.fade_red);
            Drawable drawable_c = ContextCompat.getDrawable(this, R.drawable.fade_red);
            Drawable drawable_d = ContextCompat.getDrawable(this, R.drawable.fade_red);
            mCurveData.setDrawable(drawable_a);
            mCurveData_b.setDrawable(drawable_b);
            mCurveData_c.setDrawable(drawable_c);
            mCurveData_d.setDrawable(drawable_d);
            mCurveData.setPointShape(PointShape.CIRCLE);
            mCurveData_b.setPointShape(PointShape.CIRCLE);
            mCurveData_c.setPointShape(PointShape.CIRCLE);
            mCurveData_d.setPointShape(PointShape.CIRCLE);
            mCurveData.setPaintWidth(DisplayUtil.px2dip(this,3));
            mCurveData_b.setPaintWidth(DisplayUtil.px2dip(this,3));
            mCurveData_c.setPaintWidth(DisplayUtil.px2dip(this,3));
            mCurveData_d.setPaintWidth(DisplayUtil.px2dip(this,3));
            mCurveData.setTextSize(DisplayUtil.px2dip(this,10));
            mCurveData_b.setTextSize(DisplayUtil.px2dip(this,10));
            mCurveData_c.setTextSize(DisplayUtil.px2dip(this,10));
            mCurveData_d.setTextSize(DisplayUtil.px2dip(this,10));
            mDataList.add(mCurveData);
            mDataList_b.add(mCurveData_b);
            mDataList_c.add(mCurveData_c);
            mDataList_d.add(mCurveData_d);
            Log.i("wwwwwwwwww", mDataList.size() + "");
            if (mPointArrayList_h.size() > 1) {
                curveChart_a.setDataList(mDataList);
            }
            if (mPointArrayList_b.size() > 1) {

                curveChart_b.setDataList(mDataList_b);
            }
            if (mPointArrayList_c.size() > 1) {
                curveChart_c.setDataList(mDataList_c);
            }
            if (mPointArrayList_d.size() > 1) {
                curveChart_d.setDataList(mDataList_d);
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
                Intent intent = new Intent(this, BloodFatDataActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void setBgaAdpaterAndClickResult(PressureFatBean result) {
        if (result.getData().getElements() != null && result.getData().getElements().size() != 0) {
//            List<BloodFatBean> blood_fat = result.getData().getElements();
            String healthStatus = result.getData().getElements().get(0).getHealthStatus();
            if (healthStatus.equals("NORMAL")) {
                tv_blood_fat_name.setText("血脂正常");
                tv_fat_tx.setText("您的血脂正常");
                tv_fat_tx.setTextColor(Color.parseColor("#15B77C"));
                Resources resources = this.getResources();
                Drawable drawable = resources.getDrawable(R.drawable.back_start_end);
                Drawable drawable_a = resources.getDrawable(R.drawable.back_start_end);
                ll_blood_fat.setBackgroundDrawable(drawable);
                head_view.setBackgroundDrawable(drawable_a);
            } else if (healthStatus.equals("WARNING")) {
                tv_blood_fat_name.setText("血脂异常");
                tv_fat_tx.setText("您的血脂异常");
                tv_fat_tx.setTextColor(Color.parseColor("#F96B21"));
                Resources resources = this.getResources();
                Drawable drawable = resources.getDrawable(R.drawable.back_start_end_yj);
                Drawable drawable_a = resources.getDrawable(R.drawable.back_start_end_yj);
                ll_blood_fat.setBackgroundDrawable(drawable);
                head_view.setBackgroundDrawable(drawable_a);
            }

            tv_total_value.setText(result.getData().getElements().get(0).getTotalCholesterol() + "");
            tv_triglyceride.setText(result.getData().getElements().get(0).getTriglyceride() + "");
            tv_topLipo.setText(result.getData().getElements().get(0).getTopLipo() + "");
            tv_lowLipo.setText(result.getData().getElements().get(0).getLowLipo() + "");
            tv_fat_time.setText(TimeToTime.stampToDate(result.getData().getElements().get(0).getCreateTime() + ""));
            tv_blood_fat_introduce.setText(result.getData().getElements().get(0).getSuggestion());
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
