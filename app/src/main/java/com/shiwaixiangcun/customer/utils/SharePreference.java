package com.shiwaixiangcun.customer.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/8/8.
 */
public class SharePreference {

    public static void saveStringToSpParams(Context context, String spName, String paramName, String vlaue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(paramName, vlaue);
        edit.commit();
    }

    public static String getStringSpParams(Context context, String spName, String paramName) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        String string = sharedPreferences.getString(paramName, "");
        return string;
    }
    //保存图片
    public static void saveBitmapToSpParams(Context context, String spName, String paramName, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(paramName, str);
        edit.commit();
    }

    public static String getBitmapSpParams(Context context, String spName, String paramName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        String string = sharedPreferences.getString(paramName, "");
        return string;
    }
}
