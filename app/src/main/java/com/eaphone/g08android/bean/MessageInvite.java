package com.eaphone.g08android.bean;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/30 14:21
 * 修改人：Administrator
 * 修改时间：2017/8/30 14:21
 * 修改备注：
 */
public class MessageInvite {



    /**
     * id : 57809424233339618effd128
     * from : 575fc34ad46168400f52a627
     * to : 575fa8f6d46168400f52a624
     * status : waiting
     * title : tinker
     * text : 邀请您成为 TA 的家庭成员
     * createTime : 2016-07-18T10:15:22+08:00
     * expiration : 2016-07-25T10:15:22+08:00
     */

    private String id;
    private String from;
    private String to;
    private String status;
    private String title;
    private String text;
    private String createTime;
    private String expiration;
    private String name;
    /**
     * expirationTime : 2016-07-29T11:27:04+08:00
     * relationship : siblings
     */

    private String expirationTime;
    private String relationship;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
}
