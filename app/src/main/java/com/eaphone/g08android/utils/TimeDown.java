package com.eaphone.g08android.utils;

import android.os.CountDownTimer;
import android.widget.Button;

import com.eaphone.g08android.R;

/**
 * 项目名称：心相随
 * 类描述：倒计时
 * 创建人：zlq
 * 创建时间：2017/8/29 18:08
 * 修改人：Administrator
 * 修改时间：2017/8/29 18:08
 * 修改备注：
 */
public class TimeDown extends CountDownTimer {

    private Button view;

    public TimeDown(long millisInFuture, long countDownInterval,Button view) {
        super(millisInFuture, countDownInterval);
        this.view = view;
    }

    @Override
    public void onTick(long l) {
        view.setEnabled(false);
        view.setBackgroundResource(R.drawable.shape_gray);
        view.setText((int) l / 1000 + "秒后获取");
    }

    @Override
    public void onFinish() {
        view.setEnabled(true);
        view.setText("获取验证码");
        view.setBackgroundResource(R.drawable.shape_time_solid5);
    }
}
