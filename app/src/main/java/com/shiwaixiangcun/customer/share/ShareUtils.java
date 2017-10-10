package com.shiwaixiangcun.customer.share;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.http.Common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.utils.WechatHelper;

/**
 * Created by Administrator on 2017/9/21.
 */

public class ShareUtils {

    public static void Share(Context mContext, PlatformActionListener paListener,
                             String platform_name, String title, String summary) {
        Share(mContext, paListener, platform_name, title, summary, null, null, null,
                Platform.SHARE_TEXT);
    }

    public static void Share(Context mContext, PlatformActionListener paListener,
                             String platform_name, String title, String summary, String imageurl, String imagepath) {
        Share(mContext, paListener, platform_name, title, summary, imageurl, imagepath,
                Common.domainPM + "mi/article/detailView.htm", Platform.SHARE_WEBPAGE);
    }

    public static void Share(Context mContext, PlatformActionListener paListener,
                             String platform_name, String title, String summary, String imageurl, String imagepath,
                             String url) {
        Share(mContext, paListener, platform_name, title, summary, imageurl, imagepath, url,
                Platform.SHARE_WEBPAGE);
    }

    public static void Share(Context mContext, PlatformActionListener paListener,
                             String platform_name, String title, String summary, String imageUrl, String imagePath,
                             String url, int shareType) {
        System.out.println("platform_name:" + platform_name + ",title:" + title + ",summary:" + summary + ",imageurl:" + imageUrl + ",imagepath:" + imagePath + ",url:" + url + ",sharetype:" + shareType);
        if (platform_name != null) {
            ShareSDK.initSDK(mContext);
            WechatHelper.ShareParams sp = new WechatHelper.ShareParams();
            sp.setTitle(title);
            sp.setText(summary);
            if (!Wechat.NAME.equals(platform_name) || Platform.SHARE_WEBPAGE == shareType) {
                if (TextUtils.isEmpty(imageUrl)) {
                    if (TextUtils.isEmpty(imagePath)) {
                        initShareIcon(mContext);
                        sp.setImagePath(mContext.getFilesDir().getAbsolutePath() + File.separator + "ic_launcher.png");
                        sp.setImageData(BitmapFactory.decodeResource(mContext.getResources(),
                                R.drawable.ic_launcher));
                    } else {
                        sp.setImagePath(imagePath);
                    }
                } else {
                    sp.setImageUrl(imageUrl);
                }
            }
            sp.setTitleUrl(url);
            sp.setSite(mContext.getString(R.string.app_name));
            sp.setUrl(url);
            sp.setSiteUrl(url);
            sp.setShareType(shareType);
            Platform platform = ShareSDK.getPlatform(platform_name);
            platform.setPlatformActionListener(paListener);
            platform.share(sp);
        }
    }

    public static boolean Authorize(Context mContext, PlatformActionListener paListener, String platform_name) {
        ShareSDK.initSDK(mContext);
        Platform platform = ShareSDK.getPlatform(mContext, platform_name);
        if (platform.isValid()) {
            return true;
        } else {
            platform.setPlatformActionListener(paListener);
            platform.authorize();
            return false;
        }
    }

    private static void initShareIcon(Context context) {
        File iconFile = new File(context.getFilesDir().getAbsolutePath() + File.separator + "start_page.png");
        if (!iconFile.exists()) {
            try {
                InputStream lancher = context.getResources().openRawResource(R.drawable.start_page);
                FileOutputStream outStream = new FileOutputStream(iconFile);
                byte[] buffer = new byte[1024];
                int count = 0;
                while ((count = lancher.read(buffer)) != -1) {
                    outStream.write(buffer, 0, count);
                }
                outStream.close();
                lancher.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
