package com.shiwaixiangcun.customer.ui.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.share.OnekeyShare;
import com.shiwaixiangcun.customer.ui.IDetailView;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;
import com.shiwaixiangcun.customer.utils.SdCordUtil;
import com.shiwaixiangcun.customer.utils.Utils;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;
import com.shiwaixiangcun.customer.widget.ObservableWebView;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * @author Administrator
 */
public class DetailsActivity extends BaseActivity implements View.OnClickListener, IDetailView {


    private ChangeLightImageView backLeft;
    private ImageView ivShareRight;
    private ObservableWebView webView;
    private String articleId;
    private StringBuilder webUrl = new StringBuilder();
    private StringBuilder shareUrl = new StringBuilder();
    private String detailTitle;
    private TextView tvPageName;
    private TextView tvTop;
    private String detailContent;
    private String shareImage;
    private int siteId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Resources res = getResources();

        siteId = (int) AppSharePreferenceMgr.get(mContext, GlobalConfig.CURRENT_SITE_ID, 0);
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
        Log.e(BUG_TAG, detailTitle);
        Log.e(BUG_TAG, detailContent);
        layoutView();
        initData();


        webView = findViewById(R.id.webview);
        //设置WebView属性，能够执行Javascript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        //加载需要显示的网页
        webView.loadUrl(webUrl.toString());
        //设置Web视图
        webView.setWebViewClient(new HelloWebViewClient());
        webView.setOnScrollChangedCallback(new ObservableWebView.OnScrollChangedCallback() {
            @Override
            public void onScroll(int dx, int dy) {
                int scrollY = webView.getScrollY();
                if (scrollY > 300) {
                    if (Utils.isNotEmpty(detailTitle)) {
                        tvPageName.setText(detailTitle);
                        tvTop.setVisibility(View.VISIBLE);
                    }

                } else {
                    tvPageName.setText("");
                    tvTop.setVisibility(View.GONE);
                }

            }
        });


    }

    private void layoutView() {
        backLeft = findViewById(R.id.back_left);
        ivShareRight = findViewById(R.id.iv_share_right);
        tvTop = findViewById(R.id.tv_top);
        tvPageName = findViewById(R.id.tv_page_name);
        tvPageName.setEllipsize(TextUtils.TruncateAt.END);
        tvPageName.setLines(1);
    }

    private void initData() {


        webUrl.append(Common.domainPM).append("mi/article/detailView.htm")
                .append("?articleId=")
                .append(articleId)
                .append("&siteId=")
                .append(siteId)
                .append("&app=true");
        shareUrl.append(Common.domainPM)
                .append("mi/article/detailView.htm")
                .append("?articleId=")
                .append(articleId)
                .append("&siteId=")
                .append(siteId);
        Log.e(BUG_TAG, webUrl.toString());
        ivShareRight.setVisibility(View.VISIBLE);
        backLeft.setOnClickListener(this);
        ivShareRight.setOnClickListener(this);
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
            default:
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
        oks.setTitle(detailTitle);
        Log.e(BUG_TAG, "标题" + detailTitle);
//        oks.setTitle(detailTitle);
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(shareUrl.toString());
        // text是分享文本，所有平台都需要这个字段
//        oks.setText(detailContent);
        Log.e(BUG_TAG, "内容" + detailContent);
        oks.setText(detailContent);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://resource.hxteb.com/group1/M00/00/26/rBKx5Vl4TMCAUPgUAAB6YxNdWvs030.png");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(shareUrl.toString());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment(detailContent);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getResources().getResourceName(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(shareUrl.toString());
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                Toast.makeText(DetailsActivity.this, "分享成功", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

                Toast.makeText(DetailsActivity.this, "分享失败", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel(Platform platform, int i) {

                Toast.makeText(DetailsActivity.this, "取消分享", Toast.LENGTH_LONG).show();
            }
        });

// 启动分享GUI
        oks.show(this);
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
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
