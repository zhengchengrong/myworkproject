package com.eaphone.g08android.bean;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/9/2 17:39
 * 修改人：Administrator
 * 修改时间：2017/9/2 17:39
 * 修改备注：
 */
public class HealthyWarn  {
    /**
     * id : 70158928-ab51-4b53-a79d-591e1aab9afe
     * title : 周心率预警
     * measureCount : 4
     * alarmCount : 0
     */

    private String id;
    private String title;
    private int measureCount;
    private int alarmCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMeasureCount() {
        return measureCount;
    }

    public void setMeasureCount(int measureCount) {
        this.measureCount = measureCount;
    }

    public int getAlarmCount() {
        return alarmCount;
    }

    public void setAlarmCount(int alarmCount) {
        this.alarmCount = alarmCount;
    }

    @Override
    public String toString() {
        return "HealthyWarm{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", measureCount=" + measureCount +
                ", alarmCount=" + alarmCount +
                '}';
    }

}
