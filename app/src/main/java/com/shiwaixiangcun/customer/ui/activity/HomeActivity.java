package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.google.gson.reflect.TypeToken;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.ComListAdapter;
import com.shiwaixiangcun.customer.broadCast.RegisterBrodUtils;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.imageloader.GlideImageLoader;
import com.shiwaixiangcun.customer.model.AnnouncementBean;
import com.shiwaixiangcun.customer.model.BannerBean;
import com.shiwaixiangcun.customer.model.InformationBean;
import com.shiwaixiangcun.customer.model.PageBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.model.WeatherDataBean;
import com.shiwaixiangcun.customer.presenter.IHomePresenter;
import com.shiwaixiangcun.customer.presenter.impl.HomePresenterImpl;
import com.shiwaixiangcun.customer.ui.IHomeView;
import com.shiwaixiangcun.customer.utils.DecoratorViewPager;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.NoFastClickUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.pullableview.MyListener;
import com.shiwaixiangcun.customer.widget.pullableview.PullToRefreshLayout;
import com.shiwaixiangcun.customer.widget.pullableview.PullableListView;
import com.squareup.picasso.Picasso;
import com.yyydjk.library.BannerLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by fyj on 2017/05/22.
 */
public class HomeActivity extends BaseActivity implements IHomeView, ViewPager.OnPageChangeListener, View.OnClickListener, ListView.OnItemClickListener, ListView.OnScrollListener {

    private final long TIME_INTERVAL = 8000L;
    private IHomePresenter ihomePresenter;
    private PullableListView lv_details;
    private List<String> list_home = new ArrayList<>();
    private DecoratorViewPager viewPager;
    /**
     * 装点点的ImageView数组
     */
    private ImageView[] tips;
    /**
     * 装ImageView数组
     */
    private ImageView[] mImageViews;
    /**
     * 图片资源id
     */
//    private int[] imgIdArray;


    private boolean isLooper = false;
    private ViewGroup group;
    private TextView tv_online_service;
    private TextView tv_house_renting;
    private Intent intent;
    private TextView tv_look_decorating;
    private TextView tv_health;
    private TextView tv_announcement;
    private TextView tv_more_more;
    private ViewAnimator viewAnimator;


