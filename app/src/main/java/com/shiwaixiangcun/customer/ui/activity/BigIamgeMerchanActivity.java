package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.MerchDetailBean;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.widget.ZoomImageView;

import java.util.List;

/**
 * http://www.2cto.com/kf/201608/532939.html
 */

public class BigIamgeMerchanActivity extends AppCompatActivity implements View.OnClickListener ,ViewPager.OnPageChangeListener{

    private ZoomImageView ziv_image;
    private ImageView iv_close_big_image;
    private String bigimage;


    private ViewPager mViewPager;
    //    private int[] mImgs = new int[] { R.mipmap.add_house, R.mipmap.defalt_image,
//            R.mipmap.close };
//    private ImageView[] mImageViews = new ImageView[mImgs.length];
    private List<MerchDetailBean.DataBean.CertificateBean> mylist;
    private ImageView[] mImageViews;
    private ViewGroup group;
    private ImageView[] tips;
    private int serid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_iamge);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        Intent intent = getIntent();
        mylist = (List<MerchDetailBean.DataBean.CertificateBean>) intent.getSerializableExtra("bigimagelist");
        serid = intent.getIntExtra("serid", 100);
        mImageViews = new ImageView[mylist.size()];
//        bigimage = intent.getStringExtra("bigimage");
        layoutview();
        initData();
        mViewPager.setCurrentItem(serid);
    }

    private void layoutview() {
        ziv_image = (ZoomImageView) findViewById(R.id.ziv_image);
        iv_close_big_image = (ImageView) findViewById(R.id.iv_close_big_image);
        group = (ViewGroup) findViewById(R.id.viewGroup);
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
    }

    private void initData() {
        mData();

        if (Utils.isNotEmpty(bigimage)) {
            ImageDisplayUtil.showImageView(this, bigimage, ziv_image);
        }

        iv_close_big_image.setOnClickListener(this);



        mViewPager.setOnPageChangeListener(this);
        mViewPager.setAdapter(new PagerAdapter() {

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ZoomImageView imageView = new ZoomImageView(
                        getApplicationContext());
//                imageView.setImageResource(mImgs[position]);

                ImageDisplayUtil.showImageView(BigIamgeMerchanActivity.this, mylist.get(position).getAccessUrl(), imageView);

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
                return mylist.size();
            }
        });


    }

    private void mData() {
        //将点点加入到ViewGroup中
        tips = new ImageView[mylist.size()];
        for (int i = 0; i < tips.length; i++) {
            Log.i("bbbbbiii",i+"");
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(10, 10));
            tips[i] = imageView;
            if (i == 0) {
                tips[i].setBackgroundResource(R.mipmap.image_wright);
            } else {
                tips[i].setBackgroundResource(R.mipmap.image_black);
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
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
