package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.utils.ResUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.WheelView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 添加家人页面
 *
 * @author Administrator
 */
public class AddFamilyActivity extends BaseActivity implements View.OnClickListener, WheelView.OnValueChangeListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.tv_top_right)
    TextView mTvTopRight;
    @BindView(R.id.iv_share_right)
    ImageView mIvShareRight;
    @BindView(R.id.iv_sao_right)
    ImageView mIvSaoRight;
    @BindView(R.id.ll_image_right)
    LinearLayout mLlImageRight;
    @BindView(R.id.top_bar_write)
    RelativeLayout mTopBarWrite;
    @BindView(R.id.include)
    LinearLayout mInclude;
    @BindView(R.id.WheelView_first)
    WheelView mWheelViewDialog;
    @BindView(R.id.btn_to_other)
    Button mBtnToOther;
    @BindView(R.id.activity_resident_certification)
    RelativeLayout mActivityResidentCertification;

    String[] str_wheel = {"配偶", "父亲", "母亲", "爷爷", "奶奶", "岳父", "岳母", "子女", "外公", "外婆"};
    String[] strRelationShip = {"SPOUSE", "FATHER", "MOTHER", "GRANDPA", "GRANDMA", "FATHERINLAW", "MOTHERINLAW", "CHILDRE", "GRANDFATHER", "GRANDMOTHER"};
    String relation = strRelationShip[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family_actvity);
        ButterKnife.bind(this);
        initViewAndEvent();
    }

    private void initViewAndEvent() {
        mBackLeft.setOnClickListener(this);
        mBtnToOther.setOnClickListener(this);
        mWheelViewDialog.setOnValueChangedListener(this);
        mWheelViewDialog.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
        mWheelViewDialog.setWrapSelectorWheel(false);
        mWheelViewDialog.setDividerColor(ResUtil.getColor(R.color.word_little_grad));
        mWheelViewDialog.setSelectedTextColor(ResUtil.getColor(R.color.word_black));
        mWheelViewDialog.setNormalTextColor(ResUtil.getColor(R.color.word_little_grad));
        mWheelViewDialog.refreshByNewDisplayedValues(str_wheel);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_left:
                finish();
                break;
            case R.id.btn_to_other:
                Bundle bundle = new Bundle();
                bundle.putString("relationship", relation);
                readyGo(AddFamilySecondActivity.class, bundle);
                finish();
                break;
            default:
                break;
        }

    }

    @Override
    public void onValueChange(WheelView picker, int oldVal, int newVal) {
        switch (picker.getId()) {
            case R.id.WheelView_first:
                relation = strRelationShip[newVal];
                break;
            default:
                break;

        }

    }
}
