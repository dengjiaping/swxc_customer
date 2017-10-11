package com.shiwaixiangcun.customer.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.ComListAdapter;
import com.shiwaixiangcun.customer.broadCast.RegisterBrodUtils;
import com.shiwaixiangcun.customer.model.AnnouncementBean;
import com.shiwaixiangcun.customer.model.BannerBean;
import com.shiwaixiangcun.customer.model.InformationBean;
import com.shiwaixiangcun.customer.model.PageBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.model.WeatherDataBean;
import com.shiwaixiangcun.customer.presenter.IHomePresenter;
import com.shiwaixiangcun.customer.presenter.impl.HomePresenterImpl;
import com.shiwaixiangcun.customer.ui.IHomeView;
import com.shiwaixiangcun.customer.ui.activity.AwardActivity;
import com.shiwaixiangcun.customer.ui.activity.BannerDetailsActivity;
import com.shiwaixiangcun.customer.ui.activity.CommunityAnnouncementActivity;
import com.shiwaixiangcun.customer.ui.activity.DetailsActivity;
import com.shiwaixiangcun.customer.ui.activity.HealthOkActivity;
import com.shiwaixiangcun.customer.ui.activity.HouseRentingActivity;
import com.shiwaixiangcun.customer.ui.activity.LoginActivity;
import com.shiwaixiangcun.customer.ui.activity.LookDecoratingActivity;
import com.shiwaixiangcun.customer.ui.activity.MoreMoreActivity;
import com.shiwaixiangcun.customer.ui.activity.OnlineServiceActivity;
import com.shiwaixiangcun.customer.ui.activity.ResidentCertificationActivity;
import com.shiwaixiangcun.customer.ui.activity.SiteActivity;
import com.shiwaixiangcun.customer.ui.activity.SurroundLifeActivity;
import com.shiwaixiangcun.customer.ui.activity.WeatherActivity;
import com.shiwaixiangcun.customer.ui.activity.mall.GoodDetailActivity;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.NoFastClickUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.widget.pullableview.MyListener;
import com.shiwaixiangcun.customer.widget.pullableview.PullToRefreshLayout;
import com.shiwaixiangcun.customer.widget.pullableview.PullableListView;
import com.shiwaixiangcun.customer.widget.tablayout.SlidingTabLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/21.
 */

public class FragmentHome extends BaseFragment implements IHomeView, View.OnClickListener, ListView.OnItemClickListener {

    private static String BUG_TAG = "fragment_home";
    private final long TIME_INTERVAL = 8000L;
    protected Activity mContext;
    private List<String> mToolsTitle = new ArrayList<>();
    private IHomePresenter ihomePresenter;
    private PullableListView lv_details;
    private List<String> list_home = new ArrayList<>();
    private SlidingTabLayout mTabTools;
    private ViewPager mVpTools;
    private TextView tv_online_service;
    private TextView tv_house_renting;
    private Intent intent;
    private TextView tv_look_decorating;
    private TextView tv_health;
    private TextView tv_announcement;
    private TextView tv_more_more;
    private ViewAnimator viewAnimator;
    private TextView mTvLocation;
    private ImageView mIvLocation;
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
    private RelativeLayout rl_net_not;
    private int i_ata = 0;
    private TextView tv_surrounding_life;
    private TextView tv_awards;
    private RelativeLayout rl_weather;
    private TextView tv_weather_home;
    private View home_head_view;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ToolsPagerAdapter mAdapter;
    private List<BannerBean> mBannerListFirst;
    private List<BannerBean> mBannerListSecond;
    private Banner mBannerFirst;
    private Banner mBannerSecond;
    private LinearLayout mLlayoutSite;

    private String siteName;

