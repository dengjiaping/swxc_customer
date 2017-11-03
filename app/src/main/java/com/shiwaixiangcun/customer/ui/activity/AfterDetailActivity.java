package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.shiwaixiangcun.customer.adapter.AdapterRecord;
import com.shiwaixiangcun.customer.event.EventCenter;
import com.shiwaixiangcun.customer.event.SimpleEvent;
import com.shiwaixiangcun.customer.model.AfterServiceDetail;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.ArithmeticUtils;
import com.shiwaixiangcun.customer.utils.DateUtil;
import com.shiwaixiangcun.customer.utils.DisplayUtil;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.RefreshTokenUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 售后详情页面
 *
 * @author Administrator
 */

public class AfterDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.tv_stature)
    TextView mTvStature;
    @BindView(R.id.tv_number)
    TextView mTvNumber;
    @BindView(R.id.tv_good_stature)
    TextView mTvGoodStature;
    @BindView(R.id.tv_sum)
    TextView mTvSum;
    @BindView(R.id.tv_reason)
    TextView mTvReason;
    @BindView(R.id.btn_cancel_apply)
    Button mBtnCancelApply;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.hScrollView_iamge)
    HorizontalScrollView mHScrollViewImage;
    @BindView(R.id.constraintLayout)
    ConstraintLayout mConstraintLayout;
    @BindView(R.id.rv_stature)
    RecyclerView mRvStature;
    private int id;
    private String tokenString;
    private String refreshToken;

    private List<AfterServiceDetail.DataBean.RecordsBean> mRecordsList;
    private AdapterRecord mAdapterRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_detail);
        EventCenter.getInstance().register(this);
        ButterKnife.bind(this);
        initData();
        initViewAndEvent();
        requestData();
    }

    @Override
    protected void onDestroy() {
        EventCenter.getInstance().unregister(this);
        super.onDestroy();
    }

    /**
     * 请求数据
     */
    private void requestData() {

        refreshToken = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.Refresh_token, "");
        tokenString = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.TOKEN, "");
        OkGo.<String>get(GlobalAPI.afterServiceDetail)
                .params("access_token", tokenString)
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        AfterServiceDetail afterServiceDetail = JsonUtil.fromJson(response.body(), AfterServiceDetail.class);
                        if (afterServiceDetail == null) {
                            return;
                        }
                        switch (afterServiceDetail.getResponseCode()) {
                            case 1001:
                                EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.AFTER_SERVICE_DETAIL, 1, afterServiceDetail));
                                break;
                            case 1018:
                                RefreshTokenUtil.sendIntDataInvatation(mContext, refreshToken);
                                break;
                            default:
                                Log.e(BUG_TAG, "加载失败");
                                break;
                        }


                    }
                });

    }

    /**
     * 初始化视图
     */
    private void initViewAndEvent() {
        mTvPageName.setText(R.string.after_service_detail);
        mBackLeft.setOnClickListener(this);
        mBtnCancelApply.setOnClickListener(this);
        mRecordsList = new ArrayList<>();
        mAdapterRecord = new AdapterRecord(mContext, mRecordsList);
        mRvStature.setLayoutManager(new LinearLayoutManager(this));
        mRvStature.setAdapter(mAdapterRecord);


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(SimpleEvent simpleEvent) {
        if (simpleEvent == null || simpleEvent.mEventType != SimpleEvent.AFTER_SERVICE_DETAIL) {
            return;
        }
        switch (simpleEvent.mEventValue) {
            case 1:
                AfterServiceDetail bean = (AfterServiceDetail) simpleEvent.getData();
                if (bean == null) {
                    return;
                }
                AfterServiceDetail.DataBean.DescriptionBean description = bean.getData().getDescription();

                mTvNumber.setText(description.getNumber());

                if ("".equals(description.getContent()) || description.getContent() == null) {
                    mTvReason.setText("退款原因:  用户没有填写原因");
                } else {
                    mTvReason.setText("退款原因:  " + description.getContent());
                }
                mTvSum.setText("退款金额:  ¥" + ArithmeticUtils.format(description.getPrice()));
                mTvDate.setText(DateUtil.getSecond(description.getAfterSaleDate()));


                switch (description.getStatus()) {
                    case "RefundSuccess":
                        mTvStature.setText("退款成功");
                        break;
                    case "Pending":
                        mTvStature.setText("退款中");
                        mBtnCancelApply.setVisibility(View.VISIBLE);
                        mBtnCancelApply.setOnClickListener(this);
                        break;
                    case "RefundFailed":
                        mTvStature.setText("退款失败");
                        break;
                    case "CancelServer":
                        mTvStature.setText("服务取消");
                        break;
                    default:
                        break;
                }

                switch (description.getGoodsStatus()) {
                    case "Received":
                        mTvGoodStature.setText("货物状态:  " + "已收货");
                        break;
                    case "NotReceived":
                        mTvGoodStature.setText("货物状态:  " + "未收货");
                        break;
                    default:
                        break;
                }
                if (description.getImages().size() == 0) {
                    mHScrollViewImage.setVisibility(View.GONE);
                } else {
                    mHScrollViewImage.setVisibility(View.VISIBLE);
                    LinearLayout linearLayout = new LinearLayout(mContext);
                    for (int i = 0; i < description.getImages().size(); i++) {
                        ImageView imageView = new ImageView(this);
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DisplayUtil.dip2px(mContext, 80), DisplayUtil.dip2px(mContext, 80));
                        layoutParams.setMargins(0, 0, DisplayUtil.dip2px(mContext, 10), 0);
                        imageView.setLayoutParams(layoutParams);
                        ImageDisplayUtil.showImageView(mContext, description.getImages().get(i).getThumbImageURL(), imageView);
                        linearLayout.addView(imageView);
                    }
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.gravity = Gravity.CENTER_VERTICAL;
                    linearLayout.setLayoutParams(params);
                    mHScrollViewImage.addView(linearLayout);
                }

                mRecordsList.addAll(bean.getData().getRecords());
                mAdapterRecord.notifyDataSetChanged();
                break;
            default:
                break;


        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Bundle extras = getIntent().getExtras();
        id = extras.getInt("id", 0);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_left:
                finish();
                break;
            case R.id.btn_cancel_apply:
                cancelApply();
                break;
            default:
                break;
        }
    }

    /**
     * 取消申请
     */
    private void cancelApply() {
        OkGo.<String>put(GlobalAPI.cancelAfterService)
                .params("access_token", tokenString)
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        ResponseEntity responseEntity = JsonUtil.fromJson(response.body(), ResponseEntity.class);
                        if (responseEntity == null) {
                            return;
                        }
                        switch (responseEntity.getResponseCode()) {
                            case 1001:
                                showToastShort("取消成功");
                                finish();
                                break;
                            case 1018:
                                RefreshTokenUtil.sendIntDataInvatation(mContext, refreshToken);
                                break;
                            default:
                                Log.e(BUG_TAG, "加载失败");
                                break;
                        }

                    }
                });

    }
}
