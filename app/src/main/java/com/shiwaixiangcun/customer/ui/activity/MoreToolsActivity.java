package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoreToolsActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_tools);
        ButterKnife.bind(this);
        initViewAndEvent();
    }

    private void initViewAndEvent() {
        mTvPageName.setText("全部服务");
        mBackLeft.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_left:
                finish();
                break;

        }
    }
}
