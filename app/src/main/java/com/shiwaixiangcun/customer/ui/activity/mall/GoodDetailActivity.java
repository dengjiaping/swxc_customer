package com.shiwaixiangcun.customer.ui.activity.mall;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.recyclerviewdivider.RecyclerViewDivider;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.adapter.AdapterEvaluate;
import com.shiwaixiangcun.customer.event.EventCenter;
import com.shiwaixiangcun.customer.event.SimpleEvent;
import com.shiwaixiangcun.customer.model.GoodDetail;
import com.shiwaixiangcun.customer.share.OnekeyShare;
import com.shiwaixiangcun.customer.ui.activity.EvaluatesListActivity;
import com.shiwaixiangcun.customer.ui.activity.LoginActivity;
import com.shiwaixiangcun.customer.ui.dialog.DialogSku;
import com.shiwaixiangcun.customer.ui.dialog.DialogSupport;
import com.shiwaixiangcun.customer.utils.ArithmeticUtils;
import com.shiwaixiangcun.customer.utils.DateUtil;
import com.shiwaixiangcun.customer.utils.GlideImageLoader;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * 商品详情Activity
 *
 * @author Administrator
 */
public class GoodDetailActivity extends BaseActivity implements View.OnClickListener {


    private final static int CODE_SUCCESS = 1001;
    private final static String ALL_ADVANCE = "AllAdvance";
    private static final String PRESALE_END = "预售已结束";
    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;

    @BindView(R.id.rlayout_purchase)
    RelativeLayout mRlayoutPurchase;
    @BindView(R.id.tv_hint)
    TextView mTvHint;

