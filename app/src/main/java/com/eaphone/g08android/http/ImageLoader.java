package com.eaphone.g08android.http;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.eaphone.g08android.R;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.PreferencesUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Created by Administrator on 2016/4/21.
 */
public class ImageLoader {

    private static Context contextInstance;


    public static void init(Context context) {
        contextInstance = context;
    }


    public static void displayImage(String url, ImageView imageView, int resId) {
        int a = PreferencesUtils.getSharePreInt(Const.VERSION_CACHE);
        Glide.with(contextInstance).load(url)
                .signature(new StringSignature(String.valueOf(a)))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(resId)
                .dontAnimate()
                .into(imageView);

    }

    public static void displayImage(String url, ImageView imageView) {
        displayImage(url, imageView, R.mipmap.ic_default);
    }

    public static void displayLocalImage(Uri uri, ImageView imageView) {
        Glide.with(contextInstance)
                .load(uri)
                .asBitmap()
                .centerCrop()
                .into(imageView);
    }

    /**
     * 上传图片
     *
     * @return
     */
    public static MultipartBody.Part upLoadImage(Uri uri) {

        File file = new File(uri.getEncodedPath());

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

//        MultipartBody.Builder builder = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM);
//        builder.addFormDataPart("file", file.getName(), requestFile);
//        MultipartBody body =  builder.build();

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        return body;
    }


}
