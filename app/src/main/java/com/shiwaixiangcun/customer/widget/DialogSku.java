package com.shiwaixiangcun.customer.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterAttr;
import com.shiwaixiangcun.customer.http.HttpCallBack;
import com.shiwaixiangcun.customer.http.HttpRequest;
import com.shiwaixiangcun.customer.model.GoodDetail;
import com.shiwaixiangcun.customer.utils.JsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/11.
 */

public class DialogSku extends Dialog implements DialogInterface.OnCancelListener {

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
    AdapterAttr mAdapter;
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
    }

    private void initView() {
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.height = (int) (d.heightPixels * 0.8);
        lp.gravity = Gravity.BOTTOM;
        dialogWindow.setAttributes(lp);
        setContentView(R.layout.layout_sku);
        ButterKnife.bind(this);
        mSpecificationList = new ArrayList<>();
        mAdapter = new AdapterAttr(mContext);
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
                    Log.d("SKU", data.getSpecifications().size() + "");
                    fillData(data.getSpecifications());
                }
            }

            @Override
            public void onFailed(Exception e) {
                Log.e("SKU", "failed");
            }
        });
    }

    private void fillData(List<GoodDetail.DataBean.SpecificationsBean> specifications) {
        mSpecificationList.addAll(specifications);
        mAdapter.addData(mSpecificationList);
        mAdapter.notifyDataSetChanged();
//        mSpecList.removeAllViews();
//        goodsInfoMap = new HashMap<>();
//        LayoutInflater mInflater = getLayoutInflater();
//        if (null != mSpecificationList && mSpecificationList.size() > 0) {
//            for (int i = 0, size = mSpecificationList.size(); i < size; i++) {
//
//                GoodDetail.DataBean.SpecificationsBean specificationsBean = specifications.get(i);
//                View specView = mInflater.inflate(R.layout.ui_spec_item, null);
//                TextView spec_name = (TextView) specView.findViewById(R.id.spec_name);
//                final TagFlowLayout spec_list = (TagFlowLayout) specView.findViewById(R.id.spec_list);
//                spec_name.setText(specificationsBean.getName());
//                final List<GoodDetail.DataBean.SpecificationsBean.AttributesBean> attributes = specificationsBean.getAttributes();
//
//                final List<String> attrNames = new ArrayList<>();
//                for (GoodDetail.DataBean.SpecificationsBean.AttributesBean attributesBean : attributes) {
//                    attrNames.add(attributesBean.getValue());
//                }
//                spec_list.setAdapter(new TagAdapter<String>(attrNames) {
//                    @Override
//                    public View getView(FlowLayout parent, int position, String s) {
//                        View view = LayoutInflater.from(mContext).inflate(R.layout.tag_attrs, spec_list, false);
//                        TextView textView = (TextView) view.findViewById(R.id.tv_support);
//                        textView.setText(attrNames.get(position));
//                        return view;
//                    }
//                });
//                mSpecList.addView(specView);
//
//
//            }
    }


}
