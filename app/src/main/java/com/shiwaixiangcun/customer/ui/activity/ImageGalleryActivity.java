package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.widget.ZoomImageView;

import java.util.ArrayList;

/**
 * http://www.2cto.com/kf/201608/532939.html
 *
 * @author Administrator
 */

public class ImageGalleryActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ZoomImageView zivImage;
    private ImageView ivCloseBigImage;
    private String bigimage;

    private ViewPager mViewPager;
    private ArrayList<String> mImageList;
    private ImageView[] mImageViews;
    private ViewGroup group;
    private ImageView[] tips;
    private int serid;
    private String titleImage = "";
    private TextView tvTitleImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_iamge);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        Intent intent = getIntent();
        mImageList = new ArrayList<>();
        mImageList.addAll(intent.getStringArrayListExtra("imageList"));
        titleImage = intent.getStringExtra("imageTitle");
        serid = intent.getIntExtra("serid", 100);
        mImageViews = new ImageView[mImageList.size()];
        initViewAndEvent();
        initData();
        mViewPager.setCurrentItem(0);
    }

    private void initViewAndEvent() {
        zivImage = findViewById(R.id.ziv_image);
        ivCloseBigImage = findViewById(R.id.iv_close_big_image);
        group = findViewById(R.id.viewGroup);
        mViewPager = findViewById(R.id.id_viewpager);
        tvTitleImage = findViewById(R.id.tv_title_image);
    }

    private void initData() {
        tvTitleImage.setText(titleImage);
        mData();

        if (Utils.isNotEmpty(bigimage)) {
            ImageDisplayUtil.showImageView(this, bigimage, zivImage);
        }

        ivCloseBigImage.setOnClickListener(this);


        mViewPager.setOnPageChangeListener(this);
        mViewPager.setAdapter(new PagerAdapter() {

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ZoomImageView imageView = new ZoomImageView(
                        getApplicationContext());
                ImageDisplayUtil.showImageView(ImageGalleryActivity.this, mImageList.get(position), imageView);
                container.addView(imageView);
                mImageViews[position] = imageView;
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(mImageViews[position]);
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return mImageList.size();
            }
        });


    }

    private void mData() {
        //将点点加入到ViewGroup中
        tips = new ImageView[mImageList.size()];
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(8, 8));
            tips[i] = imageView;
            if (i == 0) {
                tips[i].setBackgroundResource(R.mipmap.image_wright);
            } else {
                tips[i].setBackgroundResource(R.mipmap.image_black);
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 8;
            layoutParams.rightMargin = 8;
            group.addView(imageView, layoutParams);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_close_big_image:
                finish();
                break;
            default:
                break;
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setImageBackground(position % mImageViews.length);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    /**
     * 设置选中的tip的背景
     *
     * @param selectItems
     */
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < tips.length; i++) {
            if (i == selectItems) {
                tips[i].setBackgroundResource(R.mipmap.image_wright);
            } else {
                tips[i].setBackgroundResource(R.mipmap.image_black);
            }
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
