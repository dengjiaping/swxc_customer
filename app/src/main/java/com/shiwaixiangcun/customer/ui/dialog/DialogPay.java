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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiwaixiangcun.customer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/22.
 */

public class DialogPay extends Dialog implements View.OnClickListener {
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.tv_need_pay)
    TextView mTvNeedPay;
    @BindView(R.id.cb_weixin)
    CheckBox mCbWeixin;
    @BindView(R.id.cb_zhifubao)
    CheckBox mCbZhifubao;
    @BindView(R.id.btn_pay)
    Button mBtnPay;
    private Context mContext;
    private String defaultPay;
    private onCallBackListener listener;

    public DialogPay(@NonNull Context context) {
        super(context, R.style.AlertDialogStyle);
        this.mContext = context;
        init();
    }

    public DialogPay(@NonNull Context context, @StyleRes int themeResId, Context context1) {
        super(context, themeResId);
        this.mContext = context1;
        init();
    }

    public String getDefaultPay() {
        return defaultPay;
    }

    public void setListener(onCallBackListener listener) {
        this.listener = listener;
    }

    private void init() {
        setContentView(R.layout.dialog_pay);
        ButterKnife.bind(this);
        mBtnPay.setOnClickListener(this);
        mIvClose.setOnClickListener(this);
        mCbZhifubao.setChecked(true);
        mCbWeixin.setChecked(false);
        mCbZhifubao.setOnCheckedChangeListener(new ZhifubaoOncheckListener());
        mCbWeixin.setOnCheckedChangeListener(new weixinOnCheckListener());
    }

    public void setPrice(String price) {
        mTvNeedPay.setText(price);
    }

    @Override
    public void show() {
        super.show();
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                if (listener != null) {
                    listener.closeBtn(this);
                }
                break;
            case R.id.btn_pay:
                if (listener != null) {
                    listener.confirmBtn(this);
                }
                break;
        }
    }


    public interface onCallBackListener {

        void closeBtn(DialogPay dialog);

        void confirmBtn(DialogPay dialog);
    }

    public class weixinOnCheckListener implements OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                mCbZhifubao.setChecked(false);
                mCbWeixin.setChecked(true);
                defaultPay = "weixin";
            } else {
                mCbWeixin.setChecked(false);
                defaultPay = "";
            }

        }
    }

    public class ZhifubaoOncheckListener implements OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                mCbZhifubao.setChecked(true);
                mCbWeixin.setChecked(false);
                defaultPay = "zhifubao";
            } else {
                mCbZhifubao.setChecked(false);
                defaultPay = "";
            }
        }
    }
}
