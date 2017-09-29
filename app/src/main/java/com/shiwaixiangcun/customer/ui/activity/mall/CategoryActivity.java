package com.shiwaixiangcun.customer.ui.activity.mall;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品分类的Activity
 */
public class CategoryActivity extends BaseActivity {

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
    @BindView(R.id.top_bar_write)
    RelativeLayout mTopBarWrite;
    @BindView(R.id.rv_category)
    RecyclerView mRvCategory;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    String goodName;

    // TODO: 2017/9/29   商品分类
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Bundle bundle = getIntent().getExtras();
        goodName = bundle.getString("goodName");
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTvPageName.setText(goodName);
    }
}
