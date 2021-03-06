package com.shiwaixiangcun.customer.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.shiwaixiangcun.customer.model.Site;
import com.shiwaixiangcun.customer.model.ToolCategoryBean;
import com.shiwaixiangcun.customer.ui.activity.BannerDetailsActivity;
import com.shiwaixiangcun.customer.ui.activity.CommunityAnnouncementActivity;
import com.shiwaixiangcun.customer.ui.activity.DetailsActivity;
import com.shiwaixiangcun.customer.ui.activity.LoginActivity;
import com.shiwaixiangcun.customer.ui.activity.MessageActivity;
import com.shiwaixiangcun.customer.ui.activity.MoreToolsActivity;
import com.shiwaixiangcun.customer.ui.activity.RecipeActivity;
import com.shiwaixiangcun.customer.ui.activity.RegisterDetailActivity;
import com.shiwaixiangcun.customer.ui.activity.SiteActivity;
import com.shiwaixiangcun.customer.ui.activity.SurroundLifeActivity;
import com.shiwaixiangcun.customer.ui.activity.ToolsDetailActivity;
import com.shiwaixiangcun.customer.ui.activity.heath.IntelligentCareActivity;
import com.shiwaixiangcun.customer.ui.activity.mall.GoodDetailActivity;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.DisplayUtil;
import com.shiwaixiangcun.customer.utils.GlideImageLoader;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.widget.AutoScrollView;
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

import static com.chad.library.adapter.base.BaseQuickAdapter.ALPHAIN;

/**
 * @author Administrator
 * @date 2017/10/17
 */

public class FragmentMain extends BaseFragment implements View.OnClickListener {

    private static final int UPDATE_BANNER = 3;
    private static final int UPDATE_ANNOUNCEMENT = 2;
    private static final int UPDATE_TOOL = 4;
    private static String BUG_TAG = "fragment_main";
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
    List<NoticeBean> elementList = new ArrayList<>();
    List<ToolCategoryBean.ChildrenBeanX> firstTool = new ArrayList<>();
    List<View> views = new ArrayList<>();
    private RecyclerView mRvTools;
    private TextView tvMore;
    private Banner mBanner;
    private List<ToolCategoryBean.ChildrenBeanX> mToolList = new ArrayList<>();
    private AdapterMain mAdapterMain;
    private List<AdapterMain.MultipleItem> mMainList;
    private Context mContext;
    private Intent intent;
    private AutoScrollView mAutoScrollView;
    private int currentPage;
    private int pageSize;
    private String siteName;
    private int siteId;

