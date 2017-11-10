package com.eaphone.g08android.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.RegistEnity;
import com.eaphone.g08android.mvp.contracts.PassportContracts;
import com.eaphone.g08android.mvp.presenter.RegistPresenter;
import com.eaphone.g08android.ui.personcenter.WebActivity;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.ErrorCoreUtils;
import com.eaphone.g08android.utils.MD5Encryption;
import com.eaphone.g08android.utils.PreferencesUtils;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.utils.ToastUtils;

import butterknife.BindView;


/**
 * Created by Administrator on 2016/4/14.
 */
public class RegisterNextActivity extends CoreBaseActivity<RegistPresenter> implements PassportContracts.RegistView {

    @BindView(R.id.btn_next)
    Button btn_next;

    @BindView(R.id.tv_get_code)
    TextView tv_get_code;

    @BindView(R.id.tv_tip)
    TextView tv_tip;

    @BindView(R.id.et_username)
    EditText et_username;

    @BindView(R.id.edt_code)
    EditText edt_code;

    @BindView(R.id.et_password)
    EditText et_password;

    @BindView(R.id.et_sure_password)
    EditText et_sure_password;

    @BindView(R.id.checkbox)
    CheckBox checkbox;

    @BindView(R.id.tv_agreement)
    TextView tv_agreement;

    private CountDownTimer countDownTimer;
    private String phone;

    @Override
    public int getLayoutId() {
        return R.layout.login_registnext_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        phone = getIntent().getExtras().getString("phone");
        findView();
        initBackTitle("设置登录密码").build();
    }

    private void findView() {

        tv_agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title", "易风健康注册协议");
                bundle.putString("url", "https://static.xxs120.com/g08/eula/register-agreement.html");
                startActivity(WebActivity.class, bundle);

            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_tip.setText("");

                if (TextUtils.isEmpty(edt_code.getText().toString())) {
                    showToast(getResources().getString(R.string.input_code));
                    return;
                }

                if (edt_code.getText().toString().trim().length() != 6) {
                    showToast(getResources().getString(R.string.input_wrong_code));
                    return;
                }
                if (!checkbox.isChecked()) {
                    showToast(getResources().getString(R.string.check_vip));
                    return;
                }
                if (TextUtils.isEmpty(et_password.getText())) {
                    showToast(getResources().getString(R.string.input_pwd));
                    return;
                }

                if (TextUtils.isEmpty(et_sure_password.getText())) {
                    showToast(getResources().getString(R.string.input_pwd_again));
                    return;
                }
                if (et_password.getText().toString().trim().length() >= 6 && et_password.getText().toString().trim().length() <= 20) {
                } else {
                    showToast(getResources().getString(R.string.input_wrong_pwd));
                    return;
                }

                if (!et_password.getText().toString().equals(et_sure_password.getText().toString())) {
                    showToast(getResources().getString(R.string.pwd_sure));
                    return;
                }

                register();
                btn_next.setEnabled(false);

            }
        });
        tv_get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifycode();
            }
        });
    }

    //注册
    private void register() {
        RegistEnity enity = new RegistEnity();
        enity.setName(phone);
        enity.setPassword(MD5Encryption.MD5(et_password.getText().toString().trim()));
        enity.setPhone(phone);
        enity.setRole("user");
        enity.setAuthcode(edt_code.getText().toString().trim());
        mPresenter.regist(enity);
    }
    //获取验证码
    private void verifycode() {
        tv_get_code.setEnabled(false);
        tv_get_code.setBackgroundResource(R.drawable.shape_greybg_solid5);
        mPresenter.verifyCode(phone);
        time(60);
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
        showToast(msg);
    }

    @Override
    public void getVerifyCode(ResultBase resultBase) {
        if (!resultBase.isSuccess()) {
            ToastUtils.showToast(mContext, resultBase.getMessage());
        }
    }

    @Override
    public void getRegist(ResultBase result) {
        btn_next.setEnabled(true);
        if (result.isSuccess()) {
            Bundle bundle = new Bundle();
            bundle.putString("title", "注册成功");
            bundle.putString("content", "恭喜您注册成功！");
            PreferencesUtils.putSharePre(Const.PHONE, phone);

            startActivity(SuccessActivity.class, bundle);
        } else {
            showToast(ErrorCoreUtils.dispalyCode(result.getErrcode()));
        }
    }
}
