package com.shiwaixiangcun.customer;

import android.os.PersistableBundle;

/**
 * Created by Administrator on 2017/9/15.
 * 全局网络请求地址
 */

public class GlobalConfig {

    //测试地址
    private static String DEBUG_DOMAIN = "http://mk.shiwaixiangcun.cn";

    public static String getAddress = DEBUG_DOMAIN + "/mc/delivery/address/listdata.json";
    public static String deleteAddress = DEBUG_DOMAIN + "/mc/delivery/address/remove.json";
    public static String modifyDefaultAddress = DEBUG_DOMAIN + "/mc/delivery/address/modify.json";
    public static String addAddress = DEBUG_DOMAIN + "/mc/delivery/address/add.json";

}
