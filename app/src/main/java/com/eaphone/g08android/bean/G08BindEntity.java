package com.eaphone.g08android.bean;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/31 10:02
 * 修改人：Administrator
 * 修改时间：2017/8/31 10:02
 * 修改备注：
 */
public class G08BindEntity  {


    /**
     * ssid : YF EPHONE
     * bssid : 408D5CA9CB6A
     * phoneAddress : 192.168.1.57
     * deviceAddress : 192.168.1.96
     */

    private String ssid;
    private String bssid;
    private String phoneAddress;
    private String deviceAddress;

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public String getPhoneAddress() {
        return phoneAddress;
    }

    public void setPhoneAddress(String phoneAddress) {
        this.phoneAddress = phoneAddress;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }
}
