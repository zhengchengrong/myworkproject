package com.eaphone.g08android.utils;

import android.util.Log;

import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/6/20 13:01
 * 修改人：Administrator
 * 修改时间：2017/6/20 13:01
 * 修改备注：
 */
public class Json2Object implements Serializable {

    private static String TAG = Json2Object.class.getName();

    public static ParameterizedType type(final Class<?> raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }


    public static Object fromJson(String json, Class<?> clazz) {
        if (json==null||json.length()==0) return null;
        Object object = null;
        try {
            Gson gson = new Gson();
            object = gson.fromJson(json, clazz);
        } catch (Exception e) {
            Log.i(TAG, "Json2Object parse data error");
            e.printStackTrace();
        }
        return object;
    }

}
