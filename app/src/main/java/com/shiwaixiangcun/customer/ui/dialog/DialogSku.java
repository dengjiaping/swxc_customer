package com.shiwaixiangcun.customer.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
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
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterSpecification;
import com.shiwaixiangcun.customer.http.StringDialogCallBack;
import com.shiwaixiangcun.customer.interfaces.TagClick;
import com.shiwaixiangcun.customer.model.GoodDetail;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.model.StockBean;
import com.shiwaixiangcun.customer.ui.activity.mall.ConfirmOrderActivity;
import com.shiwaixiangcun.customer.utils.ArithmeticUtils;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.utils.JsonUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private GoodDetail goodDetail;
    private AdapterSpecification mAdapter;
    private Activity mContext;
    private int mGoodId;
    private List<GoodDetail.DataBean.SpecificationsBean> mSpecificationList;
    private List<String> mNameList = new ArrayList<>();
    private StringBuilder nameBuilder = new StringBuilder();
    private StringBuilder idBuilder = new StringBuilder();

    //已经选择的属性描述
    private String chooseAttrDesc = "";
    private String chooseName = "";
    private String chooseId = "";
    private double mChooseGoodPrice;
    private boolean noStock = false;


    public DialogSku(@NonNull Activity context) {
        super(context, R.style.AlertDialogStyle);
        this.mContext = context;
        initView();
    }

    public DialogSku(@NonNull Activity context, @StyleRes int themeResId, int id) {
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
        mAdapter = new AdapterSpecification(mContext, mSpecificationList);
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

    /**
     * 根据商品 获取商品信息
     */
    private void loadData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", mGoodId);
        OkGo.<String>get(GlobalConfig.getGoodDetail)
                .params("id", mGoodId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(BUG_TAG, response.getRawCall().request().toString());
                        goodDetail = JsonUtil.fromJson(response.body(), GoodDetail.class);
                        if (null == goodDetail) {
                            return;
                        }
                        if (1001 == goodDetail.getResponseCode()) {
                            GoodDetail.DataBean data = goodDetail.getData();
                            fillData(data);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });

    }

    private void fillData(GoodDetail.DataBean data) {
        mTvPriceSku.setText("价格：" + data.getMinPrice());
        mTvStockSku.setText("库存： " + data.getStock());
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
        mAdapter.notifyDataSetChanged();
        mAdapter.setTagClick(new TagClick() {
            @Override
            public void onTagClick() {
                HashMap<String, GoodDetail.DataBean.SpecificationsBean.AttributesBean> selectedAttr = mAdapter.getSelectedAttr();
                //对用户选择的属性进行判断
                judgeSelected(selectedAttr);
                requestStock(chooseId);
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

    /**
     * 判断用户的选择
     *
     * @param selectedAttr
     */
    private void judgeSelected(HashMap<String, GoodDetail.DataBean.SpecificationsBean.AttributesBean> selectedAttr) {

        Log.e(BUG_TAG, "判断属性");
        StringBuilder nameBuilder = new StringBuilder();
        StringBuilder idBuilder = new StringBuilder();
        StringBuilder unSelectName = new StringBuilder();
        StringBuilder selectedAttrDesc = new StringBuilder();
        for (String name : mNameList) {
            if (!selectedAttr.containsKey(name)) {
                unSelectName.append(name).append(",");
            } else {
                nameBuilder.append(selectedAttr.get(name).getValue()).append(",");
                idBuilder.append(selectedAttr.get(name).getId()).append(",");
                selectedAttrDesc.append(name).append(":").append(selectedAttr.get(name).getValue()).append(",");
            }
        }
        nameBuilder.deleteCharAt(nameBuilder.length() - 1);
        idBuilder.deleteCharAt(idBuilder.length() - 1);
        selectedAttrDesc.deleteCharAt(selectedAttrDesc.length() - 1);
        chooseAttrDesc = selectedAttrDesc.toString();
        chooseName = nameBuilder.toString();
        chooseId = idBuilder.toString();

    }

    /**
     * 请求  库存
     *
     * @param s
     */
    private void requestStock(String s) {
        OkGo.<String>get(GlobalConfig.getStock + mGoodId + ".json")
                .params("attributeIds", s)
                .execute(new StringDialogCallBack(mContext) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(BUG_TAG, response.getRawCall().request().toString());
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
                                mChooseGoodPrice = data.getMinPrice();
                                mTvPriceSku.setText("价格: " + "¥ " + ArithmeticUtils.format(data.getMinPrice()));
                                mTvStockSku.setText("库存: " + data.getStock() + "");
                                noStock = data.getStock() == 0;
                                mTvResultSku.setText("已选择：  " + chooseName);
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
                if (noStock) {
                    Toast.makeText(mContext, "当前选择的商品库存为0", Toast.LENGTH_SHORT).show();
                } else {
                    if (!checkCanGo()) {
                        Toast.makeText(mContext, "请选择商品类型", Toast.LENGTH_SHORT).show();

                    } else {
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putInt("goodId", mGoodId);
                        bundle.putString("value", chooseAttrDesc);
                        bundle.putString("id", chooseId);
                        bundle.putDouble("goodPrice", mChooseGoodPrice);
                        bundle.putParcelable("goodInfo", goodDetail.getData());
                        intent.putExtras(bundle);
                        intent.setClass(mContext, ConfirmOrderActivity.class);
                        mContext.startActivity(intent);
                        dismiss();
                    }
                }
                break;
        }

    }

    private boolean checkCanGo() {
        Log.e(BUG_TAG, mAdapter.getSelectedAttr().size() + "");
        Log.e(BUG_TAG, mNameList.size() + "");
        return mAdapter.getSelectedAttr().size() >= mNameList.size();
    }
}
