package com.eaphone.g08android.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.eaphone.g08android.R;
import com.eaphone.g08android.mvp.contracts.PassportContracts;
import com.eaphone.g08android.mvp.presenter.CheckPhonePresenter;
import com.eaphone.g08android.utils.FormatUtil;
import com.hpw.mvpframe.base.CoreBaseActivity;
import com.hpw.mvpframe.base.ResultBase;

import butterknife.BindView;


/**
 * Created by Administrator on 2016/4/14.
 */
public class RegisteActivity extends CoreBaseActivity<CheckPhonePresenter> implements PassportContracts.CheckPhoneView {
    @BindView(R.id.btn_next)
    Button btn_next;

    @BindView(R.id.et_phone)
    EditText et_phone;


    @Override
    public int getLayoutId() {
        return R.layout.login_register_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initData();
        initBackTitle("手机注册").build();
    }

    private void initData() {

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_phone.getText())) {
                    showToast(getResources().getString(R.string.input_phone));
                    return;
                }
                if (FormatUtil.isMobileNO(et_phone.getText().toString())) {
                    mPresenter.isExist(et_phone.getText().toString());
                } else {
                    showToast(getResources().getString(R.string.input_right_phone));
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

    }

    @Override
    public void getIsExist(ResultBase<Boolean> result) {

        if(result.isSuccess()){
            if("user exist".equals(result.getMessage())){
                showToast("手机（用户名）重复");
            }else{
                Bundle bundle = new Bundle();
                bundle.putString("phone", et_phone.getText().toString());
                startActivity(RegisterNextActivity.class, bundle);

            }
        }else{
            showToast(result.getMessage());
        }

    }
}
