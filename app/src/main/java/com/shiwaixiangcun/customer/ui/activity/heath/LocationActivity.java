package com.shiwaixiangcun.customer.ui.activity.heath;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.store.CookieStore;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * @author Administrator
 *         <p>
 *         家人定位Activity
 */
public class LocationActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;


    StringBuilder mStringBuilder = new StringBuilder();
    @BindView(R.id.myProgressBar)
    ProgressBar mMyProgressBar;
    @BindView(R.id.webView)
    WebView mWebView;
    private String tokenString;

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
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);
        initWebView(mWebView);
        initViewAndEvent();

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

    }

    private void getCookie(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                //http连接需要放到子线程中进行请求
                CookieStore cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
                HttpUrl httpUrl = HttpUrl.parse(url);
                List<Cookie> cookies = cookieStore.getCookie(httpUrl);
                synCookies(url, cookies.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.loadUrl(url);//webview控件调用需要在主线程中进行
                    }
                });


            }
        }).start();
    }

    /**
     * 同步一下cookie
     */
    private void synCookies(String url, String cookie) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(this);
        }
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie(url, cookie);//如果没有特殊需求，这里只需要将session id以"key=value"形式作为cookie即可
    }

    private void initViewAndEvent() {
        mTvPageName.setText(R.string.family_location);
        mBackLeft.setOnClickListener(this);
        tokenString = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.TOKEN, "");

        //加载路径
        mStringBuilder.append(GlobalAPI.getLocation)
                .append("?access_token=")
                .append(tokenString);

        String url = mStringBuilder.toString();
        Log.e(BUG_TAG, "webview加载" + mStringBuilder.toString());


        //webview Cookie
        CookieStore cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
        HttpUrl httpUrl = HttpUrl.parse(url);
        List<Cookie> cookies = cookieStore.getCookie(httpUrl);
        List<Cookie> allCookie = cookieStore.getAllCookie();
        Log.e(BUG_TAG, "所有cookie如下：" + allCookie.toString());
        Log.e(BUG_TAG, httpUrl.host() + " 对应的cookie如下：" + cookies.toString());

//        syncCookieToWebView(url, "uid=95899aa3-b22f-49cc-a207-ddd5cf4c1ac0; expires=Fri, 31 Dec 9999 23:59:59 GMT; path=/");

        removeCookie(this);
        mWebView.setWebChromeClient(new MyWebChromeViewClient());
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.loadUrl(url);

    }


    /**
     * cookie 同步
     *
     * @param url
     * @return
     */
    private void syncCookieToWebView(String url, String cookies) {
        CookieSyncManager.createInstance(this);
        CookieManager cm = CookieManager.getInstance();
        cm.removeAllCookie();
        cm.setAcceptCookie(true);

        cm.setCookie(url, cookies);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager.getInstance().sync();
        }
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

    //Web视图
    private final class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
