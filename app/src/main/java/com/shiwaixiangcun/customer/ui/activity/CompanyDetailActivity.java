package com.shiwaixiangcun.customer.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.CompanyDetilListAdapter;
import com.shiwaixiangcun.customer.event.EventCenter;
import com.shiwaixiangcun.customer.event.SimpleEvent;
import com.shiwaixiangcun.customer.model.RecoratingDetailBean;
import com.shiwaixiangcun.customer.ui.dialog.DialogLoginOut;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.MyListView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 找装修详情页面
 */

public class CompanyDetailActivity extends AppCompatActivity implements View.OnClickListener, ListView.OnItemClickListener {

    private ChangeLightImageView back_left;
    private MyListView lv_detail_cunpany;
    private List<String> list_company = new ArrayList<>();

    private LinearLayout mGallery;
    private LinearLayout ll_onclick_open;
    private TextView tv_detail;
    private ScrollView sv_company;
    private String recoratingId;
    private ImageView iv_logo_top;
    private TextView tv_title_detail;
    private TextView tv_instruct;
    private List<RecoratingDetailBean.DataBean.DecoratePlanDtosBean> decoratePlanDtos;
    private TextView tv_call_phone;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        Intent intent = getIntent();
        recoratingId = intent.getStringExtra("recoratingId");

        layoutView();
        initData();

