package com.eaphone.g08android.bean;

import java.io.Serializable;

/**
 * 项目名称：瘤盾
 * 类描述：qq返回的用户信息
 * 创建人：zlq
 * 创建时间：2017/5/3 10:05
 * 修改人：Administrator
 * 修改时间：2017/5/3 10:05
 * 修改备注：
 */
public class ResultQQMsg implements Serializable{

    /**
     * ret : 0
     * msg :
     * is_lost : 0
     * nickname : Null
     * gender : 男
     * province : 广西
     * city : 南宁
     * figureurl : http://qzapp.qlogo.cn/qzapp/1106222158/86A5A42995775F4E274697806FDAB64C/30
     * figureurl_1 : http://qzapp.qlogo.cn/qzapp/1106222158/86A5A42995775F4E274697806FDAB64C/50
     * figureurl_2 : http://qzapp.qlogo.cn/qzapp/1106222158/86A5A42995775F4E274697806FDAB64C/100
     * figureurl_qq_1 : http://q.qlogo.cn/qqapp/1106222158/86A5A42995775F4E274697806FDAB64C/40
     * figureurl_qq_2 : http://q.qlogo.cn/qqapp/1106222158/86A5A42995775F4E274697806FDAB64C/100
     * is_yellow_vip : 0
     * vip : 0
     * yellow_vip_level : 0
     * level : 0
     * is_yellow_year_vip : 0
     */

    private int ret;
    private String msg;
    private int is_lost;
    private String nickname;
    private String gender;
    private String province;
    private String city;
    private String figureurl;
    private String figureurl_1;
    private String figureurl_2;
    private String figureurl_qq_1;
    private String figureurl_qq_2;
    private String is_yellow_vip;
    private String vip;
    private String yellow_vip_level;
    private String level;
    private String is_yellow_year_vip;


    private String openid;
    private String source;
    private String phone;
    private String password;
    /**
     * sex : 1
     * language : zh_CN
     * country : CN
     * headimgurl : http://wx.qlogo.cn/mmopen/Q3auHgzwzM4M6C1ao7vQVOn4pOkyaph8FO4OiaJbpq04z8NYxgDicias4haWVnGyibGZgMrXkCFgjnxW7UiauIOLyLKtaCeOI0W4IN84K8dicNDEo/0
     * privilege : []
     * unionid : oaqw7wxJ61rF5nxXYIDMu3MFYq4s
     */

    private int sex;
    private String language;
    private String country;
    private String headimgurl;
    private String unionid;
    //private List<?> privilege;


    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOpenId() {
        return openid;
    }

    public void setOpenId(String openId) {
        this.openid = openId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getIs_lost() {
        return is_lost;
    }

    public void setIs_lost(int is_lost) {
        this.is_lost = is_lost;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFigureurl() {
        return figureurl;
    }

    public void setFigureurl(String figureurl) {
        this.figureurl = figureurl;
    }

    public String getFigureurl_1() {
        return figureurl_1;
    }

    public void setFigureurl_1(String figureurl_1) {
        this.figureurl_1 = figureurl_1;
    }

    public String getFigureurl_2() {
        return figureurl_2;
    }

    public void setFigureurl_2(String figureurl_2) {
        this.figureurl_2 = figureurl_2;
    }

    public String getFigureurl_qq_1() {
        return figureurl_qq_1;
    }

    public void setFigureurl_qq_1(String figureurl_qq_1) {
        this.figureurl_qq_1 = figureurl_qq_1;
    }

    public String getFigureurl_qq_2() {
        return figureurl_qq_2;
    }

    public void setFigureurl_qq_2(String figureurl_qq_2) {
        this.figureurl_qq_2 = figureurl_qq_2;
    }

    public String getIs_yellow_vip() {
        return is_yellow_vip;
    }

    public void setIs_yellow_vip(String is_yellow_vip) {
        this.is_yellow_vip = is_yellow_vip;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getYellow_vip_level() {
        return yellow_vip_level;
    }

    public void setYellow_vip_level(String yellow_vip_level) {
        this.yellow_vip_level = yellow_vip_level;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getIs_yellow_year_vip() {
        return is_yellow_year_vip;
    }

    public void setIs_yellow_year_vip(String is_yellow_year_vip) {
        this.is_yellow_year_vip = is_yellow_year_vip;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

//    public List<?> getPrivilege() {
//        return privilege;
//    }
//
//    public void setPrivilege(List<?> privilege) {
//        this.privilege = privilege;
//    }
}
