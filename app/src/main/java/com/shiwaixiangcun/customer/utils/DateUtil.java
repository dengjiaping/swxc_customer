/**
 * 用一句话描述该文件做什么.
 *
 * @title DateUtil.java
 * @package com.sinsoft.android.util
 * @author shimiso
 * @update 2012-6-26 上午9:57:56
 */
package com.shiwaixiangcun.customer.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 日期操作工具类.
 *
 * @author shimiso
 */

@SuppressLint("SimpleDateFormat")
public class DateUtil {
    private static final long INTERVAL_IN_MILLISECONDS = 30 * 1000;
    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final int SS = 1000;
    private static final int MI = SS * 60;
    private static final int HH = MI * 60;
    private static final int DD = HH * 24;

    /**
     * 将String 日期转化成为 Date类型
     *
     * @param str
     * @return
     */
    public static Date str2Date(String str) {
        return str2Date(str, null);
    }

    public static Date str2Date(String str, String format) {
        if (str == null || str.length() == 0) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = FORMAT;
        }
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(str);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;

    }

    public static Calendar str2Calendar(String str) {
        return str2Calendar(str, null);

    }

    public static Calendar str2Calendar(String str, String format) {

        Date date = str2Date(str, format);
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        return c;

    }

    public static String date2Str(Calendar c) {// yyyy-MM-dd HH:mm:ss
        return date2Str(c, null);
    }

    public static String date2Str(Calendar c, String format) {
        if (c == null) {
            return null;
        }
        return date2Str(c.getTime(), format);
    }

    public static String date2Str(Date d) {// yyyy-MM-dd HH:mm:ss
        return date2Str(d, null);
    }

    public static String date2Str(Date d, String format) {// yyyy-MM-dd HH:mm:ss
        if (d == null) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String s = sdf.format(d);
        return s;
    }

    public static String getCurDateStr() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH) + "-" + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE)
                + ":" + c.get(Calendar.SECOND);
    }

    /**
     * 获得当前日期的字符串格式
     *
     * @param format
     * @return
     */
    public static String getCurDateStr(String format) {
        Calendar c = Calendar.getInstance();
        return date2Str(c, format);
    }

    /**
     * 将时间格式到秒
     *
     * @param time
     * @return
     */
    public static String getSecond(long time) {

        return new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(time);

    }

    // 格式到天
    public static String getDay(long time) {

        return new SimpleDateFormat("yyyy-MM-dd").format(time);

    }

    // 格式到毫秒
    public static String getSMillon(long time) {
        return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(time);
    }

    /**
     * 获取自定义的时间格式
     *
     * @param time   时间
     * @param format 格式
     * @return string
     */
    public static String getCustomFormat(long time, String format) {
        return new SimpleDateFormat(format).format(time);
    }

    /**
     * 根据给定的时间字符串，返回年月日
     *
     * @param allDate like "yyyy-MM-dd"
     * @return
     */
    public static String getY_M_D(String allDate) {
        if (allDate == null || allDate.length() < 10) {
            return allDate;
        }
        return allDate.substring(0, 10);
    }

    public static boolean checkValidTime(String time, String begTime, String endTime) {
        if (time == null)
            return false;
        return time.compareTo(begTime) >= 0 && time.compareTo(endTime) <= 0;
    }

    public static String getTimestampString(String dateString) {
        Date date = str2Date(dateString);
        return getTimestampString(date);
    }

    public static String getTimestampString(Date messageDate) {
        try {
            Locale curLocale = Locale.CHINA;
            String languageCode = curLocale.getLanguage();
            boolean isChinese = languageCode.contains("zh");

            String format = null;
            long messageTime = messageDate.getTime();
            if (isSameDay(messageTime)) {
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTime(messageDate);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);

                format = "HH:mm";

                if (hour > 17) {
                    if (isChinese) {
                        format = "晚上 hh:mm";
                    }

                } else if (hour >= 0 && hour <= 6) {
                    if (isChinese) {
                        format = "凌晨 hh:mm";
                    }
                } else if (hour > 11 && hour <= 17) {
                    if (isChinese) {
                        format = "下午 hh:mm";
                    }

                } else {
                    if (isChinese) {
                        format = "上午 hh:mm";
                    }
                }
            } else if (isYesterday(messageTime)) {
                if (isChinese) {
                    format = "昨天 HH:mm";
                } else {
                    format = "MM-dd HH:mm";
                }

            } else {
                if (isChinese) {
                    format = "M月d日 HH:mm";
                } else {
                    format = "MM-dd HH:mm";
                }
            }

            if (isChinese) {
                return new SimpleDateFormat(format, Locale.CHINA).format(messageDate);
            } else {
                return new SimpleDateFormat(format, Locale.US).format(messageDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean isSameDay(long inputTime) {

        TimeInfo tStartAndEndTime = getTodayStartAndEndTime();
        return inputTime > tStartAndEndTime.getStartTime() && inputTime < tStartAndEndTime.getEndTime();
    }

    private static boolean isYesterday(long inputTime) {
        TimeInfo yStartAndEndTime = getYesterdayStartAndEndTime();
        return inputTime > yStartAndEndTime.getStartTime() && inputTime < yStartAndEndTime.getEndTime();
    }

    public static TimeInfo getYesterdayStartAndEndTime() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DATE, -1);
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);

        Date startDate = calendar1.getTime();
        long startTime = startDate.getTime();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DATE, -1);
        calendar2.set(Calendar.HOUR_OF_DAY, 23);
        calendar2.set(Calendar.MINUTE, 59);
        calendar2.set(Calendar.SECOND, 59);
        calendar2.set(Calendar.MILLISECOND, 999);
        Date endDate = calendar2.getTime();
        long endTime = endDate.getTime();
        TimeInfo info = new TimeInfo();
        info.setStartTime(startTime);
        info.setEndTime(endTime);
        return info;
    }

    @SuppressLint("SimpleDateFormat")
    @SuppressWarnings("unused")
    public static TimeInfo getTodayStartAndEndTime() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);
        Date startDate = calendar1.getTime();
        long startTime = startDate.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss S");

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.HOUR_OF_DAY, 23);
        calendar2.set(Calendar.MINUTE, 59);
        calendar2.set(Calendar.SECOND, 59);
        calendar2.set(Calendar.MILLISECOND, 999);
        Date endDate = calendar2.getTime();
        long endTime = endDate.getTime();
        TimeInfo info = new TimeInfo();
        info.setStartTime(startTime);
        info.setEndTime(endTime);
        return info;
    }

    public static TimeInfo getBeforeYesterdayStartAndEndTime() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DATE, -2);
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);
        Date startDate = calendar1.getTime();
        long startTime = startDate.getTime();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DATE, -2);
        calendar2.set(Calendar.HOUR_OF_DAY, 23);
        calendar2.set(Calendar.MINUTE, 59);
        calendar2.set(Calendar.SECOND, 59);
        calendar2.set(Calendar.MILLISECOND, 999);
        Date endDate = calendar2.getTime();
        long endTime = endDate.getTime();
        TimeInfo info = new TimeInfo();
        info.setStartTime(startTime);
        info.setEndTime(endTime);
        return info;
    }

    /**
     * endtime为今天
     *
     * @return
     */
    public static TimeInfo getCurrentMonthStartAndEndTime() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.DATE, 1);
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);
        Date startDate = calendar1.getTime();
        long startTime = startDate.getTime();

        Calendar calendar2 = Calendar.getInstance();
        // calendar2.set(Calendar.HOUR_OF_DAY, 23);
        // calendar2.set(Calendar.MINUTE, 59);
        // calendar2.set(Calendar.SECOND, 59);
        // calendar2.set(Calendar.MILLISECOND, 999);
        Date endDate = calendar2.getTime();
        long endTime = endDate.getTime();
        TimeInfo info = new TimeInfo();
        info.setStartTime(startTime);
        info.setEndTime(endTime);
        return info;
    }

    public static TimeInfo getLastMonthStartAndEndTime() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.MONTH, -1);
        calendar1.set(Calendar.DATE, 1);
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);
        Date startDate = calendar1.getTime();
        long startTime = startDate.getTime();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.MONTH, -1);
        calendar2.set(Calendar.DATE, 1);
        calendar2.set(Calendar.HOUR_OF_DAY, 23);
        calendar2.set(Calendar.MINUTE, 59);
        calendar2.set(Calendar.SECOND, 59);
        calendar2.set(Calendar.MILLISECOND, 999);
        calendar2.roll(Calendar.DATE, -1);
        Date endDate = calendar2.getTime();
        long endTime = endDate.getTime();
        TimeInfo info = new TimeInfo();
        info.setStartTime(startTime);
        info.setEndTime(endTime);
        return info;
    }

    public static boolean isCloseEnough(long time1, long time2) {
        // long time1 = date1.getTime();
        // long time2 = date2.getTime();
        long delta = time1 - time2;
        if (delta < 0) {
            delta = -delta;
        }
        return delta < INTERVAL_IN_MILLISECONDS;
    }

    public static String getStartTimeStr(Date beginTime) {
        String str = "";
        if (beginTime != null) {
            long begin = beginTime.getTime();
            long now = System.currentTimeMillis();
            if (begin < now) {
                if (begin == now) {
                    str = " 刚刚";
                } else {
                    long chaju = now - begin;
                    if (chaju < 60 * 1000) {
                        str = " 刚刚";
                    } else {
                        if (chaju < 60 * 60 * 1000) {
                            str = (chaju / 1000) / 60 + " 分钟前";
                        } else {
                            if (chaju < 24 * 60 * 60 * 1000) {
                                str = ((chaju / 1000) / 60) / 60 + " 小时前";
                            } else {
                                if (chaju < 7 * 24 * 60 * 60 * 1000) {
                                    str = (((chaju / 1000) / 60) / 60) / 24 + " 天前";
                                } else {
                                    str = DateUtil.date2Str(beginTime, "yyyy-MM-dd");
                                }
                            }
                        }
                    }
                }
            } else {
                str = "刚刚";
            }
        }
        return str;
    }


    /**
     * 时间信息类
     */
    static class TimeInfo {
        private long startTime;
        private long endTime;

        public TimeInfo() {
        }

        public long getStartTime() {
            return this.startTime;
        }

        public void setStartTime(long paramLong) {
            this.startTime = paramLong;
        }

        public long getEndTime() {
            return this.endTime;
        }

        public void setEndTime(long paramLong) {
            this.endTime = paramLong;
        }
    }
}
