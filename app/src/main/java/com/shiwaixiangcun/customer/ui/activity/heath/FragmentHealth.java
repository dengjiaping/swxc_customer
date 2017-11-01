package com.shiwaixiangcun.customer.ui.activity.heath;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.HealthUserBean;
import com.shiwaixiangcun.customer.ui.fragment.BaseFragment;
import com.shiwaixiangcun.customer.utils.DateUtil;
import com.shiwaixiangcun.customer.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/9/26.
 * <p>
 * 健康z状况
 */

public class FragmentHealth extends BaseFragment implements View.OnClickListener {
    private static String BUG_TAG = "fragment—health";

    HealthUserBean mHealthUserBean;

    boolean visible = false;
    @BindView(R.id.iv_health_status)
    ImageView mIvHealthStatus;
    @BindView(R.id.tv_center_health)
    TextView mTvCenterHealth;
    @BindView(R.id.tv_bottom_health)
    TextView mTvBottomHealth;
    @BindView(R.id.tv_bp_name)
    TextView mTvBpName;
    @BindView(R.id.tv_bp_detail)
    TextView mTvBpDetail;
    @BindView(R.id.tv_bp_data)
    TextView mTvBpData;
    @BindView(R.id.tv_bp_date)
    TextView mTvBpDate;
    @BindView(R.id.cv_bp)
    RelativeLayout mCvBp;
    @BindView(R.id.tv_p_name)
    TextView mTvPName;
    @BindView(R.id.tv_p_detail)
    TextView mTvPDetail;
    @BindView(R.id.tv_p_data)
    TextView mTvPData;
    @BindView(R.id.tv_p_date)
    TextView mTvPDate;
    @BindView(R.id.cv_p)
    RelativeLayout mCvP;
    @BindView(R.id.tv_glu_name)
    TextView mTvGluName;
    @BindView(R.id.tv_glu_detail)
    TextView mTvGluDetail;
    @BindView(R.id.tv_glu_data)
    TextView mTvGluData;
    @BindView(R.id.tv_glu_date)
    TextView mTvGluDate;
    @BindView(R.id.cv_glu)
    RelativeLayout mCvGlu;
    @BindView(R.id.tv_bw_name)
    TextView mTvBwName;
    @BindView(R.id.tv_bw_detail)
    TextView mTvBwDetail;
    @BindView(R.id.tv_bw_data)
    TextView mTvBwData;
    @BindView(R.id.tv_bw_other)
    TextView mTvBwOther;
    @BindView(R.id.tv_bw_date)
    TextView mTvBwDate;
    @BindView(R.id.cv_bw)
    RelativeLayout mCvBw;
    @BindView(R.id.tv_tc_name)
    TextView mTvTcName;
    @BindView(R.id.tv_tc_detail)
    TextView mTvTcDetail;
    @BindView(R.id.tv_tcho_data)
    TextView mTvTchoData;
    @BindView(R.id.tv_tg_data)
    TextView mTvTgData;
    @BindView(R.id.tv_hdl_data)
    TextView mTvHdlData;
    @BindView(R.id.tv_ldl_data)
    TextView mTvLdlData;
    @BindView(R.id.llayout_tc_detail)
    LinearLayout mLlayoutTcDetail;
    @BindView(R.id.tv_tc_date)
    TextView mTvTcDate;
    @BindView(R.id.cv_tc)
    RelativeLayout mCvTc;
    Unbinder unbinder;

    public static FragmentHealth getInstance() {

        FragmentHealth fragmentHealth = new FragmentHealth();
        return fragmentHealth;
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View view = inflater.inflate(R.layout.fragment_health, container, false);
        Bundle bundle = getArguments();//从activity传过来的Bundle
        if (bundle != null) {
            visible = bundle.getBoolean("visible");
            mHealthUserBean = bundle.getParcelable("health");
        }

        Log.e(BUG_TAG, mHealthUserBean.getAvatar());
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData(mHealthUserBean);
        initView();
    }

    private void initView() {
        mCvBp.setOnClickListener(this);
        mCvGlu.setOnClickListener(this);
        mCvP.setOnClickListener(this);
        mCvBw.setOnClickListener(this);
        mCvTc.setOnClickListener(this);
    }

