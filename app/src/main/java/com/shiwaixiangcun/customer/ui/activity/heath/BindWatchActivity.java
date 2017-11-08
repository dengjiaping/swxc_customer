package com.shiwaixiangcun.customer.ui.activity.heath;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * 绑定
 *
 * @author Administrator
 */
public class BindWatchActivity extends BaseActivity implements View.OnClickListener, QRCodeView.Delegate {


    private static final int IMEI_LENGTH = 15;
    @BindView(R.id.zxingview)
    ZXingView mZxingview;
    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_watch);
        ButterKnife.bind(this);

        initViewAndEvent();
        initData();
    }

    private void initData() {

        mZxingview.startSpot();

    }

    /**
     *
     */
    private void initViewAndEvent() {
        mTvPageName.setText(R.string.binding_watch);
        mBackLeft.setOnClickListener(this);
        mZxingview.setDelegate(this);

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

    @Override
    protected void onStart() {
        super.onStart();
        mZxingview.startCamera();
        mZxingview.showScanRect();
        mZxingview.startSpot();
    }

    @Override
    protected void onStop() {
        mZxingview.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mZxingview.onDestroy();
        super.onDestroy();
    }


    public void showDialog(String content) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(content);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                mZxingview.startCamera();
                mZxingview.showScanRect();
                mZxingview.startSpot();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();

        if (result == null) {
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString("imei", result);
        Log.e(BUG_TAG, result);
        if (result.length() != IMEI_LENGTH) {
            showDialog("无效条形码，请扫描智能手表设备包装盒上的IMEI条形码!");
        } else {

            readyGoThenKill(ConfirmBindActivity.class, bundle);

        }
        mZxingview.startSpotDelay(3000);

    }


    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeOpenCameraError() {

    }
}
