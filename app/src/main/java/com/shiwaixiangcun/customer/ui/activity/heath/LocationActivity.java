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

import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

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

        String userAgentString = mWebView.getSettings().getUserAgentString();
        //设置user_agent(以asyncHttprequest为例)
//        client.setUserAgent(defaultUserAgent);

        Map<String, String> header = new HashMap<>();
        header.put("user-agent", userAgentString);
        removeCookie(this);
        mWebView.setWebChromeClient(new MyWebChromeViewClient());
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.loadUrl(url, header);


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
