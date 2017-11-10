package com.eaphone.g08android.bean;

import java.util.List;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/9/4 11:29
 * 修改人：Administrator
 * 修改时间：2017/9/4 11:29
 * 修改备注：
 */
public class MessageWarnDetail {


    /**
     * user : {"name":"小猫","age":66,"sex":"女"}
     * title : 【心率指标报警】
     * sensor : {"sensorType":"17","sensorName":"心率","normalValue":"60-90 次/分"}
     * abnormalRecords : [{"value":"110","timestamp":"2016-07-07T11:22:33+08:00","text":"心率过速"},{"value":"110","timestamp":"2016-07-08T11:22:33+08:00","text":"心率过速"}]
     * timestamp : 2016-03-28T10:28:45+08:00
     */

    private UserBean user;
    private String title;
    private SensorBean sensor;
    private String timestamp;
    private List<AbnormalRecordsBean> abnormalRecords;
    /**
     * id : 59bcba88d8e78f0ba0715949
     * userId : 59a0de44d8e78f0ba02ecdbf
     */

    private String id;
    private String userId;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SensorBean getSensor() {
        return sensor;
    }

    public void setSensor(SensorBean sensor) {
        this.sensor = sensor;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<AbnormalRecordsBean> getAbnormalRecords() {
        return abnormalRecords;
    }

    public void setAbnormalRecords(List<AbnormalRecordsBean> abnormalRecords) {
        this.abnormalRecords = abnormalRecords;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static class UserBean {
        /**
         * name : 小猫
         * age : 66
         * sex : 女
         */

        private String name;
        private int age;
        private String sex;
        private String gender;

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }

    public static class SensorBean {
        /**
         * sensorType : 17
         * sensorName : 心率
         * normalValue : 60-90 次/分
         */

        private String sensorType;
        private String sensorName;
        private String normalValue;

        public String getSensorType() {
            return sensorType;
        }

        public void setSensorType(String sensorType) {
            this.sensorType = sensorType;
        }

        public String getSensorName() {
            return sensorName;
        }

        public void setSensorName(String sensorName) {
            this.sensorName = sensorName;
        }

        public String getNormalValue() {
            return normalValue;
        }

        public void setNormalValue(String normalValue) {
            this.normalValue = normalValue;
        }
    }

    public static class AbnormalRecordsBean {
        /**
         * value : 110
         * timestamp : 2016-07-07T11:22:33+08:00
         * text : 心率过速
         */

        private String value;
        private String timestamp;
        private String text;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
