package com.eaphone.g08android.utils;

import com.eaphone.g08android.http.APIUrl.IApi;

/**
 * Created by zlq on 2017/7/22.
 */

public class Const {
    public final static String HUAQIAO_APPID = "58057eabb67aec5bc82bc160";
    public static final String TOKEN = "token";
    public static final String USERID = "userId";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String SEX = "sex";
    public static final String BIRTHDAY = "birthday";
    public static final String ICON = "icon";
    public static final String AGE = "age";
    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String CAREER = "career";
    public static final String IDENTITY = "identity";
    public static final String CONTACT_PHONE = "contract_phone";
    public static final String ISFIRST = "ISFIRST";
    public static final String CURRENT_MEMBER = "current_member";
    public static final String CURRENT_MEMBER_NAME = "current_member_NAME";
    public static final String ALIAS_SUCESS = "alias_sucess";

    //测量状态
    public static final String BOOLDSUGAR_EMPTY = "早上空腹";
    public static final String BOOLDSUGAR_ONE = "早上饭后1小时";
    public static final String BOOLDSUGAR_TWO = "早上饭后2小时";
    public static final String BOOLDSUGAR_THREE = "中午饭后1小时";
    public static final String BOOLDSUGAR_FOUR = "中午饭后2小时";
    public static final String BOOLDSUGAR_FIVE = "晚上饭后1小时";
    public static final String BOOLDSUGAR_SIX = "晚上饭后2小时";
    public static final String BLODPRESSURE_ONE = "坐着/左手";
    public static final String BLODPRESSURE_TWO = "坐着/右手";
    public static final String BLODPRESSURE_THREE = "站着/左手";
    public static final String BLODPRESSURE_FOUR = "站着/右手";
    public static final String BLODPRESSURE_FIVE = "躺着/左手";
    public static final String BLODPRESSURE_SIX = "躺着/右手";

    public static final String SOURCE = "source";
    public static final String SOURCE_DEVICE = "device";
    public static final String SOURCE_FAMILY = "family";
    public static final String SOURCE_FAMILY_MEMBER = "family_member";
    public static final String VERSION_CACHE = "version_cache";//保存更新缓存的版本

    public static final String FLOAT_1 = "##0.0";
    public static final String FLOAT_2 = "##0";
    public static final String FLOAT_3 = "##0.00";
    public static final String FLOAT_4 = "###.##";

    public static final String PAGE_SIZE = "10";
    public static final int DELAYMILLIS = 1000;


    public static final String WEIXIN_LOGIN = "WEIXIN_LOGIN";//微信授权登录后获取返回码的action

    public static final String FUNCTION_INTRODUCE = "http://static.xxs120.com/g08/eula/function-introduction.html";
    public static final String ABOUT_STATEMENT = "https://static.xxs120.com/g08/eula/service-statement.html";

    //心率 、 心电图 、血压、 高血压 、低血压、 体温 、 血氧 、血糖
    public final static String HEARTRATE = "17", ELECTROCARDIOGRAM = "24", BLODPRESSURE = "18", BLODPRESSURE_HIGHT = "19", BLODPRESSURE_LOW = "20",
            BODYTEMPERATURE = "23", OXYGENATION = "22", BLOOD_GLUCOSE = "21";

    //拼接头像地址
    public static String getAvater(String userId) {
        return IApi.MAIN_URL + "/passport/v1/user/" + userId + "/avatar";
    }


}
