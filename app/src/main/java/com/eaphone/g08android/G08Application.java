package com.eaphone.g08android;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.eaphone.g08android.entity.DaoMaster;
import com.eaphone.g08android.entity.DaoSession;
import com.eaphone.g08android.http.APIUrl.IApi;
import com.eaphone.g08android.http.ImageLoader;
import com.eaphone.g08android.utils.CrashExceptionUtils;
import com.eaphone.g08android.utils.FetchPatchHandler;
import com.eaphone.g08android.utils.PreferencesUtils;
import com.hyphenate.chatuidemo.DemoHelper;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import cn.jpush.android.api.JPushInterface;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/14 12:54
 * 修改人：Administrator
 * 修改时间：2017/8/14 12:54
 * 修改备注：
 */
public class G08Application extends Application {

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private static G08Application instances;
    private ApplicationLike tinkerApplicationLike;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.TINKER_ENABLE) {

            // 我们可以从这里获得Tinker加载过程的信息
            tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();

            // 初始化TinkerPatch SDK, 更多配置可参照API章节中的,初始化SDK
            TinkerPatch.init(tinkerApplicationLike)
                    .reflectPatchLibrary()
                    .setPatchRollbackOnScreenOff(true)
                    .setPatchRestartOnSrceenOff(true);

            // 每隔3个小时去访问后台时候有更新,通过handler实现轮训的效果
            new FetchPatchHandler().fetchPatchWithInterval(3);
            Log.d("TAG", "Current patch version is " + TinkerPatch.with().getPatchVersion());
            Log.i("TAG", "tinker init");
        }

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        LeakCanary.install(this);
        instances = this;
        PreferencesUtils.init(instances);
        ImageLoader.init(instances);
        setDatabase();
//        FeedbackAPI.init(instances, "24620624", "2602e5ee2c3e4d7e8d960aded9d38c5e");
        if (!IApi.MAIN_URL.equals(IApi.MAIN))
            CrashExceptionUtils.getInstance().init(this);


        // 环信
        huanxin();

        // 第三方分享配置
        UMShareAPI.get(this);
        shareInfo();
    }

    private void shareInfo() {
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
    }

    private void huanxin() {
        //init demo helper
        DemoHelper.getInstance().init(instances);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }

    public static G08Application getInstances() {
        return instances;
    }

    /**
     * 设置greenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "g08", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }


}
