package com.eaphone.g08android.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.ResetPwdEnity;
import com.eaphone.g08android.mvp.contracts.PassportContracts;
import com.eaphone.g08android.mvp.presenter.ResetPresenter;
import com.eaphone.g08android.utils.ErrorCoreUtils;
import com.eaphone.g08android.utils.FormatUtil;
import com.eaphone.g08android.utils.MD5Encryption;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.utils.ToastUtils;

import butterknife.BindView;


/**
 * Created by Administrator on 2016/4/20.
 * 找回密码
 */
public class ResetNextActivity extends CoreBaseActivity<ResetPresenter> implements View.OnClickListener, PassportContracts.ResetView {

    @BindView(R.id.btn_next)
    Button btn_next;

    @BindView(R.id.tv_tip)
    TextView tv_tip;

    @BindView(R.id.edt_code)
    EditText edt_code;

    @BindView(R.id.et_password)
    EditText et_password;

    @BindView(R.id.et_username)
    EditText et_username;

    @BindView(R.id.et_sure_password)
    EditText et_sure_password;

    @BindView(R.id.tv_get_code)
    TextView tv_get_code;

    private CountDownTimer countDownTimer;
    private String phone;
    private String password;

    @Override
    public int getLayoutId() {
        return R.layout.login_resetpwd_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initBackTitle("找回密码").build();
        btn_next.setOnClickListener(this);
        tv_get_code.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                tv_tip.setText("");
                phone = et_username.getText().toString();
                password = et_password.getText().toString().trim();
                if (FormatUtil.isMobileNO(et_username.getText().toString())) {
                    if (edt_code.getText().toString().trim().length() == 6) {
                        if (password.length() >= 6 && password.length() <= 20) {
                            if (password.equals(et_sure_password.getText().toString().trim())) {
                                resetPassword();
                            } else {
                                tv_tip.setVisibility(View.VISIBLE);
                                tv_tip.setText("输入的两次密码不一致，请重新输入。");
                            }
                        } else {
                            tv_tip.setVisibility(View.VISIBLE);
                            tv_tip.setText("密码长度不正确，请重新输入。");
                        }

                    } else {
                        tv_tip.setVisibility(View.VISIBLE);
                        tv_tip.setText("验证码格式有误，请重新输入。");
                    }
                } else {
                    tv_tip.setVisibility(View.VISIBLE);
                    tv_tip.setText("手机号码格式不正确，请重新输入");
                }

                break;
            case R.id.tv_get_code:
                if (FormatUtil.isMobileNO(et_username.getText().toString())) {
                    phone = et_username.getText().toString();
                    verifycode();
                } else {
                    ToastUtils.showToast(mContext, "手机号码格式不正确，请重新输入");
                }
                break;
            default:
                break;
        }
    }

    //获取验证码
    private void verifycode() {
        mPresenter.resetCode(phone);
        tv_get_code.setEnabled(false);
        tv_get_code.setBackgroundResource(R.drawable.shape_greybg_solid5);
        time(60);

    }

    //重置密码
    private void resetPassword() {
        ResetPwdEnity reset = new ResetPwdEnity();
        reset.setAuthcode(edt_code.getText().toString());
        reset.setPhone(phone);
        reset.setNewPassword(MD5Encryption.MD5(password));
        reset.setRole("user");
        mPresenter.reset(reset);
    }

    private void time(final int second) {

        countDownTimer = new CountDownTimer(second * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                tv_get_code.setText((int) millisUntilFinished / 1000 + "秒后获取");
            }

            public void onFinish() {
                tv_get_code.setEnabled(true);
                tv_get_code.setText("获取验证码");
                tv_get_code.setBackgroundResource(R.drawable.shape_time_solid5);
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null)
            countDownTimer.cancel();
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void showError(String msg) {
        ToastUtils.showToast(mContext, msg);
    }

    @Override
    public void getVerifyCode(ResultBase resultBase) {

    }

    @Override
    public void getReset(ResultBase result) {
        if (result.isSuccess()) {
            Bundle bundle = new Bundle();
            bundle.putString("title", "找回成功");
            bundle.putString("content", "您的密码已成功找回!");
            startActivity(SuccessActivity.class, bundle);
        } else {
            showToast(ErrorCoreUtils.dispalyCode(result.getErrcode()));
        }
    }
}
