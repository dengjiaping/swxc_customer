package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.share.OnekeyShare;
import com.shiwaixiangcun.customer.ui.IDetailView;
import com.shiwaixiangcun.customer.utils.SdCordUtil;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.ObservableWebView;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

public class BannerDetailsActivity extends BaseActivity implements View.OnClickListener, IDetailView {
    private ChangeLightImageView back_left;
    private ImageView iv_share_right;
    private ObservableWebView webview;
    private TextView tv_page_name;
    private TextView tv_top;
    private String bannerlink;
    private String bannerLink_detail;
    private String shareimage;
    private String title_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_details);
        Resources res = getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.start_page);
        shareimage = SdCordUtil.saveMyBitmap("shareimage", bmp);

        Intent intent = getIntent();
        bannerlink = intent.getStringExtra("bannerlink");
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);


        layoutView();
        initData();


        webview = (ObservableWebView) findViewById(R.id.webview);
        //允许加载html网页
        webview.getSettings().setDomStorageEnabled(true);

        //设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);

        //加载需要显示的网页
        webview.loadUrl(bannerLink_detail);
        //设置Web视图
        webview.setWebViewClient(new BannerDetailsActivity.HelloWebViewClient());

        webview.setOnScrollChangedCallback(new ObservableWebView.OnScrollChangedCallback() {
            @Override
            public void onScroll(int dx, int dy) {
                int scrollY = webview.getScrollY();
                if (scrollY > 300) {

                    tv_top.setVisibility(View.VISIBLE);


                } else {
                    tv_page_name.setText("");
                    tv_top.setVisibility(View.GONE);
                }

            }
        });


        WebChromeClient wvcc = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.d("ANDROID_LAB", "TITLE=" + title);
                title_str = title;
            }

        };
        // 设置setWebChromeClient对象
        webview.setWebChromeClient(wvcc);
    }


    private void layoutView() {
        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        iv_share_right = (ImageView) findViewById(R.id.iv_share_right);
        tv_top = (TextView) findViewById(R.id.tv_top);
        tv_page_name = (TextView) findViewById(R.id.tv_page_name);
        tv_page_name.setEllipsize(TextUtils.TruncateAt.END);
        tv_page_name.setLines(1);
    }

    private void initData() {
        bannerLink_detail = bannerlink;
        Log.i("hhoop", bannerLink_detail);
        iv_share_right.setVisibility(View.VISIBLE);
        back_left.setOnClickListener(this);
        iv_share_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.back_left:
                finish();
                break;
            case R.id.iv_share_right:
                showShare();
                break;
        }
    }


    @Override
    public void setBgaAdpaterAndClickResult(ResponseEntity result) {

    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(bannerLink_detail);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(title_str);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://resource.hxteb.com/group1/M00/00/26/rBKx5Vl4TMCAUPgUAAB6YxNdWvs030.png");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(bannerLink_detail);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(bannerLink_detail);
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Toast.makeText(BannerDetailsActivity.this, "分享成功", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Toast.makeText(BannerDetailsActivity.this, "分享失败", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Toast.makeText(BannerDetailsActivity.this, "取消", Toast.LENGTH_LONG).show();
            }
        });


        oks.show(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
        webview.resumeTimers();
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
        webview.pauseTimers();
    }

    //Web视图
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.response.ResponseEntity;
import com.shiwaixiangcun.customer.share.OnekeyShare;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.ObservableWebView;
import com.shiwaixiangcun.customer.utils.SdCordUtil;
import com.shiwaixiangcun.customer.ui.IDetailView;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

public class BannerDetailsActivity extends AppCompatActivity implements View.OnClickListener,IDetailView{
    private ChangeLightImageView back_left;
    private ImageView iv_share_right;
    private ObservableWebView webview;


