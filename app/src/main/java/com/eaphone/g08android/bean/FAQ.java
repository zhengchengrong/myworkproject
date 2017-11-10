package com.eaphone.g08android.bean;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/9/5 18:14
 * 修改人：Administrator
 * 修改时间：2017/9/5 18:14
 * 修改备注：
 */
public class FAQ {


    /**
     * id : xxxxxxx
     * question : 如何补寄发票
     * createTime : 2017-08-25T14:37:04+08:00
     */

    private String id;
    private String question;
    private String createTime;
    private String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
