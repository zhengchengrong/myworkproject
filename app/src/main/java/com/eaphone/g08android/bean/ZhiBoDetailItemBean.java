package com.eaphone.g08android.bean;

import java.util.List;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/11/16 14:46
 * 修改人：Administrator
 * 修改时间：2017/11/16 14:46
 * 修改备注：
 */
public class ZhiBoDetailItemBean {


    /**
     * classes : [{"description_url":"http://192.168.1.238:8048/news/live/class/5a14e4cfd8e78f5ff7f3af9c/","id":"5a14e4cfd8e78f5ff7f3af9c","title":"你好旧时光","total_seconds":60,"url":"http://music.163.com/song/media/outer/url?id=407459645.mp3"},{"description_url":"https://www.baidu.com/?tn=57095150_2_oem_dg","id":"5a13f51fd8e78f5ff75ef2b8","title":"test2","total_seconds":30,"url":"http://music.163.com/song/media/outer/url?id=518896134.mp3"},{"description_url":"http://192.168.1.238:8048/news/live/class/5a13ed14d8e78f5ff75ef2b4/","id":"5a13ed14d8e78f5ff75ef2b4","title":"直播视频3","total_seconds":60,"url":"http://music.163.com/song/media/outer/url?id=407459645.mp3"},{"description_url":"http://192.168.1.238:8048/news/live/class/5a13f00bd8e78f5ff75ef2b6/","id":"5a13f00bd8e78f5ff75ef2b6","title":"465464565645646","total_seconds":1,"url":"http://music.163.com/song/media/outer/url?id=518896134.mp3"}]
     * description_url : http://192.168.1.238:8048/news/live/5a0b9bc4732d00043f24bfec/
     * id : 5a0b9bc4732d00043f24bfec
     * image : http://192.168.1.238:8048/common/v1/image/57d288c9ce6a11e78b8a1f6cb43f7feb.jpg
     * title : 固定的指标类别
     */

    private String description_url;
    private String id;
    private String image;
    private String title;
    private List<ClassesBean> classes;

    public String getDescription_url() {
        return description_url;
    }

    public void setDescription_url(String description_url) {
        this.description_url = description_url;
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

    public List<ClassesBean> getClasses() {
        return classes;
    }

    public void setClasses(List<ClassesBean> classes) {
        this.classes = classes;
    }

    public static class ClassesBean {
        /**
         * description_url : http://192.168.1.238:8048/news/live/class/5a14e4cfd8e78f5ff7f3af9c/
         * id : 5a14e4cfd8e78f5ff7f3af9c
         * title : 你好旧时光
         * total_seconds : 60
         * url : http://music.163.com/song/media/outer/url?id=407459645.mp3
         */

        private String description_url;
        private String id;
        private String title;
        private int total_seconds;
        private String url;


        private boolean isFouce;

        public boolean isFouce() {
            return isFouce;
        }

        public void setFouce(boolean fouce) {
            isFouce = fouce;
        }

        public int getPauseNum() {
            return pauseNum;
        }

        public void setPauseNum(int pauseNum) {
            this.pauseNum = pauseNum;
        }

        public String getLocProgress() {
            return locProgress;
        }

        public void setLocProgress(String locProgress) {
            this.locProgress = locProgress;
        }

        public int getJinduProgress() {
            return jinduProgress;
        }

        public void setJinduProgress(int jinduProgress) {
            this.jinduProgress = jinduProgress;
        }

        private int pauseNum=0;
        private String locProgress;
        private int jinduProgress;
        private String createTime;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDescription_url() {
            return description_url;
        }

        public void setDescription_url(String description_url) {
            this.description_url = description_url;
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

        public int getTotal_seconds() {
            return total_seconds;
        }

        public void setTotal_seconds(int total_seconds) {
            this.total_seconds = total_seconds;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
