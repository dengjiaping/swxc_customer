package com.shiwaixiangcun.customer.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.SurroundDetailAdapter;
import com.shiwaixiangcun.customer.model.MerchDetailBean;
import com.shiwaixiangcun.customer.presenter.impl.SurroundDetailImpl;
import com.shiwaixiangcun.customer.ui.ISurroundDetailView;
import com.shiwaixiangcun.customer.ui.dialog.DialogLoginOut;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.MyGridView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SurroundDetailActivity extends AppCompatActivity implements View.OnClickListener, ISurroundDetailView, ScrollView.OnScrollChangeListener {

    List<MerchDetailBean.DataBean.CertificateBean> list_certificate = new ArrayList<>();
    private ChangeLightImageView back_left;
    private MyGridView gv_surround_detail;
    private List<String> list_gv_surround_detail = new ArrayList<>();
    private LinearLayout id_gallery;
    private List<String> list_image_zz = new ArrayList<>();
    private ScrollView sv_surround_detail;
    private TextView tv_page_name;
    private RelativeLayout rl_bottom_phone;
    private TextView tv_phone_top;
    private TextView tv_phone_number;
    private TextView tv_phone_bottom;
    private String merchId = "";
    private ImageView iv_merch_image;
    private TextView tv_merch_detail_title;
    private TextView tv_detail_future;
    private TextView tv_merch_detail_location;
    private TextView tv_merch_detail_introduce;
    private List<MerchDetailBean.DataBean.AtlasBean> list_merch = new ArrayList<>();
    private SurroundDetailAdapter surroundDetailAdapter;
    private ImageView iv_location_image;
    private ImageView iv_phone_image;
    private TextView tv_food_title;
    private LinearLayout ll_food_title;
    private LinearLayout ll_introduce_title;
    private RelativeLayout rl_check_more;
    private TextView tv_qualification;
    private ImageView iv_food_icon;
    private MerchDetailBean.DataBean.BasicInformationBean basicInformation;
    private TextView tv_introduce_a;
    private TextView tv_introduce_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surround_detail);
        initLayout();
        initData();
    }

    private void initLayout() {
        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        gv_surround_detail = (MyGridView) findViewById(R.id.gv_surround_detail);
        id_gallery = (LinearLayout) findViewById(R.id.id_gallery);
        sv_surround_detail = (ScrollView) findViewById(R.id.sv_surround_detail);
        tv_page_name = (TextView) findViewById(R.id.tv_page_name);
        rl_bottom_phone = (RelativeLayout) findViewById(R.id.rl_bottom_phone);
        tv_phone_top = (TextView) findViewById(R.id.tv_phone_top);
        tv_phone_number = (TextView) findViewById(R.id.tv_phone_number);
        tv_phone_bottom = (TextView) findViewById(R.id.tv_phone_bottom);
        iv_merch_image = (ImageView) findViewById(R.id.iv_merch_image);
        tv_merch_detail_title = (TextView) findViewById(R.id.tv_merch_detail_title);
        tv_detail_future = (TextView) findViewById(R.id.tv_detail_future);
        tv_merch_detail_location = (TextView) findViewById(R.id.tv_merch_detail_location);
        tv_merch_detail_introduce = (TextView) findViewById(R.id.tv_merch_detail_introduce);
        iv_location_image = (ImageView) findViewById(R.id.iv_location_image);
        iv_phone_image = (ImageView) findViewById(R.id.iv_phone_image);
        tv_food_title = (TextView) findViewById(R.id.tv_food_title);
        ll_food_title = (LinearLayout) findViewById(R.id.ll_food_title);
        ll_introduce_title = (LinearLayout) findViewById(R.id.ll_introduce_title);
        rl_check_more = (RelativeLayout) findViewById(R.id.rl_check_more);
        tv_qualification = (TextView) findViewById(R.id.tv_qualification);
        iv_food_icon = (ImageView) findViewById(R.id.iv_food_icon);
        tv_introduce_a = (TextView) findViewById(R.id.tv_introduce_a);
        tv_introduce_b = (TextView) findViewById(R.id.tv_introduce_b);


        sv_surround_detail.smoothScrollTo(0, 20);
        sv_surround_detail.setFocusable(true);


    }

    private void initData() {

        Intent intent = getIntent();
        merchId = intent.getStringExtra("merchId");
        SurroundDetailImpl surroundDetail = new SurroundDetailImpl(this);
        surroundDetail.setBgaAdpaterAndClick(this, merchId);
        for (int i = 0; i < 5; i++) {
            list_gv_surround_detail.add("ggg" + i);
        }
        surroundDetailAdapter = new SurroundDetailAdapter(list_merch, this);
        gv_surround_detail.setAdapter(surroundDetailAdapter);

        for (int i = 0; i < 6; i++) {
            list_image_zz.add("ffff" + i);
        }




        back_left.setOnClickListener(this);

        gv_surround_detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("oooooooooo", i + "-------" + l);
//                Intent intent = new Intent(SurroundDetailActivity.this, MerchantDetailToImagesActivity.class);
//                intent.putExtra("serlist", (Serializable) list_merch);
//                intent.putExtra("idDetail", i + "");
////                intent.putExtra("phone",phone);
//                startActivity(intent);

                Log.i("vvvviiia", "----------aaa" + i);
                List<MerchDetailBean.DataBean.AtlasBean.AtlasListBean> atlasList = list_merch.get(i).getAtlasList();
                String title = list_merch.get(i).getTitle();
                Intent intent = new Intent(SurroundDetailActivity.this,BigIamgeMerchatImageActivity.class);
                intent.putExtra("bigimagelist",(Serializable) atlasList);
                intent.putExtra("titleImage",title);
                startActivity(intent);
            }
        });

        sv_surround_detail.setOnScrollChangeListener(this);
        tv_phone_top.setOnClickListener(this);
        tv_phone_bottom.setOnClickListener(this);
        rl_check_more.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.back_left:
                finish();
                break;
            case R.id.tv_phone_top:
                if (Utils.isNotEmpty(tv_phone_number.getText().toString().trim())) {
                    CallPhoneDialog(tv_phone_number.getText().toString().trim());
                } else {
                    Toast.makeText(SurroundDetailActivity.this, "没有商家电话", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.tv_phone_bottom:
                if (Utils.isNotEmpty(tv_phone_number.getText().toString().trim())) {
                    CallPhoneDialog(tv_phone_number.getText().toString().trim());
                } else {
                    Toast.makeText(SurroundDetailActivity.this, "没有商家电话", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.rl_check_more:
                Intent intent = new Intent(SurroundDetailActivity.this,ViewMoreActivity.class);
                intent.putExtra("articleId",merchId);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void setBgaAdpaterAndClickResult(MerchDetailBean result) {
        iv_location_image.setVisibility(View.VISIBLE);
        iv_phone_image.setVisibility(View.VISIBLE);
        tv_phone_top.setVisibility(View.VISIBLE);
        ll_food_title.setVisibility(View.VISIBLE);
        ll_introduce_title.setVisibility(View.VISIBLE);
        tv_merch_detail_introduce.setVisibility(View.VISIBLE);
        rl_check_more.setVisibility(View.VISIBLE);
        tv_qualification.setVisibility(View.VISIBLE);

        list_merch.clear();
        basicInformation = result.getData().getBasicInformation();
        if (Utils.isNotEmpty(basicInformation.getCover())) {
            Glide.with(SurroundDetailActivity.this).load(basicInformation.getCover()).into(iv_merch_image);
        }

        tv_merch_detail_title.setText(basicInformation.getName());
        tv_detail_future.setText(basicInformation.getFeature());
        tv_merch_detail_location.setText(basicInformation.getPosition());
        tv_phone_number.setText(basicInformation.getPhone());

        if (Utils.isNotEmpty(basicInformation.getDescription())){
            ll_introduce_title.setVisibility(View.VISIBLE);
            rl_check_more.setVisibility(View.VISIBLE);
            tv_merch_detail_introduce.setText(basicInformation.getDescription());
        }else {
            ll_introduce_title.setVisibility(View.GONE);
            rl_check_more.setVisibility(View.GONE);
            tv_merch_detail_introduce.setText("");
        }


        tv_phone_bottom.setText(basicInformation.getCallButton());
        tv_food_title.setText(basicInformation.getRecommendStr());
        tv_phone_top.setText(basicInformation.getCallButton());
        if (Utils.isNotEmpty(basicInformation.getRecommendIcon())){
            Glide.with(SurroundDetailActivity.this).load(basicInformation.getRecommendIcon()).into(iv_food_icon);
        }


        List<MerchDetailBean.DataBean.AtlasBean> atlas = result.getData().getAtlas();
        for (int i = 0; i < atlas.size(); i++) {

            list_merch.add(atlas.get(i));

        }
        surroundDetailAdapter.notifyDataSetChanged();

        if (atlas.size() != 0){
            ll_food_title.setVisibility(View.VISIBLE);
            tv_introduce_a.setVisibility(View.VISIBLE);
        }else {
            ll_food_title.setVisibility(View.GONE);
            tv_introduce_a.setVisibility(View.GONE);
        }


        List<MerchDetailBean.DataBean.CertificateBean> certificate = result.getData().getCertificate();
        if (certificate.size() > 0){
            tv_qualification.setVisibility(View.VISIBLE);
            getlt(certificate);
        }else {
            tv_qualification.setVisibility(View.GONE);
        }
        boolean notEmpty = Utils.isNotEmpty(basicInformation.getDescription());
        if (notEmpty == false && certificate.size() == 0){
            tv_introduce_b.setVisibility(View.GONE);
        }else {
            tv_introduce_b.setVisibility(View.VISIBLE);
        }


    }


    private void getlt(final List<MerchDetailBean.DataBean.CertificateBean> certificates) {


        for (int i = 0; i < certificates.size(); i++) {

            View view = LayoutInflater.from(this).inflate(R.layout.item_surround_detail_image, id_gallery, false);
            ImageView iv_certificates = (ImageView) view.findViewById(R.id.iv_certificates);
            if (Utils.isNotEmpty(certificates.get(i).getThumbImageURL())) {

                ImageDisplayUtil.showImageView(this, certificates.get(i).getAccessUrl(), iv_certificates);
            }
            iv_certificates.setBackgroundColor(Color.parseColor("#332D3230"));
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    String url_bit_image = certificates.get(finalI).getAccessUrl();
                    Log.i("vvvviiia", "----------aaa" + finalI);
                    Intent intent = new Intent(SurroundDetailActivity.this,BigIamgeMerchanActivity.class);
                    intent.putExtra("bigimagelist",(Serializable) certificates);
                    intent.putExtra("serid",finalI);
                    startActivity(intent);
                }
            });
            id_gallery.addView(view);
        }
    }

    @Override
    public void onScrollChange(View view, int i, int i1, int i2, int i3) {
        Log.e("bbbbbbbmmb", i + "---" + i1 + "---" + i2 + "----" + i3);
        if (i1 >= 370) {
            if (i3 != 1197 && i3 != 0){
                tv_page_name.setText(basicInformation.getName());
            }

        } else {
            tv_page_name.setText("");
        }

        if (i1 >= 950) {
            rl_bottom_phone.setVisibility(View.VISIBLE);
        } else {
            rl_bottom_phone.setVisibility(View.GONE);
        }
    }


    private void CallPhoneDialog(final String phone) {
        final DialogLoginOut dialogLoginOut = new DialogLoginOut(SurroundDetailActivity.this, R.layout.item_dialog_call_phone);
        dialogLoginOut.setTitle("是否要拨打此电话？");
        dialogLoginOut.setMessage(phone);
//        dialogLoginOut.setColor();
        dialogLoginOut.setYesOnclickListener("是", new DialogLoginOut.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                dialogLoginOut.dismiss();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                if (ActivityCompat.checkSelfPermission(SurroundDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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

        dialogLoginOut.setNoOnclickListener("否", new DialogLoginOut.onNoOnclickListener() {
            @Override
            public void onNoClick() {

                dialogLoginOut.dismiss();
            }
        });
        dialogLoginOut.show();
    }
}
