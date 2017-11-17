package com.eaphone.g08android.http.APIUrl;


/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/6/20 16:28
 * 修改人：Administrator
 * 修改时间：2017/6/20 16:28
 * 修改备注：
 */
public class IApi {

    public static final String TEST = "http://192.168.1.238:8048/";
    public static final String MAIN = "https://api.xinxiangsui.com.cn";
    public static final String MAIN_URL = TEST;

    //预约挂号
    public static final String URL_APPOINMENT = "http://open.eaphonetech.com/doctor-web/v1/bookingRegister/hospital.htm?operatorId=EphoneWY&clientUserId=";
    //网络问诊
    public static final String URL_INQUIRY = "http://open.eaphonetech.com/doctor-web/v1/front/Interrogation/getNetDoctors.htm?name=%E7%BD%91%E7%BB%9C%E9%97%AE%E8%AF%8A&type=wangluo&operatorId=EphoneWY&from=app&clientUserId=";
    //挂号记录
    public static final String URL_REGISTION_RECORD = "http://open.eaphonetech.com/doctor-web/v1/bookingRegister/registerList.htm?operatorId=EphoneWY&clientUserId=";
    //我的预约
    public static final String URL_MY_INQUIRY = "http://open.eaphonetech.com/doctor-web/v1/front/Interrogation/askRecord.htm?name=%E7%BD%91%E7%BB%9C%E9%97%AE%E8%AF%8A&sid=0&operatorId=EphoneWY&from=app&clientUserId=";
    //我的诊疗卡
    public static final String URL_MEDICAL_CARD = "http://open.eaphonetech.com/doctor-web/v1/front/card/myCard.htm?operatorId=EphoneWY&clientUserId=";
    //我的订单
    public static final String URL_MY_ORDER = "http://open.eaphonetech.com/doctor-web/v1/front/h5/myOrder.htm?operatorId=EphoneWY&clientUserId=";

    public static String getOpenPlatform(String url, String userId) {
        return url + userId;
    }

}
