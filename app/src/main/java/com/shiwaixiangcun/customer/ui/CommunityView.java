package com.shiwaixiangcun.customer.ui;

import com.shiwaixiangcun.customer.model.AnnouncementBean;
import com.shiwaixiangcun.customer.response.PageBean;
import com.shiwaixiangcun.customer.response.ResponseEntity;

/**
 * Created by Administrator on 2017/5/25.
 */

public interface CommunityView  {

    /**
     * 设置过后的回调
     * @param result
     */
    void setBgaAdpaterAndClickResult(ResponseEntity<PageBean<AnnouncementBean>> result);
}
