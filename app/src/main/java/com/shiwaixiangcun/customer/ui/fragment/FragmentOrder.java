package com.shiwaixiangcun.customer.ui.fragment;

import android.annotation.SuppressLint;
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
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.shiwaixiangcun.customer.ContextSession;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterOrder;
import com.shiwaixiangcun.customer.event.SimpleEvent;
import com.shiwaixiangcun.customer.model.OrderBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.pay.PayUtil;
import com.shiwaixiangcun.customer.ui.activity.mall.EvaluateActivity;
import com.shiwaixiangcun.customer.ui.activity.mall.OrderDetailActivity;
import com.shiwaixiangcun.customer.ui.dialog.DialogInfo;
import com.shiwaixiangcun.customer.ui.dialog.DialogPay;
import com.shiwaixiangcun.customer.utils.ArithmeticUtils;
import com.shiwaixiangcun.customer.utils.DisplayUtil;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.RefreshTokenUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2017/9/18
 * <p>
 * 订单Fragment
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
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private String BUG_TAG = this.getClass().getSimpleName();
    private AdapterOrder mAdapterOrder;
    private String mTitle;
    private List<OrderBean.ElementsBean> mOrderList = new ArrayList<>();
    private OrderBean mOrder;
    private Activity mContext;
    private String stature;
    private String mStringPrompt;
    private String tokenString;
    private String refreshToken;
    private DialogInfo mDialogCancel;
    private DialogInfo mDialogDelete;
    private DialogPay mDialogPay;
    private DialogInfo mDialogConfirm;

    private String isEvaluated = "";


    private int mCurrentPage = GlobalConfig.first_page;
    private int mPageSize = GlobalConfig.page_size;

    public static Fragment getInstance(String title) {
        FragmentOrder fragment = new FragmentOrder();
        fragment.mTitle = title;

        return fragment;
    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.order_fragment;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        initView();

    }


    /**
     * Fragment第一次可见
     */
    @Override
    protected void onFirstUserVisible() {

        requestData(mCurrentPage, mPageSize, false);

    }

    /**
     * Fragment可见状态
     */
    @Override
    protected void onUserVisible() {
        initToken();
        requestData(mCurrentPage, mPageSize, false);

    }

    @Override
    public void onStart() {
        super.onStart();
        initToken();
        requestData(mCurrentPage, mPageSize, false);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void destroyViewAndThing() {
        EventBus.getDefault().unregister(this);

    }

    /**
     * 初始化数据
     */
    @SuppressLint("SetTextI18n")
    private void initView() {
        mDialogCancel = new DialogInfo(mContext);
        mDialogDelete = new DialogInfo(mContext);
        mDialogConfirm = new DialogInfo(mContext);
        mDialogPay = new DialogPay(mContext);
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

        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (mCurrentPage == 1) {
                    mCurrentPage++;
                }
                requestData(mCurrentPage, mPageSize, true);

            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mCurrentPage = 1;
                requestData(mCurrentPage, mPageSize, false);

            }
        });
        mAdapterOrder.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                OrderBean.ElementsBean elementsBean = mOrderList.get(position);
                gotoDetail(elementsBean.getOrderId(), elementsBean.getAfterSaleId());
            }
        });
        mAdapterOrder.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                final OrderBean.ElementsBean elementsBean = (OrderBean.ElementsBean) adapter.getData().get(position);
                Button button = (Button) view;
                String string = button.getText().toString();
                switch (string) {
                    case "付款":
                        payOrder(elementsBean);
                        break;
                    case "评价":
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("order", elementsBean);
                        readyGo(EvaluateActivity.class, bundle);
                        break;
                    case "删除订单":
                        mDialogDelete.setDialogTitle("删除订单");
                        mDialogDelete.setDialogInfo("确认删除此订单？");
                        mDialogDelete.setListener(new DialogInfo.onCallBackListener() {
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
                        mDialogDelete.show();
                        break;
                    case "取消订单":
                        mDialogCancel.setDialogTitle("取消订单");
                        mDialogCancel.setDialogInfo("您确定要取消订单吗？取消订单后，订单将变为关闭状态。");
                        mDialogCancel.setListener(new DialogInfo.onCallBackListener() {
                            @Override
                            public void closeBtn(DialogInfo dialog) {
                                dialog.dismiss();
                            }

                            @Override
                            public void confirmBtn(DialogInfo dialog) {
                                cancelOrder(elementsBean);
                                dialog.dismiss();
                            }
                        });
                        mDialogCancel.show();

                        break;
                    case "确认收货":
                        mDialogConfirm.setDialogTitle("确认收货");
                        mDialogConfirm.setDialogInfo("在您确认收货前，请检查商品是否完好，确定无误无损后再确认收货，确认收货后，订单将变为完成状态");
                        mDialogConfirm.show();
                        mDialogConfirm.setListener(new DialogInfo.onCallBackListener() {
                            @Override
                            public void closeBtn(DialogInfo dialog) {
                                dialog.dismiss();
                            }

                            @Override
                            public void confirmBtn(DialogInfo dialog) {
                                configOrder(elementsBean);
                                dialog.dismiss();
                            }
                        });

                        break;
                    case "已评价":
                        showToast("已经评价过该商品");
                    default:

                        break;
                }
            }
        });


    }

    /**
     * 请求订单数据
     *
     * @param currentPage
     * @param pageSize
     * @param isLoadMore
     */
    private void requestData(int currentPage, int pageSize, final boolean isLoadMore) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("page.pn", currentPage);
        httpParams.put("page.size", pageSize);
        httpParams.put("evaluated", isEvaluated);
        httpParams.put("orderStatus", stature);
        httpParams.put("access_token", tokenString);
        OkGo.<String>get(GlobalAPI.getAllOrders)
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        mRefreshLayout.finishLoadmore();
                        mRefreshLayout.finishRefresh();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String responseJson = response.body();
                        Type listType = new TypeToken<ResponseEntity<OrderBean>>() {
                        }.getType();
                        ResponseEntity<OrderBean> orderBean = JsonUtil.fromJson(responseJson, listType);
                        if (orderBean == null) {
                            return;
                        }
                        switch (orderBean.getResponseCode()) {
                            case 1001:
                                mOrder = orderBean.getData();

                                if (mOrder.getElements().size() == 0) {
                                    mCurrentPage = 1;
                                    if (!isLoadMore) {

                                        mLlayoutNodata.setVisibility(View.VISIBLE);
                                    }
                                    mRefreshLayout.finishRefresh();
                                    mRefreshLayout.finishLoadmore();
                                    return;

                                }

                                if (mOrder.getElements().size() > 0) {
                                    if (isLoadMore) {
                                        mCurrentPage++;
                                        mRefreshLayout.finishLoadmore();

                                    } else {

                                        mCurrentPage = 1;
                                        mRefreshLayout.finishRefresh();
                                        mOrderList.clear();
                                    }
                                    mLlayoutNodata.setVisibility(View.GONE);
                                    mOrderList.addAll(mOrder.getElements());
                                    mAdapterOrder.notifyDataSetChanged();
                                }
                                break;
                            default:
                                mRefreshLayout.finishRefresh();
                                mRefreshLayout.finishLoadmore();
                                break;

                        }

                    }
                });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();
        EventBus.getDefault().register(this);
        mStringPrompt = mTitle;
        initToken();

        switch (mTitle) {
            case "全部":
                stature = "";
                break;
            case "待付款":
                stature = "WaitPay";
                break;
            case "待收货":
                stature = "Delivered";
                break;
            case "已完成":
                stature = "Finished";
                break;

            case "待评价":
                stature = "Finished";
                isEvaluated = "evaluated";
            default:
                break;
        }


    }

    /**
     * 初始化Token
     */
    private void initToken() {
        tokenString = ContextSession.getTokenString();
        refreshToken = ContextSession.getRefreshToken();
    }


    private void gotoDetail(int orderId, int afterSellId) {
        Intent intent = new Intent();
        intent.putExtra("orderId", orderId);

        intent.putExtra("afterSellId", afterSellId);
        intent.setClass(mContext, OrderDetailActivity.class);
        startActivity(intent);
    }

    private void cancelOrder(OrderBean.ElementsBean elementsBean) {
        commonRequest(elementsBean, GlobalAPI.cancelOrder, "取消订单");

    }

    /**
     * 支付订单
     *
     * @param elementsBean 订单数据
     */
    private void payOrder(final OrderBean.ElementsBean elementsBean) {
        mDialogPay.setPrice("¥" + ArithmeticUtils.format(elementsBean.getRealyPay()));
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
                        PayUtil.payWeixin(elementsBean.getOrderNumber(), tokenString, mContext);
                        break;
                    case 2:
                        PayUtil.payAli(elementsBean.getOrderNumber(), tokenString, mContext);
                        break;
                    case 0:
                        Toast.makeText(mContext, "请选择一种支付方式", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
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

    /**
     * 确认订单
     *
     * @param elementsBean 订单数据
     */
    private void configOrder(OrderBean.ElementsBean elementsBean) {
        commonRequest(elementsBean, GlobalAPI.confirmOrder, "确认收货");
    }

    /**
     * 删除订单
     *
     * @param elementsBean 订单数据
     */
    private void deleteOrder(final OrderBean.ElementsBean elementsBean) {
        OkGo.<String>put(GlobalAPI.deleteOrder)
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
                                requestData(mCurrentPage, mPageSize, false);
                                break;

                            case 1018:
                                RefreshTokenUtil.sendIntDataInvatation(mContext, refreshToken);
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
                                requestData(mCurrentPage, mPageSize, false);
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
                Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
//                readyGo(OrderDetailActivity.class);
                requestData(mCurrentPage, mPageSize, false);
                break;
            case SimpleEvent.PAY_FAIL:
                Log.e(BUG_TAG, "支付失败");
                Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


}
