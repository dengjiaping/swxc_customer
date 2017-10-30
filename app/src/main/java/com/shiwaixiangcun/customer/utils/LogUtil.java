package com.shiwaixiangcun.customer.utils;

import android.os.Build;
import android.util.Log;

import com.shiwaixiangcun.customer.app.AppContext;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author Administrator
 * @date 2017/9/13
 */

public class LogUtil {

    public static int DEBUG_LEVEL = 1;
    private static volatile boolean WRITE_FILE_LOG = false;

    public static boolean isDebug() {
        return isDebug(1);
    }

    public static boolean isDebug(int level) {
        return level >= DEBUG_LEVEL;
    }

    // 冗余信息输出
    public static void v(String tag, String msg) {
        if (isDebug(0) && StringUtil.isNotEmpty(msg)) {
            Log.v(tag, msg);
            writeLog("v", tag, msg);
        }
    }

    // 调试信息输出
    public static void d(String tag, String msg) {
        if (isDebug(1) && StringUtil.isNotEmpty(msg)) {
            Log.d(tag, msg);
            writeLog("d", tag, msg);
        }
    }

    // 提示信息输出
    public static void i(String tag, String msg) {
        if (isDebug(2) && StringUtil.isNotEmpty(msg)) {
            Log.i(tag, msg);
            writeLog("i", tag, msg);
        }
    }

    // 警告信息输出
    public static void w(String tag, String msg) {
        if (isDebug(3) && StringUtil.isNotEmpty(msg)) {
            Log.w(tag, msg);
            writeLog("w", tag, msg);
        }
    }

    // 错误信息输出
    public static void e(String tag, String msg) {
        if (isDebug(4) && StringUtil.isNotEmpty(msg)) {
            Log.e(tag, msg);
            writeLog("e", tag, msg);
        }
    }

    public static void e(String tag, Throwable e) {
        if (isDebug(4) && e != null) {
            StringWriter mExceptionMsg = new StringWriter();
            e.printStackTrace(new PrintWriter(mExceptionMsg));
            String msg = mExceptionMsg.toString();

            e(tag, msg);
        }
    }

    // 日志记录到文件中
    private static void writeLog(String flag, String tag, String msg) {
        try {
            if (!WRITE_FILE_LOG) {
                return;
            }

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.DONUT && MDMUtils.isSDCardEnable()) {

                File mFile = null;
                String DATE = DateUtil.getDay(System.currentTimeMillis());
                String filePath = MDMUtils.getFolderDir("LOG");
                if ("e".equals(flag)) {
                    mFile = new File(filePath + "ERROR" + ".log");
                } else {
                    mFile = new File(filePath + DATE + ".log");
                }

                FileWriter out = new FileWriter(mFile, true);
                out.write("[日志级别：" + flag + "] \n");
                out.write("[时间：" + DateUtil.getCurDateStr() + "] [机型：" + android.os.Build.MODEL + "] [系统版本：" + Build.VERSION.RELEASE + "] \n");
                out.write("[IMEI：" + MDMUtils.getIMEI(AppContext.mMainContext) + "] \n");
                out.write("[应用版本：" + MDMUtils.getVersion(AppContext.mMainContext, AppContext.mMainContext.getPackageName()) + "] \n");

                out.write("[日志Tag：" + tag + "] \n");
                out.write("[日志：" + msg + "] \n");
                out.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除日志，保留最近几天的
     *
     * @param days
     */
    public static void cleanLog(int days) {
        try {
            if (!MDMUtils.isSDCardEnable()) {
                return;
            }
            String filePath = MDMUtils.getFolderDir("LOG");
            File mFile = new File(filePath);
            File[] mFiles = mFile.listFiles();
            long current = System.currentTimeMillis();
            long dayTimes = 24 * 60 * 60 * 1000 * days;
            for (File temp : mFiles) {
                long time = temp.lastModified();
                if (current - time > dayTimes) {
                    temp.delete();
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}

