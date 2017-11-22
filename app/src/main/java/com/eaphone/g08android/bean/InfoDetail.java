package com.eaphone.g08android.bean;

import java.util.List;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/25 14:18
 * 修改人：Administrator
 * 修改时间：2017/8/25 14:18
 * 修改备注：
 */
public class InfoDetail {

    /**
     * id : xxxxxxx
     * title : 大医院实名制就医来了！
     * image : http://xxx.com/xxx/xxx/xxx.jpg
     * banner : http://xxx.com/xxx/xxx/xxx.jpg
     * content : 资讯的正文
     * createTime : 2016-07-26T19:09:22+08:00
     * comments : 2
     * authority : ["doctor","team","user"]
     */

    private String id;
    private String title;
    private String image;
    private String banner;
    private String content;
    private String createTime;
    private String releaseName;
    private int commentCount;
    private List<String> authority;
    private boolean collect;

    private String createBy;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

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

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getReleaseName() {
        return releaseName;
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public List<String> getAuthority() {
        return authority;
    }

    public void setAuthority(List<String> authority) {
        this.authority = authority;
    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }
}
