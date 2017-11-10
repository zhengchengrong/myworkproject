package com.eaphone.g08android.bean;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/9/13 15:25
 * 修改人：Administrator
 * 修改时间：2017/9/13 15:25
 * 修改备注：
 */
public class JianKang {


    /**
     * height : 158
     * weight : 158
     * chol : 6.4
     * tag : 1.2
     * ldlc : 2.4
     * hdlc : 1.2
     * chol : 0
     * tag : 0
     * ldlc : 0
     * hdlc : 0
     * hypotension_age : 0
     * hypotension_date : Unknown Type: date
     * systolic : 0
     * diastolic : 0
     * heart_rate : 0
     */

    private Integer hypotension_age;
    private String hypotension_date;
    private Integer systolic;
    private Integer diastolic;
    private Integer heart_rate;
    private Integer height;
    private Integer weight;
    private Double chol;
    private Double tag;
    private Double ldlc;
    private Double hdlc;

    public Integer getHypotension_age() {
        return hypotension_age;
    }

    public void setHypotension_age(Integer hypotension_age) {
        this.hypotension_age = hypotension_age;
    }

    public String getHypotension_date() {
        return hypotension_date;
    }

    public void setHypotension_date(String hypotension_date) {
        this.hypotension_date = hypotension_date;
    }

    public Integer getSystolic() {
        return systolic;
    }

    public void setSystolic(Integer systolic) {
        this.systolic = systolic;
    }

    public Integer getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(Integer diastolic) {
        this.diastolic = diastolic;
    }

    public Integer getHeart_rate() {
        return heart_rate;
    }

    public void setHeart_rate(Integer heart_rate) {
        this.heart_rate = heart_rate;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Double getChol() {
        return chol;
    }

    public void setChol(Double chol) {
        this.chol = chol;
    }

    public Double getTag() {
        return tag;
    }

    public void setTag(Double tag) {
        this.tag = tag;
    }

    public Double getLdlc() {
        return ldlc;
    }

    public void setLdlc(Double ldlc) {
        this.ldlc = ldlc;
    }

    public Double getHdlc() {
        return hdlc;
    }

    public void setHdlc(Double hdlc) {
        this.hdlc = hdlc;
    }
}
