package com.eaphone.g08android.ui.personcenter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.eaphone.g08android.R;
import com.eaphone.g08android.mvp.contracts.PassportContracts;
import com.eaphone.g08android.mvp.presenter.ResetPasswordPresenter;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.FormatUtil;
import com.eaphone.g08android.utils.MD5Encryption;
import com.eaphone.g08android.utils.PreferencesUtils;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;

import butterknife.BindView;


public class SetResetActivity extends CoreBaseActivity<ResetPasswordPresenter> implements PassportContracts.ResetPasswordView {

    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_setpwd)
    EditText et_setpwd;
    @BindView(R.id.et_surepwd)
    EditText et_surepwd;
    @BindView(R.id.tv_tip)
    TextView tv_tip;

    @BindView(R.id.btn_submit)
    Button btn_submit;


    @Override
    public int getLayoutId() {
        return R.layout.activity_set_reset;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initBackTitle("密码修改").build();

        initData();

    }

    private void initData() {
        et_username.setText(FormatUtil.getSecretPhone(PreferencesUtils.getSharePreStr(Const.PHONE)));
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_tip.setText("");
                if (MD5Encryption.MD5(et_password.getText().toString()).equals(PreferencesUtils.getSharePreStr(Const.PASSWORD))) {
                    if (et_surepwd.length() >= 6 && et_surepwd.length() <= 20) {
                        if (et_setpwd.getText().toString().trim().equals(et_surepwd.getText().toString().trim())) {

                            mPresenter.reset(MD5Encryption.MD5(et_password.getText().toString()), MD5Encryption.MD5(et_surepwd.getText().toString()));

                        } else {
                            tv_tip.setText("输入的两次密码不一致，请重新输入。");
                        }
                    } else {
                        tv_tip.setText("新密码输入长度不正确，请重新输入。");
                    }
                } else {
                    tv_tip.setText("旧密码输入不正确，请重新输入。");
                }
            }
        });

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
            showToast("修改密码成功");
            PreferencesUtils.removeSharePreStr(Const.TOKEN);
            SetResetActivity.this.finish();
        } else {
            tv_tip.setText(result.getMessage());
        }
    }
}
