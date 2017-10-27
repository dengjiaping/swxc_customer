package com.shiwaixiangcun.customer.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.RecipeTypeBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.ui.fragment.RecipeFragment;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.tablayout.SlidingTabLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Administrator
 */
public class RecipeActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.tablayout_tools)
    SlidingTabLayout mTablayoutTools;
    @BindView(R.id.vp_recipe_content)
    ViewPager mVpRecipeContent;
    private List<String> mRecipeTitle;
    private List<Integer> mIdList;
    private RecipeAdapter mAdapter;
    private ArrayList<Fragment> mFragments;
    private int currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);
        initData();
        initViewAndEvent();
    }

    private void initViewAndEvent() {
        mTvPageName.setText(R.string.recipe);
        mBackLeft.setOnClickListener(this);

    }

    private void initData() {


        currentItem = getIntent().getExtras().getInt("current", 0);
        mFragments = new ArrayList<>();
        mRecipeTitle = new ArrayList<>();
        mIdList = new ArrayList<>();
        mAdapter = new RecipeAdapter(getSupportFragmentManager(), mRecipeTitle, mFragments);
        mVpRecipeContent.setOffscreenPageLimit(3);
        mVpRecipeContent.setCurrentItem(currentItem);
        mVpRecipeContent.setAdapter(mAdapter);
        mTablayoutTools.setViewPager(mVpRecipeContent);
        OkGo.<String>get(GlobalAPI.getRecipeType).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> responseString) {
                Type type = new TypeToken<ResponseEntity<List<RecipeTypeBean>>>() {
                }.getType();
                ResponseEntity<List<RecipeTypeBean>> responseEntity = JsonUtil.fromJson(responseString.body(), type);
                if (responseEntity == null) {
                    return;
                }
                switch (responseEntity.getResponseCode()) {
                    case 1001:
                        for (RecipeTypeBean bean : responseEntity.getData()) {
                            mRecipeTitle.add(bean.getName());
                            mIdList.add(bean.getId());
                            mFragments.add(RecipeFragment.getInstance(bean.getName(), bean.getId()));
                        }
                        Log.e(BUG_TAG, mRecipeTitle.size() + "");
                        mVpRecipeContent.post(new Runnable() {
                            @Override
                            public void run() {
                                mTablayoutTools.notifyDataSetChanged();
                                mAdapter.notifyDataSetChanged();
                                mVpRecipeContent.setCurrentItem(currentItem);
                            }
                        });
                        break;
                    default:
                        showToastShort("获取类别失败");
                        break;
                }
            }
        });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back_left:
                finish();
                break;
            default:
                break;
        }
    }

    private class RecipeAdapter extends FragmentPagerAdapter {
        private List<String> mNameList;
        private ArrayList<Fragment> mFragments;

        public RecipeAdapter(FragmentManager fm, List<String> recipeTitle, ArrayList<Fragment> fragments) {
            super(fm);
            this.mNameList = recipeTitle;
            this.mFragments = fragments;
        }

        @Override
        public int getCount() {
            return mNameList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mNameList.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
