package com.eaphone.g08android.bean;

import java.util.List;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/23 10:54
 * 修改人：Administrator
 * 修改时间：2017/8/23 10:54
 * 修改备注：
 */
public class HealthyDataEnity {

    private List<DataBean> data;

    public  List<DataBean>  getData() {
        return data;
    }

    public void setData( List<DataBean>  data) {
        this.data = data;
    }

    public static class DataBean{

        private String sensorType;
        private String type;

        public String getSensorType() {
            return sensorType;
        }

        public void setSensorType(String sensorType) {
            this.sensorType = sensorType;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "HealthyDataEnity{" +
                    "sensorType='" + sensorType + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

}
