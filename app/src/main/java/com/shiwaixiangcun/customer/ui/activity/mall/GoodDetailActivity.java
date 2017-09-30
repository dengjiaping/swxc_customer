package com.shiwaixiangcun.customer.ui.activity.mall;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.event.EventCenter;
import com.shiwaixiangcun.customer.event.SimpleEvent;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.model.GoodDetail;
import com.shiwaixiangcun.customer.ui.activity.LoginActivity;
import com.shiwaixiangcun.customer.ui.dialog.DialogSku;
import com.shiwaixiangcun.customer.ui.dialog.DialogSupport;
import com.shiwaixiangcun.customer.utils.ArithmeticUtils;
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

/**
 * 商品详情Activity
 */
public class GoodDetailActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.banner_details)
    Banner mBannerDetails;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_price_fare)
    TextView mTvPriceFare;
    @BindView(R.id.tv_amount)
    TextView mTvAmount;
    @BindView(R.id.llayout_service)
    LinearLayout mLlayoutService;
    @BindView(R.id.textView4)
    TextView mTextView4;
    @BindView(R.id.tv_category)
    TextView mTvCategory;
    @BindView(R.id.iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.rl_choice)
    RelativeLayout mRlChoice;
    @BindView(R.id.taglayout)
    TagFlowLayout mTaglayout;
    @BindView(R.id.btn_purchase)
    Button mBtnPurchase;
    @BindView(R.id.llayout_content)
    LinearLayout mllayoutContent;
    @BindView(R.id.iv_share_right)
    ImageView mIvShareRight;
    @BindView(R.id.webview)
    WebView mWebView;
    List<String> services = new ArrayList<>();
    List<String> images = new ArrayList<>();
    //商品图片展示
    List<GoodDetail.DataBean.ImagesBean> mListImage = new ArrayList<>();
    //商品库存
    List<GoodDetail.DataBean.GoodsPriceStoresBean> mListGoodsStores = new ArrayList<>();
    //商品所提供的服务
    List<GoodDetail.DataBean.ServicesBean> mListServices = new ArrayList<>();
    //商品的SKU
    List<GoodDetail.DataBean.SpecificationsBean> mListSpecifications = new ArrayList<>();
    @BindView(R.id.rlayout_purchase)
    RelativeLayout mRlayoutPurchase;
    @BindView(R.id.tv_hint)
    TextView mTvHint;

    private DialogSku dialogSku;
    private int mGoodId;
    private GoodDetail.DataBean goodBean;
    private String isOrNotLogin;

    private StringBuilder strSpecification = new StringBuilder();

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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(SimpleEvent simpleEvent) {

        if (simpleEvent.mEventType != SimpleEvent.UPDATE_GOOD_DETAIL) {
            return;
        }
        switch (simpleEvent.mEventValue) {
            case 1:
                //更新界面
                goodBean = (GoodDetail.DataBean) simpleEvent.getData();
                if (goodBean.isPublished()) {
                    // TODO: 2017/9/23 标记缺货状态
                    mRlayoutPurchase.setVisibility(View.VISIBLE);
                    mTvHint.setVisibility(View.GONE);
                } else {
                    mRlayoutPurchase.setVisibility(View.GONE);
                    mTvHint.setVisibility(View.VISIBLE);
                }
                mTvTitle.setText(goodBean.getGoodsName());
                mTvDesc.setText(goodBean.getFeature());
                mTvPrice.setText("¥ " + ArithmeticUtils.format(goodBean.getMinPrice()));
                mTvPriceFare.setText("¥ " + ArithmeticUtils.format(goodBean.getTransportMoney()));
                mTvAmount.setText(goodBean.getSalesVolume() + "");
                //获取List
                mListImage = goodBean.getImages();
                mListServices = goodBean.getServices();
                mListGoodsStores = goodBean.getGoodsPriceStores();
                mListSpecifications = goodBean.getSpecifications();
                images.clear();
                services.clear();
                for (int i = 0, size = mListImage.size(); i < size; i++) {
                    String url = mListImage.get(i).getAccessUrl();
                    images.add(url);
                }
                mBannerDetails.setImages(images).setImageLoader(new GlideImageLoader()).setDelayTime(3000).start();

                for (int i = 0, size = mListServices.size(); i < size; i++) {
                    String service = mListServices.get(i).getName();
                    services.add(service);
                }


                //设置 用户需要选择的规格
                if (mListSpecifications.size() == 0) {
                    mRlChoice.setVisibility(View.GONE);
                } else {
                    for (GoodDetail.DataBean.SpecificationsBean specificationsBean : mListSpecifications) {
                        strSpecification.append(specificationsBean.getName()).append("  ");
                    }
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
                        TextView textView = (TextView) view.findViewById(R.id.tv_support);
                        textView.setText(services.get(position));
                        return view;
                    }
                });
                break;

            case 2:
                Log.e(BUG_TAG, "加载商品详情");
                String htmlString = (String) simpleEvent.getData();
                mWebView.loadUrl(htmlString);
                mWebView.loadDataWithBaseURL(null, htmlString, "text/html", "utf-8", null);
                mWebView.getSettings().setJavaScriptEnabled(true);
                mWebView.setWebChromeClient(new WebChromeClient());
                break;
        }
    }

    private void iniData() {
        Bundle bundle = getIntent().getExtras();
        mGoodId = bundle.getInt("goodId");
        Log.e(BUG_TAG, mGoodId + "");

        /**

         */
        requestContent();
        requestDetail();
    }

    /**
     * 获取商品详情
     */
    private void requestDetail() {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", mGoodId);
        OkGo.<String>get(GlobalConfig.getGoodDetail)
                .params("id", mGoodId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(BUG_TAG, response.getRawCall().request().toString());
                        GoodDetail goodDetail = JsonUtil.fromJson(response.body(), GoodDetail.class);
                        if (null == goodDetail) {
                            return;
                        }
                        if (1001 == goodDetail.getResponseCode()) {
                            GoodDetail.DataBean data = goodDetail.getData();
                            EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_GOOD_DETAIL, 1, data));
                        }
                    }
                });


    }

    /**
     * 获取图文详情页面
     */
    private void requestContent() {
        OkGo.<String>get(GlobalConfig.getOrderContent)
                .params("id", mGoodId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(BUG_TAG, "onSuccess");

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
        mRlChoice.setOnClickListener(this);
        mBtnPurchase.setOnClickListener(this);
    }

    /**
     * 初始化视图
     */

    private void initView() {
        mTvHint.setVisibility(View.GONE);
        mIvShareRight.setVisibility(View.VISIBLE);
        mBannerDetails.setBannerStyle(BannerConfig.NUM_INDICATOR);
        dialogSku = new DialogSku(this, R.style.AlertDialogStyle, mGoodId);
        mTvPageName.setText("商品详情");

        initWebView();

    }

    /**
     * 配置WebView
     */
    private void initWebView() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
    }

    @Override
    public void onClick(View view) {
        isOrNotLogin = SharePreference.getStringSpParams(GoodDetailActivity.this, Common.ISORNOLOGIN, Common.SIORNOLOGIN);
        switch (view.getId()) {
            case R.id.iv_share_right:
                // TODO: 2017/9/23  分享
                break;
            case R.id.back_left:
                finish();
                break;
            case R.id.rl_choice:
                if (Utils.isNotEmpty(isOrNotLogin)) {
                    if (mListSpecifications.size() == 0) {
                        Log.e(BUG_TAG, mListSpecifications.size() + "");
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
                        Log.e(BUG_TAG, mListSpecifications.size() + "");
                        goOrder();
                    } else {
                        dialogSku.show();
                    }
                } else {
                    readyGo(LoginActivity.class);
                }
                break;
        }
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
        if (mWebView != null) {
            mllayoutContent.removeView(mWebView);
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
