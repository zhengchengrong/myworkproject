package com.hpw.mvpframe.utils;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;


public class UmengUtil {
    //    session的统计
    public static void onResume(Context context) {
        MobclickAgent.onResume(context);
    }

    //    session的统计
    public static void onPause(Context context) {
        MobclickAgent.onPause(context);
    }

    //    当用户使用自有账号登录时
//    public static void onProfileSignIn() {
//        if (!TextUtils.isEmpty(Sp.getSharePreStr(Const.USERID)))
//            MobclickAgent.onProfileSignIn(PreferencesUtils.getSharePreStr(Const.USERID));
//    }

    //    记录进入的页面
    public static void onPageStart(String activityName) {

        MobclickAgent.onPageStart(getName(activityName));
    }

    //    记录onPause的页面
    public static void onPageEnd(String activityName) {
        MobclickAgent.onPageEnd(getName(activityName));
    }

    private static String getName(String activityName) {
        String name = "";
        if (activityName.contains("Activity")) {
            switch (activityName) {
                case "StartActivity":
                    name = "欢迎页";
                    break;
                case "MainActivity":
                    name = "主页";
                    break;
                case "Bind3rdActivity":
                    name = "第三方绑定";
                    break;

                case "Bind3rdSetPwdActivity":
                    name = "第三方绑定设置密码";
                    break;
                case "EsptouchDemoActivity":
                    name = "AIRKISS连接";
                    break;
                case "FamilyActivity":
                    name = "家庭成员";
                    break;

                case "LoginActivity":
                    name = "登录页";
                    break;
                case "RegisteActivity":
                    name = "注册页";
                    break;
                case "RegisterNextActivity":
                    name = "注册第二页";
                    break;
                case "ResetNextActivity":
                    name = "找回密码";
                    break;
                case "FamilyDetailActivity":
                    name = "成员信息";
                    break;
                case "FamilyAddActivity":
                    name = "添加成员";
                    break;
                case "FamilyDataActivity":
                    name = "健康数据";
                    break;
                case "DeviceActivity":
                    name = "我的设备";
                    break;
                case "InfoDetailActivity":
                    name = "资讯详细";
                    break;
                case "InfoCommentActivity":
                    name = "评论";
                    break;
                case "SelfMsgActivity":
                    name = "个人信息";
                    break;

                case "HealthyDetailActivity":
                    name = "健康详情";
                    break;
                case "SetActivity":
                    name = "设置";
                    break;
                case "DeviceAddActivity":
                    name = "添加设备";
                    break;
                case "DeviceConnectActivity":
                    name = "连接设备";
                    break;
                case "DeviceConnectFailedActivity":
                    name = "连接失败";
                    break;
                case "DeviceDetailActivity":
                    name = "设备详细";
                    break;
                case "DeviceBindUserActivity":
                    name = "绑定成员";
                    break;
                case "DeviceAddUserActivity":
                    name = "添加成员";
                    break;
                case "SetResetActivity":
                    name = "修改密码";
                    break;
                case "SetUpdatePhoneActivity":
                    name = "更换手机";
                    break;
                case "MessageActivity":
                    name = "消息列表";
                    break;
                case "MessageInviteActivity":
                    name = "亲人消息";
                    break;
                case "HandInDataActivity":
                    name = "手动输入";
                    break;
                case "WarnActivity":
                    name = "告警";
                    break;
                case "WarnDetailActivity":
                    name = "告警详细";
                    break;
                case "HealthyRecordActivity":
                    name = "测量记录";
                    break;
                case "BindUserNextActivity":
                    name = "绑定成员";
                    break;
                case "JiankangActivity":
                    name = "健康档案";
                    break;
                case "AboutActivity":
                    name = "关于我们";
                    break;
                case "KeFuActivity":
                    name = "客服中心";
                    break;
                case "KeFuDetailActivity":
                    name = "详细内容";
                    break;
                case "QuestionActivity":
                    name = "留言反馈";
                    break;
                case "InfoReplyActivity":
                    name = "资讯回复";
                    break;
                case "MyQuestionActivity":
                    name = "我的反馈";
                    break;
                case "MessageSystemActivity":
                    name = "系统消息";
                    break;
                case "MessageWarnDetailActivity":
                    name = "告警详情";
                    break;
                case "WebActivity":
                    name = "网页";
                    break;

                default:
                    name = activityName;
                    break;
            }
        } else {
            name = activityName;
        }

        return name;
    }

    //通过activity获取label
//    private static String getLabel(String name, Context context) {
//
//        Iterator<Map.Entry<String, String>> it = getActivities(context).entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry<String, String> entry = it.next();
//            Log.e("1234", entry.getKey() + "   " + entry.getValue());
//            if (entry.getKey().equals(name)) {
//                return entry.getValue();
//            }
//        }
//        return name;
//    }

    //获取AndroidManifest中所有的activity和label
//    public static Map<String, String> getActivities(Context ctx) {
//        Map<String, String> result = new HashMap<>();
//        Intent intent = new Intent(Intent.ACTION_MAIN, null);
//        intent.setPackage(ctx.getPackageName());
//        for (ResolveInfo info : ctx.getPackageManager().queryIntentActivities(intent, 0)) {
//            result.put(info.activityInfo.name, info.activityInfo.loadLabel(ctx.getPackageManager()) + "");
//
//        }
//        return result;
//    }
}
