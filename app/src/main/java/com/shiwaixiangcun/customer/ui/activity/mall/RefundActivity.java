package com.shiwaixiangcun.customer.ui.activity.mall;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.OrderDetailBean;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.ArithmeticUtils;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RefundActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
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
    @BindView(R.id.llayout_good_info)
    LinearLayout mLlayoutGoodInfo;
    @BindView(R.id.edt_content)
    EditText mEdtContent;
    @BindView(R.id.iv_choose_image)
    ImageView mIvChooseImage;
    @BindView(R.id.hsv_images)
    HorizontalScrollView mHsvImages;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    private String strContent;
    private float rating;
    private String strToken;
    private String strRefreshToken;
    private String goodName;
    private int goodsId;
    private String orderId = "";
    private String score = "";
    private String imageIds = "";
    private OrderDetailBean mOrderDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund);
        ButterKnife.bind(this);

        initData();
        initViewAndEvent();

    }

    private void initData() {
        Bundle extras = getIntent().getExtras();
        mOrderDetail = extras.getParcelable("orderDetail");
        strToken = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.TOKEN, "");
        strRefreshToken = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.Refresh_token, "");

    }

    private void initViewAndEvent() {
        mTvPageName.setText("申请退款");
        mBackLeft.setOnClickListener(this);

        OrderDetailBean.GoodsDetailBean goodsDetailBean = mOrderDetail.getGoodsDetail().get(0);
//        OrderBean.ElementsBean.OrderDetailDtoListBean orderDetailDtoListBean = orderDetailData.getOrderDetailDtoList().get(0);
        if (goodsDetailBean == null) {
            return;
        }
        mTvGoodTitle.setText(goodsDetailBean.getGoodName());
        mTvGoodDesc.setText(goodsDetailBean.getAttrDescription());
        mTvGoodItemPrice.setText("¥ " + ArithmeticUtils.format(goodsDetailBean.getPrice()));
        mTvGoodAmount.setText("x" + goodsDetailBean.getAmount());
        ImageDisplayUtil.showImageView(mContext, goodsDetailBean.getImgPath(), mIvGoodCover);
//
//        mBtnSubmit.setOnClickListener(this);
//        goodName = orderDetailDtoListBean.getGoodsName();
//        goodsId = orderDetailDtoListBean.getGoodsId();
//        orderId = orderDetailData.getOrderNumber();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_left:
                finish();
                break;
        }
    }
}
