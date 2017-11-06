package com.shiwaixiangcun.customer.ui;

import com.shiwaixiangcun.customer.model.LogoutBean;
import com.shiwaixiangcun.customer.model.User;
import com.shiwaixiangcun.customer.model.UserInfoBean;

/**
 * Created by Administrator on 2017/5/25.
 */

public interface IHouseInformationView {
    /**
     * 设置过后的回调
     * @param result
     */
    void setBgaAdpaterAndClickResult(UserInfoBean result);

    void setHeadImage(User result);

    void setSex(User result);

    void setData(User result);

    void setLogout(LogoutBean user);


}
