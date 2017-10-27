package com.shiwaixiangcun.customer.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.ContextSession;
import com.shiwaixiangcun.customer.R;
import com.shiwaixiangcun.customer.model.ToolCategoryBean;
import com.shiwaixiangcun.customer.ui.activity.ChunyuDoctorActivity;
import com.shiwaixiangcun.customer.ui.activity.HouseRentingActivity;
import com.shiwaixiangcun.customer.ui.activity.LoginActivity;
import com.shiwaixiangcun.customer.ui.activity.LookDecoratingActivity;
import com.shiwaixiangcun.customer.ui.activity.MerchantActivity;
import com.shiwaixiangcun.customer.ui.activity.NotOpenActivity;
import com.shiwaixiangcun.customer.ui.activity.OnlineServiceActivity;
import com.shiwaixiangcun.customer.ui.activity.ProtectRightActivity;
import com.shiwaixiangcun.customer.ui.activity.RecipeActivity;
import com.shiwaixiangcun.customer.ui.activity.RescueWayActivity;
import com.shiwaixiangcun.customer.ui.activity.ResidentCertificationActivity;
import com.shiwaixiangcun.customer.ui.activity.TelephoneActivity;
import com.shiwaixiangcun.customer.ui.activity.heath.HealthEvaluationActivity;
import com.shiwaixiangcun.customer.ui.activity.heath.PhysicalActivity;
import com.shiwaixiangcun.customer.ui.activity.heath.WebActivity;

/**
 * Created by Administrator on 2017/10/19.
 * <p>
 * 主页gridView 跳转工具
 */
public class GridUtils {
    /**
     * 页面跳转
     *
     * @param context context
     * @param bean    数据
     */

