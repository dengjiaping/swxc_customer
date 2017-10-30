package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.share.OnekeyShare;
import com.shiwaixiangcun.customer.ui.IDetailView;
import com.shiwaixiangcun.customer.utils.SdCordUtil;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.ObservableWebView;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

public class SurroundDetailsActivity extends BaseActivity implements View.OnClickListener, IDetailView {

    private ChangeLightImageView back_left;
    private ImageView iv_share_right;
    private ObservableWebView webView;
    private String articleId;
    private StringBuilder urlWeb = new StringBuilder();
    private StringBuilder urlShare = new StringBuilder();
    private String detailTitle;
    private TextView tv_page_name;
    private TextView tv_top;
    private String detailContent;
    private String shareImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Resources res = getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.start_page);
        shareImage = SdCordUtil.saveMyBitmap("shareImage", bmp);
        //        百度统计
        StatService.setLogSenderDelayed(10);
        StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
        StatService.setSessionTimeOut(30);

        Intent intent = getIntent();
        articleId = intent.getStringExtra("articleId");
        detailTitle = intent.getStringExtra("detailTitle");
        detailContent = intent.getStringExtra("detailContent");
        layoutView();
        initData();

        webView = (ObservableWebView) findViewById(R.id.webview);
        //设置WebView属性，能够执行Javascript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        //加载需要显示的网页
        Log.e("ss", urlWeb.toString());
        webView.loadUrl(urlWeb.toString());
        //设置Web视图
        webView.setWebViewClient(new HelloWebViewClient());

        webView.setOnScrollChangedCallback(new ObservableWebView.OnScrollChangedCallback() {
            @Override
            public void onScroll(int dx, int dy) {
                int scrollY = webView.getScrollY();
                if (scrollY > 300) {
                    if (Utils.isNotEmpty(detailTitle)) {
                        tv_page_name.setText(detailTitle);
                        tv_top.setVisibility(View.VISIBLE);
                    }

                } else {
                    tv_page_name.setText("");
                    tv_top.setVisibility(View.GONE);
                }

            }
        });


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
        urlWeb.append(Common.domainPM).append("/mi/article/detailView.htm").append("?articleId=").append(articleId).append("&app=true");
        urlShare.append(Common.domainPM).append("/mi/article/detailView.htm").append("?articleId=").append(articleId);
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

//    @Override
//    //设置回退
//    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
//            webView.goBack(); //goBack()表示返回WebView的上一页面
//            return true;
//        }
//        return false;
//    }

    @Override
    public void setBgaAdpaterAndClickResult(ResponseEntity result) {

    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(detailTitle);
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(urlShare.toString());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(detailContent);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
//        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(shareImage);//确保SDcard下面存在此张图片

        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(urlShare.toString());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(detailContent);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(urlShare.toString());
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                Toast.makeText(SurroundDetailsActivity.this, "分享成功", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

                Toast.makeText(SurroundDetailsActivity.this, "分享失败", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel(Platform platform, int i) {

                Toast.makeText(SurroundDetailsActivity.this, "取消", Toast.LENGTH_LONG).show();
            }
        });

// 启动分享GUI
        oks.show(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
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
