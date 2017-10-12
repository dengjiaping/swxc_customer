package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.ui.fragment.FragmentHome;
import com.shiwaixiangcun.customer.ui.fragment.FragmentMall;
import com.shiwaixiangcun.customer.ui.fragment.FragmentMe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/21.
 * HOME   页面
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.main_content)
    FrameLayout mMainContent;
    @BindView(R.id.llayout_home)
    LinearLayout mLlayoutHome;
    @BindView(R.id.llayout_mall)
    LinearLayout mLlayoutMall;
    @BindView(R.id.llayout_me)
    LinearLayout mLlayoutMe;
    @BindView(R.id.layout_bottom_bar)
    LinearLayout mLayoutBottomBar;
    private FragmentHome mFragmentHome;
    private FragmentMall mFragmentMall;
    private FragmentMe mFragmentMe;
    private long exitTime;
    private Toast mToast;
    private FragmentManager mFragmentManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);
        initView();
    }


    private void initView() {
        mLlayoutHome.setOnClickListener(this);
        mLlayoutMall.setOnClickListener(this);
        mLlayoutMe.setOnClickListener(this);
        mFragmentManager = getSupportFragmentManager();
        setTabSelected(0);
        setSelect(mLlayoutHome, true);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llayout_home:
                setSelect(mLlayoutHome, true);
                setSelect(mLlayoutMall, false);
                setSelect(mLlayoutMe, false);
                setTabSelected(0);
                break;
            case R.id.llayout_mall:
                setSelect(mLlayoutHome, false);
                setSelect(mLlayoutMall, true);
                setSelect(mLlayoutMe, false);
                setTabSelected(1);
                break;
            case R.id.llayout_me:
                setSelect(mLlayoutHome, false);
                setSelect(mLlayoutMall, false);
                setSelect(mLlayoutMe, true);
                setTabSelected(2);
                break;
        }
    }

    private void setTabSelected(int position) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (position) {
            case 0:
                if (mFragmentHome == null) {
                    mFragmentHome = new FragmentHome();
                    transaction.add(R.id.main_content, mFragmentHome);
                } else {
                    transaction.show(mFragmentHome);
                }
                break;
            case 1:
                if (mFragmentMall == null) {
                    mFragmentMall = new FragmentMall();
                    transaction.add(R.id.main_content, mFragmentMall);
                } else {
                    transaction.show(mFragmentMall);
                }
                break;
            case 2:
                if (mFragmentMe == null) {
                    mFragmentMe = new FragmentMe();
                    transaction.add(R.id.main_content, mFragmentMe);
                } else {
                    transaction.show(mFragmentMe);
                }
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mFragmentHome != null) {
            transaction.hide(mFragmentHome);
        }
        if (mFragmentMall != null) {
            transaction.hide(mFragmentMall);
        }
        if (mFragmentMe != null) {
            transaction.hide(mFragmentMe);
        }

    }


    /**
     * 设置选中状态
     *
     * @param view       选择的视图
     * @param isSelected 选择的状态
     */
    public void setSelect(View view, boolean isSelected) {
        view.setSelected(isSelected);
        view.findViewById(R.id.menu_image).setSelected(isSelected);
        view.findViewById(R.id.menu_text).setSelected(isSelected);

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
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
