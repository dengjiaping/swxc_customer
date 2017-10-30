package com.shiwaixiangcun.customer.event;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2017/9/11.
 */

public class EventCenter {
    private static final EventBus instance = EventBus.getDefault();

    public static final EventBus getInstance() {
        return instance;
    }
}
