package com.eaphone.g08android.bean;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/25 16:47
 * 修改人：Administrator
 * 修改时间：2017/8/25 16:47
 * 修改备注：
 */
public class InfoComment {
    /**
     * commentBy : {"id":"59ae4673d8e78f0ba008c9bf","name":"许猫猫","phone":"15920305675","role":"USER"}
     * commentContent : Lalalla
     * createTime : 2017-09-14T10:31:18+08:00
     * floor : 10
     * id : 59b9e9f6d8e78f0ba0efc6c0
     * newsId : 58a10f2ad461680730fd8682
     * parentNewsComment : {"commentBy":{"id":"59ae791ed8e78f0ba076af44","name":"大人物","phone":"15302212087","role":"USER"},"commentContent":"你好","createTime":"2017-09-13T16:56:58+08:00","floor":9,"id":"59b8f2dad8e78f0ba0efc6bf"}
     * zan : false
     * zanCount : 0
     */
    private CommentByBean commentBy;
    private String commentContent;
    private String createTime;
    private int floor;
    private String id;
    private String newsId;
    private ParentNewsCommentBean parentNewsComment;
    private boolean zan;
    private int zanCount;

    public CommentByBean getCommentBy() {
        return commentBy;
    }

    public void setCommentBy(CommentByBean commentBy) {
        this.commentBy = commentBy;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public ParentNewsCommentBean getParentNewsComment() {
        return parentNewsComment;
    }

    public void setParentNewsComment(ParentNewsCommentBean parentNewsComment) {
        this.parentNewsComment = parentNewsComment;
    }

    public boolean isZan() {
        return zan;
    }

    public void setZan(boolean zan) {
        this.zan = zan;
    }

    public int getZanCount() {
        return zanCount;
    }

    public void setZanCount(int zanCount) {
        this.zanCount = zanCount;
    }

    public static class CommentByBean {
        /**
         * id : 59ae4673d8e78f0ba008c9bf
         * name : 许猫猫
         * phone : 15920305675
         * role : USER
         */

        private String id;
        private String name;
        private String phone;
        private String role;

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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }

    public static class ParentNewsCommentBean {
        /**
         * commentBy : {"id":"59ae791ed8e78f0ba076af44","name":"大人物","phone":"15302212087","role":"USER"}
         * commentContent : 你好
         * createTime : 2017-09-13T16:56:58+08:00
         * floor : 9
         * id : 59b8f2dad8e78f0ba0efc6bf
         */

        private CommentByBeanX commentBy;
        private String commentContent;
        private String createTime;
        private int floor;
        private String id;

        public CommentByBeanX getCommentBy() {
            return commentBy;
        }

        public void setCommentBy(CommentByBeanX commentBy) {
            this.commentBy = commentBy;
        }

        public String getCommentContent() {
            return commentContent;
        }

        public void setCommentContent(String commentContent) {
            this.commentContent = commentContent;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getFloor() {
            return floor;
        }

        public void setFloor(int floor) {
            this.floor = floor;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public static class CommentByBeanX {
            /**
             * id : 59ae791ed8e78f0ba076af44
             * name : 大人物
             * phone : 15302212087
             * role : USER
             */

            private String id;
            private String name;
            private String phone;
            private String role;

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

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }
        }
    }
}