    @Override
    public void onResume() {
        super.onResume();
        currentPage = GlobalConfig.first_page;
        pageSize = GlobalConfig.page_size;
        siteId = (int) AppSharePreferenceMgr.get(mContext, GlobalConfig.CURRENT_SITE_ID, 0);
        siteName = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.CURRENT_SITE_NAME, "");
        initData();
    }

    protected void initViewsAndEvents(View view) {
        siteId = (int) AppSharePreferenceMgr.get(mContext, GlobalConfig.CURRENT_SITE_ID, 0);
        siteName = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.CURRENT_SITE_NAME, "");
        initHeader(view);
        mIvMessage.setOnClickListener(this);
        mLlayoutSite.setOnClickListener(this);
        mTvLocation.setText(siteName);
        mMainList = new ArrayList<>();
        mAdapterMain = new AdapterMain(mMainList);
        mRvMain.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapterMain.openLoadAnimation(ALPHAIN);
        mAdapterMain.isFirstOnly(true);
        mAdapterMain.addHeaderView(viewHeader);
        mAdapterMain.addHeaderView(viewTools);
        mAdapterMain.addHeaderView(viewAnnouncement);
        mAdapterMain.addHeaderView(viewBanner);
        mRvMain.setAdapter(mAdapterMain);
        RecyclerViewDivider divider = new RecyclerViewDivider.Builder(this.getActivity())
                .setOrientation(RecyclerViewDivider.VERTICAL)
                .setStyle(RecyclerViewDivider.Style.END)
                .setMarginLeft(16)
                .setMarginRight(16)
                .setColorRes(R.color.color_divider_0_3)
                .build();
        mRvMain.addItemDecoration(divider);


        mAdapterMain.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AdapterMain.MultipleItem item = (AdapterMain.MultipleItem) adapter.getData().get(position);
                NoticeBean data = item.getData();

                switch (data.getArticleShowType()) {
                    case "ACTIVITY":

                        Bundle bundle = new Bundle();

                        bundle.putInt("activityID", data.getId());
                        readyGo(RegisterDetailActivity.class, bundle);
                        break;
                    default:
                        Intent intent = new Intent(mContext, DetailsActivity.class);
                        intent.putExtra("articleId", data.getId() + "");
                        intent.putExtra("detailTitle", data.getTitle() + "");
                        intent.putExtra("detailContent", data.getSummary());
                        startActivity(intent);
                        break;
                }


            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore();
                refreshlayout.finishRefresh();
                currentPage = 1;
                initData();


            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (currentPage == 1) {
                    currentPage += 1;
                }
                refreshlayout.finishRefresh();
                requestHeadLineData(currentPage, pageSize, true);

            }
        });


    }

    @SuppressLint("WrongViewCast")
    private void initHeader(View view) {
        viewHeader = LayoutInflater.from(mContext).inflate(R.layout.layout_header_main, null);
        viewTools = LayoutInflater.from(mContext).inflate(R.layout.layout_header_tools, null);
        viewAnnouncement = LayoutInflater.from(mContext).inflate(R.layout.layout_header_announce, null);
        viewBanner = LayoutInflater.from(mContext).inflate(R.layout.layout_home_banner, null);
        mRvTools = viewTools.findViewById(R.id.rv_tools);
        mBanner = viewBanner.findViewById(R.id.banner_second);

        tvMore = viewAnnouncement.findViewById(R.id.tv_more);
        mAutoScrollView = viewAnnouncement.findViewById(R.id.up_view);
        tvMore.setOnClickListener(this);
        mAdapterTool = new AdapterTool(R.layout.layout_tool);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(), 4);
        mRvTools.setLayoutManager(gridLayoutManager);
        mRvTools.setAdapter(mAdapterTool);
        mRvTools.addItemDecoration(new MarginDecoration(mContext));
        mAdapterTool.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToolCategoryBean.ChildrenBeanX item = (ToolCategoryBean.ChildrenBeanX) adapter.getData().get(position);
                String jsonItem = JsonUtil.toJson(item);
                Bundle bundle = new Bundle();

                //如果点击全部服务
                if (position == adapter.getData().size() - 1) {
                    readyGo(MoreToolsActivity.class);
                } else {


                    switch (item.getSign()) {
                        //周边生活
                        case "VICINITY_LIFE":
                            readyGo(SurroundLifeActivity.class);
                            break;
                        //养生食谱
                        case "HEALTH_RECIPES":
                            bundle.putInt("current", 0);
                            readyGo(RecipeActivity.class, bundle);
                            break;
                        //生活缴费
                        case "ONLINE_PAYMENT":
                            bundle.putString("item", jsonItem);
                            bundle.putBoolean("show", true);
                            readyGo(ToolsDetailActivity.class, bundle);
                            break;

                        //智能关爱
                        case "INTELLIGENT_CARE":
                            bundle.putString("item", jsonItem);
                            readyGo(IntelligentCareActivity.class, bundle);
                            break;


                        default:
                            bundle.putString("item", jsonItem);
                            bundle.putBoolean("show", false);
                            readyGo(ToolsDetailActivity.class, bundle);
                            break;

                    }

                }

            }
        });
    }

    private void requestToolsData() {

        OkGo.<String>get(GlobalAPI.getToolCategory)
                .params("siteId", siteId)
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
                                EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_MAIN, UPDATE_TOOL, responseEntity.getData()));
                                break;
                            default:
                                break;

                        }


                    }
                });
    }


    /**
     * 设置Banner
     */

    private void requestBanner() {
        OkGo.<String>get(Common.listpage)
                .params("position", GlobalConfig.home_02)
                .params("siteId", siteId)
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
                        EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_MAIN, UPDATE_BANNER, list));

                    }
                });


    }

    /**
     * 设置社区公告
     */

    private void requestAnnouncement() {
        OkGo.<String>get(Common.articleListpage)
                .params("page.pn", currentPage)
                .params("page.size", 3)
                .params("siteId", siteId)
                .params("position", "COMMUNITY_ANNOUNCEMENT")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        // 分页列表
                        Type type = new TypeToken<ResponseEntity<PageBean<NoticeBean>>>() {
                        }.getType();
                        ResponseEntity<PageBean<NoticeBean>> announcementData = JsonUtil.fromJson(response.body(), type);
                        EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_MAIN, UPDATE_ANNOUNCEMENT, announcementData));
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
    private void requestHeadLineData(int page, int pageSize, final boolean loadMore) {
        OkGo.<String>get(Common.articleListpage)
                .params("page.pn", page)
                .params("page.size", pageSize)
                .params("siteId", siteId)
                .params("position", "COMMUNITY_HEADLINES")
                .execute(new StringCallback() {

                    @Override
                    public void onError(Response<String> response) {

                        mRefreshLayout.finishLoadmore();
                        mRefreshLayout.finishRefresh();

                    }

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
                                    mRefreshLayout.finishLoadmore(true);
                                } else {
                                    currentPage = 1;
                                    mRefreshLayout.finishRefresh(true);
                                    mAdapterMain.getData().clear();
                                    mAdapterMain.notifyDataSetChanged();
                                    EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_MAIN, 1, headLine));

                                }


                                break;
                            default:
                                Toast.makeText(mContext, "获取数据出错", Toast.LENGTH_SHORT).show();
                                mRefreshLayout.finishLoadmore(false);
                                mRefreshLayout.finishRefresh(false);
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
            case UPDATE_ANNOUNCEMENT:
                setNoticeData(simpleEvent);
                break;
            //Banner
            case UPDATE_BANNER:
                setBannerData(simpleEvent);
                break;

            case UPDATE_TOOL:
                setTools(simpleEvent);
                break;

            case 6:
                Site site = (Site) simpleEvent.getData();
                break;
            default:
                break;


        }

    }

    public void setTools(SimpleEvent simpleEvent) {
        mAdapterTool.clearData();
        mToolList.clear();
        firstTool.clear();

        ToolCategoryBean toolCategoryBean = (ToolCategoryBean) simpleEvent.getData();
        firstTool = toolCategoryBean.getChildren();

        ToolCategoryBean.ChildrenBeanX allItem = new ToolCategoryBean.ChildrenBeanX();
        allItem.setName("更多服务");
        firstTool.add(allItem);

        mToolList.addAll(firstTool);

        mAdapterTool.addData(mToolList);
        mAdapterTool.notifyDataSetChanged();
    }

    /**
     * 设置Banner数据
     *
     * @param simpleEvent
     */
    private void setBannerData(SimpleEvent simpleEvent) {
        final List<BannerBean> dataList = (List<BannerBean>) simpleEvent.getData();
        List<String> imageList = new ArrayList<>();
        if (dataList.size() == 0) {
            imageList.clear();
            mBanner.setVisibility(View.GONE);


        } else {
            imageList.clear();
            mBanner.setVisibility(View.VISIBLE);
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


    }

    @Override
    public void onStop() {
        super.onStop();
        mBanner.stopAutoPlay();
    }

    /**
     * 设置Notice数据
     *
     * @param simpleEvent
     */


    private void setNoticeData(SimpleEvent simpleEvent) {
        ResponseEntity<PageBean<NoticeBean>> NoticeData = (ResponseEntity<PageBean<NoticeBean>>) simpleEvent.getData();

        views.clear();
        elementList.clear();
        elementList.addAll(NoticeData.getData().getElements());
        if (elementList.size() == 0) {
            mAutoScrollView.setAutoScroll(false);
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.item_view, null);
            //初始化布局的控件
            TextView textView = moreView.findViewById(R.id.tv_text);
            textView.setText("暂无公告");
            views.add(moreView);
        } else {

            mAutoScrollView.setAutoScroll(true);
            for (int i = 0; i < elementList.size(); i++) {
                //设置滚动的单个布局
                LinearLayout moreView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.item_view, null);
                //初始化布局的控件
                final TextView textView = moreView.findViewById(R.id.tv_text);
                textView.setText(elementList.get(i).getTitle());
                final int finalI = i;
                moreView.findViewById(R.id.tv_text).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, DetailsActivity.class);

                        intent.putExtra("articleId", elementList.get(finalI).getId() + "");
                        intent.putExtra("detailTitle", elementList.get(finalI).getTitle());
                        intent.putExtra("detailContent", elementList.get(finalI).getSummary());
                        startActivity(intent);
                    }
                });
                views.add(moreView);

            }
        }
        mAutoScrollView.setViews(views);
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


        if (elements.size() == 0) {
            return;

        } else {
            if (!loadMore) {
                mMainList.clear();
                addData(elements);
                mAdapterMain.notifyDataSetChanged();
            } else {
                addData(elements);
                mAdapterMain.notifyLoadMoreToLoading();
            }


        }
    }

    /**
     * 将数据转换化成需要数据
     *
     * @param elements
     */
    private void addData(List<NoticeBean> elements) {
        for (NoticeBean bean : elements) {
            AdapterMain.MultipleItem multipleItem;
            switch (bean.getArticleShowType()) {
                case "ARTICLE_IMAGE":
                    multipleItem = new AdapterMain.MultipleItem(1, bean);
                    break;
                case "ACTIVE":
                    multipleItem = new AdapterMain.MultipleItem(3, bean);
                    break;

                case "ACTIVITY":
                    multipleItem = new AdapterMain.MultipleItem(4, bean);
                    break;

                default:
                    multipleItem = new AdapterMain.MultipleItem(3, bean);
                    break;
            }

            mMainList.add(multipleItem);
        }
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
            Intent intent = new Intent(mContext, BannerDetailsActivity.class);
            intent.putExtra("bannerlink", linkUrl);
            startActivity(intent);
        }
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initViewsAndEvents(view);

    }

    private void initData() {
        requestHeadLineData(currentPage, pageSize, false);
        requestBanner();
        requestAnnouncement();
        initSite();
        requestToolsData();

    }

    /**
     * 获取站点
     */
    private void initSite() {
        OkGo.<String>get(GlobalAPI.getSite)
                .params("fields", "id,name,defaultShow")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Type type = new TypeToken<ResponseEntity<List<Site>>>() {
                        }.getType();
                        ResponseEntity<List<Site>> responseEntity = JsonUtil.fromJson(response.body(), type);
                        if (responseEntity == null) {
                            return;
                        }
                        switch (responseEntity.getResponseCode()) {
                            case 1001:
                                if (responseEntity.getData().size() > 0) {
                                    Site site = responseEntity.getData().get(0);
                                    EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_MAIN, 6, site));

                                }
                                break;
                            default:
                                break;
                        }
                    }
                });

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
            default:
                break;
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    class MarginDecoration extends RecyclerView.ItemDecoration {
        private int margin;

        public MarginDecoration(Context context) {
            margin = DisplayUtil.dip2px(context, 0);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(margin, 0, margin, 0);
        }
    }


}
