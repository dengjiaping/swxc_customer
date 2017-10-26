package com.shiwaixiangcun.customer.app

import android.app.Application
import android.content.pm.ApplicationInfo

/**
 * Created by Administrator on 2017/10/26.
 *
 *
 * 正式环境和测试环境管理类
 */

class ServerConfigManger private constructor() {

    private object ServerConfigMangerHolder {
        private val instance = ServerConfigManger()
    }

    fun isRelease(pApplication: Application): Boolean {
        val applicationInfo = pApplication.applicationInfo
        val processName = applicationInfo.processName
        return if (processName.endsWith(".debug")) {
            false
        } else {
            true
        }
    }

    companion object {

        val instance: ServerConfigManger
            get() = ServerConfigMangerHolder.instance
    }

}
