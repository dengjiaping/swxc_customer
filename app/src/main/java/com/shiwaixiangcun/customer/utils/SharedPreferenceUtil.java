package com.shiwaixiangcun.customer.utils;

import android.content.SharedPreferences;

import com.shiwaixiangcun.customer.app.AppContext;


/**
 * Created by Administrator on 2017/9/13.
 */

public class SharedPreferenceUtil {


    private final static String CONFIG_FILE_NAME = "config_encrypt";
    private static SharedPreferenceUtil sInstance;

    public static SharedPreferenceUtil getInstance() {
        if (sInstance == null) {
            sInstance = new SharedPreferenceUtil();
        }
        return sInstance;
    }

    /**
     * 获取SharedPreferences.Editor
     *
     * @return
     */
    private SharedPreferences.Editor getEditor() {
        return getSettings().edit();
    }

    /**
     * 获取SharedPreferences
     *
     * @return
     */
    private SharedPreferences getSettings() {
        return AppContext.mMainContext.getSharedPreferences(CONFIG_FILE_NAME, 0);
    }

    /**
     * 获取字符串
     *
     * @param configName
     * @param def
     * @return
     */
    public String getString(String configName, String def) {
        try {
            String value = getSettings().getString(configName, def);
            if (value != null && !value.equals(def)) {
                value = Aes.Decrypt2Str(value, Aes.AES_KEY);
            }
            return value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public String getString(String configName) {
        return getString(configName, "");
    }

    /**
     * 保存字符串
     *
     * @param configName
     * @param value
     * @return
     */
    public boolean setString(String configName, String value) {
        try {
            if (value != null) {
                value = Aes.Encrypt2Str(value, Aes.AES_KEY);
            }
            SharedPreferences.Editor mEditor = getEditor();
            mEditor.putString(configName, value);
            return mEditor.commit();
        } catch (Exception e) {
        }
        return false;
    }

    public boolean removeValue(String configName) {
        SharedPreferences.Editor mEditor = getEditor();
        mEditor.remove(configName);
        return mEditor.commit();
    }

    public int getInt(String configName, int def) {
        return getSettings().getInt(configName, def);
    }

    public boolean setInt(String configName, int def) {
        SharedPreferences.Editor mEditor = getEditor();
        mEditor.putInt(configName, def);
        return mEditor.commit();
    }

    public boolean setLong(String configName, long def) {
        SharedPreferences.Editor mEditor = getEditor();
        mEditor.putLong(configName, def);
        return mEditor.commit();
    }

    public long getLong(String configName, long def) {
        return getSettings().getLong(configName, def);
    }

    public boolean setFloat(String configName, Float def) {
        SharedPreferences.Editor mEditor = getEditor();
        mEditor.putFloat(configName, def);
        return mEditor.commit();
    }

    public Float getFloat(String configName, Float def) {
        return getSettings().getFloat(configName, def);
    }

    public boolean getBoolean(String configName, boolean def) {
        return getSettings().getBoolean(configName, def);
    }

    public boolean setBoolean(String configName, boolean value) {
        SharedPreferences.Editor mEditor = getEditor();
        mEditor.putBoolean(configName, value);
        return mEditor.commit();
    }

    /**
     * 清除所有数据
     */
    public void clearData() {
        SharedPreferences.Editor mEditor = getEditor();
        mEditor.clear();
        mEditor.commit();
    }

}