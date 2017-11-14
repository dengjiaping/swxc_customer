package com.shiwaixiangcun.customer.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Administrator
 *         Doctor详情
 */
public class DoctorDetailActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.webview)
    WebView mWebView;
    @BindView(R.id.myProgressBar)
    ProgressBar mMyProgressBar;
    StringBuilder urlBuilder = new StringBuilder();
    private int id;
    private String strToken;

    /**
     * 清除Cookie
     *
     * @param context
     */
    private static void removeCookie(Context context) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("id");
        }
        strToken = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.TOKEN, "");

        initWebView();
        initViewAndEvent();


    }

    private void initViewAndEvent() {
        mBackLeft.setOnClickListener(this);
        mTvPageName.setText("名医主页");
        urlBuilder.append(GlobalAPI.doctorDetail)
                .append("?id=")
                .append(id);
        Log.e(BUG_TAG, "医生详情" + urlBuilder.toString());
        removeCookie(mContext);
        mWebView.setWebChromeClient(new MyWebChromeViewClient());
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.loadUrl(urlBuilder.toString());


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
        webSettings.setUseWideViewPort(true);
        //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true);
        // 缩放至屏幕的大小
        //缩放操作
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportZoom(true);
        //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true);
        //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setBlockNetworkImage(false);
        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //关闭webview中缓存
        webSettings.setAllowFileAccess(true);
        //设置可以访问文件
        String user_agent = "Mozilla/5.0 (Linux; Android 5.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Mobile Safari/537.36";
        webSettings.setUserAgentString(user_agent);

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true);
        //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");
        //设置编码格式
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        } else {
            finish();
            return false;
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_left:
                finish();
                break;
            default:
                break;
        }

    }

    /**
     * 处理Javascript的对话框，网站图标，网站title，加载进度等
     */
    private final class MyWebChromeViewClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mMyProgressBar.setVisibility(View.VISIBLE);
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

        @Override
        public void onLoadResource(WebView view, String url) {

            Log.i("tag", "onLoadResource url=" + url);
            super.onLoadResource(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webview,
                                                String url) {

            Log.i("tag", "intercept url=" + url);
            // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
            webview.loadUrl(url);

            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            String title = view.getTitle(); // 得到网页标题

            Log.e("tag", "onPageFinished WebView title=" + title);

        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {

            Toast.makeText(getApplicationContext(), "加载错误",
                    Toast.LENGTH_LONG).show();
        }
    }


}
