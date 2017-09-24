package com.shiwaixiangcun.customer.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterOrder;
import com.shiwaixiangcun.customer.event.SimpleEvent;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.StringDialogCallBack;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.OrderBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.pay.AliInfo;
import com.shiwaixiangcun.customer.pay.AliPay;
import com.shiwaixiangcun.customer.pay.WeiXinInfo;
import com.shiwaixiangcun.customer.ui.activity.OrderDetailActivity;
import com.shiwaixiangcun.customer.ui.dialog.DialogInfo;
import com.shiwaixiangcun.customer.utils.DisplayUtil;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/9/18.
 */

public class FragmentOrder extends LazyFragment {
    @BindView(R.id.rv_order)
    RecyclerView mRvOrder;
    @BindView(R.id.iv_no)
    ImageView mIvNo;
    @BindView(R.id.tv_prompt)
    TextView mTvPrompt;
    @BindView(R.id.llayout_nodata)
    LinearLayout mLlayoutNodata;
    @BindView(R.id.root_view)
    FrameLayout mRootView;
    private String BUG_TAG = this.getClass().getSimpleName();
    private AdapterOrder mAdapterOrder;
    private String mTitle;
    private List<OrderBean.ElementsBean> mOrderList;
    private OrderBean mOrder;
    private Activity mContext;
    private String stature;
    private String mStringPrompt;
    private String tokenString;


    public static Fragment getInstance(String title) {
        FragmentOrder fragment = new FragmentOrder();
        fragment.mTitle = title;
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.order_fragment;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        initData();

    }

    @Override
    protected void onFirstUserVisible() {
        requestData();

    }

