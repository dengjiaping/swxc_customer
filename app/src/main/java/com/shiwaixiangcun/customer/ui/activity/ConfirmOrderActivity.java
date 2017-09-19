package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.event.EventCenter;
import com.shiwaixiangcun.customer.event.SimpleEvent;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.StringDialogCallBack;
import com.shiwaixiangcun.customer.model.AddressBean;
import com.shiwaixiangcun.customer.model.GoodDetail;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.response.ResponseEntity;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.ShareUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class ConfirmOrderActivity extends BaseActivity implements View.OnClickListener {

    private static final int ADD_ADDRESS = 0X12;
    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.tv_top_right)
    TextView mTvTopRight;
    @BindView(R.id.ll_image_right)
    LinearLayout mLlImageRight;
    @BindView(R.id.top_bar_write)
    RelativeLayout mTopBarWrite;
    @BindView(R.id.iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_user_phone)
    TextView mTvUserPhone;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.rLayout_hasAddress)
    RelativeLayout mRLayoutHasAddress;
    @BindView(R.id.llayout_add_address)
    LinearLayout mLlayoutAddAddress;
    @BindView(R.id.rLayout_noAddress)
    RelativeLayout mRLayoutNoAddress;
    @BindView(R.id.iv_good_cover)
    ImageView mIvGoodCover;
    @BindView(R.id.tv_good_title)
    TextView mTvGoodName;
    @BindView(R.id.tv_good_desc)
    TextView mTvGoodDesc;
    @BindView(R.id.tv_good_price)
    TextView mTvGoodPrice;
    @BindView(R.id.tv_good_amount)
    TextView mTvGoodAmount;
    @BindView(R.id.iv_reduce)
    ImageView mIvReduce;
    @BindView(R.id.tv_order_amount)
    TextView mTvOrderAmount;
    @BindView(R.id.iv_add)
    ImageView mIvAdd;
    @BindView(R.id.tv_information)
    TextView mTvInformation;
    @BindView(R.id.edt_message)
    EditText mEdtMessage;
    @BindView(R.id.tv_total)
    TextView mTvTotal;
    @BindView(R.id.btn_commit)

    Button mBtnCommit;
    @BindView(R.id.tv_order_total)
    TextView mTvOrderTotal;
    @BindView(R.id.tv_order_fare)
    TextView mTvOrderFare;
    String userName;
    String userPhone;
    String userAddress;
    AddressBean defaultAddress;

    private boolean hasAddress = false;
    private int mGoodId;
    private String mChooseValue;
    private String mChooseId;
    private String fare;
    private String shopName;
    private GoodDetail.DataBean data;

    private int amount = 1;
    private String tokenString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.bind(this);

        EventCenter.getInstance().register(this);
        initData();
        initView();
        initEvent();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventCenter.getInstance().unregister(this);
    }

    private void initData() {

        Bundle bundle = getIntent().getExtras();
        mGoodId = bundle.getInt("goodId", 0);
        mChooseValue = bundle.getString("value");
        mChooseId = bundle.getString("id");
        data = bundle.getParcelable("goodInfo");


    }

    private void initEvent() {
        mBackLeft.setOnClickListener(this);
        mLlayoutAddAddress.setOnClickListener(this);
        mIvAdd.setOnClickListener(this);
        mIvReduce.setOnClickListener(this);
        mBtnCommit.setOnClickListener(this);
        mRLayoutHasAddress.setOnClickListener(this);
    }

    private void initView() {
        mTvPageName.setText("确认订单");
        //设置商品信息
        mTvGoodName.setText(data.getGoodsName());
        mTvInformation.setText("运费￥ " + data.getTransportMoney() + " ,由 " + data.getShopName() + "负责发货");
        mTvGoodDesc.setText(data.getFeature());
        mTvGoodPrice.setText("￥ " + String.valueOf(data.getMinPrice()));
        mTvOrderFare.setText("￥ " + String.valueOf(data.getTransportMoney()));
        mTvOrderAmount.setText(amount + "");
        mTvGoodAmount.setText("x " + amount);
        ImageDisplayUtil.showImageView(mContext, data.getImages().get(0).getThumbImageURL(), mIvGoodCover);


        //设置地址情况

        String stringSpParams = ShareUtil.getStringSpParams(this, Common.ADDRESS, Common.DEFAULT_ADDRESS);
        if (stringSpParams != null) {
//            hasAddress = true;
            defaultAddress = JsonUtil.fromJson(stringSpParams, AddressBean.class);

        }
        if (hasAddress) {
            mRLayoutHasAddress.setVisibility(View.VISIBLE);
            mLlayoutAddAddress.setVisibility(View.GONE);
            mTvUserName.setText(defaultAddress.getDeliveryName());
            mTvUserPhone.setText(defaultAddress.getDeliveryPhone());
            mTvAddress.setText(defaultAddress.getDeliveryAddress());
        } else {
            mRLayoutHasAddress.setVisibility(View.GONE);
            mLlayoutAddAddress.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_left:
                finish();
                break;
            case R.id.iv_add:
                EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.CONFIRM_ORDER, 1));
                break;
            case R.id.iv_reduce:
                EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.CONFIRM_ORDER, 2));
                break;
            case R.id.llayout_add_address:
                readyGoForResult(AddAddressActivity.class, ADD_ADDRESS);
                break;
            case R.id.rLayout_hasAddress:
                readyGo(ManageAddressActivity.class);
                break;

            case R.id.btn_commit:
                putData();
                break;
        }
    }

    /**
     * 提交订单到服务器
     */
    private void putData() {

        final String loginInfo = ShareUtil.getStringSpParams(mContext, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(loginInfo, type);
        tokenString = responseEntity.getData().getAccess_token();
        HttpParams params = new HttpParams();
        params.put("addressId", 66);
        params.put("amount", amount);
        params.put("attrDescription", mChooseValue);
        params.put("attrIds", mChooseId);
        params.put("goodsId", mGoodId);
        params.put("leavingMessage", "");
        params.put("sellerId", data.getSellerId());
        params.put("access_token", tokenString);

        Log.e(BUG_TAG, mChooseValue);
        Log.e(BUG_TAG, mChooseId);
        OkGo.<String>post(GlobalConfig.putOrder)
                .params(params)
                .isSpliceUrl(true)
                .execute(new StringDialogCallBack(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(BUG_TAG, "onSuccess");
                        Log.e(BUG_TAG, response.body());
//                        String jsonString = response.body();
//                        Gson gson = new Gson();
//                        SimpleResponse entity = gson.fromJson(jsonString, SimpleResponse.class);
//                        Log.e(BUG_TAG, entity.message + "  \n" + entity.responseCode);

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Call rawCall = response.getRawCall();
                        Log.e(BUG_TAG, rawCall.request().url() + "");
                        Log.e(BUG_TAG, "onError");
                    }
                });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(SimpleEvent simpleEvent) {
        Log.e(BUG_TAG, "点击回传");
        if (simpleEvent.mEventType != SimpleEvent.CONFIRM_ORDER) {
            return;
        }
        if (simpleEvent.mEventValue == 2) {
            if (amount == 0) {
                Toast.makeText(mContext, "至少需要选择一个商品", Toast.LENGTH_SHORT).show();
            } else {
                amount -= 1;
            }
        }
        if (simpleEvent.mEventValue == 1) {
            amount += 1;
        }
        double total_good = amount * data.getMinPrice();
        double total_order = total_good + data.getTransportMoney();
        mTvTotal.setText("￥ " + String.valueOf(total_good));
        mTvOrderTotal.setText("￥ " + String.valueOf(total_order));
        mTvOrderAmount.setText(amount + "");
        mTvGoodAmount.setText("x " + amount);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ADD_ADDRESS) {
            mRLayoutNoAddress.setVisibility(View.GONE);
            mRLayoutHasAddress.setVisibility(View.VISIBLE);
            Bundle bundle = data.getExtras();
            userName = bundle.getString("userName");
            userPhone = bundle.getString("userPhone");
            userAddress = bundle.getString("userAddress");
            mTvUserName.setText(userName);
            mTvUserPhone.setText(userPhone);
            mTvAddress.setText(userAddress);
        }
    }
}
