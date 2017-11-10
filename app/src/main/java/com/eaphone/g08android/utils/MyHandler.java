package com.eaphone.g08android.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2016/2/19.
 *
 * Handler 是个很常用也很有用的类，异步，线程安全等等。如果有下面这样的代码，会发生什么呢？handler.postDeslayed ，假设 delay 时间是几个小时… 这意味着什么？
 * 意味着只要 handler 的消息还没有被处理结束，它就一直存活着，包含它的 Activity 就跟着活着。我们来想办法修复它，修复的方案是WeakReference，也就是所谓的弱引用。
 * 垃圾回收器在回收的时候，是会忽视掉弱引用的，所以包含它的 Activity 会被正常清理掉。
 */
public class MyHandler extends Handler {
    private final WeakReference<Activity> mActivity;
    // ...
    public MyHandler(Activity activity) {
        mActivity = new WeakReference<Activity>(activity);
        //...
    }

    @Override
    public void handleMessage(Message msg) {
    }
}
