package com.shiwaixiangcun.customer.ui.activity.mall;

import android.annotation.SuppressLint;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.event.EventCenter;
import com.shiwaixiangcun.customer.event.SimpleEvent;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.http.StringDialogCallBack;
import com.shiwaixiangcun.customer.model.AddressBean;
import com.shiwaixiangcun.customer.model.CurrentOrder;
import com.shiwaixiangcun.customer.model.GoodDetail;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.pay.PayUtil;
import com.shiwaixiangcun.customer.ui.dialog.DialogPay;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.ArithmeticUtils;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.RefreshTokenUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 订单确认页面
 *
 * @author Administrator
 */
public class ConfirmOrderActivity extends BaseActivity implements View.OnClickListener {

    private static final int ADD_ADDRESS = 0X12;
    private static final int CHOOSE_ADDRESS = 0x13;

    String userName;
    String userPhone;
    String userAddress;
    AddressBean defaultAddress;
    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.tv_top_right)
    TextView mTvTopRight;
    @BindView(R.id.iv_share_right)
    ImageView mIvShareRight;
    @BindView(R.id.iv_sao_right)
    ImageView mIvSaoRight;
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
    TextView mTvGoodTitle;
    @BindView(R.id.tv_good_desc)
    TextView mTvGoodDesc;
    @BindView(R.id.tv_good_item_price)
    TextView mTvGoodItemPrice;
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
    @BindView(R.id.tv_good_total)
    TextView mTvGoodTotal;
    @BindView(R.id.tv_fare_price)
    TextView mTvFarePrice;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.should_pay)
    TextView mShouldPay;
    @BindView(R.id.btn_commit)
    Button mBtnCommit;
    String refreshToken;
    private boolean hasAddress = false;
    private int mGoodId;
    private String mStrMessage;
    private String mChooseValue;
    private String mChooseId;
    private GoodDetail.DataBean data;
    private int amount = 1;
    private String tokenString;
    private int addressId;
    private DialogPay mDialogPay;
    private double goodPrice;
    private String mOrderNumber;
    private int mOrderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.bind(this);
        EventCenter.getInstance().register(this);
        initData();
        initView();
        initEvent();
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
        goodPrice = bundle.getDouble("goodPrice", 0);
        if (goodPrice == 0) {
            goodPrice = data.getMinPrice();
        }


    }

    private void initEvent() {
        mBackLeft.setOnClickListener(this);
        mLlayoutAddAddress.setOnClickListener(this);
        mIvAdd.setOnClickListener(this);
        mIvReduce.setOnClickListener(this);
        mBtnCommit.setOnClickListener(this);
        mRLayoutHasAddress.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        mTvPageName.setText("确认订单");

        refreshToken = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.Refresh_token, "");
        tokenString = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.TOKEN, "");
        //设置商品信息
        mDialogPay = new DialogPay(this);
        mTvGoodTitle.setText(data.getGoodsName());
        mTvInformation.setText("运费￥ " + data.getTransportMoney() + " ,由 " + data.getShopName() + "负责发货");
        mTvGoodDesc.setText(mChooseValue);
        if (goodPrice == 0) {
            mTvGoodItemPrice.setText(("￥ " + ArithmeticUtils.format(data.getMinPrice())));
            mTvGoodTotal.setText("￥ " + ArithmeticUtils.format(data.getMinPrice()));
        } else {
            mTvGoodItemPrice.setText("￥ " + ArithmeticUtils.format(goodPrice));
            mTvGoodTotal.setText("￥ " + ArithmeticUtils.format(goodPrice));
        }
        mTvFarePrice.setText("￥ " + ArithmeticUtils.format(data.getTransportMoney()));

        mTvOrderAmount.setText(amount + "");
        mTvGoodAmount.setText("x " + amount);
        double allPrice = ArithmeticUtils.add(goodPrice, data.getTransportMoney());
        mShouldPay.setText("￥ " + ArithmeticUtils.format(allPrice));
        ImageDisplayUtil.showImageView(mContext, data.getImages().get(0).getThumbImageURL(), mIvGoodCover);


        //设置地址情况

        String stringSpParams = SharePreference.getStringSpParams(this, Common.ADDRESS, Common.DEFAULT_ADDRESS);
        if (stringSpParams != null) {
//            hasAddress = true;
            defaultAddress = JsonUtil.fromJson(stringSpParams, AddressBean.class);

        }
        mRLayoutNoAddress.setVisibility(View.GONE);
        mRLayoutHasAddress.setVisibility(View.INVISIBLE);
        //获取默认地址
        getDefaultAddress();
