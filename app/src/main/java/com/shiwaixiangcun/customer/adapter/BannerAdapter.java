package com.shiwaixiangcun.customer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.bumptech.glide.Glide;
import com.shiwaixiangcun.customer.R;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.shiwaixiangcun.customer.utils.ResUtil.getResources;

/**
 * Created by Administrator on 2017/9/6.
 */

public class BannerAdapter extends DelegateAdapter.Adapter<BannerAdapter.MainViewHolder> {
    public static List<?> images = new ArrayList<>();
    String[] urls = getResources().getStringArray(R.array.url);
    private Context context;
    private LayoutHelper layoutHelper;
    private RecyclerView.LayoutParams layoutParams;
    private int count = 0;

    public BannerAdapter(Context context, LayoutHelper layoutHelper, int count) {
        this(context, layoutHelper, count,
                new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
    }

    public BannerAdapter(Context context, LayoutHelper layoutHelper, int count,
                         @NonNull RecyclerView.LayoutParams layoutParams) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.count = count;
        this.layoutParams = layoutParams;
        List list = Arrays.asList(urls);
        images = new ArrayList(list);

    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.item_banner, parent, false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {

        holder.mBanner
                .setDelayTime(2500)
                .setImages(images)
                .setImageLoader(new GlideImageLoader())
                .start();
    }

    @Override
    public int getItemCount() {
        return count;
    }

    static class MainViewHolder extends RecyclerView.ViewHolder {
        public Banner mBanner;

        public MainViewHolder(View itemView) {
            super(itemView);
            mBanner = (Banner) itemView.findViewById(R.id.banner);
        }
    }

    static class GlideImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context.getApplicationContext())
                    .load(path)
                    .into(imageView);
        }
    }
}