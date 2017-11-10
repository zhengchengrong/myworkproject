package com.eaphone.g08android.bean;

/**
 * Created by zlq on 2017/7/22.
 */

public class LoginEnity {

    private String role;
    private String account;
    private String password;

    public LoginEnity(String role, String account, String password) {
        this.role = role;
        this.account = account;
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
