package com.shiwaixiangcun.customer.ui.activity.heath;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.ContextSession;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.event.EventCenter;
import com.shiwaixiangcun.customer.event.SimpleEvent;
import com.shiwaixiangcun.customer.http.StringDialogCallBack;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.model.WatchInfoBean;
import com.shiwaixiangcun.customer.ui.dialog.DialogLoginOut;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.SwitchButton;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Administrator
 *         <p>
 *         手表信息
 */
public class WatchInfoActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {


    private static String ONLINE = "ONLINE";
    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.iv_bg)
    ImageView mIvBg;
    @BindView(R.id.nested_scrollview)
    NestedScrollView mNestedScrollview;
    @BindView(R.id.tv_watch_stature)
    TextView mTvWatchStature;
    @BindView(R.id.tv_watch_power)
    TextView mTvWatchPower;
    @BindView(R.id.llayout_watch_info)
    LinearLayout mLlayoutWatchInfo;
    @BindView(R.id.rlayout_family_number)
    RelativeLayout mRlayoutFamilyNumber;
    @BindView(R.id.tv_frequency_heart)
    TextView mTvFrequencyHeart;
    @BindView(R.id.rlayout_frequency_heart)
    RelativeLayout mRlayoutFrequencyHeart;
    @BindView(R.id.tv_frequency_location)
    TextView mTvFrequencyLocation;
    @BindView(R.id.rlayout_frequency_location)
    RelativeLayout mRlayoutFrequencyLocation;
    @BindView(R.id.btn_unbind)
    Button mBtnUnbind;


    @BindView(R.id.switch_heart_rate)
    SwitchButton mSwitchHeartRate;
    @BindView(R.id.switch_location)
    SwitchButton mSwitchLocation;
    @BindView(R.id.iv_location_right)
    ImageView mIvLocationRight;
    @BindView(R.id.switch_pedometer)
    SwitchButton mSwitchPedometer;
    @BindView(R.id.switch_blue_tooth)
    SwitchButton mSwitchBlueTooth;


    private int watchID;
    private boolean isOnline;

    private int intelligenceWatchId;


    private String strToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_info);
        ButterKnife.bind(this);

        EventCenter.getInstance().register(this);
        initViewAndEvent();

        initData();

    }


    private void initData() {
        OkGo.<String>get(GlobalAPI.getWatchInfo)
                .params("access_token", strToken)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        Type type = new TypeToken<ResponseEntity<WatchInfoBean>>() {
                        }.getType();
                        ResponseEntity<WatchInfoBean> responseEntity = JsonUtil.fromJson(response.body(), type);
                        if (responseEntity == null) {
                            return;
                        }
                        switch (responseEntity.getResponseCode()) {
                            case 1001:
                                EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.GET_WATCH_INFO, 1, responseEntity.getData()));
                                break;
                            default:
                                break;
                        }
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(SimpleEvent simpleEvent) {
        if (simpleEvent == null || simpleEvent.mEventType != SimpleEvent.GET_WATCH_INFO) {
            return;
        }
        switch (simpleEvent.mEventValue) {
            case 1:
                WatchInfoBean watchInfoBean = (WatchInfoBean) simpleEvent.getData();
                mTvPageName.setText(watchInfoBean.getModelType());
                if (ONLINE.equals(watchInfoBean.getDeviceStatus())) {
                    mTvWatchStature.setText("在线");

                    isOnline = true;
                    mToolbarLayout.setContentScrimColor(getResources().getColor(R.color.watch_on));
                    mIvBg.setImageDrawable(getResources().getDrawable(R.drawable.bg_on_watch));

                } else {
                    isOnline = false;
                    mTvWatchStature.setText("离线");
                    mToolbarLayout.setContentScrimColor(getResources().getColor(R.color.watch_off));
                    mIvBg.setImageDrawable(getResources().getDrawable(R.drawable.bg_off_watch));
                }

                mTvWatchPower.setText(watchInfoBean.getRemainingPower() + "%");
                mTvFrequencyHeart.setText(watchInfoBean.getFrequencyHeartRate() + "分钟");
                mTvFrequencyLocation.setText(watchInfoBean.getFrequencyLocation() + "分钟");

                if (watchInfoBean.isBluetoothEnable()) {
                    mSwitchBlueTooth.toggleNoEvent();
                }
                if (watchInfoBean.isTrackEnable()) {
                    mSwitchLocation.toggleNoEvent();
                }
                if (watchInfoBean.isHeartRateEnable()) {
                    mSwitchHeartRate.toggleNoEvent();
                }
                if (watchInfoBean.isPedometerEnable()) {
                    mSwitchPedometer.toggleNoEvent();
                }

                watchID = watchInfoBean.getHardwareId();
                intelligenceWatchId = watchInfoBean.getIntelligenceWatchId();

                break;
            default:
                break;

        }
    }

    private void initViewAndEvent() {

        strToken = ContextSession.getTokenString();
        mTvPageName.setText(R.string.watch_type);
        mTvPageName.setTextColor(Color.WHITE);
        mBackLeft.setOnClickListener(this);
        mLlayoutWatchInfo.setOnClickListener(this);
        mRlayoutFamilyNumber.setOnClickListener(this);
        mRlayoutFrequencyHeart.setOnClickListener(this);
        mRlayoutFamilyNumber.setOnClickListener(this);
        mBtnUnbind.setOnClickListener(this);

        mSwitchPedometer.setOnCheckedChangeListener(this);
        mSwitchPedometer.setOnCheckedChangeListener(this);
        mSwitchLocation.setOnCheckedChangeListener(this);
        mSwitchHeartRate.setOnCheckedChangeListener(this);
        mSwitchBlueTooth.setOnCheckedChangeListener(this);


    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.back_left:
                finish();
                break;

            case R.id.llayout_watch_info:
                if (!isOnline) {
                    readyGo(OfflineReasonActivity.class);
                }
                break;
            case R.id.rlayout_frequency_heart:
                break;
            case R.id.rlayout_frequency_location:
                break;
            case R.id.btn_unbind:

                final DialogLoginOut unbindDialog = new DialogLoginOut(mContext, R.layout.item_dialog_loginout);

                unbindDialog.setTitle("此操作将解除手表绑定关系，并清除手表所有设置，是否解绑？");
                unbindDialog.setMessage("");
                unbindDialog.setYesOnclickListener("解除绑定", new DialogLoginOut.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        unBindWatch();
                    }
                });
                unbindDialog.setNoOnclickListener("取消", new DialogLoginOut.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {

                        unbindDialog.dismiss();
                    }
                });
                unbindDialog.show();

                break;
            case R.id.rlayout_family_number:

                bundle.putInt("intelligenceWatchId", intelligenceWatchId);
                readyGo(FamilyNumberActivity.class, bundle);
                break;
            default:
                break;
        }

    }

    /**
     * 解绑手表
     */
    private void unBindWatch() {

        OkGo.<String>delete(GlobalAPI.unBindWatch)
                .params("access_token", strToken)
                .params("hardwareId", watchID)
                .isSpliceUrl(true)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        ResponseEntity responseEntity = JsonUtil.fromJson(response.body(), ResponseEntity.class);
                        if (responseEntity == null) {
                            return;
                        }
                        switch (responseEntity.getResponseCode()) {
                            case 1001:
                                showToastShort("解除绑定成功");
                                AppSharePreferenceMgr.put(mContext, GlobalConfig.IS_BIND_WATCH, false);
                                finish();
                                break;
                            default:
                                showToastShort("解除绑定失败");
                                break;
                        }
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventCenter.getInstance().unregister(this);
    }


    /**
     * 修改设备信息
     *
     * @param type
     * @param isChecked
     */
    private void modifyWatchInfo(String type, boolean isChecked) {
        OkGo.<String>put(GlobalAPI.modifyWatchInfo)
                .params("access_token", strToken)
                .params(type, isChecked)
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
                                    showToastShort("修改信息成功");

                                    break;
                                default:
                                    showToastShort("修改信息失败");
                                    break;
                            }
                        }

                    }
                });

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switch_blue_tooth:
                modifyWatchInfo("bluetoothEnable", isChecked);
                break;
            case R.id.switch_heart_rate:
                modifyWatchInfo("heartRateEnable", isChecked);
                break;
            case R.id.switch_location:
                modifyWatchInfo("trackEnable", isChecked);
                break;
            case R.id.switch_pedometer:
                modifyWatchInfo("pedometerEnable", isChecked);
                break;
            default:
                break;

        }
    }
}
