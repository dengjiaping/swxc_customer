package com.shiwaixiangcun.customer.photo.core;


import android.content.Intent;
import android.widget.Toast;

import com.shiwaixiangcun.customer.photo.Callback;
import com.shiwaixiangcun.customer.photo.PhotoEditActivity;
import com.shiwaixiangcun.customer.photo.PhotoSelectActivity;
import com.shiwaixiangcun.customer.photo.model.PhotoInfo;
import com.shiwaixiangcun.customer.photo.utils.DeviceUtils;

import java.util.List;


/**
 * Created by liweiqin on 2016/1/31.
 */
public class PhotoFinal {

    public final static int REQUEST_CODE_CAMERA = 1000;//打开照相机的标识符
    public final static int REQUEST_CODE_MUTI = 1001;//打开相册的标识符

    private static FunctionConfig mCurrentFunctionConfig;
    private static OnHandlerResultCallback mCallback;
    private static Callback mSelectPhotoActivityCallback;

    public static void init(FunctionConfig coreConfig) {
        mCurrentFunctionConfig = coreConfig;

    }


    public static FunctionConfig getFunctionConfig() {
        return mCurrentFunctionConfig;
    }

    public static OnHandlerResultCallback getCallback() {
        return mCallback;
    }

    public static Callback getSelectPhotoActivityCallback() {
        return mSelectPhotoActivityCallback;
    }

    /**
     * 拍照
     *
     * @param callback
     * @param mSelectCallback
     */
    public static void openCamera(OnHandlerResultCallback callback, Callback mSelectCallback) {

        if (mCurrentFunctionConfig == null) {
            if (callback != null) {
                callback.onHanlderFailure(REQUEST_CODE_CAMERA, "没有设置配置");
            }
            return;
        }

        if (!DeviceUtils.existSDCard()) {
            Toast.makeText(mCurrentFunctionConfig.getContext(), "没有SD卡", Toast.LENGTH_SHORT).show();
            return;
        }
        mCallback = callback;
        mSelectPhotoActivityCallback = mSelectCallback;
        Intent intent = new Intent(mCurrentFunctionConfig.getContext(), PhotoEditActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCurrentFunctionConfig.getContext().startActivity(intent);

    }

    /**
     * 打开相册啦
     *
     * @param config
     * @param callback
     */
    public static void openMuti(FunctionConfig config, OnHandlerResultCallback callback) {

        if (config == null && mCurrentFunctionConfig == null) {
            if (callback != null) {
                callback.onHanlderFailure(REQUEST_CODE_MUTI, "没有设置配置");
            }
            return;
        }
        mCallback = callback;
        mCurrentFunctionConfig = config;

        if (!DeviceUtils.existSDCard()) {
            Toast.makeText(mCurrentFunctionConfig.getContext(), "没有SD卡", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(mCurrentFunctionConfig.getContext(), PhotoSelectActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCurrentFunctionConfig.getContext().startActivity(intent);

    }

    public interface OnHandlerResultCallback {
        /**
         * 处理成功
         *
         * @param reqeustCode
         * @param resultList
         */
        void onHandlerSuccess(int reqeustCode, List<PhotoInfo> resultList);

        /**
         * 处理失败或异常
         *
         * @param requestCode
         * @param errorMsg
         */
        void onHanlderFailure(int requestCode, String errorMsg);
    }


}
