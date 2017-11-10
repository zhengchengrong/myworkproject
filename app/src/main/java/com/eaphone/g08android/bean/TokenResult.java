package com.eaphone.g08android.bean;

/**
 * Created by zlq on 2017/7/22.
 */

public class TokenResult {


    /**
     * token : TGT-25-xa1t4O945cKBA5StlHtzZfiYMVoTvrxqcXrtlTV3BDeDEHacGG-senssun
     * tokenExpiration : 8
     * userId : ac0c4a12-2ced-433c-973b-2542ecb71b34
     */
    private String token;
    private int tokenExpiration;
    private String userId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getTokenExpiration() {
        return tokenExpiration;
    }

    public void setTokenExpiration(int tokenExpiration) {
        this.tokenExpiration = tokenExpiration;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "TokenResult{" +
                "token='" + token + '\'' +
                ", tokenExpiration=" + tokenExpiration +
                ", userId='" + userId + '\'' +
                '}';
    }
}
