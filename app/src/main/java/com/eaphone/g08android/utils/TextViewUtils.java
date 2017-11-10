package com.eaphone.g08android.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/8/25 17:29
 * 修改人：Administrator
 * 修改时间：2017/8/25 17:29
 * 修改备注：
 */
public class TextViewUtils  {

    public static String setTextColor( String color, String text) {
        //String s = "<font style=\"font-size:" + px + "px\" color=\""+color+"\">" + text +"</font>";
        String s = "<font color=\""+color+"\"><big>" + text +"</big></font>";
        Log.e("TextViewUtils",s);
//        Spanned spanned = Html.fromHtml(s);
        return s;
    }



    public static void setDrawableTop(Context context,int top, View view) {
        Drawable topDrawable = context.getResources().getDrawable(top);
        topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
        if (view instanceof TextView) {
            ((TextView) view).setCompoundDrawables(null, topDrawable, null, null);
        }
        if (view instanceof Button) {
            ((Button) view).setCompoundDrawables(null, topDrawable, null, null);
        }
    }

    public static void setDrawableLeft(Context context,int left, View view) {
        Drawable leftDrawable = context.getResources().getDrawable(left);
        leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
        if (view instanceof TextView) {
            ((TextView) view).setCompoundDrawables(leftDrawable, null, null, null);
        }
        if (view instanceof Button) {
            ((Button) view).setCompoundDrawables(leftDrawable, null, null, null);
        }
    }


    public static void setDrawableRight(Context context,int right, View view) {
        Drawable rightDrawable = context.getResources().getDrawable(right);
        rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
        if (view instanceof TextView) {
            ((TextView) view).setCompoundDrawables(null, null, rightDrawable, null);
        }
        if (view instanceof Button) {
            ((Button) view).setCompoundDrawables(null, null, rightDrawable, null);
        }
    }

    public static void setDrawableButtom(Context context,int buttom, View view) {
        Drawable buttomDrawable = context.getResources().getDrawable(buttom);
        buttomDrawable.setBounds(0, 0, buttomDrawable.getMinimumWidth(), buttomDrawable.getMinimumHeight());
        if (view instanceof TextView) {
            ((TextView) view).setCompoundDrawables(null, null, null, buttomDrawable);
        }
        if (view instanceof Button) {
            ((Button) view).setCompoundDrawables(null, null, null, buttomDrawable);
        }
    }

}
