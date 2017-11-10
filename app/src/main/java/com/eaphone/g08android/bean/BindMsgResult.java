package com.eaphone.g08android.bean;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/18 15:56
 * 修改人：Administrator
 * 修改时间：2017/8/18 15:56
 * 修改备注：
 */
public class BindMsgResult  {

    private String status;

    private TokenResult login;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TokenResult getLogin() {
        return login;
    }

    public void setLogin(TokenResult login) {
        this.login = login;
    }
}
