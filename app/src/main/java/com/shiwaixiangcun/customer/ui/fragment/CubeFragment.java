package com.shiwaixiangcun.customer.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/9/21.
 */

public abstract class CubeFragment extends Fragment implements ICubeFragment {

    private static final boolean DEBUG = false;
    protected Object mDataIn;
    protected View mRootView;
    private boolean mFirstResume = true;

    protected abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public CubeFragmentActivity getContext() {
        return (CubeFragmentActivity) getActivity();
    }

    /**
     * ===========================================================
     * Implements {@link ICubeFragment}
     * ===========================================================
     */
    @Override
    public void onEnter(Object data) {
        mDataIn = data;
        if (DEBUG) {
            showStatus("onEnter");
        }
    }

    @Override
    public void onLeave() {
        if (DEBUG) {
            showStatus("onLeave");
        }
    }

    @Override
    public void onBackWithData(Object data) {
        if (DEBUG) {
            showStatus("onBackWithData");
        }
    }

    @Override
    public boolean processBackPressed() {
        return false;
    }

    @Override
    public void onBack() {
        if (DEBUG) {
            showStatus("onBack");
        }
    }

    /**
     * Not add self to back stack when removed, so only when Activity stop
     */
    @Override
    public void onStop() {
        super.onStop();
        if (DEBUG) {
            showStatus("onStop");
        }
        onLeave();
    }

    /**
     * Only when Activity resume, not very precise.
     * When activity recover from partly invisible, onBecomesPartiallyInvisible will be triggered.
     */
    @Override
    public void onResume() {
        super.onResume();
        if (!mFirstResume) {
            onBack();
        }
        if (mFirstResume) {
            mFirstResume = false;
        }
        if (DEBUG) {
            showStatus("onResume");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (DEBUG) {
            showStatus("onAttach");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (DEBUG) {
            showStatus("onCreate");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (DEBUG) {
            showStatus("onActivityCreated");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (DEBUG) {
            showStatus("onStart");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (DEBUG) {
            showStatus("onPause");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (DEBUG) {
            showStatus("onDestroyView");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (DEBUG) {
            showStatus("onDestroy");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (DEBUG) {
            showStatus("onDetach");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = createView(inflater, container, savedInstanceState);
        return mRootView;
    }

    public final View findViewById(@IdRes int id) {
        if (id < 0 || mRootView == null) {
            return null;
        }
        return mRootView.findViewById(id);
    }

    private void showStatus(String status) {
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        return false;
    }
}

