package com.shiwaixiangcun.customer.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.widget.NumberProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * @author XuJhin
 * @date 2017/10/27
 */

public class DialogProgress extends Dialog {
    @BindView(R.id.downloadSize)
    TextView mDownloadSize;
    @BindView(R.id.pbProgress)
    NumberProgressBar mPbProgress;
    private Context mContext;

    public DialogProgress(@NonNull Context context) {
        super(context, R.style.AlertDialogStyle);
        this.mContext = context;
        initView();
    }

    public DialogProgress(@NonNull Context context, @StyleRes int themeResId) {
        super(context, R.style.AlertDialogStyle);
        this.mContext = context;
        initView();

    }

    private void initView() {
        setContentView(R.layout.layout_dialog_progress);
        ButterKnife.bind(this);
    }

    @Override
    public void show() {
        super.show();
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.9);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
    }

    public void setMax(int i) {
        mPbProgress.setMax(i);
    }

    public void setProgress(int progress) {
        mPbProgress.setProgress(progress);
    }

    public void setFileSize(String fileSize) {
        mDownloadSize.setText(fileSize);
    }
}
