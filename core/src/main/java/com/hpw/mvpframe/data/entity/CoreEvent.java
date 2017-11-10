package com.hpw.mvpframe.data.entity;

/**
 * Created by zlq on 2017/9/9.
 */

public class CoreEvent<T> {

    private int code;
    private T data;

    public CoreEvent() {
    }

    public CoreEvent(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
