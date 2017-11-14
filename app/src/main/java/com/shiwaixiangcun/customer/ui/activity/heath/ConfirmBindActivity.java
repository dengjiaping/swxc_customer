package com.shiwaixiangcun.customer.ui.activity.heath;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.ContextSession;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.http.StringDialogCallBack;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.NoFastClickUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 确定绑定页面
 *
 * @author Administrator
 */
public class ConfirmBindActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.btn_bind)
    Button mBtnBind;
    @BindView(R.id.tv_imei)
    TextView mTvImei;

    private String imei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_bind);
        ButterKnife.bind(this);
        imei = getIntent().getExtras().getString("imei");
        initViewAndEvent();

    }

    @SuppressLint("SetTextI18n")
    private void initViewAndEvent() {
        mTvPageName.setText(R.string.confirm_bind);
        mBackLeft.setOnClickListener(this);
        mBtnBind.setOnClickListener(this);
        mTvImei.setText("IMEI号：" + imei);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_left:
                finish();
                break;
            case R.id.btn_bind:
                if (NoFastClickUtil.isFastClick()) {
                    Toast.makeText(mContext, "点击太快，休息一会", Toast.LENGTH_SHORT).show();
                } else {
                    validateIMEI(imei);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 验证IMEI号
     *
     * @param result
     */
    private void validateIMEI(final String result) {

        // TODO: 2017/11/14
        OkGo.<String>post(GlobalAPI.watch_bind)

                .params("access_token", ContextSession.getTokenString())
                .params("imei", result)
                .execute(new StringDialogCallBack(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        ResponseEntity responseEntity = JsonUtil.fromJson(response.body(), ResponseEntity.class);
                        if (responseEntity == null) {
                            return;
                        }
                        switch (responseEntity.getResponseCode()) {
                            case 1001:
                                AppSharePreferenceMgr.put(mContext, GlobalConfig.IS_BIND_WATCH, true);
                                showToastShort("绑定成功");
                                readyGoThenKill(BindSuccessActivity.class);
                                break;
                            case 1002:
                                showDialog(responseEntity.getMessage());
                                break;
                            default:
                                showDialog("绑定失败，该智能手表已经被其他用户绑定");
                                break;
                        }

                    }
                });


    }

    public void showDialog(String content) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(content);

        builder.setPositiveButton("扫描新的条形码", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                readyGo(BindWatchActivity.class);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
