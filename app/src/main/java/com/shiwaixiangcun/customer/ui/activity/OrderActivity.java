package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.ui.fragment.SimpleCardFragment;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.btn_go_mall)
    Button mBtnGoMall;
    @BindView(R.id.tablayout)
    SlidingTabLayout mTablayout;
    @BindView(R.id.vp)
    ViewPager mVp;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "全部", "待付款", "待收货", "已完成"
    };
    private MyPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initEvent() {
        mBackLeft.setOnClickListener(this);
        mBtnGoMall.setOnClickListener(this);

    }

    private void initView() {
        mTvPageName.setText("我的订单");
        for (String title : mTitles) {
            mFragments.add(SimpleCardFragment.getInstance(title));
        }
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mVp.setAdapter(mAdapter);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mTablayout.setViewPager(mVp);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_left:
                finish();
                break;
            case R.id.btn_go_mall:
                readyGo(MallActivity.class);
                break;
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

}
