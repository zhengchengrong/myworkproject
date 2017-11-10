package com.hpw.mvpframe.utils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zlq on 2017/8/26.
 */

public class EventBusUtils {

    public static void regist(Object object) {
        if (!EventBus.getDefault().isRegistered(object)) {
            EventBus.getDefault().register(object);
        }
    }

    public static void unRegist(Object object) {
        if (EventBus.getDefault().isRegistered(object)) {
            EventBus.getDefault().unregister(object);
        }
    }

    public static void sendEvent(Object object) {
        EventBus.getDefault().post(object);
    }

    public static void sentSticky(Object object) {
        EventBus.getDefault().postSticky(object);
    }
}
