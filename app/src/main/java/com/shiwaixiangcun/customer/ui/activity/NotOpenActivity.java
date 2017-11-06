package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotOpenActivity extends BaseActivity {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.iv_nodata)
    ImageView mIvNodata;
    @BindView(R.id.tv_nodata)
    TextView mTvNodata;

    private String strMessage;
    private int image;
    private String strName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_open);
        ButterKnife.bind(this);
        initData();
        initViewAndEvent();
    }

    private void initData() {
        Bundle extras = getIntent().getExtras();
        strMessage = extras.getString("message");
        strName = extras.getString("name");
        image = extras.getInt("image");
    }

    private void initViewAndEvent() {
        mTvPageName.setText(strName);
        Glide.with(mContext).load(image).into(mIvNodata);
        mTvNodata.setText(strMessage);

    }

    @OnClick(R.id.back_left)
    public void onViewClicked() {
        finish();
    }
}
