package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.model.InformationBean;
import com.shiwaixiangcun.customer.presenter.impl.HousetoImpl;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.utils.ShareUtil;
import com.shiwaixiangcun.customer.ui.IHouseToView;

public class HouseRentingActivity extends AppCompatActivity implements View.OnClickListener,IHouseToView{

    private ChangeLightImageView back_left;
    private TextView tv_page_name;
    private RelativeLayout rl_rent;
    private Intent intent = new Intent();
    private RelativeLayout rl_seller_house;
    private RelativeLayout rl_get_rent;
    private RelativeLayout rl_buy_house;
    private int i_pta = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_renting);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        layoutView();
        initData();
    }

    private void layoutView() {
        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        tv_page_name = (TextView) findViewById(R.id.tv_page_name);
        rl_rent = (RelativeLayout) findViewById(R.id.rl_rent);
        rl_seller_house = (RelativeLayout) findViewById(R.id.rl_seller_house);
        rl_get_rent = (RelativeLayout) findViewById(R.id.rl_get_rent);
        rl_buy_house = (RelativeLayout) findViewById(R.id.rl_buy_house);
    }

    private void initData() {
        tv_page_name.setVisibility(View.VISIBLE);
        tv_page_name.setText("房屋租售");




        back_left.setOnClickListener(this);
        rl_rent.setOnClickListener(this);
        rl_seller_house.setOnClickListener(this);
        rl_get_rent.setOnClickListener(this);
        rl_buy_house.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.back_left:
                finish();
                break;
            case R.id.rl_rent:
                if (i_pta == 1){
                    intent.setClass(this,ItoRentActivity.class);
                    startActivity(intent);
                }else if (i_pta == 2){
                    ShareUtil.saveStringToSpParams(this, Common.ISRESIDENT,Common.SIRESIDENT,"torent");
                    intent = new Intent(this, ResidentCertificationActivity.class);
                    startActivityForResult(intent, 1009);
                }else {
                    Toast.makeText(HouseRentingActivity.this,"网络异常，请稍后再试",Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.rl_seller_house:
                if (i_pta == 1){

                    intent.setClass(this,ItoSellerActivity.class);
                    startActivity(intent);
                }else if (i_pta == 2){
                    ShareUtil.saveStringToSpParams(this, Common.ISRESIDENT,Common.SIRESIDENT,"tosell");
                    intent = new Intent(this, ResidentCertificationActivity.class);
                    startActivityForResult(intent, 1009);
                }else {
                    Toast.makeText(HouseRentingActivity.this,"网络异常，请稍后再试",Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.rl_get_rent:
                intent.setClass(this,IgetRentActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_buy_house:
                intent.setClass(this,IgetBuyActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        HousetoImpl houseto = new HousetoImpl(this);
        houseto.setBgaAdpaterAndClick(this);
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }


    @Override
    public void setBgaAdpaterAndClickResult(InformationBean result) {
        if (result != null){
            if (result.getData().isPropertyAuth()){
                i_pta = 1;
            }else {
                i_pta = 2;
            }
        }
    }
}
