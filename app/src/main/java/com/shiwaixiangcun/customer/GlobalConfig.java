package com.shiwaixiangcun.customer;

/**
 * Created by Administrator on 2017/9/15.
 * 全局网络请求地址
 */

public class GlobalConfig {

    //测试地址

    private static String OT_DOMAIN = "http://ot.shiwaixiangcun.cn";
    //商城首页相关接口
    public static String getBanner = OT_DOMAIN + "/mi/banner/listdata.json";
    private static String DEBUG_DOMAIN = "http://mk.shiwaixiangcun.cn";
    private static String DOMAIN = DEBUG_DOMAIN;
    //地址相关接口
    public static String getAddress = DOMAIN + "/mc/delivery/address/listdata.json";
    public static String deleteAddress = DOMAIN + "/mc/delivery/address/remove.json";
    public static String modifyDefaultAddress = DOMAIN + "/mc/delivery/address/modify.json";
    public static String addAddress = DOMAIN + "/mc/delivery/address/add.json";
    //订单相关接口
    public static String getAllOrders = DOMAIN + "/mc/order/my/listPage.json";
    public static String deleteOrder = DOMAIN + "/mc/order/customer/delete.json";
    public static String cancelOrder = DOMAIN + "/mc/order/cancel.json";
    public static String confirmOrder = DOMAIN + "/mc/order/customer/receive.json";
    public static String putOrder = DOMAIN + "/mc/order/add.json";
    public static String getOrderDetail = DOMAIN + "/mc/order/detail.json";
    public static String getOrderContent = DOMAIN + "/mi/goods/detail/content.htm";
    public static String getMallHome = DOMAIN + "/mi/goods/subject/home.json";
    public static String getGoodDetail = DOMAIN + "/mi/goods/detail.json";
    public static String getStock = DOMAIN + "/mi/goods/detail/attribute/";
    public static String getGuessLike = DOMAIN + "/mi/goods/subject/listpage.json";
    public static String getKeyword = DOMAIN + "/mi/keywords.json";
    public static String searchGood = DOMAIN + "/mi/goods/search/listpage.json";

    //支付
    public static String payZhiFuBao = DOMAIN + "/mc/pay/ZhiFuBao.json";
    public static String payWeiXin = DOMAIN + "/mc/pay/WeiXin.json";
    public static String getCategory = DOMAIN + "/mi/goods/category/tree.json";
    public static String checkToken = DOMAIN + "/mc/validate/access/token.json";
}
