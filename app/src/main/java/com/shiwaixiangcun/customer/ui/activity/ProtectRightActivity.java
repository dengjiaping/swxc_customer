package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.http.StringDialogCallBack;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.ui.dialog.DialogStatus;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.RefreshTokenUtil;
import com.shiwaixiangcun.customer.utils.StringUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProtectRightActivity extends BaseActivity implements View.OnClickListener {


    private static final int REQUEST_CODE_CHOOSE = 23;
    String strToken;
    String strContent;
    String strRefreshToken;
    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.tv_top_right)
    TextView mTvTopRight;
    @BindView(R.id.iv_choose_image)
    ImageView mIvChooseImage;
    @BindView(R.id.hsv_images)
    HorizontalScrollView mHsvImages;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;


    @BindView(R.id.edt_content)
    EditText mEdtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protect_right);
        ButterKnife.bind(this);
        initViewAndEvent();
    }

    private void initViewAndEvent() {

        mTvTopRight.setText("维权记录");
        mTvPageName.setText("消费维权");
        strToken = (String) AppSharePreferenceMgr.get(this, GlobalConfig.TOKEN, "");
        strRefreshToken = (String) AppSharePreferenceMgr.get(this, GlobalConfig.TOKEN, "");
        mTvTopRight.setVisibility(View.VISIBLE);
        mTvTopRight.setOnClickListener(this);
        mBackLeft.setOnClickListener(this);
        mBtnSubmit.setOnClickListener(this);
        mIvChooseImage.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_left:
                finish();
                break;
            case R.id.tv_top_right:
                readyGo(RightsRecordActivity.class);
                break;
            case R.id.btn_submit:
                strContent = mEdtContent.getText().toString().trim();
                if (StringUtil.isEmpty(strContent)) {
                    showToastShort("请输入你遇到的问题");
                } else {
                    if (getCurrentFocus() != null) {
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(getCurrentFocus()
                                                .getWindowToken(),
                                        InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    submitInfo(strContent);
                }
                break;
            case R.id.iv_choose_image:
                showToastShort("点击选择图片");


                // TODO: 2017/10/16
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {


//            List<Uri> mSelected = Matisse.obtainResult(data);
        }
    }

    private void submitInfo(String strContent) {
        OkGo.<String>post(GlobalAPI.right)
                .params("access_token", strToken)
                .params("content", strContent)
                .params("imageIds", "")
                .equals(new StringDialogCallBack(this) {
                    @Override
                    public void onSuccess(Response<String> response) {

                        ResponseEntity responseEntity = JsonUtil.fromJson(response.body(), ResponseEntity.class);
                        if (responseEntity == null) {
                            return;

                        }
                        switch (responseEntity.getResponseCode()) {
                            case 1001:
                                DialogStatus dialogStatus = new DialogStatus(mContext);
                                dialogStatus.setListener(new DialogStatus.onCallBackListener() {
                                    @Override
                                    public void closeBtn(DialogStatus dialog) {
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void confirmBtn(DialogStatus dialog) {
                                        dialog.dismiss();

                                    }
                                });
                                dialogStatus.show();
                                break;
                            case 1018:
                                RefreshTokenUtil.sendIntDataInvatation(mContext, strRefreshToken);
                                break;
                            default:

                                showToastShort("提交失败");
                                Log.e(BUG_TAG, "加载失败");
                                break;
                        }
                    }
                });

    }
}
