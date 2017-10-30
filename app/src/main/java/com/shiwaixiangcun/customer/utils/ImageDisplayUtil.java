package com.shiwaixiangcun.customer.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.shiwaixiangcun.customer.R;

/**
 *
 * @author Administrator
 * @date 2017/9/12
 */

public class ImageDisplayUtil {

    /**
     * @param context
     * @param errorImg  加载失败图片
     * @param url
     * @param imageView 显示图片控件
     */
    public static void showImageView(Context context, int errorImg, String url,
                                     ImageView imageView) {
        Glide.with(context).load(url)// 加载图片
                .error(errorImg)// 设置错误图片
                .crossFade()// 设置淡入淡出效果，默认300ms，可以传参
                .placeholder(errorImg)// 设置占位图
                .diskCacheStrategy(DiskCacheStrategy.RESULT)// 缓存修改过的图片
                .into(imageView);
    }

    /**
     * @param context
     * @param url
     * @param imageView
     */
    public static void showImageView(Context context, String url,
                                     ImageView imageView) {

        Glide.with(context)
                // 加载图片
                .load(url)
                // 设置淡入淡出效果，默认300ms，可以传参
                .crossFade()
                // 缓存修改过的图片
                .diskCacheStrategy(DiskCacheStrategy.RESULT)

                .error(R.drawable.image_error)
                .into(imageView);
    }

}
