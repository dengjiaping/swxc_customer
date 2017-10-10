package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.MyFamilyBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 家人详细信息页面
 */
public class FamilyDetailActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.iv_head_image)
    CircleImageView mIvHeadImage;
    @BindView(R.id.rl_head_image)
    RelativeLayout mRlHeadImage;
    @BindView(R.id.tv_information_name)
    TextView mTvInformationName;
    @BindView(R.id.rl_information_name)
    RelativeLayout mRlInformationName;
    @BindView(R.id.tv_information_nv)
    TextView mTvInformationNv;
    @BindView(R.id.rl_gender)
    RelativeLayout mRlGender;
    @BindView(R.id.tv_information_old)
    TextView mTvInformationOld;
    @BindView(R.id.rl_data_dialog)
    RelativeLayout mRlDataDialog;
    @BindView(R.id.tv_information_phone)
    TextView mTvInformationPhone;
    @BindView(R.id.activity_information)
    LinearLayout mActivityInformation;
    @BindView(R.id.btn_delete_family)
    Button mBtnDeleteFamily;
    private MyFamilyBean.DataBean mMyFamilyBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_detail);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        mMyFamilyBean = extras.getParcelable("relationship");
        initViewAndEvent();
    }

    /**
     * 初始化视图以及事件
     */
    private void initViewAndEvent() {
        mTvPageName.setText(mMyFamilyBean.getRelationship());
        mTvInformationName.setText(mMyFamilyBean.getName());
        mTvInformationNv.setText(mMyFamilyBean.getSex());
        if (mMyFamilyBean.getAge() != null) {
            mTvInformationOld.setText(mMyFamilyBean.getAge());
        }
        mTvInformationPhone.setText(mMyFamilyBean.getPhone());
        ImageDisplayUtil.showImageView(mContext, mMyFamilyBean.getAvatar(), mIvHeadImage);
        mBackLeft.setOnClickListener(this);
        mBtnDeleteFamily.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_left:
                finish();
                break;
            case R.id.btn_delete_family:
                deleteFamily();
                break;
        }
    }

    private void deleteFamily() {

        String strToken = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.TOKEN, "");
        Log.e(BUG_TAG, strToken);
        // TODO: 2017/10/10 验证Token
        OkGo.<String>delete(GlobalAPI.deleteFamily)
                .params("access_token", strToken)
                .params("customerId", mMyFamilyBean.getCustomerId())
                .isSpliceUrl(true)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(BUG_TAG, response.getRawCall().request().toString());
                        String jsonString = response.body();
                        ResponseEntity responseEntity = JsonUtil.fromJson(jsonString, ResponseEntity.class);
                        if (null == responseEntity) {
                            return;
                        }
                        switch (responseEntity.getResponseCode()) {
                            case 1001:
                                showToastShort("删除家人成功");
                                finish();
                                break;
                            default:
                                showToastShort("删除家人失败");
                                break;
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e(BUG_TAG, "onerror");
                    }
                });


    }
}