        EventCenter.getInstance().register(this);
    }

    private void layoutView() {

        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        lv_detail_cunpany = (MyListView) findViewById(R.id.lv_detail_cunpany);
        ll_onclick_open = (LinearLayout) findViewById(R.id.ll_onclick_open);
        tv_detail = (TextView) findViewById(R.id.tv_detail);
        sv_company = (ScrollView) findViewById(R.id.sv_company);
        iv_logo_top = (ImageView) findViewById(R.id.iv_logo_top);
        tv_title_detail = (TextView) findViewById(R.id.tv_title_detail);
        tv_instruct = (TextView) findViewById(R.id.tv_instruct);
        tv_call_phone = (TextView) findViewById(R.id.tv_call_phone);
        sv_company.smoothScrollTo(0, 20);
        sv_company.setFocusable(true);
        lv_detail_cunpany.setFocusable(false);
        back_left.setOnClickListener(this);
        lv_detail_cunpany.setOnItemClickListener(this);
        ll_onclick_open.setOnClickListener(this);
        tv_call_phone.setOnClickListener(this);
    }

    private void initData() {
//        houseRecoratingDetail = new HouseRecoratingDetailImpl(this, recoratingId);
//        houseRecoratingDetail.RequestData(this);
        OkGo.<String>get(Common.companyDetail)
                .params("id", recoratingId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        RecoratingDetailBean recoratingDetailBean = new Gson().fromJson(response.body(), RecoratingDetailBean.class);
                        if (recoratingDetailBean.getResponseCode() == 1001) {

                            EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_DETAIL_DECORATE, 1, recoratingDetailBean));
                        }
                    }
                });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setInfo(SimpleEvent simpleEvent) {
        if (simpleEvent == null || simpleEvent.mEventType != SimpleEvent.UPDATE_DETAIL_DECORATE) {
            return;
        }
        switch (simpleEvent.mEventValue) {
            case 1:
                RecoratingDetailBean result = (RecoratingDetailBean) simpleEvent.getData();
                setInfo(result);
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
            case R.id.ll_onclick_open:
                ll_onclick_open.setVisibility(View.GONE);
                tv_detail.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_call_phone:
                showRingDialog();
                break;

        }
    }


    private void getlt(final List<RecoratingDetailBean.DataBean.CertificatesBean> certificates) {
        mGallery = (LinearLayout) findViewById(R.id.id_gallery);


        for (int i = 0; i < certificates.size(); i++) {

            View view = LayoutInflater.from(this).inflate(R.layout.item_image, mGallery, false);
            ImageView iv_certificates = (ImageView) view.findViewById(R.id.iv_certificates);
            if (Utils.isNotEmpty(certificates.get(i).getThumbImageURL())) {
                ImageDisplayUtil.showImageView(this, certificates.get(i).getThumbImageURL(), iv_certificates);
            }
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url_bit_image = certificates.get(finalI).getAccessUrl();
                    Intent intent = new Intent(CompanyDetailActivity.this, BigImageActivity.class);
                    intent.putExtra("bigimagelist", (Serializable) certificates);
                    intent.putExtra("serid", finalI);
                    startActivity(intent);
                }
            });
            mGallery.addView(view);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(CompanyDetailActivity.this, CompanyDetailToDetailActivity.class);
        intent.putExtra("serlist", (Serializable) decoratePlanDtos);
        intent.putExtra("idDetail", i + "");
        intent.putExtra("phone", phone);
        startActivity(intent);
    }


    /**
     * 设置信息
     *
     * @param result
     */
    public void setInfo(RecoratingDetailBean result) {
        phone = result.getData().getPhone();
        if (Utils.isNotEmpty(result.getData().getLogo())) {
            ImageDisplayUtil.showImageView(this, result.getData().getLogo(), iv_logo_top);
        }
        tv_title_detail.setText(result.getData().getName());
        tv_detail.setText(result.getData().getIntroduce());
        tv_instruct.setText(result.getData().getSummary());
        List<RecoratingDetailBean.DataBean.CertificatesBean> certificates = result.getData().getCertificates();
        getlt(certificates);
        decoratePlanDtos = result.getData().getDecoratePlanDtos();
        CompanyDetilListAdapter companyDetilListAdapter = new CompanyDetilListAdapter(decoratePlanDtos, this);
        lv_detail_cunpany.setAdapter(companyDetilListAdapter);

    }

    /**
     * 显示拨打电话对话框
     */
    private void showRingDialog() {
        final DialogLoginOut dialogLoginOut = new DialogLoginOut(CompanyDetailActivity.this, R.layout.item_dialog_call_phone);
        dialogLoginOut.setTitle("是否要拨打此电话？");
        dialogLoginOut.setMessage(phone);
        dialogLoginOut.setYesOnclickListener("是", new DialogLoginOut.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                dialogLoginOut.dismiss();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                if (ActivityCompat.checkSelfPermission(CompanyDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

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

    @Override
    protected void onDestroy() {
        EventCenter.getInstance().unregister(this);
        super.onDestroy();
    }
}
package com.shiwaixiangcun.customer.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.CompanyDetilListAdapter;
import com.shiwaixiangcun.customer.model.RecoratingDetailBean;
import com.shiwaixiangcun.customer.presenter.impl.HouseRecoratingDetailImpl;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.MyListView;
import com.shiwaixiangcun.customer.widget.SelfLoginoutDialog;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.ui.IHouseRecoratingDetailView;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CompanyDetailActivity extends AppCompatActivity implements View.OnClickListener, ListView.OnItemClickListener, IHouseRecoratingDetailView {

    private ChangeLightImageView back_left;
    private MyListView lv_detail_cunpany;
    private List<String> list_company = new ArrayList<>();

    private LinearLayout mGallery;
    private LinearLayout ll_onclick_open;
    private TextView tv_detail;
    private ScrollView sv_company;
    private String recoratingId;
    private ImageView iv_logo_top;
    private TextView tv_title_detail;
    private TextView tv_instruct;
    private List<RecoratingDetailBean.DataBean.DecoratePlanDtosBean> decoratePlanDtos;
    private TextView tv_call_phone;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        Intent intent = getIntent();
        recoratingId = intent.getStringExtra("recoratingId");

        layoutView();
        initData();
    }

    private void layoutView() {

        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        lv_detail_cunpany = (MyListView) findViewById(R.id.lv_detail_cunpany);
        ll_onclick_open = (LinearLayout) findViewById(R.id.ll_onclick_open);
        tv_detail = (TextView) findViewById(R.id.tv_detail);
        sv_company = (ScrollView) findViewById(R.id.sv_company);
        iv_logo_top = (ImageView) findViewById(R.id.iv_logo_top);
        tv_title_detail = (TextView) findViewById(R.id.tv_title_detail);
        tv_instruct = (TextView) findViewById(R.id.tv_instruct);
        tv_call_phone = (TextView) findViewById(R.id.tv_call_phone);

    }

    private void initData() {
        HouseRecoratingDetailImpl houseRecoratingDetail = new HouseRecoratingDetailImpl(this, recoratingId);
        houseRecoratingDetail.setBgaAdpaterAndClick(this);

        sv_company.smoothScrollTo(0, 20);
        sv_company.setFocusable(true);
        lv_detail_cunpany.setFocusable(false);
        for (int i = 0; i < 2; i++) {
            list_company.add("1");
        }

        back_left.setOnClickListener(this);
        lv_detail_cunpany.setOnItemClickListener(this);
        ll_onclick_open.setOnClickListener(this);
        tv_call_phone.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.back_left:
                finish();
                break;
            case R.id.ll_onclick_open:
                ll_onclick_open.setVisibility(View.GONE);
                tv_detail.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_call_phone:
//                if (Utils.isNotEmpty(phone)){
//                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                }
                showLoginoutDialog();
                break;

        }
    }


    private void getlt(final List<RecoratingDetailBean.DataBean.CertificatesBean> certificates) {
        mGallery = (LinearLayout) findViewById(R.id.id_gallery);


        for (int i = 0; i < certificates.size(); i++) {

            View view = LayoutInflater.from(this).inflate(R.layout.item_image, mGallery, false);
            ImageView iv_certificates = (ImageView) view.findViewById(R.id.iv_certificates);
            if (Utils.isNotEmpty(certificates.get(i).getThumbImageURL())) {
                Picasso.with(this).load(certificates.get(i).getThumbImageURL()).into(iv_certificates);
            }
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url_bit_image = certificates.get(finalI).getAccessUrl();
                    Log.i("vvvviiia","----------aaa"+ finalI);
                    Intent intent = new Intent(CompanyDetailActivity.this,BigImageActivity.class);
                    intent.putExtra("bigimagelist",(Serializable) certificates);
                    intent.putExtra("serid",finalI);
                    startActivity(intent);
                }
            });
            mGallery.addView(view);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.e("oooooooooo", i + "-------" + l);
        Intent intent = new Intent(CompanyDetailActivity.this, CompanyDetailToDetailActivity.class);
        intent.putExtra("serlist", (Serializable) decoratePlanDtos);
        intent.putExtra("idDetail", i + "");
        intent.putExtra("phone",phone);
        startActivity(intent);
    }


    @Override
    public void setBgaAdpaterAndClickResult(RecoratingDetailBean result) {
        phone = result.getData().getPhone();
        if (Utils.isNotEmpty(result.getData().getLogo())) {
            Picasso.with(this).load(result.getData().getLogo()).into(iv_logo_top);
        }
        tv_title_detail.setText(result.getData().getName());
        tv_detail.setText(result.getData().getIntroduce());
        tv_instruct.setText(result.getData().getSummary());


        List<RecoratingDetailBean.DataBean.CertificatesBean> certificates = result.getData().getCertificates();
        getlt(certificates);

        decoratePlanDtos = result.getData().getDecoratePlanDtos();
        CompanyDetilListAdapter companyDetilListAdapter = new CompanyDetilListAdapter(decoratePlanDtos, this);
        lv_detail_cunpany.setAdapter(companyDetilListAdapter);

    }

    private void showLoginoutDialog() {
        final SelfLoginoutDialog selfLoginoutDialog = new SelfLoginoutDialog(CompanyDetailActivity.this, R.layout.item_dialog_call_phone);
        selfLoginoutDialog.setTitle("是否要拨打此电话？");
        selfLoginoutDialog.setMessage(phone);
//        selfLoginoutDialog.setColor();
        selfLoginoutDialog.setYesOnclickListener("是", new SelfLoginoutDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                selfLoginoutDialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                if (ActivityCompat.checkSelfPermission(CompanyDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
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

        selfLoginoutDialog.setNoOnclickListener("否", new SelfLoginoutDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {

                selfLoginoutDialog.dismiss();
            }
        });
        selfLoginoutDialog.show();
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
