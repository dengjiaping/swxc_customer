package com.shiwaixiangcun.customer.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.http.StringDialogCallBack;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.OrderBean;
import com.shiwaixiangcun.customer.response.ResponseEntity;
import com.shiwaixiangcun.customer.ui.activity.OrderDetailActivity;
import com.shiwaixiangcun.customer.ui.dialog.DialogInfo;
import com.shiwaixiangcun.customer.utils.DisplayUtil;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.ShareUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/9/18.
 */

public class OrderFragment extends BaseFragment {
    Unbinder unbinder;
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
    private AdapterOrder mAdapterOrder;
    private String mTitle;
    private List<OrderBean.ElementsBean> mOrderList;
    private OrderBean mOrder;
    private Context mContext;
    private String stature;
    private String mStringPrompt;
    private String tokenString;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();

        mStringPrompt = mTitle;
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
                stature = "Closed";
                break;
        }

        String loginInfo = ShareUtil.getStringSpParams(mContext, Common.ISSAVELOGIN, Common.SISAVELOGIN);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(loginInfo, type);
        tokenString = responseEntity.getData().getAccess_token();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initViewAndEvents(view);
    }


    /**
     * 初始化视图
     *
     * @param view
     */
    protected void initViewAndEvents(View view) {
        unbinder = ButterKnife.bind(this, view);
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
                        deleteDialog.setListener(new DialogInfo.onCallBackListener() {
                            @Override
                            public void closeBtn(DialogInfo dialog) {
                                dialog.dismiss();
                            }

                            @Override
                            public void confirmBtn(DialogInfo dialog) {
                                deleteOrder(elementsBean);
                            }
                        });
                        deleteDialog.show();
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

    private void gotoDetail(int orderId) {
        Intent intent = new Intent();
        intent.putExtra("orderId", orderId);
        intent.setClass(mContext, OrderDetailActivity.class);
        startActivity(intent);

    }

    /**
     * 显示对话框
     *
     * @param title
     * @param content
     */
    private void showDialog(String title, String content) {
//        AlertDialog alertDialog=new AlertDialog();
//        alertDialog.
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


        // TODO: 2017/9/18  订单支付
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
                                iniData();
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
                                iniData();
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

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.order_fragment;
    }

    @Override
    protected void destroyViewAndThing() {

    }

    @Override
    protected void onFirstUserVisible() {
        iniData();

    }

    @Override
    protected void onUserVisible() {
        iniData();

    }

    @Override
    protected void onUserInvisible() {

    }


    /**
     * 请求网络数据
     */
    private void iniData() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("page.pn", 1);
        httpParams.put("page.size", 10);
        httpParams.put("orderStatus", stature);
        httpParams.put("access_token", tokenString);
        OkGo.<String>get(GlobalConfig.getAllOrders)
                .params(httpParams)
                .execute(new StringDialogCallBack((Activity) mContext) {
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public static Fragment getInstance(String title) {
        OrderFragment fragment = new OrderFragment();
        fragment.mTitle = title;
        return fragment;
    }

}
