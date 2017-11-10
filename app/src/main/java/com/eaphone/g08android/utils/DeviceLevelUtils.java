package com.eaphone.g08android.utils;

/**
 * Created by Administrator on 2016/6/21.
 */
public class DeviceLevelUtils {

    public static String getSensorType(String sensorType) {
        if (sensorType.equals(Const.HEARTRATE)) {
            return "心率";
        } else if (sensorType.equals(Const.BLODPRESSURE)) {
            return "血压";
        } else if (sensorType.equals(Const.OXYGENATION)) {
            return "血氧";
        } else if (sensorType.equals(Const.BODYTEMPERATURE)) {
            return "体温";
        } else {
            return "心电";
        }
    }


    /**
     * 心率
     *
     * @param data
     * @return 1 正常 2过快 3过慢
     */
    public static int getHRVLevel(float data) {
        int level = 0;
        if (data >= 60 && data <= 100) {
            level = 1;
        } else if (data > 100) {
            level = 2;
        } else if (data < 60) {
            level = 3;
        }
        return level;
    }

    /**
     * @param high
     * @param low
     * @return//1正常 2轻度 3中度 4重度  5低压
     */

    public static int getBloodPressLevel(int high, int low) {
        if ((high >= 80 && high < 140) && (low >= 50 && low < 90)) {
            return 1;
        } else if ((high >= 80 && low >= 50) && (high < 160 && low < 100) && (high >= 140 || low >= 90)) {
            return 2;
        } else if ((high >= 80 && low >= 50) && (high < 180 && low < 110) && (high >= 160 || low >= 100)) {
            return 3;
        } else if ((high >= 80 && low >= 50) && (high >= 180 || low >= 110)) {
            return 4;
        } else if ((high < 80 || low < 50)) {
            return 5;
        } else {
            return 2;
        }

        /*if ((high >= 80 && high < 140)&&(low >= 50 && low < 90)) {
            return 1;
        } else if (((high >= 140 && high < 160) && (low >= 90 && low < 100))) {
            return 2;
        } else if ((high >= 160 && high < 180) && (low >= 100 && low < 110)) {
            return 3;
        } else if (high >= 180 && low >= 110) {
            return 4;
        }  else if ((high <80 && low < 50)||(high<80&&low>=50)||(low<50&&high>=80)) {
            return 5;
        } else{
            return 2;
        }*/
    }

    /**
     * @param data
     * @return 1正常 2低热 3中热 4高热 5 超高热 6偏低
     */
    public static int getTemperatureLevel(float data) {
        if (data >= 36 && data < 37.5) {
            return 1;
        } else if (data >= 37.5 && data < 38) {
            return 2;
        } else if (data >= 38 && data < 39) {
            return 3;
        } else if (data >= 39 && data < 40) {
            return 4;
        } else if (data >= 40) {
            return 5;
        } else {
            return 6;
        }
    }

    /**
     * @param data
     * @return1 正常 2偏低
     */
    public static int getBloodOxygen(float data) {
        if (data >= 95 && data <= 100) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * @param data  测量血糖的数据
     * @param state 1空腹  2餐后一小时  3餐后两小时
     * @return 1正常  2低  3高
     */
    public static int getBloodGlucoseEmpty(float data, int state) {
        int grade = 0;
        if (state == 1) {
            if (data < 3.9) {
                grade = 2;
            } else if (data >= 3.9 && data <= 6.2) {
                grade = 1;
            } else {
                grade = 3;
            }
        } else if (state == 2) {
            if (data < 7.8) {
                grade = 2;
            } else if (data >= 7.8 && data <= 9.0) {
                grade = 1;
            } else {
                grade = 3;
            }
        } else if (state == 3) {
            if (data < 3.9) {
                grade = 2;
            } else if (data > 3.8 && data < 7.9) {
                grade = 1;
            } else {
                grade = 3;
            }
        }
        return grade;
    }

    public static String getBloodSugar(String text) {
        if ("空腹".equals(text)) {
            return "01";
        } else if ("饭后一小时".equals(text)) {
            return "02";
        } else if ("饭后两小时".equals(text)) {
            return "03";
        } else {
            return "02";
        }
    }
}
