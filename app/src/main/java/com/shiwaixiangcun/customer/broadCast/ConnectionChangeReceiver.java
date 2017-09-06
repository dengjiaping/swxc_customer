package com.shiwaixiangcun.customer.broadCast;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author Javen
 */
public class ConnectionChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
            //改变背景或者 处理网络的全局变量
            if (onNetStateScenceListener != null) {
                onNetStateScenceListener.netStateScence(false);
            }

        } else {
            //改变背景或者 处理网络的全局变量
            if (onNetStateScenceListener != null) {
                onNetStateScenceListener.netStateScence(true);
            }

        }
    }

    private OnNetStateScenceListener onNetStateScenceListener;

    //网络监测回调
    public interface OnNetStateScenceListener {
        void netStateScence(Boolean b);
    }

    public void setNetStateScenceListener(OnNetStateScenceListener onNetStateScenceListener) {
        this.onNetStateScenceListener = onNetStateScenceListener;
    }


}