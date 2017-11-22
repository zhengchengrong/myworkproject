package com.eaphone.g08android.bean;

import java.util.List;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/11/20 12:01
 * 修改人：Administrator
 * 修改时间：2017/11/20 12:01
 * 修改备注：
 */
public class LiveHome {

    private List<BannersBean> banners;
    private List<ContentBean> content;

    public List<BannersBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannersBean> banners) {
        this.banners = banners;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class BannersBean {
        /**
         * id :
         * banner : 轮播图的图片
         * url : 轮播图的地址
         */

        private String id;
        private String banner;
        private String url;

        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class ContentBean {
        /**
         * id :
         * banner : 分类的图片
         * url : eaphonejiankang://news
         */

        private String id;
        private String banner;
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
