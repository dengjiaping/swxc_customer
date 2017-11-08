package com.shiwaixiangcun.customer.ui.activity.heath;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.shiwaixiangcun.customer.model.FamilyNumberBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.ui.dialog.DialogLoginOut;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.StringUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 编辑用户信息
 *
 * @author Administrator
 */
public class ManageNumberActivity extends BaseActivity implements View.OnClickListener {
    FamilyNumberBean.SosPhoneDtosBean data;
    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.edt_name)
    EditText mEdtName;
    @BindView(R.id.edt_phone)
    EditText mEdtPhone;
    @BindView(R.id.lLayout_body)
    LinearLayout mLLayoutBody;
    @BindView(R.id.cb_default)
    CheckBox mCbDefault;
    @BindView(R.id.tv_top_right)
    TextView mTvTopRight;
    @BindView(R.id.btn_delete_number)
    Button mBtnDeleteNumber;
    private String strName;
    private String strNumber;
    private boolean isSOS;

    private String strToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_number);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            data = extras.getParcelable("familyInfo");
        }
        initViewAndEvent();

    }

    private void initViewAndEvent() {
        mEdtName.setText(data.getName());
        mEdtPhone.setText(data.getPhone());
        mCbDefault.setChecked(data.isDialFlag());
        strToken = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.TOKEN, "");
        mTvPageName.setText("修改亲情号码");
        mTvTopRight.setText(R.string.save);
        mTvTopRight.setVisibility(View.VISIBLE);
        mTvTopRight.setOnClickListener(this);
        mBackLeft.setOnClickListener(this);
        mBtnDeleteNumber.setOnClickListener(this);

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
                    modifyFamilyNumber();
                }
                break;
            case R.id.back_left:
                finish();
                break;

            case R.id.btn_delete_number:
                final DialogLoginOut deleteDialog = new DialogLoginOut(mContext, R.layout.item_dialog_loginout);

                deleteDialog.setTitle("是否删除亲情号？");
                deleteDialog.setMessage("");
                deleteDialog.setYesOnclickListener("删除", new DialogLoginOut.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        deleteNumber();
                    }
                });
                deleteDialog.setNoOnclickListener("取消", new DialogLoginOut.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {

                        deleteDialog.dismiss();
                    }
                });
                deleteDialog.show();
            default:
                break;
        }
    }

    /**
     * 删除亲情号码
     */
    private void deleteNumber() {
        OkGo.<String>delete(GlobalAPI.deleteNumber)
                .params("access_token", strToken)
                .params("sosId", data.getId())
                .isSpliceUrl(true)
                .execute(new StringDialogCallBack(this) {
                    @Override
                    public void onSuccess(Response<String> response) {

                        ResponseEntity responseEntity = JsonUtil.fromJson(response.body(), ResponseEntity.class);
                        if (responseEntity == null) {
                            return;
                        } else {
                            switch (responseEntity.getResponseCode()) {
                                case 1001:
                                    showToastShort("删除亲情号码成功");
                                    finish();
                                    break;
                                default:
                                    showToastShort("删除亲情号码失败");
                                    break;
                            }
                        }

                    }
                });

    }

    /**
     * 编辑亲情号码
     */
    private void modifyFamilyNumber() {
        OkGo.<String>put(GlobalAPI.modifyFamilyNumber)
                .params("access_token", strToken)
                .params("name", strName)
                .params("id", data.getId())
                .params("phone", strNumber)
                .params("dialFlag", isSOS)
                .isSpliceUrl(true)
                .execute(new StringDialogCallBack(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        ResponseEntity responseEntity = JsonUtil.fromJson(response.body(), ResponseEntity.class);
                        if (responseEntity == null) {
                            return;
                        } else {
                            switch (responseEntity.getResponseCode()) {
                                case 1001:
                                    showToastShort("修改亲情号码成功");

                                    finish();

                                    break;
                                default:
                                    showToastShort("修改亲情号码失败");
                                    break;
                            }
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                        showToastShort("修改亲情号码失败");

                    }
                });
    }
}
