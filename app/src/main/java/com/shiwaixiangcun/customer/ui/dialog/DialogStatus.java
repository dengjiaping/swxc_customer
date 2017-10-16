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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.widget.ChangeLightImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/10/16.
 */


public class DialogStatus extends Dialog {


    @BindView(R.id.back_left)
    ChangeLightImageView mBackLeft;
    @BindView(R.id.iv_submit_expression)
    ImageView mIvSubmitExpression;
    @BindView(R.id.tv_submit_succsse)
    TextView mTvSubmitSuccsse;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;
    private Context mContext;
    private onCallBackListener listener;

    public DialogStatus(@NonNull Context context) {
        super(context, R.style.AlertDialogStyle);
        this.mContext = context;
        initView();
    }

    public DialogStatus(@NonNull Context context, @StyleRes int themeResId) {
        super(context, R.style.AlertDialogStyle);
        this.mContext = context;
        initView();

    }

    private void initView() {
        setContentView(R.layout.layout_dialog_status);
        ButterKnife.bind(this);
    }

    @Override
    public void show() {
        super.show();
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
    }

    public void setDialogTitle(CharSequence title) {
        mTvSubmitSuccsse.setText(title);
    }

    public void setDialogInfo(CharSequence info) {
        mTvContent.setText(info);
    }

    public onCallBackListener getListener() {
        return listener;
    }

    public void setListener(onCallBackListener listener) {
        this.listener = listener;
    }

    @OnClick({R.id.back_left, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_left:
                if (listener != null) {
                    listener.closeBtn(this);
                }
                break;
            case R.id.btn_confirm:
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

        void closeBtn(DialogStatus dialog);

        void confirmBtn(DialogStatus dialog);
    }
}

