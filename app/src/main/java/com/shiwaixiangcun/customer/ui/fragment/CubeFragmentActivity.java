package com.shiwaixiangcun.customer.ui.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.shiwaixiangcun.customer.utils.LogUtil;

/**
 * Created by Administrator on 2017/9/21.
 */

public abstract class CubeFragmentActivity extends FragmentActivity {

    private final static String LOG_TAG = "cube-fragment";

    public static boolean DEBUG = LogUtil.isDebug();
    public CubeFragment mCurrentFragment;
    private boolean mCloseWarned;

    /**
     * return the string id of close warning
     * <p/>
     * return value which lower than 1 will exit instantly when press back key
     *
     * @return
     */
    protected abstract String getCloseWarning();

    protected abstract int getFragmentContainerId();

    public void pushFragmentToBackStack(Class<?> cls, Object data) {
        FragmentParam param = new FragmentParam();
        param.cls = cls;
        param.data = data;
        goToThisFragment(param);
    }

    protected String getFragmentTag(FragmentParam param) {
        StringBuilder sb = new StringBuilder(param.cls.toString());
        return sb.toString();
    }

    private void goToThisFragment(FragmentParam param) {
        int containerId = getFragmentContainerId();
        Class<?> cls = param.cls;
        if (cls == null) {
            return;
        }
        try {
            String fragmentTag = getFragmentTag(param);
            FragmentManager fm = getSupportFragmentManager();
            if (DEBUG) {
                LogUtil.d(LOG_TAG, "before operate, stack entry count: " + fm.getBackStackEntryCount());
            }
            CubeFragment fragment = (CubeFragment) fm.findFragmentByTag(fragmentTag);
            if (fragment == null) {
                fragment = (CubeFragment) cls.newInstance();
            }

            FragmentTransaction ft = fm.beginTransaction();
            if (mCurrentFragment != null && mCurrentFragment != fragment) {    //组件发生了变化
                LogUtil.d(LOG_TAG, "组件发生了变化");

                mCurrentFragment.onLeave();
                ft.hide(mCurrentFragment);
                mCurrentFragment.onPause();
            } else if (mCurrentFragment != null && mCurrentFragment == fragment) {//数据发生了变化
                LogUtil.d(LOG_TAG, "组件未发生变化");

                if (!equals(fragment.mDataIn, param.data)) {
                    LogUtil.d(LOG_TAG, "点击:数据发生了变化");
                    fragment.onEnter(param.data);
                }
                return;
            }
            if (fragment.isAdded()) {
                if (DEBUG) {
                    LogUtil.d(LOG_TAG, fragmentTag + " has been added, will be shown again.");
                }
                ft.show(fragment);

                if (!equals(fragment.mDataIn, param.data)) {
                    LogUtil.d(LOG_TAG, "切换:数据发生了变化");
                    fragment.onEnter(param.data);
                }
                fragment.onResume();
            } else {
                if (DEBUG) {
                    LogUtil.d(LOG_TAG, fragmentTag + " is added.");
                }
                fragment.mDataIn = param.data;
                ft.add(containerId, fragment, fragmentTag);
            }
            mCurrentFragment = fragment;
            ft.addToBackStack(fragmentTag);
            ft.commitAllowingStateLoss();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mCloseWarned = false;
    }

    public boolean equals(Object obj1, Object obj2) {
        try {
            if (obj1 == obj2) {
                return true;
            }
            if (obj1 != null && obj2 == null) {
                return false;
            }
            if (obj1 == null && obj2 != null) {
                return false;
            }
            if (obj1 != null && obj2 != null && obj1.getClass() != obj2.getClass()) {
                return false;
            }

            if (obj1 != null && obj2 != null && obj1.hashCode() != obj2.hashCode()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void goToFragment(Class<?> cls, Object data) {
        if (cls == null) {
            return;
        }
        CubeFragment fragment = (CubeFragment) getSupportFragmentManager().findFragmentByTag(cls.toString());
        if (fragment != null) {
            mCurrentFragment = fragment;
            fragment.onBackWithData(data);
        }
        getSupportFragmentManager().popBackStackImmediate(cls.toString(), 0);
    }

    public void popTopFragment(Object data) {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStackImmediate();
        if (tryToUpdateCurrentAfterPop() && mCurrentFragment != null) {
            mCurrentFragment.onBackWithData(data);
        }
    }

    public void popToRoot(Object data) {
        FragmentManager fm = getSupportFragmentManager();
        while (fm.getBackStackEntryCount() > 1) {
            fm.popBackStackImmediate();
        }
        popTopFragment(data);
    }

    public void pushFragmentToRoot(Class<?> cls, Object data) {
        FragmentManager fm = getSupportFragmentManager();
        while (fm.getBackStackEntryCount() > 0) {
            fm.popBackStackImmediate();
        }
        pushFragmentToBackStack(cls, data);
    }

    /**
     * process the return back logic
     * return true if back pressed event has been processed and should stay in current view
     *
     * @return
     */
    protected boolean processBackPressed() {
        return false;
    }

    /**
     * process back pressed
     */
    @Override
    public void onBackPressed() {

        // process back for fragment
        if (processBackPressed()) {
            return;
        }

        // process back for fragment
        boolean enableBackPressed = true;
        if (mCurrentFragment != null) {
            enableBackPressed = !mCurrentFragment.processBackPressed();
        }
        if (enableBackPressed) {
            int cnt = getSupportFragmentManager().getBackStackEntryCount();
            if (cnt <= 1 && isTaskRoot()) {
                String closeWarningHint = getCloseWarning();
                if (!mCloseWarned && !TextUtils.isEmpty(closeWarningHint)) {
                    Toast toast = Toast.makeText(this, closeWarningHint, Toast.LENGTH_SHORT);
                    toast.show();
                    mCloseWarned = true;
                } else {
                    doReturnBack();
                }
            } else {
                mCloseWarned = false;
                doReturnBack();
            }
        }
    }

    private boolean tryToUpdateCurrentAfterPop() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        int cnt = fm.getBackStackEntryCount();
        if (cnt > 0) {
            String name = fm.getBackStackEntryAt(cnt - 1).getName();
            Fragment fragment = fm.findFragmentByTag(name);
            if (fragment != null && fragment instanceof CubeFragment) {
                mCurrentFragment = (CubeFragment) fragment;
                ft.show(mCurrentFragment);
                ft.commitAllowingStateLoss();
                mCurrentFragment.onResume();
            }
            return true;
        }
        return false;
    }

    public void doReturnBack() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count <= 1) {
            finish();
        } else {
            getSupportFragmentManager().popBackStackImmediate();
            if (tryToUpdateCurrentAfterPop() && mCurrentFragment != null) {
                mCurrentFragment.onBack();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean op = false;
        if (mCurrentFragment != null && mCurrentFragment.isVisible()) {
            op = mCurrentFragment.onKeyDown(keyCode, event);
        }
        return op || super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        boolean op = false;
        if (mCurrentFragment != null && mCurrentFragment.isVisible()) {
            op = mCurrentFragment.dispatchKeyEvent(event);
        }
        return op || super.dispatchKeyEvent(event);
    }

    public void hideKeyboardForCurrentFocus() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public void showKeyboardAtView(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public void forceShowKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

    }

    protected void exitFullScreen() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    }
}