    @Override
    protected void onUserVisible() {
        requestData();

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void DestroyViewAndThing() {
        EventBus.getDefault().unregister(this);

    }

    /**
     * 初始化数据
     */
    private void initData() {
        mOrderList = new ArrayList<>();
        mAdapterOrder = new AdapterOrder(mOrderList);
        mTvPrompt.setText("没有" + mStringPrompt + "订单");
        mRvOrder.setLayoutManager(new LinearLayoutManager(mContext));
        mRvOrder.setAdapter(mAdapterOrder);
        mRvOrder.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 0, DisplayUtil.dip2px(mContext, 16));
            }
        });
        mAdapterOrder.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                OrderBean.ElementsBean elementsBean = mOrderList.get(position);
                gotoDetail(elementsBean.getOrderId());
            }
        });
        mAdapterOrder.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                final OrderBean.ElementsBean elementsBean = mOrderList.get(position);
                Button button = (Button) view;
                String string = button.getText().toString();
                switch (string) {
                    case "付款":
                        payOrder(elementsBean);
                        break;
                    case "删除订单":
                        DialogInfo deleteDialog = new DialogInfo(mContext);
                        deleteDialog.setDialogTitle("删除订单");
                        deleteDialog.setDialogInfo("确认删除此订单？");
                        deleteDialog.show();
                        deleteDialog.setListener(new DialogInfo.onCallBackListener() {
                            @Override
                            public void closeBtn(DialogInfo dialog) {
                                dialog.dismiss();
                            }

                            @Override
                            public void confirmBtn(DialogInfo dialog) {
                                deleteOrder(elementsBean);
                                dialog.dismiss();
                            }
                        });

                        break;
                    case "取消订单":
                        cancelOrder(elementsBean);
                        break;
                    case "确认收货":
                        DialogInfo dialogInfo = new DialogInfo(mContext);
                        dialogInfo.show();
                        configOrder(elementsBean);
                        break;
                }
            }
        });


    }

    private void requestData() {
        Log.e(BUG_TAG, "请求数据");
        HttpParams httpParams = new HttpParams();
        httpParams.put("page.pn", 1);
        httpParams.put("page.size", 10);
        httpParams.put("orderStatus", stature);
        httpParams.put("access_token", tokenString);
        OkGo.<String>get(GlobalConfig.getAllOrders)
                .params(httpParams)
                .execute(new StringDialogCallBack(mContext) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (response == null) {
                            return;
                        }
                        String responseJson = response.body();
                        Type listType = new TypeToken<ResponseEntity<OrderBean>>() {
                        }.getType();
                        Gson gson = new Gson();
                        ResponseEntity<OrderBean> order = gson.fromJson(responseJson, listType);
                        if (order == null) {
                            return;
                        }
                        mOrder = order.getData();
                        if (mOrder.getTotalAmount() == 0) {
                            mLlayoutNodata.setVisibility(View.VISIBLE);
                        }
                        if (mOrder.getTotalAmount() > 0) {
                            mLlayoutNodata.setVisibility(View.GONE);
                            mOrderList.clear();
                            mOrderList.addAll(mOrder.getElements());
                            mAdapterOrder.notifyDataSetChanged();
                        }

                    }
                });

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();
        mStringPrompt = mTitle;
        switch (mTitle) {
            case "全部":
                stature = "";
                break;
            case "待付款":
                stature = "WaitPay";
                break;
            case "待收货":
                stature = "WaitDeliver";
                break;
            case "已完成":
                stature = "Closed";
                break;
        }

        String loginInfo = SharePreference.getStringSpParams(mContext, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(loginInfo, type);
        tokenString = responseEntity.getData().getAccess_token();
        EventBus.getDefault().register(this);


    }

    private void gotoDetail(int orderId) {
        Intent intent = new Intent();
        intent.putExtra("orderId", orderId);
        intent.setClass(mContext, OrderDetailActivity.class);
        startActivity(intent);

    }

    private void cancelOrder(OrderBean.ElementsBean elementsBean) {
        commonRequest(elementsBean, GlobalConfig.cancelOrder, "取消订单");

    }

    /**
     * 支付订单
     *
     * @param elementsBean 订单数据
     */
    private void payOrder(OrderBean.ElementsBean elementsBean) {


        String orderNumber = elementsBean.getOrderNumber();
        Log.e(BUG_TAG, orderNumber);
        Log.e(BUG_TAG, tokenString);
        OkGo.<String>post(GlobalConfig.payWeiXin)
                .params("access_token", tokenString)
                .params("orderNumber", orderNumber)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(BUG_TAG, response.body());
                        Type type = new TypeToken<ResponseEntity<WeiXinInfo>>() {
                        }.getType();
                        ResponseEntity<WeiXinInfo> entity = JsonUtil.fromJson(response.body(), type);
                        if (entity == null) {
                            return;
                        }
                        WeiXinInfo.WeiXinResponseBean weiXinResponse = entity.getData().getWeiXinResponse();
                        String APP_ID = weiXinResponse.getAppid();
                        IWXAPI wxapi = WXAPIFactory.createWXAPI(mContext, APP_ID, false);
                        wxapi.registerApp(APP_ID);

                        if (isWxAppInstalled()) {
                            PayReq req = new PayReq();
                            req.appId = APP_ID;
                            req.partnerId = weiXinResponse.getPartnerid();
                            req.prepayId = weiXinResponse.getPrepayid();
                            req.nonceStr = weiXinResponse.getNoncestr();
                            req.timeStamp = weiXinResponse.getTimestamp();
                            req.sign = weiXinResponse.getSign();
                            Toast.makeText(mContext, "正常调起支付", Toast.LENGTH_SHORT).show();
                            wxapi.sendReq(req);
                        }


                    }
                });
    }

    /**
     * 判断是否安装微信
     *
     * @return
     */
    private boolean isWxAppInstalled() {
        final PackageManager packageManager = mContext.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }

        Toast.makeText(mContext, "请安装微信", Toast.LENGTH_SHORT).show();
        return false;


    }

    private void pay(String orderNumber) {
        OkGo.<String>post(GlobalConfig.payZhiFuBao)
                .params("access_token", tokenString)
                .params("orderNumber", orderNumber)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(BUG_TAG, response.body());
                        Type type = new TypeToken<ResponseEntity<AliInfo>>() {
                        }.getType();
                        ResponseEntity<AliInfo> entity = JsonUtil.fromJson(response.body(), type);
                        if (entity == null) {
                            return;
                        }
                        String zhiFuBaoResponse = entity.getData().getZhiFuBaoResponse();
                        AliPay.getInstance().pay(zhiFuBaoResponse, mContext);

                    }
                });

    }

    /**
     * 确认订单
     *
     * @param elementsBean 订单数据
     */
    private void configOrder(OrderBean.ElementsBean elementsBean) {
        commonRequest(elementsBean, GlobalConfig.confirmOrder, "确认收货");
    }

    /**
     * 删除订单
     *
     * @param elementsBean 订单数据
     */
    private void deleteOrder(final OrderBean.ElementsBean elementsBean) {
        OkGo.<String>put(GlobalConfig.deleteOrder)
                .params("access_token", tokenString)
                .params("id", elementsBean.getOrderId())
                .isSpliceUrl(true)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        if (body == null) {
                            return;
                        }
                        ResponseEntity responseEntity = JsonUtil.fromJson(body, ResponseEntity.class);
                        switch (responseEntity != null ? responseEntity.getResponseCode() : 0) {
                            case 1001:
                                Snackbar.make(mRootView, "删除成功", Snackbar.LENGTH_SHORT).show();
                                requestData();
                                break;
                            default:
                                Snackbar.make(mRootView, "删除失败", Snackbar.LENGTH_LONG)
                                        .setAction("重试", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                deleteOrder(elementsBean);
                                            }
                                        }).show();
                                break;
                        }
                    }
                });

    }

    /**
     * 通用的网络请求   取消订单，删除订单，确认收货共用
     *
     * @param elementsBean 用于操作的数据类型
     * @param url          网络请求路径
     * @param prompt       提示信息
     */
    private void commonRequest(final OrderBean.ElementsBean elementsBean, String url, final String prompt) {
        OkGo.<String>put(url)
                .params("access_token", tokenString)
                .params("id", elementsBean.getOrderId())
                .isSpliceUrl(true)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        if (body == null) {
                            return;
                        }
                        ResponseEntity responseEntity = JsonUtil.fromJson(body, ResponseEntity.class);
                        switch (responseEntity != null ? responseEntity.getResponseCode() : 0) {
                            case 1001:
                                Snackbar.make(mRootView, prompt + "成功", Snackbar.LENGTH_SHORT).show();
                                requestData();
                                break;
                            default:
                                Snackbar.make(mRootView, prompt + "失败", Snackbar.LENGTH_LONG)
                                        .setAction("重试", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                deleteOrder(elementsBean);
                                            }
                                        }).show();
                                break;
                        }
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
                Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                requestData();
                break;
            case SimpleEvent.PAY_DEFAULT:
                Log.e(BUG_TAG, "支付失败");
                Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
