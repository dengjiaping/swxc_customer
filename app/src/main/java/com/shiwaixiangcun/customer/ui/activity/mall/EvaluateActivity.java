package com.shiwaixiangcun.customer.ui.activity.mall;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.OrderBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.ArithmeticUtils;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.RefreshTokenUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.ratingBar.BaseRatingBar;

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
    @BindView(R.id.ratingbar)
    BaseRatingBar mRatingbar;
    @BindView(R.id.edt_content)
    EditText mEdtContent;
    @BindView(R.id.iv_choose_image)
    ImageView mIvChooseImage;
    @BindView(R.id.hsv_images)
    HorizontalScrollView mHsvImages;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;


    String strContent;
    float rating;
    private String strToken;
    private String strRefreshToken;
    private String goodName;
    private int goodsId;
    private String orderId = "";
    private String score = "";
    private String imageIds = "";

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
        strToken = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.TOKEN, "");
        strRefreshToken = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.Refresh_token, "");

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

        mBtnSubmit.setOnClickListener(this);
        goodName = orderDetailDtoListBean.getGoodsName();
        goodsId = orderDetailDtoListBean.getGoodsId();
        orderId = orderDetailData.getOrderNumber();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_left:
                finish();
                break;
            case R.id.btn_submit:

                strContent = mEdtContent.getText().toString().trim();
                rating = mRatingbar.getRating();
                submitInfo();
                break;
        }
    }

    /**
     * 提交信息
     */
    private void submitInfo() {

        OkGo.<String>post(GlobalAPI.addEvaluate)
                .params("access_token", strToken)
                .params("content", strContent)
                .params("goodsId", goodsId)
                .params("goodsName", goodName)
                .params("imageIds", imageIds)
                .params("orderId", orderId)
                .params("score", rating)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        ResponseEntity responseEntity = JsonUtil.fromJson(response.body(), ResponseEntity.class);
                        if (responseEntity == null) {
                            return;

                        }
                        switch (responseEntity.getResponseCode()) {
                            case 1001:
                                showToastShort("提交评价成功");
                                break;
                            case 1018:
                                RefreshTokenUtil.sendIntDataInvatation(mContext, strRefreshToken);
                                break;
                            default:

                                showToastShort("提交评价失败");
                                Log.e(BUG_TAG, "加载失败");
                                break;
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        showToastShort("提交评价失败");
                    }
                });
    }
}
