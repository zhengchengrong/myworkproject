package com.eaphone.g08android.widget;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.eaphone.g08android.R;

import cn.qqtheme.framework.picker.OptionPicker;

/**
 * 自定义顶部及底部
 */
public class CustomHeaderAndFooterPicker extends OptionPicker {

    public CustomHeaderAndFooterPicker(Activity activity, String[] strings) {
        super(activity, strings);
        setTitleText("请选择");
        setSelectedItem(strings[0]);
    }

    @Override
    public void show() {
        super.show();

    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Nullable
    @Override
    protected View makeHeaderView() {
        View view = LayoutInflater.from(activity).inflate(R.layout.picker_header, null);
//        TextView titleView = (TextView) view.findViewById(R.id.picker_title);
//        titleView.setText(titleText);
        TextView tv_sure = (TextView) view.findViewById(R.id.tv_sure);
        tv_sure.setText(submitText);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onSubmit();
            }
        });
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_cancel.setText(cancelText);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onCancel();
            }
        });
        return view;
    }

    @Nullable
    @Override
    protected View makeFooterView() {
        View view = LayoutInflater.from(activity).inflate(R.layout.picker_footer, null);

        return view;
    }

}