    private boolean autoPlayFlag = false;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (autoPlayFlag) {

                showNext();
            }
            handler.sendMessageDelayed(new Message(), TIME_INTERVAL);
        }
    };
    private List<AnnouncementBean> elements_headline;
    private ChangeLightImageView back_left;
    private List<BannerBean> list_banner;
    private RelativeLayout rl_net_not;
    private TextView tv_page_name;
    private int i_ata = 0;
    private RelativeLayout rl_home_banner;
    private BannerLayout bannerLayout;
    private TextView tv_surrounding_life;
    private TextView tv_awards;
    private RelativeLayout rl_weather;
    private TextView tv_weather_home;
    private List<String> urls_image = new ArrayList<>();
    private long exitTime;
    private Toast mToast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //        百度统计
        StatService.setLogSenderDelayed(10);

        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);


        ihomePresenter = new HomePresenterImpl(this);
        ihomePresenter.setBgaAdpaterAndClick(HomeActivity.this);
        ihomePresenter.setAnnouncement(HomeActivity.this);
        ihomePresenter.setHeadline(HomeActivity.this);
        ihomePresenter.setWeatherHomeClick(HomeActivity.this, "101260209");

        layoutView();
        // 广播注册
        RegisterBrodUtils.registerReceiver(this, rl_net_not);
        initData();


    }

    private void layoutView() {
        lv_details = (PullableListView) findViewById(R.id.lv_details);
        rl_net_not = (RelativeLayout) findViewById(R.id.rl_net_not);
        MyListener myListener = new MyListener();
        PullToRefreshLayout refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        refresh_view.setOnRefreshListener(myListener);
        myListener.setRefreshListener(new MyListener.onRefreshListener() {
            @Override
            public void refreshScence(boolean isnot) {
                ihomePresenter.setBgaAdpaterAndClick(HomeActivity.this);
                ihomePresenter.setAnnouncement(HomeActivity.this);
                ihomePresenter.setHeadline(HomeActivity.this);
                ihomePresenter.setWeatherHomeClick(HomeActivity.this, "101260209");
            }
        });

        View home_head_view = LayoutInflater.from(this).inflate(R.layout.home_head_view, null);
        group = (ViewGroup) home_head_view.findViewById(R.id.viewGroup);
        viewPager = (DecoratorViewPager) home_head_view.findViewById(R.id.viewPager);
        viewPager.setNestedpParent((ViewGroup) viewPager.getParent());


        tv_online_service = (TextView) home_head_view.findViewById(R.id.tv_online_service);
        tv_house_renting = (TextView) home_head_view.findViewById(R.id.tv_house_renting);
        tv_look_decorating = (TextView) home_head_view.findViewById(R.id.tv_look_decorating);
        tv_health = (TextView) home_head_view.findViewById(R.id.tv_health);
        tv_announcement = (TextView) home_head_view.findViewById(R.id.tv_announcement);
        tv_more_more = (TextView) home_head_view.findViewById(R.id.tv_more_more);
//        looperview = (LooperTextView) home_head_view.findViewById(R.id.looperview);
        viewAnimator = (ViewAnimator) home_head_view.findViewById(R.id.animator);
        rl_home_banner = (RelativeLayout) home_head_view.findViewById(R.id.rl_home_banner);
        bannerLayout = (BannerLayout) home_head_view.findViewById(R.id.banner);
        tv_surrounding_life = (TextView) home_head_view.findViewById(R.id.tv_surrounding_life);
        tv_awards = (TextView) home_head_view.findViewById(R.id.tv_awards);
        rl_weather = (RelativeLayout) home_head_view.findViewById(R.id.rl_weather);
        tv_weather_home = (TextView) home_head_view.findViewById(R.id.tv_weather_home);

        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        tv_page_name = (TextView) findViewById(R.id.tv_page_name);

        lv_details.addHeaderView(home_head_view);

//        viewPager.setNestedpParent((ViewGroup)viewPager.getParent());
    }

    private void initData() {
        tv_page_name.setText("世外生活");

//        looperview.setTipList(generateTips());
        list_home.add("1");
        list_home.add("2");
        list_home.add("3");
        list_home.add("2");
        list_home.add("1");


        tv_online_service.setOnClickListener(this);
        tv_house_renting.setOnClickListener(this);
        tv_look_decorating.setOnClickListener(this);
        tv_health.setOnClickListener(this);
        tv_announcement.setOnClickListener(this);
        tv_more_more.setOnClickListener(this);
        lv_details.setOnItemClickListener(this);
        back_left.setOnClickListener(this);

        lv_details.setOnScrollListener(this);
        tv_surrounding_life.setOnClickListener(this);
        tv_awards.setOnClickListener(this);
        rl_weather.setOnClickListener(this);

        rl_net_not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        viewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                viewPager.getParent().requestDisallowInterceptTouchEvent(true);
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

    }

    private void initViewpager(List<String> urls_image) {
        final List<String> urls = new ArrayList<>();
        urls.add("http://img3.imgtn.bdimg.com/it/u=2674591031,2960331950&fm=23&gp=0.jpg");
        urls.add("http://img5.imgtn.bdimg.com/it/u=3639664762,1380171059&fm=23&gp=0.jpg");
        urls.add("http://img0.imgtn.bdimg.com/it/u=1095909580,3513610062&fm=23&gp=0.jpg");
        urls.add("http://img4.imgtn.bdimg.com/it/u=1030604573,1579640549&fm=23&gp=0.jpg");
        urls.add("http://img5.imgtn.bdimg.com/it/u=2583054979,2860372508&fm=23&gp=0.jpg");
        bannerLayout.setImageLoader(new GlideImageLoader());
        bannerLayout.setViewUrls(urls_image);

        //添加监听事件
        bannerLayout.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (Utils.isNotEmpty(list_banner.get(position).getLink())) {
                    Intent intent = new Intent(HomeActivity.this, BannerDetailsActivity.class);
                    intent.putExtra("bannerlink", list_banner.get(position).getLink() + "");
//                        intent.putExtra("titledetail", list_banner.get(finalPosition).get)
                    startActivity(intent);
                }
            }
        });


    }

    public void showNext() {
        viewAnimator.setOutAnimation(this, R.anim.slide_out_up);
        viewAnimator.setInAnimation(this, R.anim.slide_in_down);
        viewAnimator.showNext();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_online_service:
                String isOrnotLogin_service = SharePreference.getStringSpParams(HomeActivity.this, Common.ISORNOLOGIN, Common.SIORNOLOGIN);

                if (NoFastClickUtil.isFastClick()) {
                    //快速点击后的逻辑，可以提示用户点击太快，休息一会
                    Toast.makeText(this, "点击太快，休息一会", Toast.LENGTH_SHORT).show();
                } else {
                    if (Utils.isNotEmpty(isOrnotLogin_service)) {
                        ihomePresenter.setInformation(HomeActivity.this);

                    } else {
                        intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);


                    }
                }
                break;
            case R.id.tv_house_renting:
                String isOrnotLogin_renting = SharePreference.getStringSpParams(HomeActivity.this, Common.ISORNOLOGIN, Common.SIORNOLOGIN);
                if (Utils.isNotEmpty(isOrnotLogin_renting)) {
                    intent = new Intent(this, HouseRentingActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);

                }

                break;
            case R.id.tv_look_decorating:
                intent = new Intent(this, LookDecoratingActivity.class);
                startActivity(intent);

                break;
            case R.id.tv_health:
                String isOrnotLogin_health = SharePreference.getStringSpParams(HomeActivity.this, Common.ISORNOLOGIN, Common.SIORNOLOGIN);
                if (Utils.isNotEmpty(isOrnotLogin_health)) {
                    //住户认证
//                    intent = new Intent(this, ResidentCertificationActivity.class);
//                    startActivity(intent);
                    intent = new Intent(this, HealthOkActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);

                }


                break;
            case R.id.tv_announcement:

