package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.event.EventCenter;
import com.shiwaixiangcun.customer.event.SimpleEvent;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.StringDialogCallBack;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.OrderDetailBean;
import com.shiwaixiangcun.customer.response.ResponseEntity;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.ShareUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/18.
 */

public class OrderDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_order_status)
    TextView mTvOrderStatus;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_user_phone)
    TextView mTvUserPhone;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.iv_cover_sku)
    ImageView mIvCoverSku;
    @BindView(R.id.tv_order_title)
    TextView mTvOrderTitle;
    @BindView(R.id.tv_order_desc)
    TextView mTvOrderDesc;
    @BindView(R.id.tv_order_price)
    TextView mTvOrderPrice;
    @BindView(R.id.tv_order_amount)
    TextView mTvOrderAmount;
    @BindView(R.id.tv_order_info)
    TextView mTvOrderInfo;
    @BindView(R.id.tv_user_message)
    TextView mTvUserMessage;
    @BindView(R.id.tv_order_express)
    TextView mTvOrderExpress;
    @BindView(R.id.tv_order_id)
    TextView mTvOrderId;
    @BindView(R.id.tv_order_date)
    TextView mTvOrderDate;
    @BindView(R.id.tv_order_total)
    TextView mTvOrderTotal;
    @BindView(R.id.tv_fare)
    TextView mTvFare;
    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.tv_total)
    TextView mTvTotal;
    @BindView(R.id.btn_commit)
    Button mBtnCommit;
    private int orderId = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderId = getIntent().getIntExtra("orderId", 0);
        setContentView(R.layout.layout_order_detail);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initData() {
        final String loginInfo = ShareUtil.getStringSpParams(mContext, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Type logType = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> loginRespose = JsonUtil.fromJson(loginInfo, logType);
        String tokenString = loginRespose.getData().getAccess_token();
        Log.e(BUG_TAG, tokenString);
        Log.e(BUG_TAG, String.valueOf(orderId));
        OkGo.<String>get(GlobalConfig.getOrderDetail)
                .params("access_token", tokenString)
                .params("id", orderId)
                .execute(new StringDialogCallBack(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (response == null) {
                            return;
                        }
                        String json = response.body();
                        Type dataType = new TypeToken<ResponseEntity<OrderDetailBean>>() {
                        }.getType();
                        Gson gson = new Gson();
                        ResponseEntity<OrderDetailBean> responseEntity = gson.fromJson(json, dataType);
                        if (responseEntity == null) {
                            return;
                        }
                        switch (responseEntity.getResponseCode()) {
                            case 1001:
                                OrderDetailBean orderDetail = responseEntity.getData();
                                Log.e(BUG_TAG, orderDetail.getBuyersInfo().getDeliveryAddress());


                                EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_ORDER_DETAIL,1,orderDetail));
                                break;

                        }
                    }
                });
    }

    private void initView() {
        mBackLeft.setOnClickListener(this);
        mTvPageName.setText("订单详情");

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(SimpleEvent simpleEvent) {
        switch (simpleEvent.mEventType) {
//            case SimpleEvent.UPDATE_ORDER_DETAIL:
//                if (isfromMeAct(simpleEvent.mData, this)) {
//                    CustomToast.show("支付成功", Toast.LENGTH_SHORT);
//                    onSuccess();
//                }
//                break;
//            case SimpleEvent.EVENT_WX_PAY_CANCEL:
//                if (isfromMeAct(simpleEvent.mData, this)) {
//                    // TODO: 16/12/8
//                }
//                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_left:
                finish();
                break;

        }

    }
}
