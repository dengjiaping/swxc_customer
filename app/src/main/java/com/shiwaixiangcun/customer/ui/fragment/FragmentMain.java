package com.shiwaixiangcun.customer.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.jaeger.recyclerviewdivider.RecyclerViewDivider;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterMain;
import com.shiwaixiangcun.customer.adapter.AdapterTool;
import com.shiwaixiangcun.customer.event.EventCenter;
import com.shiwaixiangcun.customer.event.SimpleEvent;
import com.shiwaixiangcun.customer.model.BannerBean;
import com.shiwaixiangcun.customer.model.NoticeBean;
import com.shiwaixiangcun.customer.model.PageBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.model.ToolCategoryBean;
import com.shiwaixiangcun.customer.ui.activity.BannerDetailsActivity;
import com.shiwaixiangcun.customer.ui.activity.CommunityAnnouncementActivity;
import com.shiwaixiangcun.customer.ui.activity.DetailsActivity;
import com.shiwaixiangcun.customer.ui.activity.LoginActivity;
import com.shiwaixiangcun.customer.ui.activity.MessageActivity;
import com.shiwaixiangcun.customer.ui.activity.MoreToolsActivity;
import com.shiwaixiangcun.customer.ui.activity.SiteActivity;
import com.shiwaixiangcun.customer.ui.activity.mall.GoodDetailActivity;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.GlideImageLoader;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/10/17.
 */

public class FragmentMain extends BaseFragment implements View.OnClickListener {

    private static String BUG_TAG = "fragment_main";
    private final long TIME_INTERVAL = 8000L;
    View viewHeader = null;
    View viewTools = null;
    View viewAnnouncement = null;
    View viewBanner = null;
    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_location)
    TextView mTvLocation;
    @BindView(R.id.llayout_site)
    LinearLayout mLlayoutSite;
    @BindView(R.id.iv_message)
    ImageView mIvMessage;
    @BindView(R.id.rv_main)
    RecyclerView mRvMain;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    AdapterTool mAdapterTool;
    private RecyclerView mRvTools;
    private TextView tvMore;
    private Banner mBanner;
    private List<ToolCategoryBean.ChildrenBeanX> mToolList = new ArrayList<>();
    private AdapterMain mAdapterMain;
    private List<AdapterMain.MultipleItem> mMainList;
    private String siteName;
    private Context mContext;
    private List<String> imageList = new ArrayList<>();
    private Intent intent;
    private ViewAnimator viewAnimator;
    private int currentPage = 1;
    private int pageSize = 20;
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

    protected void initViewsAndEvents(View view) {

        initHeader(view);
        siteName = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.SITE_NAME, "天鹅堡森林公园");
        mIvMessage.setOnClickListener(this);
        mLlayoutSite.setOnClickListener(this);
        mTvLocation.setText(siteName);
        mMainList = new ArrayList<>();
        mAdapterMain = new AdapterMain(mMainList);
        mAdapterMain.addHeaderView(viewHeader);
        mAdapterMain.addHeaderView(viewTools);
        mAdapterMain.addHeaderView(viewAnnouncement);
        mAdapterMain.addHeaderView(viewBanner);
        mRvMain.setLayoutManager(new LinearLayoutManager(mContext));
        mRvMain.setAdapter(mAdapterMain);

        RecyclerViewDivider divider = new RecyclerViewDivider.Builder(this.getActivity())
                .setOrientation(RecyclerViewDivider.VERTICAL)
                .setStyle(RecyclerViewDivider.Style.END)
                .setMarginLeft(16)
                .setMarginRight(16)
                .setDrawableRes(R.drawable.divider)
                .build();
        mRvMain.addItemDecoration(divider);
        mAdapterMain.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AdapterMain.MultipleItem item = (AdapterMain.MultipleItem) adapter.getData().get(position);
                NoticeBean data = item.getData();
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra("articleId", data.getId() + "");
                intent.putExtra("detailTitle", data.getTitle() + "");
                intent.putExtra("detailContent", data.getSummary());
                startActivity(intent);

            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore();
                currentPage = 1;
                initData();

            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (currentPage == 1) {
                    currentPage++;
                }
                refreshlayout.finishRefresh();
                initHeadLineData(currentPage, pageSize, true);

            }
        });


    }

    private void initHeader(View view) {
        viewHeader = LayoutInflater.from(mContext).inflate(R.layout.layout_header_main, null);
        viewTools = LayoutInflater.from(mContext).inflate(R.layout.layout_header_tools, null);
        viewAnnouncement = LayoutInflater.from(mContext).inflate(R.layout.layout_header_announce, null);
        viewBanner = LayoutInflater.from(mContext).inflate(R.layout.layout_home_banner, null);
        mRvTools = (RecyclerView) viewTools.findViewById(R.id.rv_tools);
        mBanner = (Banner) viewBanner.findViewById(R.id.banner_second);

        tvMore = (TextView) viewAnnouncement.findViewById(R.id.tv_more);
        viewAnimator = (ViewAnimator) viewAnnouncement.findViewById(R.id.animator);

        tvMore.setOnClickListener(this);
        mAdapterTool = new AdapterTool(R.layout.layout_tool);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(), 4);
        mRvTools.setLayoutManager(gridLayoutManager);
        mRvTools.setAdapter(mAdapterTool);
        mAdapterTool.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                readyGo(MoreToolsActivity.class);
            }
        });
        initToolsData();

