package com.shiwaixiangcun.customer.ui.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalAPI;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.event.EventCenter;
import com.shiwaixiangcun.customer.event.SimpleEvent;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 春雨医生
 */
public class ChunyuDoctorActivity extends BaseActivity {

    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.tv_page_name)
    TextView mTvPageName;
    @BindView(R.id.myProgressBar)
    ProgressBar mMyProgressBar;
    @BindView(R.id.webview)
    WebView mWebView;


    private String strToken;

    private StringBuilder urlBuilder = new StringBuilder();

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
        setContentView(R.layout.activity_chunyu_doctor);
        ButterKnife.bind(this);
        EventCenter.getInstance().register(this);
        initData();
        initWebView();
        initViewAndEvent();
    }

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
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
    }

    private void initViewAndEvent() {
        mTvPageName.setText("普通问诊");
    }

    private void initData() {
        strToken = (String) AppSharePreferenceMgr.get(mContext, GlobalConfig.TOKEN, "");
        OkGo.<String>get(GlobalAPI.getDoctor)
                .params("access_token", strToken)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(BUG_TAG, response.body());
                        ChunyuDoctorActivity.DoctorBean doctorBean = JsonUtil.fromJson(response.body(), ChunyuDoctorActivity.DoctorBean.class);
                        if (doctorBean == null) {
                            return;
                        }
                        EventCenter.getInstance().post(new SimpleEvent(SimpleEvent.UPDATE_DOCTOR, 1, doctorBean.getData()));
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(SimpleEvent simpleEvent) {
        if (simpleEvent == null || simpleEvent.mEventType != SimpleEvent.UPDATE_DOCTOR) {
            return;
        }
        DoctorBean.DataBean data = (DoctorBean.DataBean) simpleEvent.getData();
        urlBuilder.append(GlobalAPI.chunyuDoctor).append("/cooperation/wap/login/")
                .append("?atime=").append(data.getAtime())
                .append("&partner=").append(data.getPartner())
                .append("&sign=").append(data.getSign())
                .append("&user_id=").append(data.getUser_id());
        removeCookie(mContext);
        mWebView.clearCache(true);

        //获取本地user_agent;
        String userAgentString = mWebView.getSettings().getUserAgentString();
        //设置user_agent(以asyncHttprequest为例)
//        client.setUserAgent(defaultUserAgent);

        Map<String, String> header = new HashMap<>();
        header.put("user-agent", userAgentString);
        mWebView.loadUrl(urlBuilder.toString(), header);
        mWebView.setWebChromeClient(new ChunyuDoctorActivity.MyWebChromeViewClient());
        mWebView.setWebViewClient(new ChunyuDoctorActivity.MyWebViewClient());

    }

    @OnClick(R.id.back_left)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        EventCenter.getInstance().unregister(this);
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    private static class DoctorBean {

        /**
         * data : {"atime":"1506666038","partner":"shiwaishenghuo","sign":"5b14863c5731b810","user_id":"15520447006"}
         * message : 操作成功
         * responseCode : 1001
         * success : true
         */

        private DataBean data;
        private String message;
        private int responseCode;
        private boolean success;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getResponseCode() {
            return responseCode;
        }

        public void setResponseCode(int responseCode) {
            this.responseCode = responseCode;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public class DataBean {
            /**
             * atime : 1506666038
             * partner : shiwaishenghuo
             * sign : 5b14863c5731b810
             * user_id : 15520447006
             */

            private String atime;
            private String partner;
            private String sign;
            private String user_id;

            public String getAtime() {
                return atime;
            }

            public void setAtime(String atime) {
                this.atime = atime;
            }

            public String getPartner() {
                return partner;
            }

            public void setPartner(String partner) {
                this.partner = partner;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }
        }
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

    private final class MyWebViewClient extends WebViewClient {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            mWebView.loadUrl(request.getUrl().toString());
            return true;
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
            view.loadUrl(url);
            return true;
        }

    }

}
