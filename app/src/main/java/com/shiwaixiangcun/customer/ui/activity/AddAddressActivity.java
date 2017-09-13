package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 添加地址Activity
 */
public class AddAddressActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.edt_name)
    EditText mEdtName;
    @BindView(R.id.edt_phone)
    EditText mEdtPhone;
    @BindView(R.id.edt_address)
    EditText mEdtAddress;
    @BindView(R.id.lLayout_body)
    LinearLayout mLLayoutBody;
    @BindView(R.id.tv_top_right)
    TextView mTvTopRight;
    @BindView(R.id.ll_image_right)
    LinearLayout mLlImageRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initEvent() {
        mTvTopRight.setOnClickListener(this);
        mBackLeft.setOnClickListener(this);
    }

    private void initView() {
        mTvPageName.setText("添加收获地址");
        mTvTopRight.setText("保存");
        mTvTopRight.setVisibility(View.VISIBLE);
        mLlImageRight.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_top_right:
                break;
            case R.id.leftTop:
                break;
        }
    }
}
