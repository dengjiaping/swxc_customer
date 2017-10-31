package com.shiwaixiangcun.customer;

import com.shiwaixiangcun.customer.app.App;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;

/**
 *
 * @author Administrator
 * @date 2017/10/11
 */

public class ContextSession {


    /**
     * 获取Token
     *
     * @return
     */
    public static String getTokenString() {
        try {
            String token = (String) AppSharePreferenceMgr.get(App.getContext(), GlobalConfig.TOKEN, "");
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * 检测用户是否认证
     *
     * @return
     */
    public static boolean isPropertyAuth() {
        boolean isPropertyAuth = (boolean) AppSharePreferenceMgr.get(App.getContext(), GlobalConfig.propertyAuth, false);
        return isPropertyAuth;
    }

    public static String getRefreshToken() {
        try {
            String token = (String) AppSharePreferenceMgr.get(App.getContext(), GlobalConfig.Refresh_token, "");
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
