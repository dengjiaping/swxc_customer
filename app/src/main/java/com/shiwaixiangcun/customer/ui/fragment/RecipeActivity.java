package com.shiwaixiangcun.customer.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.tablayout_tools)
    SlidingTabLayout mTablayoutTools;
    @BindView(R.id.vp_recipe_content)
    ViewPager mVpRecipeContent;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private List<String> mRecipeTitle = new ArrayList<>();
    private RecipeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);
        initViewAndEvent();
    }


//        String loginInfo = SharePreference.getStringSpParams(mContext, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN);
//        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
//        }.getType();
//        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(loginInfo, type);
//        if (responseEntity == null) {
//            return;
//        }


    private void initViewAndEvent() {
        mTvPageName.setText(R.string.recipe);
        mBackLeft.setOnClickListener(this);
        String titles[] = {"高血压", "糖尿病", "高血脂"};
        mRecipeTitle = Arrays.asList(titles);
        Log.e(BUG_TAG, mRecipeTitle.size() + "");
        for (String title : mRecipeTitle) {
            mFragments.add(RecipeFragment.getInstance(title));
        }
        mVpRecipeContent.setOffscreenPageLimit(3);
        mAdapter = new RecipeAdapter(getSupportFragmentManager());
        mVpRecipeContent.setAdapter(mAdapter);
        mTablayoutTools.setViewPager(mVpRecipeContent);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back_left:
                finish();
                break;
        }
    }

    private class RecipeAdapter extends FragmentPagerAdapter {
        public RecipeAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mRecipeTitle.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
