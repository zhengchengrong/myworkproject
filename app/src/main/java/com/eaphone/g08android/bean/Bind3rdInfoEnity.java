package com.eaphone.g08android.bean;

/**
 * 项目名称：心相随
 * 类描述：完善第三方信息
 * 创建人：zlq
 * 创建时间：2017/8/21 10:45
 * 修改人：Administrator
 * 修改时间：2017/8/21 10:45
 * 修改备注：
 */
public class Bind3rdInfoEnity {

    private String name;
    private String password;
    private String phone;
    private String role;
    private String address;
    private String avatar;
    private String userId;
    private String sex;

    public Bind3rdInfoEnity() {
    }

    public Bind3rdInfoEnity(String name, String password, String phone, String role, String address, String avatar, String userId,String sex) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.address = address;
        this.avatar = avatar;
        this.userId = userId;
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
