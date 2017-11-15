package com.eaphone.g08android.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/9/6 16:55
 * 修改人：Administrator
 * 修改时间：2017/9/6 16:55
 * 修改备注：
 */
public class DeviceDetail implements Serializable{
    /**
     * channels : [{"channelId":"019a3300-62cc-11e7-907b-a6006ad3dba0","name":"1"},{"channelId":"019a43ae-62cc-11e7-907b-a6006ad3dba0","name":"2"}]
     * id : 59a7cc59d8e78f0ba0364f70
     * imageUrl : http://static.xxs120.com/images/device/g08.jpg
     * name : 心相随智能坐便垫
     * product : {"category":"心相随智能坐便垫","name":"心相随智能坐便垫"}
     * serialNumber : 864074020170707
     * thumbnailUrl : http://static.xxs120.com/images/device/g08.jpg
     */
    private String id;
    private String imageUrl;
    private String name;
    private ProductBean product;
    private String serialNumber;
    private String thumbnailUrl;
    private List<ChannelsBean> channels;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public List<ChannelsBean> getChannels() {
        return channels;
    }

    public void setChannels(List<ChannelsBean> channels) {
        this.channels = channels;
    }

    public static class ProductBean implements Serializable{
        /**
         * category : 心相随智能坐便垫
         * name : 心相随智能坐便垫
         */

        private String category;
        private String name;
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class ChannelsBean implements Serializable{
        /**
         * channelId : 019a3300-62cc-11e7-907b-a6006ad3dba0
         * name : 1
         */

        private String channelId;
        private String name;
        /**
         * displayName : 勇
         * userId : 59a0dc3dd8e78f0ba02ecdbe
         */

        private String displayName;
        private String userId;


        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
