package com.eaphone.g08android.utils;

import android.text.TextUtils;

/**
 * Created by zlq on 2017/9/9.
 */

public class LoginUtil {

    /**
     * 判断用户是否登陆
     *
     * @return
     */
    public static boolean isLogin() {
        if (TextUtils.isEmpty(PreferencesUtils.getSharePreStr(Const.TOKEN))) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 查看选中了家庭成员的ID
     *
     * @return
     */
    public static String isFamilyMember() {
        if (TextUtils.isEmpty(PreferencesUtils.getSharePreStr(Const.CURRENT_MEMBER))) {
            return PreferencesUtils.getSharePreStr(Const.USERID);
        } else {
            return PreferencesUtils.getSharePreStr(Const.CURRENT_MEMBER);
        }
    }

    /**
     * 查看选中了家庭成员的NAME
     *
     * @return
     */
    public static String isFamilyName() {
        if (TextUtils.isEmpty(PreferencesUtils.getSharePreStr(Const.CURRENT_MEMBER_NAME))) {
            return "我的";
        } else {
            return PreferencesUtils.getSharePreStr(Const.CURRENT_MEMBER_NAME);
        }
    }

}
