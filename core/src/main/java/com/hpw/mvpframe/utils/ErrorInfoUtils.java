package com.hpw.mvpframe.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.hpw.mvpframe.base.ResultBase;

import java.net.UnknownHostException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;

public class ErrorInfoUtils {

    /**
     * 解析服务器错误信息
     */
    public static String parseHttpErrorInfo(Throwable throwable) {
        String errorInfo = throwable.getMessage();

        if (throwable instanceof HttpException) {
            // 如果是Retrofit的Http错误,则转换类型,获取信息
            HttpException exception = (HttpException) throwable;
            ResponseBody responseBody = exception.response().errorBody();
            MediaType type = responseBody.contentType();

            // 如果是application/json类型数据,则解析返回内容
            if (type != null) {

                if ("application".equals(type.type()) && "json".equals(type.subtype())) {
                    try {
                        ResultBase errorResponse = new Gson().fromJson(
                                responseBody.string(), ResultBase.class);

                        errorInfo = errorResponse.getMessage();
                    } catch (Exception e) {
//                    e.printStackTrace();
                    }
                }
            }
        } else if (throwable instanceof UnknownHostException) {
            errorInfo = "无法连接到服务器";
        }else  {
            Log.e("ErrorInfoUtils",errorInfo);
            errorInfo = "数据出错啦";
        }

        return errorInfo;
    }
}
