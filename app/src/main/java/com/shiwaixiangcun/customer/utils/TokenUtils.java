package com.shiwaixiangcun.customer.utils;

import android.app.Activity;

import com.lzy.okgo.OkGo;
import com.shiwaixiangcun.customer.GlobalConfig;

/**
 * Created by Administrator on 2017/9/24.
 * <p>
 * TokenUtils
 * 用于获取Token  更新Token
 */

public class TokenUtils {
    Activity mActivity;

    public TokenUtils(Activity activity) {
        mActivity = activity;
    }

    public void checkToken(String token) {
        OkGo.<String>get(GlobalConfig.checkToken)
                .params("access_token", "4eda1000a808182c5459c9eca2e32637");

    }
}
