package com.shiwaixiangcun.customer.ui.activity;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.HeatateListAdapter;
import com.shiwaixiangcun.customer.model.HeartateBean;
import com.shiwaixiangcun.customer.model.PageBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.presenter.impl.HeartateImpl;
import com.shiwaixiangcun.customer.ui.IHeartateView;
import com.shiwaixiangcun.customer.utils.TimeToTime;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.MyListView;
import com.shiwaixiangcun.customer.widget.pullableview.MyListener;
import com.shiwaixiangcun.customer.widget.pullableview.PullToRefreshLayout;

import java.util.List;

public class HeartateActivity extends AppCompatActivity implements View.OnClickListener,IHeartateView{

    private ChangeLightImageView back_left;
    private LinearLayout ll_top_heart;
    private TextView tv_heart_normol;
    private TextView tv_heartate_data;
    private TextView tv_heart_creat_time;
    private MyListView lv_detail_heartate;
    private ScrollView sv_heartate;
    private TextView tv_heartate_introduce;
    private RelativeLayout head_view;
    private HeartateImpl heartate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heartate);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        layoutView();
        initData();
    }

    private void layoutView() {
        head_view = (RelativeLayout) findViewById(R.id.head_view);
        MyListener myListener = new MyListener();
        PullToRefreshLayout refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        refresh_view.setOnRefreshListener(myListener);
        myListener.setRefreshListener(new MyListener.onRefreshListener() {
            @Override
            public void refreshScence(boolean isnot) {
                heartate.setBgaAdpaterAndClick(HeartateActivity.this);
            }
        });

        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        ll_top_heart = (LinearLayout) findViewById(R.id.ll_top_heart);
        tv_heart_normol = (TextView) findViewById(R.id.tv_heart_normol);
        tv_heartate_data = (TextView) findViewById(R.id.tv_heartate_data);
        tv_heart_creat_time = (TextView) findViewById(R.id.tv_heart_creat_time);
        lv_detail_heartate = (MyListView) findViewById(R.id.lv_detail_heartate);
        sv_heartate = (ScrollView) findViewById(R.id.sv_heartate);
        tv_heartate_introduce = (TextView) findViewById(R.id.tv_heartate_introduce);
    }

    private void initData() {
        sv_heartate.smoothScrollTo(0,20);
        lv_detail_heartate.setFocusable(false);

        heartate = new HeartateImpl(this,"");
        heartate.setBgaAdpaterAndClick(this);
        back_left.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.back_left:
                finish();
                break;
        }
    }

    @Override
    public void setBgaAdpaterAndClickResult(ResponseEntity<PageBean<HeartateBean>> result) {
        if (result.getData().getElements() != null && result.getData().getElements().size() != 0){

            HeartateBean heartateBean = result.getData().getElements().get(0);
            Log.i("aaaaaaaaa",heartateBean.getHealthStatus()+"");
//            if (heartateBean.getHealthStatus().equals("NORMAL")){
                Resources resources = this.getResources();
                Drawable drawable = resources.getDrawable(R.drawable.back_start_end);
                Drawable drawable_a = resources.getDrawable(R.drawable.back_start_end);
                ll_top_heart.setBackgroundDrawable(drawable);
                head_view.setBackgroundDrawable(drawable_a);

//            }else if (heartateBean.getHealthStatus().equals("WARNING")){
//                Resources resources = this.getResources();
//                Drawable drawable = resources.getDrawable(R.drawable.back_start_end_yj);
//                Drawable drawable_a = resources.getDrawable(R.drawable.back_start_end_yj);
//                ll_top_heart.setBackgroundDrawable(drawable);
//                head_view.setBackgroundDrawable(drawable_a);
//            }

//            String statusEnum = result.getData().getElements().get(0).getStatusEnum();
//            if (statusEnum.equals("Zhengchang")){
//                tv_heart_normol.setText("心率");
//            }else if (statusEnum.equals("Piangao")){
//                tv_heart_normol.setText("心率偏高");
//            }else if (statusEnum.equals("Paindi")){
//                tv_heart_normol.setText("心率偏低");
//            }

//            result.getData().getElements().get(0).get
            tv_heartate_data.setText(heartateBean.getHeartRate()+"");
            tv_heart_creat_time.setText(TimeToTime.stampToDate(heartateBean.getCreateTime()+""));
            tv_heartate_introduce.setText(result.getData().getElements().get(0).getSuggestion());

            List<HeartateBean> elements_heartate = result.getData().getElements();
            HeatateListAdapter heatateListAdapter = new HeatateListAdapter(elements_heartate,this);
            lv_detail_heartate.setAdapter(heatateListAdapter);

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
