package com.eaphone.g08android.bean;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/9/5 10:31
 * 修改人：Administrator
 * 修改时间：2017/9/5 10:31
 * 修改备注：
 */
public class JiankangFileEnity {

    private String title;
    private String content;
    private String unit;
    private int type;

    public JiankangFileEnity(String title, String content, String unit, int type) {
        this.title = title;
        this.content = content;
        this.unit = unit;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
