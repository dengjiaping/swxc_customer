package com.shiwaixiangcun.customer.ui.activity.mall;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.OrderBean;
import com.shiwaixiangcun.customer.utils.ArithmeticUtils;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 评价页面
 */
public class EvaluateActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    OrderBean.ElementsBean orderDetailData;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        ButterKnife.bind(this);
        initData();
        initViewAndEvent();
    }

    private void initData() {
        Bundle extras = getIntent().getExtras();
        orderDetailData = extras.getParcelable("order");

    }

    private void initViewAndEvent() {
        mTvPageName.setText("评价商品");
        mBackLeft.setOnClickListener(this);
        OrderBean.ElementsBean.OrderDetailDtoListBean orderDetailDtoListBean = orderDetailData.getOrderDetailDtoList().get(0);
        if (orderDetailData == null) {
            return;
        }
        mTvGoodTitle.setText(orderDetailDtoListBean.getGoodsName());
        mTvGoodDesc.setText(orderDetailDtoListBean.getGoodsAttrDescription());
        mTvGoodItemPrice.setText("¥ " + ArithmeticUtils.format(orderDetailDtoListBean.getPrice()));
        mTvGoodAmount.setText("x" + orderDetailDtoListBean.getGoodsAmount());
        ImageDisplayUtil.showImageView(mContext, orderDetailDtoListBean.getThumbImageURL(), mIvGoodCover);
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
