package com.shiwaixiangcun.customer.model;

import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.app.App;
import com.shiwaixiangcun.customer.utils.AppSharePreferenceMgr;

/**
 * @author Administrator
 * @date 2017/11/9
 */

public class Token {
    private static String strToken = (String) AppSharePreferenceMgr.get(App.getContext(), GlobalConfig.TOKEN, "");

    private static String strRefreshToken = (String) AppSharePreferenceMgr.get(App.getContext(), GlobalConfig.Refresh_token, "");

    public static String token() {
        return strToken;
    }

    public static String refreshToken() {
        return strRefreshToken;
    }
}
