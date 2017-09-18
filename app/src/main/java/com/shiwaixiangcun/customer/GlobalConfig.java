package com.shiwaixiangcun.customer;

/**
 * Created by Administrator on 2017/9/15.
 * 全局网络请求地址
 */

public class GlobalConfig {

    //测试地址

    private static String DEBUG_DOMAIN = "http://mk.shiwaixiangcun.cn";
    private static String DOMAIN = DEBUG_DOMAIN;

    public static String getAddress = DOMAIN + "/mc/delivery/address/listdata.json";
    public static String deleteAddress = DOMAIN + "/mc/delivery/address/remove.json";
    public static String modifyDefaultAddress = DOMAIN + "/mc/delivery/address/modify.json";
    public static String addAddress = DOMAIN + "/mc/delivery/address/add.json";
    public static String getAllOrders = DOMAIN + "/mc/order/my/listPage.json";
    public static String deleteOrder = DOMAIN + "/mc/order/customer/delete.json";
    public static String cancelOrder=DOMAIN+"/mc/order/cancel.json";
    public static String confirmOrder=DOMAIN+"/mc/order/customer/receive.json";
    public static String putOrder=DOMAIN+"/mc/order/add.json";
    public static String getOrderDetail=DOMAIN+"/mc/order/detail.json";
}