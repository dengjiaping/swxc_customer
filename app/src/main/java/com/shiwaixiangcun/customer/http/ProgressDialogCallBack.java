package com.shiwaixiangcun.customer.http;

import android.app.Activity;
import android.text.format.Formatter;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.shiwaixiangcun.customer.ui.dialog.DialogProgress;

/**
 * Created by Administrator on 2017/10/27.
 */

public abstract class ProgressDialogCallBack extends StringCallback {

    private DialogProgress mDialogProgress;

    private Activity mActivity;

    public ProgressDialogCallBack(Activity activity) {
        mActivity = activity;
        mDialogProgress = new DialogProgress(activity);
        mDialogProgress.setCanceledOnTouchOutside(false);
    }


    @Override
    public void onStart(Request<String, ? extends Request> request) {
        if (mDialogProgress != null && !mDialogProgress.isShowing()) {
            mDialogProgress.show();
        }
    }

    @Override
    public void onFinish() {
        if (mDialogProgress != null && mDialogProgress.isShowing()) {
            mDialogProgress.dismiss();
        }
    }


    @Override
    public void uploadProgress(Progress progress) {
        String downloadLength = Formatter.formatFileSize(mActivity, progress.currentSize);
        String totalLength = Formatter.formatFileSize(mActivity, progress.totalSize);
        mDialogProgress.setMax(10000);
        mDialogProgress.setProgress((int) (progress.fraction * 10000));
        mDialogProgress.setFileSize(downloadLength + "/" + totalLength);
    }


    @Override
    public void onError(Response<String> response) {
        super.onError(response);
        mDialogProgress.dismiss();
    }
}
