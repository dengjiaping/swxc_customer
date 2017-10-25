package com.shiwaixiangcun.customer.ui.activity.heath;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.tv_top_right)
    TextView mTvTopRight;
    @BindView(R.id.iv_share_right)
    ImageView mIvShareRight;
    @BindView(R.id.iv_sao_right)
    ImageView mIvSaoRight;
    @BindView(R.id.ll_image_right)
    LinearLayout mLlImageRight;
    @BindView(R.id.top_bar_write)
    RelativeLayout mTopBarWrite;
    @BindView(R.id.webview)
    WebView mWebView;
    String isLogin;
    @BindView(R.id.myProgressBar)
    ProgressBar mMyProgressBar;
    private boolean isApperenceToken = false;
    private int type;
    private StringBuilder urlBuilder = new StringBuilder();
    private String tokenString;
    private int userId;
    private String strLink;
    private boolean authorization;
    private String strName;

    /**
     * 清除Cookie
     *
     * @param context
     */
    public static void removeCookie(Context context) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        initData();
        initWebView();
        initView();
    }

    /**
     * 初始化WebView
     */
    private void initWebView() {
        //声明WebSettings子类
        WebSettings webSettings = mWebView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setBlockNetworkImage(false);
        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        String user_agent = "Mozilla/5.0 (Linux; Android 5.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Mobile Safari/537.36";
        webSettings.setUserAgentString(user_agent);

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
    }

    private void initView() {
        mBackLeft.setOnClickListener(this);
        urlBuilder.append(strLink);
        if (authorization) {
            urlBuilder.append("?access_token=").append(tokenString);
        }
        Log.e(BUG_TAG, "webview加载" + urlBuilder.toString());

        removeCookie(mContext);

//        mWebView.loadUrl("https://open.zhongan.com/standard/insure/productShowPageMobile.htm?productCode=PRD160600129006&promotionCode=INST170970022019&payChannel=wxpay,alipay,alipay_app&accessChannel=mobile");
        mWebView.loadUrl(urlBuilder.toString());
        mWebView.setWebChromeClient(new MyWebChromeViewClient());
        mWebView.setWebViewClient(new MyWebViewClient());

    }


    private void initData() {
        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt("type", -1);
        strLink = bundle.getString("link");
        strName = bundle.getString("title");
        authorization = bundle.getBoolean("authorization");
        mTvPageName.setText(strName);
        isLogin = SharePreference.getStringSpParams(mContext, Common.ISORNOLOGIN, Common.SIORNOLOGIN);
        tokenString = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.TOKEN, "");
        Log.e(BUG_TAG, "页面获取的Token：" + tokenString);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_left:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    //Web视图
    private final class MyWebChromeViewClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mMyProgressBar.setVisibility(View.GONE);
            } else {
                if (View.INVISIBLE == mMyProgressBar.getVisibility()) {
                    mMyProgressBar.setVisibility(View.VISIBLE);
                }
                mMyProgressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }


    }

    private class MyWebViewClient extends WebViewClient {

        public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
            view.loadUrl(url);
            return true;
        }

    }
}
