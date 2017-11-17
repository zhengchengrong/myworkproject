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
     * banner : http://xxx.com/image2.png
     * classes : [{"bytes":12151325,"id":"5a0b9c0f732d00043f24bfed","title":"测试课程1","total_seconds":64,"url":"http://192.168.1.238:8048/common/v1/file/5a0bde0165f8f81e9cf257d4.mp3"},{"bytes":1241241241,"id":"5a0b9ced732d00043f24bfef","title":"测试课程2","total_seconds":128,"url":"http://192.168.1.238:8048/common/v1/file/5a0bde0165f8f81e9cf257d4.mp3"}]
     * id : 5a0b9bc4732d00043f24bfec
     * image : http://xxx.com/image1.jpg
     * title : 测试直播1
     */
    private String banner;
    private String id;
    private String image;
    private String title;
    private List<ClassesBean> classes;

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
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
         * bytes : 12151325
         * id : 5a0b9c0f732d00043f24bfed
         * title : 测试课程1
         * total_seconds : 64
         * url : http://192.168.1.238:8048/common/v1/file/5a0bde0165f8f81e9cf257d4.mp3
         */

        private int bytes;
        private String id;
        private String title;
        private int total_seconds;
        private String url;
        private String progress="0%";
        private boolean isFouce;

        public boolean isFouce() {
            return isFouce;
        }

        public void setFouce(boolean fouce) {
            isFouce = fouce;
        }

        public String getProgress() {
            return progress;
        }

        public void setProgress(String progress) {
            this.progress = progress;
        }


        public int getBytes() {
            return bytes;
        }

        public void setBytes(int bytes) {
            this.bytes = bytes;
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