//        if (hasAddress) {
//            mRLayoutHasAddress.setVisibility(View.VISIBLE);
//            mLlayoutAddAddress.setVisibility(View.GONE);
//            mTvUserName.setText(defaultAddress.getDeliveryName());
//            mTvUserPhone.setText(defaultAddress.getDeliveryPhone());
//            mTvAddress.setText(defaultAddress.getDeliveryAddress());
//        } else {
//            mRLayoutHasAddress.setVisibility(View.GONE);
//            mLlayoutAddAddress.setVisibility(View.VISIBLE);
//        }
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
                Bundle bundle = new Bundle();
                bundle.putBoolean("clickable", true);
                readyGoForResult(ManageAddressActivity.class, CHOOSE_ADDRESS, bundle);
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
        Log.e(BUG_TAG, "收货地址Id" + addressId);
        if (addressId == 0) {
            Toast.makeText(mContext, "请添加地址", Toast.LENGTH_SHORT).show();
            return;
        }
        final String loginInfo = SharePreference.getStringSpParams(mContext, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(loginInfo, type);


        mStrMessage = mEdtMessage.getText().toString();
        HttpParams params = new HttpParams();
        params.put("addressId", addressId);
        params.put("amount", amount);
        params.put("attrDescription", mChooseValue);
        params.put("attrIds", mChooseId);
        params.put("goodsId", mGoodId);
        params.put("leavingMessage", mStrMessage);
        params.put("sellerId", data.getSellerId());
        params.put("access_token", tokenString);
        params.put("siteId", GlobalConfig.siteID);
        OkGo.<String>post(GlobalAPI.putOrder)
                .params(params)
                .execute(new StringDialogCallBack(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(BUG_TAG, response.getRawCall().request().toString());
                        Log.e(BUG_TAG, response.body());
                        String jsonString = response.body();
                        CurrentOrder currentOrder = JsonUtil.fromJson(jsonString, CurrentOrder.class);
                        Log.e(BUG_TAG, currentOrder.getResponseCode() + "");
                        switch (currentOrder.getResponseCode()) {
                            case 1001:
                                Toast.makeText(mContext, "提交订单成功", Toast.LENGTH_SHORT).show();
                                mOrderNumber = currentOrder.getData().getNumber();
                                mOrderId = currentOrder.getData().getId();
                                mDialogPay.setPrice("¥" + ArithmeticUtils.format(currentOrder.getData().getShouldPay()));
                                mDialogPay.show();
                                mDialogPay.setListener(new DialogPay.onCallBackListener() {
                                    @Override
                                    public void closeBtn(DialogPay dialog) {
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void confirmBtn(DialogPay dialog) {
                                        int defaultPay = dialog.getDefaultPay();
                                        switch (defaultPay) {
                                            case 1:
                                                Toast.makeText(mContext, "正在进行微信支付", Toast.LENGTH_SHORT).show();
                                                PayUtil.payWeixin(mOrderNumber, tokenString, ConfirmOrderActivity.this);
                                                break;
                                            case 2:
                                                PayUtil.payAli(mOrderNumber, tokenString, ConfirmOrderActivity.this);
                                                break;
                                            case 0:
                                                Toast.makeText(mContext, "请选择一种支付方式", Toast.LENGTH_SHORT).show();
                                                break;
                                        }
                                    }
                                });
                                break;

                            case 1018:
                                RefreshTokenUtil.sendIntDataInvatation(mContext, refreshToken);
                                break;
                            default:
                                Toast.makeText(mContext, currentOrder.getMessage(), Toast.LENGTH_SHORT).show();
                                break;

                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Call rawCall = response.getRawCall();
                        Log.e(BUG_TAG, rawCall.request().url() + "");
                        Log.e(BUG_TAG, "onError");
                        Toast.makeText(mContext, "提交订单失败", Toast.LENGTH_SHORT).show();
                    }
                });

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(SimpleEvent simpleEvent) {
        if (simpleEvent == null) {
            return;
        }
        switch (simpleEvent.mEventType) {
            case SimpleEvent.PAY_SUCCESS:
                Log.e(BUG_TAG, "支付成功");
                mDialogPay.dismiss();
                Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("orderId", mOrderId);
                intent.setClass(mContext, OrderDetailActivity.class);
                startActivity(intent);
                finish();
                break;
            case SimpleEvent.PAY_FAIL:
                mDialogPay.dismiss();
                Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                break;
            case SimpleEvent.CONFIRM_ORDER:
                if (simpleEvent.mEventValue == 2) {
                    if (amount == 1) {
                        Toast.makeText(mContext, "至少需要选择一个商品", Toast.LENGTH_SHORT).show();
                    } else {
                        amount -= 1;
                    }
                }
                if (simpleEvent.mEventValue == 1) {
                    amount += 1;
                }

                double good_total = ArithmeticUtils.mul(amount, goodPrice, 2);
                double should_pay = ArithmeticUtils.add(good_total, data.getTransportMoney());

                if (amount == 0) {
                    mShouldPay.setText("￥ " + ArithmeticUtils.format(data.getTransportMoney()));
                } else {
                    mShouldPay.setText("￥ " + ArithmeticUtils.format(should_pay));
                }

                mTvGoodTotal.setText("￥ " + ArithmeticUtils.format(good_total));
                mTvOrderAmount.setText(amount + "");
                mTvGoodAmount.setText("x " + amount);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_ADDRESS && resultCode == 0x12) {
            Log.e(BUG_TAG, "添加地址返回");
            Bundle addBundle = data.getExtras();
            mRLayoutNoAddress.setVisibility(View.GONE);
            mRLayoutHasAddress.setVisibility(View.VISIBLE);
            addressId = addBundle.getInt("addressID", 0);
            userName = addBundle.getString("userName");
            userPhone = addBundle.getString("userPhone");
            userAddress = addBundle.getString("userAddress");
            mTvUserName.setText(userName);
            mTvUserPhone.setText(userPhone);
            mTvAddress.setText(userAddress);
        }
        if (requestCode == CHOOSE_ADDRESS && resultCode == 0x13) {
            Log.e(BUG_TAG, "选择地址返回");
            Bundle chooseBundle = data.getExtras();
            mRLayoutNoAddress.setVisibility(View.GONE);
            mRLayoutHasAddress.setVisibility(View.VISIBLE);
            addressId = chooseBundle.getInt("addressID", 0);
            userName = chooseBundle.getString("userName");
            userPhone = chooseBundle.getString("userPhone");
            userAddress = chooseBundle.getString("userAddress");
            mTvUserName.setText(userName);
            mTvUserPhone.setText(userPhone);
            mTvAddress.setText(userAddress);
        }


    }

    public void getDefaultAddress() {
        HashMap<String, Object> params = new HashMap<>(2);
        params.put("access_token", tokenString);
        params.put("fields", "");
        HttpRequest.get(GlobalAPI.getAddress, params, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                super.onSuccess(responseJson);
                Log.e(BUG_TAG, "onSuccess");
                if (responseJson == null) {
                    return;
                }
                Log.e(BUG_TAG, responseJson);
                Type listType = new TypeToken<ResponseEntity<List<AddressBean>>>() {
                }.getType();
                Gson gson = new Gson();
                ResponseEntity<List<AddressBean>> response = gson.fromJson(responseJson, listType);
                if (response == null) {
                    return;
                }
                switch (response.getResponseCode()) {
                    case 1001:
                        if (response.getData().size() > 0) {
                            AddressBean defaultAddress = response.getData().get(0);
                            mRLayoutHasAddress.setVisibility(View.VISIBLE);
                            mRLayoutNoAddress.setVisibility(View.GONE);
                            addressId = defaultAddress.getId();
                            mTvUserName.setText(defaultAddress.getDeliveryName());
                            mTvUserPhone.setText(defaultAddress.getDeliveryPhone());
                            mTvAddress.setText(defaultAddress.getDeliveryAddress());
                        } else {
                            mRLayoutHasAddress.setVisibility(View.GONE);
                            mRLayoutNoAddress.setVisibility(View.VISIBLE);
                        }
                        break;
                    default:
                        mRLayoutHasAddress.setVisibility(View.GONE);
                        mRLayoutNoAddress.setVisibility(View.VISIBLE);
                        Toast.makeText(mContext, "获取地址出错", Toast.LENGTH_SHORT).show();
                        break;

                }

            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }
}
