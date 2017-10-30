package com.shiwaixiangcun.customer.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.recyclerviewdivider.RecyclerViewDivider;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterJingxuan;
import com.shiwaixiangcun.customer.adapter.AdapterMall;
import com.shiwaixiangcun.customer.event.EventCenter;
import com.shiwaixiangcun.customer.event.SimpleEvent;
import com.shiwaixiangcun.customer.model.BannerBean;
import com.shiwaixiangcun.customer.model.ElementBean;
import com.shiwaixiangcun.customer.model.Keyword;
import com.shiwaixiangcun.customer.model.MallBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.ui.activity.BannerDetailsActivity;
import com.shiwaixiangcun.customer.ui.activity.mall.GoodDetailActivity;
import com.shiwaixiangcun.customer.ui.activity.mall.GoodListActivity;
import com.shiwaixiangcun.customer.ui.activity.mall.MallCategoryActivity;
import com.shiwaixiangcun.customer.ui.activity.mall.SearchActivity;
import com.shiwaixiangcun.customer.utils.ArithmeticUtils;
import com.shiwaixiangcun.customer.utils.GlideImageLoader;
import com.shiwaixiangcun.customer.utils.ImageDisplayUtil;
import com.shiwaixiangcun.customer.utils.JsonUtil;
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
 * 商城 Fragment
 */

public class FragmentMall extends BaseFragment implements View.OnClickListener {

