package com.shiwaixiangcun.customer.photo.adpater;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.photo.core.PhotoFinal;
import com.shiwaixiangcun.customer.photo.utils.PhotoUtil;

import java.util.List;


/**
 * Created by Qinda on 2016/2/16.
 */
public class PhotoShowListAdpater extends ViewHolderAdapter<PhotoShowListAdpater.PhotoViewHolder, String> {

    private int mScreenWidth;
    private int mRowWidth;
    private List<String> mSelectList;

    public PhotoShowListAdpater(Context context, List<String> list, int mScreenWidth) {
        super(context, list);
        this.mScreenWidth = mScreenWidth;
        this.mSelectList = list;
        this.mRowWidth = mScreenWidth / 3;
    }

    @Override
    public int getCount() {
        return super.getCount() + 1;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = inflate(R.layout.item_photo, viewGroup);
        setHeight(view);
        return new PhotoViewHolder(view);
    }

    public void setHeight(View view) {
        int height = mScreenWidth / 4 - 8;
        view.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
    }


    @Override
    public void onBindViewHolder(PhotoViewHolder viewHolder, final int position) {
        Log.i("bbbbbbbbbnnn",position+"-----------"+mSelectList.size());
        if (position == mSelectList.size()) {
            PhotoUtil.display((Activity) getContext(), R.mipmap.phone_lpw, viewHolder.iv_thumb, mRowWidth, mRowWidth);
            Log.i("bbbbbbbbbbbvvv",position+"-----------"+getMaxSize());
//            if (position == getMaxSize()-1){
                viewHolder.iv_delete_select.setVisibility(View.GONE);
//            }

            final int maxSize = getMaxSize();
            if (position == maxSize) {
                viewHolder.iv_thumb.setVisibility(View.GONE);

            }
        } else {
            Log.i("bbbbbbbbb", mSelectList.get(position));
            PhotoUtil.display((Activity) getContext(), mSelectList.get(position), viewHolder.iv_thumb, mRowWidth, mRowWidth);
            viewHolder.iv_delete_select.setVisibility(View.VISIBLE);
        }

        viewHolder.iv_delete_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("vvvvvvooo","点击了删除"+position);
                mSelectList.remove(position);
                notifyDataSetChanged();
                onImageListener.imageScence(mSelectList);
            }
        });
    }

    private int getMaxSize() {
        if (PhotoFinal.getFunctionConfig() != null)
            return PhotoFinal.getFunctionConfig().getMaxSize();
        return -1;
    }

    public class PhotoViewHolder extends ViewHolderAdapter.ViewHolder {

        public ImageView iv_thumb;
        public ImageView iv_check;
        public ImageView iv_delete_select;

        public PhotoViewHolder(View view) {
            super(view);
            iv_check = (ImageView) view.findViewById(R.id.iv_check);
            iv_delete_select = (ImageView) view.findViewById(R.id.iv_delete_select);
            iv_check.setVisibility(View.GONE);

            iv_delete_select.setVisibility(View.VISIBLE);
//            Resources resources = getContext().getResources();
//            Drawable drawable = resources.getDrawable(R.mipmap.h);
//            iv_check.setImageDrawable(drawable);
            iv_thumb = (ImageView) view.findViewById(R.id.iv_thumb);
        }
    }
    private onImageListener onImageListener;
    //添加日历回调
    public interface onImageListener {
        void imageScence(List<String> json);
    }

    public void setImageListener(onImageListener onImageListener) {
        this.onImageListener = onImageListener;
    }

}
