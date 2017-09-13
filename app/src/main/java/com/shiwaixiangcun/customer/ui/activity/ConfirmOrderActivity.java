package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfirmOrderActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.llayout_add_address)
    LinearLayout mLlayoutAddAddress;
    @BindView(R.id.iv_cover_sku)
    ImageView mIvCoverSku;
    @BindView(R.id.tv_title_order)
    TextView mTvTitleOrder;
    @BindView(R.id.tv_desc_order)
    TextView mTvDescOrder;
    @BindView(R.id.tv_price_price)
    TextView mTvPricePrice;
    @BindView(R.id.tv_number_order)
    TextView mTvNumberOrder;
    @BindView(R.id.iv_reduce)
    ImageView mIvReduce;
    @BindView(R.id.tv_number)
    TextView mTvNumber;
    @BindView(R.id.iv_add)
    ImageView mIvAdd;
    @BindView(R.id.tv_information)
    TextView mTvInformation;
    @BindView(R.id.edt_message)
    EditText mEdtMessage;
    @BindView(R.id.tv_total)
    TextView mTvTotal;
    @BindView(R.id.btn_commit)
    Button mBtnCommit;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_user_phone)
    TextView mTvUserPhone;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.rLayout_hasAddress)
    RelativeLayout mRLayoutHasAddress;
    @BindView(R.id.rLayout_noAddress)
    RelativeLayout mRLayoutNoAddress;

    private boolean hasAddress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initEvent() {
        mBackLeft.setOnClickListener(this);
        mLlayoutAddAddress.setOnClickListener(this);
        mIvAdd.setOnClickListener(this);
        mIvReduce.setOnClickListener(this);
        mBtnCommit.setOnClickListener(this);
        mRLayoutHasAddress.setOnClickListener(this);
    }

    private void initView() {
        mTvPageName.setText("确认订单");
        if (hasAddress) {
            mRLayoutHasAddress.setVisibility(View.VISIBLE);
            mLlayoutAddAddress.setVisibility(View.GONE);
        } else {
            mRLayoutHasAddress.setVisibility(View.GONE);
            mLlayoutAddAddress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_left:
                finish();
                break;
            case R.id.llayout_add_address:
                readyGo(AddAddressActivity.class);
                break;

        }
    }
}
