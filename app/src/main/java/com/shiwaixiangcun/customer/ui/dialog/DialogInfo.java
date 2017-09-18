package com.shiwaixiangcun.customer.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/9/18.
 * 消息提示对话框
 */

public class DialogInfo extends Dialog {

    @BindView(R.id.dialog_title)
    TextView mDialogTitle;
    @BindView(R.id.dialog_info)
    TextView mDialogInfo;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.tv_confirm)
    TextView mTvConfirm;
    private Context mContext;
    private onCallBackListener listener;

    public DialogInfo(@NonNull Context context) {
        this(context, 0);
        this.mContext = context;
        initView();
    }

    public DialogInfo(@NonNull Context context, @StyleRes int themeResId) {
        super(context, R.style.AlertDialogStyle);
        this.mContext = context;
        initView();

    }

    private void initView() {


        setContentView(R.layout.layout_dialog_info);
        ButterKnife.bind(this);
    }

    @Override
    public void show() {
        super.show();
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.77);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
    }

    public void setDialogTitle(CharSequence title) {
        mDialogTitle.setText(title);
    }

    public void setDialogInfo(CharSequence info) {
        mDialogInfo.setText(info);
    }

    public onCallBackListener getListener() {
        return listener;
    }

    public void setListener(onCallBackListener listener) {
        this.listener = listener;
    }

    @OnClick({R.id.tv_cancel, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                if (listener != null) {
                    listener.closeBtn(this);
                }
                break;
            case R.id.tv_confirm:
                if (listener != null) {
                    listener.confirmBtn(this);
                }
                break;
        }
    }

    /**
     * ui层 点击事件 回调
     *
     * @author LiaoBo
     */
    public interface onCallBackListener {

        void closeBtn(DialogInfo dialog);

        void confirmBtn(DialogInfo dialog);
    }
}
