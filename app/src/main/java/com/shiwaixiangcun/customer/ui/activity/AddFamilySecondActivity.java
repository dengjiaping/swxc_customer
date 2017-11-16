package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.ContextSession;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.RefreshTokenUtil;
import com.shiwaixiangcun.customer.utils.TimeCount;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Administrator
 */
public class AddFamilySecondActivity extends BaseActivity implements View.OnClickListener {

    String relationship;
    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.btn_submit_open)
    Button mBtnSubmitOpen;
    @BindView(R.id.edt_phone)
    EditText mEdtPhone;
    @BindView(R.id.edt_get_psw)
    EditText mEdtGetPsw;
    @BindView(R.id.tv_get_verification)
    TextView mTvGetVerification;
    @BindView(R.id.iv_submit_expression)
    ImageView mIvSubmitExpression;
    @BindView(R.id.tv_submit_succsse)
    TextView mTvSubmitSuccsse;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.rl_success_submit)
    RelativeLayout mRlSuccessSubmit;
    @BindView(R.id.activity_resident_certification)
    RelativeLayout mActivityResidentCertification;
    String token;
    String refreshToken;
    private TimeCount time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family_second);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        relationship = extras.getString("relationship");
        initViewAndEvent();
    }

    private void initViewAndEvent() {
        initToken();

        time = new TimeCount(60000, 1000, mTvGetVerification);
        mBackLeft.setOnClickListener(this);
        mBtnSubmitOpen.setOnClickListener(this);
        mTvGetVerification.setOnClickListener(this);

    }

    private void initToken() {
        token = ContextSession.getTokenString();
        refreshToken = ContextSession.getRefreshToken();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initToken();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_left:
                finish();
                break;
            case R.id.tv_get_verification:
                if (!Utils.isNotEmpty(mEdtPhone.getText().toString().trim())) {
                    showToastShort("请输入家人手机号");
                    return;
                }
                time.start();
                sendGetVerificationHttp();
                break;
            case R.id.btn_submit_open:
                if (!Utils.isNotEmpty(mEdtPhone.getText().toString().trim())) {
                    showToastShort("请输入家人手机号");
                    return;
                } else if (!Utils.isNotEmpty(mEdtGetPsw.getText().toString().trim())) {
                    showToastShort("请输入验证码");
                    return;
                }
                sendLoginHttp();
                break;
            default:
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void sendGetVerificationHttp() {
        Log.e(BUG_TAG, mEdtPhone.getText().toString());
        OkGo.<String>get(GlobalAPI.getPhone)
                .params("access_token", token)
                .params("phone", mEdtPhone.getText().toString().trim())
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(BUG_TAG, "onSuccess");
                        ResponseEntity entity = JsonUtil.fromJson(response.body(), ResponseEntity.class);
                        if (entity == null) {
                            return;

                        }
                        switch (entity.getResponseCode()) {
                            case 1001:
                                showToastShort("获取成功");
                                break;
                            case 1018:
                                RefreshTokenUtil.sendIntDataInvatation(mContext, refreshToken);
                                break;
                            default:
                                showToastShort("获取验证码失败");
                                break;

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        showToastShort("获取验证码失败");
                        Log.e(BUG_TAG, "error");
                    }
                });

    }

    /**
     * 上传数据
     */
    private void sendLoginHttp() {

        OkGo.<String>post(GlobalAPI.addFamily)
                .params("access_token", token)
                .params("code", mEdtGetPsw.getText().toString().trim())
                .params("phone", mEdtPhone.getText().toString().trim())
                .params("relationship", relationship)
                .isSpliceUrl(true)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        ResponseEntity responseEntity = JsonUtil.fromJson(response.body(), ResponseEntity.class);
                        switch (responseEntity.getResponseCode()) {
                            case 1001:
                                showToastShort("添加家人成功");
//                                readyGo(FamilyActivity.class);
                                finish();
                                break;
                            case 1016:
                                showToastShort("验证码错误");
                                break;
                            default:
                                showToastShort(responseEntity.getMessage());
                                break;
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });


    }
}