    /**
     * 设置用户的数据
     *
     * @param healthUserBean
     */
    private void initData(HealthUserBean healthUserBean) {
        if (visible) {
            mIvHealthStatus.setVisibility(View.VISIBLE);
        } else {
            mIvHealthStatus.setVisibility(View.GONE);
        }
        if (healthUserBean.getTotalStatus() != null) {

            switch (healthUserBean.getTotalStatus()) {
                case "NORMAL":
                    mIvHealthStatus.setImageResource(R.mipmap.heart_green);
                    mTvCenterHealth.setText("非常好");
                    break;
                case "WARNING":
                    mIvHealthStatus.setImageResource(R.mipmap.health_jx);
                    mTvCenterHealth.setText("警告");
                    break;
                case "DANGER":
                    mIvHealthStatus.setImageResource(R.mipmap.health_wx);
                    mTvCenterHealth.setText("紧急");
                    break;
            }
        }

        mTvBottomHealth.setText(healthUserBean.getSuggestion() + "");
        //进行血压初始化
        setVisible(healthUserBean.getPressureStatus(), mCvBp);
        mTvBpName.setText("血压");
        mTvBpDetail.setText("(mmHg)");
        mTvBpData.setText(String.format("%d/%d", healthUserBean.getShrinkBlood(), healthUserBean.getRelaxationBlood()));
        mTvBpDate.setText(DateUtil.getSecond(healthUserBean.getPressureCreateTime()) + "");
        //初始化心率
        setVisible(healthUserBean.getHeartRateStatus(), mCvP);
        mTvPName.setText("心率");
        mTvPDetail.setText("(bpm)");
        mTvPData.setText(String.valueOf(healthUserBean.getHeartRate()) + "");
        mTvPDate.setText(DateUtil.getSecond(healthUserBean.getHeartRateTime()));

        //初始化血糖
        setVisible(healthUserBean.getSugarStatus(), mCvGlu);
        mTvGluData.setText("血糖");
        mTvGluDetail.setText("(mmol/L)");
        mTvGluData.setText(String.valueOf(healthUserBean.getBloodSugar()));
        mTvGluDate.setText(DateUtil.getSecond(healthUserBean.getBloodCreateTime()));
        //初始化体重的值
        setVisible(healthUserBean.getBmiStatus(), mCvBw);
        mTvBwName.setText("体重");
        mTvBwDetail.setText("(kg)");
        mTvBwData.setText(String.valueOf(healthUserBean.getWeight()));
        mTvBwOther.setText("BMI " + String.valueOf(healthUserBean.getBmi()));
        mTvBwDate.setText(DateUtil.getSecond(healthUserBean.getBmiCreateTime()));

        //初始化血脂
        setVisible(healthUserBean.getBloodStatus(), mCvTc);
        mTvTcDetail.setText("(mmol/L)");
        mTvTcName.setText("血脂");
        mTvTcDate.setText(DateUtil.getSecond(healthUserBean.getBloodCreateTime()));
        mTvLdlData.setText(String.valueOf(healthUserBean.getLowLipo()));
        mTvTchoData.setText(String.valueOf(healthUserBean.getTotalCholesterol()));
        mTvTgData.setText(String.valueOf(healthUserBean.getTriglyceride()));
        mTvHdlData.setText(String.valueOf(healthUserBean.getTopLipo()));

    }

    /**
     * 设置控件颜色以及是否可见
     *
     * @param status 状态
     * @param view   需要判定的布局
     */
    private void setVisible(String status, RelativeLayout view) {
        if (StringUtil.isEmpty(status)) {
            view.setVisibility(View.GONE);
            return;
        }
        switch (status) {
            case "DANGER":
                view.setBackground(getResources().getDrawable(R.drawable.shape_red_corner_gradient));
                break;
            case "NORMAL":
                view.setBackground(getResources().getDrawable(R.drawable.shape_green_corner_gradient));
                break;
            case "WARNING":
                view.setBackground(getResources().getDrawable(R.drawable.shape_yellow_corner_gradient));
                break;

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("customID", mHealthUserBean.getCustomerId());
        switch (view.getId()) {
            //跳转至血压详情界面
            case R.id.cv_bp:
                readyGo(BloodPressureActivity.class, bundle);
                break;
            //跳转至体重详情界面
            case R.id.cv_bw:
                readyGo(WeightActivity.class, bundle);
                break;
            //跳转至血糖详情页面
            case R.id.cv_glu:
                readyGo(BloodSugarActivity.class, bundle);
                break;
            //跳转至心率详情界面
            case R.id.cv_p:
                readyGo(HeartRateActivity.class, bundle);
                break;
            //跳转至血脂详情界面
            case R.id.cv_tc:
                readyGo(BloodFatActivity.class, bundle);
                break;
        }

    }
}
