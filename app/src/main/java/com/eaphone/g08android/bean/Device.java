package com.eaphone.g08android.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/25 10:17
 * 修改人：Administrator
 * 修改时间：2017/8/25 10:17
 * 修改备注：
 */
public class Device implements Serializable{

    /**
     * deviceId : 3
     * name : test3
     * description : just a test
     * channel : 01
     */

    private String id;
    private String name;
    private String description;
    private String channel;
    private String imageUrl;
    private String thumbnailUrl;
    private String productId;
    private String serialNumber;
    /**
     * category : 心电图仪
     * code : synwingEcg
     * id : ec0a596b-0c46-4425-b6a4-fb1feb35e6f3
     * name : 信汇聚源心电图仪
     * usage : 长按心电记录仪电源开关，绿灯闪烁三下为已开机。将手机蓝牙设备打开，保持手机与心电记录仪的距离在有效范围内（5米）。
     */

    private ProductBean product;


    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    /**
     * name : 血糖
     * sensorId : 1212-efde-1523-785feabcd1234Sensor
     * type : 21
     */


    private List<SensorsBean> sensors;


    public String getDeviceId() {
        return id;
    }

    public void setDeviceId(String deviceId) {
        this.id = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public List<SensorsBean> getSensors() {
        return sensors;
    }

    public void setSensors(List<SensorsBean> sensors) {
        this.sensors = sensors;
    }

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }

    public static class SensorsBean {
        private String name;
        private String sensorId;
        private String type;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSensorId() {
            return sensorId;
        }

        public void setSensorId(String sensorId) {
            this.sensorId = sensorId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class ProductBean {
        private String category;
        private String code;
        private String id;
        private String name;
        private String usage;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsage() {
            return usage == null ? "" : usage;
        }

        public void setUsage(String usage) {
            this.usage = usage;
        }
    }

}
