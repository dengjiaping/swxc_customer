package com.shiwaixiangcun.customer.presenter;


import android.content.Context;

/**
 * Created by fyj on 2017/05/22.
 */
public interface IHomePresenter {


    void setBannerFirst(Context context);

    void setBannerSecond(Context context);
//    void setBgaAdapterAndClick(Context context);

    void setAnnouncement(Context context);

    void setHeadline(Context context);

    void setInformation(Context context);

    void setWeatherHomeClick(Context context,String cityCode);
}
package com.shiwaixiangcun.customer.presenter;


import android.content.Context;

/**
 * Created by fyj on 2017/05/22.
 */
public interface IHomePresenter {


    void setBgaAdpaterAndClick(Context context);

    void setAnnouncement(Context context);

    void setHeadline(Context context);

    void setInformation(Context context);

    void setWeatherHomeClick(Context context,String cityCode);
}
