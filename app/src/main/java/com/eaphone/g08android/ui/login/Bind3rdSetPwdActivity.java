package com.eaphone.g08android.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.eaphone.g08android.R;
import com.eaphone.g08android.bean.ResultQQMsg;
import com.eaphone.g08android.mvp.contracts.PassportContracts;
import com.eaphone.g08android.mvp.presenter.BindInfoPresenter;
import com.eaphone.g08android.ui.MainActivity;
import com.eaphone.g08android.utils.Const;
import com.eaphone.g08android.utils.PreferencesUtils;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;
import com.hpw.mvpframe.utils.ToastUtils;

import butterknife.BindView;


/**
 * 项目名称：瘤盾
 * 类描述：
 * 创建人：zlq
 * 创建时间：2017/5/5 16:41
 * 修改人：Administrator
 * 修改时间：2017/5/5 16:41
 * 修改备注：
 */
public class Bind3rdSetPwdActivity extends CoreBaseActivity<BindInfoPresenter> implements PassportContracts.Bind3rdInfoView {

    @BindView(R.id.et_password)
    EditText et_password;

    @BindView(R.id.et_sure_password)
    EditText et_sure_password;

    @BindView(R.id.btn_next)
    Button btn_next;


    private ResultQQMsg mMsg;
    private String phone;

    @Override
    public int getLayoutId() {
        return R.layout.bind_setpwd_acitivity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        phone = getIntent().getExtras().getString("phone");
        mMsg = (ResultQQMsg) getIntent().getExtras().getSerializable("data");
        initBackTitle("设置密码").build();

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(R.string.loading_up);
                if (et_password.getText().toString().trim().length() >= 6 && et_password.getText().toString().trim().length() <= 20) {
                    if (et_password.getText().toString().trim().equals(et_sure_password.getText().toString().trim())) {

                        loadInfo();
                    } else {
                        showToast("两次输入的密码不一致，请重新输入");
                    }
                } else {
                    showToast("密码长度不正确，请重新输入");
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        loadInfo();
    }

    private void loadInfo() {
        mMsg.setPhone(phone);
        if (!TextUtils.isEmpty(et_password.getText()))
            mMsg.setPassword(et_password.getText().toString().trim());
        mPresenter.bind3rdInfo(mMsg);
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
    public void getBindInfo(ResultBase result) {
        dismiss();
        if (result.isSuccess()) {
            PreferencesUtils.putSharePre(Const.NAME, mMsg.getNickname());
            PreferencesUtils.putSharePre(Const.PHONE, phone);
            PreferencesUtils.putSharePre(Const.ADDRESS, mMsg.getProvince() + mMsg.getCity());
            startActivity(MainActivity.class);
        } else {
            ToastUtils.showToast(mContext, result.getMessage());
        }
    }
}
