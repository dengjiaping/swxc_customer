package com.shiwaixiangcun.customer.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterSpecification;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.interfaces.TagClick;
import com.shiwaixiangcun.customer.model.GoodDetail;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.model.StockBean;
import com.shiwaixiangcun.customer.ui.activity.ConfirmOrderActivity;
import com.shiwaixiangcun.customer.utils.ArithmeticUtils;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.utils.JsonUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;



public class DialogSku extends Dialog implements DialogInterface.OnCancelListener, View.OnClickListener {

    private final String BUG_TAG = "skuDialog";
    @BindView(R.id.iv_good_cover)
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
    private List<GoodDetail.DataBean.SpecificationsBean> mSpecificationList;
    private StringBuilder nameBuilder = new StringBuilder();
    private List<String> mNameList = new ArrayList<>();

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
        mAdapter = new AdapterSpecification(mContext, mGoodId);
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
        HttpRequest.get(GlobalConfig.getGoodDetail, hashMap, new HttpCallBack() {
            @Override
            public void onSuccess(String responseJson) {
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
        mTvStockSku.setText("价格：");
        mTvStockSku.setText("库存： ");
        mTvResultSku.setText("请选择： ");
        ImageDisplayUtil.showImageView(mContext, data.getImages().get(0).getThumbImageURL(), mIvCoverSku);
        List<GoodDetail.DataBean.SpecificationsBean> specifications = data.getSpecifications();
        for (GoodDetail.DataBean.SpecificationsBean attributesBean : specifications) {
            nameBuilder.append(attributesBean.getName()).append(",");
        }
        if (nameBuilder.length() > 1) {
            nameBuilder.deleteCharAt(nameBuilder.length() - 1);
        }
        mTvResultSku.setText("请选择： " + nameBuilder.toString());
        mSpecificationList.addAll(specifications);
        mAdapter.addData(mSpecificationList);
        mAdapter.setTagClick(new TagClick() {
            @Override
            public void onTagClick() {
                requestStock(mAdapter.getIdBuilder().toString());
            }


        });
        mAdapter.notifyDataSetChanged();
        for (GoodDetail.DataBean.SpecificationsBean bean : specifications) {
            mNameList.add(bean.getName());
        }

        if (data.getImages() == null) {
            return;
        }
    }

    private void requestStock(String s) {
        OkGo.<String>get(GlobalConfig.getStock + mGoodId + ".json")
                .params("attributeIds", s)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String jsonString = response.body();
                        if (jsonString == null) {
                            return;
                        }
                        Type type = new TypeToken<ResponseEntity<StockBean>>() {
                        }.getType();
                        ResponseEntity<StockBean> stockResponseEntity = JsonUtil.fromJson(jsonString, type);
                        if (stockResponseEntity == null) {
                            return;
                        }
                        switch (stockResponseEntity.getResponseCode()) {
                            case 1001:
                                StockBean data = stockResponseEntity.getData();
                                mTvPriceSku.setText("价格: " + "¥ " + ArithmeticUtils.format(data.getMinPrice()));
                                mTvStockSku.setText("库存: " + data.getStock() + "");
                                break;
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.btn_confirm:
                HashMap<String, String> valueMap = mAdapter.getValueMap();
                HashMap<String, Integer> idMap = mAdapter.getIdMap();
                StringBuilder nameBuilder = new StringBuilder();
                StringBuilder idBuilder = new StringBuilder();
                for (String name : mNameList) {
                    idBuilder.append(idMap.get(name)).append(",");
                    Log.e(BUG_TAG, idMap.get(name) + "");

                }
                Set setValue = valueMap.keySet();
                for (Object aSetValue : setValue) {
                    String key = (String) aSetValue;
                    nameBuilder.append(key).append(":").append(valueMap.get(key)).append(";");
                }

                if (idBuilder.length() > 0) {
                    idBuilder.deleteCharAt(idBuilder.length() - 1);
                }
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("goodId", mGoodId);
                bundle.putString("value", nameBuilder.toString());
                bundle.putString("id", idBuilder.toString());
                bundle.putParcelable("goodInfo", goodDetail.getData());
                intent.putExtras(bundle);
                intent.setClass(mContext, ConfirmOrderActivity.class);
                mContext.startActivity(intent);
                dismiss();
                break;
        }
    }
}
