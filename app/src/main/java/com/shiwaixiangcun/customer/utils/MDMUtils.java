package com.shiwaixiangcun.customer.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.shiwaixiangcun.customer.app.AppContext;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/9/13.
 * 全局工具类
 */
public class MDMUtils {

    /**
     * 屏幕宽
     */
    public static int mScreenWidth = 0;
    /**
     * 屏幕高
     */
    public static int mScreenHeight = 0;

    /**
     * 状态栏高度
     */
    public static int mStatusBarHeight = 0;

    static {
        mScreenWidth = SharedPreferenceUtil.getInstance().getInt("ScreenWidth", 0);
        mScreenHeight = SharedPreferenceUtil.getInstance().getInt("ScreenHeight", 0);
        mStatusBarHeight = SharedPreferenceUtil.getInstance().getInt("StatusBarHeight", 0);
    }

    /**
     * init
     *
     * @param mActivity
     */
    public static void initScreenSize(Activity mActivity) {
        Display dis = mActivity.getWindowManager().getDefaultDisplay();
        setScreenSize(dis.getWidth(), dis.getHeight());

        int resourceId = mActivity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            mStatusBarHeight = mActivity.getResources().getDimensionPixelSize(resourceId);
        }

        SharedPreferenceUtil.getInstance().setInt("ScreenWidth", mScreenWidth);
        SharedPreferenceUtil.getInstance().setInt("ScreenHeight", mScreenHeight);
        SharedPreferenceUtil.getInstance().setInt("StatusBarHeight", mStatusBarHeight);
    }

    /**
     * 作用：
     * <p>
     * 设置屏幕宽高
     * </p>
     *
     * @param width
     * @param height
     */
    public static void setScreenSize(int width, int height) {
        mScreenWidth = width;
        mScreenHeight = height;
    }

    public static int dip2px(float dipValue) {

        final float scale = AppContext.mMainContext.getResources().getDisplayMetrics().density;

        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(float pxValue) {
        final float scale = AppContext.mMainContext.getResources().getDisplayMetrics().density;

        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(float pxValue) {
        final float fontScale = AppContext.mMainContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(float spValue) {
        final float fontScale = AppContext.mMainContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 子文件目录
     *
     * @param subFolder
     * @return
     */
    public static String getFolderDir(String subFolder) {
        File mFile = null;
        if (isSDCardEnable()) {
            mFile = new File(getAppSDRootDir(), subFolder);
        } else {
            mFile = new File(getAppPhoneRootDir(), subFolder);
        }

        if (!mFile.exists()) {
            mFile.mkdirs();
        }

        return mFile.getAbsolutePath() + File.separator;
    }

    public static String getSDRootDir() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }

    public static String getAppSDRootDir() {
        try {
            File file = AppContext.mMainContext.getExternalCacheDir();
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            return file.getParent() + File.separator;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getAppPhoneRootDir() {
        try {
            File file = AppContext.mMainContext.getCacheDir();
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            return file.getParent() + File.separator;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * SD是否可用
     *
     * @return
     */
    public static boolean isSDCardEnable() {
        try {
            return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 验证url的合法性
     *
     * @param url
     * @return
     */
    public static boolean checkUrl(String url) {
        return url.matches("^[a-zA-z]+://[^\\s]*$");
    }

    /**
     * 验证手机号是否合法
     *
     * @param mobiles
     * @return boolean
     */
    public static boolean isMobile(String mobiles) {

        String num = "[1][358]\\d{9}";
        return num.matches(mobiles);
    }

    /**
     * 验证邮箱是否合法
     *
     * @return boolean
     */
    public static boolean isEmail(String str) {
        if (str == null) {
            return false;
        }

        String strPattern = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]+");
        return pattern.matcher(str).matches();
    }

    public static boolean isAlpha(String str) {
        String strPattern = "[a-zA-Z]+";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean isAlphaNumeric(String str) {
        String strPattern = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{2,}$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /*
     * 提取url中的参数
     */
    public static String getParameter(String url, String name) {
        if (url == null || name == null) {
            return null;
        }

        int start = url.indexOf(name + "=");
        if (start == -1)
            return null;
        int len = start + name.length() + 1;
        int end = -1;
        if (url.substring(len).startsWith("{")) {
            end = getParameterEnd(url, len);
        } else {
            end = url.indexOf("&", len);
        }
        if (end == -1) {
            end = url.length();
        }

        return url.substring(len, end);
    }

    private static int getParameterEnd(String url, int start) {
        int count = 0;
        int end = -1;
        for (int i = start; i < url.length(); i++) {
            if (url.charAt(i) == '{') {
                count++;
            }
            if (url.charAt(i) == '}') {
                count--;
            }
            if (count == 0) {
                end = i + 1;
                break;
            }
        }
        return end;
    }

    /**
     * 判断GPS是否打开
     *
     * @return
     */
    public static final boolean isGPSOPen() {
        LocationManager locationManager = (LocationManager) AppContext.mMainContext.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return gps;
    }

    /**
     * GPS开关
     */
    public static final void openGPS() {
        Intent gpsIntent = new Intent();
        gpsIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
        gpsIntent.addCategory("android.intent.category.ALTERNATIVE");
        gpsIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(AppContext.mMainContext, 0, gpsIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }


    /**
     * WIFI网络开关
     *
     * @param mContext
     * @param enabled
     */
    public static final void toggleWiFi(Context mContext, boolean enabled) {
        WifiManager wm = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        //wm.setWifiEnabled(enabled);
    }

    /**
     * 移动网络开关
     *
     * @param mContext
     * @param enabled
     */
    public static final void toggleMobileData(Context mContext, boolean enabled) {
        ConnectivityManager conMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        Class<?> conMgrClass = null; // ConnectivityManager类
        Field iConMgrField = null; // ConnectivityManager类中的字段
        Object iConMgr = null; // IConnectivityManager类的引用
        Class<?> iConMgrClass = null; // IConnectivityManager类
        Method setMobileDataEnabledMethod = null; // setMobileDataEnabled方法

        try {
            // 取得ConnectivityManager类
            conMgrClass = Class.forName(conMgr.getClass().getName());
            // 取得ConnectivityManager类中的对象mService
            iConMgrField = conMgrClass.getDeclaredField("mService");
            // 设置mService可访问
            iConMgrField.setAccessible(true);
            // 取得mService的实例化类IConnectivityManager
            iConMgr = iConMgrField.get(conMgr);
            // 取得IConnectivityManager类
            iConMgrClass = Class.forName(iConMgr.getClass().getName());
            // 取得IConnectivityManager类中的setMobileDataEnabled(boolean)方法
            setMobileDataEnabledMethod = iConMgrClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            // 设置setMobileDataEnabled方法可访问
            setMobileDataEnabledMethod.setAccessible(true);
            // 调用setMobileDataEnabled方法
            setMobileDataEnabledMethod.invoke(iConMgr, enabled);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static final void toggleSoftInput() {
        InputMethodManager imm = (InputMethodManager) AppContext.mMainContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static final void hideSoftInput(Context mContext, View view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view == null) {
            if (mContext instanceof Activity) {
                view = ((Activity) mContext).getCurrentFocus();
            }
        }
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 关闭软键盘
     */
    public static final void hideSoftInput(Context mContext) {
        hideSoftInput(mContext, null);
    }

    public static final void showSoftInput(Context mContext, View view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view == null) {
            if (mContext instanceof Activity) {
                view = ((Activity) mContext).getCurrentFocus();
            }
        }
        if (view != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        }
    }

    public static final void showSoftInput(Context mContext) {
        showSoftInput(mContext, null);
    }

    public static String getPhoneNumber(Context mContext) {
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getLine1Number();
    }

    /**
     * 半角转换为全角
     *
     * @param input
     * @return
     */
    public static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127 && c[i] > 32)
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

    /**
     * 获取Applicatin中元数据
     *
     * @param mContext
     * @param key
     * @return
     */
    public static String getMetaData(Context mContext, String key) {
        String value = "";
        try {
            ApplicationInfo appInfo = mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
            value = appInfo.metaData.get(key).toString();
        } catch (Exception e) {
        }
        return value;
    }

    /**
     * 安装应用
     *
     * @param context 上下文
     *                apk全名
     */
    public static final void setupApp(Context context, String apkPath) {

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            File file = new File(apkPath);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取安装应用版本号
     *
     * @param mContext
     * @param packageName
     */
    public static final String getVersion(Context mContext, String packageName) {
        String version = "";
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo info = pm.getPackageInfo(packageName, 0);
            if (info != null) {
                version = String.valueOf(info.versionName);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }

        return version;
    }

    public static final String getVersionCode(Context mContext, String packageName) {
        String versionCode = "";
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo info = pm.getPackageInfo(packageName, 0);
            if (info != null) {
                versionCode = String.valueOf(info.versionCode);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return versionCode;
    }

    public static final PackageInfo getPackageInfoByPath(Context mContext, String mFilePath) {
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo info = pm.getPackageArchiveInfo(mFilePath, PackageManager.GET_ACTIVITIES);
            return info;
        } catch (Exception e) {
            //e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取未安装应用版本号
     *
     * @param mContext
     */
    public static final String getVersionByPath(Context mContext, String mFilePath) {
        String version = "";
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo info = pm.getPackageArchiveInfo(mFilePath, PackageManager.GET_ACTIVITIES);
            if (info != null) {
                version = String.valueOf(info.versionName);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }

        return version;
    }

    public static final boolean isServiceStart(Class<?> className) {
        try {
            ActivityManager mActivityManager = (ActivityManager) AppContext.mMainContext.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningServiceInfo> serviceList = mActivityManager.getRunningServices(30);
            for (int i = 0; i < serviceList.size(); i++) {
                if (serviceList.get(i) != null && serviceList.get(i).service.getPackageName().equals(AppContext.mMainContext.getPackageName()) && serviceList.get(i).service.getClass().getName().equals(className.getName())) {
                    return true;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }

    public final static boolean isScreenLocked() {
        android.app.KeyguardManager mKeyguardManager = (KeyguardManager) AppContext.mMainContext.getSystemService(Context.KEYGUARD_SERVICE);
        return mKeyguardManager.inKeyguardRestrictedInputMode();
    }

    public final static boolean isUseApp() {
        ActivityManager am = (ActivityManager) AppContext.mMainContext.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        return cn.getPackageName().equals(AppContext.mMainContext.getPackageName()) && !isScreenLocked();
    }

    public final static boolean isCurrentActivity(Object obj) {
        try {
            ActivityManager am = (ActivityManager) AppContext.mMainContext.getSystemService(Context.ACTIVITY_SERVICE);
            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;

            String className = "";
            if (obj != null && obj instanceof View) {
                className = ((View) obj).getContext().getClass().getName();
            } else if (obj != null && obj instanceof Context) {
                className = obj.getClass().getName();
            }

            return cn.getClassName().equals(className);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }

    public static final boolean isAppInFront() {
        try {
            ActivityManager am = (ActivityManager) AppContext.mMainContext.getSystemService(Context.ACTIVITY_SERVICE);
            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
            String switchPackageName = cn.getPackageName();
            String localPackageName = AppContext.mMainContext.getPackageName();
            return localPackageName.endsWith(switchPackageName);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }


    public final static String getRealPathFromURI(Uri contentUri) {
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            ContentResolver mResolver = AppContext.mMainContext.getContentResolver();
            Cursor cursor = mResolver.query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    /**
     * 获取手机的imei号
     *
     * @return
     */
    public static final String getIMEI(Context mContext) {
        String android_id = "";
        try {
            TelephonyManager telManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            android_id = telManager.getDeviceId();
            if (android_id == null) {
                android_id = android.provider.Settings.System.getString(mContext.getContentResolver(), "android_id");
            }
        } catch (Exception e) {
        }
        return android_id;
    }

    /**
     * 获取手机的imsi号
     *
     * @return
     */
    public static final String getIMSI(Context mContext) {
        String id = "";
        try {
            TelephonyManager telManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            id = telManager.getSubscriberId();
        } catch (Exception e) {
        }
        return id;
    }

    public static CharSequence getHtmlString(String txt) {
        try {
            CharSequence html = Html.fromHtml("<font color='black'>" + txt + "\u3000\u3000</font>");
            return html;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 是否是平板
     *
     * @param context
     * @return
     */
    public static boolean isTabletDevice(Context context) {
        int screenLayout = context.getResources().getConfiguration().screenLayout;
        int cur = screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        if (cur == Configuration.SCREENLAYOUT_SIZE_UNDEFINED)
            return false;
        return cur >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 获取Unicode编码
     *
     * @param gbString
     * @return
     */
    public static String encodeUnicode(String gbString) {
        char[] utfBytes = gbString.toCharArray();
        String unicodeBytes = "";
        for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
            String hexB = Integer.toHexString(utfBytes[byteIndex]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }

    /**
     * 比较系统版本号
     *
     * @param v1
     * @param v2
     * @return 0--等；-1--小；1--大
     */
    public static int compareVersion(String v1, String v2) {
        if (v1 == null) {
            return -1;
        }

        if (v2 == null) {
            return 1;
        }

        if (v1.equals(v2)) {
            return 0;
        }

        String[] arrayStr1 = v1.split("\\.");
        String[] arrayStr2 = v2.split("\\.");

        int leng1 = arrayStr1.length;
        int leng2 = arrayStr2.length;
        int leng = (leng1 > leng2) ? leng2 : leng1;

        int result = 0;
        for (int i = 0; i < leng; i++) {
            result = arrayStr1[i].length() - arrayStr2[i].length();
            if (isNumeric(arrayStr1[i]) && isNumeric(arrayStr2[i])) {
                result = Integer.valueOf(arrayStr1[i]) - Integer.valueOf(arrayStr2[i]);

                if (result != 0) {
                    return result > 0 ? 1 : -1;
                }
            }

            if (result != 0) {
                return result > 0 ? 1 : -1;
            }
            result = arrayStr1[i].compareTo(arrayStr2[i]);
            if (result != 0) {
                return result > 0 ? 1 : -1;
            }
        }

        if (result == 0) {
            if (leng1 > leng2) {
                result = 1;
            } else if (leng1 < leng2) {
                result = -1;
            }
        }

        return result;
    }

    public static String fromFenToYuan(int fen) {
        DecimalFormat format = new DecimalFormat("0.##");
        format.setMaximumFractionDigits(2);

        return format.format((float) fen / 100);
    }

    /**
     * 应用是否第一次安装
     *
     * @return
     */
    public static boolean isFirstRun() {
        try {
            AppContext.mMainContext.openFileInput(AppContext.mMainContext.getPackageName());
            return false;
        } catch (Exception e) {
        }
        return true;
    }

    /**
     * 设置安装标志位
     *
     * @return
     */
    public static void setFirstRun(boolean isFirst) {
        try {
            if (isFirst) {
                AppContext.mMainContext.deleteFile(AppContext.mMainContext.getPackageName());
            } else {
                AppContext.mMainContext.openFileOutput(AppContext.mMainContext.getPackageName(), Context.MODE_WORLD_WRITEABLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 七牛图片处理 具体参数参考：http://developer.qiniu.com/docs/v6/api/reference/fop/image/imageview2.html
     *
     * @param url
     * @param mode
     * @param w
     * @param h
     * @param format
     * @return
     */
    public static String dealQiNiuImageUrl(String url, int mode, int w, int h, String format) {
        try {
            if (url != null && (url.contains("clouddn") || url.contains("qiniucdn") || url.contains("qiniudn") || url.contains("resource.haitai.tv") || url.contains("qnssl.com")) && !url.contains("imageView2") && (w > 0 || h > 0)) {
                if (url.contains("?")) {
                    url += "&" + "imageView2/" + mode + "/w/" + w + "/h/" + h;
                } else {
                    url += "?" + "imageView2/" + mode + "/w/" + w + "/h/" + h;
                }

                if (StringUtil.isNotEmpty(format)) {
                    url += "/format/" + format;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return url;
    }

    public static final void setCallPhoneMode() {
        AudioManager audioManager = (AudioManager) AppContext.mMainContext.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMode(AudioManager.MODE_IN_CALL);// 把模式调成听筒放音模式
        audioManager.setSpeakerphoneOn(false);

    }

    public static final void setNormalPhoneMode() {
        AudioManager audioManager = (AudioManager) AppContext.mMainContext.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMode(AudioManager.MODE_NORMAL);
        audioManager.setSpeakerphoneOn(true);
    }

    /**
     * screen是否打开状态
     *
     * @return
     */
    public static boolean isScreenOn() {
        boolean screenState;
        try {
            PowerManager manager = (PowerManager) AppContext.mMainContext.getSystemService(Activity.POWER_SERVICE);
            Method mReflectScreenState = PowerManager.class.getMethod("isScreenOn");
            screenState = (Boolean) mReflectScreenState.invoke(manager);
        } catch (Exception e) {
            screenState = false;
        }
        return screenState;
    }

    /**
     * 获取本地IP地址
     *
     * @return
     */
    public static final String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
        }
        return null;
    }

}