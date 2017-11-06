package com.shiwaixiangcun.customer.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.shiwaixiangcun.customer.app.App;

/**
 * Created by Administrator on 2017/9/27.
 */

public class SPUtils {
    private final static String name = "shiwaixiangcun";// 表名
    public static SharedPreferences sp;
    public static SharedPreferences.Editor ed;

    static {
        sp = App.getInstance().getApplicationContext()
                .getSharedPreferences(name, Context.MODE_PRIVATE);
        ed = sp.edit();
    }

    private SPUtils() {
        super();
    }

    /**
     * @param key
     * @param value
     * @return 添加成功返回true，否则false
     * @Description: 添加boolean
     * @author liliwei
     * @create 2013-8-20 下午4:49:07
     * @updateTime 2013-8-20 下午4:49:07
     */
    public static boolean putBoolean(String key, boolean value) {
        try {
            ed.putBoolean(key, value);
            ed.commit();

        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }

    /**
     * @param key
     * @param value
     * @return
     * @Description: TODO
     * @author liliwei
     * @create 2013-8-20 下午4:55:51
     * @updateTime 2013-8-20 下午4:55:51
     */
    public static boolean getBoolean(String key, boolean value) {
        return sp.getBoolean(key, value);
    }

    public static boolean putFloat(String key, float value) {
        try {
            ed.putFloat(key, value);
            ed.commit();

        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;

    }

    public static float getFloat(String key, float value) {
        return sp.getFloat(key, 0f);

    }

    public static boolean putInt(String key, int value) {
        try {

            ed.putInt(key, value);
            ed.commit();
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;

    }

    public static int getInt(String key, int value) {
        return sp.getInt(key, 0);

    }

    public static boolean putLong(String key, Long value) {
        try {
            ed.putFloat(key, value);
            ed.commit();
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;

    }

    public static long getLong(String key, Long value) {
        return sp.getLong(key, value);

    }

    public static boolean putString(String key, String value) {
        try {
            ed.putString(key, value);
            ed.commit();
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;

    }

    /**
     * @param key
     * @param value 默认
     * @return
     */
    public static String getString(String key, String value) {
        return sp.getString(key, value);

    }

    public static void removeShare(String key) {
        ed.remove(key);
        ed.commit();
    }


}
