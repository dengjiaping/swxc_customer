package com.shiwaixiangcun.customer;

import java.io.Serializable;

/**
 * @author Administrator
 * @date 2017/5/24
 */

public class Common implements Serializable {


    /**
     * other
     */
    public static final String ADDRESS = "address";
    public static final String DEFAULT_ADDRESS = "defaultAddress";
    public static final String TOKEN = "token";
    public static final String REFRESH_TOKEN = "refresh";
    public static final String isLogin = "isLogin";
    /**
     * 测试服务器
     */
    public static String domain = "http://shiwaixiangcun.cn/";
    public static String domainPM = "http://pm.shiwaixiangcun.cn/";
    public static String domainHM = "http://hm.shiwaixiangcun.cn";
    public static String domainOT = "http://ot.shiwaixiangcun.cn/";
    /**
     * 正式服务器
     */
//    public static String domain = "http://hxteb.com/";
//    public static String domainPM = "http://pm.hxteb.com/";
//    public static String domainHM = "http://hm.hxteb.com/";
//    public static String domainOT = "http://ot.hxteb.com/";
    /**
     * 登录
     */
    public static String login = domain + "oauth2/token";
    /**
     * 登出
     */
    public static String logout = domainPM + "logout.htm";
    /**
     * 获取验证码
     */
    public static String getVerification = domain + "mi/dynamic/password/message.json";
    public static String getGoodDetail = domain + "mi/goods/detail.json";
    /**
     * viewpager banner列表 public
     */
    public static String listpage = domainOT + "mi/banner/listdata.json";
    /**
     * 文章分页查询  public
     */
    public static String articleListpage = domainPM + "mi/article/listpage.json";
    /**
     * 在线报修  public
     */
    public static String OnlineRepair = domainPM + "mc/online/repair/add.json ";
    /**
     * 多文件上传 post
     */
    public static String filesSend = domainPM + "upload/images.json";
    /**
     * 我要出租 提交 post
     */
    public static String toRent = domainPM + "mc/house/mobile/lease.json";
    /**
     * 管理的房屋列表 post
     */
    public static String associatedHouses = domainPM + "mc/current/houses.json";
    /**
     * 我要售房 post
     */
    public static String toSeller = domainPM + "mc/house/mobile/sell.json";
    /**
     * 我要租房 post
     */
    public static String getRent = domainPM + "mc/house/mobile/rental.json";
    /**
     * 我要买房 post
     */
    public static String getBuy = domainPM + "mc/house/mobile/buy.json";
    /**
     * 提交记录，报修记录 get
     */
    public static String records = domainPM + "mc/online/repair/listpage.json";
    /**
     * 装修列表 get
     */
    public static String decorateList = domainPM + "mi/decorate/company/listpage.json";
    /**
     * 装修详情 get
     */
    public static String companyDetail = domainPM + "mi/decorate/company/detail.json";
    /**
     * 多文件上传 post
     */
    public static String fileSend = domainPM + "upload/images.json";
    /**
     * 个人信息 get
     */
    public static String information = domainPM + "mc/current.json";
    /**
     * 区域树形 get
     */
    public static String areaTree = domainPM + "mc/region/tree.json";
    /**
     * 客户资料修改 put
     */
    public static String modify = domainPM + "mc/modify.json";
    /**
     * 某单元下得房屋 get
     */
    public static String houseUnit = domainPM + "mc/house/unit.json";

//    /**
//     * 血压 get
//     */
//    public static String pressureBlood = domainHM + "mc/pressure/blood.json";
    /**
     * 关联电话 get
     */
    public static String housePhone = domainPM + "mc/house/phone.json";
    /**
     * 绑定房屋 get
     */
    public static String bindPhone = domainPM + "mc/bind/phone.json";
    /**
     * 血压 get
     */
    public static String pressureBlood = domainHM + "/mc/pressure/history/record.json";
    /**
     * 心率 get
     */
    public static String pressureHeartrate = domainHM + "mc/pressure/heartrate.json";
    /**
     * 血糖页面和列表 get
     */
    public static String sugarList = domainHM + "mc/sugar/list.json";
    /**
     * 体重比 get
     */
    public static String bmiList = domainHM + "mc/bmi/list.json";
    /**
     * 用户健康状况 get
     */
    public static String customerDetail = domainHM + "mc/customer/detail.json";
    /**
     * 血压历史数据 get
     */
    public static String pressureRecord = domainHM + "mc/pressure/history/record.json";
    /**
     * 血脂 和 列表 get
     */
    public static String bloodFat = domainHM + "mc/blood/fat.json";
    /**
     * 意见反馈 post
     */
    public static String feedBack = domainPM + "mi/feedback/add.json";
    /**
     * 文章详情 get
     */
    public static String articleDetailView = domainPM + "mi/article/detailView.htm";
    /**
     * app更新 get
     */
    public static String appUpdate = domainPM + "app/versionUpdate.json";
    /**
     * 商户类型列表 get
     */
//    public static String merchantType = domainPM + "mi/merchant/type/listdata.json?fields=all,detailId";
    public static String merchantType = domainPM + "mi/merchant/type/listdata.json";
    /**
     * 商户列表 get
     */
    public static String merchant = domainPM + "mi/merchant/listpage.json";

//    /**
//     *  查询城市 get
//     */
//    public static String findCityCode = domainPM + "mi/cityCode/findCityCode.json";
    /**
     * 活动get
     */
    public static String article = domainPM + "mi/article/active/listpage.json";


    ///mc/customer/avatar 单文件上传已经改成了这个接口
    /**
     * 查询默认城市列表 get
     */
    public static String findCityList = domainPM + "mi/cityCode/find.json";
    /**
     * 商户详情 get
     */
    public static String merchantDetail = domainPM + "mi/merchant/detail.json";
    //    是否登录share
    public static String ISORNOLOGIN = "isornologin";
    public static String SIORNOLOGIN = "siornologin";
    //    保存账号
    public static String IS_SAVE_ACCOUNT = "issaveaccount";
    public static String SI_SAVE_ACCOUNT = "sisaveaccount";
    //    保存登录信息
    public static String IS_SAVE_LOGIN = "issavelogin";
    public static String SISAVELOGIN = "sisavelogin";
    //    选定的房产
    public static String ISSELECTHOSE = "isselecthose";
    public static String SISELECTHOSE = "siselecthose";
    //用户头像连接
    public static String ISIMAGEHEAD = "isImagehead";
    public static String SIIMAGEHEAD = "siImagehead";
    //用户昵称
    public static String ISUSERNAME = "isuserName";
    public static String SIUSERNAME = "siuserName";
    //用户性别
    public static String ISUSERSEX = "isuserSex";
    public static String SIUSERSEX = "siuserSex";
    //用户生日
    public static String ISUSEROLD = "isuserOld";
    public static String SIUSEROLD = "siuserOld";
    //用户手机号
    public static String ISUSERPHONE = "isuserPhone";
    public static String SIUSERPHONE = "siuserPhone";
    //保存cookie
    public static String ISCOOKIE = "iscookie";
    public static String SICOOKIE = "sicookie";


    //认证进入方式
    public static String ISRESIDENT = "isresident";
    public static String SIRESIDENT = "siresident";

    //站点id
    public static int siteID = 20;
    public static String getArticle = domainPM + "mi/article/detailView.htm";
}
