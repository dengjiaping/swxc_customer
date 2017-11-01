package com.shiwaixiangcun.customer.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
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

/**
 * @author Administrator
 */
public class SurroundDetailActivity extends AppCompatActivity implements View.OnClickListener, ISurroundDetailView, ScrollView.OnScrollChangeListener {

    List<MerchDetailBean.DataBean.CertificateBean> list_certificate = new ArrayList<>();
    private ChangeLightImageView backLeft;
    private MyGridView gvSurroundDetail;
    private List<String> listGvSurroundDetail = new ArrayList<>();
    private LinearLayout idGallery;
    private List<String> listImageZz = new ArrayList<>();
    private ScrollView svSurroundDetail;
    private TextView tvPageName;
    private RelativeLayout rlBottomPhone;
    private TextView tvPhoneTop;
    private TextView tvPhoneNumber;
    private TextView tvPhoneBottom;
    private String merchId = "";
    private ImageView ivMerchImage;
    private TextView tvMerchDetailTitle;
    private TextView tvDetailFuture;
    private TextView tvMerchDetailLocation;
    private TextView tvMerchDetailIntroduce;
    private List<MerchDetailBean.DataBean.AtlasBean> list_merch = new ArrayList<>();
    private SurroundDetailAdapter surroundDetailAdapter;
    private ImageView ivLocationImage;
    private ImageView ivPhoneImage;
    private TextView tvFoodTitle;
    private LinearLayout llFoodTitle;
    private LinearLayout llIntroduceTitle;
    private RelativeLayout rlCheckMore;
    private TextView tvQualification;
    private ImageView ivFoodIcon;
    private MerchDetailBean.DataBean.BasicInformationBean basicInformation;
    private TextView tvIntroduceA;
    private TextView tvIntroduceB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surround_detail);
        initLayout();
        initData();
    }

    private void initLayout() {
        backLeft = findViewById(R.id.back_left);
        gvSurroundDetail = findViewById(R.id.gv_surround_detail);
        idGallery = findViewById(R.id.id_gallery);
        svSurroundDetail = findViewById(R.id.sv_surround_detail);
        tvPageName = findViewById(R.id.tv_page_name);
        rlBottomPhone = findViewById(R.id.rl_bottom_phone);
        tvPhoneTop = findViewById(R.id.tv_phone_top);
        tvPhoneNumber = findViewById(R.id.tv_phone_number);
        tvPhoneBottom = findViewById(R.id.tv_phone_bottom);
        ivMerchImage = findViewById(R.id.iv_merch_image);
        tvMerchDetailTitle = findViewById(R.id.tv_merch_detail_title);
        tvDetailFuture = findViewById(R.id.tv_detail_future);
        tvMerchDetailLocation = findViewById(R.id.tv_merch_detail_location);
        tvMerchDetailIntroduce = findViewById(R.id.tv_merch_detail_introduce);
        ivLocationImage = findViewById(R.id.iv_location_image);
        ivPhoneImage = findViewById(R.id.iv_phone_image);
        tvFoodTitle = findViewById(R.id.tv_food_title);
        llFoodTitle = findViewById(R.id.ll_food_title);
        llIntroduceTitle = findViewById(R.id.ll_introduce_title);
        rlCheckMore = findViewById(R.id.rl_check_more);
        tvQualification = findViewById(R.id.tv_qualification);
        ivFoodIcon = findViewById(R.id.iv_food_icon);
        tvIntroduceA = findViewById(R.id.tv_introduce_a);
        tvIntroduceB = findViewById(R.id.tv_introduce_b);


        svSurroundDetail.smoothScrollTo(0, 20);
        svSurroundDetail.setFocusable(true);


    }

    private void initData() {

        Intent intent = getIntent();
        merchId = intent.getStringExtra("merchId");
        SurroundDetailImpl surroundDetail = new SurroundDetailImpl(this);
        surroundDetail.setBgaAdpaterAndClick(this, merchId);
        for (int i = 0; i < 5; i++) {
            listGvSurroundDetail.add("ggg" + i);
        }
        surroundDetailAdapter = new SurroundDetailAdapter(list_merch, this);
        gvSurroundDetail.setAdapter(surroundDetailAdapter);

        for (int i = 0; i < 6; i++) {
            listImageZz.add("ffff" + i);
        }


        backLeft.setOnClickListener(this);

        gvSurroundDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                List<MerchDetailBean.DataBean.AtlasBean.AtlasListBean> atlasList = list_merch.get(i).getAtlasList();
                String title = list_merch.get(i).getTitle();
                ArrayList<String> imageList = new ArrayList<>();
                for (MerchDetailBean.DataBean.AtlasBean.AtlasListBean item : atlasList) {
                    imageList.add(item.getAccessUrl());
                }
                Intent intent = new Intent();
                intent.setClass(SurroundDetailActivity.this, ImageGalleryActivity.class);
                intent.putExtra("imageList", imageList);
                intent.putExtra("imageTitle", title);
                startActivity(intent);
            }
        });

        svSurroundDetail.setOnScrollChangeListener(this);
        tvPhoneTop.setOnClickListener(this);
        tvPhoneBottom.setOnClickListener(this);
        rlCheckMore.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.back_left:
                finish();
                break;
            case R.id.tv_phone_top:
                if (Utils.isNotEmpty(tvPhoneNumber.getText().toString().trim())) {
                    CallPhoneDialog(tvPhoneNumber.getText().toString().trim());
                } else {
                    Toast.makeText(SurroundDetailActivity.this, "没有商家电话", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.tv_phone_bottom:
                if (Utils.isNotEmpty(tvPhoneNumber.getText().toString().trim())) {
                    CallPhoneDialog(tvPhoneNumber.getText().toString().trim());
                } else {
                    Toast.makeText(SurroundDetailActivity.this, "没有商家电话", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.rl_check_more:
                Intent intent = new Intent(SurroundDetailActivity.this, ViewMoreActivity.class);
                intent.putExtra("articleId", merchId);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void setBgaAdpaterAndClickResult(MerchDetailBean result) {
        ivLocationImage.setVisibility(View.VISIBLE);
        ivPhoneImage.setVisibility(View.VISIBLE);
        tvPhoneTop.setVisibility(View.VISIBLE);
        llFoodTitle.setVisibility(View.VISIBLE);
        llIntroduceTitle.setVisibility(View.VISIBLE);
        tvMerchDetailIntroduce.setVisibility(View.VISIBLE);
        rlCheckMore.setVisibility(View.VISIBLE);
        tvQualification.setVisibility(View.VISIBLE);

        list_merch.clear();
        basicInformation = result.getData().getBasicInformation();
        if (Utils.isNotEmpty(basicInformation.getCover())) {
            Glide.with(SurroundDetailActivity.this).load(basicInformation.getCover()).into(ivMerchImage);
        }

        tvMerchDetailTitle.setText(basicInformation.getName());
        tvDetailFuture.setText(basicInformation.getFeature());
        tvMerchDetailLocation.setText(basicInformation.getPosition());
        tvPhoneNumber.setText(basicInformation.getPhone());

        if (Utils.isNotEmpty(basicInformation.getDescription())) {
            llIntroduceTitle.setVisibility(View.VISIBLE);
            rlCheckMore.setVisibility(View.VISIBLE);
            tvMerchDetailIntroduce.setText(basicInformation.getDescription());
        } else {
            llIntroduceTitle.setVisibility(View.GONE);
            rlCheckMore.setVisibility(View.GONE);
            tvMerchDetailIntroduce.setText("");
        }


        tvPhoneBottom.setText(basicInformation.getCallButton());
        tvFoodTitle.setText(basicInformation.getRecommendStr());
        tvPhoneTop.setText(basicInformation.getCallButton());
        if (Utils.isNotEmpty(basicInformation.getRecommendIcon())) {
            Glide.with(SurroundDetailActivity.this).load(basicInformation.getRecommendIcon()).into(ivFoodIcon);
        }


        List<MerchDetailBean.DataBean.AtlasBean> atlas = result.getData().getAtlas();
        for (int i = 0; i < atlas.size(); i++) {

            list_merch.add(atlas.get(i));

        }
        surroundDetailAdapter.notifyDataSetChanged();

        if (atlas.size() != 0) {
            llFoodTitle.setVisibility(View.VISIBLE);
            tvIntroduceA.setVisibility(View.VISIBLE);
        } else {
            llFoodTitle.setVisibility(View.GONE);
            tvIntroduceA.setVisibility(View.GONE);
        }


        List<MerchDetailBean.DataBean.CertificateBean> certificate = result.getData().getCertificate();
        if (certificate.size() > 0) {
            tvQualification.setVisibility(View.VISIBLE);
            getlt(certificate);
        } else {
            tvQualification.setVisibility(View.GONE);
        }
        boolean notEmpty = Utils.isNotEmpty(basicInformation.getDescription());
        if (notEmpty == false && certificate.size() == 0) {
            tvIntroduceB.setVisibility(View.GONE);
        } else {
            tvIntroduceB.setVisibility(View.VISIBLE);
        }


    }


    private void getlt(final List<MerchDetailBean.DataBean.CertificateBean> certificates) {


        for (int i = 0; i < certificates.size(); i++) {

            View view = LayoutInflater.from(this).inflate(R.layout.item_surround_detail_image, idGallery, false);
            ImageView iv_certificates = view.findViewById(R.id.iv_certificates);
            if (Utils.isNotEmpty(certificates.get(i).getThumbImageURL())) {

                ImageDisplayUtil.showImageView(this, certificates.get(i).getAccessUrl(), iv_certificates);
            }
            iv_certificates.setBackgroundColor(Color.parseColor("#332D3230"));
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SurroundDetailActivity.this, BigIamgeMerchanActivity.class);
                    intent.putExtra("bigimagelist", (Serializable) certificates);
                    intent.putExtra("serid", finalI);
                    startActivity(intent);
                }
            });
            idGallery.addView(view);
        }
    }

    @Override
    public void onScrollChange(View view, int i, int i1, int i2, int i3) {
        if (i1 >= 370) {
            if (i3 != 1197 && i3 != 0) {
                tvPageName.setText(basicInformation.getName());
            }

        } else {
            tvPageName.setText("");
        }

        if (i1 >= 950) {
            rlBottomPhone.setVisibility(View.VISIBLE);
        } else {
            rlBottomPhone.setVisibility(View.GONE);
        }
    }


    private void CallPhoneDialog(final String phone) {
        final DialogLoginOut dialogLoginOut = new DialogLoginOut(SurroundDetailActivity.this, R.layout.item_dialog_call_phone);
        dialogLoginOut.setTitle("是否要拨打此电话？");
        dialogLoginOut.setMessage(phone);
        dialogLoginOut.setYesOnclickListener("是", new DialogLoginOut.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                dialogLoginOut.dismiss();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                if (ActivityCompat.checkSelfPermission(SurroundDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

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
