package com.eaphone.g08android.utils;

import android.content.Context;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/29 17:01
 * 修改人：Administrator
 * 修改时间：2017/8/29 17:01
 * 修改备注：
 */
public class DensityUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}