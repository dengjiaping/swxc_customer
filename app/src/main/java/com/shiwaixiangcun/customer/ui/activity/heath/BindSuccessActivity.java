package com.shiwaixiangcun.customer.ui.activity.heath;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Administrator
 *         绑定成功页面
 */
public class BindSuccessActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.btn_add_number)
    Button mBtnAddNumber;
    @BindView(R.id.tv_ignore)
    TextView mTvIgnore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_success);
        ButterKnife.bind(this);
        initViewAndEvent();
        mTvIgnore.setOnClickListener(this);
        mBtnAddNumber.setOnClickListener(this);
    }

    private void initViewAndEvent() {
        mTvPageName.setText(R.string.bind_success);
        mBackLeft.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_number:

                readyGoThenKill(AddFamilyNumberActivity.class);
                break;
            case R.id.tv_ignore:
                readyGoThenKill(WatchInfoActivity.class);
                finish();
                break;
            default:
                break;
        }
    }
}
