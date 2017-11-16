package com.shiwaixiangcun.customer.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.share.OnekeyShare;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * 报名活动详情
 *
 * @author Administrator
 */
public class RegisterDetailActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.webview)
    WebView mWebView;
    @BindView(R.id.myProgressBar)
    ProgressBar mMyProgressBar;
    @BindView(R.id.iv_share_right)
    ImageView mIvShareRight;
    private StringBuilder urlBuilder = new StringBuilder();
    private StringBuilder urlShare = new StringBuilder();
    private int activityID;
    private int siteID;
    private String title;
    private String cover;

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
        setContentView(R.layout.activity_register_detail);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            activityID = extras.getInt("activityID");
            title = extras.getString("activityTitle");
            cover = extras.getString("cover");
        }
        siteID = (int) AppSharePreferenceMgr.get(mContext, GlobalConfig.CURRENT_SITE_ID, 0);
        initWebView();
        initViewAndEvent();

    }

    private void initViewAndEvent() {
        mBackLeft.setOnClickListener(this);
        mTvPageName.setText("活动详情");
        mIvShareRight.setVisibility(View.VISIBLE);
        mTvPageName.setMaxLines(1);
        mTvPageName.setEllipsize(TextUtils.TruncateAt.END);
        urlBuilder.append(GlobalAPI.activityDetail)
                .append("?activityId=")
                .append(activityID)
                .append("&siteId=")
                .append(siteID);
        urlShare.append(GlobalAPI.activityDetail)
                .append("?activityId=")
                .append(activityID)
                .append("&siteId=")
                .append(siteID);
        removeCookie(mContext);
        mIvShareRight.setOnClickListener(this);
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
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    finish();
                }
                break;
            case R.id.iv_share_right:
                showShare();

            default:
                break;
        }

    }


    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(urlShare.toString());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(title);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(cover);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(urlShare.toString());

        oks.setSite(getResources().getResourceName(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(urlShare.toString());
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
     * 处理Javascript的对话框，网站图标，网站title，加载进度等
     */
    private final class MyWebChromeViewClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            Log.d("ANDROID_LAB", "TITLE=" + title);
            mTvPageName.setText(title);
        }

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


        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {

            Toast.makeText(getApplicationContext(), "加载错误",
                    Toast.LENGTH_LONG).show();
        }

    }


}