    private TextView tv_page_name;
    private TextView tv_top;
    private String bannerlink;
    private String bannerLink_detail;
    private String shareimage;
    private String title_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_details);
        Resources res = getResources();
        Bitmap bmp= BitmapFactory.decodeResource(res, R.drawable.start_page);
        shareimage = SdCordUtil.saveMyBitmap("shareimage", bmp);


        Log.i("sssssaaafa","sss====");
        Intent intent = getIntent();
        bannerlink = intent.getStringExtra("bannerlink");
        Log.i("sssssaaafa",bannerlink+"");


        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);


        layoutView();
        initData();



//        HttpRequest.post("ww").executeEntity(new HttpCallBack<ResponseEntity<Object>>() {
//
//            @Override
//            public void onSuccess(ResponseEntity<ResponseEntity<Object>> responseEntity) {
//
//            }
//
//            @Override
//            public void onFailed(Exception e) {
//
//            }
//        });

        webview = (ObservableWebView) findViewById(R.id.webview);
        //允许加载html网页
        webview.getSettings().setDomStorageEnabled(true);

        //设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);

        Log.i("ssssaaaaa========",bannerLink_detail);
        //加载需要显示的网页
        webview.loadUrl(bannerLink_detail);
        //设置Web视图
        webview.setWebViewClient(new BannerDetailsActivity.HelloWebViewClient());

        webview.setOnScrollChangedCallback(new ObservableWebView.OnScrollChangedCallback() {
            @Override
            public void onScroll(int dx, int dy) {
                int scrollY = webview.getScrollY();
                if (scrollY > 300){
//                    if (Utils.isNotEmpty(detailtitle)){
//                        tv_page_name.setText(detailtitle);
                        tv_top.setVisibility(View.VISIBLE);
//                    }

                }else {
                    tv_page_name.setText("");
                    tv_top.setVisibility(View.GONE);
                }
                Log.i("hhhoiioa",dx+"=----"+dy+"------=="+scrollY);
            }
        });


        WebChromeClient wvcc = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.d("ANDROID_LAB", "TITLE=" + title);
//                txtTitle.setText("ReceivedTitle:" +title);
                title_str = title;
            }

        };
        // 设置setWebChromeClient对象
        webview.setWebChromeClient(wvcc);
    }


    private void layoutView() {
        back_left = (ChangeLightImageView) findViewById(R.id.back_left);
        iv_share_right = (ImageView) findViewById(R.id.iv_share_right);
        tv_top = (TextView) findViewById(R.id.tv_top);
        tv_page_name = (TextView) findViewById(R.id.tv_page_name);
        tv_page_name.setEllipsize(TextUtils.TruncateAt.END);
        tv_page_name.setLines(1);
    }

    private void initData() {
        bannerLink_detail = "http://"+bannerlink;
        Log.i("hhoop",bannerLink_detail);
//        DetailImpl detail = new DetailImpl(this,articleId);
//        detail.setBgaAdpaterAndClick(this);
        iv_share_right.setVisibility(View.VISIBLE);
        back_left.setOnClickListener(this);
        iv_share_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.back_left:
                finish();
                break;
            case R.id.iv_share_right:
                showShare();
                break;
        }
    }

//    @Override
//    //设置回退
//    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
//            webview.goBack(); //goBack()表示返回WebView的上一页面
//            return true;
//        }
//        return false;
//    }

    @Override
    public void setBgaAdpaterAndClickResult(ResponseEntity result) {

    }

    //Web视图
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(bannerLink_detail);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(title_str);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
//        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(shareimage);//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(bannerLink_detail);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(bannerLink_detail);
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.i("rrrrrrrrr","onComplete");
                Toast.makeText(BannerDetailsActivity.this,"分享成功",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.i("rrrrrrrrr","onError");
                Log.i("rrrrrrrrr",throwable.toString());
                Toast.makeText(BannerDetailsActivity.this,"分享失败",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Log.i("rrrrrrrrr","onCancel");
                Toast.makeText(BannerDetailsActivity.this,"取消",Toast.LENGTH_LONG).show();
            }
        });

// 启动分享GUI
        oks.show(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
        webview.resumeTimers();
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
        webview.pauseTimers();
    }
}
