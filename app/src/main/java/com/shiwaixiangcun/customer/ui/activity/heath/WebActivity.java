package com.shiwaixiangcun.customer.ui.activity.heath;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.event.EventCenter;
import com.shiwaixiangcun.customer.event.SimpleEvent;
import com.shiwaixiangcun.customer.http.Common;
import com.shiwaixiangcun.customer.model.LoginResultBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.utils.JsonUtil;
import com.shiwaixiangcun.customer.utils.SharePreference;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;

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

    private boolean isApperenceToken = false;
    private int type;
    private StringBuilder urlBuilder = new StringBuilder();
    private String tokenString;

    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        EventCenter.getInstance().register(this);
        ButterKnife.bind(this);
        initData();
        initWebview();
        initView();
    }

    /**
     * 初始化WebView
     */
    private void initWebview() {
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

    private void initView() {
        mBackLeft.setOnClickListener(this);
        switch (type) {
            case 3:
                mTvPageName.setText("健康方案");
                urlBuilder.append("http://hm.shiwaixiangcun.cn/mc/scheme/view.htm");
                isApperenceToken = true;
                break;
            case 4:
                urlBuilder.append("http://hm.shiwaixiangcun.cn/mc/serviceList/view.htm");
                mTvPageName.setText("健康动态");
                isApperenceToken = true;
                break;
            case 5:
                urlBuilder.append("https://wy.guahao.com/fastorder/hospital");
                mTvPageName.setText("预约挂号");
                break;
            case 7:
                mTvPageName.setText("在线问诊");
                requestDoctor();
                break;
            case 14:
                urlBuilder.append("https://ztg.zhongan.com/promote/showcase/landingH5.htm?promoteType=2&promotionCode=INST170970022019&redirectType=h5");
                mTvPageName.setText("健康保险");
                break;
            case 15:
                urlBuilder.append("http://u.ctrip.com/union/CtripRedirect.aspx?TypeID=2&Allianceid=683754&sid=1217406&OUID=&jumpUrl=http%3A%2F%2Fwww.ctrip.com%2F%3FAllianceid%3D683754%26sid%3D1217406%26OUID%3D%26MultiUnionSupport%3Dtrue");
                mTvPageName.setText("旅游度假");
                break;
        }
        if (isApperenceToken) {

            urlBuilder.append("?access_token=").append(tokenString);
        }
        Log.e(BUG_TAG, urlBuilder.toString());
        mWebView.loadUrl(urlBuilder.toString());
        mWebView.setWebViewClient(new MyWebViewClient());

    }

    private void requestDoctor() {
        OkGo.<String>get(GlobalConfig.getDoctor)
                .params("access_token", tokenString)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(BUG_TAG, response.body());
                        DoctorBean doctorBean = JsonUtil.fromJson(response.body(), DoctorBean.class);
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

        urlBuilder.append("https://test.chunyu.me/cooperation/wap/login/")
                .append("?atime=").append(data.getAtime())
                .append("&partner=").append(data.getPartner())
                .append("&sign=").append(data.getSign())
                .append("&user_id=").append(data.getUser_id());
        Log.e(BUG_TAG, urlBuilder.toString());
        mWebView.loadUrl(urlBuilder.toString());

    }


    private void initData() {
        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt("type", -1);

        String loginInfo = SharePreference.getStringSpParams(mContext, Common.IS_SAVE_LOGIN, Common.SISAVELOGIN);
        Type type = new TypeToken<ResponseEntity<LoginResultBean>>() {
        }.getType();
        ResponseEntity<LoginResultBean> responseEntity = JsonUtil.fromJson(loginInfo, type);
        tokenString = responseEntity.getData().getAccess_token();
//        userId=responseEntity.getData().get


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
    private final class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private final class DoctorBean {

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
}
