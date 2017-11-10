package com.eaphone.g08android.utils;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesUtils {
    /**
     * 普通字段存放地址
     */
    private static final String ZUODIAN = "yifeng_zuodian";
    private static Context contextInstance;

    public static void init(Context context) {
        contextInstance = context;
    }

    public static String getSharePreStr(String field) {
        SharedPreferences sp = (SharedPreferences) contextInstance.getSharedPreferences(ZUODIAN, Activity.MODE_PRIVATE);
        String s = sp.getString(field, "");//如果该字段没对应值，则取出字符串0
        return s;
    }


    //取出whichSp中field字段对应的int类型的值
    public static int getSharePreInt(String field) {
        SharedPreferences sp = (SharedPreferences) contextInstance.getSharedPreferences(ZUODIAN, Activity.MODE_PRIVATE);
        int i = sp.getInt(field, 0);//如果该字段没对应值，则取出0
        return i;
    }

    public static long getSharePrelong(String field) {
        SharedPreferences sp = (SharedPreferences) contextInstance.getSharedPreferences(ZUODIAN, Activity.MODE_PRIVATE);
        long i = sp.getLong(field, System.currentTimeMillis());//如果该字段没对应值，则取出0
        return i;
    }

    //取出whichSp中field字段对应的boolean类型的值
    public static boolean getSharePreBoolean(String field) {
        SharedPreferences sp = (SharedPreferences) contextInstance.getSharedPreferences(ZUODIAN, Activity.MODE_PRIVATE);
        boolean i = sp.getBoolean(field, false);//如果该字段没对应值，则取出0
        return i;
    }

    //保存string类型的value到whichSp中的field字段
    public static void putSharePre(String field, String value) {
        SharedPreferences sp = (SharedPreferences) contextInstance.getSharedPreferences(ZUODIAN, Activity.MODE_PRIVATE);
        sp.edit().putString(field, value).commit();
    }

    //保存long类型的value到whichSp中的field字段
    public static void putSharePre(String field, long value) {
        SharedPreferences sp = (SharedPreferences) contextInstance.getSharedPreferences(ZUODIAN, Activity.MODE_PRIVATE);
        sp.edit().putLong(field, value).commit();
    }

    //保存int类型的value到whichSp中的field字段
    public static void putSharePre(String field, int value) {
        SharedPreferences sp = (SharedPreferences) contextInstance.getSharedPreferences(ZUODIAN, Activity.MODE_PRIVATE);
        sp.edit().putInt(field, value).commit();
    }

    //保存boolean类型的value到whichSp中的field字段
    public static void putSharePre(String field, Boolean value) {
        SharedPreferences sp = (SharedPreferences) contextInstance.getSharedPreferences(ZUODIAN, Activity.MODE_PRIVATE);
        sp.edit().putBoolean(field, value).commit();
    }

    //清空保存的数据
    public static void clearSharePre() {
        try {
            SharedPreferences sp = contextInstance.getSharedPreferences(ZUODIAN, Activity.MODE_PRIVATE);
            sp.edit().clear().commit();
        } catch (Exception e) {
        }
    }

    public static void removeSharePreStr(String field) {
        SharedPreferences sp = (SharedPreferences) contextInstance.getSharedPreferences(ZUODIAN, Activity.MODE_PRIVATE);
        sp.edit().remove(field).commit();
    }

    public static void removeSharePreLong(String field) {
        SharedPreferences sp = (SharedPreferences) contextInstance.getSharedPreferences(ZUODIAN, Activity.MODE_PRIVATE);
        sp.edit().remove(field).commit();
    }
}
