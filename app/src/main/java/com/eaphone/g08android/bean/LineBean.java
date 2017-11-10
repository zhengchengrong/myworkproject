package com.eaphone.g08android.bean;

/**
 * Created by zlq on 2017/9/25.
 */

public class LineBean {


    public LineBean(String text, String timestamp) {
        this.text = text;
        this.timestamp = timestamp;
    }

    /**
     * sensorType : 22
     * text : 85
     * timestamp : 2017-09-12T00:00:00+08:00
     * value : 85
     */




    private String sensorType;
    private String text;
    private String timestamp;

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