//        mAdapterTool.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                ToolBean toolBean = mToolList.get(position);
//                Bundle bundle = new Bundle();
//
//                String isOrNotLogin = SharePreference.getStringSpParams(mContext, Common.ISORNOLOGIN, Common.SIORNOLOGIN);
//                switch (toolBean.id) {
//                    case 1:
//                        //体征数据
//
//                        if (Utils.isNotEmpty(isOrNotLogin)) {
//                            readyGo(PhysicalActivity.class);
//                        } else {
//                            readyGo(LoginActivity.class);
//                        }
//
//
//                        break;
//                    case 2:
//                        //健康评测
//                        readyGo(HealthEvaluationActivity.class);
//                        break;
//                    case 3:
//                        //健康方案
//                        if (Utils.isNotEmpty(isOrNotLogin)) {
//                            bundle.putInt("type", 3);
//                            readyGo(WebActivity.class, bundle);
//                        } else {
//                            readyGo(LoginActivity.class);
//                        }
//
//
//                        break;
//                    case 4:
//
//                        if (Utils.isNotEmpty(isOrNotLogin)) {
//                            bundle.putInt("type", 4);
//                            readyGo(WebActivity.class, bundle);
//                        } else {
//                            readyGo(LoginActivity.class);
//                        }
//
//                        break;
//                    case 5:
//                        //预约挂号
//                        bundle.putInt("type", 5);
//                        readyGo(WebActivity.class, bundle);
//                        break;
//                    case 6:
//                        //健康食谱
////                        Toast.makeText(mContext, "暂未开通此功能", Toast.LENGTH_SHORT).show();
//                        readyGo(RecipeActivity.class);
//                        break;
//                    case 7:
//                        //在线问诊
//                        bundle.putInt("type", 7);
//                        readyGo(WebActivity.class, bundle);
//                        break;
//                    case 8:
//                        //在线报修
//                        if (Utils.isNotEmpty(isOrNotLogin)) {
//                            if (!ContextSession.isPropertyAuth()) {
//                                readyGo(ResidentCertificationActivity.class);
//                            } else {
//                                readyGo(OnlineServiceActivity.class);
//                            }
//                        } else {
//                            readyGo(LoginActivity.class);
//                        }
//                        break;
//                    case 9:
//                        //找装修
//                        readyGo(LookDecoratingActivity.class);
//                        break;
//                    case 10:
//                        //房屋租售
//
//                        if (Utils.isNotEmpty(isOrNotLogin)) {
//                            readyGo(HouseRentingActivity.class);
//                        } else {
//                            readyGo(LoginActivity.class);
//                        }
//
//
//                        break;
//                    case 11:
//                        //在线缴费
//
//                        if (Utils.isNotEmpty(isOrNotLogin)) {
//                            Toast.makeText(mContext, "暂未开通此功能", Toast.LENGTH_SHORT).show();
//                        } else {
//                            readyGo(LoginActivity.class);
//                        }
//
//
//                        break;
//                    case 12:
//                        //周边生活
//                        readyGo(MoreToolsActivity.class);
//                        break;
//
//
//                }
//            }
//        });
//

    }

    private void initToolsData() {

        OkGo.<String>get(GlobalAPI.getToolCategory)
                .params("siteId", GlobalConfig.siteID)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<ResponseEntity<ToolCategoryBean>>() {
                        }.getType();
                        ResponseEntity<ToolCategoryBean> responseEntity = JsonUtil.fromJson(response.body(), type);
                        if (responseEntity == null) {
                            return;
                        }
                        switch (responseEntity.getResponseCode()) {
                            case 1001:
                                EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_MAIN, 4, responseEntity.getData()));
                                break;

                        }


                    }
                });
    }
