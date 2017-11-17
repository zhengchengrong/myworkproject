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
public class ZhiboInfo {


    /**
     * banner : http://xxx.com/image2.png
     * createTime : 2017-11-15T16:05:47+08:00
     * description : <ul>直播1 的内容</ul>
     * id : 5a0b9bc4732d00043f24bfec
     * image : http://xxx.com/image1.jpg
     * title : 测试直播1
     */

    private String banner;
    private String createTime;
    private String description;
    private String id;
    private String image;
    private String title;

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
