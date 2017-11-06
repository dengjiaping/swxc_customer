package com.shiwaixiangcun.customer.app;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.bumptech.glide.Glide;

/**
 * Created by Administrator on 2017/9/13.
 */

public class AppContext {
    public static final Handler mMainHandler = new Handler(Looper.getMainLooper());    // 公共Handler

    /*
     * 初始化上下问
     */
    public static Context mMainContext = null;

    public static void init(Context mainContext) {
        mMainContext = mainContext.getApplicationContext();
        Glide.get(mMainContext); //初始化Glide配制，级联初始化DiskCacheUtil
    }


    public static Context getContext() {
        return mMainContext;
    }

    /**
     * 销毁全局变量
     */
    public static void destory() {
//        DataLoader.getInstance().clearRequests();
//        DownLoader.getInstance().clearRequests();
        Glide.with(mMainContext).onDestroy();
//        TaskUtil.quit();
    }

    public static void clearMemory() {
        Glide.get(mMainContext).clearMemory();
    }
}
