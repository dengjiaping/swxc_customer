package com.shiwaixiangcun.customer.ui.activity.heath;


import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.http.StringDialogCallBack;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.StringUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Administrator
 *         添加亲情号码
 */
public class AddFamilyNumberActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.tv_top_right)
    TextView mTvTopRight;
    @BindView(R.id.edt_name)
    EditText mEdtName;
    @BindView(R.id.edt_phone)
    EditText mEdtPhone;
    @BindView(R.id.lLayout_body)
    LinearLayout mLLayoutBody;
    @BindView(R.id.cb_default)
    CheckBox mCbDefault;

    private String strName;
    private String strNumber;
    private boolean isSOS;

    private String strToken;

    private boolean isKill = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family_number);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            isKill = extras.getBoolean("isKiss");
        }
        initViewAndEvent();
    }

    private void initViewAndEvent() {

        strToken = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.TOKEN, "");
        mTvPageName.setText(R.string.add_family_name);
        mTvTopRight.setText(R.string.save);
        mTvTopRight.setVisibility(View.VISIBLE);
        mTvTopRight.setOnClickListener(this);
        mBackLeft.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_top_right:
                strName = mEdtName.getText().toString().trim();
                strNumber = mEdtPhone.getText().toString().trim();
                isSOS = mCbDefault.isChecked();
                if (StringUtil.isEmpty(strName)) {
                    showToastShort("姓名不能为空");
                } else if (StringUtil.isEmpty(strNumber)) {
                    showToastShort("电话号码不能为空");
                } else {
                    addFamilyNumber();
                }
                break;
            case R.id.back_left:
                finish();
                break;
            default:
                break;
        }
    }

    private void addFamilyNumber() {
        OkGo.<String>post(GlobalAPI.addFamilyNumber)
                .params("access_token", strToken)
                .params("name", strName)
                .params("phone", strNumber)
                .params("dialFlag", isSOS)

                .execute(new StringDialogCallBack(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        ResponseEntity responseEntity = JsonUtil.fromJson(response.body(), ResponseEntity.class);
                        if (responseEntity == null) {
                            return;
                        } else {
                            switch (responseEntity.getResponseCode()) {
                                case 1001:
                                    showToastShort("添加亲情号码成功");
                                    if (isKill) {
                                        readyGoThenKill(WatchInfoActivity.class);
                                    } else {
                                        finish();
                                    }
                                    break;
                                default:
                                    showToastShort("添加亲情号码失败");
                                    break;
                            }
                        }

                    }
                });
    }
}
