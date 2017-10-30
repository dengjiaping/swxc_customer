package com.shiwaixiangcun.customer;

import android.app.Application;
import android.content.Context;

import com.baidu.mobstat.StatService;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.model.HttpHeaders;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;

public class App extends Application {
    private static Context mContext;
    private static App instance;

    public static App getInstance() {
        return instance;
    }

    /**
     * 获取上下文
     *
     * @return Context
     */
    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);


        mContext = this;
        instance = this;
        OkGo.init(this);


        HttpHeaders headers = new HttpHeaders();
        headers.put("X-Requested-With", "XMLHttpRequest");    //所有的 header 都 不支持 中文
        headers.put("User-Agent", "android");

        OkGo.getInstance()//
                .debug("OkHttpUtils")//是否打开调试
                .setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)               //全局的连接超时时间
                .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)                  //全局的读取超时时间
                .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)                 //全局的写入超时时间
                .setCookieStore(new MemoryCookieStore())                           //cookie使用内存缓存（app退出后，cookie消失）
                .setCookieStore(new PersistentCookieStore())                       //cookie持久化存储，如果cookie不过期，则一直有效
                .addCommonHeaders(headers);                                         //设置全局公共头;


        ShareSDK.initSDK(this);



        //百度统计
        StatService.setAppChannel(this, "RepleceWithYourChannel", true);
        StatService.setOn(this, StatService.JAVA_EXCEPTION_LOG);

    }





}
