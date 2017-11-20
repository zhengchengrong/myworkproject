package com.eaphone.g08android.bean;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
     * title : 方法：一万次试验法则
     * image : https://xxx/xxx/xxx/xxx.jpg
     * banner : http://xxx/xxx/xxx/xxx.jpg
     * description_url : http://xxx/xxx/xxx/xxx/xxx.html
     * classes : [{"id":"","title":"方法：一万次试验法则（一）","createTime":"2017-11-03T16:48:20","description_url":"http://xxx/xxx/xxx/xxx/xxx.html","total_seconds":657,"bytes":1235763,"url":"http://192.168.1.238:8048/common/v1/file/5a0bde0165f8f81e9cf257d4.mp3","progress":{"finished":false,"last_seconds":428}},{"id":"","title":"方法：一万次试验法则（二）","createTime":"2017-11-13T16:48:20","description_url":"http://xxx/xxx/xxx/xxx/xxx.html","total_seconds":657,"bytes":1235763,"url":"http://192.168.1.238:8048/common/v1/file/5a0bde0165f8f81e9cf257d4.mp3","progress":{"finished":false,"last_seconds":428}},{"id":"","title":"方法：一万次试验法则（三）","createTime":"2017-11-23T16:48:20","description_url":"http://xxx/xxx/xxx/xxx/xxx.html","total_seconds":657,"bytes":1235763,"url":"http://192.168.1.238:8048/common/v1/file/5a0bde0165f8f81e9cf257d4.mp3","progress":{"finished":true}}]
     */

    private String title;
    private String image;
    private String banner;
    private String description_url;
    private List<ClassesBean> classes;

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

    public String getDescription_url() {
        return description_url;
    }

    public void setDescription_url(String description_url) {
        this.description_url = description_url;
    }

    public List<ClassesBean> getClasses() {
        return classes;
    }

    public void setClasses(List<ClassesBean> classes) {
        this.classes = classes;
    }

    public static class ClassesBean implements Comparable<ClassesBean>{
        /**
         * id :
         * title : 方法：一万次试验法则（一）
         * createTime : 2017-11-03T16:48:20
         * description_url : http://xxx/xxx/xxx/xxx/xxx.html
         * total_seconds : 657
         * bytes : 1235763
         * url : http://192.168.1.238:8048/common/v1/file/5a0bde0165f8f81e9cf257d4.mp3
         * progress : {"finished":false,"last_seconds":428}
         */

        private String id;
        private String title;
        private String createTime;
        private String description_url;
        private int total_seconds;
        private int bytes;
        private String url;
        private ProgressBean progress;

        private boolean isFouce;
        private int pauseNum=0;
        private String locProgress;
        private int jinduProgress;

        public int getJinduProgress() {
            return jinduProgress;
        }

        public void setJinduProgress(int jinduProgress) {
            this.jinduProgress = jinduProgress;
        }

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

        public int getTotal_seconds() {
            return total_seconds;
        }

        public void setTotal_seconds(int total_seconds) {
            this.total_seconds = total_seconds;
        }

        public int getBytes() {
            return bytes;
        }

        public void setBytes(int bytes) {
            this.bytes = bytes;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public ProgressBean getProgress() {
            return progress;
        }

        public void setProgress(ProgressBean progress) {
            this.progress = progress;
        }

        @Override
        public int compareTo(@NonNull ClassesBean o) {
              return (int) (getStringToDate(this.getCreateTime()) - getStringToDate(o
                    .getCreateTime()));
        }
        /* 将字符串转为时间戳 */
        public static long getStringToDate(String time) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = new Date();
            try {
                date = sdf.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date.getTime() / 100000;
        }
        public static class ProgressBean {
            /**
             * finished : false
             * last_seconds : 428
             */

            private boolean finished;
            private int last_seconds;

            public boolean isFinished() {
                return finished;
            }

            public void setFinished(boolean finished) {
                this.finished = finished;
            }

            public int getLast_seconds() {
                return last_seconds;
            }

            public void setLast_seconds(int last_seconds) {
                this.last_seconds = last_seconds;
            }
        }
    }
}
