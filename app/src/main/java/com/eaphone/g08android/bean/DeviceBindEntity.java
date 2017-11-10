package com.eaphone.g08android.bean;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/9/7 14:54
 * 修改人：Administrator
 * 修改时间：2017/9/7 14:54
 * 修改备注：
 */
public class DeviceBindEntity {


    /**
     * product_id : string
     * serial_number : string
     * channel_name : string
     * name : string
     * userId : string
     */

    private String product_id;
    private String serial_number;
    private String channel_name;
    private String name;
    private String userId;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "DeviceBindEntity{" +
                "product_id='" + product_id + '\'' +
                ", serial_number='" + serial_number + '\'' +
                ", channel_name='" + channel_name + '\'' +
                ", name='" + name + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