//        ToolBean tizheng = new ToolBean(1, "体征数据", R.drawable.home_sign);
//        mToolList.add(tizheng);
//        ToolBean jiankangceping = new ToolBean(2, "健康评测", R.drawable.home_appraisal);
//        mToolList.add(jiankangceping);
//        ToolBean jiankanfangan = new ToolBean(3, "健康方案", R.drawable.home_scheme);
//        mToolList.add(jiankanfangan);
//        ToolBean jiankangdongtai = new ToolBean(4, "健康动态", R.drawable.home_dynamic);
//        mToolList.add(jiankangdongtai);
//        ToolBean yuyueguahao = new ToolBean(5, "预约挂号", R.drawable.home_consultation);
//        mToolList.add(yuyueguahao);
//        ToolBean jiankangshipu = new ToolBean(6, "健康食谱", R.drawable.home_recipe);
//        mToolList.add(jiankangshipu);
//        ToolBean zaixianwenzhen = new ToolBean(7, "在线问诊", R.drawable.home_inquiry);
//        mToolList.add(zaixianwenzhen);
//
//        //添加物业服务数据
//        ToolBean zaixianbaoxiu = new ToolBean(8, "在线报修", R.drawable.home_repair);
//        ToolBean zhaozhuangxiu = new ToolBean(9, "找装修", R.drawable.home_fitment);
//        ToolBean fangwuzushou = new ToolBean(10, "房屋租售", R.drawable.home_rent);
//        ToolBean zaixianjiaofei = new ToolBean(11, "在线缴费", R.drawable.home_payment);
//        ToolBean zhoubianshenghuo = new ToolBean(12, "更多服务", R.drawable.tool_more);
//        mToolList.add(zaixianbaoxiu);
//        mToolList.add(zhaozhuangxiu);
//        mToolList.add(fangwuzushou);
//        mToolList.add(zaixianjiaofei);
//        mToolList.add(zhoubianshenghuo);
//
//        mAdapterTool.addData(mToolList);
//    }

    public void showNext() {
        viewAnimator.setOutAnimation(mContext, R.anim.slide_out_up);
        viewAnimator.setInAnimation(mContext, R.anim.slide_in_down);
        viewAnimator.showNext();
    }

    private void initBanner() {
        OkGo.<String>get(Common.listpage)
                .params("position", GlobalConfig.home_02)
                .params("siteId", GlobalConfig.siteID)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(BUG_TAG, response.getRawCall().request().toString());
                        String jsonString = response.body();
                        Type type = new TypeToken<ResponseEntity<List<BannerBean>>>() {
                        }.getType();
                        ResponseEntity<List<BannerBean>> responseEntity = JsonUtil.fromJson(jsonString, type);
                        if (responseEntity == null) {
                            return;
                        }
                        List<BannerBean> list = responseEntity.getData();
                        EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_MAIN, 3, list));

                    }
                });


    }

    private void initAnnouncement() {
        OkGo.<String>get(Common.articleListpage)
                .params("page.pn", 1)
                .params("page.size", 3)
                .params("position", "COMMUNITY_ANNOUNCEMENT")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        // 分页列表
                        Type type = new TypeToken<ResponseEntity<PageBean<NoticeBean>>>() {
                        }.getType();
                        ResponseEntity<PageBean<NoticeBean>> announcementData = JsonUtil.fromJson(response.body(), type);
                        EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_MAIN, 2, announcementData));
                    }
                });


    }

    /**
     * 获取头条新闻
     *
     * @param page     当前页
     * @param pageSize 每页数据量
     * @param loadMore 是否加载更多
     */
    private void initHeadLineData(int page, int pageSize, final boolean loadMore) {
        OkGo.<String>get(Common.articleListpage)
                .params("page.pn", page)
                .params("page.size", pageSize)
                .params("position", "COMMUNITY_HEADLINES")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        // 分页列表
                        Type type = new TypeToken<ResponseEntity<PageBean<NoticeBean>>>() {
                        }.getType();
                        ResponseEntity<PageBean<NoticeBean>> headLine = JsonUtil.fromJson(response.body(), type);
                        if (headLine == null) {
                            return;
                        }
                        switch (headLine.getResponseCode()) {
                            case 1001:


                                if (loadMore) {
                                    currentPage++;
                                    EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_MAIN, 5, headLine));
                                    mRefreshLayout.finishLoadmore();
                                } else {
                                    currentPage = 1;
                                    EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_MAIN, 1, headLine));
                                    mRefreshLayout.finishRefresh();
                                }


                                break;
                            default:
                                Toast.makeText(mContext, "获取数据出错", Toast.LENGTH_SHORT).show();
                                mRefreshLayout.finishLoadmore();
                                mRefreshLayout.finishRefresh();
                                break;
                        }


                    }
                });


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handlEvent(SimpleEvent simpleEvent) {
        if (simpleEvent == null) {
            return;
        }
        if (simpleEvent.mEventType != SimpleEvent.UPDATE_MAIN) {
            return;
        }
        switch (simpleEvent.mEventValue) {
            //头条新闻
            case 1:
                setDataHeadLine(simpleEvent, false);

                break;

            case 5:
                setDataHeadLine(simpleEvent, true);
                break;
            //社区公告
            case 2:
                setNoticeData(simpleEvent);
                break;
            //Banner
            case 3:
                setBannerData(simpleEvent);
                break;

            case 4:
                setTools(simpleEvent);

        }

    }

    public void setTools(SimpleEvent simpleEvent) {

        ToolCategoryBean toolCategoryBean = (ToolCategoryBean) simpleEvent.getData();
        List<ToolCategoryBean.ChildrenBeanX> firstTool = toolCategoryBean.getChildren();

        mToolList.addAll(firstTool);
        mAdapterTool.addData(firstTool);
        mAdapterTool.notifyDataSetChanged();
    }

    /**
     * 设置Banner数据
     *
     * @param simpleEvent
     */
    private void setBannerData(SimpleEvent simpleEvent) {
        final List<BannerBean> dataList = (List<BannerBean>) simpleEvent.getData();
        for (BannerBean bean : dataList) {
            imageList.add(bean.getImagePath());
        }
        mBanner.setImages(imageList)
                .setImageLoader(new GlideImageLoader())
                .setDelayTime(3000)
                .start();
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerBean bannerBean = dataList.get(position);
                String link = bannerBean.getLink();
                judgeUrl(link);
            }
        });
    }

    /**
     * 设置Notice数据
     *
     * @param simpleEvent
     */

    private void setNoticeData(SimpleEvent simpleEvent) {
        ResponseEntity<PageBean<NoticeBean>> NoticeData = (ResponseEntity<PageBean<NoticeBean>>) simpleEvent.getData();
        final List<NoticeBean> elementList = NoticeData.getData().getElements();
        for (int i = 0; i < elementList.size(); i++) {
            TextView textView = new TextView(mContext);
            textView.setText(elementList.get(i).getTitle());
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

                    intent.putExtra("articleId", elementList.get(finalI1).getId() + "");
                    intent.putExtra("detailTitle", elementList.get(finalI1).getTitle());
                    intent.putExtra("detailContent", elementList.get(finalI1).getSummary());
                    startActivity(intent);
                }
            });
        }

        handler.sendMessageDelayed(new Message(), TIME_INTERVAL);
        autoPlayFlag = true;
    }

    /**
     * 设置头条新闻
     *
     * @param simpleEvent 下拉刷新
     * @param loadMore    是否加载更多
     */
    private void setDataHeadLine(SimpleEvent simpleEvent, boolean loadMore) {
        ResponseEntity<PageBean<NoticeBean>> data = (ResponseEntity<PageBean<NoticeBean>>) simpleEvent.getData();
        List<NoticeBean> elements = data.getData().getElements();
        if (!loadMore) {
            mMainList.clear();
        }
        for (NoticeBean bean : elements) {
            AdapterMain.MultipleItem multipleItem;
            switch (bean.getArticleShowType()) {
                case "ARTICLE_IMAGE":
                    multipleItem = new AdapterMain.MultipleItem(1, bean);
                    break;
                case "ACTIVE":
                    multipleItem = new AdapterMain.MultipleItem(2, bean);
                    break;
                default:
                    multipleItem = new AdapterMain.MultipleItem(3, bean);
                    break;
            }

            mMainList.add(multipleItem);
        }

        mAdapterMain.notifyDataSetChanged();
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
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragment_home = inflater.inflate(R.layout.fragment_home, container, false);
        return fragment_home;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initViewsAndEvents(view);
        initData();
    }

    private void initData() {
        initHeadLineData(currentPage, pageSize, false);
        initBanner();
        initAnnouncement();

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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_message:
                String isOrNotLogin = SharePreference.getStringSpParams(mContext, Common.ISORNOLOGIN, Common.SIORNOLOGIN);
                if (Utils.isNotEmpty(isOrNotLogin)) {
                    readyGo(MessageActivity.class);

                } else {
                    intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.llayout_site:
                Intent intent = new Intent();
                intent.setClass(mContext, SiteActivity.class);
                startActivityForResult(intent, 0x113);
                break;
            case R.id.tv_more:
                intent = new Intent(mContext, CommunityAnnouncementActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }


}
