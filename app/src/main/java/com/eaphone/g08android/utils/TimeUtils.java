package com.eaphone.g08android.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/18 16:38
 * 修改人：Administrator
 * 修改时间：2017/8/18 16:38
 * 修改备注：
 */
public class TimeUtils {


    public static final String TIME_TYPE_1 = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String TIME_TYPE_2 = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_TYPE_3 = "yyyy-MM-dd";
    public static final String TIME_TYPE_4 = "yyyy-MM";
    public static final String TIME_TYPE_5 = "yyyy年MM月dd日";
    public static final String TIME_TYPE_6 = "yyyy-MM-dd HH:mm";
    public static final String TIME_TYPE_7 = "yyyy";
    public static final String TIME_TYPE_8 = "HH:mm";
    public static final String TIME_TYPE_9 = "MM月dd日";
    public static final String TIME_TYPE_10 = "yyyy年MM月";
    public static final String TIME_TYPE_11 = "MM";
    public static final String TIME_TYPE_12 = "mm";
    public static final String TIME_TYPE_13 = "MM-dd";
    public static final String TIME_TYPE_14 = "HH";
    public static final String TIME_TYPE_15 = "dd";
    public static final String TIME_TYPE_16 = "MM-dd HH:mm:ss";
    public static final String TIME_TYPE_17 = "HH:mm:ss";


    /**
     * 修改时间类型
     *
     * @param date
     * @param input_type
     * @param output_type
     * @return
     */
    public static String timeTypeChange(String date, String input_type, String output_type) {
        SimpleDateFormat sdf_in = new SimpleDateFormat(input_type);
        SimpleDateFormat sdf_out = new SimpleDateFormat(output_type);

        Date parse = null;
        try {
            parse = sdf_in.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return sdf_out.format(parse);
    }

    /**
     * 获取当前时间
     *
     * @param type 时间格式
     * @return
     */
    public static String getCurrentTime(String type) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(type);
        String format = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        return format;
    }



    /**
     * 如何是今天的则显示时分，否则显示年月日
     *
     * @param time
     * @return
     */
    public static String displayTime(String time) {
        String showTime = "";
        if (!TextUtils.isEmpty(time)) {
            showTime = timeTypeChange(time, TIME_TYPE_1, TIME_TYPE_3);
            if (showTime.equals(getCurrentTime(TIME_TYPE_3))) {
                showTime = timeTypeChange(time, TIME_TYPE_1, TIME_TYPE_8);
            }
        }
        return showTime;
    }

