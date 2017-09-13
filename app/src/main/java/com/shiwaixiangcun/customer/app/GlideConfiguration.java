package com.shiwaixiangcun.customer.app;

import android.content.Context;
import android.text.format.Formatter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.engine.executor.FifoPriorityThreadPoolExecutor;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.request.target.ViewTarget;
import com.shiwaixiangcun.customer.utils.DeviceUtil;
import com.shiwaixiangcun.customer.utils.GlideDiskCacheUtil;
import com.shiwaixiangcun.customer.utils.GlideMemCacheUtil;
import com.shiwaixiangcun.customer.utils.LogUtil;
import com.shiwaixiangcun.customer.utils.MDMUtils;

/**
 * Created by Administrator on 2017/9/13.
 */

public class GlideConfiguration implements GlideModule {

    public static final int MAX_VALUE = 240 * 1024 * 1024;

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // Apply options to the builder here.
        ViewTarget.setTagId(Integer.MAX_VALUE);
        // builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        builder.setResizeService(new FifoPriorityThreadPoolExecutor(Math.max(3, Runtime.getRuntime().availableProcessors())));// 最少3个下载线程
        builder.setDiskCacheService(new FifoPriorityThreadPoolExecutor(2)); // 两个本地线程

        DiskCache.Factory diskCacheFactory = null;
        try {
            if (MDMUtils.isSDCardEnable()) {
                String path = DeviceUtil.getSDCardPath();
                if (path != null) {
                    long total = DeviceUtil.getSDCardAvailableSize(path);
                    if (total < DeviceUtil.getRamAvailableSize(context)) {
                        throw new RuntimeException("优先使用内部存储");
                    }

                    LogUtil.i("SD free total:", Formatter.formatFileSize(context, total));
                    long size = getCacheSize(total, MAX_VALUE);
                    if (size != 0) {
                        diskCacheFactory = new ExternalCacheDiskCacheFactory(context, "imageCache", (int) size);
                    } else {
                        throw new RuntimeException("SD卡存储已经满");
                    }
                } else {
                    throw new RuntimeException("未获取存储路径");
                }
            } else {
                throw new RuntimeException("SD卡不可用");
            }
        } catch (Exception e) {
            e.printStackTrace();
            long total = DeviceUtil.getRamAvailableSize(context);
            LogUtil.i("internal free total:", Formatter.formatFileSize(context, total));
            long size = getCacheSize(total, MAX_VALUE);
            if (size != 0) {
                diskCacheFactory = new InternalCacheDiskCacheFactory(context, "imageCache", (int) size);
            }
        }
        if (diskCacheFactory != null) {
            builder.setDiskCache(diskCacheFactory);
            GlideDiskCacheUtil.getInstance().initDiskCacheFactory(diskCacheFactory);
        }


        MemorySizeCalculator calculator1 = new MemorySizeCalculator(context);
        MemoryCache memoryCache = new LruResourceCache(calculator1.getMemoryCacheSize());
        builder.setMemoryCache(memoryCache);
        GlideMemCacheUtil.getInstance().initMemoryCache(memoryCache);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        // register ModelLoaders here.
    }

    /**
     * 计算最优存储大小
     *
     * @param total
     * @param size
     * @return
     */
    public long getCacheSize(long total, long size) {
        if (total > size) {
            // default
        } else if (total > size / 2) {
            size = size / 2;
        } else if (total > size / 4) {
            size = size / 4;
        } else if (total > size / 8) {
            size = size / 8;
        } else if (total > size / 16) {
            size = size / 16;
        } else if (total > 0) {
            size = total;
        } else {
            size = 0;
        }

        return size;
    }

}
