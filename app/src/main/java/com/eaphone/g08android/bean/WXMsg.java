package com.eaphone.g08android.bean;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/10/27 11:14
 * 修改人：Administrator
 * 修改时间：2017/10/27 11:14
 * 修改备注：
 */
public class WXMsg {


    /**
     * access_token : KgZzXehwyn6FlQ23Zm9NZdFmXEw4CibqG8SjTznH3grPmwjcYC0WCdHjBMQDZ-UoCIjB7PlnT01OwUY-5tqp7mLuT5bC2QzACD8GcXDPQKg
     * expires_in : 7200
     * refresh_token : bUHNYt6vmATMTbYJJe_0n0_Q3SuldbZw21MtezqzLT3hUEViMBu5eHDzSy5z7T6D-qjSEJUvcm6td-1HzyyNa9FD3PQ1n1pIIGJ1fAATrUM
     * openid : ojlu5wqqWxz-8J9KMhVYr-kcX-I0
     * scope : snsapi_userinfo
     * unionid : oaqw7wxJ61rF5nxXYIDMu3MFYq4s
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String unionid;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
