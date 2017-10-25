package com.shiwaixiangcun.customer.photo.utils;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.photo.core.PhotoFinal;
import com.shiwaixiangcun.customer.photo.model.PhotoFolderInfo;
import com.shiwaixiangcun.customer.photo.model.PhotoInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by liweiqin on 2016/1/31.
 */
public class PhotoUtil {

    private static final String[] projectionPhotos = {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DATE_TAKEN,
            MediaStore.Images.Media.ORIENTATION,
            MediaStore.Images.Thumbnails.DATA
    };
    private static Drawable defaultDrawable;

    public static void display(Activity activity, String path, ImageView imageView, int width, int height) {
        if (isNotNull(activity, imageView)) return;
        imageView.setImageResource(R.mipmap.ic_gf_default_photo);
        if (null == path) {
            Glide.with(activity).load(R.mipmap.ic_gf_default_photo).into(imageView);
        } else {
            Glide.with(activity)
                    .load(new File(path))
                    .placeholder(R.mipmap.ic_gf_default_photo)
                    .error(defaultDrawable)

                    .centerCrop()
                    .into(imageView);
        }

    }

    public static void display(Activity activity, int drawable, ImageView imageView, int width, int height) {
        if (isNotNull(activity, imageView)) return;
        imageView.setImageResource(R.mipmap.ic_gf_default_photo);
        if (0 == drawable) {
            Glide.with(activity).load(R.mipmap.ic_gf_default_photo).into(imageView);
        } else {
            Glide.with(activity)
                    .load(drawable)
                    .error(defaultDrawable)
                    .centerCrop()
                    .into(imageView);
        }

    }

    private static boolean isNotNull(Activity activity, ImageView imageView) {
        if (null == activity || null == imageView)
            return true;
        if (null == defaultDrawable)
            getDefaultDrawable(activity);
        return false;
    }

    private static void getDefaultDrawable(Activity activity) {
        defaultDrawable = activity.getResources().getDrawable(R.mipmap.ic_gf_default_photo);
    }


    /**
     * 获取所有图片
     *
     * @param activity
     * @param mSelectPhotoMap
     * @return
     */
    public static List<PhotoFolderInfo> getAllPhotoFolder(Activity activity, HashMap<String, PhotoInfo> mSelectPhotoMap) {
        List<PhotoFolderInfo> allFolderList = new ArrayList<>();
        List<String> selectedList = PhotoFinal.getFunctionConfig().getSelectedList();

        final ArrayList<PhotoFolderInfo> allPhotoFolderList = new ArrayList<>();
        HashMap<Integer, PhotoFolderInfo> bucketMap = new HashMap<>();
        Cursor cursor = null;
        //所有照片
        PhotoFolderInfo allPhotoFolderInfo = new PhotoFolderInfo();
        allPhotoFolderInfo.setFolderId(0);
        allPhotoFolderInfo.setFolderName("所有照片");
        allPhotoFolderInfo.setPhotoInfoList(new ArrayList<PhotoInfo>());
        allPhotoFolderList.add(allPhotoFolderInfo);
        try {
            cursor = MediaStore.Images.Media.query(activity.getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    , projectionPhotos, "", null, MediaStore.Images.Media.DATE_TAKEN + " DESC");
            if (cursor != null) {
                int bucketNameColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
                while (cursor.moveToNext()) {
                    final int bucketIdColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID);
                    int bucketId = cursor.getInt(bucketIdColumn);
                    String bucketName = cursor.getString(bucketNameColumn);
                    final int dataColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                    final int imageIdColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                    //int thumbImageColumn = cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA);
                    final int imageId = cursor.getInt(imageIdColumn);
                    final String path = cursor.getString(dataColumn);
                    //final String thumb = cursor.getString(thumbImageColumn);
                    File file = new File(path);
                    final PhotoInfo photoInfo = new PhotoInfo();
                    photoInfo.setPhotoId(imageId);
                    photoInfo.setPhotoPath(path);
                    //photoInfo.setThumbPath(thumb);
                    if (allPhotoFolderInfo.getCoverPhoto() == null) {
                        allPhotoFolderInfo.setCoverPhoto(photoInfo);
                    }
                    //添加到所有图片
                    allPhotoFolderInfo.getPhotoInfoList().add(photoInfo);

                    //通过bucketId获取文件夹
                    PhotoFolderInfo photoFolderInfo = bucketMap.get(bucketId);

                    if (photoFolderInfo == null) {
                        photoFolderInfo = new PhotoFolderInfo();
                        photoFolderInfo.setPhotoInfoList(new ArrayList<PhotoInfo>());
                        photoFolderInfo.setFolderId(bucketId);
                        photoFolderInfo.setFolderName(bucketName);
                        photoFolderInfo.setCoverPhoto(photoInfo);
                        bucketMap.put(bucketId, photoFolderInfo);
                        allPhotoFolderList.add(photoFolderInfo);
                    }
                    photoFolderInfo.getPhotoInfoList().add(photoInfo);

                    if (selectedList != null && selectedList.size() > 0 && selectedList.contains(path)) {
                        mSelectPhotoMap.put(path, photoInfo);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        allFolderList.addAll(allPhotoFolderList);
        if (selectedList != null) {
            selectedList.clear();
        }
        return allFolderList;
    }


}
