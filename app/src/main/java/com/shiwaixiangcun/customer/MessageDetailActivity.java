package com.shiwaixiangcun.customer;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageDetailActivity extends BaseActivity {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.webView)
    WebView mWebView;

    String partner;
    String problemId;
    private StringBuilder strUrl = new StringBuilder();

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
        setContentView(R.layout.activity_message_detail);
        ButterKnife.bind(this);
        initData();

        initWebView();

        initViewAndEvent();
    }

    /**
     * 初始化WebView
     */
    private void initWebView() {

        mWebView.setVerticalScrollBarEnabled(false);
        //声明WebSettings子类
        WebSettings webSettings = mWebView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
//        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
    }

    private void initData() {
        Bundle extras = getIntent().getExtras();
        partner = extras.getString("partner");
        problemId = extras.getString("problemId");
    }

    private void initViewAndEvent() {
        mBackLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        strUrl.append(GlobalAPI.getDoctor)
                .append("/cooperation/wap/problem_detail_page/?partner=")
                .append(partner)
                .append("&problem_id=")
                .append(problemId);
//        removeCookie(mContext);
        mWebView.loadUrl(strUrl.toString());
        mWebView.setWebViewClient(new MyWebViewClient());

    }

    //Web视图
    private final class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
