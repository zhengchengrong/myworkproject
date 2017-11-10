package com.eaphone.g08android.http;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.PreferencesUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by zlq on 2017/8/27.
 */

public class TokenInterceptor implements Interceptor {

    @NonNull
    private static String _parseParams(RequestBody body, Buffer requestBuffer) throws UnsupportedEncodingException {
        if (body.contentType() != null && !body.contentType().toString().contains("multipart")) {
            return URLDecoder.decode(requestBuffer.readUtf8(), "UTF-8");
        }
        return "null";
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();


        if (TextUtils.isEmpty(PreferencesUtils.getSharePreStr(Const.TOKEN))) {//如果没有token则直接请求
            return chain.proceed(original);
        } else if (original.header("Authorization") != null) {//表示已添加token
            return chain.proceed(original);
        } else {//未添加token则添加
            Request.Builder requestBuilder = original.newBuilder()
                    .addHeader("Authorization", "Bearer " + PreferencesUtils.getSharePreStr(Const.TOKEN));
            Request request = requestBuilder.build();
            return chain.proceed(request);
        }

    }
}
