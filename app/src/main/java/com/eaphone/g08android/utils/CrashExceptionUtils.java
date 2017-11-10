package com.eaphone.g08android.utils;

import android.content.Context;

import com.hpw.mvpframe.AppManager;
import com.umeng.analytics.MobclickAgent;

/**
 * 项目名称：康馨健康管家
 * 类描述：全局捕获异常
 * 创建人：zlq
 * 创建时间：2016/12/19 10:44
 * 修改人：Administrator
 * 修改时间：2016/12/19 10:44
 * 修改备注：
 */
public class CrashExceptionUtils implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CrashExceptionUtils";
    private static CrashExceptionUtils instance = new CrashExceptionUtils();
    private Context context;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private CrashExceptionUtils() {

    }

    public static CrashExceptionUtils getInstance() {
        return instance;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {

            //退出程序
            AppManager.getAppManager().finishAllActivity();
            System.exit(0);
            MobclickAgent.onKillProcess(context);//友盟，调用Process.kill或者System.exit之类的方法杀死进程
        }
    }

    public void init(Context context) {
        this.context = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 自定义处理异常消息
     *
     * @param ex
     * @return
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        return true;

    }
}