    public static Fragment getInstance() {
        FragmentHome fragmentHome = new FragmentHome();

        return fragmentHome;
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, null);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(mContext, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);
        String jiangkang = "健康服务";
        String wuye = "物业服务";
        String youxuan = "优选服务";
        mToolsTitle.add(jiangkang);
        mToolsTitle.add(wuye);
        mToolsTitle.add(youxuan);
        ihomePresenter = new HomePresenterImpl(this);
        ihomePresenter.setBannerFirst(mContext);
        ihomePresenter.setBannerSecond(mContext);
        ihomePresenter.setAnnouncement(mContext);
        ihomePresenter.setHeadline(mContext);
        ihomePresenter.setWeatherHomeClick(mContext, "101260209");
        layoutView(view);
        // 广播注册
        RegisterBrodUtils.registerReceiver(mContext, rl_net_not);
        initData();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();
    }

    private void layoutView(View view) {
        mLlayoutSite = (LinearLayout) findViewById(R.id.llayout_site);
        lv_details = (PullableListView) view.findViewById(R.id.lv_details);
        rl_net_not = (RelativeLayout) view.findViewById(R.id.rl_net_not);
        mTvLocation = (TextView) view.findViewById(R.id.tv_location);
        mIvLocation = (ImageView) view.findViewById(R.id.back_left);
        siteName = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.SITE_NAME, "天鹅堡森林公园");

        mLlayoutSite.setOnClickListener(this);
        mTvLocation.setText(siteName);
        mBannerListSecond = new ArrayList<>();
        MyListener myListener = new MyListener();
        PullToRefreshLayout refresh_view = (PullToRefreshLayout) view.findViewById(R.id.refresh_view);
        refresh_view.setOnRefreshListener(myListener);
        myListener.setRefreshListener(new MyListener.onRefreshListener() {
            @Override
            public void refreshScence(boolean isnot) {
                ihomePresenter.setBannerFirst(mContext);
                ihomePresenter.setBannerSecond(mContext);
                ihomePresenter.setAnnouncement(mContext);
                ihomePresenter.setHeadline(mContext);
                ihomePresenter.setWeatherHomeClick(mContext, "101260209");
            }
        });

        initHeader(view);

        lv_details.addHeaderView(home_head_view);
    }

    /**
     * 初始化列表头部
     *
     * @param view
     */
    private void initHeader(View view) {
        home_head_view = LayoutInflater.from(mContext).inflate(R.layout.home_head_view, null);
        tv_online_service = (TextView) home_head_view.findViewById(R.id.tv_online_service);
        tv_house_renting = (TextView) home_head_view.findViewById(R.id.tv_house_renting);
        tv_look_decorating = (TextView) home_head_view.findViewById(R.id.tv_look_decorating);
        tv_health = (TextView) home_head_view.findViewById(R.id.tv_health);
        tv_announcement = (TextView) home_head_view.findViewById(R.id.tv_announcement);
        tv_more_more = (TextView) home_head_view.findViewById(R.id.tv_more_more);
        viewAnimator = (ViewAnimator) home_head_view.findViewById(R.id.animator);
        tv_surrounding_life = (TextView) home_head_view.findViewById(R.id.tv_surrounding_life);
        tv_awards = (TextView) home_head_view.findViewById(R.id.tv_awards);
        rl_weather = (RelativeLayout) home_head_view.findViewById(R.id.rl_weather);
        tv_weather_home = (TextView) home_head_view.findViewById(R.id.tv_weather_home);
        mBannerSecond = (Banner) home_head_view.findViewById(R.id.banner_second);
        mBannerFirst = (Banner) home_head_view.findViewById(R.id.banner_first);
        mTabTools = (SlidingTabLayout) home_head_view.findViewById(R.id.tablayout_tools);
        mVpTools = (ViewPager) home_head_view.findViewById(R.id.vp_tools);
        for (String title : mToolsTitle) {
            mFragments.add(ToolFragment.getInstance(title));
        }
        mAdapter = new ToolsPagerAdapter(getFragmentManager());
        mVpTools.setAdapter(mAdapter);
        mTabTools.setViewPager(mVpTools);
    }

    private void initData() {
        list_home.add("1");
        list_home.add("2");
        list_home.add("3");
        list_home.add("2");
        list_home.add("1");


        mLlayoutSite.setOnClickListener(this);
        tv_online_service.setOnClickListener(this);
        tv_house_renting.setOnClickListener(this);
        tv_look_decorating.setOnClickListener(this);
        tv_health.setOnClickListener(this);
        tv_announcement.setOnClickListener(this);
        tv_more_more.setOnClickListener(this);
        lv_details.setOnItemClickListener(this);
        tv_surrounding_life.setOnClickListener(this);
        tv_awards.setOnClickListener(this);
        rl_weather.setOnClickListener(this);
        rl_net_not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    public void showNext() {
        viewAnimator.setOutAnimation(mContext, R.anim.slide_out_up);
        viewAnimator.setInAnimation(mContext, R.anim.slide_in_down);
        viewAnimator.showNext();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        String site = data.getStringExtra("site");
        mTvLocation.setText(site);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.llayout_site:
                Intent intent = new Intent();
                intent.setClass(mContext, SiteActivity.class);
                startActivityForResult(intent, 0x113);
                break;
            case R.id.tv_online_service:
                String isOrnotLogin_service = SharePreference.getStringSpParams(mContext, Common.ISORNOLOGIN, Common.SIORNOLOGIN);

                if (NoFastClickUtil.isFastClick()) {
                    //快速点击后的逻辑，可以提示用户点击太快，休息一会
                    Toast.makeText(mContext, "点击太快，休息一会", Toast.LENGTH_SHORT).show();
                } else {
                    if (Utils.isNotEmpty(isOrnotLogin_service)) {
                        ihomePresenter.setInformation(mContext);

                    } else {
                        intent = new Intent(mContext, LoginActivity.class);
                        startActivity(intent);


                    }
                }
                break;
            case R.id.tv_house_renting:
                String isOrnotLogin_renting = SharePreference.getStringSpParams(mContext, Common.ISORNOLOGIN, Common.SIORNOLOGIN);
                if (Utils.isNotEmpty(isOrnotLogin_renting)) {
                    intent = new Intent(mContext, HouseRentingActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);

                }

                break;
            case R.id.tv_look_decorating:
                intent = new Intent(mContext, LookDecoratingActivity.class);
                startActivity(intent);

                break;
            case R.id.tv_health:
                String isOrnotLogin_health = SharePreference.getStringSpParams(mContext, Common.ISORNOLOGIN, Common.SIORNOLOGIN);
                if (Utils.isNotEmpty(isOrnotLogin_health)) {
                    //住户认证
//                    intent = new Intent(this, ResidentCertificationActivity.class);
//                    startActivity(intent);
                    intent = new Intent(mContext, HealthOkActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);

                }


                break;
            case R.id.tv_announcement:

//                intent = new Intent(this, MoreMoreActivity.class);
//                startActivity(intent);
                break;
            case R.id.tv_more_more:
                intent = new Intent(mContext, CommunityAnnouncementActivity.class);
                startActivity(intent);
                break;

            case R.id.tv_surrounding_life:
                intent = new Intent(mContext, SurroundLifeActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_awards:
                intent = new Intent(mContext, AwardActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_weather:
                intent = new Intent(mContext, WeatherActivity.class);
                startActivity(intent);
                break;
        }

        for (int i = 0; i < 10; i++) {
            if (id == i) {
                intent = new Intent(mContext, MoreMoreActivity.class);
                startActivity(intent);
            }
        }
    }


    @Override

    public void setBannerFirst(String result) {
        Log.e(BUG_TAG, result);
        Type type = new TypeToken<ResponseEntity<List<BannerBean>>>() {
        }.getType();
        ResponseEntity<List<BannerBean>> responseEntity = JsonUtil.fromJson(result, type);
        mBannerListFirst = responseEntity.getData();
        List<String> secondImageList = new ArrayList<>();
        secondImageList.clear();
        for (BannerBean bannerBean : mBannerListFirst) {
            String imageUrl = bannerBean.getImagePath();
            secondImageList.add(imageUrl);
        }
        mBannerFirst.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImages(secondImageList)
                .setImageLoader(new com.shiwaixiangcun.customer.utils.GlideImageLoader())
                .setDelayTime(3000)
                .start();
        mBannerFirst.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerBean bannerBean = mBannerListFirst.get(position);
                String linkUrl = bannerBean.getLink();
                judgeUrl(linkUrl);
            }
        });

    }

    /**
     * 判断连接是否是商品链接
     *
     * @param linkUrl
     */

    public void judgeUrl(String linkUrl) {
        if (null == linkUrl) {
            return;
        }
        String mallUrl = GlobalAPI.getJudgeUrl;
        if (linkUrl.contains(mallUrl)) {
            Log.e(BUG_TAG, "是商品");
            //将路径通过"/"分割出来
            String[] arr1 = linkUrl.split("[/]");
            int length = arr1.length;
            //取最后一个字段
            String url = arr1[length - 1];
            String goodId = url.substring(0, url.length() - 4);
            Bundle bundle = new Bundle();
            bundle.putInt("goodId", Integer.parseInt(goodId));
            Intent intent = new Intent(mContext, GoodDetailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);

        } else {
            Log.e(BUG_TAG, "不是商品");
            Intent intent = new Intent(mContext, BannerDetailsActivity.class);
            Log.e("fragmentMall", "banner连接：" + linkUrl);
            intent.putExtra("bannerlink", linkUrl);
            startActivity(intent);
        }
    }

    @Override
    public void setBannerSecond(String result) {
        Log.e(BUG_TAG, result);
        Type type = new TypeToken<ResponseEntity<List<BannerBean>>>() {
        }.getType();
        ResponseEntity<List<BannerBean>> responseEntity = JsonUtil.fromJson(result, type);
        mBannerListSecond = responseEntity.getData();
        List<String> secondImageList = new ArrayList<>();
        secondImageList.clear();
        for (BannerBean bannerBean : mBannerListSecond) {
            String imageUrl = bannerBean.getImagePath();
            secondImageList.add(imageUrl);
        }
        mBannerSecond.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImages(secondImageList)
                .setImageLoader(new com.shiwaixiangcun.customer.utils.GlideImageLoader())
                .setDelayTime(4000)
                .start();
        mBannerSecond.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerBean bannerBean = mBannerListSecond.get(position);
                String linkUrl = bannerBean.getLink();
                judgeUrl(linkUrl);

            }
        });

    }

    @Override
    public void setAnnouncementResult(ResponseEntity<PageBean<AnnouncementBean>> result) {
        final List<AnnouncementBean> elements_ann = result.getData().getElements();
        for (int i = 0; i < elements_ann.size(); i++) {
            TextView textView = new TextView(mContext);
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
                    Intent intent = new Intent(mContext, DetailsActivity.class);

                    intent.putExtra("articleId", elements_ann.get(finalI1).getId() + "");
                    intent.putExtra("detailTitle", elements_ann.get(finalI1).getTitle());
                    intent.putExtra("detailContent", elements_ann.get(finalI1).getSummary());
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
        ComListAdapter comListAdapter = new ComListAdapter(mContext, elements_headline);
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
                SharePreference.saveStringToSpParams(mContext, Common.ISRESIDENT, Common.SIRESIDENT, "online");
                intent = new Intent(mContext, ResidentCertificationActivity.class);

                startActivityForResult(intent, 1009);
            } else if (i_ata == 1) {
                intent = new Intent(mContext, OnlineServiceActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(mContext, "网络异常，请稍后再试", Toast.LENGTH_LONG).show();
            }


        }
    }

    @Override
    public void setHomeWeatherClick(WeatherDataBean result) {
        String temperature = "";
        temperature = result.getRealtime().getWeather().getTemperature();
        String info = result.getRealtime().getWeather().getInfo();

        tv_weather_home.setText(info + " " + temperature + "°C");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        intent = new Intent(mContext, DetailsActivity.class);
        Log.e(BUG_TAG, elements_headline.get(i - 1).getTitle());
        Log.e(BUG_TAG, elements_headline.get(i - 1).getSummary());
        intent.putExtra("articleId", elements_headline.get(i - 1).getId() + "");
        intent.putExtra("detailTitle", elements_headline.get(i - 1).getTitle() + "");
        intent.putExtra("detailContent", elements_headline.get(i - 1).getSummary());
        startActivity(intent);

    }


    private class ToolsPagerAdapter extends FragmentPagerAdapter {
        public ToolsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mToolsTitle.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

}
