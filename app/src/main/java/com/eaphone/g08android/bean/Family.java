package com.eaphone.g08android.bean;

import java.io.Serializable;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/22 12:09
 * 修改人：Administrator
 * 修改时间：2017/8/22 12:09
 * 修改备注：
 */
public class Family implements Serializable {


    /**
     * userId : 575fa8f6d46168400f52a624
     * name : tinker
     * relationship : 父亲
     * avatarUrl : http://192.168.1.238:8048/common/v1/image/b8518a25920611e7a931f782e8580fd6-s.jpg
     * phone : 13726278510
     */

    private String userId;
    private String name;
    private String relationship;
    private String phone;
    private boolean isCheck;
    private String avatarUrl;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
