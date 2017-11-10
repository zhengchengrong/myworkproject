package com.eaphone.g08android.bean;

import java.util.Date;

/**
 * 项目名称：心相随 患者端
 * 类描述：曲线图数据实体类
 * 创建人：wjq
 * 创建时间：2016/6/29 14:16
 * 修改人：user
 * 修改时间：2016/6/29 14:16
 * 修改备注：
 */
public class Data {

    private Date timestamp;
    private float value;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Data{" +
                "timestamp=" + timestamp +
                ", value=" + value +
                '}';
    }
}
