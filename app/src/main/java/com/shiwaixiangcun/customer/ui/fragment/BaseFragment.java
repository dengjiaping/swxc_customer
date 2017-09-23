package com.shiwaixiangcun.customer.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.shiwaixiangcun.customer.BaseActivity;
import com.shiwaixiangcun.customer.event.EventCenter;

/**
 * Created by Administrator on 2017/9/15.
 */

public abstract class BaseFragment extends CubeFragment implements View.OnClickListener {
    protected static final String TAG = BaseFragment.class.getName();
    protected String requestID;
    private Context mContext;
    private boolean isPause;
    private boolean mRegistered;


    private boolean visialbe = false;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onResume() {
        super.onResume();
        isPause = false;

    }

    @Override
    public void onPause() {
        super.onPause();
        isPause = true;
        removeProgressDialog();// pause时关闭加载框
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    protected boolean isPause() {
        return isPause;
    }

    public void showMessage(String msg) {

    }

    /**
     * 显示进度对话框
     */
    public final void showProgressDialog(DialogInterface.OnCancelListener mCancel) {
        if (getContext() instanceof BaseActivity) {
//            ((BaseActivity) getContext()).showProgressDialog(mCancel);
        }
    }

    /**
     * 进度对话框是否显示
     *
     * @return
     */
    public final boolean isProgressShowing() {
        if (getContext() instanceof BaseActivity) {
//            return ((BaseActivity) getContext()).isProgressShowing();
        }

        return false;
    }

    /**
     * 隐藏进度对话框
     */
    public final void removeProgressDialog() {
        if (getContext() instanceof BaseActivity) {
//            ((BaseActivity) getContext()).removeProgressDialog();
        }
    }

    public synchronized void register() {
        if (!mRegistered) {
            mRegistered = true;
            EventCenter.getInstance().register(this);
        }
    }

    public synchronized void unRegister() {
        if (mRegistered) {
            mRegistered = false;
            EventCenter.getInstance().unregister(this);
        }
    }

    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this.getActivity(), clazz);
        startActivity(intent);
    }

    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this.getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(this.getActivity(), clazz);
        startActivity(intent);

    }

    protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this.getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);

    }

    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this.getActivity(), clazz);
        startActivityForResult(intent, requestCode);
    }

    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this.getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


}
