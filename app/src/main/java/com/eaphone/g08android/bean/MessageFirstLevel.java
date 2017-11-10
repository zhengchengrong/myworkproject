package com.eaphone.g08android.bean;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/30 11:14
 * 修改人：Administrator
 * 修改时间：2017/8/30 11:14
 * 修改备注：
 */
public class MessageFirstLevel {

    /**
     * createTime : 2016-06-21T14:46:50+08:00
     * id : 57736ec5233339618effd0f8
     * image : http://static.xinxiangsui.net/images/message/icon/icon_service_message.png
     * level : 1
     * readStatus : read
     * text : 您已成功签约张三丰医生的家庭医生服务
     * title : 家庭医生签约
     * type : fuwuxiaoxi
     * updateTime : 2016-06-21T14:46:50+08:00
     * url : http://www.baidu.com
     * expireTime : 2017-09-26T18:36:26+08:00
     * userId : 575fb4b0d46168400f52a625
     */

    private String createTime;
    private String id;
    private String image;
    private Integer level;
    private String readStatus;
    private String text;
    private String title;
    private String type;
    private String updateTime;
    private String url;
    private String userId;
    private String expireTime;


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "MessageLevelOne{" +
                "createTime='" + createTime + '\'' +
                ", id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", level=" + level +
                ", readStatus='" + readStatus + '\'' +
                ", text='" + text + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", url='" + url + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }
}
