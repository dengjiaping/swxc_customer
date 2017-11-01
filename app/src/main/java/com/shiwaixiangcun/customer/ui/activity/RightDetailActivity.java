package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
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
import com.shiwaixiangcun.customer.adapter.AdapterProcess;
import com.shiwaixiangcun.customer.event.EventCenter;
import com.shiwaixiangcun.customer.event.SimpleEvent;
import com.shiwaixiangcun.customer.model.RightDetailBean;
import com.shiwaixiangcun.customer.model.RightsRecordBean;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
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

public class RightDetailActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.tv_stature)
    TextView mTvStature;
    @BindView(R.id.tv_number)
    TextView mTvNumber;
    @BindView(R.id.tv_reason)
    TextView mTvReason;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.hScrollView_iamge)
    HorizontalScrollView mHScrollViewImage;
    @BindView(R.id.constraintLayout)
    ConstraintLayout mConstraintLayout;
    @BindView(R.id.rv_stature)
    RecyclerView mRvStature;

    RightsRecordBean.ElementsBean data;
    private String refreshToken;
    private String tokenString;


    private List<RightDetailBean.DataBean.ProcessBean> mProcessList;

    private AdapterProcess mAdapterRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_right_detail);
        EventCenter.getInstance().register(this);
        initData();
        ButterKnife.bind(this);
        initViewAndEvent();
        requestData(data.getId());
    }

    @Override
    protected void onDestroy() {
        EventCenter.getInstance().unregister(this);
        super.onDestroy();
    }

    /**
     * 获取详情数据
     *
     * @param id 维权消息id
     */
    private void requestData(int id) {
        OkGo.<String>get(GlobalAPI.rightDetail)
                .params("access_token", tokenString)
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        RightDetailBean bean = JsonUtil.fromJson(response.body(), RightDetailBean.class);
                        if (bean == null) {
                            return;

                        }
                        switch (bean.getResponseCode()) {
                            case 1001:
                                EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.RIGHT_DETAIL, 1, bean));
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

    private void initData() {
        Bundle extras = getIntent().getExtras();
        data = extras.getParcelable("detail");
    }

    private void initViewAndEvent() {
        mTvPageName.setText("维权记录详情");
        refreshToken = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.Refresh_token, "");
        tokenString = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.TOKEN, "");
        mProcessList = new ArrayList<>();
        mAdapterRecord = new AdapterProcess(mContext, mProcessList);
        mRvStature.setLayoutManager(new LinearLayoutManager(this));
        mRvStature.setAdapter(mAdapterRecord);
        mBackLeft.setOnClickListener(this);


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(SimpleEvent simpleEvent) {
        if (simpleEvent == null || simpleEvent.mEventType != SimpleEvent.RIGHT_DETAIL) {
            return;
        }
        switch (simpleEvent.mEventValue) {
            case 1:
                RightDetailBean bean = (RightDetailBean) simpleEvent.getData();
                if (bean == null) {
                    return;
                }

                RightDetailBean.DataBean.BaseInfoBean data = bean.getData().getBaseInfo();
                switch (data.getStatus()) {
                    case "ACCEPTED":
                        mTvStature.setBackgroundResource(R.drawable.shape_stature_green);
                        mTvStature.setText("受理中");
                        break;
                    case "FINISHED":
                        mTvStature.setBackgroundResource(R.drawable.shape_stature_gray);
                        mTvStature.setText("已完成");

                }

                mTvNumber.setText(data.getNumber());
                mTvReason.setText(data.getContent());
                mTvDate.setText(DateUtil.getSecond(data.getTime()));


                if (data.getImages().size() == 0) {
                    mHScrollViewImage.setVisibility(View.GONE);
                } else {
                    mHScrollViewImage.setVisibility(View.VISIBLE);
                    LinearLayout linearLayout = new LinearLayout(mContext);
                    for (int i = 0; i < data.getImages().size(); i++) {
                        ImageView imageView = new ImageView(this);
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DisplayUtil.dip2px(mContext, 80), DisplayUtil.dip2px(mContext, 80));
                        layoutParams.setMargins(0, 0, DisplayUtil.dip2px(mContext, 10), 0);
                        imageView.setLayoutParams(layoutParams);
                        ImageDisplayUtil.showImageView(mContext, data.getImages().get(i).getThumbImageURL(), imageView);
                        linearLayout.addView(imageView);
                    }
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.gravity = Gravity.CENTER_VERTICAL;
                    linearLayout.setLayoutParams(params);
                    mHScrollViewImage.addView(linearLayout);
                }

                mProcessList.addAll(bean.getData().getProcess());
                mAdapterRecord.notifyDataSetChanged();


        }
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