    public static String getMonthLastDay(String date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timeToDate(date,TIME_TYPE_4));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = calendar.getTime();
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDayOfMonth = calendar.getTime();
        return  dateToTime(lastDayOfMonth,TIME_TYPE_3);
    }


    /*
     * 如果是今天就显示时分，其它则显示年月日
     * */
    public static String formatDisplayTime(String time, String pattern) {
        String display = "";
        int tMin = 60 * 1000;
        int tHour = 60 * tMin;
        int tDay = 24 * tHour;
        if (time != null) {
            try {
                Date tDate = new SimpleDateFormat(pattern).parse(time);
                Date today = new Date();
                SimpleDateFormat thisYearDf = new SimpleDateFormat("yyyy");
                SimpleDateFormat todayDf = new SimpleDateFormat("yyyy-MM-dd");
                Date thisYear = new Date(thisYearDf.parse(thisYearDf.format(today)).getTime());
                Date yesterday = new Date(todayDf.parse(todayDf.format(today)).getTime());
                Date beforeYes = new Date(yesterday.getTime() - tDay);
                if (tDate != null) {
//                    SimpleDateFormat halfDf = new SimpleDateFormat("MM-dd ");
                    SimpleDateFormat halfDf = new SimpleDateFormat("yyyy-MM-dd ");
                    long dTime = today.getTime() - tDate.getTime();
                    if (tDate.before(thisYear)) {
                        display = new SimpleDateFormat("yyyy-MM-dd ").format(tDate);
                    } else {

                        /*if (dTime < tMin) {
                            display = "刚刚";
                        } else if (dTime < tHour) {
                            display = (int) Math.ceil(dTime / tMin) + "分钟前";
                        } else*/
                        if (dTime < tDay && tDate.after(yesterday)) {
//                            display = (int) Math.ceil(dTime / tHour) + "小时前";
                            display = new SimpleDateFormat("HH:mm ").format(tDate);
                        } else if (tDate.after(beforeYes) && tDate.before(yesterday)) {
                            display = halfDf.format(tDate);
//                            display = "昨天" + new SimpleDateFormat("HH:mm").format(tDate);
                        } else {
                            display = halfDf.format(tDate);
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
        return display;
    }


    //时间转换成date
    public static Date timeToDate(String time, String type) {
        SimpleDateFormat sdf = new SimpleDateFormat(type);

        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            return null;
//            ExceptionCatchUtil.exceptionToprint(e);
        }

    }

    /**
     * 判断输入的时间是现在之前的时间还是未来的时间
     * @param time
     * @param type
     * @return
     */
    public static boolean isFuture(String time,String type){
        Date date = timeToDate(time, type);
        Date dateNow = new Date(System.currentTimeMillis());
        if(dateNow.after(date)){
            return false;
        }else{
            return true;
        }
    }

    //    date转换成type的时间类型
    public static String dateToTime(Date date, String type) {
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        return sdf.format(date);
    }



    public enum FIRST_OR_LAST {
        FIRST, LAST
    }


    //通过date转化成type类型的时间
    public static String getDateToTime(Date date, String type) {
        SimpleDateFormat sdf1 = new SimpleDateFormat(type);
        String time = sdf1.format(date);
        return time;
    }


    /**
     * 根据当前日期获得是星期几
     *
     * @return
     */
    public static String parseWeekDate(String time,String timeType) {
        String Week = "";

        SimpleDateFormat format = new SimpleDateFormat(timeType);
        Calendar c = Calendar.getInstance();
        try {

            c.setTime(format.parse(time));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            Week += "周日";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            Week += "周一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            Week += "周二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            Week += "周三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            Week += "周四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            Week += "周五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            Week += "周六";
        }
        return Week;
    }

    public enum ChangeTimeType {//加减的枚举
        ADD, REDUCE
    }

    public enum DateType {//时间类型的枚举
        SECOND, MINUTE, HOUR, DAY, WEEK, MONTH, YEAR
    }

    /**
     * 通用的修改时间方法
     *
     * @param mDate       原来的数据
     * @param input_type  原来数据的类型
     * @param output_type 输出的数据类型
     * @param change_type 增加或者减少
     * @param dateType    修改什么时间类型
     * @return
     */
    public static String changeToTime(String mDate, String input_type, String output_type, ChangeTimeType change_type, DateType dateType) {
        if (TextUtils.isEmpty(mDate)) return "";
        SimpleDateFormat simpleDateFormat_input = new SimpleDateFormat(input_type);
        SimpleDateFormat simpleDateFormat_output = new SimpleDateFormat(output_type);
        SimpleDateFormat simpleDateFormat_year = new SimpleDateFormat(TIME_TYPE_7);
        SimpleDateFormat simpleDateFormat_month = new SimpleDateFormat(TIME_TYPE_11);
        SimpleDateFormat simpleDateFormat_month_year = new SimpleDateFormat(TIME_TYPE_4);
        String result = mDate;
        try {
            Date parse = simpleDateFormat_input.parse(mDate);
            long time = parse.getTime();
            long change_time = 0;
            int format_year = Integer.valueOf(simpleDateFormat_year.format(parse));
            int format_month = Integer.valueOf(simpleDateFormat_month.format(parse));
            switch (dateType) {
                case SECOND:
                    change_time = 1000;
                    break;
                case MINUTE:
                    change_time = 1000 * 60;
                    break;
                case HOUR:
                    change_time = 1000 * 60 * 60;
                    break;
                case DAY:
                    change_time = 1000 * 60 * 60 * 24;
                    break;
                case WEEK:
                    change_time = 1000 * 60 * 60 * 24 * 6;
                    break;
                case MONTH:
                    if (ChangeTimeType.ADD.equals(change_type)) {//加
                        if (format_month == 12) {
                            format_year += 1;
                            Date parse1 = simpleDateFormat_month_year.parse(format_year + "-01");
                            return simpleDateFormat_output.format(parse1);
                        } else {
                            format_month += 1;
                            String month = String.valueOf(format_month);
                            if (month.length() != 2) {
                                month = "0" + format_month;
                            }
                            Date parse1 = simpleDateFormat_month_year.parse(format_year + "-" + month);
                            return simpleDateFormat_output.format(parse1);
                        }

                    } else {//ChangeTimeType.REDUCE  减
                        if (format_month == 1) {
                            format_year -= 1;
                            Date parse1 = simpleDateFormat_month_year.parse(format_year + "-12");
                            return simpleDateFormat_output.format(parse1);
                        } else {
                            format_month -= 1;
                            String month = String.valueOf(format_month);
                            if (month.length() != 2) {
                                month = "0" + format_month;
                            }
                            Date parse1 = simpleDateFormat_month_year.parse(format_year + "-" + month);
                            return simpleDateFormat_output.format(parse1);
                        }
                    }
//                    break;
                case YEAR:
//                    change_time = 0;
                    if (ChangeTimeType.ADD.equals(change_type)) {
                        format_year += 1;

                    } else {//ChangeTimeType.REDUCE
                        format_year -= 1;
                    }
                    Date parse1 = simpleDateFormat_year.parse(format_year + "");
                    return simpleDateFormat_output.format(parse1);
//                    break;
                default:
                    change_time = 1000;
                    break;
            }


            if (ChangeTimeType.ADD.equals(change_type)) {
                time += change_time;
            } else {//ChangeTimeType.REDUCE
                time -= change_time;
            }
//            result = simpleDateFormat_output.format(time);
            return simpleDateFormat_output.format(time);

        } catch (ParseException e) {
//            ExceptionCatchUtil.exceptionToprint(e);
        }
        return result;
    }

    /**
     * 秒转化为天小时分秒字符串
     *
     * @param seconds
     * @return String
     */
    public static String formatSeconds(long seconds) {
        String timeStr = seconds + "秒";
        if (seconds > 60) {
            long second = seconds % 60;
            long min = seconds / 60;
            timeStr = min + "分" + second + "秒";
            if (min > 60) {
                min = (seconds / 60) % 60;
                long hour = (seconds / 60) / 60;
                timeStr = hour + "小时" + min + "分" + second + "秒";
                if (hour > 24) {
                    hour = ((seconds / 60) / 60) % 24;
                    long day = (((seconds / 60) / 60) / 24);
                    timeStr = day + "天" + hour + "小时" + min + "分" + second + "秒";
                }
            }
        }
        return timeStr;
    }
}
