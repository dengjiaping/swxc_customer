package com.shiwaixiangcun.customer.app;

import android.app.Application;
import android.content.pm.ApplicationInfo;

/**
 * Created by Administrator on 2017/10/26.
 * <p>
 * 正式环境和测试环境管理类
 */

public class ServerConfigManger {

    private ServerConfigManger() {
    }

    public static ServerConfigManger getInstance() {
        return ServerConfigMangerHolder.instance;
    }

    /**
     * 判断是否是正式环境
     *
     * @param pApplication
     * @return
     */
    public boolean isRelease(Application pApplication) {
        ApplicationInfo applicationInfo = pApplication.getApplicationInfo();
        String processName = applicationInfo.processName;
        return !processName.endsWith(".debug");
    }

    private static class ServerConfigMangerHolder {
        private static final ServerConfigManger instance = new ServerConfigManger();
    }

}
