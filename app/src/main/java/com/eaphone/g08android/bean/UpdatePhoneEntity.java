package com.eaphone.g08android.bean;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/9/16 14:35
 * 修改人：Administrator
 * 修改时间：2017/9/16 14:35
 * 修改备注：
 */
public class UpdatePhoneEntity {


    /**
     * name : string
     * password : string
     * phone : string
     * email : user@example.com
     * role : user
     * authcode : string
     */

    private String name;
    private String password;
    private String phone;
    private String email;
    private String role;
    private String authcode;

    public UpdatePhoneEntity(String password, String phone, String authcode) {
        this.password = password;
        this.phone = phone;
        this.authcode = authcode;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAuthcode() {
        return authcode;
    }

    public void setAuthcode(String authcode) {
        this.authcode = authcode;
    }
}
