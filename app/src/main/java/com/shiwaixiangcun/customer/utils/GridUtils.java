package com.shiwaixiangcun.customer.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.shiwaixiangcun.customer.Common;
import com.shiwaixiangcun.customer.ContextSession;
import com.shiwaixiangcun.customer.model.ToolCategoryBean;
import com.shiwaixiangcun.customer.ui.activity.HouseRentingActivity;
import com.shiwaixiangcun.customer.ui.activity.LoginActivity;
import com.shiwaixiangcun.customer.ui.activity.LookDecoratingActivity;
import com.shiwaixiangcun.customer.ui.activity.OnlineServiceActivity;
import com.shiwaixiangcun.customer.ui.activity.ResidentCertificationActivity;
import com.shiwaixiangcun.customer.ui.activity.heath.HealthEvaluationActivity;
import com.shiwaixiangcun.customer.ui.activity.heath.PhysicalActivity;

/**
 * Created by Administrator on 2017/10/19.
 */
//                    BODY_SIGN_DATA("健康数据"),
//                    HEALTH_EVALUATION("健康测评"),
//                    HOUSE_RENTAL("房屋租售"),
//                    ONLINE_REPAIR("在线报修"),
//                    FOOD_AND_BEVERAGE("餐饮美食"),
//                    HOTELS_AND_LODGING("酒店住宿"),
//                    SUPERMARKET("超市"),
//                    HOSPITAL("医院"),
//                    FIND_DECORATION("找装修"),
//                    SAFEGUARD_RIGHTS("消费维权"),
//
//
//                    HEALTH_MANAGEMENT("健康管理"),
//                    EXPERT_APPOINTMENT("专家预约"),
//                    HEALTH_RECIPES("养生食谱"),
//                    EMERGENCY_RESCUE("应急救助"),
//                    COMMERCIAL_SERVICE("物业服务"),
//                    VICINITY_LIFE("周边生活"),
//                    CULTURAL_LIFE("文化生活"),
//                    ONLINE_PAYMENT("在线缴费"),
//                    LOVE_INTERACTION("情亲互动"),
//                    INSURANCE_MANAGEMENT("保险理财"),
//                    INTELLIGENT_CARE("智能关爱");
public class GridUtils {
    /**
     * 页面跳转
     *
     * @param context context
     * @param bean    数据
     */

    public static void go(Context context, ToolCategoryBean.ChildrenBeanX.ChildrenBean bean) {
        String isOrNotLogin = SharePreference.getStringSpParams(context, Common.ISORNOLOGIN, Common.SIORNOLOGIN);
        if (bean.getSign() == null) {
            return;
        }
        switch (bean.getSign()) {

            //健康数据
            case "BODY_SIGN_DATA":
                if (Utils.isNotEmpty(isOrNotLogin)) {
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
                if (Utils.isNotEmpty(isOrNotLogin)) {
                    readyGo(context, HouseRentingActivity.class);
                } else {
                    readyGo(context, LoginActivity.class);
                }
                break;
            //在线报修
            case "ONLINE_REPAIR":
                if (Utils.isNotEmpty(isOrNotLogin)) {
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
                break;
            //酒店住宿
            case "HOTELS_AND_LODGING":
                break;
            //超市
            case "SUPERMARKET":
                break;
            //医院
            case "HOSPITAL":
                break;
            //找装修
            case "FIND_DECORATION":
                readyGo(context, LookDecoratingActivity.class);
                break;
            //消费维权
            case "SAFEGUARD_RIGHTS":
                break;
            //健康管理
            case "HEALTH_MANAGEMENT":
                break;
            //专家预约
            case "EXPERT_APPOINTMENT":
                break;
            //养生食谱
            case "HEALTH_RECIPES":
                break;
            //应急救助
            case "EMERGENCY_RESCUE":
                break;
            //物业服务
            case "COMMERCIAL_SERVICE":
                break;
            //周边生活
            case "VICINITY_LIFE":
                break;
            //文化生活
            case "CULTURAL_LIFE":
                break;
            //在线缴费
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


