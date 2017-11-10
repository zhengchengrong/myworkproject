package com.eaphone.g08android.utils;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/9/13 17:19
 * 修改人：Administrator
 * 修改时间：2017/9/13 17:19
 * 修改备注：
 */
public class ErrorCoreUtils {

    public static String dispalyCode(int code){

        String text = "";

        switch (code) {
            /*错误异常代码（负数）*/
            case -1:
                text = "未知异常";
                break;
            case -2:
                text = "参数格式、数值、@valid不合法";
                break;
            case -3:
                text = "数据转化异常";
                break;
            case -4:
                text = "token不合法或者已过期";
                break;
            case -5:
                text = "身份证号格式错误，请重新输入";
                break;
            case -6:
                text = "您没有访问权限";
                break;
          /*设备相关  21-40*/
            case 1:
                text = "用户名或密码错误";
                break;
            case 2:
                text = "手机(用户名)重复";
                break;
            case 3:
                text = "邮箱(用户名)重复";
                break;
            case 4:
                text = "短信发送失败";
                break;
            case 5:
//                text = "用户不存在";
                text = "用户不存在";
                break;
            case 6:
                text = "密码有误";
                break;
            case 7:
                text = "短信验证码不正确，请重新输入";
                break;
            case 8:
                text = "验证码已过期";
                break;
            case 9:
                text = "密码少于6位";
                break;
            case 10:
                text = "没有登录";
                break;
            //家庭成员相关
            case 11:
                text = "家庭成员不存在";
                break;
            case 12:
                text = "家庭邀请不存在";
                break;
            case 13:
                text = "家庭成员重复";
                break;
            case 14:
                text = "不能邀请自己";
                break;
            case 15:
                text = "邀请超过期限";
                break;

             /*设备相关  21-40*/
            case 21:
                text = "设备不存在";
                break;
            case 22:
                text = "设备重复";
                break;
            case 23:
                text = "传感器不存在";
                break;
            case 24:
                text = "传感器重复";
                break;
            case 25:
                text = "数据点不存在";
                break;
            case 26:
                text = "数据点重复";
                break;
            case 27:
                text = "传感器类型不存在";
                break;
            case 28:
                text = "通道不存在";
                break;
            case 29:
                text = "产品不存在";
                break;
            case 30:
                text = "设备不存在厂商白名单中";
                break;
            case 31:
                text = "密码有误";
                break;
            case 32:
                text = "通道已经被绑定";
                break;
            case 33:
                text = "通道还未被绑定";
                break;
            case 34:
                text = "传感器还未被绑定";
                break;
           /* 数据点相关（41-60） */
            case 41:
                text = "统计数据不存在";
                break;
     /* 告警消息相关（61-80）  */
            case 61:
                text = "告警消息不存在";
                break;
            case 62:
                text = "统计类型不存在";
                break;
     /* 增值服务相关（81-100）   */
            case 81:
                text = "增值服务内容不存在";
                break;
            case 82:
                text = "增值服务消息不存在";
                break;
        /* 建议相关（101-120）    */
            case 101:
                text = "建议不存在";
                break;
       /* 121; 媒体资源不存在   */
            case 121:
                text = "媒体资源不存在";
                break;
   /* 161; 患者没有绑定诊疗卡   */
            case 161:
                text = "患者没有绑定诊疗卡";
                break;

            default:
                text = "网络异常，请检查网络。";
                break;
        }
        return text;
    }

}
