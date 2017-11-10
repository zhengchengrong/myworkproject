package com.hpw.mvpframe.base;

import java.io.Serializable;

public class ResultBase<T> implements Serializable {


    /**
     * success : true
     * message:
     * errcode:
     */
    private boolean success;
    private int errcode;
    private String message;
    private int page_index;
    private int page_size;
    private int total_record;
    private T data;



    public int getPage_index() {
        return page_index;
    }

    public void setPage_index(int page_index) {
        this.page_index = page_index;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public int getTotal_record() {
        return total_record;
    }

    public void setTotal_record(int total_record) {
        this.total_record = total_record;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ResultBase{" +
                "success=" + success +
                ", errcode=" + errcode +
                ", message='" + message + '\'' +
                ", page_index=" + page_index +
                ", page_size=" + page_size +
                ", total_record=" + total_record +
                ", data=" + data +
                '}';
    }

}