    @BindView(R.id.rv_evaluate)
    RecyclerView mRvEvaluate;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.iv_share_right)
    ImageView mIvShareRight;
    @BindView(R.id.btn_purchase)
    Button mBtnPurchase;
    List<String> services = new ArrayList<>();
    List<String> images = new ArrayList<>();
    /**
     * 商品图片展示
     */
    List<GoodDetail.DataBean.ImagesBeanX> mListImage = new ArrayList<>();
    /**
     * 商品库存
     */
    List<GoodDetail.DataBean.GoodsPriceStoresBean> mListGoodsStores = new ArrayList<>();
    /**
     * 商品所提供的服务
     */
    List<GoodDetail.DataBean.ServicesBean> mListServices = new ArrayList<>();
    /**
     * 商品的SKU
     */
    List<GoodDetail.DataBean.SpecificationsBean> mListSpecifications = new ArrayList<>();
    /**
     * 用户评价
     */
    List<GoodDetail.DataBean.EvaluatesBean> mEvaluatesBeanList = new ArrayList<>();
    private DialogSku dialogSku;
    private int mGoodId;
    private int highTotal;
    private int allTotal;
    private int midTotal;
    private int badTotal;
    private GoodDetail.DataBean goodBean;
    private String isOrNotLogin;
    private StringBuilder shareUrl = new StringBuilder();
    private StringBuilder strSpecification = new StringBuilder();
    /**
     * 2个Header和一个Footer
     */
    private View viewBanner = null;
    private View viewGoodInfo = null;
    private View viewDetail = null;
    private LayoutInflater mInflater;
    private Banner mBanner;
    /**
     * GoodInfo中的控件
     */

    private ConstraintLayout cLayoutAdvance;
    private TextView mTvLatestDeliveryTime;
    private TagFlowLayout mTaglayout;
    private LinearLayout mLlayoutService;
    private TextView mTvTitle;
    private TextView mTvDesc;
    private TextView mTvPrice;
    private TextView mTvPriceFare;
    private TextView mTvAmount;
    private TextView mTvEvaluateAmount;
    private RelativeLayout mRlayoutChoice;
    private TextView mTvCategory;
    private TextView mTvEvaluateAll;
    private TextView mAdSellTime;
    private TextView mTvCurrentPrice;
    private TextView mTvPrimePrice;
    /**
     * 图文详情中的 控件
     */
    private LinearLayout mLlayoutWebView;
    private WebView mWebView;
    private AdapterEvaluate mAdapterEvaluate;

    private long adSellTime;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                mAdSellTime.setText(dateInterval(adSellTime));
                sendEmptyMessageDelayed(1, 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_detail);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        iniData();
        initView();
        iniEvent();
    }


    @SuppressLint({"SetTextI18n", "SetJavaScriptEnabled"})
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(SimpleEvent simpleEvent) {

        if (simpleEvent.mEventType != SimpleEvent.UPDATE_GOOD_DETAIL) {
            return;
        }
        switch (simpleEvent.mEventValue) {
            case 1:
                goodBean = (GoodDetail.DataBean) simpleEvent.getData();
                highTotal = goodBean.getHighTotal();
                midTotal = goodBean.getMidTotal();
                badTotal = goodBean.getBadTotal();
                allTotal = goodBean.getEvaluateTotal();

                //更新界面

                if (goodBean.getEvaluateTotal() == 0) {
                    mTvEvaluateAll.setVisibility(View.GONE);
                    mTvEvaluateAmount.setVisibility(View.GONE);
                } else {
                    mTvEvaluateAmount.setText("用户评价" + "(" + goodBean.getEvaluateTotal() + ")");
                }
                //设置评论
                mEvaluatesBeanList.clear();
                mEvaluatesBeanList.addAll(goodBean.getEvaluates());
                mAdapterEvaluate.notifyDataSetChanged();


                //设置商品信息
                mTvTitle.setText(goodBean.getGoodsName());
                mTvDesc.setText(goodBean.getFeature());
                mTvPrice.setText("¥ " + ArithmeticUtils.format(goodBean.getMinPrice()));
                mTvPriceFare.setText("¥ " + ArithmeticUtils.format(goodBean.getTransportMoney()));
                mTvAmount.setText(goodBean.getSalesVolume() + "");

                if (goodBean.isPublished()) {
                    mRlayoutChoice.setVisibility(View.VISIBLE);
                    mRlayoutPurchase.setVisibility(View.VISIBLE);

                    mTvHint.setVisibility(View.GONE);
                } else {
                    mRlayoutPurchase.setVisibility(View.GONE);
                    mTvHint.setVisibility(View.VISIBLE);
                    mRlayoutChoice.setVisibility(View.GONE);
                }


                //设置Banner
                mListImage = goodBean.getImages();
                images.clear();
                services.clear();
                for (int i = 0, size = mListImage.size(); i < size; i++) {
                    String url = mListImage.get(i).getAccessUrl();
                    images.add(url);
                }
                mBanner.setImages(images).setImageLoader(new GlideImageLoader()).setDelayTime(3000).start();


                //设置服务信息

                mListServices = goodBean.getServices();

                for (int i = 0, size = mListServices.size(); i < size; i++) {
                    String service = mListServices.get(i).getName();
                    services.add(service);
                }


                //设置 用户需要选择的规格

                mListSpecifications = goodBean.getSpecifications();
                if (mListSpecifications.size() == 0) {
                    mRlayoutChoice.setVisibility(View.GONE);
                } else {
                    for (GoodDetail.DataBean.SpecificationsBean specificationsBean : mListSpecifications) {
                        strSpecification.append(specificationsBean.getName()).append("  ");
                    }
                    mRlayoutChoice.setVisibility(View.VISIBLE);
                    mTvCategory.setText(strSpecification.toString().trim());
                }
                if (mListServices.size() == 0) {
                    mLlayoutService.setVisibility(View.GONE);
                }
                //添加支持的服务到布局
                mTaglayout.setAdapter(new TagAdapter<String>(services) {
                    @Override
                    public View getView(FlowLayout parent, int position, String s) {
                        View view = LayoutInflater.from(mContext).inflate(R.layout.tag, mTaglayout, false);
                        TextView textView = view.findViewById(R.id.tv_support);
                        textView.setText(services.get(position));
                        return view;
                    }
                });

                //商品预售
                if (ALL_ADVANCE.equals(goodBean.getAdvanceStatus())) {

                    mTvPrice.setVisibility(View.GONE);
                    cLayoutAdvance.setVisibility(View.VISIBLE);
                    mTvCurrentPrice.setText("¥ " + ArithmeticUtils.format(goodBean.getMinPrice()));
                    mTvPrimePrice.setVisibility(View.GONE);
                    mTvPrimePrice.setText("¥ " + ArithmeticUtils.format(goodBean.getMinPrice()));
                    mTvPrimePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    mTvLatestDeliveryTime.setText("最晚 " + DateUtil.getCustomFormat(goodBean.getAdSellTime(), "MM月dd日 HH:mm") + " 前发货");
                    mAdSellTime.setText(dateInterval(goodBean.getAdSellTime()));
                    adSellTime = goodBean.getAdSellTime();
                    if (PRESALE_END.equals(mAdSellTime.getText())) {
                        mBtnPurchase.setVisibility(View.GONE);
                        mTvHint.setVisibility(View.VISIBLE);
                    }
                    handler.sendEmptyMessage(1);

                } else {
                    cLayoutAdvance.setVisibility(View.GONE);
                }


                break;
            case 2:
                String htmlString = (String) simpleEvent.getData();
                mWebView.loadUrl(htmlString);
                mWebView.loadDataWithBaseURL(null, htmlString, "text/html", "utf-8", null);
                mWebView.setWebChromeClient(new WebChromeClient());
                break;
            default:
                break;
        }
    }

    /**
     * 转换距离预售结束时间
     *
     * @param adSellTime 预售时间
     * @return
     */
    private String dateInterval(long adSellTime) {
        long now = System.currentTimeMillis();
        if (adSellTime <= now) {
            return PRESALE_END;
        } else {
            long interval = adSellTime - now;
            int ss = 1000;
            int mi = ss * 60;
            int hh = mi * 60;
            int dd = hh * 24;

            long day = interval / dd;
            long hour = (interval - day * dd) / hh;
            long minute = (interval - day * dd - hour * hh) / mi;
            long second = (interval - day * dd - hour * hh - minute * mi) / ss;
            long milliSecond = interval - day * dd - hour * hh - minute * mi - second * ss;
            //天
            String strDay = day < 10 ? "0" + day : "" + day;
            //小时
            String strHour = hour < 10 ? "0" + hour : "" + hour;
            //分钟
            String strMinute = minute < 10 ? "0" + minute : "" + minute;
            //秒
            String strSecond = second < 10 ? "0" + second : "" + second;
            //毫秒
            String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;
            strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;

            return "距离预售结束还剩  " + strDay + "天 " + strHour + ":" + strMinute + ":" + strSecond;

        }
    }

    /**
     * 初始化数据
     */
    private void iniData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        mGoodId = bundle.getInt("goodId");
        requestDetail();
        requestContent();

    }

    /**
     * 获取商品详情
     */
    private void requestDetail() {

        HashMap<String, Object> hashMap = new HashMap<>(1);
        hashMap.put("id", mGoodId);
        OkGo.<String>get(GlobalAPI.getGoodDetail)
                .params("id", mGoodId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        GoodDetail goodDetail = JsonUtil.fromJson(response.body(), GoodDetail.class);
                        if (null == goodDetail) {
                            return;
                        }
                        if (CODE_SUCCESS == goodDetail.getResponseCode()) {
                            GoodDetail.DataBean data = goodDetail.getData();
                            EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_GOOD_DETAIL, 1, data));
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });


    }

    /**
     * 获取图文详情页面
     */
    private void requestContent() {
        OkGo.<String>get(GlobalAPI.getOrderContent)
                .params("id", mGoodId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String html = response.body();
                        EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_GOOD_DETAIL, 2, html));
                    }
                });

    }

    /**
     * 初始化点击事件
     */
    private void iniEvent() {
        mTaglayout.setClickable(false);
        mBackLeft.setOnClickListener(this);
        mLlayoutService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogSupport dialogSupport = new DialogSupport(mContext);
                dialogSupport.show();
                dialogSupport.setData(mListServices);

            }
        });
        mIvShareRight.setOnClickListener(this);

    }

    /**
     * 初始化视图
     */

    private void initView() {
        mTvHint.setVisibility(View.GONE);
        mIvShareRight.setVisibility(View.VISIBLE);

        dialogSku = new DialogSku(this, R.style.AlertDialogStyle, mGoodId);
        mTvPageName.setText("商品详情");

        mInflater = LayoutInflater.from(mContext);
        viewBanner = mInflater.inflate(R.layout.layout_good_detail_banner, null, false);
        viewGoodInfo = mInflater.inflate(R.layout.layout_good_detail_info, null, false);
        viewDetail = mInflater.inflate(R.layout.layout_good_detail, null, false);

        initHeaderView();
        initFootView();

        mAdapterEvaluate = new AdapterEvaluate(mEvaluatesBeanList);
        mRvEvaluate.setLayoutManager(new LinearLayoutManager(this));
        mRvEvaluate.setAdapter(mAdapterEvaluate);
        RecyclerViewDivider divider = new RecyclerViewDivider.Builder(mContext)
                .setOrientation(RecyclerViewDivider.VERTICAL)
                .setStyle(RecyclerViewDivider.Style.BETWEEN)
                .setMarginLeft(20)
                .setMarginRight(0)
                .setDrawableRes(R.drawable.divider)
                .build();
        mRvEvaluate.addItemDecoration(divider);
        mAdapterEvaluate.addHeaderView(viewBanner);
        mAdapterEvaluate.addHeaderView(viewGoodInfo);
        mAdapterEvaluate.addFooterView(viewDetail);

    }

    /**
     *
     */
    private void initFootView() {
        mWebView = viewDetail.findViewById(R.id.webview);
        mLlayoutWebView = viewDetail.findViewById(R.id.llayout_webView);

        mTvEvaluateAll = viewDetail.findViewById(R.id.tv_evaluate_all);
        mTvEvaluateAll.setOnClickListener(this);
        initWebView();


    }

    /**
     * 初始化头部视图
     */
    private void initHeaderView() {

        mBanner = viewBanner.findViewById(R.id.banner_details);
        mTaglayout = viewGoodInfo.findViewById(R.id.taglayout);
        mLlayoutService = viewGoodInfo.findViewById(R.id.llayout_service);
        mTvTitle = viewGoodInfo.findViewById(R.id.tv_title);
        mTvDesc = viewGoodInfo.findViewById(R.id.tv_desc);
        mTvPrice = viewGoodInfo.findViewById(R.id.tv_price);
        mTvPriceFare = viewGoodInfo.findViewById(R.id.tv_price_fare);
        mTvAmount = viewGoodInfo.findViewById(R.id.tv_amount);
        mTvEvaluateAmount = viewGoodInfo.findViewById(R.id.tv_evaluate_amount);
        mRlayoutChoice = viewGoodInfo.findViewById(R.id.rl_choice);
        mTvCategory = viewGoodInfo.findViewById(R.id.tv_category);
        cLayoutAdvance = viewGoodInfo.findViewById(R.id.cLayout_advance);

        mAdSellTime = viewGoodInfo.findViewById(R.id.tv_end_time);
        mTvLatestDeliveryTime = viewGoodInfo.findViewById(R.id.tv_last_delivery);
        mTvCurrentPrice = viewGoodInfo.findViewById(R.id.tv_price_current);
        mTvPrimePrice = viewGoodInfo.findViewById(R.id.tv_price_prime);

        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR);

        mRlayoutChoice.setOnClickListener(this);
        mBtnPurchase.setOnClickListener(this);


    }

    /**
     * 配置WebView
     */
    private void initWebView() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true);
        //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true);
        // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true);
        //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true);
        //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false);
        //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //关闭webview中缓存
        webSettings.setAllowFileAccess(true);
        //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true);
        //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");
        //设置编码格式
    }

    @Override
    public void onClick(View view) {
        isOrNotLogin = SharePreference.getStringSpParams(GoodDetailActivity.this, Common.ISORNOLOGIN, Common.SIORNOLOGIN);
        switch (view.getId()) {
            case R.id.iv_share_right:
                showShare();
                break;
            case R.id.back_left:
                finish();
                break;
            case R.id.rl_choice:
                if (Utils.isNotEmpty(isOrNotLogin)) {
                    if (mListSpecifications.size() == 0) {
                        goOrder();
                    } else {
                        dialogSku.show();
                    }
                } else {
                    readyGo(LoginActivity.class);
                }

                break;
            case R.id.btn_purchase:
                if (Utils.isNotEmpty(isOrNotLogin)) {
                    if (mListSpecifications.size() == 0) {
                        goOrder();
                    } else {
                        dialogSku.show();
                    }
                } else {
                    readyGo(LoginActivity.class);
                }
                break;
            case R.id.tv_evaluate_all:
                Bundle bundle = new Bundle();
                bundle.putInt("goodID", mGoodId);
                bundle.putInt("allTotal", allTotal);
                bundle.putInt("highTotal", highTotal);
                bundle.putInt("midTotal", midTotal);
                bundle.putInt("badTotal", badTotal);

                readyGo(EvaluatesListActivity.class, bundle);
                break;
            default:
                break;
        }
    }


    /**
     * 分享
     */
    private void showShare() {
        shareUrl.append(GlobalAPI.shareGoods).append(mGoodId).append(".htm");
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(goodBean.getShopName());
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(shareUrl.toString());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(goodBean.getGoodsName());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(goodBean.getImages().get(0).getThumbImageURL());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(shareUrl.toString());

        oks.setSite(getResources().getResourceName(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(shareUrl.toString());
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Toast.makeText(mContext, "分享成功", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Toast.makeText(mContext, "分享失败", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Toast.makeText(mContext, "取消分享", Toast.LENGTH_LONG).show();
            }
        });
        // 启动分享GUI
        oks.show(this);
    }


    /**
     * 跳转至Order页面
     */
    private void goOrder() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        //商品ID
        bundle.putInt("goodId", mGoodId);
        //用户选择的属性名字
        bundle.putString("value", null);
        //用户确认的id
        bundle.putString("id", null);
        //商品信息
        bundle.putParcelable("goodInfo", goodBean);
        intent.putExtras(bundle);
        intent.setClass(mContext, ConfirmOrderActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null && viewDetail != null) {
            mLlayoutWebView.removeView(mWebView);
            mWebView.destroy();
        }
        super.onDestroy();


    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
