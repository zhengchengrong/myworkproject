package com.eaphone.g08android.bean;

import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.FormatUtil;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/23 11:21
 * 修改人：Administrator
 * 修改时间：2017/8/23 11:21
 * 修改备注：
 */
public class Healthy {


    /**
     * sensorType : 02
     * value : 41
     * type : min
     */

    private String sensorType;
    private Object value;
    private String value_second;
    private String type;
    private String timestamp;
    private Status status;
    /**
     * batchId : 7ec77eba-1c82-4a8d-bf88-51ba26ac07a9
     * deviceId : 57fdabead4616850ba839ad5
     * product : {"id":"1212-efde-1523-785feabcd123","name":"TD-4279 血糖仪"}
     */

    private String batchId;
    private String deviceId;

    private String text;
    /**
     * id : 1212-efde-1523-785feabcd123
     * name : TD-4279 血糖仪
     */

    private SourceBean source;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * text : 15677820273
     * value : 饭后2小时
     */



    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public String getValue() {
        try {
            if (value.toString().contains("{")) {
                String value2 = value.toString().replace("{diastolic=", "").replace(" systolic=", "").replace("}","");
                String[] split = value2.split(",");
                int value11 = Integer.parseInt(FormatUtil.formatFloat(Float.parseFloat(split[0]), Const.FLOAT_2));
                int value22 = Integer.parseInt(FormatUtil.formatFloat(Float.parseFloat(split[1]), Const.FLOAT_2));
                value = "[" + value11 + "," + value22 + "]";
            }
        } catch (Exception e) {
        }

        return value.toString();
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue_second() {
        return value_second;
    }

    public void setValue_second(String value_second) {
        this.value_second = value_second;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "HealthyDataHome{" +
                "sensorType='" + sensorType + '\'' +
                ", value='" + value + '\'' +
                ", value_second='" + value_second + '\'' +
                ", type='" + type + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", status=" + status +
                '}';
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public SourceBean getSource() {
        return source;
    }

    public void setSource(SourceBean source) {
        this.source = source;
    }

    public static class SourceBean {
        private String text;
        private String type;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class Status {
        private String text;
        private String value;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class  PressureBean{

        /**
         * diastolic : 43
         * systolic : 16
         */

        private int diastolic;
        private int systolic;

        public int getDiastolic() {
            return diastolic;
        }

        public void setDiastolic(int diastolic) {
            this.diastolic = diastolic;
        }

        public int getSystolic() {
            return systolic;
        }

        public void setSystolic(int systolic) {
            this.systolic = systolic;
        }
    }

}
