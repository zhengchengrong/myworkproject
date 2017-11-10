# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\application_soft\android-sdk\android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#友盟统计
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}
-keep public class [yifeng.com.huaqiao_user].R$*{
public static final int *;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class android.net.http.SslError
-keep class android.webkit.**{*;}
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class m.framework.**{*;}
-keep class **.R{*;}
-dontwarn cn.sharesdk.**
-dontwarn **.R$*
#忽略警告
#-dontwarn
-ignorewarnings


-optimizationpasses 5          # 指定代码的压缩级别
-dontusemixedcaseclassnames   # 是否使用大小写混合
-dontpreverify           # 混淆时是否做预校验
-verbose                # 混淆时是否记录日志
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 混淆时所采用的算法

-keepattributes *Annotation*
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keep public class * extends android.app.Activity      # 保持哪些类不被混淆
-keep public class * extends android.app.Application   # 保持哪些类不被混淆
-keep public class * extends android.app.Service       # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider    # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference        # 保持哪些类不被混淆
-keep public class com.android.vending.licensing.ILicensingService    # 保持哪些类不被混淆

#-keepclasseswithmembernames class DoorDetailActivity {  # 保持 native 方法不被混淆
#   native <methods>;
#}
 -keep class **.R$* { *; }
-keepclasseswithmembers class * {   # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {# 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity { # 保持自定义控件类不被混淆
    public void *(android.view.View);
}
-keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆
    public static final android.os.Parcelable$Creator *;
}
-keepattributes *JavascriptInterface*
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
public *;
}
-keepclassmembers class cn.xx.xx.Activity$AppAndroid {
public *;
}


-keep class org.achartengine.** { *; }
-keep class test.unit.** { *; }
-keep class com.** { *; }
-keep class org.json.alipay.** { *; }
-keep class bolts.** { *; }
-keep class android.backport.webp.** { *; }
-keep class org.xutils.** { *; }
-keep class com.github.mikephil.charting.** { *; }
-keep class com.parse.** { *; }
-keep class com.taidoc.pclinklibrary.** { *; }
-keep class tw.com.prolific.driver.pl2303.** { *; }
-keep class com.robotium.solo.** { *; }
-keep class com.umeng.analytics.** { *; }
-keep class u.aly.** { *; }
-keep class com.** { *; }
-keep class u.upd.** { *; }
-keep class com.nostra13.universalimageloader.** { *; }
-keep class com.google.zxing.** { *; }
-keep class assets.** { *; }
-keep class com.baidu.** { *; }
-keep class vi.com.gdi.bgl.android.java.** { *; }
-keep class com.bumptech.glide.** { *; }
-keep class com.google.android.gms.** { *; }
-keep class com.hyphenate.** { *; }
-dontwarn  com.hyphenate.**
-keep class internal.org.apache.http.entity.mime.** { *; }
-keep class android.net.** { *; }
-keep class com.android.internal.http.multipart.** { *; }
-keep class org.apache.** { *; }
-keep class com.tencent.mm.** { *; }
-keep class butterknife.** { *; }
-keep class com.alibaba.fastjson.** { *; }
-keep class com.synwing.android.** { *; }
-keep class no.nordicsemi.android.dfu.** { *; }


#支付宝
#-libraryjars libs/alipaySDK-20160516.jar
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}



#3D 地图
    -keep   class com.amap.api.mapcore.**{*;}
    -keep   class com.amap.api.maps.**{*;}
    -keep   class com.autonavi.amap.mapcore.*{*;}
#    定位
    -keep  class com.amap.api.location.**{*;}
    -keep   class com.aps.**{*;}
#    搜索
    -keep   class com.amap.api.services.**{*;}
#    2D地图
    -keep class com.amap.api.maps2d.**{*;}
    -keep class com.amap.api.mapcore2d.**{*;}
#    导航
    -keep class com.amap.api.navi.**{*;}
    -keep class com.autonavi.**{*;}


 #保持 greenDao 的方法不被混淆
 -keepclassmembers class * extends de.greenrobot.dao.AbstractDao { *; }
 -keep class **$Properties { *;}
 -keepattributes *Annotation*
 -keepclassmembers class ** {
 @org.greenrobot.eventbus.Subscribe <methods>;
 }
 -keepattributes Exceptions,InnerClasses,Signature
 -keep class com.synwing.volley.** { *; }
 -keep enum * { *; }
 -keep class com.synwing.android.ble.event.** { *; }
 -keep class no.nordicsemi.android.dfu.internal.manifest.** { *; }
 -keep class com.synwing.products.sensors.synwingecg.SynwingECGSenserFactory{ *;}
 -keep class com.synwing.** extends com.synwing.android.ble.BleManufacturerData{ public static *** parse(byte[]); }
 -keep class com.synwing.android.ecgchart.ECGChart { public protected *; }
 -keep class com.synwing.android.ecgchart.ECGChart$* { public protected *; }
 -keep class com.synwing.ecg.sdk.LiveEcgChart { public protected *; }
 -keep class com.synwing.ecg.sdk.LiveEcgChart$* { public protected *; }
 -keep class com.synwing.products.Device { *; }
 -keep class com.synwing.ecg.sdk.model.** { *; }
 -keep class com.synwing.ecg.sdk.SynwingECGSDK { public *; }
 -keep class com.synwing.ecg.sdk.BeatParams { public *; }
 -keep class com.synwing.ecg.sdk.EcgAnnotation { public *; }
 -keep class com.synwing.ecg.sdk.EcgSignalSegment { public *; }
 -keep class com.synwing.ecg.sdk.HrvData { public *; }
 -keep class com.synwing.ecg.sdk.SynwingEcgSdkService { public *; }
 -keep class com.synwing.ecg.sdk.event.** { *; }
 -keep class com.synwing.ecg.sdk.SynwingDfuService { *; }
 -keep class com.synwing.ecg.sdk.db.entity.** { *; }
 -keep class com.synwing.ecg.sdk.util.CacheManager { *; }
 -keep class com.synwing.ecg.sdk.db.DaoSession { *; }
 -keep class com.synwing.ecg.sdk.db.DBManager { *; }
 -keep class com.synwing.products.sensors.synwingecg.SynwingECGSensor { *; }
 -keep class com.synwing.android.commons.Utils { *; }
 -keep class com.synwing.android.commons.CircularBuffer { *; }
 -keep class com.synwing.ecg.sdk.util.SensorDataServiceUtil { *; }
 -keep class no.nordicsemi.android.dfu.** {*;}
 -keep class com.synwing.commons.codec.binary.** {*;}
 -keepclassmembers class com.synwing.ecg.sdk.config.Urls{
 static final java.lang.String LOGIN_URL;
 }
 -dontshrink

 -dontoptimize
 -dontpreverify

 -dontwarn cn.jpush.**
 -keep class cn.jpush.** { *; }
 -keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }

 -dontwarn cn.jiguang.**
 -keep class cn.jiguang.** { *; }