    private static String BUG_TAG = "fragmentMall";
    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.edt_search)
    TextView mEdtSearch;
    @BindView(R.id.rv_mall)
    RecyclerView mRvMall;
    @BindView(R.id.rlayout_search)
    RelativeLayout mRlayoutSearch;
    @BindView(R.id.activity_mall)
    LinearLayout mActivityMall;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;


    private AdapterMall mAdapterMall;
    private List<ElementBean.ElementsBean> mGuessList = new ArrayList<>();
    private List<MallBean.DataBean.DailySelectionListBean> jingxuanList = new ArrayList<>();
    private AdapterJingxuan mJingXuanAdapter;
    private View mBannerView = null;
    private View mJingxuanView = null;
    private View mSuggestView = null;
    private View mTitleView = null;

    private Keyword keyword;

    private TextView mTvPinzhiTitle;
    private TextView mTvPinzhiPrice;
    private ImageView mIvPinzhiCover;
    private ImageView mIvHotCover;
    private ImageView mIvNewCover;

    private RecyclerView mRvJingxuan;
    private Banner mBannerMall;
    private List<String> imageList = new ArrayList<>();

    private Activity mContext;

    private int pinzhiGoodID = 0;
    private int currentPage = GlobalConfig.first_page;
    private int pageSize = GlobalConfig.page_size;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);

        EventBus.getDefault().register(this);
        initHeaders();
        initView();
        requestKeyword();
        requestBanner();
        requestData();
        requestGood("GuessLike", 5, currentPage, pageSize, false);
    }


    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mall, container, false);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this.getActivity();

    }


    /**
     * 请求关键词
     */
    private void requestKeyword() {
        OkGo.<String>get(GlobalAPI.getKeyword)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String jsonString = response.body();
                        Type type = new TypeToken<ResponseEntity<Keyword>>() {
                        }.getType();
                        ResponseEntity<Keyword> data = JsonUtil.fromJson(jsonString, type);
                        if (data == null) {
                            return;
                        }
                        switch (data.getResponseCode()) {
                            case 1001:
                                EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_MALL, 2, data.getData()));
                                break;
                            default:
                                Toast.makeText(mContext, "请求数据失败", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
    }

    /**
     * 请求商品列表
     *
     * @param type   请求类型
     * @param flag   标志位
     * @param start  请求页码
     * @param size   每页数目
     * @param isPull 类型（判断是上拉加载还是，下拉刷新）
     */
    private void requestGood(String type, final int flag, int start, int size, final boolean isPull) {

        HttpParams params = new HttpParams();
        params.put("goodsSubjectValue", type);
        params.put("page.pn", start);
        params.put("page.size", size);
        params.put("siteId", GlobalConfig.siteID);
        OkGo.<String>get(GlobalAPI.getGuessLike)
                .params(params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        Log.e(BUG_TAG, response.getRawCall().request().toString());

                        String jsonString = response.body();
                        Type type = new TypeToken<ResponseEntity<ElementBean>>() {
                        }.getType();
                        Gson gson = new Gson();
                        ResponseEntity<ElementBean> data = gson.fromJson(jsonString, type);
                        if (data == null) {
                            return;
                        }
                        switch (data.getResponseCode()) {

                            case 1001:
                                if (data.getData().getElements().size() == 0) {
                                    mRefreshLayout.finishLoadmore(true);
                                    return;
                                }

                                if (isPull) {
                                    currentPage++;
                                    EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_MALL, flag, data.getData()));
                                    mRefreshLayout.finishLoadmore(true);
                                } else {
                                    currentPage = 1;
                                    EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_MALL, flag, data.getData()));
                                    mRefreshLayout.finishRefresh(true);
                                }
                                break;
                            default:
                                Toast.makeText(mContext, "获取数据失败", Toast.LENGTH_SHORT).show();
                                mRefreshLayout.finishLoadmore(false);
                                break;
                        }
                    }
                });
    }

    /**
     * 获取首页Banner
     */
    private void requestBanner() {
        HttpParams params = new HttpParams();
        params.put("position", GlobalConfig.market_01);
        params.put("siteId", GlobalConfig.siteID);
        OkGo.<String>get(GlobalAPI.getBanner)
                .params(params)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("fragmentmall", response.getRawCall().request().toString());
                        String jsonString = response.body();
                        Type type = new TypeToken<ResponseEntity<List<BannerBean>>>() {
                        }.getType();
                        ResponseEntity<List<BannerBean>> responseEntity = JsonUtil.fromJson(jsonString, type);
                        List<BannerBean> list = responseEntity.getData();
                        EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_MALL, 1, list));
                    }
                });
    }

    /**
     * 初始化各个View
     */
    private void initHeaders() {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        mBannerView = layoutInflater.inflate(R.layout.layout_mall_banner, null);
        mJingxuanView = layoutInflater.inflate(R.layout.layout_mall_jingxuan, null);
        mSuggestView = layoutInflater.inflate(R.layout.layout_mall_suggest, null);
        mTitleView = layoutInflater.inflate(R.layout.layout_mall_title, null);

        setBannerView(mBannerView);
        setJingxuanView(mJingxuanView);
        setSuggest(mSuggestView);
    }

    /**
     * 设置bannerView
     *
     * @param bannerView
     */
    private void setBannerView(View bannerView) {
        mBannerMall = (Banner) bannerView.findViewById(R.id.banner_mall);

    }


    /**
     * 设置推荐页面
     *
     * @param suggestView
     */
    private void setSuggest(View suggestView) {

        mTvPinzhiTitle = (TextView) suggestView.findViewById(R.id.tv_pinzhi_name);
        mTvPinzhiPrice = (TextView) suggestView.findViewById(R.id.tv_pinzhi_price);
        mIvPinzhiCover = (ImageView) suggestView.findViewById(R.id.iv_pinzhi_icon);
        mIvHotCover = (ImageView) suggestView.findViewById(R.id.iv_hot_cover);
        mIvNewCover = (ImageView) suggestView.findViewById(R.id.iv_new_cover);
        LinearLayout mLlayoutHot = (LinearLayout) suggestView.findViewById(R.id.llayout_hot_good);
        LinearLayout mLlayoutNew = (LinearLayout) suggestView.findViewById(R.id.llayout_new_good);
        LinearLayout mLlayoutPinzhi = (LinearLayout) suggestView.findViewById(R.id.llayout_pingzhi);
        TextView mTvMore = (TextView) suggestView.findViewById(R.id.tv_pinzhi_more);
        mLlayoutPinzhi.setOnClickListener(this);
        mLlayoutHot.setOnClickListener(this);
        mLlayoutNew.setOnClickListener(this);
        mTvMore.setOnClickListener(this);


    }


    /**
     * 请求商城首页数据
     */
    private void requestData() {
        Log.e(TAG, "请求数据");
        OkGo.<String>get(GlobalAPI.getMallHome)
                .cacheKey("mall")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        MallBean mallBean = JsonUtil.fromJson(response.body(), MallBean.class);
                        if (mallBean != null) {
                            switch (mallBean.getResponseCode()) {
                                case 1001:
                                    EventBus.getDefault().post(new SimpleEvent(SimpleEvent.UPDATE_MALL, 6, mallBean.getData()));
                                    break;
                            }

                        }
                    }

                });
    }


    /**
     * 初始化视图
     */
    private void initView() {
        mAdapterMall = new AdapterMall(mGuessList);
        mAdapterMall.addHeaderView(mBannerView);
        mAdapterMall.addHeaderView(mJingxuanView);
        mAdapterMall.addHeaderView(mSuggestView);
        mAdapterMall.addHeaderView(mTitleView);
        mRvMall.setLayoutManager(new LinearLayoutManager(mContext));

        mRvMall.setAdapter(mAdapterMall);
        RecyclerViewDivider divider = new RecyclerViewDivider.Builder(this.getActivity())
                .setOrientation(RecyclerViewDivider.VERTICAL)
                .setStyle(RecyclerViewDivider.Style.END)
                .setMarginLeft(16)
                .setMarginRight(16)
                .setDrawableRes(R.drawable.divider)
                .build();
        mRvMall.addItemDecoration(divider);
        mAdapterMall.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("goodId", mGuessList.get(position).getGoodsId());
                readyGo(GoodDetailActivity.class, bundle);
            }
        });
        mBackLeft.setOnClickListener(this);
        mRlayoutSearch.setOnClickListener(this);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore();
                currentPage = 1;
                requestData();
                requestGood("GuessLike", 5, currentPage, pageSize, false);

            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (currentPage == 1) {
                    currentPage++;
                }
                refreshlayout.finishRefresh();
                requestGood("GuessLike", 7, currentPage, pageSize, true);

            }
        });

    }

    /**
     * 设置每日精选
     *
     * @param jingxuanView
     */
    private void setJingxuanView(View jingxuanView) {
        TextView mTvMore;
        mTvMore = (TextView) jingxuanView.findViewById(R.id.tv_jingxuan_more);
        mRvJingxuan = (RecyclerView) jingxuanView.findViewById(R.id.rv_mall_jingxuan);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mJingXuanAdapter = new AdapterJingxuan(jingxuanList);
        mRvJingxuan.setLayoutManager(layoutManager);
        mRvJingxuan.setAdapter(mJingXuanAdapter);
        mTvMore.setOnClickListener(this);
        mJingXuanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("goodId", jingxuanList.get(position).getGoodsId());
                readyGo(GoodDetailActivity.class, bundle);
            }
        });


    }


    /**
     * 从服务器获取数据以后更新
     *
     * @param simpleEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(SimpleEvent simpleEvent) {
        if (simpleEvent.mEventType != SimpleEvent.UPDATE_MALL) {
            return;
        }
        switch (simpleEvent.mEventValue) {
            case 1:
                final List<BannerBean> dataList = (List<BannerBean>) simpleEvent.getData();
                for (BannerBean bean : dataList) {
                    imageList.add(bean.getImagePath());
                }
                mBannerMall.setImages(imageList)
                        .setImageLoader(new GlideImageLoader())
                        .setDelayTime(3000)
                        .start();
                mBannerMall.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {

                        BannerBean bannerBean = dataList.get(position);
                        String link = bannerBean.getLink();
                        judgeUrl(link);


                    }
                });
                break;
            case 2:
                keyword = (Keyword) simpleEvent.getData();
                mEdtSearch.setText(keyword.getGuide());
                break;

            //精选商品下拉刷新
            case 5:
                ElementBean guessData = (ElementBean) simpleEvent.getData();
                mGuessList.clear();
                mGuessList.addAll(guessData.getElements());
                mAdapterMall.notifyDataSetChanged();
                mRefreshLayout.finishRefresh(true);
                break;
            case 6:
                MallBean.DataBean mallData = (MallBean.DataBean) simpleEvent.getData();
                MallBean.DataBean.QualityGoodsBean pinzhiData = mallData.getQualityGoods();
                MallBean.DataBean.HotSellBean hotDataBean = mallData.getHotSell();
                MallBean.DataBean.NewGoodsSaleBean newDataBean = mallData.getNewGoodsSale();
                //更新品质好货
                jingxuanList.clear();
                jingxuanList.addAll(mallData.getDailySelectionList());
                mJingXuanAdapter.notifyDataSetChanged();
                mTvPinzhiPrice.setText("¥ " + ArithmeticUtils.format(pinzhiData.getMinPrice()));
                mTvPinzhiTitle.setText(pinzhiData.getGoodsName());
                pinzhiGoodID = pinzhiData.getGoodsId();
                ImageDisplayUtil.showImageView(mContext, pinzhiData.getImagePath(), mIvPinzhiCover);
                //更新热卖商品
                ImageDisplayUtil.showImageView(mContext, hotDataBean.getImagePath(), mIvHotCover);
                //更新新品热卖
                ImageDisplayUtil.showImageView(mContext, newDataBean.getImagePath(), mIvNewCover);

                break;
            //精选商品上拉加载
            case 7:
                ElementBean guessDataMore = (ElementBean) simpleEvent.getData();
                mGuessList.addAll(guessDataMore.getElements());
                mAdapterMall.notifyDataSetChanged();
                mRefreshLayout.finishLoadmore(true);

                break;

        }
    }

    /**
     * 判断连接路径
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.back_left:
                readyGo(MallCategoryActivity.class);
                break;
            case R.id.rlayout_search:
                bundle.putParcelable("keyword", keyword);
                readyGo(SearchActivity.class, bundle);
                break;
            case R.id.tv_pinzhi_more:
                bundle.putString("type", "QualityGoods");
                bundle.putInt("flag", 1);
                readyGo(GoodListActivity.class, bundle);
                break;
            case R.id.tv_jingxuan_more:
                bundle.putString("type", "DailySelection");
                bundle.putInt("flag", 4);
                readyGo(GoodListActivity.class, bundle);
                break;
            case R.id.llayout_hot_good:
                bundle.putString("type", "HotSell");
                bundle.putInt("flag", 2);
                readyGo(GoodListActivity.class, bundle);
                break;
            case R.id.llayout_new_good:
                bundle.putString("type", "NewGoodsSale");
                bundle.putInt("flag", 3);
                readyGo(GoodListActivity.class, bundle);
                break;
            case R.id.llayout_pingzhi:
                bundle.putInt("goodId", pinzhiGoodID);
                readyGo(GoodDetailActivity.class, bundle);
                break;

        }

    }


}
