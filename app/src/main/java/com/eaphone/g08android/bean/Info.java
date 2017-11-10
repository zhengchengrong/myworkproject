package com.eaphone.g08android.bean;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/23 17:32
 * 修改人：Administrator
 * 修改时间：2017/8/23 17:32
 * 修改备注：
 */
public class Info  {

    /**
     * id : xxxxxxx
     * title : 大医院实名制就医来了！
     * image : http://xxx.com/xxx/xxx/xxx.jpg
     * createTime : 2016-07-26T19:09:22+08:00
     * comments : 2
     */

    private String id;
    private String title;
    private String image;
    private String createTime;
    private int commentCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getComments() {
        return commentCount;
    }

    public void setComments(int comments) {
        this.commentCount = comments;
    }
}
