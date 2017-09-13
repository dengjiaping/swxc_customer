package com.shiwaixiangcun.customer.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterSpecification;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.GoodDetail;
import com.shiwaixiangcun.customer.ui.activity.ConfirmOrderActivity;
import com.shiwaixiangcun.customer.utils.JsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/11.
 */

public class DialogSku extends Dialog implements DialogInterface.OnCancelListener, View.OnClickListener {

    private static String TAG = "skuDialog";
    @BindView(R.id.iv_cover_sku)
    ImageView mIvCoverSku;
    @BindView(R.id.tv_price_sku)
    TextView mTvPriceSku;
    @BindView(R.id.tv_stock_sku)
    TextView mTvStockSku;
    @BindView(R.id.tv_result_sku)
    TextView mTvResultSku;
    @BindView(R.id.rv_attr)
    RecyclerView mRvAttr;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;
    GoodDetail goodDetail;
    AdapterSpecification mAdapter;
    private Context mContext;
    private int mGoodId;
    private HashMap<String, GoodDetail.DataBean.SpecificationsBean.AttributesBean> goodsInfoMap;

    private List<GoodDetail.DataBean.SpecificationsBean> mSpecificationList;

    public DialogSku(@NonNull Context context) {
        super(context, R.style.AlertDialogStyle);
        this.mContext = context;
        initView();
    }


    public DialogSku(@NonNull Context context, @StyleRes int themeResId, int id) {
        super(context, themeResId);
        this.mContext = context;
        this.mGoodId = id;
        initView();
        loadData();
        initEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initEvent() {
        mBtnConfirm.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
    }

    private void initView() {
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.height = (int) (d.heightPixels * 0.7);
        lp.gravity = Gravity.BOTTOM;
        dialogWindow.setAttributes(lp);
        setContentView(R.layout.layout_sku);
        ButterKnife.bind(this);
        mSpecificationList = new ArrayList<>();
        mAdapter = new AdapterSpecification(mContext);
        mRvAttr.setLayoutManager(new LinearLayoutManager(mContext));
        mRvAttr.setAdapter(mAdapter);
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
    }

    @Override
    public void show() {
        super.show();
    }

    private void loadData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", mGoodId);
        HttpRequest.get("http://mk.shiwaixiangcun.cn/mi/goods/detail.json", hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
                Log.e("SKU", "onSuccess");
                goodDetail = JsonUtil.fromJson(responseJson, GoodDetail.class);
                if (null == goodDetail) {
                    return;
                }
                if (1001 == goodDetail.getResponseCode()) {
                    GoodDetail.DataBean data = goodDetail.getData();
                    fillData(data);
                }
            }

            @Override
            public void onFailed(Exception e) {
            }
        });
    }

    private void fillData(GoodDetail.DataBean data) {
        List<GoodDetail.DataBean.SpecificationsBean> specifications = data.getSpecifications();
        mSpecificationList.addAll(specifications);
        mAdapter.addData(mSpecificationList);
        mAdapter.notifyDataSetChanged();
        if (data.getImages() == null) {
            return;
        }
        Glide.with(mContext).load(data.getImages().get(0).getAccessUrl()).into(mIvCoverSku);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.btn_confirm:
                Intent intent = new Intent();
                intent.setClass(mContext, ConfirmOrderActivity.class);
                mContext.startActivity(intent);
                dismiss();
                break;
        }
    }
}
