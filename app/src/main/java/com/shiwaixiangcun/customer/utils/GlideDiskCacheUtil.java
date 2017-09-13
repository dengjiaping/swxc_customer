package com.shiwaixiangcun.customer.utils;

import android.graphics.Bitmap;

import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskCacheAdapter;
import com.bumptech.glide.signature.EmptySignature;

import java.io.File;

/**
 * Created by Administrator on 2017/9/13.
 */

public class GlideDiskCacheUtil {
    private static GlideDiskCacheUtil mInstance = null;
    private Key signature = EmptySignature.obtain();
    private DiskCacheProvider diskCacheProvider;

    private GlideDiskCacheUtil() {

    }

    /**
     * 获得实例的唯一全局访问点
     *
     * @return
     */
    public static GlideDiskCacheUtil getInstance() {
        if (mInstance == null) {
            // 增加类锁,保证只初始化一次
            synchronized (GlideDiskCacheUtil.class) {
                if (mInstance == null) {
                    mInstance = new GlideDiskCacheUtil();
                }
            }
        }
        return mInstance;
    }

    /**
     * Glide配制处初始化，进行缓存绑定
     *
     * @param factory
     */
    public void initDiskCacheFactory(DiskCache.Factory factory) {
        if (diskCacheProvider == null) {
            diskCacheProvider = new LazyDiskCacheProvider(factory);
        }
    }

    public boolean isExists(String id) {
        File mFile = get(id);
        return mFile != null && mFile.exists();
    }

    public File get(String id) {
        return get(id, signature);
    }

    public File get(String id, Key signature) {
        return diskCacheProvider.getDiskCache().get(new OriginalKey(id, signature));
    }

    public void delete(String id) {
        delete(id, signature);
    }

    public void delete(String id, Key signature) {
        diskCacheProvider.getDiskCache().delete(new OriginalKey(id, signature));
    }

    public void clear() {
        diskCacheProvider.getDiskCache().clear();
    }

    /**
     * @param id
     * @param data 可传文件  文件路径  位图对象
     */
    public void put(String id, Object data) {
        diskCacheProvider.getDiskCache().put(new OriginalKey(id, signature), new SourceWriter(data));
    }


    public void put(String id, DiskCache.Writer writer) {
        diskCacheProvider.getDiskCache().put(new OriginalKey(id, signature), writer);
    }

    public void put(String id, Key signature, DiskCache.Writer writer) {
        diskCacheProvider.getDiskCache().put(new OriginalKey(id, signature), writer);
    }


    interface DiskCacheProvider {
        DiskCache getDiskCache();
    }

    private static class LazyDiskCacheProvider implements DiskCacheProvider {

        private final DiskCache.Factory factory;
        private volatile DiskCache diskCache;

        public LazyDiskCacheProvider(DiskCache.Factory factory) {
            this.factory = factory;
        }

        @Override
        public DiskCache getDiskCache() {
            if (diskCache == null) {
                synchronized (this) {
                    if (diskCache == null) {
                        diskCache = factory.build();
                    }
                    if (diskCache == null) {
                        diskCache = new DiskCacheAdapter();
                    }
                }
            }
            return diskCache;
        }
    }

    class SourceWriter implements DiskCache.Writer {

        private final Object data;

        public SourceWriter(Object data) {
            this.data = data;
        }

        @Override
        public boolean write(File file) {
            boolean success = false;
            try {
                if (data instanceof Bitmap) {
                    success = StreamUtil.saveBitmap(file.getAbsolutePath(), (Bitmap) data, 100);
                } else if (data instanceof String) {
                    success = StreamUtil.copyFile((String) data, file.getAbsolutePath());
                } else if (data instanceof File) {
                    success = StreamUtil.copyFile(((File) data).getAbsolutePath(), file.getAbsolutePath());
                }
            } catch (Exception e) {
                LogUtil.d(getClass().getName(), "Failed to find file to write to disk cache");
            }
            return success;
        }
    }

}
