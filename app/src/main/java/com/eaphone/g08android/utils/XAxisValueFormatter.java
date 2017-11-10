package com.eaphone.g08android.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charlie on 2016/9/23.
 * 对字符串类型的坐标轴标记进行格式化
 */
public class XAxisValueFormatter /*implements IAxisValueFormatter */{


    /**
     * 周曲线x轴数据
     */
    private static  List<String> setWeekXAxisValue(List<String> mStrs) {
        List<String> weekList = new ArrayList<>();

        for (int i = 0; i < mStrs.size(); i++) {
//            weekList.add(TimeUtils.parseWeekDate(mStrs.get(i), TimeUtils.TIME_TYPE_1));
            weekList.add(TimeUtils.timeTypeChange(mStrs.get(i), TimeUtils.TIME_TYPE_1, TimeUtils.TIME_TYPE_15));
        }


        return weekList;
    }

    /**
     * 日曲线
     *
     * @return
     */
    private static  List<String> setDayXAxisValue(List<String> mStrs) {
        List<String> dayList = new ArrayList<>();
        for (int i = 0; i < mStrs.size(); i++) {
            dayList.add(TimeUtils.timeTypeChange(mStrs.get(i), TimeUtils.TIME_TYPE_1, TimeUtils.TIME_TYPE_14));
        }
        for (int i = 0; i <dayList.size() ; i++) {
            if(dayList.get(i).startsWith("0")){
                String aaa = dayList.get(i).replace("0","");
                dayList.set(i,aaa);
            }
        }
        return dayList;
    }

    /**
     * 月曲线
     *
     * @return
     */
    private static  List<String> setMonthXAxisValue(List<String> mStrs) {
        List<String> monthList = new ArrayList<>();
        for (int i = 0; i < mStrs.size(); i++) {
            monthList.add(TimeUtils.timeTypeChange(mStrs.get(i), TimeUtils.TIME_TYPE_1, TimeUtils.TIME_TYPE_15));
        }

        for (int i = 0; i <monthList.size() ; i++) {
            if(monthList.get(i).startsWith("0")){
                String aaa = monthList.get(i).replace("0","");
                monthList.set(i,aaa);
            }
        }
        return monthList;
    }


    /**
     * 年曲线
     *
     * @return
     */
    private static List<String> setYearXAxisValue(List<String> mStrs) {
        List<String> monthList = new ArrayList<>();
        for (int i = 0; i < mStrs.size(); i++) {
            monthList.add(TimeUtils.timeTypeChange(mStrs.get(i), TimeUtils.TIME_TYPE_1, TimeUtils.TIME_TYPE_11));
        }

        for (int i = 0; i <monthList.size() ; i++) {
            if(monthList.get(i).startsWith("0")){
                String aaa = monthList.get(i).replace("0","");
                monthList.set(i,aaa);
            }
        }

        return monthList;
    }

    public static List<String> getXAxisValue(List<String> xAxisValue,String type){

        List<String>  xxx ;

        switch (type) {
            case "日":
                xxx = setDayXAxisValue(xAxisValue);
                break;
            case "周":
                xxx = setWeekXAxisValue(xAxisValue);
                break;

            case "月":
                xxx = setMonthXAxisValue(xAxisValue);
                break;
            case "年":
                xxx = setYearXAxisValue(xAxisValue);
                break;
            default:
                xxx = new ArrayList<>();
                break;
        }

        return xxx;
    }

}
