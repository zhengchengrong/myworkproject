package com.eaphone.g08android.utils;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/18 16:25
 * 修改人：Administrator
 * 修改时间：2017/8/18 16:25
 * 修改备注：
 */
public class FormatUtil {

    /**
     * 验证手机号码正则表达式
     */
    public static boolean isMobileNO(String mobiles) {
        boolean isValid = false;
        String expression = "((^(13|14|15|17|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
        CharSequence inputStr = mobiles;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    //判断email格式是否正确
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    //验证身份证号码是否正确
    public static boolean isCard(String cardNO) {
        String reg = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
//        String reg1 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|[Xx])$";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(cardNO);
        return m.matches();
    }

    public static String[] setDataFoat(int length, int round) {
        String[] datas = new String[length];
        for (int m = 0; m < length; m++) {
            datas[m] = String.valueOf(FormatUtil.formatFloat((float) (round + 0.1 * m)));
        }
        return datas;
    }

    //int 型数范围
    public static String[] setData(int length, int round) {
        String[] datas = new String[length];
        for (int m = 0; m < length; m++) {
            datas[m] = String.valueOf(m + round);
        }
        return datas;
    }


    /**
     * 手机号前三后四显示
     *
     * @param phone
     * @return
     */
    public static String getSecretPhone(String phone) {
        StringBuffer stringBuffer = new StringBuffer(phone);
        int length = phone.length();
        stringBuffer.replace(3, length - 3, "*****");

        return stringBuffer.toString();
    }


    /**
     * 身份证号前三后四显示
     *
     * @param cardNumber
     * @return
     */
    public static String getSecretCard(String cardNumber) {
        StringBuffer stringBuffer = new StringBuffer(cardNumber);
        int length = cardNumber.length();
        stringBuffer.replace(3, length - 4, "***********");

        return stringBuffer.toString();
    }

    /**
     * 精确到type
     *
     * @param data
     * @return
     */
    public static String formatFloat(float data, String type) {
        float scale = data;
        DecimalFormat fnum = new DecimalFormat(type);
        String dd = fnum.format(scale);
        return dd;
    }

    /**
     * 精确到小数点后一位数String
     *
     * @param data
     * @return
     */
    public static String formatFloat(String data) {
        float scale = 0;
        if (!TextUtils.isEmpty(data))
            scale = Float.parseFloat(data);
        DecimalFormat fnum = new DecimalFormat(Const.FLOAT_1);
        String dd = fnum.format(scale);
        return dd;
    }

    /**
     * 精确到小数点后一位数float
     *
     * @param data
     * @return
     */
    public static String formatFloat(float data) {
        float scale = data;
        DecimalFormat fnum = new DecimalFormat("##0.0");
        String dd = fnum.format(scale);
        return dd;
    }

    public static String getUnit(String sensorType) {
        if (sensorType.equals(Const.BODYTEMPERATURE)) {
            return "℃";
        } else if (sensorType.equals(Const.BLODPRESSURE)) {
            return "mmHg";
        } else if (sensorType.equals(Const.ELECTROCARDIOGRAM)) {
            return "bpm";
        } else if (sensorType.equals(Const.OXYGENATION)) {
            return "%";
        } else {
            return "bpm";
        }
    }


    /*
           * 判断字符串是否包含一些字符 indexOf
           */
    public static boolean indexOfString(String src, String dest) {
        boolean flag = false;
        if (src.indexOf(dest) != -1) {
            flag = true;
        }
        return flag;
    }

    public static String double2String(double d, int num) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(num);//保留两位小数
        nf.setGroupingUsed(false);//去掉数值中的千位分隔符

        String temp = nf.format(d);
        if (temp.contains(".")) {
            String s1 = temp.split("\\.")[0];
            String s2 = temp.split("\\.")[1];
            for (int i = s2.length(); i > 0; --i) {
                if (!s2.substring(i - 1, i).equals("0")) {
                    return s1 + "." + s2.substring(0, i);
                }
            }
            return s1;
        }
        return temp;
    }

    /**
     * 将double转为数值，并最多保留num位小数。
     *
     * @param d
     * @param num      小数个数
     * @param defValue 默认值。当d为null时，返回该值。
     * @return
     */
    public static String double2String(Double d, int num, String defValue) {
        if (d == null) {
            return defValue;
        }

        return double2String(d, num);
    }
}
