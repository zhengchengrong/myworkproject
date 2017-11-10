package com.eaphone.g08android.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/26 11:04
 * 修改人：Administrator
 * 修改时间：2017/8/26 11:04
 * 修改备注：
 */
@Entity
public class NewsType {

    /**
     * id : xxxxxxx
     * name : 血压
     * createTime : 2016-07-26T19:09:22+08:00
     */

//    @Id(autoincrement = true)
//    private long newTypeId;

    @Index(unique = true)
    private String id;
    private String name;
    private String createTime;



    @Generated(hash = 1987521192)
    public NewsType(String id, String name, String createTime) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
    }

    @Generated(hash = 1722088998)
    public NewsType() {
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
