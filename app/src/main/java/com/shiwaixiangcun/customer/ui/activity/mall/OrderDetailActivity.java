package com.shiwaixiangcun.customer.ui.activity.mall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.event.EventCenter;
import com.shiwaixiangcun.customer.event.SimpleEvent;
import com.shiwaixiangcun.customer.http.StringDialogCallBack;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.OrderDetailBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.pay.PayUtil;
import com.shiwaixiangcun.customer.ui.dialog.DialogInfo;
import com.shiwaixiangcun.customer.ui.dialog.DialogPay;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.ArithmeticUtils;
import com.shiwaixiangcun.customer.utils.DateUtil;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.RefreshTokenUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.utils.StringUtil;
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
    @BindView(R.id.iv_good_cover)
    ImageView mIvGoodCover;
    @BindView(R.id.tv_good_title)
    TextView mTvGoodTitle;
    @BindView(R.id.tv_good_desc)
    TextView mTvGoodDesc;
    @BindView(R.id.tv_good_item_price)
    TextView mTvGoodPrice;
    @BindView(R.id.tv_good_amount)
    TextView mTvGoodAmount;
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
    @BindView(R.id.tv_good_total)
    TextView mTvOrderTotal;
    @BindView(R.id.tv_fare)
    TextView mTvFare;
    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.should_pay)
    TextView mTvTotal;
    @BindView(R.id.btn_commit)
    Button mBtnCommit;
    @BindView(R.id.tv_order_pay_way)
    TextView mTvOrderPayWay;
    @BindView(R.id.tv_cue)
    TextView mTvCue;
    @BindView(R.id.rlayout_bottom)
    RelativeLayout mRlayoutBottom;
    @BindView(R.id.root_view)
    FrameLayout mRootView;
    @BindView(R.id.ll_info_price)
    LinearLayout mLlInfoPrice;

    OrderDetailBean.OrderInfoBean orderInfo;
    @BindView(R.id.llayout_good_info)
    LinearLayout mLlayoutGoodInfo;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
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
    int goodsId = 0;
    DialogInfo mDialogDelete;
    DialogInfo mDialogCancel;
    DialogPay mDialogPay;
    private int orderId = 0;
    private String tokenString;
    private String refresh_token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderId = getIntent().getIntExtra("orderId", 0);
        Log.e(BUG_TAG, orderId + "");
        setContentView(R.layout.layout_order_detail);
        EventCenter.getInstance().register(this);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventCenter.getInstance().unregister(this);
    }

    private void initData() {
        String loginInfo = SharePreference.getStringSpParams(mContext, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(loginInfo, type);
        refresh_token = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.Refresh_token, "");
        tokenString = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.TOKEN, "");
        Log.e(BUG_TAG, tokenString);
        Log.e(BUG_TAG, String.valueOf(orderId));
        OkGo.<String>get(GlobalAPI.getOrderDetail)
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
                                EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_ORDER_DETAIL, 1, orderDetail));
                                break;
                            case 1018:
                                RefreshTokenUtil.sendIntDataInvatation(mContext, refresh_token);
                                break;

                        }
                    }
                });
    }

    private void initView() {
        mDialogPay = new DialogPay(mContext);
        mDialogCancel = new DialogInfo(this);
        mDialogDelete = new DialogInfo(this);
        mLlayoutGoodInfo.setOnClickListener(this);
        mBackLeft.setOnClickListener(this);
        mTvPageName.setText("订单详情");
        mBtnCommit.setOnClickListener(this);
        mTvCancel.setClickable(true);
        mTvCancel.setOnClickListener(this);


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(SimpleEvent simpleEvent) {

        switch (simpleEvent.mEventType) {

            case SimpleEvent.UPDATE_ORDER_DETAIL:
                if (simpleEvent.mEventValue == 1) {
                    updateUI((OrderDetailBean) simpleEvent.mData);
                }
                break;
            case SimpleEvent.PAY_SUCCESS:
                mDialogPay.dismiss();
                initData();
                break;
            case SimpleEvent.PAY_FAIL:
        }

    }

    /**
     * 更新界面
     *
     * @param orderDetail
     */
    public void updateUI(OrderDetailBean orderDetail) {

        Log.e(BUG_TAG, "更新界面");
        switch (orderDetail.getOrderStatus().getStatus()) {
            case "Finished":
                mBtnCommit.setVisibility(View.GONE);
                mTvCancel.setVisibility(View.GONE);
                mTvOrderStatus.setText("已完成");
                mTvCue.setText("实际付款");
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mLlInfoPrice.getLayoutParams();
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                mLlInfoPrice.setLayoutParams(layoutParams);
                break;
            case "WaitPay":
                mTvOrderStatus.setText("等待付款");
                mBtnCommit.setVisibility(View.VISIBLE);
                mTvCancel.setVisibility(View.VISIBLE);
                mBtnCommit.setText("立即付款");
                break;
            case "WaitDeliver":
                mTvOrderStatus.setText("等待卖家发货");
                mTvCancel.setVisibility(View.GONE);
                mTvCue.setText("实际付款");
                mBtnCommit.setVisibility(View.GONE);
                RelativeLayout.LayoutParams layoutParam = (RelativeLayout.LayoutParams) mLlInfoPrice.getLayoutParams();
                layoutParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                mLlInfoPrice.setLayoutParams(layoutParam);
                break;
            case "Delivered":
                mTvOrderStatus.setText("已发货");
                mTvCue.setText("实际付款");
                mBtnCommit.setText("确认收货");
                mTvCancel.setVisibility(View.GONE);
                mBtnCommit.setVisibility(View.VISIBLE);
                mBtnCommit.setText("确认收货");
                mBtnCommit.setOnClickListener(this);
                break;
            case "Closed":
                mTvOrderStatus.setText("已关闭");
                mBtnCommit.setVisibility(View.VISIBLE);
                mBtnCommit.setText("删除订单");
                mBtnCommit.setOnClickListener(this);
                break;


        }

        // TODO: 2017/9/23 设置配送信息

        //更新收货人信息
        OrderDetailBean.BuyersInfoBean buyersInfo = orderDetail.getBuyersInfo();
        mTvUserName.setText(buyersInfo.getDeliveryName());
        mTvAddress.setText(buyersInfo.getDeliveryAddress());
        mTvUserPhone.setText(buyersInfo.getDeliveryPhone());
        mTvOrderInfo.setText(buyersInfo.getDeliveryWay());
        if (StringUtil.isEmpty(buyersInfo.getExpressWay())) {
            mTvOrderExpress.setText("暂没有快递信息");
        } else {
            mTvOrderExpress.setText(buyersInfo.getExpressWay());
        }
        if (StringUtil.isEmpty(buyersInfo.getLeavingMessage())) {
            mTvUserMessage.setText("无");
        } else {
            mTvUserMessage.setText(buyersInfo.getLeavingMessage());
        }


        //更新订单信息
        orderInfo = orderDetail.getOrderInfo();
        mTvOrderId.setText(orderInfo.getOrderNumber());
        mTvOrderDate.setText(DateUtil.getMillon(orderInfo.getOrderTime()));
        mTvOrderTotal.setText("¥ " + ArithmeticUtils.format(orderInfo.getRealPay()));
        mTvFare.setText("¥ " + ArithmeticUtils.format(orderInfo.getTransportMoney()));
        mTvTotal.setText("¥ " + ArithmeticUtils.format(orderInfo.getShouldPay()));
        switch (orderInfo.getPayWay()) {
            case "WeiXin":
                mTvOrderPayWay.setText("微信支付");
                break;
            case "ZhiFuBao":
                mTvOrderPayWay.setText("支付宝支付");
                break;
            default:
                mTvOrderPayWay.setText("未付款");
                break;
        }


        //更新商品的信息
        OrderDetailBean.GoodsDetailBean goodsDetailBean = orderDetail.getGoodsDetail().get(0);

        goodsId = goodsDetailBean.getGoodsId();
        mTvGoodDesc.setText(goodsDetailBean.getAttrDescription());
        mTvGoodTitle.setText(goodsDetailBean.getGoodName());
        mTvGoodPrice.setText("¥ " + ArithmeticUtils.format(goodsDetailBean.getPrice()));
        mTvGoodAmount.setText("x" + goodsDetailBean.getAmount());
        ImageDisplayUtil.showImageView(this, goodsDetailBean.getImgPath(), mIvGoodCover);
        Log.e(BUG_TAG, orderDetail.getBuyersInfo().getDeliveryAddress());

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_left:
                finish();
                break;
            case R.id.btn_commit:
                switch (mBtnCommit.getText().toString()) {
                    case "确认收货":
                        Log.e(BUG_TAG, "确认收货");
                        DialogInfo dialogInfo = new DialogInfo(mContext);
                        dialogInfo.setDialogTitle("确认收货");
                        dialogInfo.setDialogInfo("在您确认收货前，请检查商品是否完好，确定无误无损后再确认收货，确认收货后，订单将变为完成状态");
                        dialogInfo.setListener(new DialogInfo.onCallBackListener() {
                            @Override
                            public void closeBtn(DialogInfo dialog) {
                                dialog.dismiss();
                            }

                            @Override
                            public void confirmBtn(DialogInfo dialog) {
                                confirmOrder();
                                dialog.dismiss();
                            }
                        });
                        dialogInfo.show();
                        break;
                    case "立即付款":
                        Log.e(BUG_TAG, "立即付款");
                        payOrder();
                        break;
                    case "删除订单":
                        Log.e(BUG_TAG, "删除订单");
                        mDialogDelete = new DialogInfo(mContext);
                        mDialogDelete.setDialogTitle("删除订单");
                        mDialogDelete.setDialogInfo("您确定要删除订单吗？");
                        mDialogDelete.setListener(new DialogInfo.onCallBackListener() {
                            @Override
                            public void closeBtn(DialogInfo dialog) {
                                dialog.dismiss();
                            }

                            @Override
                            public void confirmBtn(DialogInfo dialog) {
                                deleteOrder();
                                dialog.dismiss();
                            }
                        });
                        mDialogDelete.show();
                        break;
                }

                break;
            case R.id.tv_cancel:
                Log.e(BUG_TAG, "点击取消");
                mDialogCancel.setDialogTitle("取消订单");
                mDialogCancel.setDialogInfo("您确定要取消订单吗？取消订单后，订单将变为关闭状态。");
                mDialogCancel.setListener(new DialogInfo.onCallBackListener() {
                    @Override
                    public void closeBtn(DialogInfo dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void confirmBtn(DialogInfo dialog) {
                        cancelOrder();
                        dialog.dismiss();
                    }
                });
                mDialogCancel.show();
                break;

            case R.id.llayout_good_info:
                Bundle bundle = new Bundle();
                bundle.putInt("goodId", goodsId);
                readyGo(GoodDetailActivity.class, bundle);
                break;
        }
    }


    /**
     *
     */
    private void payOrder() {
        mDialogPay.setPrice("¥" + ArithmeticUtils.format(orderInfo.getShouldPay()));
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
                        PayUtil.payWeixin(orderInfo.getOrderNumber(), tokenString, OrderDetailActivity.this);
                        break;
                    case 2:
                        PayUtil.payAli(orderInfo.getOrderNumber(), tokenString, OrderDetailActivity.this);
                        break;
                    case 0:
                        Toast.makeText(mContext, "请选择一种支付方式", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }

    /**
     * 确认订单
     */

    private void confirmOrder() {

        commonRequest(orderId, GlobalAPI.confirmOrder, "确认收货");
    }

    /**
     * 取消订单
     */
    private void cancelOrder() {

        commonRequest(orderId, GlobalAPI.cancelOrder, "取消订单");

    }

    /**
     * 删除订单
     */
    private void deleteOrder() {

        commonRequest(orderId, GlobalAPI.deleteOrder, "删除订单");
    }

    private void commonRequest(int orderId, String url, final String prompt) {
        OkGo.<String>put(url)
                .params("access_token", tokenString)
                .params("id", orderId)
                .isSpliceUrl(true)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        Log.e(BUG_TAG, response.getRawCall().request().toString());
                        String body = response.body();
                        if (body == null) {
                            return;
                        }
                        ResponseEntity responseEntity = JsonUtil.fromJson(body, ResponseEntity.class);
                        switch (responseEntity != null ? responseEntity.getResponseCode() : 0) {
                            case 1001:
                                Snackbar.make(mRootView, prompt + "成功", Snackbar.LENGTH_SHORT).show();
                                finish();
                                break;
                            default:
                                Snackbar.make(mRootView, prompt + "失败", Snackbar.LENGTH_LONG).show();
                                break;
                        }
                    }
                });
    }
}
