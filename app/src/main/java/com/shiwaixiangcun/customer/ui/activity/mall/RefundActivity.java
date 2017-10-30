package com.shiwaixiangcun.customer.ui.activity.mall;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.OrderDetailBean;
import com.shiwaixiangcun.customer.ui.dialog.DialogLoginOut;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.ArithmeticUtils;
import com.shiwaixiangcun.customer.utils.DisplayUtil;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author xujhin
 * @date
 */
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
    @BindView(R.id.tv_money)
    EditText mTvMoney;
    @BindView(R.id.llayout_good_statue)
    RelativeLayout mLlayoutGoodStatue;
    @BindView(R.id.tv_refund_message)
    TextView mTvRefundMessage;
    @BindView(R.id.sv_body)
    ScrollView mSvBody;
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

    @SuppressLint("SetTextI18n")
    private void initViewAndEvent() {
        mTvPageName.setText("申请退款");
        mBackLeft.setOnClickListener(this);

        mLlayoutGoodStatue.setOnClickListener(this);
        mTvMoney.setOnClickListener(this);
        mIvChooseImage.setOnClickListener(this);
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
            case R.id.tv_money:

                mTvMoney.setText("");
                mSvBody.scrollBy(0, DisplayUtil.dip2px(mContext, 200));
                showToastShort("修改价格");
                break;

            case R.id.iv_choose_image:
                showToastShort("选择图片");
                break;

            case R.id.llayout_good_statue:
                final DialogLoginOut dialogLoginOut = new DialogLoginOut(mContext, R.layout.item_dialog_call_phone);
                dialogLoginOut.setTitle("货物状态");
                dialogLoginOut.setMessage("请选择当前货物状态");
                dialogLoginOut.setYesOnclickListener("已收货", new DialogLoginOut.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {

                    }
                });

                dialogLoginOut.setNoOnclickListener("未收货", new DialogLoginOut.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {

                        dialogLoginOut.dismiss();
                    }
                });
                dialogLoginOut.show();
                break;
            default:

                break;
        }
    }
}
