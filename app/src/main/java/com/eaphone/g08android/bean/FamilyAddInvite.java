package com.eaphone.g08android.bean;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/30 16:11
 * 修改人：Administrator
 * 修改时间：2017/8/30 16:11
 * 修改备注：
 */
public class FamilyAddInvite {


    public FamilyAddInvite(String phone, String relationship) {
        this.phone = phone;
        this.relationship = relationship;
    }

    /**
     * phone : string
     * relationship : 祖辈
     */

    private String phone;
    private String relationship;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
}
