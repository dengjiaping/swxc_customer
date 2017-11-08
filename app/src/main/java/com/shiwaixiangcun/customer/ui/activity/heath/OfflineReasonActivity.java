package com.shiwaixiangcun.customer.ui.activity.heath;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Administrator
 *         离线原因
 */
public class OfflineReasonActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_reason);
        ButterKnife.bind(this);
        initEventAndView();
    }


    private void initEventAndView() {

        mTvPageName.setText(R.string.off_line_reason);
        mBackLeft.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_left:
                finish();
                break;
            default:
                break;
        }
    }
}
