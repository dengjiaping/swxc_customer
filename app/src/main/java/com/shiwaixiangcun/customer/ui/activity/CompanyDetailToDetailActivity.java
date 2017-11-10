package com.shiwaixiangcun.customer.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.ImageDetailAdapter;
import com.shiwaixiangcun.customer.model.RecoratingDetailBean;
import com.shiwaixiangcun.customer.ui.dialog.DialogLoginOut;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.util.List;

public class CompanyDetailToDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private ChangeLightImageView back_left;
    private List<RecoratingDetailBean.DataBean.DecoratePlanDtosBean> serlist;
    private ListView lv_image_detail;
    private int id;
    private Button btn_phone;
    private String phone;
    private TextView tv_title_compay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail_to_detail);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        Intent intent = getIntent();
        String idDetail = intent.getStringExtra("idDetail");
        id = Integer.parseInt(idDetail);
        phone = intent.getStringExtra("phone");
        serlist = (List<RecoratingDetailBean.DataBean.DecoratePlanDtosBean>) intent.getSerializableExtra("serlist");
        Log.i("tttttrrr",serlist.get(0).getPlanName());
        layoutView();
        initData();
    }

    private void layoutView() {
        back_left = findViewById(R.id.back_left);
        lv_image_detail = findViewById(R.id.lv_image_detail);


        View image_head_view = LayoutInflater.from(this).inflate(R.layout.image_head_detial, null);
        tv_title_compay = image_head_view.findViewById(R.id.tv_title_compay);
        lv_image_detail.addHeaderView(image_head_view);

        View image_footer_view = LayoutInflater.from(this).inflate(R.layout.iamge_footer_detail, null);
        btn_phone = image_footer_view.findViewById(R.id.btn_phone);
        lv_image_detail.addFooterView(image_footer_view);
    }

    private void initData() {
        List<RecoratingDetailBean.DataBean.DecoratePlanDtosBean.DesignChartsBean> designCharts = serlist.get(id).getDesignCharts();
        if (serlist != null && serlist.size() != 0){
            ImageDetailAdapter imageDetailAdapter = new ImageDetailAdapter(designCharts,this);
            lv_image_detail.setAdapter(imageDetailAdapter);
        }
        String planName = serlist.get(id).getPlanName();

        tv_title_compay.setText(planName);

        back_left.setOnClickListener(this);
        btn_phone.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.back_left:
                finish();
                break;
            case R.id.btn_phone:
                showLoginoutDialog();
                break;
        }
    }


    private void showLoginoutDialog() {
        final DialogLoginOut dialogLoginOut = new DialogLoginOut(CompanyDetailToDetailActivity.this, R.layout.item_dialog_call_phone);
        dialogLoginOut.setTitle("是否要拨打此电话？");
        dialogLoginOut.setMessage(phone);
//        dialogLoginOut.setColor();
        dialogLoginOut.setYesOnclickListener("是", new DialogLoginOut.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                dialogLoginOut.dismiss();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                if (ActivityCompat.checkSelfPermission(CompanyDetailToDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            }
        });

        dialogLoginOut.setNoOnclickListener("否", new DialogLoginOut.onNoOnclickListener() {
            @Override
            public void onNoClick() {

                dialogLoginOut.dismiss();
            }
        });
        dialogLoginOut.show();
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
