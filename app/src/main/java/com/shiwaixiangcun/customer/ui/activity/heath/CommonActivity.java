package com.shiwaixiangcun.customer.ui.activity.heath;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.ToolBean;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 通用的Activity  处理评测内容
 */

public class CommonActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.iv_share_right)
    ImageView mIvShareRight;
    @BindView(R.id.webview)
    WebView mWebView;

    private ToolBean mToolBean;

    private String type = "";
    private StringBuilder url = new StringBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        mToolBean = bundle.getParcelable("healthEvaluating");
        Log.e(BUG_TAG, mToolBean.name);
        initData();
        initWebView();
        initView();

    }

    /***
     * WebView配置
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

    private void initData() {
        switch (mToolBean.id) {
            case 1:
                type = "Chinese_Medicine";
                break;
            case 2:
                type = "Dietary_Dabits";
                break;
            case 3:
                type = "Endocrine_System";
                break;
            case 4:
                type = "Immune_System";
                break;
            case 5:
                type = "Digestive_System";
                break;
            case 6:
                type = "Respiratory_System";
                break;
            case 7:
                type = "Circulatory_System";
                break;
            case 8:
                type = "Sport_System";
                break;
            case 9:
                type = "Psychology_System";
                break;
            case 10:
                type = "Man_System";
                break;
            case 11:
                type = "Woman_System";
                break;
        }
    }

    private void initView() {
        url.append(GlobalConfig.getEvaluating).append("?assessmentType=").append(type);
        mTvPageName.setText(mToolBean.name);
        mIvShareRight.setVisibility(View.VISIBLE);
        mBackLeft.setOnClickListener(this);
        mIvShareRight.setOnClickListener(this);
        Log.e(BUG_TAG, url.toString());
        mWebView.loadUrl(url.toString());
        mWebView.setWebViewClient(new MyWebViewClient());

    }

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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_left:
                finish();
                break;
            case R.id.iv_share_right:
                Toast.makeText(mContext, "分享", Toast.LENGTH_SHORT).show();
                // TODO: 2017/9/27 完成分享 
                break;
        }

    }

    @Override
    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        return false;
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
