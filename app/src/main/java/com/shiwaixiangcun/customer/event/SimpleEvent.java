package com.shiwaixiangcun.customer.event;/**
 * Author:   xujing
 * Date:  2017/9/13
 * Desc： eg
 */

/**
 * Created by Administrator on 2017/9/13.
 */

public class SimpleEvent {
    //SKU选择更新商品信息
    public static final int UPDATE_GOOD_INFO = 1;

    public static final int UPDATE_MALL = 4;
    public static final int UPDATE_SEARCH = 6;
    public static final int CONFIRM_ORDER = 3;
    public static final int UPDATE_ORDER_DETAIL = 2;
    public static final int UPDATE_GOOD_LIST = 5;
    public static final int UPDATE_GOOD_DETAIL = 7;
    public static final int PAY_SUCCESS = 8;
    public static final int PAY_FAIL = 9;
    public static final int UPDATE_BLOOD_PRESSURE = 10;
    public static final int UPDATE_HEART_RATE = 11;
    public static final int UPDATE_BLOOD_SUGAR = 12;
    public static final int UPDATE_WIGHT = 13;
    public static final int UPDATE_BLOOD_FAT = 14;
    public static final int REFRESH_TOKEN = 15;
    public static final int CHECK_TOKEN = 16;
    public static final int UPDATE_DOCTOR = 17;
    public static final int UPDATE_CATEGORY = 18;
    public static final int GET_RECIPE_TYPE = 19;
    public static final int UPDATE_MAIN = 20;
    /**
     * 操作类型
     */
    public int mEventType;
    /**
     * 操作值
     */
    public int mEventValue;

    public Object mData;

    public SimpleEvent(int mEventType) {
        this.mEventType = mEventType;
    }

    public SimpleEvent(int mEventType, int mEventValue) {
        this.mEventType = mEventType;
        this.mEventValue = mEventValue;
    }

    public SimpleEvent(int mEventType, int mEventValue, Object mData) {
        this.mEventType = mEventType;
        this.mEventValue = mEventValue;
        this.setData(mData);
    }

    /**
     * 数据对象
     */
    public Object getData() {
        return mData;
    }

    public void setData(Object data) {
        mData = data;
    }
}