//                intent = new Intent(this, MoreMoreActivity.class);
//                startActivity(intent);
                break;
            case R.id.tv_more_more:
                intent = new Intent(this, CommunityAnnouncementActivity.class);
                startActivity(intent);
                break;
            case R.id.back_left:
//                String isOrnotLogin_mine = SharePreference.getStringSpParams(HomeActivity.this, Common.ISORNOLOGIN, Common.SIORNOLOGIN);
//                if (Utils.isNotEmpty(isOrnotLogin_mine)) {
                intent = new Intent(this, MymineActivity.class);
                startActivity(intent);
//                } else {
//                    intent = new Intent(this,LoginActivity.class);
//                    startActivity(intent);
//
//                }

                break;
            case R.id.tv_surrounding_life:
                intent = new Intent(HomeActivity.this, SurroundLifeActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_awards:
                intent = new Intent(HomeActivity.this, AwardActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_weather:
                intent = new Intent(HomeActivity.this, WeatherActivity.class);
                startActivity(intent);
                break;
        }

        for (int i = 0; i < 10; i++) {
            if (id == i) {
                intent = new Intent(this, MoreMoreActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void setBgaAdpaterAndClickResult(String result) {
        Log.e(BUG_TAG, result);
        Type type = new TypeToken<ResponseEntity<List<BannerBean>>>() {
        }.getType();
        ResponseEntity<List<BannerBean>> responseEntity = JsonUtil.fromJson(result, type);
        list_banner = responseEntity.getData();
        urls_image.clear();
        for (int i = 0; i < list_banner.size(); i++) {
            urls_image.add(list_banner.get(i).getImagePath());
        }

        if (list_banner.size() == 0) {
            rl_home_banner.setVisibility(View.GONE);
        } else {
            rl_home_banner.setVisibility(View.VISIBLE);
            initViewpager(urls_image);
        }

    }

    @Override
    public void setAnnouncementResult(ResponseEntity<PageBean<AnnouncementBean>> result) {
        final List<AnnouncementBean> elements_ann = result.getData().getElements();
        for (int i = 0; i < elements_ann.size(); i++) {
            TextView textView = new TextView(this);
            textView.setText(elements_ann.get(i).getTitle());
            textView.setLines(1);
            textView.setSingleLine(true);
            textView.setTextSize(14);
            textView.setId(i);
            textView.setTextColor(Color.parseColor("#2D3230"));
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setOnClickListener(this);
            viewAnimator.addView(textView);
            final int finalI = i;
            final int finalI1 = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HomeActivity.this, DetailsActivity.class);
                    intent.putExtra("articleId", elements_ann.get(finalI1).getId() + "");
                    intent.putExtra("detailtitle", elements_ann.get(finalI1).getTitle());
                    intent.putExtra("detailcontent", elements_ann.get(finalI1).getSummary());

                    startActivity(intent);
                }
            });
        }

        handler.sendMessageDelayed(new Message(), TIME_INTERVAL);
        autoPlayFlag = true;
    }

    @Override
    public void setHeadlineResult(ResponseEntity<PageBean<AnnouncementBean>> result) {
        elements_headline = result.getData().getElements();
        ComListAdapter comListAdapter = new ComListAdapter(this, elements_headline);
        lv_details.setAdapter(comListAdapter);
    }

    @Override
    public void setInformationResult(InformationBean result) {
        if (result != null) {
            boolean propertyAuth = result.getData().isPropertyAuth();
            if (propertyAuth) {
                i_ata = 1;
            } else {
                i_ata = 2;
            }


            if (i_ata == 2) {
                SharePreference.saveStringToSpParams(this, Common.ISRESIDENT, Common.SIRESIDENT, "online");
                intent = new Intent(this, ResidentCertificationActivity.class);

                startActivityForResult(intent, 1009);
            } else if (i_ata == 1) {
                intent = new Intent(this, OnlineServiceActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(HomeActivity.this, "网络异常，请稍后再试", Toast.LENGTH_LONG).show();
            }


        }
    }

    @Override
    public void setHomeWeatherClick(WeatherDataBean result) {
        String temperature = result.getRealtime().getWeather().getTemperature();
        String info = result.getRealtime().getWeather().getInfo();

        tv_weather_home.setText(info + " " + temperature + "°C");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("articleId", elements_headline.get(i - 1).getId() + "");
        intent.putExtra("detailtitle", elements_headline.get(i - 1).getTitle() + "");
        intent.putExtra("detailcontent", elements_headline.get(i - 1).getSummary());
        startActivity(intent);

    }

    private void InitViewpager(List<BannerBean> list_banner) {
        group.removeAllViews();
        for (int i = 0; i < list_banner.size(); i++) {
        }

        //载入图片资源ID
//        imgIdArray = new int[]{R.mipmap.photo, R.mipmap.photo, R.mipmap.photo, R.mipmap.photo,
//                R.mipmap.photo, R.mipmap.photo, R.mipmap.photo, R.mipmap.photo};


        //将点点加入到ViewGroup中
        tips = new ImageView[list_banner.size()];
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(10, 10));
            tips[i] = imageView;
            if (i == 0) {
                tips[i].setBackgroundResource(R.mipmap.vpbtoa);
            } else {
                tips[i].setBackgroundResource(R.mipmap.vpbtob);
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
            group.addView(imageView, layoutParams);

        }


        //将图片装载到数组中
        mImageViews = new ImageView[list_banner.size()];
        for (int i = 0; i < mImageViews.length; i++) {
            ImageView imageView = new ImageView(this);
            mImageViews[i] = imageView;
            Picasso.with(this).load(list_banner.get(i).getImagePath()).into(imageView);
//            imageView.setBackgroundResource(imgIdArray[i]);
        }
        if (mImageViews.length != 0) {
            //设置Adapter
            viewPager.setAdapter(new MyAdapter());
            //设置监听，主要是设置点点的背景
            viewPager.setOnPageChangeListener(this);
            //设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
            viewPager.setCurrentItem((mImageViews.length) * 100);
        }


        //开启一个线程，用于循环
        new Thread(new Runnable() {
            @Override
            public void run() {
                isLooper = mImageViews.length > 1;

                while (isLooper) {
                    try {
                        Thread.sleep(11000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //这里是设置当前页的下一页
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        setImageBackground(arg0 % mImageViews.length);

    }

    /**
     * 设置选中的tip的背景
     *
     * @param selectItems
     */
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < tips.length; i++) {
            if (i == selectItems) {
                tips[i].setBackgroundResource(R.mipmap.vpbtoa);
            } else {
                tips[i].setBackgroundResource(R.mipmap.vpbtob);
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

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

        if (i == 0) {
            tv_page_name.setText("世外生活");
        } else {
            tv_page_name.setText("热点资讯");
        }
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 1500) {
            mToast = Toast.makeText(this, "再点一次退出", Toast.LENGTH_SHORT);
            mToast.show();
            exitTime = System.currentTimeMillis();
        } else {
            if (mToast != null) {
                mToast.cancel();
            }
            finish();
            System.exit(0);
        }
    }

    /**
     * @author xiaanming
     */
    public final class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
//            ((ViewPager) container).removeView(mImageViews[position % mImageViews.length]);

        }

        /**
         * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
         */
        @Override
        public Object instantiateItem(View container, int position) {
//            ((ViewPager) container).addView(mImageViews[position % mImageViews.length], 0);
//            return mImageViews[position % mImageViews.length];
            position %= mImageViews.length;
            if (position < 0) {
                position = mImageViews.length + position;
            }
            ImageView view = mImageViews[position % mImageViews.length];

            final int finalPosition = position;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.isNotEmpty(list_banner.get(finalPosition).getLink())) {
                        Intent intent = new Intent(HomeActivity.this, BannerDetailsActivity.class);
                        intent.putExtra("bannerlink", list_banner.get(finalPosition).getLink() + "");
//                        intent.putExtra("titledetail", list_banner.get(finalPosition).get)
                        startActivity(intent);
                    }

                }
            });
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
            ViewParent vp = view.getParent();
            if (vp != null) {
                ViewGroup parent = (ViewGroup) vp;
                parent.removeView(view);
            }

            ((ViewPager) container).addView(mImageViews[position % mImageViews.length], 0);
//            container.addView(view);
            //add listeners here if necessary


            return mImageViews[position % mImageViews.length];


        }


    }


}