    public static void readyGo(Context context, ToolCategoryBean.ChildrenBeanX.ChildrenBean bean) {
        String isLogin = SharePreference.getStringSpParams(context, Common.ISORNOLOGIN, Common.SIORNOLOGIN);
        Bundle bundle = new Bundle();

        //先判断是H5页面还是原生页面
        //再判断是否需要登录
        if (bean.getAppCategoryStatus().endsWith("H5")) {
            bundle.putInt("type", 12);
            bundle.putString("title", bean.getName());
            bundle.putString("link", bean.getLink());
            bundle.putBoolean("authorization", bean.isAuthorization());
            readyGo(context, WebActivity.class, bundle);
        } else {
            switch (bean.getSign()) {


                //食谱
                case "HYPERLIPIDEMIA_RECIPE":
                    bundle.putInt("current", 2);
                    readyGo(context, RecipeActivity.class, bundle);
                    break;
                case "HYPERTENSION_RECIPE":
                    bundle.putInt("current", 0);
                    readyGo(context, RecipeActivity.class, bundle);
                    break;
                case "DIABETES_RECIPE":
                    bundle.putInt("current", 1);
                    readyGo(context, RecipeActivity.class, bundle);
                    break;

                //健康数据
                case "BODY_SIGN_DATA":
                    if (Utils.isNotEmpty(isLogin)) {
                        readyGo(context, PhysicalActivity.class);
                    } else {
                        readyGo(context, LoginActivity.class);
                    }
                    break;
                //健康测评
                case "HEALTH_EVALUATION":
                    readyGo(context, HealthEvaluationActivity.class);
                    break;

                //房屋租售
                case "HOUSE_RENTAL":
                    if (Utils.isNotEmpty(isLogin)) {
                        readyGo(context, HouseRentingActivity.class);
                    } else {
                        readyGo(context, LoginActivity.class);
                    }
                    break;
                //在线报修
                case "ONLINE_REPAIR":
                    if (Utils.isNotEmpty(isLogin)) {
                        if (!ContextSession.isPropertyAuth()) {
                            readyGo(context, ResidentCertificationActivity.class);
                        } else {
                            readyGo(context, OnlineServiceActivity.class);
                        }
                    } else {
                        readyGo(context, LoginActivity.class);
                    }
                    break;

                //餐饮美食
                case "FOOD_AND_BEVERAGE":
                    bundle.putString("sign", "FOOD_AND_BEVERAGE");
                    readyGo(context, MerchantActivity.class, bundle);
                    break;
                //酒店住宿
                case "HOTELS_AND_LODGING":
                    bundle.putString("sign", "HOTELS_AND_LODGING");
                    readyGo(context, MerchantActivity.class, bundle);
                    break;
                //超市
                case "SUPERMARKET":
                    bundle.putString("sign", "SUPERMARKET");
                    readyGo(context, MerchantActivity.class, bundle);
                    break;
                //医院
                case "HOSPITAL":
                    bundle.putString("sign", "HOSPITAL");
                    readyGo(context, MerchantActivity.class, bundle);
                    break;
                //找装修
                case "FIND_DECORATION":
                    readyGo(context, LookDecoratingActivity.class);
                    break;
                //消费维权
                case "SAFEGUARD_RIGHTS":
                    readyGo(context, ProtectRightActivity.class);
                    break;
                //健康管理
                case "HEALTH_MANAGEMENT":
                    break;
                //专家预约
                case "EXPERT_APPOINTMENT":
                    break;
                //全国专家库
                case "EXPERT_LIBRARY":
                    bundle.putString("name", "全国专家库");
                    bundle.putInt("image", R.drawable.expert);
                    bundle.putString("message", "全国专家库正在建设中...");
                    readyGo(context, NotOpenActivity.class, bundle);
                    break;
                //养生食谱
                case "HEALTH_RECIPES":
                    bundle.putInt("current", 0);
                    readyGo(context, RecipeActivity.class);
                    break;
                //应急救助
                case "EMERGENCY_RESCUE":

                    break;

                case "VIDEO_ESCORT":
                    bundle.putString("name", "视频陪护");
                    bundle.putInt("image", R.drawable.video);
                    bundle.putString("message", "你所在的社区暂未开通视频陪护功能");
                    readyGo(context, NotOpenActivity.class, bundle);
                    break;
                case "FAMILY_FARM":
                    bundle.putString("name", "家庭农场");
                    bundle.putInt("image", R.drawable.family_farm);
                    bundle.putString("message", "你所在的社区暂未开通家庭农场功能");
                    readyGo(context, NotOpenActivity.class, bundle);
                    break;
                case "FAMILY_EVENT_RECORD":
                    bundle.putString("name", "家庭大事件");
                    bundle.putInt("image", R.drawable.family_event);
                    bundle.putString("message", "你所在的社区暂未开通家庭大事件功能");
                    readyGo(context, NotOpenActivity.class, bundle);
                    break;
                case "RESCUE_PHONE":
                    readyGo(context, TelephoneActivity.class);
                    break;
                case "RESCUE_WAY":
                    readyGo(context, RescueWayActivity.class);
                    break;
                case "RESCUE_RECORD":
                    bundle.putString("name", "救助记录");
                    bundle.putInt("image", R.drawable.rescue_record);
                    bundle.putString("message", "还没有救助记录");
                    readyGo(context, NotOpenActivity.class, bundle);
                    break;
                case "BLOOD_PRESSURE_METER":
                    bundle.putString("name", "智能血压仪");
                    bundle.putInt("image", R.drawable.intelligent_blood_presure);
                    bundle.putString("message", "智能血压仪正在筹建中，敬请期待");
                    readyGo(context, NotOpenActivity.class, bundle);

                    break;
                case "BLOOD_GLUCOSE_METER":
                    bundle.putString("name", "智能血糖仪");
                    bundle.putInt("image", R.drawable.intelligent_blood);
                    bundle.putString("message", "智能血糖仪正在筹建中，敬请期待");
                    readyGo(context, NotOpenActivity.class, bundle);

                    break;
                case "SMART_WATCH":
                    bundle.putString("name", "智能手表");
                    bundle.putInt("image", R.drawable.intelligent_watch);
                    bundle.putString("message", "智能手表正在筹建中，敬请期待");
                    readyGo(context, NotOpenActivity.class, bundle);

                    break;

                case "ELECTRICITY_FEE":
                    bundle.putString("name", "电费");
                    bundle.putInt("image", R.drawable.electricity_frees);
                    bundle.putString("message", "您所在社区暂未开通缴存电费功能");
                    readyGo(context, NotOpenActivity.class, bundle);
                    break;

                case "GAS_COSTS":
                    bundle.putString("name", "燃气费");
                    bundle.putInt("image", R.drawable.gas_fee);
                    bundle.putString("message", "您所在社区暂未开通缴存燃气费功能");
                    readyGo(context, NotOpenActivity.class, bundle);

                    break;

                case "WATER_FEE":
                    bundle.putString("name", "水费");
                    bundle.putInt("image", R.drawable.water_cahrge);
                    bundle.putString("message", "您所在社区暂未开通缴存水费功能");
                    readyGo(context, NotOpenActivity.class, bundle);

                    break;

                case "PROPERTY_COSTS":
                    bundle.putString("name", "物业费");
                    bundle.putInt("image", R.drawable.property_fee);
                    bundle.putString("message", "您所在社区暂未开通缴存物业费功能");
                    readyGo(context, NotOpenActivity.class, bundle);
                    break;

                case "MOBILE_PHONE_COSTS":
                    bundle.putString("name", "话费");
                    bundle.putInt("image", R.drawable.telephone_fare);
                    bundle.putString("message", "您所在社区暂未开通缴存话费功能");
                    readyGo(context, NotOpenActivity.class, bundle);

                    break;

                case "FUEL_COSTS":
                    bundle.putString("name", "加油卡");
                    bundle.putInt("image", R.drawable.fuel_card);
                    bundle.putString("message", "您所在社区暂未开通加油卡缴费功能");
                    readyGo(context, NotOpenActivity.class, bundle);

                    break;

                case "COMMERCIAL_SERVICE":
                    break;
                //周边生活
                case "VICINITY_LIFE":

                    break;
                //铂金管家
                case "HOUSEKEEPER":
                    bundle.putString("name", "铂金管家");
                    bundle.putInt("image", R.drawable.house_keeper);
                    bundle.putString("message", "你所在的社区没有开通铂金管家服务");
                    readyGo(context, NotOpenActivity.class, bundle);
                    break;

                //门禁钥匙
                case "ACCESS_KEY":
                    bundle.putString("name", "门禁钥匙");
                    bundle.putInt("image", R.drawable.access_key);
                    bundle.putString("message", "你所在的社区暂未开通门禁管家");
                    readyGo(context, NotOpenActivity.class, bundle);
                    break;
                //文化生活
                case "CULTURAL_LIFE":
                    break;

                //文化课堂
                case "COMMUNITY_CLASSROOM":
                    bundle.putString("name", "世外课堂");
                    bundle.putInt("image", R.drawable.classroom);
                    bundle.putString("message", "你所在的社区暂未开通世外课堂功能");
                    readyGo(context, NotOpenActivity.class, bundle);

                    break;


                //春雨医生
                case "ORDINARY_TREAT":
                    readyGo(context, ChunyuDoctorActivity.class, bundle);
                    break;


                //兴趣社团
                case "INTERESTED_COMMUNITY":
                    bundle.putString("name", "兴趣社团");
                    bundle.putInt("image", R.drawable.association);
                    bundle.putString("message", "你所在的社区暂未开通兴趣社团功能");
                    readyGo(context, NotOpenActivity.class, bundle);
                    break;

                //活动报名
                case "ACTIVE_REGISTRATION":
                    bundle.putString("name", "活动报名");
                    bundle.putInt("image", R.drawable.enroll);
                    bundle.putString("message", "你所在的社区暂未开通活动报名功能");
                    readyGo(context, NotOpenActivity.class, bundle);
                    break;

                case "ONLINE_PAYMENT":
                    break;
                //情亲互动
                case "LOVE_INTERACTION":
                    break;
                //保险理财
                case "INSURANCE_MANAGEMENT":
                    break;
                //智能关爱
                case "INTELLIGENT_CARE":
                    break;
            }
        }


    }

    private static void readyGo(Context context, Class<?> clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);

    }

    protected static void readyGo(Context context, Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(context, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}


