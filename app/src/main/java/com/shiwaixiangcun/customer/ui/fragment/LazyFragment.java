package com.shiwaixiangcun.customer.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ta.utdid2.android.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 懒加载Fragment
 */

public abstract class LazyFragment extends Fragment {
    protected Activity mContext;
    Unbinder unbinder;
    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;
    private boolean isPrepared;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getContentViewLayoutID() != 0) {
            return inflater.inflate(getContentViewLayoutID(), null);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initViewsAndEvents(view);
        mContext = this.getActivity();
    }

    protected abstract int getContentViewLayoutID();

    protected abstract void initViewsAndEvents(View view);


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    private synchronized void initPrepare() {
        if (isPrepared) {
            onFirstUserVisible();
        } else {
            isPrepared = true;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInvisible();
            } else {
                onUserInvisible();
            }
        }
    }

    /**
     * 用户第一次可见
     */
    protected abstract void onFirstUserVisible();

    /**
     * 用户可见
     */
    protected abstract void onUserVisible();

    private void onFirstUserInvisible() {
    }

    /**
     * 用户不可见
     */
    protected abstract void onUserInvisible();

    @Override
    public void onDestroy() {
        destroyViewAndThing();
        unbinder.unbind();
        super.onDestroy();
    }

    /**
     * 销毁数据
     */
    protected abstract void destroyViewAndThing();

    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivityForResult(intent, requestCode);
    }

    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    protected void showToast(String msg) {
        if (null != msg && !StringUtils.isEmpty(msg)) {
            Snackbar.make(mContext.getWindow().getDecorView(), msg, Snackbar.LENGTH_SHORT).show();
        }
    }
}
