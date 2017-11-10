package com.eaphone.g08android.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eaphone.g08android.R;

/**
 * 项目名称：心相随
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/9/6 15:42
 * 修改人：Administrator
 * 修改时间：2017/9/6 15:42
 * 修改备注：
 */
public class EmptyView extends LinearLayout {

    private Context context;
    private ImageView imageView;
    private TextView tv_empty;
    private View view;
    private TextView tv_text;
    private TextView tv_content;

    public EmptyView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        view = LayoutInflater.from(context).inflate(R.layout.include_empty, null);
        imageView = (ImageView) view.findViewById(R.id.iv_empty);
        tv_empty = (TextView) view.findViewById(R.id.tv_empty_);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_text = (TextView) view.findViewById(R.id.tv_text);
    }

    public void setImageResouce(@DrawableRes int resouce) {
        imageView.setImageResource(resouce);
    }

    public void setText(String text) {
        tv_empty.setText(text);
    }

    public View getView(){
        return view;
    }

    public void setVisibility(){
        tv_text.setVisibility(VISIBLE);
        tv_content.setVisibility(VISIBLE);
    }
}
