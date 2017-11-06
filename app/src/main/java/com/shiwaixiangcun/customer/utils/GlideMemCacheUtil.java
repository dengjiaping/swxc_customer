package com.shiwaixiangcun.customer.utils;

import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.signature.EmptySignature;

/**
 * Created by Administrator on 2017/9/13.
 */

public class GlideMemCacheUtil {
    private static GlideMemCacheUtil mInstance = null;
    private Key signature = EmptySignature.obtain();
    private MemoryCache memoryCache;

    private GlideMemCacheUtil() {

    }

    /**
     * 获得实例的唯一全局访问点
     *
     * @return
     */
    public static GlideMemCacheUtil getInstance() {
        if (mInstance == null) {
            // 增加类锁,保证只初始化一次
            synchronized (GlideMemCacheUtil.class) {
                if (mInstance == null) {
                    mInstance = new GlideMemCacheUtil();
                }
            }
        }
        return mInstance;
    }

    /**
     * Glide配制处初始化，进行缓存绑定
     *
     * @param memoryCache
     */
    public void initMemoryCache(MemoryCache memoryCache) {
        this.memoryCache = memoryCache;
    }

    public void delete(String id) {
        delete(id, signature);
    }

    public void delete(String id, Key signature) {
        memoryCache.remove(new OriginalKey(id, signature));
    }

    public void clear() {
        memoryCache.clearMemory();
    }

}
