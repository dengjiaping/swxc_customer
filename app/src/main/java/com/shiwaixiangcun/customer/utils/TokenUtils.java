package com.shiwaixiangcun.customer.utils;

import android.app.Activity;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shiwaixiangcun.customer.GlobalConfig;
import com.shiwaixiangcun.customer.http.Common;

/**
 * Author   xujing
 * Desc     TokenUtils  用于获取Token  更新Token
 */

public class TokenUtils {
    private static final String BUG_TAG = "TokenUtils";
    private static String tokenString = SharedPreferenceUtil.getInstance().getString(Common.TOKEN);
    private Activity mActivity;


    public TokenUtils(Activity activity) {
        this.mActivity = activity;

        Log.e(BUG_TAG, toString());
    }


    /**
     * 检查Token是否有效
     *
     * @param token
     */
    public static void checkToken(String token) {
        OkGo.<String>get(GlobalConfig.checkToken)
                .params("access_token", tokenString)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        // TODO: 2017/9/24  token工具类
                    }
                });

    }
}