-keepattributes *Annotation*
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}

# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

# We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-keep class android.support.v4.** {*;}

-keep class org.xmlpull.** {*;}
-keep class com.baidu.** {*;}
-keep public class * extends com.umeng.**
-keep class com.umeng.** { *; }
-keep class com.squareup.picasso.* {*;}


-keep class com.hyphenate.** {*;}
-keep class com.hyphenate.chat.** {*;}
-keep class org.jivesoftware.** {*;}
-keep class org.apache.** {*;}

#另外，demo中发送表情的时候使用到反射，需要keep SmileUtils,注意前面的包名，
#不要SmileUtils复制到自己的项目下keep的时候还是写的demo里的包名
-keep class com.hyphenate.chatuidemo.utils.SmileUtils {*;}

#2.0.9后加入语音通话功能，如需使用此功能的api，加入以下keep
-keep class net.java.sip.** {*;}
-keep class org.webrtc.voiceengine.** {*;}
-keep class org.bitlet.** {*;}
-keep class org.slf4j.** {*;}
-keep class ch.imvs.** {*;}



#2.0.9后加入语音通话功能，如需使用此功能的api，加入以下keep
-keep class net.java.sip.** {*;}
-keep class org.webrtc.voiceengine.** {*;}
-keep class org.bitlet.** {*;}
-keep class org.slf4j.** {*;}
-keep class ch.imvs.** {*;}


#另外，demo中发送表情的时候使用到反射，需要keep SmileUtils,注意前面的包名，
#不要SmileUtils复制到自己的项目下keep的时候还是写的demo里的包名
-keep class com.hyphenate.chatuidemo.utils.SmileUtils {*;}

-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.sdk.app.H5PayCallback {
    <fields>;
    <methods>;
}
-keep class com.alipay.android.phone.mrpc.core.** { *; }
-keep class com.alipay.apmobilesecuritysdk.** { *; }
-keep class com.alipay.mobile.framework.service.annotation.** { *; }
-keep class com.alipay.mobilesecuritysdk.face.** { *; }
-keep class com.alipay.tscenter.biz.rpc.** { *; }
-keep class org.json.alipay.** { *; }
-keep class com.alipay.tscenter.** { *; }
-keep class com.ta.utdid2.** { *;}
-keep class com.ut.device.** { *;}

#友盟分享
-dontshrink
-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-dontwarn com.facebook.**
-keep public class javax.**
-keep public class android.webkit.**
-dontwarn android.support.v4.**
-keep enum com.facebook.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

-keep public interface com.facebook.**
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**

-keep public class com.umeng.socialize.* {*;}


-keep class com.facebook.**
-keep class com.facebook.** { *; }
-keep class com.umeng.scrshot.**
-keep public class com.tencent.** {*;}
-keep class com.umeng.socialize.sensor.**
-keep class com.umeng.socialize.handler.**
-keep class com.umeng.socialize.handler.*
-keep class com.umeng.weixin.handler.**
-keep class com.umeng.weixin.handler.*
-keep class com.umeng.qq.handler.**
-keep class com.umeng.qq.handler.*
-keep class UMMoreHandler{*;}
-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
-keep class im.yixin.sdk.api.YXMessage {*;}
-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
-keep class com.tencent.mm.sdk.** {
   *;
}
-keep class com.tencent.mm.opensdk.** {
   *;
}
-keep class com.tencent.wxop.** {
   *;
}
-keep class com.tencent.mm.sdk.** {
   *;
}
-dontwarn twitter4j.**
-keep class twitter4j.** { *; }

-keep class com.tencent.** {*;}
-dontwarn com.tencent.**
-keep class com.kakao.** {*;}
-dontwarn com.kakao.**
-keep public class com.umeng.com.umeng.soexample.R$*{
    public static final int *;
}
-keep public class com.linkedin.android.mobilesdk.R$*{
    public static final int *;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}
-keep class com.umeng.socialize.impl.ImageImpl {*;}
-keep class com.sina.** {*;}
-dontwarn com.sina.**
-keep class  com.alipay.share.sdk.** {
   *;
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

-keep class com.linkedin.** { *; }
-keep class com.android.dingtalk.share.ddsharemodule.** { *; }
-keepattributes Signature