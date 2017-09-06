package com.shiwaixiangcun.customer.broadCast;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/4/1.
 */

public class RegistBrodUtils {

    public static ConnectionChangeReceiver myReceiver;
    private static boolean isNet = false;

    //广播注册
    public static void registerReceiver(final Activity a_regist, final View view) {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        myReceiver = new ConnectionChangeReceiver();
        a_regist.registerReceiver(myReceiver, filter);

        myReceiver.setNetStateScenceListener(new ConnectionChangeReceiver.OnNetStateScenceListener() {
            @Override
            public void netStateScence(Boolean b) {
                if (b) {
                    view.setVisibility(View.GONE);
                } else {
                    view.setVisibility(View.VISIBLE);
                }
            }
        });
        isNet = true;
        if (onIsNetStateScenceListener != null) {
            onIsNetStateScenceListener.isNetStateScence(isNet);
        }

    }


    //取消广播注册
    public static void unregisterReceiver(Activity a_unRegist) {
        a_unRegist.unregisterReceiver(myReceiver);
    }

    private static OnIsNetStateScenceListener onIsNetStateScenceListener;

    //是否注册
    public interface OnIsNetStateScenceListener {
        void isNetStateScence(Boolean b);
    }

    public static void setIsNetStateScenceListener(OnIsNetStateScenceListener onIsNetStateScenceListener) {
        RegistBrodUtils.onIsNetStateScenceListener = onIsNetStateScenceListener;
    }


}
