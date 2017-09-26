package com.shiwaixiangcun.customer.ui.activity;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.http.StringDialogCallBack;
import com.shiwaixiangcun.customer.model.HeartateBean;
import com.shiwaixiangcun.customer.model.PageBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.ui.IHeartateView;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 心率详情界面
 */
public class HeartActivity extends BaseActivity implements View.OnClickListener, IHeartateView {

    @BindView(R.id.tv_heart_normal)
    TextView mTvHeartNormal;
    @BindView(R.id.tv_heart_data)
    TextView mTvHeartData;
    @BindView(R.id.tv_heart_create_time)
    TextView mTvHeartCreateTime;
    @BindView(R.id.ll_top_heart)
    LinearLayout mLlTopHeart;
    @BindView(R.id.tv_heart_introduce)
    TextView mTvHeartIntroduce;
    @BindView(R.id.rl_content_center)
    RelativeLayout mRlContentCenter;
    @BindView(R.id.rl_heart_little_title)
    RelativeLayout mRlHeartLittleTitle;
    @BindView(R.id.divider)
    View mDivider;
    @BindView(R.id.lv_detail_heartate)
    RecyclerView mLvDetailHeartate;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.tv_top_right)
    TextView mTvTopRight;
    @BindView(R.id.iv_share_right)
    ImageView mIvShareRight;
    @BindView(R.id.iv_sao_right)
    ImageView mIvSaoRight;
    @BindView(R.id.ll_image_right)
    LinearLayout mLlImageRight;
    @BindView(R.id.top_bar_transparent)
    RelativeLayout mTopBarTransparent;
    @BindView(R.id.activity_heartate)
    RelativeLayout mActivityHeartate;

    int customId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heartate);
        ButterKnife.bind(this);
        //百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        initView();
        initData();
    }

    private void initView() {
        mBackLeft.setOnClickListener(this);
    }

    private void initData() {

        OkGo.<String>get(GlobalConfig.getHeartRate)
                // TODO: 2017/9/26 Token获取
                .params("access_token", "62d47321f05ad0d3ce3e39c8ffddab2b")
                .params("customerId", customId)
                .params("page.pn", 1)
                .params("page.size", 7)
                .execute(new StringDialogCallBack(this) {
                    @Override
                    public void onSuccess(Response<String> response) {

                    }
                });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.back_left:
                finish();
                break;
        }
    }

    @Override
    public void setBgaAdapterAndClickResult(ResponseEntity<PageBean<HeartateBean>> result) {
        if (result.getData().getElements() != null && result.getData().getElements().size() != 0) {

            HeartateBean heartateBean = result.getData().getElements().get(0);
            Log.i("aaaaaaaaa", heartateBean.getHealthStatus() + "");
//            if (heartateBean.getHealthStatus().equals("NORMAL")){
            Resources resources = this.getResources();
            Drawable drawable = resources.getDrawable(R.drawable.back_start_end);
            Drawable drawable_a = resources.getDrawable(R.drawable.back_start_end);
//            ll_top_heart.setBackgroundDrawable(drawable);
//            head_view.setBackgroundDrawable(drawable_a);

//            }else if (heartateBean.getHealthStatus().equals("WARNING")){
//                Resources resources = this.getResources();
//                Drawable drawable = resources.getDrawable(R.drawable.back_start_end_yj);
//                Drawable drawable_a = resources.getDrawable(R.drawable.back_start_end_yj);
//                ll_top_heart.setBackgroundDrawable(drawable);
//                head_view.setBackgroundDrawable(drawable_a);
//            }

//            String statusEnum = result.getData().getElements().get(0).getStatusEnum();
//            if (statusEnum.equals("Zhengchang")){
//                tv_heart_normol.setText("心率");
//            }else if (statusEnum.equals("Piangao")){
//                tv_heart_normol.setText("心率偏高");
//            }else if (statusEnum.equals("Paindi")){
//                tv_heart_normol.setText("心率偏低");
//            }

//            result.getData().getElements().get(0).get
//            tv_heartate_data.setText(heartateBean.getHeartRate() + "");
//            tv_heart_creat_time.setText(TimeToTime.stampToDate(heartateBean.getCreateTime() + ""));
//            tv_heartate_introduce.setText(result.getData().getElements().get(0).getSuggestion());
//
//            List<HeartateBean> elements_heartate = result.getData().getElements();
//            HeatateListAdapter heatateListAdapter = new HeatateListAdapter(elements_heartate, this);
//            lv_detail_heartate.setAdapter(heatateListAdapter);

        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }
}
