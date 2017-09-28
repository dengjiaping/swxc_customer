package com.shiwaixiangcun.customer.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.BloodPressureBean;
import com.shiwaixiangcun.customer.model.BloodSugarBean;
import com.shiwaixiangcun.customer.model.HealthAllActivity;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.PageBean;
import com.shiwaixiangcun.customer.model.PressureFatBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.model.WeightBean;
import com.shiwaixiangcun.customer.presenter.impl.HealthOkImpl;
import com.shiwaixiangcun.customer.ui.IHealthOkView;
import com.shiwaixiangcun.customer.ui.activity.heath.BloodFatActivity;
import com.shiwaixiangcun.customer.ui.activity.heath.BloodPressureActivity;
import com.shiwaixiangcun.customer.ui.activity.heath.BloodSugarActivity;
import com.shiwaixiangcun.customer.ui.activity.heath.HeartRateActivity;
import com.shiwaixiangcun.customer.ui.activity.heath.WeightActivity;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.LoginOutUtil;
import com.shiwaixiangcun.customer.utils.RefreshTockenUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.utils.TimeToTime;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.pullableview.MyListener;
import com.shiwaixiangcun.customer.widget.pullableview.PullToRefreshLayout;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class HealthOkActivity extends AppCompatActivity implements View.OnClickListener,IHealthOkView{

    private RelativeLayout rl_blood_pressure;
    private ChangeLightImageView back_left;
    private RelativeLayout rl_health_heart_rate;
    private RelativeLayout rl_blood_sugar;
    private RelativeLayout rl_weight_person;
    private LinearLayout ll_blood_fat;
    private TextView tv_blood_pressure_data;
    private TextView tv_blood_pressure_ok_time;
    private TextView tv_heart_ok_time;
    private TextView tv_heart_ok_data;
    private TextView tv_blood_sugar_ok_data;
    private TextView tv_blood_sugar_ok_time;
    private TextView tv_weight_ok_data;
    private TextView tv_weight_ok_time;
    private TextView tv_ok_totalCholesterol;
    private TextView tv_ok_triglyceride;
    private TextView tv_ok_topLipo;
    private TextView tv_ok_lowLipo;
    private TextView tv_blood_fat_ok_time;
    private ImageView iv_health_status;
    private TextView tv_center_health;
    private TextView tv_bottom_health;
    private RelativeLayout head_view;
    private HealthOkImpl healthOk;
    private List<WeightBean> elements_weight;
    private List<BloodPressureBean> data_pressure;
    //    private List<BloodSugarBean.DataBean.ElementsBean> elements_sugar;
//    private List<PressureFatBean.DataBean.ElementsBean> elements_fat;
    private TextView tv_botton_not_all;
    private LinearLayout ll_top_health;
    private LinearLayout ll_low_health;
    private TextView tv_top_datacomplete;
    private TextView tv_low_datacomplete;
    private List<BloodPressureBean> data_pressure_week;
    private List<BloodPressureBean> data_pressure_month;
    private List<BloodPressureBean> data_pressure_year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_ok);
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
                healthOk.setBgaAdpaterAndClick(HealthOkActivity.this);
            }
        });


        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        rl_blood_pressure = (RelativeLayout) findViewById(R.id.rl_blood_pressure);
        rl_health_heart_rate = (RelativeLayout) findViewById(R.id.rl_health_heart_rate);
        rl_blood_sugar = (RelativeLayout) findViewById(R.id.rl_blood_sugar);
        rl_weight_person = (RelativeLayout) findViewById(R.id.rl_weight_person);
        ll_blood_fat = (LinearLayout) findViewById(R.id.ll_blood_fat);
        tv_blood_pressure_data = (TextView) findViewById(R.id.tv_blood_pressure_data);
        tv_blood_pressure_ok_time = (TextView) findViewById(R.id.tv_blood_pressure_ok_time);
        tv_heart_ok_data = (TextView) findViewById(R.id.tv_heart_ok_data);
        tv_heart_ok_time = (TextView) findViewById(R.id.tv_heart_ok_time);
        tv_blood_sugar_ok_data = (TextView) findViewById(R.id.tv_blood_sugar_ok_data);
        tv_blood_sugar_ok_time = (TextView) findViewById(R.id.tv_blood_sugar_ok_time);
        tv_weight_ok_data = (TextView) findViewById(R.id.tv_weight_ok_data);
        tv_weight_ok_time = (TextView) findViewById(R.id.tv_weight_ok_time);
        tv_ok_totalCholesterol = (TextView) findViewById(R.id.tv_ok_totalCholesterol);
        tv_ok_triglyceride = (TextView) findViewById(R.id.tv_ok_triglyceride);
        tv_ok_topLipo = (TextView) findViewById(R.id.tv_ok_topLipo);
        tv_ok_lowLipo = (TextView) findViewById(R.id.tv_ok_lowLipo);
        tv_blood_fat_ok_time = (TextView) findViewById(R.id.tv_blood_fat_ok_time);
        iv_health_status = (ImageView) findViewById(R.id.iv_health_status);
        tv_center_health = (TextView) findViewById(R.id.tv_center_health);
        tv_bottom_health = (TextView) findViewById(R.id.tv_bottom_health);

        tv_botton_not_all = (TextView) findViewById(R.id.tv_botton_not_all);
        ll_top_health = (LinearLayout) findViewById(R.id.ll_top_health);
        ll_low_health = (LinearLayout) findViewById(R.id.ll_low_health);

        tv_top_datacomplete = (TextView) findViewById(R.id.tv_top_datacomplete);
        tv_low_datacomplete = (TextView) findViewById(R.id.tv_low_datacomplete);


    }

    private void initData() {
        head_view.setBackgroundColor(Color.parseColor("#FFFFFF"));
        healthOk = new HealthOkImpl(this,"");
        healthOk.setBgaAdpaterAndClick(this);
        back_left.setOnClickListener(this);
        rl_blood_pressure.setOnClickListener(this);
        rl_health_heart_rate.setOnClickListener(this);
        rl_blood_sugar.setOnClickListener(this);
        rl_weight_person.setOnClickListener(this);
        ll_blood_fat.setOnClickListener(this);

        sendWeightHttp(this);
        sendBloodPressureHttp(this);
        sendBloodSugarHttp(this);
        sendBloodFatHttp(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.back_left:
                finish();
                break;
            case R.id.rl_blood_pressure:
                Intent intent = new Intent(this,BloodPressureActivity.class);
                intent.putExtra("list_pressure",(Serializable) data_pressure);
                intent.putExtra("list_pressure_week",(Serializable) data_pressure_week);
                intent.putExtra("list_pressure_month",(Serializable) data_pressure_month);
                intent.putExtra("list_pressure_year",(Serializable) data_pressure_year);
                startActivity(intent);
                break;
            case R.id.rl_health_heart_rate:
                Intent intent_heart = new Intent(this, HeartRateActivity.class);
                startActivity(intent_heart);
                break;
            case R.id.rl_blood_sugar:
                Intent intent_boold = new Intent(this,BloodSugarActivity.class);
//                intent_boold.putExtra("list_sugar",(Serializable)elements_sugar);
                startActivity(intent_boold);
                break;
            case R.id.rl_weight_person:
                Intent intent_weight = new Intent(this,WeightActivity.class);
                intent_weight.putExtra("list_weight",(Serializable) elements_weight);
                startActivity(intent_weight);
                break;
            case R.id.ll_blood_fat:
                Intent intent_fat = new Intent(this,BloodFatActivity.class);
//                intent_fat.putExtra("list_fat",(Serializable) elements_fat);
                startActivity(intent_fat);
                break;
        }
    }

    @Override
    public void setBgaAdpaterAndClickResult(HealthAllActivity result) {
        if (result != null){
            Integer customerId = result.getData().getCustomerId();
            String totalStatus = result.getData().getTotalStatus();
            if (customerId == null){
                ll_top_health.setVisibility(View.VISIBLE);
                tv_top_datacomplete.setText(result.getData().getSuggestion());
            }else {
                ll_top_health.setVisibility(View.GONE);
                if (totalStatus == null){
                    ll_low_health.setVisibility(View.VISIBLE);
                    tv_low_datacomplete.setText(result.getData().getSuggestion());
                }else {
                    ll_low_health.setVisibility(View.GONE);
                    if (totalStatus.equals("NORMAL")){
                        iv_health_status.setImageResource(R.mipmap.heart_green);
                        tv_center_health.setText("非常好");
//                tv_bottom_health.setText("数据均正常，请继续保持");
                    }else if (totalStatus.equals("WARNING")){
                        iv_health_status.setImageResource(R.mipmap.health_jx);
                        tv_center_health.setText("警告");
//                tv_bottom_health.setText("健康数据异常，请注意调理");
                    }else if (totalStatus.equals("DANGER")){
                        iv_health_status.setImageResource(R.mipmap.health_wx);
                        tv_center_health.setText("紧急");
//                tv_bottom_health.setText("数据严重异常，请立即就医");
                    }
                }
            }





            String pressureStatus = result.getData().getPressureStatus();
            if (Utils.isNotEmpty(pressureStatus)){
                rl_blood_pressure.setVisibility(View.VISIBLE);
                if (pressureStatus.equals("NORMAL")){
                    Resources resources = this.getResources();
                    Drawable drawable = resources.getDrawable(R.drawable.acr_blue_curcle_bg);
                    rl_blood_pressure.setBackgroundDrawable(drawable);
                }else if (pressureStatus.equals("WARNING")){
                    Resources resources = this.getResources();
                    Drawable drawable = resources.getDrawable(R.drawable.acr_blue_curcle_bg_jx);
                    rl_blood_pressure.setBackgroundDrawable(drawable);
                }else if (pressureStatus.equals("DANGER")){
                    Resources resources = this.getResources();
                    Drawable drawable = resources.getDrawable(R.drawable.acr_blue_curcle_bg_wx);
                    rl_blood_pressure.setBackgroundDrawable(drawable);
                }
            }else {
                rl_blood_pressure.setVisibility(View.GONE);
            }


            String xinlvStatus = result.getData().getXinlvStatus();
            if (Utils.isNotEmpty(xinlvStatus)){
                rl_health_heart_rate.setVisibility(View.VISIBLE);
                if (xinlvStatus.equals("NORMAL")){
                    Resources resources = this.getResources();
                    Drawable drawable = resources.getDrawable(R.drawable.acr_blue_curcle_bg);
                    rl_health_heart_rate.setBackgroundDrawable(drawable);
                }else if (xinlvStatus.equals("WARNING")){
                    Resources resources = this.getResources();
                    Drawable drawable = resources.getDrawable(R.drawable.acr_blue_curcle_bg_jx);
                    rl_health_heart_rate.setBackgroundDrawable(drawable);
                }else if (xinlvStatus.equals("DANGER")){
                    Resources resources = this.getResources();
                    Drawable drawable = resources.getDrawable(R.drawable.acr_blue_curcle_bg_wx);
                    rl_health_heart_rate.setBackgroundDrawable(drawable);
                }
            }else {
                rl_health_heart_rate.setVisibility(View.GONE);
            }


            String sugarStatus = result.getData().getSugarStatus();
            if (Utils.isNotEmpty(sugarStatus)){
                rl_blood_sugar.setVisibility(View.VISIBLE);
                if (sugarStatus.equals("NORMAL")){
                    Resources resources = this.getResources();
                    Drawable drawable = resources.getDrawable(R.drawable.acr_blue_curcle_bg);
                    rl_blood_sugar.setBackgroundDrawable(drawable);
                }else if (sugarStatus.equals("WARNING")){
                    Resources resources = this.getResources();
                    Drawable drawable = resources.getDrawable(R.drawable.acr_blue_curcle_bg_jx);
                    rl_blood_sugar.setBackgroundDrawable(drawable);
                }else if (sugarStatus.equals("DANGER")){
                    Resources resources = this.getResources();
                    Drawable drawable = resources.getDrawable(R.drawable.acr_blue_curcle_bg_wx);
                    rl_blood_sugar.setBackgroundDrawable(drawable);
                }
            }else {
                rl_blood_sugar.setVisibility(View.GONE);
            }

            String bmiStatus = result.getData().getBmiStatus();
            if (Utils.isNotEmpty(bmiStatus)){
                rl_weight_person.setVisibility(View.VISIBLE);
                if (bmiStatus.equals("NORMAL")){
                    Resources resources = this.getResources();
                    Drawable drawable = resources.getDrawable(R.drawable.acr_blue_curcle_bg);
                    rl_weight_person.setBackgroundDrawable(drawable);
                }else if (bmiStatus.equals("WARNING")){
                    Resources resources = this.getResources();
                    Drawable drawable = resources.getDrawable(R.drawable.acr_blue_curcle_bg_jx);
                    rl_weight_person.setBackgroundDrawable(drawable);
                }else if (bmiStatus.equals("DANGER")){
                    Resources resources = this.getResources();
                    Drawable drawable = resources.getDrawable(R.drawable.acr_blue_curcle_bg_wx);
                    rl_weight_person.setBackgroundDrawable(drawable);
                }
            }else {
                rl_weight_person.setVisibility(View.GONE);
            }

            String bloodStatus = result.getData().getBloodStatus();
            if (Utils.isNotEmpty(bloodStatus)){
                ll_blood_fat.setVisibility(View.VISIBLE);
                if (bloodStatus.equals("NORMAL")){
                    Resources resources = this.getResources();
                    Drawable drawable = resources.getDrawable(R.drawable.acr_blue_curcle_bg);
                    ll_blood_fat.setBackgroundDrawable(drawable);
                }else if (bloodStatus.equals("WARNING")){
                    Resources resources = this.getResources();
                    Drawable drawable = resources.getDrawable(R.drawable.acr_blue_curcle_bg_jx);
                    ll_blood_fat.setBackgroundDrawable(drawable);
                }else if (bloodStatus.equals("DANGER")){
                    Resources resources = this.getResources();
                    Drawable drawable = resources.getDrawable(R.drawable.acr_blue_curcle_bg_wx);
                    ll_blood_fat.setBackgroundDrawable(drawable);
                }
            }else {
                ll_blood_fat.setVisibility(View.GONE);
            }

            if (!Utils.isNotEmpty(pressureStatus) || !Utils.isNotEmpty(xinlvStatus) || !Utils.isNotEmpty(sugarStatus) || !Utils.isNotEmpty(bmiStatus) || !Utils.isNotEmpty(bloodStatus)){
                tv_botton_not_all.setVisibility(View.VISIBLE);
                tv_botton_not_all.setText(result.getData().getDataComplete());
            }else {
                tv_botton_not_all.setVisibility(View.GONE);
            }



            tv_blood_pressure_data.setText(result.getData().getShrinkBlood()+"/"+result.getData().getRelaxationBlood());
            tv_blood_pressure_ok_time.setText(TimeToTime.stampToDate(result.getData().getPressureCreateTime()+""));

            tv_heart_ok_data.setText(result.getData().getHeartRate()+"");
            tv_heart_ok_time.setText(TimeToTime.stampToDate(result.getData().getPressureCreateTime()+""));

            tv_blood_sugar_ok_data.setText(result.getData().getBloodSugar()+"");
            tv_blood_sugar_ok_time.setText(TimeToTime.stampToDate(result.getData().getSugarCreateTime()+""));

            tv_weight_ok_data.setText(result.getData().getWeight()+"");
            tv_weight_ok_time.setText(TimeToTime.stampToDate(result.getData().getBmiCreateTime()+""));

            tv_ok_totalCholesterol.setText(result.getData().getTotalCholesterol()+"");
            tv_ok_triglyceride.setText(result.getData().getTriglyceride()+"");
            tv_ok_topLipo.setText(result.getData().getTopLipo()+"");
            tv_ok_lowLipo.setText(result.getData().getLowLipo()+"");
            tv_blood_fat_ok_time.setText(TimeToTime.stampToDate(result.getData().getBloodCreateTime()+""));
            tv_bottom_health.setText(result.getData().getSuggestion());




        }

    }


    //体重
    private void sendWeightHttp(final Context context) {
        String login_detail = SharePreference.getStringSpParams(context, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());
        hashMap.put("page.pn", 1);
        hashMap.put("page.size", 7);

        Log.i("ddddddaaaa", hashMap.toString() + "-----------" + Common.bmiList);
        HttpRequest.get(Common.bmiList, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---weight", responseJson);
                Type type = new TypeToken<ResponseEntity<PageBean<WeightBean>>>() {
                }.getType();
                ResponseEntity<PageBean<WeightBean>> responseEntity = JsonUtil.fromJson(responseJson, type);
                if (responseEntity.getResponseCode() == 1001) {
                    elements_weight = responseEntity.getData().getElements();
                } else if (responseEntity.getResponseCode() == 1018) {
                    RefreshTockenUtil.sendIntDataInvatation(context, refresh_token);
                } else if (responseEntity.getResponseCode() == 1019) {
                    LoginOutUtil.sendLoginOutUtil(context);
                }
            }

            @Override
            public void onFailed(Exception e) {
                Log.i("oooooo---onFailed---", e.toString());
            }
        });
    }

    //血压
    private void sendBloodPressureHttp(final Context context) {
        String login_detail = SharePreference.getStringSpParams(context, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());
        hashMap.put("dateType", "DAY");
//        hashMap.put("fields", "createTime,healthStatus,id,heartRate,relaxationBlood,shrinkBlood");

        Log.i("dddddd", hashMap.toString() + "-----------" + Common.pressureBlood);
        HttpRequest.get(Common.pressureBlood, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---hhhh", responseJson);
                Type type = new TypeToken<ResponseEntity<List<BloodPressureBean>>>() {
                }.getType();
                ResponseEntity<List<BloodPressureBean>> responseEntity = JsonUtil.fromJson(responseJson, type);

                if (responseEntity.getResponseCode() == 1001) {
                    data_pressure = responseEntity.getData();
                } else if (responseEntity.getResponseCode() == 1018) {
                    RefreshTockenUtil.sendIntDataInvatation(context, refresh_token);
                } else if (responseEntity.getResponseCode() == 1019) {
                    LoginOutUtil.sendLoginOutUtil(context);
                }

            }

            @Override
            public void onFailed(Exception e) {
                Log.i("oooooo---onFailed---", e.toString());
            }
        });
    }



    //血糖页面和列表
    private void sendBloodSugarHttp(final Context context) {
        String login_detail = SharePreference.getStringSpParams(context, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());
        hashMap.put("page.pn", 1);
        hashMap.put("page.size", 7);

        Log.i("dddddd", hashMap.toString() + "-----------" + Common.sugarList);
        HttpRequest.get(Common.sugarList, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---sugar", responseJson);
                BloodSugarBean bloodSugarBean = new Gson().fromJson(responseJson, BloodSugarBean.class);

//                if (bloodSugarBean.getResponseCode() == 1001) {
//                    elements_sugar = bloodSugarBean.getData().getElements();
//                } else if (bloodSugarBean.getResponseCode() == 1018) {
//                    RefreshTockenUtil.sendIntDataInvatation(context, refresh_token);
//                } else if (bloodSugarBean.getResponseCode() == 1019) {
//                    LoginOutUtil.sendLoginOutUtil(context);
//                }
            }

            @Override
            public void onFailed(Exception e) {
                Log.i("oooooo---onFailed---", e.toString());
            }
        });
    }


    //血脂
    private void sendBloodFatHttp(final Context context) {
        String login_detail = SharePreference.getStringSpParams(context, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Log.i("eeeeeettt", login_detail);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(login_detail, type);
        final String refresh_token = responseEntity.getData().getRefresh_token();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("access_token", responseEntity.getData().getAccess_token());
        hashMap.put("page.pn", 1);
        hashMap.put("page.size", 7);

        Log.i("dddddd", hashMap.toString() + "-----------" + Common.bloodFat);
        HttpRequest.get(Common.bloodFat, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.i("oooooo---onSuccess---fat", responseJson);
//                Type type = new TypeToken<ResponseEntity<PageBean<BloodFatBean>>>() {
//                }.getType();
//                ResponseEntity<PageBean<BloodFatBean>> responseEntity = JsonUtil.fromJson(responseJson, type);
                PressureFatBean pressureFatBean = new Gson().fromJson(responseJson, PressureFatBean.class);
//
//                if (pressureFatBean.getResponseCode() == 1001) {
//                    elements_fat = pressureFatBean.getData().getElements();
//                } else if (pressureFatBean.getResponseCode() == 1018) {
//                    RefreshTockenUtil.sendIntDataInvatation(context, refresh_token);
//                } else if (pressureFatBean.getResponseCode() == 1019) {
//                    LoginOutUtil.sendLoginOutUtil(context);
//                }
            }

            @Override
            public void onFailed(Exception e) {
                Log.i("oooooo---onFailed---", e.toString());
            }
        });
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
