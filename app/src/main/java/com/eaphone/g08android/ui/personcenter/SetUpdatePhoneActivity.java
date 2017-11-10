package com.eaphone.g08android.ui.personcenter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.eaphone.g08android.R;
import com.eaphone.g08android.mvp.contracts.PassportContracts;
import com.eaphone.g08android.mvp.presenter.UpdatePhonePresenter;
import com.eaphone.g08android.ui.login.LoginActivity;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.ErrorCoreUtils;
import com.eaphone.g08android.utils.FormatUtil;
import com.eaphone.g08android.utils.MD5Encryption;
import com.eaphone.g08android.utils.PreferencesUtils;
import com.eaphone.g08android.utils.TimeDown;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;

import butterknife.BindView;


public class SetUpdatePhoneActivity extends CoreBaseActivity<UpdatePhonePresenter> implements View.OnClickListener, PassportContracts.UpdatePhoneView {
    @BindView(R.id.btn_send_code)
    Button btn_send_code;

    @BindView(R.id.btn_sure)
    Button btn_sure;

    @BindView(R.id.edt_password)
    EditText edt_password;

    @BindView(R.id.edt_phone)
    EditText edt_phone;

    @BindView(R.id.edt_code)
    EditText edt_code;


    private TimeDown countDownTimer;

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_update_phone;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initBackTitle("更换绑定").build();
        btn_send_code.setOnClickListener(this);
        btn_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_code:
                if (TextUtils.isEmpty(edt_phone.getText())) {
                    showToast("请输入手机号码");
                    return;
                }

                if (!FormatUtil.isMobileNO(edt_phone.getText().toString())) {
                    showToast("请输入正确手机号码");
                    return;
                }
                mPresenter.verifyCode(edt_phone.getText().toString());
                time();
                break;

            case R.id.btn_sure:
                if (edt_password.getText().toString().trim().length() >= 6 && edt_password.getText().toString().trim().length() <= 20) {
                    if (FormatUtil.isMobileNO(edt_phone.getText().toString())) {
                        if (edt_code.getText().toString().trim().length() == 6) {
                            mPresenter.reset(MD5Encryption.MD5(edt_password.getText().toString()), edt_phone.getText().toString(), edt_code.getText().toString().trim());
                        } else {
                            showToast("验证码格式有误，请重新输入。");
                        }
                    } else {
                        showToast("输入的手机号格式不正确，请重新输入");
                    }
                } else {
                    showToast("密码长度不正确，请重新输入。");
                }
                break;
        }
    }

    private void time() {
        countDownTimer = new TimeDown(60 * 1000, 1000, btn_send_code);
        countDownTimer.start();
    }


    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void showError(String msg) {
        showToast(msg);
    }

    @Override
    public void getReset(ResultBase result) {
        if (result.isSuccess()) {
            PreferencesUtils.putSharePre(Const.PHONE, edt_phone.getText().toString());
            startActivity(LoginActivity.class);
            showToast("绑定更换成功");
            SetUpdatePhoneActivity.this.finish();
        } else {
            showToast(ErrorCoreUtils.dispalyCode(result.getErrcode()));
        }
    }

    @Override
    public void getCode(ResultBase result) {

    }

}
