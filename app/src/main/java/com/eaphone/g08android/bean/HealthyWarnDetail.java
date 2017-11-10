package com.eaphone.g08android.bean;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/9/19 17:16
 * 修改人：Administrator
 * 修改时间：2017/9/19 17:16
 * 修改备注：
 */
public class HealthyWarnDetail {


    /**
     * brief_content : 您的体温测量值偏低，请注意添加衣服，然后多喝温水或者生姜水，提高体温，如长时间持续低温，建议尽快到医院就医检查。
     * createTime : 2017-09-16T13:45:08+08:00
     * displayValue : 35.8
     * id : 59bcba64d8e78f0ba0715945
     * sensorType : 23
     * status : 体温过低
     * time : 2017-09-15T13:44:00+08:00
     * title : 体温 35.8 ℃, 体温过低
     * type : 23
     * userId : 59a0de44d8e78f0ba02ecdbf
     */

    private String brief_content;
    private String createTime;
    private String displayValue;
    private String id;
    private String sensorType;
    private String status;
    private String time;
    private String title;
    private String type;
    private String userId;

    public String getBrief_content() {
        return brief_content;
    }

    public void setBrief_content(String brief_content) {
        this.brief_content = brief_content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
