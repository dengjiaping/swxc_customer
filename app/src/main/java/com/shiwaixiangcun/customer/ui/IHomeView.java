package com.shiwaixiangcun.customer.ui;

import com.shiwaixiangcun.customer.model.InformationBean;
import com.shiwaixiangcun.customer.model.NoticeBean;
import com.shiwaixiangcun.customer.model.PageBean;
import com.shiwaixiangcun.customer.model.ResponseEntity;
import com.shiwaixiangcun.customer.model.WeatherDataBean;

/**
 * Created by fyj on 2017/05/22.
 */
public interface IHomeView {


    /**
     *首页第一个Banner
     * @param result
     */
    void setBannerFirst(String result);


    /**
     * 首页第二个Banner
     * @param request
     */
    void setBannerSecond(String request);

    /**
     * 公告
     *
     * @param result
     */
    void setAnnouncementResult(ResponseEntity<PageBean<NoticeBean>> result);

    /**
     * 公告
     *
     * @param result
     */
    void setHeadlineResult(ResponseEntity<PageBean<NoticeBean>> result);


    void setInformationResult(InformationBean result);

    void setHomeWeatherClick(WeatherDataBean result);
}
package com.shiwaixiangcun.customer.ui;

import com.shiwaixiangcun.customer.model.AnnouncementBean;
import com.shiwaixiangcun.customer.model.InformationBean;
import com.shiwaixiangcun.customer.model.WeatherDataBean;
import com.shiwaixiangcun.customer.response.PageBean;
import com.shiwaixiangcun.customer.response.ResponseEntity;

/**
 * Created by fyj on 2017/05/22.
 */
public interface IHomeView {


    /**
     * 设置过后的回调
     * @param result
     */
    void setBgaAdpaterAndClickResult(String result);


    /**
     * 公告
     * @param result
     */
    void setAnnouncementResult(ResponseEntity<PageBean<AnnouncementBean>> result);

    /**
     * 公告
     * @param result
     */
    void setHeadlineResult(ResponseEntity<PageBean<AnnouncementBean>> result);


    void setInformationResult(InformationBean result);

    void setHomeWeatherClick(WeatherDataBean result);
}
