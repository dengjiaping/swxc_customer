package com.shiwaixiangcun.customer;

/**
 * Created by Administrator on 2017/9/15.
 * 全局网络请求地址
 */

public class GlobalAPI {

    private static final String PM_DOMAIN = "http://pm.shiwaixiangcun.cn";
    //春雨医生测试服务器
    public static String DC_DOMAIN = "https://test.chunyu.me";
    public static String HM_DOMAIN = "http://hm.shiwaixiangcun.cn";
    public static String NO_DOMAIN = "http://shiwaixiangcun.cn";
    public static String OT_DOMAIN = "http://ot.shiwaixiangcun.cn";
    public static String MK_DOMAIN = "http://mk.shiwaixiangcun.cn";
    //春雨医生正式服务器
//    public static String DC_DOMAIN = "https://www.chunyuyisheng.com";
//    public static String HM_DOMAIN = "http://hm.hxteb.com";
//    public static String MK_DOMAIN = "http://mk.hxteb.com";
//    public static String OT_DOMAIN = "http://ot.hxteb.com";
//    public static String NO_DOMAIN = "http://hxteb.com";
    public static String getEvaluating = HM_DOMAIN + "/mi/assessment/view.htm";
    //健康地址获取
    public static String getPhysical = HM_DOMAIN + "/mc/customer/detail.json";
    public static String getHeartRate = HM_DOMAIN + "/mc/pressure/heartrate.json";
    public static String getBloodSugar = HM_DOMAIN + "/mc/sugar/list.json";
    //测试地址
    public static String getWeight = HM_DOMAIN + "/mc/bmi/list.json";
    public static String getBloodFat = HM_DOMAIN + "/mc/blood/fat.json";
    //Token相关接口
    public static String checkToken = HM_DOMAIN + "/mc/validate/access/token.json";
    public static String getDoctor = HM_DOMAIN + "/mc/chunyu/view.json";
    public static String getFamily = HM_DOMAIN + "/mc/family/list.json";
    public static String deleteFamily = HM_DOMAIN + "/mc/removeFamily.json";
    public static String addFamily = HM_DOMAIN + "/mc/addFamily.json";
    public static String getPhone = HM_DOMAIN + "/mc/phone.json";
    public static String chunyuDoctor = DC_DOMAIN;
    public static String getTravel = "http://u.ctrip.com/union/CtripRedirect.aspx?TypeID=2&Allianceid=683754&sid=1217406&OUID=&jumpUrl=http%3A%2F%2Fwww.ctrip.com%2F%3FAllianceid%3D683754%26sid%3D1217406%26OUID%3D%26MultiUnionSupport%3Dtrue\"";
    public static String refreshToken = NO_DOMAIN + "/oauth2/token";
    //商城首页相关接口
    public static String getBanner = OT_DOMAIN + "/mi/banner/listdata.json";
    public static String getJudgeUrl = MK_DOMAIN + "/mi/goods/share/";
    public static String getRecipeType = HM_DOMAIN + "/mi/health/dietType.json";
    public static String getRecipeList = HM_DOMAIN + "/mi/health/dietList.json";
    public static String getMessage = HM_DOMAIN + "/mc/message/core/list.json";
    public static String getMerchant = PM_DOMAIN + "/mi/merchant/listpage/type.json";
    public static String getToolCategory = OT_DOMAIN + "/mi/app/category/tree.json";
    public static String getPrivacy = PM_DOMAIN + "/mi/privacyPolicy.htm";
    public static String getSite = OT_DOMAIN + "/mi/site/listdata.json";
    private static String DOMAIN = MK_DOMAIN;
    public static String getCategory = DOMAIN + "/mi/goods/category/tree.json";
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
    //支付相关接口
    public static String payZhiFuBao = DOMAIN + "/mc/pay/ZhiFuBao.json";
    public static String payWeiXin = DOMAIN + "/mc/pay/WeiXin.json";
    public static String searchCategory = DOMAIN + "/mi/goods/category/listpage.json";
    //分享相关
    public static String shareGoods = DOMAIN + "/mi/goods/share/";
    public static String appLogo = DOMAIN + "http://resource.hxteb.com/group1/M00/00/26/rBKx5Vl4TMCAUPgUAAB6YxNdWvs030.png";
}
